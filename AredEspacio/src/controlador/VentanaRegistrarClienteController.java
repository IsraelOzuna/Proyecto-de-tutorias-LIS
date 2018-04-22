package controlador;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Israel Reyes Ozuna
 */
public class VentanaRegistrarClienteController implements Initializable {

    @FXML
    private ImageView fotoSeleccionada;
    @FXML
    private JFXButton botonSeleccionarImagen;
    @FXML
    private Label etiquetaNombre;
    @FXML
    private TextField campoNombre;
    @FXML
    private Label etiquetaApellidos;
    @FXML
    private TextField campoApellidos;
    @FXML
    private Label etiquetaCorreo;
    @FXML
    private TextField campoCorreo;
    @FXML
    private Label etiquetaTelefono;
    @FXML
    private TextField campoTelefono;
    @FXML
    private JFXButton botonRegistrar;
    @FXML
    private JFXButton botonCancelar;
    @FXML
    private Label etiquetaErrorNombre;
    @FXML
    private Label etiquetaErrorApellidos;
    @FXML
    private Label etiquetaErrorCorreo;
    @FXML
    private Label etiquetaErrorTelefono;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void seleccionarImagen(ActionEvent event) {
    }

    @FXML
    private void limitarNombre(KeyEvent event) {
    }

    @FXML
    private void limitarApellidos(KeyEvent event) {
    }

    @FXML
    private void limitarCorreo(KeyEvent event) {
    }

    @FXML
    private void limitarTelefono(KeyEvent event) {
    }

    @FXML
    private void registrarNuevoAlumno(ActionEvent event) {
    }

    @FXML
    private void cancelarRegistro(ActionEvent event) {
    }
    
}
