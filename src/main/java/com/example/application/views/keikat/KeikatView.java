package com.example.application.views.keikat;

import com.example.application.data.Keikat;
import com.example.application.services.KeikatService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import java.time.Duration;
import java.util.Optional;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Keikat")
@Route("/:keikatID?/:action?(edit)")
@Menu(order = 0, icon = LineAwesomeIconUrl.MUSIC_SOLID)
@RouteAlias("")
public class KeikatView extends Div implements BeforeEnterObserver {

    private final String KEIKAT_ID = "keikatID";
    private final String KEIKAT_EDIT_ROUTE_TEMPLATE = "/%s/edit";

    private final Grid<Keikat> grid = new Grid<>(Keikat.class, false);

    private TextField artisti;
    private TextField sijainti;
    private TextField hinta;
    private DateTimePicker ajankohta;

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

    private final BeanValidationBinder<Keikat> binder;

    private Keikat keikat;

    private final KeikatService keikatService;

    public KeikatView(KeikatService keikatService) {
        this.keikatService = keikatService;
        addClassNames("keikat-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("artisti").setAutoWidth(true);
        grid.addColumn("sijainti").setAutoWidth(true);
        grid.addColumn("hinta").setAutoWidth(true);
        grid.addColumn("ajankohta").setAutoWidth(true);
        grid.setItems(query -> keikatService.list(VaadinSpringDataHelpers.toSpringPageRequest(query)).stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(KEIKAT_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(KeikatView.class);
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(Keikat.class);

        // Bind fields. This is where you'd define e.g. validation rules
        binder.forField(hinta).withConverter(new StringToIntegerConverter("Only numbers are allowed")).bind("hinta");

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.keikat == null) {
                    this.keikat = new Keikat();
                }
                binder.writeBean(this.keikat);
                keikatService.save(this.keikat);
                clearForm();
                refreshGrid();
                Notification.show("Data updated");
                UI.getCurrent().navigate(KeikatView.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Error updating the data. Somebody else has updated the record while you were making changes.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
            } catch (ValidationException validationException) {
                Notification.show("Failed to update the data. Check again that all values are valid");
            }
        });
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Long> keikatId = event.getRouteParameters().get(KEIKAT_ID).map(Long::parseLong);
        if (keikatId.isPresent()) {
            Optional<Keikat> keikatFromBackend = keikatService.get(keikatId.get());
            if (keikatFromBackend.isPresent()) {
                populateForm(keikatFromBackend.get());
            } else {
                Notification.show(String.format("The requested keikat was not found, ID = %s", keikatId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(KeikatView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        artisti = new TextField("Artisti");
        sijainti = new TextField("Sijainti");
        hinta = new TextField("Hinta");
        ajankohta = new DateTimePicker("Ajankohta");
        ajankohta.setStep(Duration.ofSeconds(1));
        formLayout.add(artisti, sijainti, hinta, ajankohta);

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("button-layout");
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setClassName("grid-wrapper");
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Keikat value) {
        this.keikat = value;
        binder.readBean(this.keikat);

    }
}
