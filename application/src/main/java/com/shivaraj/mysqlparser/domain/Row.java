package com.shivaraj.mysqlparser.domain;

import java.util.List;

public class Row {


    private List<Cell> cells;

    public List<Cell> getCells() {
        return cells;
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
    }

    @Override
    public String toString() {
        return "Row{" +
            "cells=" + cells +
            '}';
    }

}
