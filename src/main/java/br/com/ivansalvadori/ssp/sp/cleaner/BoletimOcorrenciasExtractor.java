package br.com.ivansalvadori.ssp.sp.cleaner;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class BoletimOcorrenciasExtractor {
    
    public Map<String, String> parseDocument() throws IOException{
        Map<String, String> properties = new HashMap<>();
        File input = new File("/home/ivan/Downloads/bo.html");
        Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
        Element element = doc.select("div:matchesOwn(Local:)").get(0);
        System.out.println(element);
        return properties; 
    }

}
