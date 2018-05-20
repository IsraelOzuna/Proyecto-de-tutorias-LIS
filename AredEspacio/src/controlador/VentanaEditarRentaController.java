/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import persistencia.Renta;

/**
 * FXML Controller class
 *
 * @author Irdevelo
 */
public class VentanaEditarRentaController implements Initializable {

    @FXML
    private Label etiquetaCliente;
    @FXML
    private ComboBox<?> comboBoxHoraInicio;
    @FXML
    private ComboBox<?> comboBoxHoraFin;
    @FXML
    private Label etiquetaHoraFin;
    @FXML
    private DatePicker campoFecha;
    @FXML
    private Label etiquetaFecha;
    @FXML
    private Label etiquetaCantidad;
    @FXML
    private TextField campoCantidad;
    @FXML
    private Button botonMostrarHorarios;
    @FXML
    private TableView<?> tablaGrupos;
    @FXML
    private TableColumn<?, ?> columnaHorarioGrupo;
    @FXML
    private TableColumn<?, ?> columnaNombreGrupo;
    @FXML
    private TableView<?> tablaRentas;
    @FXML
    private TableColumn<?, ?> columnaHorarioRenta;
    @FXML
    private TableColumn<?, ?> columnaHoraFin;
    @FXML
    private TableColumn<?, ?> columnaNombreClienteRenta;
    @FXML
    private JFXButton botonGuardar;
    @FXML
    private JFXButton botonCancelar;
    @FXML
    private Label etiquetaNombreCliente;

    private Pane panelPrincipal;

    private Renta renta;
    @FXML
    private Label etiquetaGrupos;
    @FXML
    private Label etiquetaRentas;

    public void obtenerPanel(Pane panelPrincipal, Renta renta) {
        this.panelPrincipal = panelPrincipal;
        this.renta = renta;
    }

    public void llenarDatos() {
        etiquetaNombreCliente.setText(renta.getNombreCliente());

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void limitarCaracteresCantidad(KeyEvent event) {

    }

    @FXML
    private void consultarHorariosDelDia(ActionEvent event) {
    }

    @FXML
    private void guardarCambios(ActionEvent event) {
    }

    @FXML
    private void cerrarVentanaEditarRenta(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaRentas.fxml"));
        Parent root = (Parent) loader.load();
        VentanaRentasController ventanaRentas = loader.getController();
        ventanaRentas.obtenerPanel(panelPrincipal);
        ventanaRentas.llenarTablaRentas();
        panelPrincipal.getChildren().add(root);
    }

}
