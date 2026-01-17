package org.zyneonstudios.apex.utilities.misc;

import java.util.Locale;

public final class OSUtility {

    private static final String OS_NAME = System.getProperty("os.name");
    private static final String OS_VERSION = System.getProperty("os.version");
    private static final String OS_ARCH = System.getProperty("os.arch");

    private static final Type CURRENT_OS_TYPE = determineType();
    private static final Architecture CURRENT_ARCHITECTURE = determineArchitecture();

    private OSUtility() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Type getType() {
        return CURRENT_OS_TYPE;
    }

    public static Architecture getArchitecture() {
        return CURRENT_ARCHITECTURE;
    }

    public static String getName() {
        return OS_NAME;
    }

    public static String getVersion() {
        return OS_VERSION;
    }

    public static boolean isWindows() {
        return CURRENT_OS_TYPE == Type.Windows;
    }

    public static boolean isMac() {
        return CURRENT_OS_TYPE == Type.macOS;
    }

    public static boolean isLinux() {
        return CURRENT_OS_TYPE == Type.Linux;
    }

    public static boolean is64Bit() {
        return CURRENT_ARCHITECTURE == Architecture.x64 || CURRENT_ARCHITECTURE == Architecture.arm64;
    }

    private static Type determineType() {
        if (OS_NAME == null) return Type.unknown;
        String os = OS_NAME.toLowerCase(Locale.ROOT);
        if (os.contains("win")) {
            return Type.Windows;
        } else if (os.contains("mac")) {
            return Type.macOS;
        } else if (os.contains("linux") || os.contains("nix") || os.contains("nux")) {
            return Type.Linux;
        }
        return Type.unknown;
    }

    private static Architecture determineArchitecture() {
        if (OS_ARCH == null) return Architecture.x86;
        String arch = OS_ARCH.toLowerCase(Locale.ROOT);
        String dataModel = System.getProperty("sun.arch.data.model");

        boolean is64Bit = "64".equals(dataModel) || arch.contains("64");

        if (arch.contains("arm") || arch.contains("aarch")) {
            return is64Bit ? Architecture.arm64 : Architecture.arm32;
        }
        return is64Bit ? Architecture.x64 : Architecture.x86;
    }

    public enum Type {
        Linux,
        macOS,
        Windows,
        unknown
    }

    public enum Architecture {
        arm32,
        arm64,
        x64,
        x86
    }
}
