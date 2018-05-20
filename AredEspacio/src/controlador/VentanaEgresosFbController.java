package controlador;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import persistencia.Maestro;
import negocio.MaestroDAO;

/**
 * FXML Controller class
 *
 * @author Israel Reyes Ozuna
 */
public class VentanaEgresosFbController implements Initializable {

    @FXML
    private Label etiquetaFechaActual;
    @FXML
    private JFXDatePicker campoFehcaInicioPub;
    @FXML
    private JFXDatePicker campoFechaFinPub;
    @FXML
    private JFXTextField campoCosto;
    @FXML
    private TextArea campoDescripcion;
    String fechaActual;
    @FXML
    private JFXComboBox<?> comboCreador;

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
        etiquetaFechaActual.setText(fechaActual);
        llenarComboMaestros();
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

    private void llenarComboMaestros(){
        ObservableList maestros =FXCollections.observableArrayList();
        MaestroDAO maestroDAO = new MaestroDAO();
        List<Maestro> listaMaestros;
        listaMaestros = maestroDAO.adquirirMaestros();
        for (int i = 0; i < listaMaestros.size(); i ++){
            maestros.add(listaMaestros.get(i).getNombre() + " " + listaMaestros.get(i).getApellidos());
        }
        comboCreador.setItems(maestros);
    }
}
