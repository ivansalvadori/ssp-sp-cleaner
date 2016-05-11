package cleaner.test;

import java.io.IOException;

import org.junit.Test;

import br.com.ivansalvadori.ssp.sp.cleaner.HomicidioDolosoExtractor;

public class HomicidioDolosoExtractorTest {

    @Test
    public void homicidioDolosoExtractorTest() throws IOException {
        HomicidioDolosoExtractor extractor = new HomicidioDolosoExtractor();
        extractor.parse("/home/ivan/Documents/Working/SSP-Dataset/homicidioDoloso/2015");
    }

}
