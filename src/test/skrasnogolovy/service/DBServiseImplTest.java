package skrasnogolovy.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import skrasnogolovy.model.TestTable;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application.properties")
@AutoConfigureMockMvc
public class DBServiseImplTest {
    @Autowired
    private DbService dbService;

    @Test
    public void setDbServiceImplTest() throws Exception {
        TestTable testTable1 = new TestTable("1", "AA", "1.5");
        TestTable testTable2 = new TestTable("2", "BB", "1.6");
        TestTable testTable3 = new TestTable("3", "CC", "1.7");
        List<TestTable> testTableList = new ArrayList<>();
        testTableList.add(testTable1);
        testTableList.add(testTable2);
        testTableList.add(testTable3);
        Assert.assertEquals(dbService.addAllTestTable(testTableList), dbService.getAllTables());
    }

}
