package com.shivaraj.mysqlparser.service;

import com.shivaraj.mysqlparser.domain.Database;
import com.shivaraj.mysqlparser.web.rest.vm.ConnectionVM;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Database.
 */
public interface DatabaseService {

    /**
     * Save a database.
     *
     * @param database the entity to save
     * @return the persisted entity
     */
    Database save(Database database);

    /**
     * Get all the databases.
     *
     * @return the list of entities
     */
    List<Database> findAll();


    /**
     * Get the "id" database.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Database> findOne(String id);

    /**
     * Delete the "id" database.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    Optional<Database> findByName(String name);

    Optional<ConnectionVM> testConnection(String id);
}
