package br.com.ivansalvadori.ssp.sp.cleaner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
public class ListaOcorrenciasPoliciaisExtractor {

	public void parseListaEdownloadBOs(File pastaPaginasHomicidios, File pastaGravacaoBos, String sessionId)
			throws IOException, InterruptedException {
		File[] files = pastaPaginasHomicidios.listFiles();
		for (File file : files) {
			if (!file.isFile()) {
				continue;
			}
			System.out.println("Processando: " + file.getName());
			Document doc = Jsoup.parse(file, "UTF-8", "http://example.com/");
			Object[] linksToBo = doc.getElementsByAttribute("onclick").toArray();
			for (Object link : linksToBo) {
				String infoLink = ((Element) link).attr("onclick");
				infoLink = infoLink.replace("relatorioBO(", "");
				infoLink = infoLink.replace(");", "");
				String[] split = infoLink.split(",");
				String ano = split[0].trim();
				String numeroBo = split[1].trim();
				String idDelegacia = split[2].trim();
				
				File boDownloaded  = new File(pastaGravacaoBos,
						idDelegacia + "-" + numeroBo + ".html");

		        if(boDownloaded.exists()){
		            System.out.println(boDownloaded.getPath() + " ja baixado");
		            continue;
		        }
				
				downloadBo(sessionId, ano, numeroBo, idDelegacia, pastaGravacaoBos);
			}
		}
	}

	public void downloadBo(String sessionId, String ano, String numeroBo, String idDelegacia, File pastaGravacaoBos)
			throws IOException, InterruptedException {

		File boFile = new File(pastaGravacaoBos, idDelegacia + "-" + numeroBo + ".html");

		System.out.println("baixando BO: " + idDelegacia + "-" + numeroBo);

		HttpClient client = HttpClientBuilder.create().build();

		HttpPost postBo = new HttpPost("http://www.ssp.sp.gov.br/transparenciassp/Consulta.aspx/AbrirBoletim");
		postBo.addHeader("Cookie", "ASP.NET_SessionId=" + sessionId);
		postBo.addHeader("Content-Type", "application/json; charset=utf-8;");
		String requestBody = "{ \"anoBO\": \"%s\", \"numBO\": \"%s\", \"delegacia\": \"%s\" }";
		String format = String.format(requestBody, ano, numeroBo, idDelegacia);
		postBo.setEntity(new StringEntity(format));
		try {
			client.execute(postBo);
			HttpGet getBo = new HttpGet("http://www.ssp.sp.gov.br/transparenciassp/BO.aspx");
			getBo.setHeader("Cookie", "ASP.NET_SessionId=" + sessionId);
			HttpResponse bOresponse = client.execute(getBo);

			InputStream boHtml = bOresponse.getEntity().getContent();

			try {
				Files.copy(boHtml, boFile.toPath());
			} catch (FileAlreadyExistsException e) {
				System.out.println(boFile + " já existe");
			}

		} catch (HttpHostConnectException e) {
			try {
				Thread.sleep(10000);
				System.out.println("segunda tentativa - baixando BO: " + idDelegacia + "-" + numeroBo);
				downloadBo(sessionId, ano, numeroBo, idDelegacia, pastaGravacaoBos);
			} catch (HttpHostConnectException e2) {
				Thread.sleep(10000);
				System.out.println("terceira e eterna tentativa - baixando BO: " + idDelegacia + "-" + numeroBo);
				downloadBo(sessionId, ano, numeroBo, idDelegacia, pastaGravacaoBos);
			}
		}

	}

}
