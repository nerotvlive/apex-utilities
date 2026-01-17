package org.zyneonstudios.apex.utilities.storage;

import com.alibaba.fastjson2.JSONObject;
import org.zyneonstudios.apex.utilities.json.EditableJsonData;

public class JsonStorage extends LocalStorage implements EditableJsonData {

    private final JSONObject data = new JSONObject();

    @Override
    public boolean set(String key, Object value) {
        data.put(key, value);
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
        if(data.containsKey(key)) {
            data.remove(key);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean has(String key) {
        return data.containsKey(key);
    }

    @Override
    public Object get(String key) {
        return data.get(key);
    }

    @Override
    public String getString(String key) {
        return get(key).toString();
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

    @Override
    public void clear() {
        data.clear();
    }

    @Override
    public JSONObject getData() {
        return data;
    }

    @Override
    public boolean ensure(String key, Object value) {
        if(has(key)) {
            return true;
        }
        return set(key, value);
    }

    @Override @Deprecated
    public boolean reload() {
        return false;
    }
}