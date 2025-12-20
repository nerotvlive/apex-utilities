package org.zyneonstudios.apex.utilities.storage;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.zyneonstudios.apex.utilities.json.GsonUtility;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;

public class ReadableJsonStorage implements Storage {

    private final JsonObject root;

    @SuppressWarnings("all")
    public ReadableJsonStorage(String json) {
        if(json.toLowerCase().startsWith("http://") || json.toLowerCase().startsWith("https://")) {
            json = GsonUtility.getFromURL(json);
        }
        root = new Gson().fromJson(json, JsonObject.class);
    }

    public ReadableJsonStorage(URL url) {
        root = new Gson().fromJson(GsonUtility.getFromURL(url.toString()), JsonObject.class);
    }

    public ReadableJsonStorage(File file) {
        root = new Gson().fromJson(GsonUtility.getFromFile(file), JsonObject.class);
    }

    public ReadableJsonStorage(Path path) {
        root = new Gson().fromJson(GsonUtility.getFromFile(path.toFile()), JsonObject.class);
    }
                               @Override
    public Object get(String path) {
        return root.get(path);
    }

    @Override
    public boolean has(String path) {
        return root.has(path);
    }

    @Override
    public String getString(String path) {
        return root.get(path).getAsString();
    }

    @Override
    public Integer getInteger(String path) {
        return root.get(path).getAsInt();
    }

    @Override
    public int getInt(String path) {
        return root.get(path).getAsInt();
    }

    @Override
    public Double getDouble(String path) {
        return root.get(path).getAsDouble();
    }

    @Override
    public double getDoub(String path) {
        return root.get(path).getAsDouble();
    }

    @Override
    public Boolean getBoolean(String path) {
        return root.get(path).getAsBoolean();
    }

    @Override
    public boolean getBool(String path) {
        return root.get(path).getAsBoolean();
    }

    public JsonObject getJson() {
        return root;
    }
}
