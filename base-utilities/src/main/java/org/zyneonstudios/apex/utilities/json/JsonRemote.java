package org.zyneonstudios.apex.utilities.json;

import com.alibaba.fastjson2.JSONObject;

import java.io.File;
import java.net.URI;
import java.net.URL;

public class JsonRemote implements JsonData {

    private final String url;
    private JSONObject data;

    public JsonRemote(String data_or_url) {
        if(data_or_url.startsWith("http:/") || data_or_url.startsWith("https:/") || data_or_url.startsWith("file:/")) {
            this.url = data_or_url;
            this.data = JsonUtility.getObjectFromUrl(url);
        } else {
            this.url = null;
            this.data = JSONObject.parseObject(data_or_url);
        }
    }

    public JsonRemote(JSONObject data) {
        this.url = null;
        this.data = data;
    }

    public JsonRemote(JsonData data) {
        this(data.getData());
    }

    public JsonRemote(URI uri) {
        this(uri.toString());
    }

    public JsonRemote(URL url) {
        this(url.toString());
    }

    public JsonRemote(File file) {
        this("file://"+file.getAbsolutePath().replace("\\","/").replace("0","%20"));
    }

    public JsonRemote(JsonFile jsonFile) {
        this(jsonFile.getFile());
    }

    @Override
    public JSONObject getData() {
        return data;
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
        return data.getString(key);
    }

    @Override
    public Integer getInteger(String key) {
        return data.getInteger(key);
    }

    @Override
    public Long getLong(String key) {
        return data.getLong(key);
    }

    @Override
    public Double getDouble(String key) {
        return data.getDouble(key);
    }

    @Override
    public Boolean getBoolean(String key) {
        return data.getBoolean(key);
    }

    @Override
    public boolean reload() {
        try {
            this.data = JsonUtility.getObjectFromUrl(url);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}