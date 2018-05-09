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
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import negocio.MaestroDAO;


public class VentanaMenuDirectorController implements Initializable {

    @FXML
    private JFXButton botonGrupos;
    @FXML
    private JFXButton botonAlumnos;
    @FXML
    private JFXButton botonRecursosReportes;
    @FXML
    private JFXButton botonMaestros;
    @FXML
    private JFXButton botonRenta;
    @FXML
    private Label etiquetaNombreUsuario;
    @FXML
    private Pane panelPrincipal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void desplegarVentanaBusquedaAlumno(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaBuscar.fxml"));
        Parent root = (Parent) loader.load();
        VentanaBuscarController ventanaBuscar = loader.getController();
        ventanaBuscar.obtenerSeccion("Alumnos", panelPrincipal);
        panelPrincipal.getChildren().add(root);
    }

    @FXML
    public void desplegarVentanaBusquedaMaestro(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaBuscar.fxml"));
        Parent root = (Parent) loader.load();
        VentanaBuscarController ventanaBuscar = loader.getController();
        ventanaBuscar.obtenerSeccion("Maestros", panelPrincipal);
        panelPrincipal.getChildren().add(root);
    }

    @FXML
    public void desplegarVentanaBusquedaRenta(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaRentas.fxml"));
        Parent root = (Parent) loader.load();
        VentanaRentasController ventanaRentas = loader.getController();
        ventanaRentas.obtenerPanel(panelPrincipal);
        ventanaRentas.llenarTablaRentas();
        panelPrincipal.getChildren().add(root);
    }

    @FXML
    public void desplegarVentanaBusquedaGrupos(ActionEvent event) throws IOException {
        MaestroDAO maestroDAO = new MaestroDAO();
        if (maestroDAO.obtenerNumeroMaestros() > 0) {
            FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaConsultarGrupos.fxml"));
            Parent root = (Parent) loader.load();
            VentanaConsultarGruposController ventanaConsultarGruposController = loader.getController();
            ventanaConsultarGruposController.iniciarVentana();
            panelPrincipal.getChildren().add(root);
        } else {
            DialogosController.mostrarMensajeInformacion("No hay maestros registrados", "No hay maestros registrados", "Debe haber al menos un maestro registrado para realizar esta acción");
        }
    }

    @FXML
    public void desplegarVentanaBusquedaClientes(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaBuscar.fxml"));
        Parent root = (Parent) loader.load();
        VentanaBuscarController ventanaBuscar = loader.getController();
        ventanaBuscar.obtenerSeccion("Clientes", panelPrincipal);
        panelPrincipal.getChildren().add(root);
    }
}
