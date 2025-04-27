package com.example.application.data; // use your real base package

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;

@Theme("mytheme") // <-- Move your @Theme here
@PWA(name = "My App", shortName = "App") // Optional, but nice for PWA support
public class AppShell implements AppShellConfigurator {
}