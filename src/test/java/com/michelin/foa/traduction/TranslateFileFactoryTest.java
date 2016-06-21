package com.michelin.foa.traduction;

import org.apache.commons.lang3.StringUtils;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * SUT {@link TranslateFileFactory}.
 * Created by FP11523 on 20/02/2016.
 */
public class TranslateFileFactoryTest {

    @Test
    public void testBuild_shouldGetNoLangWithDefaultProperties() {
        final TranslateFileFactory factory = new TranslateFileFactory();
        final TranslateFile translateFile = factory.buildTranslateFile("C:\\thierry\\workspaceRAD8\\foa\\foaCommons\\src-gen\\resources\\appCommons.properties");
        assertNotNull(translateFile);
        assertTrue("Lang must be empty its the reference", StringUtils.isEmpty(translateFile.getLang()));
    }

    @Test
    public void testBuild_shouldGetLangFr() {
        final TranslateFileFactory factory = new TranslateFileFactory();
        final TranslateFile translateFile = factory.buildTranslateFile("C:\\thierry\\workspaceRAD8\\foa\\foaCommons\\src-gen\\resources\\appCommons_fr.properties");
        assertNotNull(translateFile);
        assertEquals("fr", translateFile.getLang());
    }

    @Test
    public void testRetrieveLang_shouldGetFr() {
        final TranslateFileFactory factory = new TranslateFileFactory();
        final String lang = factory.retrieveLang("file_fr_FR.properties");
        assertEquals("fr_FR", lang);
    }

}
