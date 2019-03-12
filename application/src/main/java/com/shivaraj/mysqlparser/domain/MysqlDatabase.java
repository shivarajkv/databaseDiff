package com.shivaraj.mysqlparser.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.*;

/**
 * A MysqlDatabase.
 */
@Document(collection = "mysql_database")
public class MysqlDatabase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("version")
    private Double version;

    @Field("tables")
    private HashMap<String,Table> tables;

    private

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getVersion() {
        return version;
    }

    public MysqlDatabase version(Double version) {
        this.version = version;
        return this;
    }

    public void setVersion(Double version) {
        this.version = version;
    }

    public HashMap<String,Table> getTables() {
        return tables;
    }

    public MysqlDatabase tables(HashMap<String,Table> tables) {
        this.tables = tables;
        return this;
    }

    public void setTables(HashMap<String,Table> tables) {
        this.tables = tables;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MysqlDatabase mysqlDatabase = (MysqlDatabase) o;
        if (mysqlDatabase.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mysqlDatabase.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MysqlDatabase{" +
            "id=" + getId() +
            ", version=" + getVersion() +
            ", tables='" + getTables() + "'" +
            "}";
    }

    private void createNewTablesIfEmpty(){
        if(CollectionUtils.isEmpty(tables)){
            tables = new HashMap<>();
        }
    }

    public Table addTable(Table table) {
        createNewTablesIfEmpty();
        String name = table.getName();
        tables.put(name,table);
        return table;
    }

    public Table addTable(String tableName) {
        createNewTablesIfEmpty();
        Table table = tables.get(tableName);
        if(ObjectUtils.isEmpty(table)){
            table = new Table();
            tables.put(tableName,table);
        }
        return table;
    }

}
