package controlador;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author iro19
 */
public class VentanaInicioSesionController implements Initializable {

    @FXML
    private JFXTextField campoUsuario;
    @FXML
    private JFXPasswordField campoContrasena;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void limitarUsuario(KeyEvent event) {
        char caracter = event.getCharacter().charAt(0);

        if (campoUsuario.getText().length() >= 50 || !(Character.isDigit(caracter) || Character.isLetter(caracter))) {
            event.consume();
        }
    }
    
}
