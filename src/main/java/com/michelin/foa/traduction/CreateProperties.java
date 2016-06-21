package com.michelin.foa.traduction;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Properties;

/**
 * Create properties file from excel file
 * Created by FP11523 on 04/03/2016.
 */
public class CreateProperties {


    public static void main(String... args) {
        final CreateProperties createProperties = new CreateProperties();
        createProperties.integrateProperties();
    }

    /**
     * Integrate properties.
     */
    public void integrateProperties() {
        // Find all the file to integrate
        final Path xlsDirectory = Paths.get(Config.getInstance().getProperty("xls.path"));
        try {
            Files.walkFileTree(xlsDirectory, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    String output = StringUtils.substringBeforeLast(file.toAbsolutePath().toString(), "xls") + "properties";
                    storeProperties(file, output, false);

                    //
                    if (StringUtils.contains(file.getFileName().toString(), "_fr")) {
                        output = StringUtils.substringBeforeLast(file.toAbsolutePath().toString(), "_fr.xls") + "_ja.properties";
                        storeProperties(file, output, true);
                    }

                    //
                    if (StringUtils.contains(file.getFileName().toString(), "_en")) {
                        output = StringUtils.substringBeforeLast(file.toAbsolutePath().toString(), "_en.xls") + "_sv.properties";
                        storeProperties(file, output, true);
                    }

                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void storeProperties(Path file, String output, boolean withKey) throws IOException {
        final ReadXls readXls = new ReadXls();
        final Properties properties = withKey ? readXls.readWithKey(file.toFile()) : readXls.read(file.toFile());

        final FileOutputStream os = new FileOutputStream(new File(output));
        properties.store(os, "Generated Translation for FOA properties");
    }
}
