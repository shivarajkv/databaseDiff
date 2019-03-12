package com.shivaraj.mysqlparser.repository;

import com.shivaraj.mysqlparser.domain.MysqlDatabase;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the MysqlDatabase entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MysqlDatabaseRepository extends MongoRepository<MysqlDatabase, String> {

}
