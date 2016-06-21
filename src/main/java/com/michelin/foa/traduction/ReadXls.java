package com.michelin.foa.traduction;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Generate a XLS file.
 * Created by FP11523 on 20/02/2016.
 */
public class ReadXls {

    private static final Logger LOGGER = Logger.getLogger(ReadXls.class.getName());

    /**
     * Read the excel file and generate properties file.
     *
     * @param xlsFile the file xls
     * @return the properties file modified.
     */
    public Properties read(File xlsFile) {
        return buildNewProperties(xlsFile, false);
    }

    /**
     * Build new properties.
     *
     * @param xlsFile the XLS file.
     * @param addKey  the key.
     * @return properties.
     */
    private Properties buildNewProperties(File xlsFile, boolean addKey) {
        final Properties originProperties = new Properties();
        try {
            LOGGER.fine("File process :" + xlsFile.getName());
            final Workbook workbook = new HSSFWorkbook(new POIFSFileSystem(xlsFile));
            final Sheet sheet = workbook.getSheetAt(0);
            final int nbRows = sheet.getPhysicalNumberOfRows();
            for (int i = 0; i < nbRows; i++) {
                LOGGER.finer("Line to process in XLS : " + i);
                final Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                //
                final String key = row.getCell(2).getStringCellValue();

                String value = "";
                if (addKey) {
                    // Read the database to get the key
                    value = row.getCell(0).getStringCellValue() + " ";
                }
                value = value + row.getCell(3).getStringCellValue();

                // Set in the property file
                originProperties.setProperty(key, value);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return originProperties;
    }

    /**
     * Read with key.
     *
     * @param xlsFile the xls file.
     * @return properties with key
     */
    public Properties readWithKey(File xlsFile) {
        return buildNewProperties(xlsFile, true);
    }
}
