package org.zyneonstudios.apex.utilities.storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EditableStorageGroup extends StorageGroup implements EditableStorage {

    private final ArrayList<EditableStorage> storages;

    public EditableStorageGroup(Collection<EditableStorage> storages) {
        this.storages = new ArrayList<>();
        this.storages.addAll(storages);
    }

    public EditableStorageGroup(EditableStorage... storages) {
        this.storages = new ArrayList<>();
        this.storages.addAll(List.of(storages));
    }

    public EditableStorageGroup(EditableStorage storage) {
        this.storages = new ArrayList<>();
        this.storages.add(storage);
    }

    public EditableStorageGroup() {
        this.storages = new ArrayList<>();
    }

    @Override
    public EditableStorage[] getStorages() {
        return storages.toArray(new EditableStorage[0]);
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

    @Deprecated @Override
    public void addStorage(Storage storage) throws ClassCastException {
        storages.add((EditableStorage) storage);
    }

    @Deprecated @Override
    public void addStorages(Storage... storages) throws ClassCastException {
        this.storages.addAll(List.of((EditableStorage[]) storages));
    }

    public void removeStorage(Storage storage) {
        storages.remove((EditableStorage)storage);
    }

    public void addStorage(EditableStorage storage) {
        storages.add(storage);
    }

    public void addStorages(EditableStorage... storages) {
        this.storages.addAll(List.of(storages));
    }

    public void addStorages(Collection<Storage> storages) {
        for(Storage storage : storages) {
            this.storages.add((EditableStorage) storage);
        }
    }

    public void removeStorage(EditableStorage storage) {
        storages.remove(storage);
    }

    public void removeStorage(int index) {
        storages.remove(index);
    }

    public void clear() {
        storages.clear();
    }

    @Override
    public boolean set(String key, Object value) {
        for(EditableStorage storage : storages) {
            if(storage.has(key)) {
                return storage.set(key, value);
            }
        }
        if(!storages.isEmpty()) {
            storages.getFirst().set(key, value);
            return true;
        }
        return false;
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
    public boolean ensure(String key, Object value) {
        if(has(key)) {
            return true;
        }
        return set(key, value);
    }

    @Override
    public boolean remove(String key) {
        boolean removed = false;
        for(EditableStorage storage : storages) {
            if(storage.has(key)) {
                removed = true;
                return storage.remove(key);
            }
        }
        return removed;
    }
}