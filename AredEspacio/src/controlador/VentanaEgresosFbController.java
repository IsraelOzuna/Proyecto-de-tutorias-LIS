package controlador;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Calendar;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Calendar fecha = Calendar.getInstance();
        int dia = fecha.get(Calendar.DATE);
        int mes = fecha.get(Calendar.MONTH);
        int anio = fecha.get(Calendar.YEAR);
        fechaActual = dia + "/" + (mes + 1) + "/" + anio;
        etiquetaFechaActual.setText(fechaActual);
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
                    egresoFb.setFechaRegistro(fechaRegistro);
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

    private void llenarComboMaestros() {
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
        return camposVacios;
    }

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

    public void limpiarEtiquetas() {
        etiquetaCosto.setText("");
        etiquetaCreador.setText("");
        etiquetaDescripcion.setText("");
        etiquetaFechaFin.setText("");
        etiquetaFechaPublicacion.setText("");
        etiquetaLink.setText("");
    }

    public void limpiarCampos() {
        campoCosto.setText("");
        campoDescripcion.setText("");
        campoFechaFinPub.setValue(null);
        campoFehcaInicioPub.setValue(null);
        campoLink.setText("");
        comboCreador.setValue(null);
    }
}
