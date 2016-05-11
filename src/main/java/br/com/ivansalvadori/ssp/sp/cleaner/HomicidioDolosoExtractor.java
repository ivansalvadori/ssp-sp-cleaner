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
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class HomicidioDolosoExtractor {

    public void parse(String folderPath) throws IOException {
        File[] files = new File(folderPath).listFiles();
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
                downloadBo("", ano, numeroBo, idDelegacia);
            }

        }

    }

    public void downloadBo(String sessionId, String ano, String numeroBo, String idDelegacia) throws IOException {

        HttpClient client = HttpClientBuilder.create().build();

        HttpGet getBo = new HttpGet("http://www.ssp.sp.gov.br/transparenciassp/BO.aspx");
        getBo.setHeader("Cookie", "ASP.NET_SessionId=ukdueib12uc2tv24oas5wx2q; _ga=GA1.3.1899212414.1462842076; style=padrao");
        HttpResponse bOresponse = client.execute(getBo);

        String boFilePath = "/home/ivan/Documents/Working/SSP-Dataset/homicidioDoloso/2015/BOs/BO_" + idDelegacia + "-" + numeroBo + ".html";
        InputStream boHtml = bOresponse.getEntity().getContent();

        try {
            Files.copy(boHtml, Paths.get(boFilePath));
        } catch (FileAlreadyExistsException e) {
            System.out.println(boFilePath + " já existe");
        }

    }
}
