package skrasnogolovy.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skrasnogolovy.model.TestTable;
import skrasnogolovy.service.DbService;

import java.util.List;

@RestController
@RequestMapping("/jsonSave")
public class JsonSaveController {
    private static Logger logger = LoggerFactory.getLogger(JsonSaveController.class);

    @Autowired
    private DbService dbService;

    @GetMapping(value = {"/"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<TestTable>> getJsonTable() {
        HttpHeaders headers = new HttpHeaders();
        List<TestTable> testTableList = dbService.getAllTables();

        return new ResponseEntity<>(testTableList, headers, HttpStatus.FOUND);
    }

}
