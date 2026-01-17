package org.zyneonstudios.apex.utilities.storage;

public class ReadableStorage implements Storage {

    private Storage storage;

    public ReadableStorage(Storage storage) {
        this.storage = storage;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    @Override
    public boolean has(String key) {
        return storage.has(key);
    }

    @Override
    public Object get(String key) {
        return storage.get(key);
    }

    @Override
    public String getString(String key) {
        return storage.getString(key);
    }

    @Override
    public Integer getInteger(String key) {
        return storage.getInteger(key);
    }

    @Override
    public Long getLong(String key) {
        return storage.getLong(key);
    }

    @Override
    public Double getDouble(String key) {
        return storage.getDouble(key);
    }

    @Override
    public Boolean getBoolean(String key) {
        return storage.getBoolean(key);
    }
}