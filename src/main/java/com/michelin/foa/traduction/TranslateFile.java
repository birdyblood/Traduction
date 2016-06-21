package com.michelin.foa.traduction;

import org.apache.commons.lang3.StringUtils;

import java.util.Properties;
import java.util.Set;

/**
 * Represent a traduction file. With the lang, the associated lang and the properties file of the reference.
 * Created by FP11523 on 20/02/2016.
 */
public class TranslateFile {

    /**
     * Lang.
     */
    private String lang;

    /**
     * Properties of the reference lang.
     */
    private Properties properties;

    /**
     * Get the lang.
     *
     * @return the lang.
     */
    public String getLang() {
        return lang;
    }

    /**
     * Set the lang.
     *
     * @param lang the lang.
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     * Set the properties.
     *
     * @param properties
     */
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    /**
     * Get the keys.
     *
     * @return
     */
    public Set<String> getKeys() {
        return properties.stringPropertyNames();
    }

    /**
     * Check if the key exist.
     *
     * @param key the key to check.
     * @return true or false.
     */
    public boolean isKeyExist(Object key) {
        return properties.containsKey(key);
    }

    /**
     * Get a property
     *
     * @param key the key.
     * @return the value.
     */
    public String getProperty(String key) {
        return properties.getProperty(key, StringUtils.EMPTY);
    }
}
