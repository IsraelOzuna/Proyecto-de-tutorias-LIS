package controlador;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * Este controlador es usado para manipular la interfaz gráfica al mostrar la
 * información de un maestro
 *
 * @author Irvin Vera
 * @version 1.0 / 5 de junio de 2018
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

    /**
     * Este método permite obtener el maestro de cual se mostrara la información
     *
     * @param maestro
     */
    public void obtenerMaestro(Maestro maestro) {
        this.maestro = maestro;
    }

    /**
     * Este método permite obtener el panel sobre el cual se mostrará la
     * información del maestro
     *
     * @param panelPrincipal sobre el cual se mostrará la ventana de la
     * información del maestro
     */
    public void obtenerPanel(Pane panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    /**
     * Este método llena los campos con la información del maestro
     *
     * @param nombreUsuario identificador único del maestro del cual se mostrará
     * la información
     * 
     */
    public void llenarCamposInformacion(String nombreUsuario) throws MalformedURLException {
        try {
            nombreUsuarioActual = nombreUsuario;
            etiquetaNombre.setText(maestro.getNombre() + " " + maestro.getApellidos());
            etiquetaCorreo.setText(maestro.getCorreoElectronico());
            if (maestro.getMensualidad() != null) {
                etiquetaMontoAPagar.setText(Double.toString(maestro.getMensualidad()));
            }
            etiquetaTelefono.setText(maestro.getTelefono());

            CodeSource direccion = VentanaMostrarInformacionMaestroController.class.getProtectionDomain().getCodeSource();
            File fileJar = new File(direccion.getLocation().toURI().getPath());
            File fileDir = fileJar.getParentFile();
            File fileProperties = new File(fileDir.getAbsolutePath());

            String rutaFoto = fileProperties.getAbsolutePath();
            if (maestro.getRutaFoto() != null) {
                Image foto = new Image("file:" + rutaFoto + "/imagenesMaestros/" + maestro.getRutaFoto(), 100, 100, false, true, true);
                imagenPerfil.setImage(foto);
            }
        } catch (URISyntaxException ex) {
            Logger.getLogger(VentanaMostrarInformacionMaestroController.class.getName()).log(Level.SEVERE, null, ex);
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
