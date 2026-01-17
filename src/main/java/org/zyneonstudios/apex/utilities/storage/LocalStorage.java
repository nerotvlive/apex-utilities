package org.zyneonstudios.apex.utilities.storage;

import java.util.HashMap;

public class LocalStorage implements EditableStorage {

    private final HashMap<String,Object> storage = new HashMap<>();

    @Override
    public boolean set(String key, Object value) {
        storage.put(key, value);
        return true;
    }

    @Override
    public boolean setString(String key, String value) {
        return set(key, value);
    }

    @Override
    public boolean setDouble(String key, double value) {
        return set(key, value);
    }

    @Override
    public boolean setLong(String key, long value) {
        return set(key, value);
    }

    @Override
    public boolean setInteger(String key, int value) {
        return set(key, value);
    }

    @Override
    public boolean setBoolean(String key, boolean value) {
        return set(key, value);
    }

    @Override
    public boolean remove(String key) {
        if(storage.containsKey(key)) {
            storage.remove(key);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean has(String key) {
        return storage.containsKey(key);
    }

    @Override
    public Object get(String key) {
        return storage.get(key);
    }

    @Override
    public String getString(String key) {
        return storage.get(key).toString();
    }

    @Override
    public Integer getInteger(String key) {
        return Integer.parseInt(getString(key));
    }

    @Override
    public Long getLong(String key) {
        return Long.parseLong(getString(key));
    }

    @Override
    public Double getDouble(String key) {
        return Double.parseDouble(getString(key));
    }

    @Override
    public Boolean getBoolean(String key) {
        return Boolean.parseBoolean(getString(key));
    }
}