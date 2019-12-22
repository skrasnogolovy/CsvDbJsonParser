package skrasnogolovy.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skrasnogolovy.service.CsvService;
import skrasnogolovy.service.DbService;
import skrasnogolovy.utils.PropertiesReader;
import java.util.Map;

@RestController
@RequestMapping("/csvLoad")
public class CsvLoadController {
    private static Logger logger = LoggerFactory.getLogger(CsvLoadController.class);

    @Autowired
    private CsvService csvService;
    @Autowired
    private DbService dbService;
    @Autowired
    private PropertiesReader propertiesReader;

    //С использованием apache commons csv
    @GetMapping(value = "/CSVParser")
    public ResponseEntity loadCsvWhithCSVParser() {
        String documentPath = propertiesReader.getProperties("csv.path");
        String fileName = propertiesReader.getProperties("csv.fileName");
        String destinationFolder = propertiesReader.getProperties("csv.successpath");
        Map<Boolean, Boolean> mapResult = csvService.loadCsv(documentPath, fileName, destinationFolder);
        for (Map.Entry<Boolean, Boolean> map : mapResult.entrySet()) {
            if (map.getKey().equals(true) && map.getValue().equals(true)) {
                return new ResponseEntity(null, new HttpHeaders(), HttpStatus.OK);
            }
        }

        return new ResponseEntity(null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Парсинг с использованием io

    @GetMapping(value = "/SimpleParser")
    public ResponseEntity loadCsvWhithSimpleParser() {
        String documentPath = propertiesReader.getProperties("csv.path");
        String fileName = propertiesReader.getProperties("csv.fileName");
        String destinationFolder = propertiesReader.getProperties("csv.successpath");
        Map<Boolean, Boolean> mapResult = csvService.simpleLoadCsv(documentPath, fileName, destinationFolder);
        for (Map.Entry<Boolean, Boolean> map : mapResult.entrySet()) {
            if (map.getKey().equals(true) && map.getValue().equals(true)) {
                return new ResponseEntity(null, new HttpHeaders(), HttpStatus.OK);
            }
        }

        return new ResponseEntity(null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
