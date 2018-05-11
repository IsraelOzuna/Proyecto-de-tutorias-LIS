/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Renato
 */
public class VentanaGenerarReporteController implements Initializable {

    @FXML
    private Label etiquetaPeriodoReporte;
    @FXML
    private TableView<?> tablaGanancias;
    @FXML
    private TableColumn<?, ?> columnaRazonGanancia;
    @FXML
    private TableColumn<?, ?> columnaCantidadGanacias;
    @FXML
    private TableView<?> tablaPerdidas;
    @FXML
    private TableColumn<?, ?> columnaRazonPerdidas;
    @FXML
    private TableColumn<?, ?> columnaCantidadPerdidas;
    @FXML
    private Label etiquetaGananciaTotal;
    @FXML
    private Label etiquetaPerdidaTotal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
