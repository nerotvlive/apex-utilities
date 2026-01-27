package org.zyneonstudios.apex.desktop.window;

import javax.swing.*;
import java.awt.*;

public class ApexWindow extends JFrame {

    public ApexWindow() {
        super("new ApexWindow");
        initialize();
    }

    public ApexWindow(String title) {
        super(title);
        initialize();
    }

    public ApexWindow(String title, int width, int height) {
        super(title);
        setSize(width, height);
        initialize();
    }

    public ApexWindow(String title, int width, int height, boolean resizable) {
        super(title);
        setSize(width, height);
        setResizable(resizable);
        initialize();
    }

    public ApexWindow(String title, int width, int height, boolean resizable, boolean visible) {
        super(title);
        setSize(width, height);
        setResizable(resizable);
        initialize();
        setVisible(visible);
    }

    public ApexWindow(String title, int width, int height, boolean resizable, boolean visible, boolean undecorated) {
        super(title);
        setSize(width, height);
        setResizable(resizable);
        setUndecorated(undecorated);
        initialize();
        setVisible(visible);
    }

    public ApexWindow(String title, int width, int height, boolean resizable, boolean visible, boolean undecorated, boolean alwaysOnTop) {
        super(title);
        setSize(width, height);
        setResizable(resizable);
        setUndecorated(undecorated);
        setAlwaysOnTop(alwaysOnTop);
        initialize();
        setVisible(visible);
    }

    private void initialize() {
        try {

        } catch (Exception e) {
            dispose();
            throw  new RuntimeException("Unable to initialize ApexWindow \""+getTitle()+"\": "+e.getMessage());
        }
    }

    public void setTitlebarBackground(Color color) {
        getRootPane().putClientProperty("JRootPane.titleBarBackground", color);
    }

    public void setTitlebarForeground(Color color) {
        getRootPane().putClientProperty("JRootPane.titleBarForeground", color);
    }

    public void setTitlebarColors(Color background, Color foreground) {
        setTitlebarBackground(background);
        setTitlebarForeground(foreground);
    }

    public void setTitlebar(String title, Color background, Color foreground) {
        setTitle(title);
        setTitlebarColors(background, foreground);
    }

    @Override
    public void setUndecorated(boolean undecorated) {
        super.setUndecorated(undecorated);
        new ApexResizer(this);
        getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
        setTitlebar(getTitle(),Color.BLACK,Color.WHITE);
        getRootPane().setBorder(BorderFactory.createLineBorder(Color.GRAY, 1,true));
    }
}