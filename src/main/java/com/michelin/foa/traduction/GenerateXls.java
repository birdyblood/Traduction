package com.michelin.foa.traduction;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;

/**
 * Generate a XLS file.
 * Created by FP11523 on 20/02/2016.
 */
public class GenerateXls {

    public TranslateFile defaultProperties;

    public TranslateFile langProperties;

    public String exportFilePathName;

    /**
     * Generate the excel file.
     *
     * @param typeFile type of the file
     */
    public void generate(String typeFile) {

        try (FileOutputStream file = new FileOutputStream(exportFilePathName)) {
            final Workbook workbook = new HSSFWorkbook();
            final Sheet sheet = workbook.createSheet("lang");

            // create header
            createHeader(sheet);

            // Write the lines
            int lastLine = writeLines(sheet, typeFile);
            sheet.setColumnHidden(4, true);
            sheet.setAutoFilter(new CellRangeAddress(0, lastLine, 0, 4));

            // write the file
            workbook.write(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Create the header.
     *
     * @param sheet the sheet.
     */
    private void createHeader(Sheet sheet) {
        final Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("number");
        header.createCell(1).setCellValue("status");
        header.createCell(2).setCellValue("key");
        header.createCell(3).setCellValue("value");
        header.createCell(4).setCellValue("oldValue");
    }

    /**
     * Write the lines.
     *
     * @param sheet    the sheet.
     * @param typeFile the type of file.
     * @return the last line.
     */
    private int writeLines(Sheet sheet, String typeFile) {
        try (KeyIdentity keyIdentity = new KeyIdentity()) {
            int number = 0;
            final Set<String> enumeration = defaultProperties.getKeys();
            Row row;
            for (String key : enumeration) {
                row = sheet.createRow(++number);
                row.createCell(0).setCellValue(keyIdentity.getIdentity(typeFile, key));

                final Cell formulaCell = row.createCell(1);
                formulaCell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
                formulaCell.setCellFormula(getFormula(number + 1));

                row.createCell(2).setCellValue(Objects.toString(key));
                row.createCell(3).setCellValue(langProperties.isKeyExist(key) ? langProperties.getProperty(key) : defaultProperties.getProperty(key));
                row.createCell(4).setCellValue(StringUtils.defaultString(langProperties.getProperty(key)));
            }
            return number;
        }
    }

    /**
     * Get the formula for the line number.
     *
     * @param number number.
     * @return the formula.
     */
    private String getFormula(int number) {
        final StringBuilder builder = new StringBuilder();
        builder.append("IF(E").append(number).append("=\"\",\"new\",IF(E");
        builder.append(number).append(" = D").append(number).append(",\"ok\",\"changed\"))");
        return builder.toString();
    }

}
