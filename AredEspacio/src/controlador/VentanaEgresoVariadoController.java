package controlador;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Israel Reyes Ozuna
 */
public class VentanaEgresoVariadoController implements Initializable {

    @FXML
    private Label etiquetaFecha;
    @FXML
    private JFXTextField campoGasto;
    @FXML
    private JFXTextField campoCosto;
    @FXML
    private TextArea campoDescripcion;
    String fechaActual;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Calendar fecha = Calendar.getInstance();
        int dia = fecha.get(Calendar.DATE);
        int mes = fecha.get(Calendar.MONTH);
        int anio = fecha.get(Calendar.YEAR);
        fechaActual = dia + "/" + (mes + 1) + "/" + "/" + anio;
        etiquetaFecha.setText(fechaActual);
    }

    @FXML
    private void limitarNombreGasto(KeyEvent event) {
        if (campoDescripcion.getText().length() >= 50) {
            event.consume();
        }
    }

    @FXML
    private void limitarCosto(KeyEvent event) {
        char caracter = event.getCharacter().charAt(0);
        if (campoCosto.getText().length() >= 10 || !Character.isDigit(caracter)) {
            event.consume();
        }
    }

    @FXML
    private void limitarDescripcion(KeyEvent event) {
        if (campoDescripcion.getText().length() >= 500) {
            event.consume();
        }
    }

}
