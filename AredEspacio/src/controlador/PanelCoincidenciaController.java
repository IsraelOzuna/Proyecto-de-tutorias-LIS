package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import persistencia.Alumno;
import persistencia.Maestro;

/**
 * FXML Controller class
 *
 * @author Israel Reyes Ozuna
 */
public class PanelCoincidenciaController implements Initializable {

    @FXML
    private ImageView imagenUsuario;
    @FXML
    private Label etiquetaNombre;
    @FXML
    private Button botonVerDetalle;

    Pane panelDetalles = new Pane();
    Pane panelPrincipal = new Pane();
    private String seccion = "";
    private Maestro maestro;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void obtenerSeccion(String seccion, Pane panelPrincipal) {
        this.seccion = seccion;
        this.panelPrincipal = panelPrincipal;
    }

    @FXML
    public void desplegarDetalles(ActionEvent event) throws IOException {
        FXMLLoader loader;
        Parent root;
        switch (seccion) {
            case "Alumnos":

                break;
            case "Maestros":
                loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaMostrarInformacionMaestro.fxml"));
                root = (Parent) loader.load();
                VentanaMostrarInformacionMaestroController ventanaMostrarInformacionMaestro = loader.getController();
                root.relocate(350, 100);
                ventanaMostrarInformacionMaestro.obtenerMaestro(maestro);
                ventanaMostrarInformacionMaestro.obtenerPanel(panelPrincipal);
                ventanaMostrarInformacionMaestro.llenarCamposInformacion();
                panelPrincipal.getChildren().add(root);
                break;
            case "Rentas":
                break;
            default:
                break;
        }
    }

    public void llenarDatos(Alumno alumno) {
        String nombre = alumno.getNombre();
        String apellidos = alumno.getApellidos();
        etiquetaNombre.setText(nombre + " " + apellidos);
    }

    public void llenarDatosMaestro(Maestro maestro) {
        this.maestro = maestro;
        String nombre = maestro.getNombre();
        String apellidos = maestro.getApellidos();
        etiquetaNombre.setText(nombre + " " + apellidos);
    }

}
