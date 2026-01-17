package org.zyneonstudios.apex.utilities.misc;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;

public final class StringUtility {

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final String ALPHABETIC = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMERIC = "0123456789";
    private static final String ALPHANUMERIC = ALPHABETIC + NUMERIC;
    private static final String SYMBOLS = "!@#$%^&*()-=_+,./<>?;':\"\\{}|";
    private static final String ALL_CHARS = ALPHANUMERIC + SYMBOLS;

    private StringUtility() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static String getURLString(String input) {
        if (input == null) return null;
        String normalized = input.replace("\\", "/");
        if (normalized.startsWith("file://")) {
            return normalized;
        }
        return "file://" + normalized;
    }

    public static String getURLString(Path path) {
        return path == null ? null : getURLString(path.toString());
    }

    public static String getURLString(File file) {
        return file == null ? null : getURLString(file.getAbsolutePath());
    }

    public static String getUUIDString(String input) {
        if (input == null || input.length() != 32) {
            throw new IllegalArgumentException("Invalid UUID string length. Expected 32 hex characters.");
        }
        StringBuilder sb = new StringBuilder(36);
        sb.append(input, 0, 8)
                .append('-')
                .append(input, 8, 12)
                .append('-')
                .append(input, 12, 16)
                .append('-')
                .append(input, 16, 20)
                .append('-')
                .append(input, 20, 32);
        return sb.toString();
    }

    public static String toBase64(String input) {
        if (input == null) return null;
        return Base64.getEncoder().encodeToString(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String fromBase64(String input) {
        if (input == null) return null;
        return new String(Base64.getDecoder().decode(input), StandardCharsets.UTF_8);
    }

    public static String generateString(int length) {
        return generateFromPool(length, ALL_CHARS);
    }

    public static String generateAlphabeticString(int length) {
        return generateFromPool(length, ALPHABETIC);
    }

    public static String generateAlphanumericString(int length) {
        return generateFromPool(length, ALPHANUMERIC);
    }

    public static String generateNumericString(int length) {
        return generateFromPool(length, NUMERIC);
    }

    public static String generateUnrestrictedString(int length) {
        if (length <= 0) return "";
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            stringBuilder.append((char) secureRandom.nextInt(Character.MAX_VALUE));
        }
        return stringBuilder.toString();
    }

    public static String generateFromPool(int length, String pool) {
        if (length <= 0) return "";
        if (pool == null || pool.isEmpty()) throw new IllegalArgumentException("Pool cannot be empty");

        StringBuilder sb = new StringBuilder(length);
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int poolSize = pool.length();

        for (int i = 0; i < length; i++) {
            sb.append(pool.charAt(random.nextInt(poolSize)));
        }
        return sb.toString();
    }

    public static String generate(int length, String regex) {
        switch (regex) {
            case "[a-zA-Z0-9!@#$%^&*()-=_+,./<>?;':\"\\{}|]": return generateFromPool(length, ALL_CHARS);
            case "[a-zA-Z]": return generateFromPool(length, ALPHABETIC);
            case "[a-zA-Z0-9]": return generateFromPool(length, ALPHANUMERIC);
            case "[0-9]": return generateFromPool(length, NUMERIC);
        }

        StringBuilder stringBuilder = new StringBuilder(length);
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int attempts = 0;
        int maxAttempts = length * 1000;

        for (int i = 0; i < length; i++) {
            char randomChar;
            do {
                randomChar = (char) random.nextInt(128);
                attempts++;
                if (attempts > maxAttempts) throw new IllegalStateException("Could not generate string matching regex within reasonable attempts");
            } while (!String.valueOf(randomChar).matches(regex));
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
    }
}