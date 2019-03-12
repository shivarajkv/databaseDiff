package com.shivaraj.mysqlparser.web.rest;

import com.shivaraj.mysqlparser.MysqlParserApp;

import com.shivaraj.mysqlparser.domain.MysqlDatabase;
import com.shivaraj.mysqlparser.domain.Table;
import com.shivaraj.mysqlparser.repository.MysqlDatabaseRepository;
import com.shivaraj.mysqlparser.service.MysqlDatabaseService;
import com.shivaraj.mysqlparser.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


import static com.shivaraj.mysqlparser.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MysqlDatabaseResource REST controller.
 *
 * @see MysqlDatabaseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MysqlParserApp.class)
public class MysqlDatabaseResourceIntTest {

    private static final Double DEFAULT_VERSION = 1D;
    private static final Double UPDATED_VERSION = 2D;

    private static final HashMap DEFAULT_TABLES = new HashMap(){{
        put("table1",new Table());
        put("table2",new Table());
    }};
    private static final HashMap UPDATED_TABLES = new HashMap(){{
        put("table3",new Table());
        put("table4",new Table());
    }};

    @Autowired
    private MysqlDatabaseRepository mysqlDatabaseRepository;



    @Autowired
    private MysqlDatabaseService mysqlDatabaseService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restMysqlDatabaseMockMvc;

    private MysqlDatabase mysqlDatabase;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MysqlDatabaseResource mysqlDatabaseResource = new MysqlDatabaseResource(mysqlDatabaseService);
        this.restMysqlDatabaseMockMvc = MockMvcBuilders.standaloneSetup(mysqlDatabaseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MysqlDatabase createEntity() {
        MysqlDatabase mysqlDatabase = new MysqlDatabase()
            .version(DEFAULT_VERSION)
            .tables(DEFAULT_TABLES);
        return mysqlDatabase;
    }

    @Before
    public void initTest() {
        mysqlDatabaseRepository.deleteAll();
        mysqlDatabase = createEntity();
    }

    @Test
    public void createMysqlDatabase() throws Exception {
        int databaseSizeBeforeCreate = mysqlDatabaseRepository.findAll().size();

        // Create the MysqlDatabase
        restMysqlDatabaseMockMvc.perform(post("/api/mysql-databases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mysqlDatabase)))
            .andExpect(status().isCreated());

        // Validate the MysqlDatabase in the database
        List<MysqlDatabase> mysqlDatabaseList = mysqlDatabaseRepository.findAll();
        assertThat(mysqlDatabaseList).hasSize(databaseSizeBeforeCreate + 1);
        MysqlDatabase testMysqlDatabase = mysqlDatabaseList.get(mysqlDatabaseList.size() - 1);
        assertThat(testMysqlDatabase.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testMysqlDatabase.getTables()).isEqualTo(DEFAULT_TABLES);
    }

    @Test
    public void createMysqlDatabaseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mysqlDatabaseRepository.findAll().size();

        // Create the MysqlDatabase with an existing ID
        mysqlDatabase.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restMysqlDatabaseMockMvc.perform(post("/api/mysql-databases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mysqlDatabase)))
            .andExpect(status().isBadRequest());

        // Validate the MysqlDatabase in the database
        List<MysqlDatabase> mysqlDatabaseList = mysqlDatabaseRepository.findAll();
        assertThat(mysqlDatabaseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllMysqlDatabases() throws Exception {
        // Initialize the database
        mysqlDatabaseRepository.save(mysqlDatabase);

        // Get all the mysqlDatabaseList
        restMysqlDatabaseMockMvc.perform(get("/api/mysql-databases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mysqlDatabase.getId())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.doubleValue())))
            .andExpect(jsonPath("$.[*].tables").value(hasItem(DEFAULT_TABLES.toString())));
    }


    @Test
    public void getMysqlDatabase() throws Exception {
        // Initialize the database
        mysqlDatabaseRepository.save(mysqlDatabase);

        // Get the mysqlDatabase
        restMysqlDatabaseMockMvc.perform(get("/api/mysql-databases/{id}", mysqlDatabase.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mysqlDatabase.getId()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.doubleValue()))
            .andExpect(jsonPath("$.tables").value(DEFAULT_TABLES.toString()));
    }
    @Test
    public void getNonExistingMysqlDatabase() throws Exception {
        // Get the mysqlDatabase
        restMysqlDatabaseMockMvc.perform(get("/api/mysql-databases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateMysqlDatabase() throws Exception {
        // Initialize the database
        mysqlDatabaseService.save(mysqlDatabase);

        int databaseSizeBeforeUpdate = mysqlDatabaseRepository.findAll().size();

        // Update the mysqlDatabase
        MysqlDatabase updatedMysqlDatabase = mysqlDatabaseRepository.findById(mysqlDatabase.getId()).get();
        updatedMysqlDatabase
            .version(UPDATED_VERSION)
            .tables(UPDATED_TABLES);

        restMysqlDatabaseMockMvc.perform(put("/api/mysql-databases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMysqlDatabase)))
            .andExpect(status().isOk());

        // Validate the MysqlDatabase in the database
        List<MysqlDatabase> mysqlDatabaseList = mysqlDatabaseRepository.findAll();
        assertThat(mysqlDatabaseList).hasSize(databaseSizeBeforeUpdate);
        MysqlDatabase testMysqlDatabase = mysqlDatabaseList.get(mysqlDatabaseList.size() - 1);
        assertThat(testMysqlDatabase.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testMysqlDatabase.getTables()).isEqualTo(UPDATED_TABLES);
    }

    @Test
    public void updateNonExistingMysqlDatabase() throws Exception {
        int databaseSizeBeforeUpdate = mysqlDatabaseRepository.findAll().size();

        // Create the MysqlDatabase

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMysqlDatabaseMockMvc.perform(put("/api/mysql-databases")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mysqlDatabase)))
            .andExpect(status().isBadRequest());

        // Validate the MysqlDatabase in the database
        List<MysqlDatabase> mysqlDatabaseList = mysqlDatabaseRepository.findAll();
        assertThat(mysqlDatabaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteMysqlDatabase() throws Exception {
        // Initialize the database
        mysqlDatabaseService.save(mysqlDatabase);

        int databaseSizeBeforeDelete = mysqlDatabaseRepository.findAll().size();

        // Get the mysqlDatabase
        restMysqlDatabaseMockMvc.perform(delete("/api/mysql-databases/{id}", mysqlDatabase.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MysqlDatabase> mysqlDatabaseList = mysqlDatabaseRepository.findAll();
        assertThat(mysqlDatabaseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MysqlDatabase.class);
        MysqlDatabase mysqlDatabase1 = new MysqlDatabase();
        mysqlDatabase1.setId("id1");
        MysqlDatabase mysqlDatabase2 = new MysqlDatabase();
        mysqlDatabase2.setId(mysqlDatabase1.getId());
        assertThat(mysqlDatabase1).isEqualTo(mysqlDatabase2);
        mysqlDatabase2.setId("id2");
        assertThat(mysqlDatabase1).isNotEqualTo(mysqlDatabase2);
        mysqlDatabase1.setId(null);
        assertThat(mysqlDatabase1).isNotEqualTo(mysqlDatabase2);
    }
}
