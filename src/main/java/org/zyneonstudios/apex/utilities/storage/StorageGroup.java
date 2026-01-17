package org.zyneonstudios.apex.utilities.storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StorageGroup implements Storage {

    private final ArrayList<Storage> storages;

    public StorageGroup(Collection<Storage> storages) {
        this.storages = new ArrayList<>();
        this.storages.addAll(storages);
    }

    public StorageGroup(Storage... storages) {
        this.storages = new ArrayList<>();
        this.storages.addAll(List.of(storages));
    }

    public StorageGroup(Storage storage) {
        this.storages = new ArrayList<>();
        this.storages.add(storage);
    }

    public StorageGroup() {
        this.storages = new ArrayList<>();
    }

    public Storage[] getStorages() {
        return storages.toArray(new Storage[0]);
    }

    @Override
    public boolean has(String key) {
        for(Storage storage : storages) {
            if(storage.has(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object get(String key) {
        for(Storage storage : storages) {
            if(storage.has(key)) {
                return storage.get(key);
            }
        }
        return null;
    }

    @Override
    public String getString(String key) {
        return get(key).toString();
    }

    @Override
    public Integer getInteger(String key) {
        return  Integer.parseInt(getString(key));
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

    public void addStorage(Storage storage) {
        storages.add(storage);
    }

    public void addStorages(Storage... storages) {
        this.storages.addAll(List.of(storages));
    }

    public void addStorages(Collection<Storage> storages) {
        this.storages.addAll(storages);
    }

    public void removeStorage(Storage storage) {
        storages.remove(storage);
    }

    public void removeStorage(int index) {
        storages.remove(index);
    }

    public void clear() {
        storages.clear();
    }
}