package org.zyneonstudios.apex.utilities.json;

import com.alibaba.fastjson2.JSONObject;
import org.zyneonstudios.apex.utilities.ApexUtilities;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonFile implements EditableJsonData {

    private JSONObject data;
    private final File file;

    public JsonFile(String path) {
        this(new File(path));
    }

    public JsonFile(Path path) {
        this(path.toFile());
    }

    public JsonFile(File file) {
        this.file = file;
        String error = "Invalid JSON File!";
        if(validate()) {
            try {
                reload();
                return;
            } catch (Exception e) { error = e.getMessage(); }
        }
        throw new IllegalStateException(error);
    }

    public File getFile() {
        return file;
    }

    public String getPath() {
        return file.getPath();
    }

    public String getAbsolutePath() {
        return file.getAbsolutePath();
    }

    public String getFileName() {
        return file.getName();
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
    public Boolean getBoolean(String key) {
        return data.getBoolean(key);
    }

    @Override
    public Double getDouble(String key) {
        return data.getDouble(key);
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
    public String getString(String key) {
        return data.getString(key);
    }

    @Override
    public boolean set(String key, Object value) {
        try {
            data.put(key, value);
            Files.write(file.toPath(), data.toJSONString().getBytes());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean setBoolean(String key, boolean value) {
        return set(key, value);
    }

    @Override
    public boolean setDouble(String key, double value) {
        return set(key, value);
    }

    @Override
    public boolean setInteger(String key, int value) {
        return set(key, value);
    }

    @Override
    public boolean setLong(String key, long value) {
        return set(key, value);
    }

    @Override
    public boolean setString(String key, String value) {
        return set(key, value);
    }

    @Override
    public boolean remove(String key) {
        try {
            data.remove(key);
            Files.write(file.toPath(), data.toJSONString().getBytes());
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void clear() {
        data.clear();
        try {
            Files.write(file.toPath(), data.toJSONString().getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean reload() {
        try {
            data = JsonUtility.getObjectFromFile(file);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validate() {
        ApexUtilities.getLogger().deb("Validating JsonFile "+file.getAbsolutePath()+"...");
        if(!file.exists()) {
            ApexUtilities.getLogger().deb("JsonFile doesn't exist.");
            ApexUtilities.getLogger().deb("Created JsonFile path: "+file.getParentFile().mkdirs());
            try {
                ApexUtilities.getLogger().deb("Created JsonFile: " + file.createNewFile());
                Files.write(file.toPath(), "{}".getBytes());
                ApexUtilities.getLogger().deb("JsonFile created.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            ApexUtilities.getLogger().deb("JsonFile does exist.");
        }
        ApexUtilities.getLogger().deb("JsonFile "+file.getAbsolutePath()+" valid: "+file.exists());
        return file.exists();
    }
}