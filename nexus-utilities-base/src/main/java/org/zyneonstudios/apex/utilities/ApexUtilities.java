package org.zyneonstudios.apex.utilities;

import org.zyneonstudios.apex.utilities.logger.ApexLogger;

public class ApexUtilities {

    private static ApexLogger logger = new ApexLogger("APX");

    public static void init() {

    }

    public static ApexLogger getLogger() {
        return logger;
    }

    public static void setLogger(ApexLogger logger) {
        ApexUtilities.logger = logger;
    }
}