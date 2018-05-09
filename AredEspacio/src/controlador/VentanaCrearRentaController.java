/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import negocio.Utileria;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import persistencia.Horario;

/**
 * FXML Controller class
 *
 * @author Irdevelo
 */
public class VentanaCrearRentaController implements Initializable {

    private Pane panelPrincipal;
    @FXML
    private DatePicker campoFecha;
    @FXML
    private Label etiquetaFecha;
    @FXML
    private Label etiquetaCliente;
    @FXML
    private Label etiquetaCantidad;
    @FXML
    private TextField campoCantidad;
    @FXML
    private JFXButton botonRegistrar;
    @FXML
    private JFXButton botonCancelar;
    @FXML
    private ComboBox<?> comboBoxClientes;
    @FXML
    private TableColumn<Horario, String> columnaHorarioGrupo;
    @FXML
    private TableColumn<Horario, String> columnaNombreGrupo;
    @FXML
    private TableColumn<?, ?> columnaHorarioRenta;
    @FXML
    private TableColumn<?, ?> columnaNombreClienteRenta;
    @FXML
    private Button botonMostrarHorarios;
    
    String diaDeLaSemanaSeleccionado;

    ObservableList<Horario> horarios;

    @FXML
    private TableView<Horario> tablaGrupos;
    @FXML
    private TableView<?> tablaRentas;

