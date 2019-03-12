package com.shivaraj.mysqlparser.service.impl.common;

import com.shivaraj.mysqlparser.service.DatabaseService;
import com.shivaraj.mysqlparser.domain.Database;
import com.shivaraj.mysqlparser.repository.DatabaseRepository;
import com.shivaraj.mysqlparser.service.MysqlConnector;
import com.shivaraj.mysqlparser.service.impl.mysql.MysqlConnectorImpl;
import com.shivaraj.mysqlparser.web.rest.vm.ConnectionVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
/**
 * Service Implementation for managing Database.
 */
@Service
public class DatabaseServiceImpl implements DatabaseService {

    private final Logger log = LoggerFactory.getLogger(DatabaseServiceImpl.class);

    @Autowired
    private ApplicationContext applicationContext;
    private final DatabaseRepository databaseRepository;
    private MysqlConnector mysqlConnector;

    public DatabaseServiceImpl(DatabaseRepository databaseRepository) {
        this.databaseRepository = databaseRepository;
    }

    /**
     * Save a database.
     *
     * @param database the entity to save
     * @return the persisted entity
     */
    @Override
    public Database save(Database database) {
        log.debug("Request to save Database : {}", database);        return databaseRepository.save(database);
    }

    /**
     * Get all the databases.
     *
     * @return the list of entities
     */
    @Override
    public List<Database> findAll() {
        log.debug("Request to get all Databases");
        return databaseRepository.findAll();
    }


    /**
     * Get one database by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<Database> findOne(String id) {
        log.debug("Request to get Database : {}", id);
        return databaseRepository.findById(id);
    }

    /**
     * Delete the database by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Database : {}", id);
        databaseRepository.deleteById(id);
    }

    @Override
    public Optional<Database> findByName(String name) {
        log.debug("Request to get Database : {}", name);
        return databaseRepository.findByName();
    }

    @Override
    public Optional<ConnectionVM> testConnection(String id) {
        ConnectionVM connectionVM = null;
        Connection connection = null;
        if(mysqlConnector ==null){
            mysqlConnector = applicationContext.getBean(MysqlConnectorImpl.class);
        }
        try {
            connection = mysqlConnector.getConnection(findOne(id).get());
            if(connection.isValid(0)){
                connectionVM = getSuccessfullConnectionVM();
            }else{
                connectionVM = getUnsuccessfullConnectionVM("Error while connecting to DB");
            }

        } catch (NoSuchElementException | SQLException  e) {
            log.trace("Error while getting database details of id "+id,e);
            connectionVM = getUnsuccessfullConnectionVM(e.getMessage());
        }finally {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                connection = null;
                log.trace("Error while closing connection ",e);
            }
        }
        return Optional.of(connectionVM);
    }

    private ConnectionVM getUnsuccessfullConnectionVM(String errorDescription){
        ConnectionVM connectionVM = new ConnectionVM();
        connectionVM.setConnectionStatus("UNSUCCESSFUL");
        connectionVM.setErrorDescription(errorDescription);
        return connectionVM;
    }
    private ConnectionVM getSuccessfullConnectionVM(){
        ConnectionVM connectionVM = new ConnectionVM();
        connectionVM.setConnectionStatus("SUCCESSFUL");
        return connectionVM;
    }

}
