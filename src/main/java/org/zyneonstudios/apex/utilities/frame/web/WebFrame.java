package org.zyneonstudios.apex.utilities.frame.web;

import org.zyneonstudios.apex.utilities.events.AsyncWebFrameConnectorEvent;
import org.zyneonstudios.apex.utilities.events.WebFrameConnectorEvent;
import org.cef.browser.CefBrowser;

import javax.swing.*;

public interface WebFrame {

    AsyncWebFrameConnectorEvent getAsyncWebFrameConnectorEvent();

    WebFrameConnectorEvent getWebFrameConnectorEvent();

    void setAsyncWebFrameConnectorEvent(AsyncWebFrameConnectorEvent asyncWebFrameConnectorEvent);

    void setWebFrameConnectorEvent(WebFrameConnectorEvent webFrameConnectorEvent);

    CefBrowser getBrowser();

    boolean isBrowserFocussed();

    void executeJavaScript(String... scripts);

    JFrame getAsJFrame();
}