     private String rutaXML = "C:\\Users\\irdev\\OneDrive\\Documentos\\GitHub\\Repositorio-Desarrollo-de-Software\\AredEspacio\\src\\Archivos\\Horarios.xml";
   
    
    public void obtenerPanel(Pane panelPrincipal) {
        this.panelPrincipal = panelPrincipal;

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void cerrarVentanaCrearRenta(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaRentas.fxml"));
        Parent root = (Parent) loader.load();
        VentanaRentasController ventanaRentas = loader.getController();
        ventanaRentas.obtenerPanel(panelPrincipal);
        ventanaRentas.llenarTablaRentas();
        panelPrincipal.getChildren().add(root);
    }

    @FXML
    private void registrarRenta(ActionEvent event) {
        boolean cantidadIngresadaCorrecta = true;
        if (campoCantidad.getText().isEmpty() || campoFecha.getValue() == null) {
            DialogosController.mostrarMensajeInformacion("Campo vacio", "Debe llenar todos los campos", "Debe ingresar una cantidad, elegir una fecha y el nombre del cliente");
        } else {
            try {
                Double.parseDouble(campoCantidad.getText());
            } catch (NumberFormatException ex) {
                cantidadIngresadaCorrecta = false;
            }
            if (cantidadIngresadaCorrecta) {

            } else {
                DialogosController.mostrarMensajeInformacion("Dato incorrecto", "Las letras no son una cantidad", "Debe ingresar una cantidad numérica");
            }

        }
    }

  //  public void llenarTablaHorario() {

    
//}

    public void llenarTablaRentas() {

    }
    
        public void inicializarTablaHorario(){
        columnaHorarioGrupo.setCellValueFactory(new PropertyValueFactory<Horario, String>("hora"));
        //columnaNombreGrupo.setCellValueFactory(new PropertyValueFactory<Horario, String>(""));
        horarios = FXCollections.observableArrayList();
        tablaGrupos.setItems(horarios);
        //tablaHorario.setEditable(true);
        tablaGrupos.getSelectionModel().setCellSelectionEnabled(true);
       
        
    }
    

    @FXML
    private void consultarHorariosDelDia(ActionEvent event) {
        if (campoFecha.getValue() == null) {
            DialogosController.mostrarMensajeInformacion("Campo fecha vacío", "Seleccione una fecha", "Debe ingresar una fecha para consultar los horarios del dia ");
        } else {
            diaDeLaSemanaSeleccionado = Utileria.convertirDia(campoFecha.getValue().getDayOfWeek().toString());
            inicializarTablaHorario();
            llenarTabla();
            
        }
    }

    public void llenarTabla() {
        try {
            File inputFile = new File(rutaXML);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("dia");
            for (int i = 0; i < 24; i++) {
                Horario h1 = new Horario();
                h1 = establecerFila(i, h1, nList);
                System.out.println(h1.getHora());
                horarios.add(h1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Horario establecerFila(int i, Horario h1, NodeList nList) {
        String nombreEtiqueta = "";
        switch (i) {
            case 0:
                h1.setHora("8:00-8:30");
                nombreEtiqueta = "ocho-ochoMedia";
                break;
            case 1:
                h1.setHora("8:30-9:00");
                nombreEtiqueta = "ochoMedia-nueve";
                break;
            case 2:
                h1.setHora("9:00-9:00");
                nombreEtiqueta = "nueve-nueveMedia";
                break;
            case 3:
                h1.setHora("9:30-10:00");
                nombreEtiqueta = "nueveMedia-diez";
                break;
            case 4:
                h1.setHora("10:00-10:30");
                nombreEtiqueta = "diez-diezMedia";
                break;
            case 5:
                h1.setHora("10:30-11:00");
                nombreEtiqueta = "diezMedia-once";
                break;
            case 6:
                h1.setHora("11:00-11:30");
                nombreEtiqueta = "once-onceMedia";
                break;
            case 7:
                h1.setHora("11:30-12:00");
                nombreEtiqueta = "onceMedia-doce";
                break;
            case 8:
                h1.setHora("12:00-12:30");
                nombreEtiqueta = "doce-doceMedia";
                break;
            case 9:
                h1.setHora("12:30-13:00");
                nombreEtiqueta = "doceMedia-una";
                break;
            case 10:
                h1.setHora("13:00-13:30");
                nombreEtiqueta = "una-unaMedia";
                break;
            case 11:
                h1.setHora("13:30-14:00");
                nombreEtiqueta = "unaMedia-dos";
                break;
            case 12:
                h1.setHora("14:00-14:30");
                nombreEtiqueta = "dos-dosMedia";
                break;
            case 13:
                h1.setHora("14:30-15:00");
                nombreEtiqueta = "dosMedia-tres";
                break;
            case 14:
                h1.setHora("15:00-15:30");
                nombreEtiqueta = "tres-tresMedia";
                break;
            case 15:
                h1.setHora("15:30-16:00");
                nombreEtiqueta = "tresMedia-cuatro";
                break;
            case 16:
                h1.setHora("16:00-16:30");
                nombreEtiqueta = "cuatro-cuatroMedia";
                break;
            case 17:
                h1.setHora("16:30-17:00");
                nombreEtiqueta = "cuatroMedia-cinco";
                break;
            case 18:
                h1.setHora("17:00-17:30");
                nombreEtiqueta = "cinco-cincoMedia";
                break;
            case 19:
                h1.setHora("17:30-18:00");
                nombreEtiqueta = "cincoMedia-seis";
                break;
            case 20:
                h1.setHora("18:00-18:30");
                nombreEtiqueta = "seis-seisMedia";
                break;
            case 21:
                h1.setHora("18:30-19:00");
                nombreEtiqueta = "seisMedia-siete";
                break;
            case 22:
                h1.setHora("19:00-19:30");
                nombreEtiqueta = "siete-sieteMedia";
                break;
            case 23:
                h1.setHora("19:30-20:00");
                nombreEtiqueta = "sieteMedia-ocho";
                break;
        }
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                switch (eElement.getAttribute("diaDeLaSemanaSeleccionado")) {
                    case "Domingo":
                        h1.setDia(eElement.getAttribute("nombre"));
                        h1.setDomingo(eElement.getElementsByTagName(nombreEtiqueta).item(0).getTextContent());
                        break;
                    case "Lunes":
                        h1.setDia(eElement.getAttribute("nombre"));
                        h1.setLunes(eElement.getElementsByTagName(nombreEtiqueta).item(0).getTextContent());
                        break;
                    case "Martes":
                        h1.setDia(eElement.getAttribute("nombre"));
                        h1.setMartes(eElement.getElementsByTagName(nombreEtiqueta).item(0).getTextContent());
                        break;
                    case "Miercoles":
                        h1.setDia(eElement.getAttribute("nombre"));
                        h1.setMiercoles(eElement.getElementsByTagName(nombreEtiqueta).item(0).getTextContent());
                        break;
                    case "Jueves":
                        h1.setDia(eElement.getAttribute("nombre"));
                        h1.setJueves(eElement.getElementsByTagName(nombreEtiqueta).item(0).getTextContent());
                        break;
                    case "Viernes":
                        h1.setDia(eElement.getAttribute("nombre"));
                        h1.setViernes(eElement.getElementsByTagName(nombreEtiqueta).item(0).getTextContent());
                        break;
                    case "Sabado":
                        h1.setDia(eElement.getAttribute("nombre"));
                        h1.setSabado(eElement.getElementsByTagName(nombreEtiqueta).item(0).getTextContent());
                        break;

                }

            }
        }
        return h1;
    }

}
