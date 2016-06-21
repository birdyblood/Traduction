package com.bb.traduction;

import org.apache.commons.lang3.StringUtils;

import java.io.Closeable;
import java.sql.*;

/**
 * Key identity of one key and the name.
 * Created by FP11523 on 21/02/2016.
 */
class KeyIdentity implements Closeable {

    /**
     * DB connection.
     */
    private Connection connection;


    /**
     * Singleton.
     */
    KeyIdentity() {
        try {
            //
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(Config.getInstance().getProperty("h2Base"), "sa", "");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Get the identity of one key property.
     *
     * @param key      the key property.
     * @param typeFile the type of the file
     * @return a string.
     */
    String getIdentity(String typeFile, String key) {
        try {
            return findKeyFromProperties(key, typeFile);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String findKeyFromProperties(String property, String typeFile) throws SQLException {
        final PreparedStatement statement = connection.prepareStatement("select id_key from foa_key where key_property = ?");
        statement.setString(1, property);
        final ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return createLine(property, typeFile);
    }

    private String createLine(String property, String typeFile) throws SQLException {
        final String prefix = getPrefix(typeFile);
        final int count = countForPrefix(prefix);
        final String key = prefix + StringUtils.leftPad(Integer.toString(count + 1), 5, "0");

        final PreparedStatement statement = connection.prepareStatement("insert into foa_key (id_key, key_property, type_file) values (?, ?, ?)");
        statement.setString(1, key);
        statement.setString(2, property);
        statement.setString(3, prefix);
        statement.execute();
        return key;
    }

    private int countForPrefix(String prefix) throws SQLException {
        final PreparedStatement statement = connection.prepareStatement("select count(type_file) from foa_key where type_file = ?");
        statement.setString(1, prefix);
        final ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    private String getPrefix(String typeFile) {
        String prefix;
        switch (typeFile) {
            case "appCommons":
                prefix = "AC";
                break;
            case "appFacesWeb":
                prefix = "AFW";
                break;
            case "javaxFaces":
                prefix = "JF";
                break;
            case "javaxValidation":
                prefix = "JV";
                break;
            case "xnetCommons":
                prefix = "XC";
                break;
            case "xnetFacesWeb":
                prefix = "XFW";
                break;
            default:
                prefix = "XX";
        }
        return prefix;
    }


    @Override
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
