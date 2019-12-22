package skrasnogolovy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skrasnogolovy.model.TestTable;
import skrasnogolovy.repository.TestTableRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class DBServiseImpl implements DbService {
    private static Logger logger = LoggerFactory.getLogger(DBServiseImpl.class);

    @Autowired
    private TestTableRepo testTableRepo;

    @Override
    public List<TestTable> getAllTables() {
        return testTableRepo.findAll();
    }

    @Override
    public List<TestTable> addAllTestTable(Iterable<TestTable> testTableIterable) {
        return testTableRepo.saveAll(testTableIterable);
    }
}
