package org.zyneonstudios.apex.utilities.json;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

public class JsonUtility {

    private static final HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(30)).followRedirects(HttpClient.Redirect.NORMAL).build();

    public static Object get(String path_or_url) {
        if (path_or_url.startsWith("http://") || path_or_url.startsWith("https://") || path_or_url.startsWith("file://")) {
            return getFromUrl(path_or_url);
        } else {
            return getFromFile(path_or_url);
        }
    }

    public static JSONObject getObject(String path_or_url) {
        if (path_or_url.startsWith("http://") || path_or_url.startsWith("https://") || path_or_url.startsWith("file://")) {
            return getObjectFromUrl(path_or_url);
        } else {
            return getObjectFromFile(path_or_url);
        }
    }

    public static JSONArray getArray(String path_or_url) {
        if (path_or_url.startsWith("http://") || path_or_url.startsWith("https://") || path_or_url.startsWith("file://")) {
            return getArrayFromUrl(path_or_url);
        } else {
            return getArrayFromFile(path_or_url);
        }
    }

    public static Object getFromFile(String path) {
        return getFromFile(new File(path));
    }

    public static Object getFromFile(Path path) {
        return getFromFile(path.toFile());
    }

    public static Object getFromFile(File file) {
        try {
            return JSON.parse(Files.readString(file.toPath()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getFromUrl(URL url) {
        return getFromUrl(url.toString());
    }

    public static Object getFromUrl(URI uri) {
        return getFromUrl(uri.toString());
    }

    public static Object getFromUrl(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("Accept", "application/json").timeout(Duration.ofMinutes(2)).GET().build();
            HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());
            if (response.statusCode() != 200) {
                throw new RuntimeException("HTTP Fehler: " + response.statusCode());
            }
            return JSON.parse(response.body());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONObject getObjectFromFile(String path) {
        return getObjectFromFile(new File(path));
    }

    public static JSONObject getObjectFromFile(Path path) {
        return getObjectFromFile(path.toFile());
    }

    public static JSONObject getObjectFromFile(File file) {
        try {
            return JSONObject.parseObject(Files.readString(file.toPath()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONObject getObjectFromUrl(URL url) {
        return getObjectFromUrl(url.toString());
    }

    public static JSONObject getObjectFromUrl(URI uri) {
        return getObjectFromUrl(uri.toString());
    }

    public static JSONObject getObjectFromUrl(String url) {
        try {
            return JSON.parseObject(URI.create(url).toURL());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONArray getArrayFromFile(String path) {
        return getArrayFromFile(new File(path));
    }

    public static JSONArray getArrayFromFile(Path path) {
        return getArrayFromFile(path.toFile());
    }

    public static JSONArray getArrayFromFile(File file) {
        try {
            return JSONArray.parseArray(Files.readString(file.toPath()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONArray getArrayFromUrl(URL url) {
        return getArrayFromUrl(url.toString());
    }

    public static JSONArray getArrayFromUrl(URI uri) {
        return getArrayFromUrl(uri.toString());
    }

    public static JSONArray getArrayFromUrl(String url) {
        try {
            return JSON.parseArray(URI.create(url).toURL());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}