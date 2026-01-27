package org.zyneonstudios.apex.desktop;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import org.zyneonstudios.apex.desktop.window.ApexWindow;

import javax.swing.*;

public class ApexDesktopUtilities {

    private static final boolean initialized = initDesktop();

    private static boolean initDesktop() {
        try {
            FlatDarkLaf.setup();
            if(System.getProperty("os.name").toLowerCase().contains("mac")) {
                UIManager.setLookAndFeel(new FlatMacDarkLaf());
            } else {
                UIManager.setLookAndFeel(new FlatDarkLaf());
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isInitialized() {
        return initialized;
    }

    static void main() {
        ApexWindow w = new ApexWindow();
        w.setUndecorated(false);
        w.setVisible(true);
    }
}