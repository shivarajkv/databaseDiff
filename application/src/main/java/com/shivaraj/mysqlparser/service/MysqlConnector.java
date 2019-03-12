package com.shivaraj.mysqlparser.service;

import com.shivaraj.mysqlparser.domain.Database;

import java.sql.Connection;
import java.sql.SQLException;

public interface MysqlConnector {

    Connection getConnection(Database database) throws SQLException;


}
