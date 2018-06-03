package controlador;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import negocio.PagoMaestro;
import negocio.PagoMaestroDAO;
import negocio.Utileria;
import persistencia.Maestro;

public class VentanaRegistrarPagoMaestroController implements Initializable {

    @FXML
    private ImageView imagenPerfil;
    @FXML
    private Label etiquetaMontoAPagar;
    @FXML
    private TextField campoCantidadPagada;
    @FXML
    private JFXButton botonCancelar;
    @FXML
    private JFXButton botonAceptar;
    @FXML
    private Label etiquetaNombre;
    private Pane panelPrincipal;
    private Maestro maestro;
    @FXML
    private AnchorPane panelRegistroPago;
    @FXML
    private DatePicker campoFechaPago;

    public void obtenerMaestro(Maestro maestro) {
        this.maestro = maestro;
        try {
            llenarCamposInformacion();
        } catch (MalformedURLException ex) {
            Logger.getLogger(VentanaRegistrarPagoMaestroController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void obtenerPanel(Pane panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void llenarCamposInformacion() throws MalformedURLException {
        try {
            etiquetaNombre.setText(maestro.getNombre() + " " + maestro.getApellidos());
            
            CodeSource direccion = VentanaRegistrarPagoMaestroController.class.getProtectionDomain().getCodeSource();
            File fileJar = new File(direccion.getLocation().toURI().getPath());
            File fileDir = fileJar.getParentFile();
            File fileProperties = new File(fileDir.getAbsolutePath());
            
            String rutaFoto = fileProperties.getAbsolutePath();
            if (maestro.getRutaFoto() != null) {
                Image foto = new Image("file:" + rutaFoto + "/imagenesMaestros/" + maestro.getRutaFoto(), 100, 100, false, true, true);
                imagenPerfil.setImage(foto);
            }
        } catch (URISyntaxException ex) {
            Logger.getLogger(VentanaRegistrarPagoMaestroController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void cerrarVentanaRegistroPago(ActionEvent event) {
        panelRegistroPago.setVisible(false);
    }

    @FXML
    private void registrarPago(ActionEvent event) {
        boolean cantidadCorrecta = true;
        if (campoCantidadPagada.getText().isEmpty() || campoFechaPago.getValue() == null) {
            DialogosController.mostrarMensajeInformacion("Campo vacio", "Debe llenar todos los campos", "Debe ingresar una cantidad y elegir una fecha de pago");
        } else {
            try {
                Double.parseDouble(campoCantidadPagada.getText());
            } catch (NumberFormatException ex) {
                cantidadCorrecta = false;
                DialogosController.mostrarMensajeInformacion("Dato incorrecto", "Las letras no son una cantidad", "Debe ingresar una cantidad numérica");
            }
            if (campoCantidadPagada.getText().length() <= 5) {
                if (cantidadCorrecta) {
                    PagoMaestro pagoMaestro = new PagoMaestro();
                    PagoMaestroDAO pagoMaestroDAO = new PagoMaestroDAO();

                    pagoMaestro.setUsuario(maestro.getUsuario());
                    pagoMaestro.setFecha(Utileria.convertirFechaNacimiento(campoFechaPago.getValue()));
                    pagoMaestro.setCantidad(Double.parseDouble(campoCantidadPagada.getText()));

                    if (pagoMaestroDAO.registrarPagoMaestro(pagoMaestro)) {
                        DialogosController.mostrarMensajeInformacion("", "Registro de pago exitoso", "El pago se ha registrado correctamente");
                        panelRegistroPago.setVisible(false);

                    } else {
                        DialogosController.mostrarMensajeInformacion("", "Registro no exitoso", "El pago no se ha registrado correctamente");
                    }

                }
            } else {
                DialogosController.mostrarMensajeInformacion("", "Limite excedido", "El campo del pago esta excedido de caracteres");
            }
        }
    }

    @FXML
    private void limitarCampoMotoAPagar(KeyEvent event) {
        char caracter = event.getCharacter().charAt(0);
        limitarCaracteres(event, campoCantidadPagada, 5);
        if (!Character.isDigit(caracter)) {
            event.consume();
        }
    }

    public void limitarCaracteres(KeyEvent event, TextField campo, int caracteresMaximos) {
        if (campo.getText().trim().length() >= caracteresMaximos) {
            event.consume();
        }
    }

}
