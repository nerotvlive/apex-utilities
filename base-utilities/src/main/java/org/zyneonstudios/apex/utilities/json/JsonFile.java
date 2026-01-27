package org.zyneonstudios.apex.utilities.json;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import org.zyneonstudios.apex.utilities.ApexUtilities;
import org.zyneonstudios.apex.utilities.storage.JsonStorage;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class JsonFile extends JsonStorage {

    private JSONObject data;
    private final File file;
    private boolean prettyPrint = false;
    private static final String LOCK_SUFFIX = ".lock";
    private static final String TEMP_SUFFIX = ".tmp";

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
                String content = readFileContents();
                this.prettyPrint = detectPrettyPrint(content);
                this.data = parseContent(content);
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
        PathResolution resolution = resolvePath(key, false);
        return resolution != null && resolution.parent.containsKey(resolution.key);
    }

    @Override
    public Object get(String key) {
        PathResolution resolution = resolvePath(key, false);
        return resolution == null ? null : resolution.parent.get(resolution.key);
    }

    @Override
    public Boolean getBoolean(String key) {
        PathResolution resolution = resolvePath(key, false);
        return resolution == null ? null : resolution.parent.getBoolean(resolution.key);
    }

    @Override
    public Double getDouble(String key) {
        PathResolution resolution = resolvePath(key, false);
        return resolution == null ? null : resolution.parent.getDouble(resolution.key);
    }

    @Override
    public Integer getInteger(String key) {
        PathResolution resolution = resolvePath(key, false);
        return resolution == null ? null : resolution.parent.getInteger(resolution.key);
    }

    @Override
    public Long getLong(String key) {
        PathResolution resolution = resolvePath(key, false);
        return resolution == null ? null : resolution.parent.getLong(resolution.key);
    }

    @Override
    public String getString(String key) {
        PathResolution resolution = resolvePath(key, false);
        return resolution == null ? null : resolution.parent.getString(resolution.key);
    }

    @Override
    public synchronized boolean set(String key, Object value) {
        try {
            PathResolution resolution = resolvePath(key, true);
            if (resolution == null) {
                return false;
            }
            resolution.parent.put(resolution.key, value);
            persist();
            return true;
        } catch (Exception e) {
            tryReloadAfterFailure();
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
    public synchronized boolean remove(String key) {
        try {
            PathResolution resolution = resolvePath(key, false);
            if (resolution == null) {
                return false;
            }
            resolution.parent.remove(resolution.key);
            persist();
            return true;
        } catch (Exception e) {
            tryReloadAfterFailure();
            return false;
        }
    }

    @Override
    public synchronized void clear() {
        data.clear();
        try {
            persist();
        } catch (Exception e) {
            tryReloadAfterFailure();
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized boolean reload() {
        try {
            String content = readFileContents();
            this.prettyPrint = detectPrettyPrint(content);
            data = parseContent(content);
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
                Files.write(file.toPath(), "{}".getBytes(StandardCharsets.UTF_8));
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

    public boolean isPrettyPrint() {
        return prettyPrint;
    }

    public synchronized void setPrettyPrint(boolean prettyPrint) {
        this.prettyPrint = prettyPrint;
        try {
            persist();
        } catch (Exception e) {
            tryReloadAfterFailure();
            throw new RuntimeException(e);
        }
    }

    private synchronized void persist() throws IOException {
        Path targetPath = file.toPath();
        Path parent = targetPath.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        Path lockPath = targetPath.resolveSibling(targetPath.getFileName().toString() + LOCK_SUFFIX);
        Path tempPath = targetPath.resolveSibling(targetPath.getFileName().toString() + TEMP_SUFFIX);
        byte[] bytes = serialize();
        try (FileChannel lockChannel = FileChannel.open(lockPath, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
             FileLock lock = lockChannel.lock()) {
            try (FileChannel tempChannel = FileChannel.open(tempPath, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
                tempChannel.write(ByteBuffer.wrap(bytes));
                tempChannel.force(true);
            }
            try {
                Files.move(tempPath, targetPath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException e) {
                Files.move(tempPath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    private byte[] serialize() {
        String json = prettyPrint
                ? JSON.toJSONString(data, JSONWriter.Feature.PrettyFormat)
                : data.toJSONString();
        return json.getBytes(StandardCharsets.UTF_8);
    }

    private JSONObject parseContent(String content) {
        String trimmed = content == null ? "" : content.trim();
        if (trimmed.isEmpty()) {
            return new JSONObject();
        }
        return JSONObject.parseObject(trimmed);
    }

    private boolean detectPrettyPrint(String content) {
        if (content == null) {
            return false;
        }
        return content.contains("\n") || content.contains("\r");
    }

    private String readFileContents() throws IOException {
        Path targetPath = file.toPath();
        if (!Files.exists(targetPath)) {
            return "{}";
        }
        Path lockPath = targetPath.resolveSibling(targetPath.getFileName().toString() + LOCK_SUFFIX);
        try (FileChannel lockChannel = FileChannel.open(lockPath, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
             FileLock lock = lockChannel.lock()) {
            return Files.readString(targetPath, StandardCharsets.UTF_8);
        }
    }

    private void tryReloadAfterFailure() {
        try {
            reload();
        } catch (Exception ignored) {
        }
    }

    private PathResolution resolvePath(String key, boolean createMissing) {
        if (key == null || key.isEmpty()) {
            return null;
        }
        List<String> parts = splitPath(key);
        if (parts.isEmpty()) {
            return null;
        }
        JSONObject current = data;
        for (int i = 0; i < parts.size() - 1; i++) {
            String part = parts.get(i);
            Object next = current.get(part);
            if (next instanceof JSONObject) {
                current = (JSONObject) next;
                continue;
            }
            if (!createMissing) {
                return null;
            }
            JSONObject created = new JSONObject();
            current.put(part, created);
            current = created;
        }
        return new PathResolution(current, parts.get(parts.size() - 1));
    }

    private List<String> splitPath(String key) {
        List<String> parts = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        for (int i = 0; i < key.length(); i++) {
            char ch = key.charAt(i);
            if (ch == '\\') {
                if (i + 1 < key.length()) {
                    char next = key.charAt(i + 1);
                    if (next == '.' || next == '\\') {
                        current.append(next);
                        i++;
                        continue;
                    }
                }
                current.append(ch);
                continue;
            }
            if (ch == '.') {
                parts.add(current.toString());
                current.setLength(0);
                continue;
            }
            current.append(ch);
        }
        parts.add(current.toString());
        return parts;
    }

    private static class PathResolution {
        private final JSONObject parent;
        private final String key;

        private PathResolution(JSONObject parent, String key) {
            this.parent = parent;
            this.key = key;
        }
    }
}
