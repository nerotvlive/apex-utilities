package org.zyneonstudios.apex.utilities;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import org.zyneonstudios.apex.utilities.logger.ApexLogger;
import org.zyneonstudios.apex.utilities.system.OperatingSystem;

import javax.swing.*;

public class ApexUtilities {

    private static final ApexLogger logger = new ApexLogger("APEX");

    public static boolean init() {
        logger.dbg("Initializing...");
        try {
            FlatDarkLaf.setup();
            if(OperatingSystem.getType().equals(OperatingSystem.Type.macOS)) {
                UIManager.setLookAndFeel(new FlatMacDarkLaf());
            } else {
                UIManager.setLookAndFeel(new FlatDarkLaf());
            }
            logger.dbg("Initialed!");
            return true;
        } catch (Exception e) {
            logger.err("Failed to initialize!");
            logger.err(e.getMessage());
            return false;
        }
    }

    public static ApexLogger getLogger() {
        return logger;
    }
}