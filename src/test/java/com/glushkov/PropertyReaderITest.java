package com.glushkov;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.github.stefanbirkner.systemlambda.SystemLambda.withEnvironmentVariable;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PropertyReaderITest {

    private PropertyReader propertyReader;
    private Map<String, String> propertiesMap;

    private PropertyReaderITest() {
        propertyReader = new PropertyReader();
        propertiesMap = new HashMap<>();
    }

    @Test
    @DisplayName("Returns properties from file")
    void getPropertiesTest() {
        //prepare
        propertiesMap.put("db.url", "jdbc:postgresql://localhost:5432/user_store");
        propertiesMap.put("db.user", "postgres");
        propertiesMap.put("db.password", "23061993");
        propertiesMap.put("port", "8080");
        propertiesMap.put("CREATE_TABLE", "create table IF NOT EXISTS USERS (id serial PRIMARY KEY, firstName " +
                "VARCHAR (50) not null, secondName VARCHAR(50) not null, salary double precision not null, dateOfBirth date not null)");

        //when
        Properties properties = propertyReader.getProperties();

        //then
        assertEquals(propertiesMap.get("db.url"), properties.getProperty("db.url"));
        assertEquals(propertiesMap.get("db.user"), properties.getProperty("db.user"));
        assertEquals(propertiesMap.get("db.password"), properties.getProperty("db.password"));
        assertEquals(propertiesMap.get("port"), properties.getProperty("port"));
        assertEquals(propertiesMap.get("CREATE_TABLE"), properties.getProperty("CREATE_TABLE"));
    }

    @Test
    @DisplayName("Returns complex properties if environment is PROD")
    void getPropertiesProdEnvironmentTest() throws Exception {
        //prepare
        withEnvironmentVariable("env", "PROD")
                .and("db.url", "jdbc:postgresql://localhost:5432/user_store_prod")
                .and("db.user", "alex")
                .and("db.password", "00000000")
                .and("PORT", "3030")
                .execute(() -> {

                    propertiesMap.put("db.url", "jdbc:postgresql://localhost:5432/user_store_prod");
                    propertiesMap.put("db.user", "alex");
                    propertiesMap.put("db.password", "00000000");
                    propertiesMap.put("port", "3030");
                    propertiesMap.put("CREATE_TABLE", "create table IF NOT EXISTS USERS (id serial PRIMARY KEY, firstName " +
                            "VARCHAR (50) not null, secondName VARCHAR(50) not null, salary double precision not null, dateOfBirth date not null)");

                    //when
                    Properties properties = propertyReader.getProperties();

                    //then
                    assertEquals(propertiesMap.get("db.url"), properties.getProperty("db.url"));
                    assertEquals(propertiesMap.get("db.user"), properties.getProperty("db.user"));
                    assertEquals(propertiesMap.get("db.password"), properties.getProperty("db.password"));
                    assertEquals(propertiesMap.get("port"), properties.getProperty("port"));
                    assertEquals(propertiesMap.get("CREATE_TABLE"), properties.getProperty("CREATE_TABLE"));
                });
    }

    @Test
    @DisplayName("Returns properties from application.properties")
    void readApplicationPropertiesTest() {
        //prepare
        propertiesMap.put("db.url", "jdbc:postgresql://localhost:5432/user_store");
        propertiesMap.put("db.user", "postgres");
        propertiesMap.put("db.password", "23061993");
        propertiesMap.put("port", "8080");
        propertiesMap.put("CREATE_TABLE", "create table IF NOT EXISTS USERS (id serial PRIMARY KEY, firstName " +
                "VARCHAR (50) not null, secondName VARCHAR(50) not null, salary double precision not null, dateOfBirth date not null)");
        //when
        Properties properties = propertyReader.readApplicationProperties();

        //then
        assertEquals(propertiesMap.get("db.url"), properties.getProperty("db.url"));
        assertEquals(propertiesMap.get("db.user"), properties.getProperty("db.user"));
        assertEquals(propertiesMap.get("db.password"), properties.getProperty("db.password"));
        assertEquals(propertiesMap.get("port"), properties.getProperty("port"));
        assertEquals(propertiesMap.get("CREATE_TABLE"), properties.getProperty("CREATE_TABLE"));
    }

    @Test
    @DisplayName("Returns properties from environment variables")
    void readProdPropertiesTest() throws Exception {
        //prepare
        withEnvironmentVariable("env", "PROD")
                .and("db.url", "jdbc:postgresql://localhost:5432/user_store_prod")
                .and("db.user", "alex")
                .and("db.password", "00000000")
                .and("PORT", "3030")
                .execute(() -> {

                    propertiesMap.put("db.url", "jdbc:postgresql://localhost:5432/user_store_prod");
                    propertiesMap.put("db.user", "alex");
                    propertiesMap.put("db.password", "00000000");
                    propertiesMap.put("port", "3030");

                    //when
                    Properties prodProperties = propertyReader.readProdProperties();

                    //then
                    assertEquals(propertiesMap.get("db.url"), prodProperties.getProperty("db-prod.url"));
                    assertEquals(propertiesMap.get("db.user"), prodProperties.getProperty("db-prod.user"));
                    assertEquals(propertiesMap.get("db.password"), prodProperties.getProperty("db-prod.password"));
                    assertEquals(propertiesMap.get("port"), prodProperties.getProperty("port-prod"));
                });
    }

    @Test
    @DisplayName("Returns merge properties from application.properties and environment variables")
    void mergeTest() throws Exception {
        //prepare
        withEnvironmentVariable("env", "PROD")
                .and("db.url", "jdbc:postgresql://localhost:5432/user_store_prod")
                .and("db.user", "alex")
                .and("db.password", "00000000")
                .and("PORT", "3030")
                .execute(() -> {

                    propertiesMap.put("db.url", "jdbc:postgresql://localhost:5432/user_store_prod");
                    propertiesMap.put("db.user", "alex");
                    propertiesMap.put("db.password", "00000000");
                    propertiesMap.put("port", "3030");
                    propertiesMap.put("CREATE_TABLE", "create table IF NOT EXISTS USERS (id serial PRIMARY KEY, firstName " +
                            "VARCHAR (50) not null, secondName VARCHAR(50) not null, salary double precision not null, dateOfBirth date not null)");

                    Properties applicationProperties = propertyReader.readApplicationProperties();
                    Properties prodProperties = propertyReader.readProdProperties();

                    //when
                    Properties properties = propertyReader.merge(applicationProperties, prodProperties);

                    //then
                    assertEquals(propertiesMap.get("db.url"), properties.getProperty("db.url"));
                    assertEquals(propertiesMap.get("db.user"), properties.getProperty("db.user"));
                    assertEquals(propertiesMap.get("db.password"), properties.getProperty("db.password"));
                    assertEquals(propertiesMap.get("port"), properties.getProperty("port"));
                    assertEquals(propertiesMap.get("CREATE_TABLE"), properties.getProperty("CREATE_TABLE"));
                });
    }
}

