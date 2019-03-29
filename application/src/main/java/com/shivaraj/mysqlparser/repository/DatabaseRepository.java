package com.shivaraj.mysqlparser.repository;

import com.shivaraj.mysqlparser.domain.Database;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data MongoDB repository for the Database entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DatabaseRepository extends MongoRepository<Database, String> {


    Optional<Database> findByName();
}
