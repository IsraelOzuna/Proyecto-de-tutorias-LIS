package controlador;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import negocio.Egreso;
import negocio.EgresoDAO;

/**
 * Este controlador es usado para registrar diferentes tipos de egresos
 *
 * @author Israel Reyes Ozuna
 * @version 1.0 / 5 de junio de 2018
 */
public class VentanaEgresoVariadoController implements Initializable {

    @FXML
    private JFXTextField campoGasto;
    @FXML
    private JFXTextField campoCosto;
    @FXML
    private TextArea campoDescripcion;
    @FXML
    private Label etiquetaGasto;
    @FXML
    private Label etiquetaCosto;
    @FXML
    private Label etiquetaDescripcion;
    String fechaActual;
    @FXML
    private AnchorPane panelPrincipal;
    @FXML
    private JFXDatePicker campoFechaRegistro;
    @FXML
    private Label etiquetaFechaRegistro;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void registrarEgreso(ActionEvent event) {
        limpiarEtiquetas();
        if (!existenCamposVacios()) {
            if (!existeCamposExcedidos()) {
                Date fechaRegistro = new Date();
                Egreso egreso = new Egreso();
                EgresoDAO nuevoEgreso = new EgresoDAO();
                boolean cantidadValida = true;

                try {
                    Double.parseDouble(campoCosto.getText().trim());
                } catch (NumberFormatException ex) {
                    cantidadValida = false;
                    etiquetaCosto.setText("Solo ingresa números");
                }

                if (cantidadValida) {
                    egreso.setCosto(Double.parseDouble(campoCosto.getText().trim()));
                    egreso.setFechaRegistro(fechaRegistro);
                    egreso.setNombreGasto(campoGasto.getText().trim());
                    egreso.setDescripcion(campoDescripcion.getText().trim());

                    if (nuevoEgreso.registrarEgresoVariado(egreso)) {
                        DialogosController.mostrarMensajeInformacion("Registrado", "Egreso registrado", "El egreso de ha registrado correctamente");
                        panelPrincipal.setVisible(false);
                    } else {
                        DialogosController.mostrarMensajeAdvertencia("Error", "Error al registrar", "El egreso no se pudo registrar");
                    }
                }
            }
        }
    }

    @FXML
    private void limitarNombreGasto(KeyEvent event) {
        if (campoDescripcion.getText().length() >= 30) {
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

    /**
     * Este método permite que no se dejen campos obligatorios del gasto en
     * blanco
     *
     * @return boolean que verifica si existen o no campos vacíos
     * @since 1.0 / 5 de junio de 2018
     */
    public boolean existenCamposVacios() {
        boolean camposExcedidos = false;

        if (campoCosto.getText().trim().isEmpty()) {
            camposExcedidos = true;
            etiquetaCosto.setText("Campo obligatorio");
        }

        if (campoGasto.getText().trim().isEmpty()) {
            camposExcedidos = true;
            etiquetaGasto.setText("Campo obligatorio");
        }
        if (campoFechaRegistro.getValue() == null) {
            camposExcedidos = true;
            etiquetaFechaRegistro.setText("Campo obligatorio");
        }
        return camposExcedidos;
    }

    /**
     * Este método verifica que los datos ingresados por el usuario no excedan
     * el limite para poder almacenarlos correctamente
     *
     * @return boolean que verifica si existen o no campos excedidos
     * @since 1.0 / 5 de junio de 2018
     */
    public boolean existeCamposExcedidos() {
        boolean limiteExcedido = false;

        if (campoCosto.getText().trim().length() > 10) {
            limiteExcedido = true;
            etiquetaCosto.setText("Campo excedido");
        }

        if (campoGasto.getText().trim().length() > 30) {
            limiteExcedido = true;
            etiquetaGasto.setText("Campo excedido. Solo 30 caracteres");
        }

        if (campoDescripcion.getText().trim().length() > 500) {
            limiteExcedido = true;
            etiquetaDescripcion.setText("Solo 500 caracteres");
        }

        return limiteExcedido;
    }

    /**
     * Este método quita el contenido de las etiquetas que se muestran en caso
     * de algún error
     *
     * @since 1.0 / 5 de junio de 2018
     */
    public void limpiarEtiquetas() {
        etiquetaCosto.setText("");
        etiquetaDescripcion.setText("");
        etiquetaGasto.setText("");
        etiquetaFechaRegistro.setText("");
    }

    @FXML
    private void cerrarVentana(ActionEvent event) {
        panelPrincipal.setVisible(false);
    }
}
