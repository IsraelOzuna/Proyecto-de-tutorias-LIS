/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
    @FXML
    private AnchorPane panelInformacionMaestro;
    private String nombreUsuarioActual;

    public void obtenerMaestro(Maestro maestro) {
        this.maestro = maestro;
    }

    public void obtenerPanel(Pane panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void llenarCamposInformacion(String nombreUsuario) throws MalformedURLException {
        nombreUsuarioActual=nombreUsuario;
        etiquetaNombre.setText(maestro.getNombre() + " " + maestro.getApellidos());
        etiquetaCorreo.setText(maestro.getCorreoElectronico());
        etiquetaMontoAPagar.setText(Double.toString(maestro.getMensualidad()));
        etiquetaTelefono.setText(maestro.getTelefono());
        if (maestro.getRutaFoto() != null) {
            Image foto = new Image("file:"+ System.getProperty("user.dir") +"\\imagenesMaestros\\" + maestro.getRutaFoto(), 100, 100, false, true, true);
            imagenPerfil.setImage(foto);
        }
    }

    @FXML
    private void cerrarVentanaInformacionMaestro(ActionEvent event) {
        panelInformacionMaestro.setVisible(false);
    }

    @FXML
    private void desplegarVentanaRegistrarPago(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaRegistrarPagoMaestro.fxml"));
        Parent root = (Parent) loader.load();
        VentanaRegistrarPagoMaestroController ventanaRegistrarPagoMaestro = loader.getController();
        ventanaRegistrarPagoMaestro.obtenerMaestro(maestro);
        ventanaRegistrarPagoMaestro.obtenerPanel(panelPrincipal);
        panelPrincipal.getChildren().add(root);
    }

    @FXML
    private void desplegarVentanaEditarInformacionMaestro(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaEditarInformacionMaestro.fxml"));
        Parent root = (Parent) loader.load();
        VentanaEditarInformacionMaestroController ventanaEditarInformacionMaestro = loader.getController();
        ventanaEditarInformacionMaestro.obtenerMaestro(maestro, nombreUsuarioActual);
        ventanaEditarInformacionMaestro.obtenerPanel(panelPrincipal);
        panelPrincipal.getChildren().add(root);
    }
}
