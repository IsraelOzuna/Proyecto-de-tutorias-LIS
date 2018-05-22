package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

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
    @FXML
    private AnchorPane panelPrincipal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void desplegarVentanaEgreso(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaEgresoVariado.fxml"));
        Parent root = (Parent) loader.load();
        VentanaEgresoVariadoController ventanaEgresoVariado = loader.getController();
        panelPrincipal.getChildren().clear();
        panelPrincipal.getChildren().add(root);
    }
    
}
