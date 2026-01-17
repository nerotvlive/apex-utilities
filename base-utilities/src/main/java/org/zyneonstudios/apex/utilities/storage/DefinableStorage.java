package org.zyneonstudios.apex.utilities.storage;

public class DefinableStorage extends ReadableStorage implements EditableStorage {

    private EditableStorage storage;

    public DefinableStorage(EditableStorage storage) {
        super(storage);
        this.storage = storage;
    }

    @Override
    public Storage getStorage() {
        return storage;
    }

    public void setStorage(EditableStorage storage) {
        this.storage = storage;
    }

    @Deprecated
    public void setStorage(Storage storage) throws ClassCastException {
        this.storage = (EditableStorage) storage;
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

    @Override
    public boolean set(String key, Object value) {
        return storage.set(key, value);
    }

    @Override
    public boolean setString(String key, String value) {
        return storage.setString(key, value);
    }

    @Override
    public boolean setDouble(String key, double value) {
        return storage.setDouble(key, value);
    }

    @Override
    public boolean setLong(String key, long value) {
        return storage.setLong(key, value);
    }

    @Override
    public boolean setInteger(String key, int value) {
        return storage.setInteger(key, value);
    }

    @Override
    public boolean setBoolean(String key, boolean value) {
        return storage.setBoolean(key, value);
    }

    @Override
    public boolean remove(String key) {
        return storage.remove(key);
    }
}
