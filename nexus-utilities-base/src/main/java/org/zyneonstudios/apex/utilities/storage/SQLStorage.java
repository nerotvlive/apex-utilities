package org.zyneonstudios.apex.utilities.storage;

import org.zyneonstudios.apex.utilities.ApexUtilities;
import org.zyneonstudios.apex.utilities.sql.SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLStorage implements EditableStorage {

    private final SQL sql;
    private final String table;

    public SQLStorage(String tableName, SQL sql) {
        this.sql = sql;
        this.table = tableName;
        sql.execute("CREATE TABLE IF NOT EXISTS "+table+" ( `key` VARCHAR(255) NOT NULL PRIMARY KEY, `value` TEXT );");
    }

    @Override
    public boolean has(String path) {
        return get(path) != null;
    }

    @Override
    public Object get(String path) {
        sql.disconnect();
        sql.connect();
        String query = "SELECT `value` FROM `"+table+"` WHERE `key` = ?";
        try (Connection connection = sql.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, path);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getObject("value");
                }
            }
        } catch (SQLException e) {
            ApexUtilities.getLogger().err("[UTILITIES] (SQLStorage) Couldn't read from database: " + e.getMessage());
        }
        return null;
    }

    @Override
    public String getString(String path) {
        return get(path).toString();
    }

    @Override
    public Integer getInteger(String path) {
        return Integer.parseInt(getString(path));
    }

    @Override
    public Long getLong(String key) {
        return Long.parseLong(getString(key));
    }


    @Override
    public Double getDouble(String path) {
        return Double.parseDouble(getString(path));
    }


    @Override
    public Boolean getBoolean(String path) {
        return getBoolean(path);
    }

    @Override
    public boolean set(String key, Object value) {
        sql.disconnect();
        sql.connect();
        String query = "INSERT INTO `" + table + "` (`key`, `value`) VALUES (?, ?) ON DUPLICATE KEY UPDATE `value` = ?";
        try (Connection connection = sql.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, key);
            statement.setString(2, value.toString());
            statement.setString(3, value.toString());
            statement.execute();
            return true;
        } catch (SQLException e) {
            ApexUtilities.getLogger().err("[UTILITIES] (SQLStorage) Couldn't write to database: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean setString(String key, String value) {
        return set(key,value);
    }

    @Override
    public boolean setDouble(String key, double value) {
        return set(key,value);
    }

    @Override
    public boolean setLong(String key, long value) {
        return set(key,value);
    }

    @Override
    public boolean setInteger(String key, int value) {
        return set(key,value);
    }

    @Override
    public boolean setBoolean(String key, boolean value) {
        return set(key,value);
    }

    @Override
    public boolean remove(String key) {
        sql.disconnect();
        sql.connect();
        String query = "DELETE FROM `"+table+"` WHERE `key` = ?";
        try (Connection connection = sql.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, key);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            ApexUtilities.getLogger().err("[UTILITIES] (SQLStorage) Couldn't delete database entry ("+key+"): " + e.getMessage());
            return false;
        }
    }
}