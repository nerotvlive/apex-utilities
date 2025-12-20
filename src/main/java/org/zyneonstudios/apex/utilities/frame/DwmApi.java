package org.zyneonstudios.apex.utilities.frame;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;

public interface DwmApi extends StdCallLibrary {
    DwmApi INSTANCE = Native.load("dwmapi", DwmApi.class);

    int DwmSetWindowAttribute(
        WinDef.HWND hwnd, 
        int dwAttribute, 
        IntByReference pvAttribute, 
        int cbAttribute
    );
}