package br.com.ivansalvadori.ssp.sp.cleaner;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class BoletimOcorrenciasExtractor {

    public Map<String, String> parseDocument() throws IOException {
        BoletimOcorrencia boletimOcorrencia = new BoletimOcorrencia();
        Map<String, String> properties = new HashMap<>();
        File input = new File("/home/ivan/Downloads/bo.html");
        Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
        Element divLocal = doc.select("div:matchesOwn(Local:)").get(0);
        Iterator<Element> iteratorDetalhes = divLocal.parent().parent().parent().getElementsByTag("div").iterator();
        while (iteratorDetalhes.hasNext()) {
            String textoDetalhe = iteratorDetalhes.next().html();
            if (textoDetalhe.equalsIgnoreCase("Local:")) {
                StringBuilder local = new StringBuilder(iteratorDetalhes.next().html());
                local.append(" - ");
                local.append(iteratorDetalhes.next().html());
                boletimOcorrencia.setLocal(local.toString());
            }
            if (textoDetalhe.equalsIgnoreCase("Tipo de Local:")) {
                StringBuilder tipoLocal = new StringBuilder(iteratorDetalhes.next().html());
                boletimOcorrencia.setTipoLocal(tipoLocal.toString());
            }
            if (textoDetalhe.equalsIgnoreCase("Circunscrição:")) {
                StringBuilder circunscricao = new StringBuilder(iteratorDetalhes.next().html());
                boletimOcorrencia.setCircunscricao(circunscricao.toString());
            }
            if (textoDetalhe.equalsIgnoreCase("Ocorrência:")) {
                StringBuilder ocorrencia = new StringBuilder(iteratorDetalhes.next().html());
                boletimOcorrencia.setDataOcorrencia(ocorrencia.toString());
            }
            if (textoDetalhe.equalsIgnoreCase("Comunicação:")) {
                StringBuilder comunicacao = new StringBuilder(iteratorDetalhes.next().html());
                Pattern pattern = Pattern.compile("([0-9]{2})/([0-9]{2})/([0-9]{4})");
                Matcher matcher = pattern.matcher(comunicacao);
                if (matcher.find()) {
                    String data = matcher.group();
                    boletimOcorrencia.setDataComunicacao(data);
                    String comunicacaoCompleta = comunicacao.toString();
                    String hora = comunicacaoCompleta.replace(data, "");
                    hora = hora.replace("às", "");
                    hora = hora.replace("horas", "");
                    boletimOcorrencia.setHoraComunicacao(hora.trim());
                }
            }
            if (textoDetalhe.equalsIgnoreCase("Elaboração:")) {
                StringBuilder elaboracao = new StringBuilder(iteratorDetalhes.next().html());
                Pattern pattern = Pattern.compile("([0-9]{2})/([0-9]{2})/([0-9]{4})");
                Matcher matcher = pattern.matcher(elaboracao);
                if (matcher.find()) {
                    String data = matcher.group();
                    boletimOcorrencia.setDataElaboracao(data);
                    String elaboracaoCompleta = elaboracao.toString();
                    String hora = elaboracaoCompleta.replace(data, "");
                    hora = hora.replace("às", "");
                    hora = hora.replace("horas", "");
                    boletimOcorrencia.setHoraElaboracao(hora.trim());
                }
            }
            if (textoDetalhe.equalsIgnoreCase("Flagrante:")) {
                StringBuilder flagrante = new StringBuilder(iteratorDetalhes.next().html());
                boletimOcorrencia.setFlagrante(flagrante.toString());
            }
        }

        System.out.println(boletimOcorrencia);

        return properties;
    }

}
