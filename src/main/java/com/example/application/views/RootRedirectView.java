package com.example.application.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

import java.util.Optional;

@Route("/redirect")
@PermitAll


public class RootRedirectView extends VerticalLayout {
    public RootRedirectView() {

        UI.getCurrent().navigate("http://localhost:8080/1/edit"); // <-- redirect somewhere safe

    }
}