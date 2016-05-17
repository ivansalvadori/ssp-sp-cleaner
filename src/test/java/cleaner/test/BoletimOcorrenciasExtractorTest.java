package cleaner.test;

import java.io.IOException;

import org.junit.Test;

import br.com.ivansalvadori.ssp.sp.cleaner.BoletimOcorrenciasExtractor;

public class BoletimOcorrenciasExtractorTest {

    @Test
    public void boExtractorTest() throws IOException {
        BoletimOcorrenciasExtractor extractor = new BoletimOcorrenciasExtractor();
        String input = "/home/ivan/Documents/Working/SSP-Datasets/HomicidioDoloso/2015/Fev/RawData/BOs";
        String output = "/home/ivan/Documents/Working/SSP-Datasets/HomicidioDoloso/2015/Fev/BOsProcessados/";
        extractor.parseDocument(input, output);
    }

}
