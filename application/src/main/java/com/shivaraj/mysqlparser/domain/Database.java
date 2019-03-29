package com.shivaraj.mysqlparser.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Database.
 */
@Document(collection = "database")
public class Database implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("user")
    private String user;

    @NotNull
    @Field("password")
    //@JsonIgnore
    private String password;

    @NotNull
    @Field("address")
    @Pattern(regexp = "[\\d]{1,3}[.][\\d]{1,3}[.][\\d]{1,3}[.][\\d]{1,3}")
    private String address;

    @Field("port")
    private String port;

    @Field("extras")
    private String extras;

    @Field("type")
    private DatabaseTypes type;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Database name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public Database password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPort() {
        return port;
    }

    public Database port(String port) {
        this.port = port;
        return this;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getExtras() {
        return extras;
    }

    public Database extras(String extras) {
        this.extras = extras;
        return this;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Database address(String address) {
        this.address = address;
        return this;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Database user(String user) {
        this.user = user;
        return this;
    }

    public DatabaseTypes getType() {
        return type;
    }

    public void setType(DatabaseTypes type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Database database = (Database) o;
        if (database.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), database.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Database{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", user='" + user + '\'' +
            ", password='" + password + '\'' +
            ", address='" + address + '\'' +
            ", port='" + port + '\'' +
            ", extras='" + extras + '\'' +
            ", type=" + type +
            '}';
    }
}
