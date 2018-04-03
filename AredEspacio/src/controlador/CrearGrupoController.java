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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import negocio.GrupoDAO;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import persistencia.Cuenta;
import persistencia.Grupo;
import persistencia.Horario;
import persistencia.Maestro;

/**
 * FXML Controller class
 *
 * @author Equipo
 */
public class CrearGrupoController implements Initializable {

    @FXML
    private JFXButton botonGurdar;
    @FXML
    private JFXButton botonCancelar;
    @FXML
    private TableView<Horario> tablaHorario;
    @FXML
    private Label etiquetaCrearGrupo;
    @FXML
    private Label etiquetaNombre;
    @FXML
    private Label etiquetaPrecioMensualidad;
    @FXML
    private Label etuqietaMaestro;
    @FXML
    private Label etiquetaInscripción;
    @FXML
    private TextField campoMensualidad;
    @FXML
    private TextField campoInscripcion;
    @FXML
    private TableColumn columnaHorario;
    @FXML
    private TableColumn columnaDomingo;
    @FXML
    private TableColumn columnaLunes;
    @FXML
    private TableColumn ColumnaMartes;
    @FXML
    private TableColumn columnaMiercoles;
    @FXML
    private TableColumn columnaJueves;
    @FXML
    private TableColumn columnaViernes;
    @FXML
    private TableColumn columnaSabado;
    @FXML
    private TextField campoNombreGrupo;
    @FXML
    private ComboBox<String> comboBoxMaestro;
    @FXML
    private AnchorPane panelCrearGrupos;
    
    ObservableList<Horario> horarios;
 
    public void inicializarTablaHorario(){
        columnaHorario.setCellValueFactory(new PropertyValueFactory<Horario, String>("hora"));
        columnaDomingo.setCellValueFactory(new PropertyValueFactory<Horario, String>("domingo"));
        columnaLunes.setCellValueFactory(new PropertyValueFactory<Horario, String>("lunes"));
        ColumnaMartes.setCellValueFactory(new PropertyValueFactory<Horario, String>("martes"));
        columnaMiercoles.setCellValueFactory(new PropertyValueFactory<Horario, String>("miercoles"));
        columnaJueves.setCellValueFactory(new PropertyValueFactory<Horario, String>("jueves"));
        columnaViernes.setCellValueFactory(new PropertyValueFactory<Horario, String>("viernes"));
        columnaSabado.setCellValueFactory(new PropertyValueFactory<Horario, String>("sabado"));
        horarios = FXCollections.observableArrayList();
        //alumnos = FXCollections.observableArrayList();
        tablaHorario.setItems(horarios);
        
    }
    
    public void iniciarVentana(){
        ObservableList<String> maestros =FXCollections.observableArrayList();
        List<Cuenta> listaCuentas=null;
        GrupoDAO grupoDAO = new GrupoDAO();
        listaCuentas=grupoDAO.adquirirCuentas();//////////////
        //System.out.println("Lista de Grupos");
        //System.out.println("\t"+listaCuentas);
        for(int i=0; i<listaCuentas.size(); i++){
            maestros.add(listaCuentas.get(i).getUsuario());
        }
        comboBoxMaestro.setItems(maestros);
        
        this.inicializarTablaHorario();
        
        for (int i = 0; i < 12; i++) {
            Horario h1 = new Horario();
            switch(i){
                case 0:
                    h1.setHora("8:00-9:00");
                break;
                case 1:
                    h1.setHora("9:00-10:00");
                break;
                case 2:
                    h1.setHora("10:00-11:00");
                break;
                case 3:
                    h1.setHora("11:00-12:00");
                break;
                case 4:
                    h1.setHora("12:00-13:00");
                break;
                case 5:
                    h1.setHora("13:00-14:00");
                break;
                case 6:
                    h1.setHora("14:00-15:00");
                break;
                case 7:
                    h1.setHora("15:00-16:00");
                break;
                case 8:
                    h1.setHora("16:00-17:00");
                break;
                case 9:
                    h1.setHora("17:00-18:00");
                break;
                case 10:
                    h1.setHora("18:00-19:00");
                break;
                case 11:
                    h1.setHora("19:00-20:00");
                break;
            }
            h1.setLunes("Disponible");
            h1.setMartes("Disponible");
            h1.setMiercoles("Disponible");
            h1.setJueves("Disponible");
            h1.setViernes("Disponible");
            h1.setSabado("Disponible");
            horarios.add(h1);
        }
        
        
        
        /*columnaHorario.setCellValueFactory(datoHorario-> new ReadOnlyStringWrapper(datoHorario.getValue()));
        //columnaDomingo.setCellValueFactory(datoDomingo-> new ReadOnlyStringWrapper(datoDomingo.getValue()));

        tablaHorario.getItems().add("8:00-9:00");
        tablaHorario.getItems().add("9:00-10:00");
        tablaHorario.getItems().add("10:00-11:00");
        tablaHorario.getItems().add("11:00-12:00");
        tablaHorario.getItems().add("12:00-13:00");
        tablaHorario.getItems().add("13:00-14:00");
        tablaHorario.getItems().add("14:00-15:00");
        tablaHorario.getItems().add("15:00-16:00");
        tablaHorario.getItems().add("16:00-17:00");
        tablaHorario.getItems().add("17:00-18:00");
        tablaHorario.getItems().add("18:00-19:00");
        tablaHorario.getItems().add("19:00-20:00");*/
        
        /*Collection<String> listaHoras = new ArrayList<>();
        listaHoras.add("string1");
        listaHoras.add("cadena");
        listaHoras.add("cadena");
        listaHoras.add("cadena");
        listaHoras.add("cadena");
        listaHoras.add("cadena");
        ObservableList<String> detalles = FXCollections.observableArrayList(listaHoras);
        tablaHorario.getColumns().addAll(columnaHorario);*/
        
    
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
        
    }    

    @FXML
    private void registrarGrupo(ActionEvent event) throws IOException {
        GrupoDAO nuevoGrupoDAO = new GrupoDAO();
        Cuenta nuevaCuenta=new Cuenta();
        nuevaCuenta.setUsuario(comboBoxMaestro.getValue());
        Grupo nuevoGrupo = new Grupo();
        nuevoGrupo.setNombreGrupo(campoNombreGrupo.getText());
        nuevoGrupo.setUsuario(nuevaCuenta);
        nuevoGrupo.setInscripcion(Double.parseDouble(campoInscripcion.getText()));
        nuevoGrupo.setMensualidad(Double.parseDouble(campoMensualidad.getText()));
        //System.out.println(comboBoxMaestro.getValue());
        if(nuevoGrupoDAO.crearGrupo(nuevoGrupo)){
            FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaConsultarGrupos.fxml"));
            Parent root = (Parent) loader.load();
            ConsultarGruposController ventanaConsultarGruposController = loader.getController();
            ventanaConsultarGruposController.iniciarVentana();
            panelCrearGrupos.getChildren().add(root); 
        }
    }

    @FXML
    private void cancelarRegistro(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaConsultarGrupos.fxml"));
            Parent root = (Parent) loader.load();
            ConsultarGruposController ventanaConsultarGruposController = loader.getController();
            ventanaConsultarGruposController.iniciarVentana();
            panelCrearGrupos.getChildren().add(root); 
    }
    
}