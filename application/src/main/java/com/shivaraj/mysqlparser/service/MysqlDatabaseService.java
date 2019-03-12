package com.shivaraj.mysqlparser.service;

import com.shivaraj.mysqlparser.domain.MysqlDatabase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing MysqlDatabase.
 */
public interface MysqlDatabaseService {

    /**
     * Save a mysqlDatabase.
     *
     * @param mysqlDatabase the entity to save
     * @return the persisted entity
     */
    MysqlDatabase save(MysqlDatabase mysqlDatabase);

    /**
     * Get all the mysqlDatabases.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MysqlDatabase> findAll(Pageable pageable);


    /**
     * Get the "id" mysqlDatabase.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<MysqlDatabase> findOne(String id);

    /**
     * Delete the "id" mysqlDatabase.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    void syncData(String id);

}
