package org.zyneonstudios.apex.utilities;

import org.zyneonstudios.apex.utilities.logger.NexusLogger;
import org.zyneonstudios.apex.utilities.strings.StringGenerator;

public class NexusUtilities {

    private final static NexusLogger logger = new NexusLogger("NEX"+ StringGenerator.generateAlphanumericString(3));

    public static NexusLogger getLogger() {
        return logger;
    }
}