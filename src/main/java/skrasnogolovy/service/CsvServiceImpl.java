package skrasnogolovy.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skrasnogolovy.model.TestTable;
import skrasnogolovy.utils.PropertiesReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class CsvServiceImpl implements CsvService {
    private static Logger logger = LoggerFactory.getLogger(CsvServiceImpl.class);

    @Autowired
    private DbService dbService;


    @Override
    // Метод использует org.apache.commons.csv
    public Map<Boolean, Boolean> loadCsv(String documentsPath, String fileName, String destinationFolder) {
        Path documentDirectory = Paths.get(documentsPath);
        Path csvPath = documentDirectory.resolve(fileName);
        Map<Boolean, Boolean> returnMap = new HashMap<>();
        CSVParser csvParser;
        List<TestTable> testTableListResult = null;
        try {
            csvParser = CSVParser.parse(csvPath, Charset.defaultCharset(), CSVFormat.DEFAULT.withHeader("id", "name", "value"));
        } catch (IOException e) {
            e.printStackTrace();
            returnMap.put(false, false);
            return returnMap;
        }

        int flag = 0;
        List<Map<String, String>> csvParsedList = new ArrayList<>();

        for (CSVRecord csvRecord : csvParser) {
            Map<String, String> csvMap = csvRecord.toMap();
            if (flag == 0) {
                flag = 1;
                continue;
            }
            csvParsedList.add(csvMap);
//запись части csv-файла в БД
            if (csvParsedList.size() == 1000) {
                testTableListResult = dbService.addAllTestTable(csvParsedList
                        .stream()
                        .map(row -> new TestTable(row.get("id"), row.get("name"), row.get("value")))
                        .collect(Collectors.toList()));
                if (testTableListResult == null) {
                    returnMap.put(false, false);
                    return returnMap;
                }
                testTableListResult = null;
                csvParsedList.clear();
            }
        }
//запись оставшихся строк csv-файла в БД
        if (csvParsedList.size() != 0) {
            testTableListResult = dbService.addAllTestTable(csvParsedList
                    .stream()
                    .map(row -> new TestTable(row.get("id"), row.get("name"), row.get("value")))
                    .collect(Collectors.toList()));

            if (testTableListResult == null) {
                returnMap.put(false, false);
                return returnMap;
            }

        }
//перемещение файла
        File oldDestination = new File(documentsPath, fileName);
        File newDestination = new File(documentsPath + destinationFolder, fileName);
        Boolean fileReanameStatus = oldDestination.renameTo(newDestination);
        returnMap.put(true, fileReanameStatus);
        return returnMap;
    }

    //метод использует io
    public Map<Boolean, Boolean> simpleLoadCsv(String documentsPath, String fileName, String destinationFolder) {
        List<TestTable> testTableList = new ArrayList<>();
        Map<Boolean, Boolean> returnMap = new HashMap<>();
        List<TestTable> testTableListResult = null;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(documentsPath + "\\" + fileName))) {
            String reader = null;
            int flag = 0;
            while ((reader = bufferedReader.readLine()) != null) {
                if (flag == 0) {
                    flag = 1;
                    continue;
                }
                String[] tempReader = reader.split(",");
                testTableList.add(new TestTable(tempReader[0].trim(), tempReader[1].trim(), tempReader[2].trim()));
                if (testTableList.size() == 1000) {
                    testTableListResult = dbService.addAllTestTable(testTableList);
                    if (testTableListResult == null) {
                        returnMap.put(false, false);
                        return returnMap;
                    }
                    testTableListResult = null;
                    testTableList.clear();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            returnMap.put(false, false);
            return returnMap;
        }

        if (testTableList.size() != 0) {
            testTableListResult = dbService.addAllTestTable(testTableList);
            if (testTableListResult == null) {
                returnMap.put(false, false);
                return returnMap;
            }
        }

        File oldDestination = new File(documentsPath, fileName);
        File newDestination = new File(documentsPath + destinationFolder, fileName);
        Boolean fileReanameStatus = oldDestination.renameTo(newDestination);
        returnMap.put(true, fileReanameStatus);
        return returnMap;
    }
}
