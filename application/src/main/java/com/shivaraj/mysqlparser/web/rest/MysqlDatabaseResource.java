package com.shivaraj.mysqlparser.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.shivaraj.mysqlparser.domain.MysqlDatabase;
import com.shivaraj.mysqlparser.service.MysqlDatabaseService;
import com.shivaraj.mysqlparser.web.rest.errors.BadRequestAlertException;
import com.shivaraj.mysqlparser.web.rest.util.HeaderUtil;
import com.shivaraj.mysqlparser.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MysqlDatabase.
 */
@RestController
@RequestMapping("/api")
public class MysqlDatabaseResource {

    private final Logger log = LoggerFactory.getLogger(MysqlDatabaseResource.class);

    private static final String ENTITY_NAME = "mysqlDatabase";

    private final MysqlDatabaseService mysqlDatabaseService;

    public MysqlDatabaseResource(MysqlDatabaseService mysqlDatabaseService) {
        this.mysqlDatabaseService = mysqlDatabaseService;
    }

    /**
     * POST  /mysql-databases : Create a new mysqlDatabase.
     *
     * @param mysqlDatabase the mysqlDatabase to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mysqlDatabase, or with status 400 (Bad Request) if the mysqlDatabase has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mysql-databases")
    @Timed
    public ResponseEntity<MysqlDatabase> createMysqlDatabase(@RequestBody MysqlDatabase mysqlDatabase) throws URISyntaxException {
        log.debug("REST request to save MysqlDatabase : {}", mysqlDatabase);
        if (mysqlDatabase.getId() != null) {
            throw new BadRequestAlertException("A new mysqlDatabase cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MysqlDatabase result = mysqlDatabaseService.save(mysqlDatabase);
        return ResponseEntity.created(new URI("/api/mysql-databases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mysql-databases : Updates an existing mysqlDatabase.
     *
     * @param mysqlDatabase the mysqlDatabase to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mysqlDatabase,
     * or with status 400 (Bad Request) if the mysqlDatabase is not valid,
     * or with status 500 (Internal Server Error) if the mysqlDatabase couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mysql-databases")
    @Timed
    public ResponseEntity<MysqlDatabase> updateMysqlDatabase(@RequestBody MysqlDatabase mysqlDatabase) throws URISyntaxException {
        log.debug("REST request to update MysqlDatabase : {}", mysqlDatabase);
        if (mysqlDatabase.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MysqlDatabase result = mysqlDatabaseService.save(mysqlDatabase);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mysqlDatabase.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mysql-databases : get all the mysqlDatabases.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mysqlDatabases in body
     */
    @GetMapping("/mysql-databases")
    @Timed
    public ResponseEntity<List<MysqlDatabase>> getAllMysqlDatabases(Pageable pageable) {
        log.debug("REST request to get a page of MysqlDatabases");
        Page<MysqlDatabase> page = mysqlDatabaseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mysql-databases");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mysql-databases/:id : get the "id" mysqlDatabase.
     *
     * @param id the id of the mysqlDatabase to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mysqlDatabase, or with status 404 (Not Found)
     */
    @GetMapping("/mysql-databases/{id}")
    @Timed
    public ResponseEntity<MysqlDatabase> getMysqlDatabase(@PathVariable String id) {
        log.debug("REST request to get MysqlDatabase : {}", id);
        Optional<MysqlDatabase> mysqlDatabase = mysqlDatabaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mysqlDatabase);
    }

    /**
     * DELETE  /mysql-databases/:id : delete the "id" mysqlDatabase.
     *
     * @param id the id of the mysqlDatabase to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mysql-databases/{id}")
    @Timed
    public ResponseEntity<Void> deleteMysqlDatabase(@PathVariable String id) {
        log.debug("REST request to delete MysqlDatabase : {}", id);
        mysqlDatabaseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    @GetMapping("/mysql-databases/sync/{id}")
    @Timed
    public ResponseEntity<Void> getAllMysqlDatabases(@PathVariable String id) {
        log.debug("REST request to sync MysqlDatabases");
        mysqlDatabaseService.syncData(id);
        return ResponseEntity.ok().build();
    }


}
