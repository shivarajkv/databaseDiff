package com.shivaraj.mysqlparser.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.shivaraj.mysqlparser.domain.Database;
import com.shivaraj.mysqlparser.service.DatabaseService;
import com.shivaraj.mysqlparser.web.rest.errors.BadRequestAlertException;
import com.shivaraj.mysqlparser.web.rest.util.HeaderUtil;
import com.shivaraj.mysqlparser.web.rest.vm.ConnectionVM;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Database.
 */
@RestController
@RequestMapping("/api")
public class DatabaseResource {

    private final Logger log = LoggerFactory.getLogger(DatabaseResource.class);

    private static final String ENTITY_NAME = "database";

    private final DatabaseService databaseService;

    public DatabaseResource(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    /**
     * POST  /databases : Create a new database.
     *
     * @param database the database to create
     * @return the ResponseEntity with status 201 (Created) and with body the new database, or with status 400 (Bad Request) if the database has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/databases")
    @Timed
    public ResponseEntity<Database> createDatabase(@Valid @RequestBody Database database) throws URISyntaxException {
        log.debug("REST request to save Database : {}", database);
        if (database.getId() != null) {
            throw new BadRequestAlertException("A new database cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Database result = databaseService.save(database);
        return ResponseEntity.created(new URI("/api/databases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /databases : Updates an existing database.
     *
     * @param database the database to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated database,
     * or with status 400 (Bad Request) if the database is not valid,
     * or with status 500 (Internal Server Error) if the database couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/databases")
    @Timed
    public ResponseEntity<Database> updateDatabase(@Valid @RequestBody Database database) throws URISyntaxException {
        log.debug("REST request to update Database : {}", database);
        if (database.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Database result = databaseService.save(database);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, database.getId().toString()))
            .body(result);
    }

    /**
     * GET  /databases : get all the databases.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of databases in body
     */
    @GetMapping("/databases")
    @Timed
    public List<Database> getAllDatabases() {
        log.debug("REST request to get all Databases");
        return databaseService.findAll();
    }

    /**
     * GET  /databases/:id : get the "id" database.
     *
     * @param id the id of the database to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the database, or with status 404 (Not Found)
     */
    @GetMapping("/databases/{id}")
    @Timed
    public ResponseEntity<Database> getDatabase(@PathVariable String id) {
        log.debug("REST request to get Database : {}", id);
        Optional<Database> database = databaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(database);
    }

    /**
     * DELETE  /databases/:id : delete the "id" database.
     *
     * @param id the id of the database to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/databases/{id}")
    @Timed
    public ResponseEntity<Void> deleteDatabase(@PathVariable String id) {
        log.debug("REST request to delete Database : {}", id);
        databaseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    @GetMapping("/databases/{id}/test")
    @Timed
    public ResponseEntity<ConnectionVM> testConnection(@PathVariable String id) {
        Optional<ConnectionVM> connectionVM = databaseService.testConnection(id);
        return ResponseUtil.wrapOrNotFound(connectionVM);
    }
}
