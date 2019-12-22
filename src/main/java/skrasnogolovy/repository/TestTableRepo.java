package skrasnogolovy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import skrasnogolovy.model.TestTable;

import java.util.List;

@Repository
public interface TestTableRepo extends JpaRepository<TestTable, Long> {
    List<TestTable> findAllByOrderById();

    @Transactional
    TestTable save(TestTable testTable);
}
