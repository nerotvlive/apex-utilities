package org.zyneonstudios.apex.utilities.frame;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.ptr.IntByReference;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ApexFrame extends JFrame {

    private final Color transparent = new Color(0,0,0,1);

    private int x = 0;
    private int y = 0;

    JPanel mainPanel = new JPanel();

    public ApexFrame() {
        init(true);
    }

    public ApexFrame(String title) {
        super(title);
        init(true);
    }

    public ApexFrame(boolean systemStyle) {
        init(!systemStyle);
    }

    public ApexFrame(String title, boolean systemStyle) {
        super(title);
        init(!systemStyle);
    }

    private void init(boolean customTitlebar) {
        if(customTitlebar) {
            setUndecorated(true);
            new ApexResizer(this);
            setupTitlebar();

            getRootPane().setBorder(BorderFactory.createLineBorder(Color.GRAY, 1,true));
        }

        mainPanel.setOpaque(false);
        mainPanel.setBackground(transparent);
        getRootPane().setOpaque(false);
        getRootPane().setBackground(transparent);
        Dimension defaultSize = new Dimension(1280, 720);
        setSize(defaultSize);
        setLocationRelativeTo(null);
    }

    private void setupTitlebar() {
        getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
        setTitlebar(getTitle(),Color.BLACK,Color.WHITE, (Image) null);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JFrame getAsJFrame() {
        return this;
    }

    public void setTitleColors(Color background, Color foreground) {
        getRootPane().putClientProperty("JRootPane.titleBarBackground", background);
        getRootPane().putClientProperty("JRootPane.titleBarForeground", foreground);
    }

    public void setTitlebar(String title, Color background, Color foreground, Image icon) {
        setTitleColors(background,foreground);
        setTitle(title);
        setIconImage(icon);
    }

    public void setTitlebar(String title, Color background, Color foreground, List<Image> icons) {
        setTitleColors(background,foreground);
        setTitle(title);
        setIconImages(icons);
    }

    public void applyMica() {
        if (System.getProperty("os.name").toLowerCase().contains("windows 11")) {
            setBackground(transparent);
            setTitleColors(transparent, Color.LIGHT_GRAY);
            WinDef.HWND hwnd = new WinDef.HWND(Native.getWindowPointer(this));
            DwmApi.INSTANCE.DwmSetWindowAttribute(hwnd, 20, new IntByReference(1), 4);
            DwmApi.INSTANCE.DwmSetWindowAttribute(hwnd, 38, new IntByReference(2), 4);
            this.repaint();
        }
    }
}