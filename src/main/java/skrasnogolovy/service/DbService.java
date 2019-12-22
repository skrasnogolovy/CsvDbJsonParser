package skrasnogolovy.service;

import skrasnogolovy.model.TestTable;

import java.util.List;

public interface DbService {
    List<TestTable> getAllTables();
    List<TestTable> addAllTestTable(Iterable<TestTable> testTableIterable);
}
