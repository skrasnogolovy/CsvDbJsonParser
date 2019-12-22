package skrasnogolovy.controllers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import skrasnogolovy.utils.PropertiesReader;

import java.io.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application.properties")
@AutoConfigureMockMvc
public class JsonSaveControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CsvLoadController csvLoadController;
    @Autowired
    private JsonSaveController jsonSaveController;
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
        csvLoadController.loadCsvWhithCSVParser();
    }
    @Test
    public void getJsonTableTest() throws Exception{
        this.mockMvc
                .perform(get("/jsonSave/"))
                .andExpect(status().isFound())
                .andExpect(content().string("[{\"id\":1,\"name\":\"AAA\",\"value\":\"1.02\"},{\"id\":2,\"name\":\"AAB\",\"value\":\"1.5\"},{\"id\":3,\"name\":\"AAC\",\"value\":\"2\"},{\"id\":4,\"name\":\"AAD\",\"value\":\"2\"},{\"id\":5,\"name\":\"AAE\",\"value\":\"1.9\"},{\"id\":6,\"name\":\"AAF\",\"value\":\"1.53\"},{\"id\":7,\"name\":\"AAG\",\"value\":\"1.6\"},{\"id\":8,\"name\":\"AAH\",\"value\":\"1.46\"},{\"id\":9,\"name\":\"AAI\",\"value\":\"1.01\"},{\"id\":10,\"name\":\"AAJ\",\"value\":\"1.08\"}]"));


    }
}
