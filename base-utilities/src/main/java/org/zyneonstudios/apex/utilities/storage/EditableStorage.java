package org.zyneonstudios.apex.utilities.storage;

public interface EditableStorage extends Storage {

    boolean set(String key, Object value);
    boolean setString(String key, String value);
    boolean setDouble(String key, double value);
    boolean setLong(String key, long value);
    boolean setInteger(String key, int value);
    boolean setBoolean(String key, boolean value);
    default boolean ensure(String key, Object value) {
        if(has(key)) {
            return true;
        } else {
            return set(key, value);
        }
    }
    boolean remove(String key);

}