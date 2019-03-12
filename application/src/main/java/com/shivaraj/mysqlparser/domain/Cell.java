package com.shivaraj.mysqlparser.domain;

public class Cell {

    private Column column;
    private String value;

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Cell{" +
            "column=" + column +
            ", value='" + value + '\'' +
            '}';
    }

}
