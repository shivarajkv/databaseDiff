package com.shivaraj.mysqlparser.service.impl.mysql;

import com.shivaraj.mysqlparser.domain.Column;
import com.shivaraj.mysqlparser.domain.Database;
import com.shivaraj.mysqlparser.domain.Table;
import com.shivaraj.mysqlparser.service.DatabaseService;
import com.shivaraj.mysqlparser.service.MysqlConnector;
import com.shivaraj.mysqlparser.service.MysqlDatabaseService;
import com.shivaraj.mysqlparser.domain.MysqlDatabase;
import com.shivaraj.mysqlparser.repository.MysqlDatabaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
/**
 * Service Implementation for managing MysqlDatabase.
 */
@Service
public class MysqlDatabaseServiceImpl implements MysqlDatabaseService {

    private final Logger log = LoggerFactory.getLogger(MysqlDatabaseServiceImpl.class);

    private final MysqlDatabaseRepository mysqlDatabaseRepository;

    private final DatabaseService databaseService;

    private final MysqlConnector mysqlConnector;

    public MysqlDatabaseServiceImpl(MysqlDatabaseRepository mysqlDatabaseRepository, DatabaseService databaseService, MysqlConnector mysqlConnector) {
        this.mysqlDatabaseRepository = mysqlDatabaseRepository;
        this.databaseService = databaseService;
        this.mysqlConnector = mysqlConnector;
    }

    /**
     * Save a mysqlDatabase.
     *
     * @param mysqlDatabase the entity to save
     * @return the persisted entity
     */
    @Override
    public MysqlDatabase save(MysqlDatabase mysqlDatabase) {
        log.debug("Request to save MysqlDatabase : {}", mysqlDatabase);
        return mysqlDatabaseRepository.save(mysqlDatabase);
    }

    /**
     * Get all the mysqlDatabases.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<MysqlDatabase> findAll(Pageable pageable) {
        log.debug("Request to get all MysqlDatabases");
        return mysqlDatabaseRepository.findAll(pageable);
    }


    /**
     * Get one mysqlDatabase by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<MysqlDatabase> findOne(String id) {
        log.debug("Request to get MysqlDatabase : {}", id);
        return mysqlDatabaseRepository.findById(id);
    }

    /**
     * Delete the mysqlDatabase by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete MysqlDatabase : {}", id);
        mysqlDatabaseRepository.deleteById(id);
    }

    public void syncData(String id){
        Database database = databaseService.findOne(id).get();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        final String getAllTableDetails = "SELECT TABLE_NAME,COLUMN_NAME,COLUMN_DEFAULT,COLUMN_TYPE " +
            "FROM INFORMATION_SCHEMA.COLUMNS " +
            "WHERE TABLE_SCHEMA='panaces'";
        try {
            connection = mysqlConnector.getConnection(database);
            preparedStatement = connection.prepareStatement(getAllTableDetails);
            ResultSet resultSet = preparedStatement.executeQuery();
            MysqlDatabase mysqlDatabase = new MysqlDatabase();
            while (resultSet.next()){
                String tableName = resultSet.getString("TABLE_NAME");
                String columnName = resultSet.getString("COLUMN_NAME");
                //resultSet.getString("COLUMN_DEFAULT");
                String columnType = resultSet.getString("COLUMN_TYPE");
                Table table = mysqlDatabase.addTable(tableName);
                Column column = new Column(columnName,columnType);
                table.addHeader(column);
            }
            mysqlDatabaseRepository.save(mysqlDatabase);
        } catch (SQLException e) {
            log.trace("Error while connecting to DB",e);
        }finally {
            if(!(ObjectUtils.isEmpty(connection) && ObjectUtils.isEmpty(preparedStatement))){
                try {
                    connection.close();
                    preparedStatement.close();
                } catch (SQLException e) {
                    log.trace("Error while closing connection",e);
                }
            }
        }
    }

}
