package com.glushkov;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

    public Properties getProperties() {
        Properties applicationProperties = readApplicationProperties();

        if (("PROD").equals(System.getenv("env"))) {
            Properties prodProperties = readProdProperties();
            return merge(applicationProperties, prodProperties);
        }

        return applicationProperties;
    }

    Properties readApplicationProperties() {
        Properties properties = new Properties();

        try (BufferedInputStream propertiesBufferedInputStream = new BufferedInputStream(Starter.class.getResourceAsStream("/application.properties"))) {
            properties.load(propertiesBufferedInputStream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("Error while reading application.properties", e);
        }
    }

    Properties readProdProperties() {
        Properties prodProperties = new Properties();
        prodProperties.setProperty("db-prod.url", System.getenv("db.url"));
        prodProperties.setProperty("db-prod.user", System.getenv("db.user"));
        prodProperties.setProperty("db-prod.password", System.getenv("db.password"));
        prodProperties.setProperty("port-prod", System.getenv("PORT"));
        return prodProperties;
    }

    Properties merge(Properties applicationProperties, Properties prodProperties) {
        applicationProperties.setProperty("db.url", prodProperties.getProperty("db-prod.url"));
        applicationProperties.setProperty("db.user", prodProperties.getProperty("db-prod.user"));
        applicationProperties.setProperty("db.password", prodProperties.getProperty("db-prod.password"));
        applicationProperties.setProperty("port", prodProperties.getProperty("port-prod"));
        return applicationProperties;
    }
}
