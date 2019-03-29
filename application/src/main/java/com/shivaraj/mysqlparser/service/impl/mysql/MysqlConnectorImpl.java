package com.shivaraj.mysqlparser.service.impl.mysql;


import com.shivaraj.mysqlparser.domain.Database;
import com.shivaraj.mysqlparser.service.DatabaseService;
import com.shivaraj.mysqlparser.service.MysqlConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

@Service
public class MysqlConnectorImpl implements MysqlConnector{

    private final Logger log = LoggerFactory.getLogger(MysqlConnectorImpl.class);

    private final DatabaseService databaseService;

    public MysqlConnectorImpl(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    private Optional<Database> getDatabaseDetails(String dbName) {
        return databaseService.findByName(dbName);
    }

    @Override
    public Connection getConnection(Database database) throws SQLException {
        return DriverManager.getConnection(getUrl(database));
    }

    private String getUrl(Database database){
        String address = database.getAddress();
        String port = database.getPort();
        String user = database.getUser();
        String password = database.getPassword();
        return String.format("jdbc:mariadb://%s:%s/panaces?user=%s&password=%s",address,port,user,password);
    }

}
