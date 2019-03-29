package com.shivaraj.mysqlparser.domain;

import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Table {

    private String name;
    private HashSet<Column> header;
    private List<Row> rows;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Column> getHeader() {
        return header;
    }

    public void setHeader(HashSet<Column> header) {
        this.header = header;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Table{" +
            "name='" + name + '\'' +
            ", header=" + header +
            ", rows=" + rows +
            ", type='" + type + '\'' +
            '}';
    }

    private void createNewHeaderIfEmpty(){
        if(CollectionUtils.isEmpty(header)){
            header = new HashSet<>();
        }
    }

    public void addHeader(Column column){
        createNewHeaderIfEmpty();
        header.add(column);
    }




}
