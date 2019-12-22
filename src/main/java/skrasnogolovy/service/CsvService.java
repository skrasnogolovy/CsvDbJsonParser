package skrasnogolovy.service;

import skrasnogolovy.model.TestTable;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CsvService {
    Map<Boolean, Boolean> loadCsv(String documentsPath, String fileName, String destinationFolder);
    Map<Boolean, Boolean> simpleLoadCsv(String documentsPath, String fileName, String destinationFolder);
}
