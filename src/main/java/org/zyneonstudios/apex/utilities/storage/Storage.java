package org.zyneonstudios.apex.utilities.storage;

public interface Storage {

    boolean has(String key);
    Object get(String key);
    String getString(String key);
    Integer getInteger(String key);
    Long getLong(String key);
    Double getDouble(String key);
    Boolean getBoolean(String key);
}