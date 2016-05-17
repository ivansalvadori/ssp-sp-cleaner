package br.com.ivansalvadori.ssp.sp.cleaner.main;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import br.com.ivansalvadori.ssp.sp.cleaner.BoletimOcorrenciasExtractor;
import br.com.ivansalvadori.ssp.sp.cleaner.ListaOcorrenciasPoliciaisExtractor;

@ComponentScan("br.com.ivansalvadori.ssp")
@SpringBootApplication
public class Main {

	private static String opcao;
	
    @Autowired
    private Config config;

    @Autowired
    private ListaOcorrenciasPoliciaisExtractor listaOcorrenciasPoliciaisExtractor;

    @Autowired
    private BoletimOcorrenciasExtractor boletimOcorrenciasExtractor;

    @PostConstruct
    public void iniciarDownload() throws IOException, InterruptedException {
    	if(opcao.equalsIgnoreCase("baixar")){
    		listaOcorrenciasPoliciaisExtractor.parseListaEdownloadBOs(config.getPastaPaginasListagem(), config.getPastaLeituraBOs(), config.getSessionId());
    	}
    	
    	else if(opcao.equalsIgnoreCase("processar")){
    		boletimOcorrenciasExtractor.parseDocument(config.getPastaLeituraBOs(), config.getPastaGravacaoBOsProcessados());
    	}
    	
    	else if(opcao.equalsIgnoreCase("baixar&processar")){
    		listaOcorrenciasPoliciaisExtractor.parseListaEdownloadBOs(config.getPastaPaginasListagem(), config.getPastaLeituraBOs(), config.getSessionId());
    		boletimOcorrenciasExtractor.parseDocument(config.getPastaLeituraBOs(), config.getPastaGravacaoBOsProcessados());
    	}
    	System.out.println("Fim...");
    }

    public static void main(String[] args) {
    	opcao = args[0];
        SpringApplication.run(Main.class, args);
    }

}
