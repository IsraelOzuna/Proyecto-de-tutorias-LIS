/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.StageStyle;
import negocio.RentaDAO;
import persistencia.Renta;

public class VentanaRentasController implements Initializable {

    private Pane panelPrincipal;
    @FXML
    private TableView<persistencia.Renta> tablaRentas;
    @FXML
    private JFXButton botonCrearRenta;
    @FXML
    private TableColumn<persistencia.Renta, String> columnaCliente;
    @FXML
    private TableColumn<persistencia.Renta, String> columnaFecha;
    @FXML
    private TableColumn<persistencia.Renta, String> columnaHoraInicio;
    @FXML
    private TableColumn<persistencia.Renta, String> columnaHoraFin;
    @FXML
    private TableColumn<persistencia.Renta, Double> columnaCantidad;
    @FXML
    private TableColumn<persistencia.Renta, Integer> columnaIDRenta;
    @FXML
    private Button botonEliminarRenta;
    @FXML
    private Button botonEditarRenta;

    public void obtenerPanel(Pane panelPrincipal) {
        this.panelPrincipal = panelPrincipal;

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void llenarTablaRentas() {

        RentaDAO rentaDAO = new RentaDAO();
        List<persistencia.Renta> listaRentas = null;
        listaRentas = rentaDAO.obtenerRentas();

        columnaIDRenta.setCellValueFactory(new PropertyValueFactory<persistencia.Renta, Integer>("idRenta"));

        columnaCliente.setCellValueFactory(new PropertyValueFactory<persistencia.Renta, String>("nombreCliente"));

        columnaFecha.setCellValueFactory(new PropertyValueFactory<persistencia.Renta, String>("formatoFecha"));

        columnaCantidad.setCellValueFactory(new PropertyValueFactory<persistencia.Renta, Double>("Cantidad"));

        columnaHoraInicio.setCellValueFactory(new PropertyValueFactory<persistencia.Renta, String>("FormatoHoraInicio"));

        columnaHoraFin.setCellValueFactory(new PropertyValueFactory<persistencia.Renta, String>("FormatoHoraFin"));

        tablaRentas.setItems(FXCollections.observableList(listaRentas));

    }

    @FXML
    private void desplegarVentanaCrearRenta(ActionEvent event) throws IOException {
        FXMLLoader loader;
        Parent root;
        loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaCrearRenta.fxml"));
        root = (Parent) loader.load();
        VentanaCrearRentaController ventanaCrearRenta = loader.getController();
        ventanaCrearRenta.obtenerPanel(panelPrincipal);
        panelPrincipal.getChildren().add(root);
    }

    @FXML
    private void eliminarRenta(ActionEvent event) {
        Renta renta = null;
        renta = tablaRentas.getSelectionModel().getSelectedItem();
        if (renta == null) {
            DialogosController.mostrarMensajeInformacion("", "No hay una renta seleccionada", "Debe elegir la renta que deseas eliminar");
        } else {
            mostrarMensajeConfirmacion(renta);
        }
    }

    public void actualizarTablaRentas() throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaRentas.fxml"));
        Parent root = (Parent) loader.load();
        VentanaRentasController ventanaRentas = loader.getController();
        ventanaRentas.obtenerPanel(panelPrincipal);
        ventanaRentas.llenarTablaRentas();
        panelPrincipal.getChildren().add(root);
    }

    public void mostrarMensajeConfirmacion(Renta renta) {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Ventana de confirmación");
        alert.setHeaderText(null);
        alert.initStyle(StageStyle.UTILITY);
        alert.setContentText("¿Estas seguro que deseas cancelar la renta?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            RentaDAO rentaDAO = new RentaDAO();
            if (rentaDAO.eliminarRenta(renta.getIdRenta())) {
                DialogosController.mostrarMensajeInformacion("", "Renta eliminada", "La renta ha sido eliminada exitosamente");
                try {
                    actualizarTablaRentas();
                } catch (IOException ex) {
                    Logger.getLogger(VentanaRentasController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                DialogosController.mostrarMensajeInformacion("", "Error al eliminar la renta", "La renta no ha sido eliminada correctamente");
            }

        } else {
            alert.close();
        }
    }

    @FXML
    private void editarRenta(ActionEvent event) {
    }

}
