/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import negocio.RentaDAO;

public class VentanaRentasController implements Initializable {

    private Pane panelPrincipal;
    @FXML
    private JFXButton botonRegistrarNuevo;
    @FXML
    private TableView<persistencia.Renta> tablaRentas;

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

        TableColumn<persistencia.Renta, String> colNombreCliente = new TableColumn("Nombre cliente");
        colNombreCliente.setCellValueFactory(new PropertyValueFactory<persistencia.Renta, String>("nombreCliente"));

        TableColumn<persistencia.Renta, Date> colFecha = new TableColumn("Fecha");
        colFecha.setCellValueFactory(new PropertyValueFactory<persistencia.Renta, Date>("Fecha"));

        TableColumn<persistencia.Renta, Double> colCantidad = new TableColumn("Cantidad");
        colCantidad.setCellValueFactory(new PropertyValueFactory<persistencia.Renta, Double>("Cantidad"));

        TableColumn<persistencia.Renta, Time> colHoraInicio = new TableColumn("Hora inicio");
        colHoraInicio.setCellValueFactory(new PropertyValueFactory<persistencia.Renta, Time>("horaInicio"));

        TableColumn<persistencia.Renta, Time> colHoraFin = new TableColumn("Hora Fin");
        colHoraFin.setCellValueFactory(new PropertyValueFactory<persistencia.Renta, Time>("horaFin"));

        tablaRentas.getColumns().addAll(colNombreCliente, colFecha, colCantidad, colHoraInicio, colHoraFin);
        tablaRentas.setItems(FXCollections.observableList(listaRentas));

    }

}
