package controlador;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import persistencia.Cliente;

/**
 * Este controlador es usado para manipular la vista en la que se muestran los
 * detalles de un cliente y también puede llevar a otras funcionalidades
 *
 * @author Israel Reyes Ozuna
 * @version 1.0 / 5 de junio de 2018
 */
public class VentanaInformacionClientesController {

    @FXML
    private AnchorPane panelTrasero;
    @FXML
    private ImageView imagenPerfil;
    @FXML
    private Label etiquetaNombre;
    @FXML
    private Label etiquetaCorreo;
    @FXML
    private Label etiquetaTelefono;
    private Pane panelPrincipal;
    private Cliente cliente;
    private String nombreUsuarioActual;

    /**
     * Este método permite obtener el panel anterior para poder manipularlo
     *
     * @param panelPrincipal
     * @since 1.0 / 5 de junio de 2018
     */
    public void obtenerPanel(Pane panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    /**
     * Este método permite obtener el cliente de la busqueda para ver sus datos
     *
     * @param cliente obtenido en la busqueda
     * @since 1.0 / 5 de junio de 2018
     */
    public void obtenerCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @FXML
    private void desplegarVentanaEditar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaEditarCliente.fxml"));
        Parent root = (Parent) loader.load();
        VentanaEditarClienteController ventanaEditar = loader.getController();
        ventanaEditar.llenarCampos(cliente, nombreUsuarioActual);
        panelPrincipal.getChildren().add(root);
    }

    @FXML
    private void cerrarDetalles(ActionEvent event) {
        panelTrasero.setVisible(false);
    }

    /**
     * Este método carga los datos existentes del cliente
     *
     * @param nombreUsuario usuario el cual revisa los datos del cliente
     * @since 1.0 / 5 de junio de 2018
     */
    public void llenarCamposInformacion(String nombreUsuario) {
        try {
            nombreUsuarioActual = nombreUsuario;
            etiquetaNombre.setText(cliente.getNombre() + " " + cliente.getApellidos());
            etiquetaCorreo.setText(cliente.getCorreo());
            etiquetaTelefono.setText(cliente.getTelefono());

            CodeSource direccion = VentanaInformacionClientesController.class.getProtectionDomain().getCodeSource();
            File fileJar = new File(direccion.getLocation().toURI().getPath());
            File fileDir = fileJar.getParentFile();
            File fileProperties = new File(fileDir.getAbsolutePath());

            String rutaFoto = fileProperties.getAbsolutePath();
            if (cliente.getRutaFoto() != null) {
                Image foto = new Image("file:" + rutaFoto + "/imagenesClientes/" + cliente.getRutaFoto(), 100, 100, false, true, true);
                imagenPerfil.setImage(foto);
            }
        } catch (URISyntaxException ex) {
            Logger.getLogger(VentanaInformacionClientesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
