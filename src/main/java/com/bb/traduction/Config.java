package com.bb.traduction;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration class to get all the configuration.
 * Created by fp11523 on 22/02/2016.
 */
class Config {

    /**
     * The unique instance.
     */
    private static Config instance;

    /**
     * The properties of the config file.
     */
    private Properties properties;

    /**
     *
     */
    private Config() {
        properties = new Properties();
        try (InputStream inputStream = Traduction.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * The config instance.
     * @return a config.
     */
    static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    /**
     * Get a property value.
     * @param key the key.
     * @return the value.
     */
    String getProperty(String key) {
        return properties.getProperty(key);
    }
}
