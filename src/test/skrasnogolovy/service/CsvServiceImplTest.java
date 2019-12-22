package skrasnogolovy.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import skrasnogolovy.utils.PropertiesReader;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application.properties")
@AutoConfigureMockMvc
public class CsvServiceImplTest {

    @Autowired
    private CsvServiceImpl csvService;
    @Autowired
    private PropertiesReader propertiesReader;



    @Before
    public void beforeTest() throws IOException {

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(propertiesReader.getProperties("csv.path")+"/source.csv"));
        String fileText = "id,name,value\n" +
                "1,AAA,1.02\n" +
                "2,AAB,1.5\n" +
                "3,AAC,2\n" +
                "4,AAD,2\n" +
                "5,AAE,1.9\n" +
                "6,AAF,1.53\n" +
                "7,AAG,1.6\n" +
                "8,AAH,1.46\n" +
                "9,AAI,1.01\n" +
                "10,AAJ,1.08";
        bufferedWriter.write(fileText);
        bufferedWriter.close();
    }

    @Test
    public void loadCsvTest() throws Exception{
        String documentPath = propertiesReader.getProperties("csv.path");
        String fileName = propertiesReader.getProperties("csv.fileName");
        String destinationFolder = propertiesReader.getProperties("csv.successpath");
        Assert.assertEquals(csvService.loadCsv(documentPath, fileName, destinationFolder).get(true), true);
    }

    @Test
    public void simpleLoadCsvTest() throws Exception{
        String documentPath = propertiesReader.getProperties("csv.path");
        String fileName = propertiesReader.getProperties("csv.fileName");
        String destinationFolder = propertiesReader.getProperties("csv.successpath");
        Assert.assertEquals(csvService.simpleLoadCsv(documentPath, fileName, destinationFolder).get(true), true);
    }
}
