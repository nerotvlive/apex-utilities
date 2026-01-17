package org.zyneonstudios.apex.utilities.logger;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ApexLogger {

    private static final String RESET = "\u001B[0m";
    private static final String BLUE_BRIGHT = "\u001B[94m";
    private static final String RED = "\u001B[31m";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final String loggerName;
    private boolean debug = false;

    public ApexLogger(String loggerName) {
        this.loggerName = loggerName;
    }

    public void enableDebugging(boolean debug) {
        this.debug = debug;
    }

    public void raw(String message) {
        System.out.println(message);
    }

    public void log(String message) {
        raw("[%s] [%s|LOG] %s".formatted(getTimestamp(), loggerName, message));
    }

    public void deb(String message) {
        if (debug) {
            raw("\u001B[94m[%s] [%s|DEB] %s\u001B[0m".formatted(getTimestamp(), loggerName, message));
        }
    }

    public void err(String message) {
        System.err.printf("\u001B[31m[%s] [%s|ERR] %s\u001B[0m%n", getTimestamp(), loggerName, message);
    }

    public String getLoggerName() {
        return loggerName;
    }

    private String getTimestamp() {
        return LocalTime.now().format(TIME_FORMATTER);
    }
}