package skrasnogolovy.model;

import javax.persistence.*;

@Entity
@Table(name = "test_table")
public class TestTable {

    @Id
    @Column(name = "id")
    private Long id;

    @Column
    private String name;
    @Column
    private String value;

    public TestTable(){}

    public TestTable(String id, String name, String value) {
        this.id = Long.parseLong(id);
        this.name = name;
        this.value = value;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "TestTable{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
