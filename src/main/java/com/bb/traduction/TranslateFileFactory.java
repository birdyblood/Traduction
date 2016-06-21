package com.bb.traduction;

import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Factory for {@link TranslateFile}.
 * Created by FP11523 on 20/02/2016.
 */
class TranslateFileFactory {

    /**
     * Build a translate file.
     *
     * @param path the path of the properties file.
     * @return the translate file.
     */
    TranslateFile buildTranslateFile(String path) {
        final TranslateFile translateFile = new TranslateFile();

        try (FileInputStream inputStream = new FileInputStream(path)) {
            final Properties properties = new Properties();
            properties.load(inputStream);
            translateFile.setProperties(properties);
            translateFile.setLang(retrieveLang(path));
            return translateFile;

        } catch (FileNotFoundException e) {
            final Properties properties = new Properties();
            translateFile.setProperties(properties);
            translateFile.setLang(retrieveLang(path));
            return translateFile;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieve the lang.
     *
     * @param path the path.
     * @return the lang in the path.
     */
    String retrieveLang(String path) {
        final String pathWithoutExtension = StringUtils.removeEnd(path, ".properties");
        return StringUtils.substringAfter(pathWithoutExtension, "_");
    }


}
