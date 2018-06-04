package controlador;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import negocio.Egreso;
import negocio.EgresoDAO;
import persistencia.Maestro;
import negocio.MaestroDAO;
import negocio.Utileria;

/**
 * Este controlador es usado para registrar los gastos que son consecuencia de
 * los anuncios en Facebook
 *
 * @author Israel Reyes Ozuna
 * @version 1.0 / 5 de junio de 2018
 */
public class VentanaEgresosFbController implements Initializable {

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
    @FXML
    private Label etiquetaFechaPublicacion;
    @FXML
    private Label etiquetaFechaFin;
    @FXML
    private Label etiquetaCosto;
    @FXML
    private JFXTextField campoLink;
    @FXML
    private Label etiquetaLink;
    @FXML
    private Label etiquetaCreador;
    @FXML
    private Label etiquetaDescripcion;
    @FXML
    private JFXDatePicker campoFechaRegistro;
    @FXML
    private Label etiquetaFechaRegistro;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        llenarComboMaestros();
    }

    @FXML
    private void registrarEgresoFb(ActionEvent event) {
        limpiarEtiquetas();

        if (!existenCamposVacios()) {
            if (!existenCamposExcedidos()) {
                Date fechaRegistro = new Date();
                Egreso egresoFb = new Egreso();
                EgresoDAO nuevoEgresoFb = new EgresoDAO();
                boolean cantidadValida = true;

                try {
                    Double.parseDouble(campoCosto.getText().trim());
                } catch (NumberFormatException ex) {
                    cantidadValida = false;
                    etiquetaCosto.setText("Solo ingresa números");
                }
                if (cantidadValida) {
                    egresoFb.setCosto(Double.parseDouble(campoCosto.getText().trim()));
                    egresoFb.setCreador((String) comboCreador.getValue());
                    egresoFb.setDescripcion(campoDescripcion.getText().trim());
                    egresoFb.setFechaFinPublicacion(Utileria.convertirFecha(campoFechaFinPub.getValue()));
                    egresoFb.setFechaInicioPublicacion(Utileria.convertirFecha(campoFehcaInicioPub.getValue()));

                    egresoFb.setFechaRegistro(Utileria.convertirFecha(campoFechaRegistro.getValue()));

                    egresoFb.setUrl(campoLink.getText().trim());

                    if (nuevoEgresoFb.registrarEgresoFb(egresoFb, (String) comboCreador.getValue())) {
                        DialogosController.mostrarMensajeInformacion("Registrado", "Egreso registrado", "El egreso de ha registrado correctamente");
                        limpiarCampos();
                    } else {
                        DialogosController.mostrarMensajeAdvertencia("Error", "Error al registrar", "El egreso no se pudo registrar");
                    }
                }
            }
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
     * Este método llena el combo box del creador para saber cual de los
     * maestros fue el que realizó la publicación
     *
     * @since 1.0 / 5 de junio de 2018
     */
    public void llenarComboMaestros() {
        ObservableList maestros = FXCollections.observableArrayList();
        MaestroDAO maestroDAO = new MaestroDAO();
        List<Maestro> listaMaestros;
        listaMaestros = maestroDAO.adquirirMaestros();
        for (int i = 0; i < listaMaestros.size(); i++) {
            maestros.add(listaMaestros.get(i).getUsuario());
        }
        comboCreador.setItems(maestros);
    }

    @FXML
    private void limitarLink(KeyEvent event) {
        if (campoLink.getText().trim().length() >= 320) {
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
        boolean camposVacios = false;

        if (campoCosto.getText().trim().isEmpty()) {
            camposVacios = true;
            etiquetaCosto.setText("Campo obligatorio");
        }
        if (campoFechaFinPub.getValue() == null) {
            camposVacios = true;
            etiquetaFechaFin.setText("Campo obligatorio");
        }
        if (campoFehcaInicioPub.getValue() == null) {
            camposVacios = true;
            etiquetaFechaPublicacion.setText("Campo obligatorio");
        }
        if (campoLink.getText().trim().isEmpty()) {
            camposVacios = true;
            etiquetaLink.setText("Campo obligatorio");
        }
        if (comboCreador.getSelectionModel().getSelectedItem() == null) {
            camposVacios = true;
            etiquetaCreador.setText("Campo obligatorio");
        }
        if (campoFechaRegistro.getValue() == null) {
            camposVacios = true;
            etiquetaFechaRegistro.setText("Campo obligatorio");
        }
        return camposVacios;
    }

    /**
     * Este método verifica que los datos ingresados por el usuario no excedan
     * el limite para poder almacenarlos correctamente
     *
     * @return boolean que verifica si existen o no campos excedidos
     * @since 1.0 / 5 de junio de 2018
     */
    public boolean existenCamposExcedidos() {
        boolean camposExcedidos = false;

        if (campoCosto.getText().trim().length() > 10) {
            camposExcedidos = true;
            etiquetaCosto.setText("Campo excedido");
        }
        if (campoDescripcion.getText().trim().length() > 500) {
            camposExcedidos = true;
            etiquetaDescripcion.setText("Solo 500 caracteres");
        }
        return camposExcedidos;
    }

    /**
     * Este método quita el contenido de las etiquetas que se muestran en caso
     * de algún error
     *
     * @since 1.0 / 5 de junio de 2018
     */
    public void limpiarEtiquetas() {
        etiquetaCosto.setText("");
        etiquetaCreador.setText("");
        etiquetaDescripcion.setText("");
        etiquetaFechaFin.setText("");
        etiquetaFechaPublicacion.setText("");
        etiquetaLink.setText("");
        etiquetaFechaRegistro.setText("");
    }

    /**
     * Este método quita el contenido de los campos después de que el egreso se
     * registró correctamente
     *
     * @since 1.0 / 5 de junio de 2018
     */
    public void limpiarCampos() {
        campoCosto.setText("");
        campoDescripcion.setText("");
        campoFechaFinPub.setValue(null);
        campoFehcaInicioPub.setValue(null);
        campoLink.setText("");
        comboCreador.setValue(null);
        campoFechaRegistro.setValue(null);
    }
}
