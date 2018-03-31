/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import persistencia.Maestro;

/**
 * FXML Controller class
 *
 * @author Irdevelo
 */
public class VentanaMostrarInformacionMaestroController implements Initializable {

    @FXML
    private Label etiquetaTelefono;
    @FXML
    private ImageView imagenPerfil;
    @FXML
    private JFXButton botonCerrar;
    @FXML
    private Button botonRegistrarPago;
    @FXML
    private Button botonEditar;
    @FXML
    private Label etiquetaNombre;
    @FXML
    private Label etiquetaCorreo;
    @FXML
    private Label etiquetaMontoAPagar;

    private Maestro maestro;

    private Pane panelPrincipal;

    public void obtenerMaestro(Maestro maestro) {
        this.maestro = maestro;
    }

    public void obtenerPanel(Pane panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void llenarCamposInformacion() throws MalformedURLException {
        etiquetaNombre.setText(maestro.getNombre() + " " + maestro.getApellidos());
        etiquetaCorreo.setText(maestro.getCorreoElectronico());
        etiquetaMontoAPagar.setText(Double.toString(maestro.getMensualidad()));
        etiquetaTelefono.setText(maestro.getTelefono());
        Image foto = new Image("file:" + '"' + maestro.getRutaFoto() + '"');
        imagenPerfil.setImage(foto);

    }
}
