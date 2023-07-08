package hn.clinica.views.pacientes;

import com.vaadin.flow.component.UI;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouteAlias;
import hn.clinica.data.entity.Pacientes;
import hn.clinica.views.MainLayout;
import java.util.Collection;
import java.util.List;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import java.util.ArrayList;

@PageTitle("Pacientes")
@Route(value = "pacientes/:pacientesID?/:action?(edit)", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class PacientesView extends Div implements BeforeEnterObserver {

    private final String PACIENTES_EDIT_ROUTE_TEMPLATE = "pacientes/%s/edit";
    private final Grid<Pacientes> grid = new Grid<>(Pacientes.class, false);

    private TextField nombre;
    private TextField identidad;
    private TextField telefono;
    private TextField edad;
    //private TextField sangre;
    private ComboBox<Pacientes> sangre;
    private TextField peso;
    private TextField altura;
    

    private final Button cancel = new Button("Cancelar");
    private final Button save = new Button("Guardar");
    private Pacientes pacientes;
    

    public PacientesView() {
        addClassNames("pacientes-view");

        // Create UI
        SplitLayout splitLayout = new SplitLayout();
        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        // Configure Grid
        grid.addColumn("nombre").setAutoWidth(true);
        grid.addColumn("identidad").setAutoWidth(true);
        grid.addColumn("telefono").setAutoWidth(true);
        grid.addColumn("edad").setAutoWidth(true);
        grid.addColumn("sangre").setAutoWidth(true);
        grid.addColumn("peso").setAutoWidth(true);
        grid.addColumn("altura").setAutoWidth(true);
        
        
        /*grid.setItems(query -> pacientesService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());*/
        
        
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(PACIENTES_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(PacientesView.class);
            }
        });

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.pacientes == null) {
                    this.pacientes = new Pacientes();
                }
                clearForm();
                refreshGrid();
                Notification.show("Data updated");
                UI.getCurrent().navigate(PacientesView.class);
            } catch (ObjectOptimisticLockingFailureException exception) {
                Notification n = Notification.show(
                        "Error updating the data. Somebody else has updated the record while you were making changes.");
                n.setPosition(Position.MIDDLE);
                n.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }});
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
    	/*
        Optional<Long> pacientesId = event.getRouteParameters().get(PACIENTES_ID).map(Long::parseLong);
        if (pacientesId.isPresent()) {
            Optional<Pacientes> pacientesFromBackend = pacientesService.get(pacientesId.get());
            if (pacientesFromBackend.isPresent()) {
                populateForm(pacientesFromBackend.get());
            } else {
                Notification.show(String.format("The requested pacientes was not found, ID = %s", pacientesId.get()),
                        3000, Notification.Position.BOTTOM_START);
                // when a row is selected but the data is no longer available,
                // refresh grid
                refreshGrid();
                event.forwardTo(PacientesView.class); 
            }
        }*/
    	
    }

    	
    	
    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setClassName("editor");
        editorLayoutDiv.add(editorDiv);


        FormLayout formLayout = new FormLayout();
        nombre = new TextField("Nombre");
        identidad = new TextField("Identidad");
        telefono = new TextField("Telefono");
        telefono.setPrefixComponent(new Span("+504"));
        //sangre = new TextField("sangre");
        edad = new TextField("Edad");
        edad.setSuffixComponent(new Span("Años"));
       
        
        sangre = new ComboBox<>("Sangre");
        Collection<Pacientes> listadoTipoSangre = generarTipoSangre();
        sangre.setItems(listadoTipoSangre);
        sangre.setItemLabelGenerator(Pacientes::getSangre);
  
        
        peso = new TextField("Peso");
        peso.setSuffixComponent(new Span("Lbs"));

        
        altura = new TextField("Altura");
        altura.setSuffixComponent(new Span("Cm"));
        
        
        //Agregar Componentes al Layout
        formLayout.add(nombre, identidad,sangre, edad, peso, altura);

        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }


	private Collection<Pacientes> generarTipoSangre() {
		
		
		List<Pacientes> listado = new ArrayList<>();
		Pacientes aPositivo = new Pacientes();
		aPositivo.setSangre("A-POSITIVO");
		
		Pacientes oPositivo = new Pacientes();
		oPositivo.setSangre("O-POSITIVO");
		
		Pacientes oNegativo = new Pacientes();
		oNegativo.setSangre("O-NEGATIVO");
	
		
		listado.add(aPositivo);
		listado.add(oPositivo);
		listado.add(oNegativo);
		
		return listado;
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

    private void populateForm(Pacientes value) {
        this.pacientes = value;
    }
}
