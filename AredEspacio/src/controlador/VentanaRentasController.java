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
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import negocio.RentaDAO;

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
        panelPrincipal.getChildren().add(root);
    }

}
