package cleaner.test;

import java.io.IOException;

import org.junit.Test;

import br.com.ivansalvadori.ssp.sp.cleaner.ListaOcorrenciasPoliciaisExtractor;

public class HomicidioDolosoExtractorTest {

    @Test
    public void homicidioDolosoExtractorTest() throws IOException {
        ListaOcorrenciasPoliciaisExtractor extractor = new ListaOcorrenciasPoliciaisExtractor();
        String pastaPaginasHomicidios = "/home/ivan/Documents/Working/SSP-Datasets/HomicidioDoloso/2015/Fev/RawData/";
        String pastaGravacaoBos = "/home/ivan/Documents/Working/SSP-Datasets/HomicidioDoloso/2015/Fev/RawData/BOs/";
        String sessionId = "wvmuubkkllijo3s2lu4lsviw";
        extractor.parse(pastaPaginasHomicidios, pastaGravacaoBos, sessionId);
    }

}
