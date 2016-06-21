package com.bb.traduction;

import java.util.Objects;

/**
 * The translate batch.
 * Created by FP11523 on 20/02/2016.
 */
public class Traduction {

    private GenerateXls generateXls = new GenerateXls();

    private String typeFile;

    private String lang;


    public Traduction(String typeFile, String lang) {
        this.typeFile = typeFile;
        this.lang = "_" + lang;
    }

    public static void main(String... args) {
        final String type = Objects.requireNonNull(args[0]);
        final String lang = Objects.requireNonNull(args[1]);
        final Traduction traduction = new Traduction(type, lang);
        traduction.generate();
    }


    private void generate() {
        generateXls.exportFilePathName = getXlsFile();
        final TranslateFileFactory factory = new TranslateFileFactory();
        final String propertiesPath = Config.getInstance().getProperty(typeFile + ".path");
        generateXls.defaultProperties = factory.buildTranslateFile(propertiesPath + typeFile + ".properties");
        generateXls.langProperties = factory.buildTranslateFile(propertiesPath + typeFile + lang + ".properties");
        generateXls.generate(typeFile);
    }

    /**
     * Get the Xls file.
     *
     * @return the file/
     */
    private String getXlsFile() {
        final String exportPath = Config.getInstance().getProperty("output");
        return exportPath + typeFile + lang + ".xls";
    }

}
