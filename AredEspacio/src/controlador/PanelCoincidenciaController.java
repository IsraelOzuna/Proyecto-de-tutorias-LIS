package controlador;

import java.io.File;
import java.io.IOException;
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
import persistencia.Alumno;
import persistencia.Cliente;
import persistencia.Maestro;

/**
 * Este controlador es usado para manipular la interfaz gráfica de las
 * coincidencias de búsqueda de una persona
 *
 * @author Irvin Vera
 * @version 1.0 / 5 de junio de 2018
 */
public class PanelCoincidenciaController implements Initializable {

    @FXML
    private Label etiquetaNombre;
    @FXML
    private Button botonVerDetalle;
    @FXML
    private ImageView fotoPerfil;
    @FXML
    private AnchorPane panelCoincidencia;
    private AnchorPane panelDetalles;
    Pane panelPrincipal = new Pane();
    private String seccion = "";
    private Maestro maestro;
    private Alumno alumno;
    private Cliente cliente;
    private String nombreUsuarioActual;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    /**
     * Este método permite obtener los datos de la ventana anterior
     *
     * @param seccion especifica si lo que se buscara son alumnos, maestros o
     * clientes
     * @param panelPrincipal sobre el que se mostrarán las coincidencias
     * @param nombreUsuario identificador único del la persona que está usando
     * el sistema
     */
    public void obtenerSeccion(String seccion, Pane panelPrincipal, String nombreUsuario) {
        nombreUsuarioActual = nombreUsuario;
        this.seccion = seccion;
        this.panelPrincipal = panelPrincipal;
    }

    @FXML
    private void desplegarDetalles(ActionEvent event) throws IOException {
        FXMLLoader loader;
        Parent root;
        switch (seccion) {
            case "Alumnos":
                loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaInformacionAlumnos.fxml"));
                root = (Parent) loader.load();
                VentanaInformacionAlumnosController ventanaMostrarInformacionAlumno = loader.getController();
                ventanaMostrarInformacionAlumno.obtenerAlumno(alumno);
                ventanaMostrarInformacionAlumno.obtenerPanel(panelPrincipal);
                ventanaMostrarInformacionAlumno.llenarCamposInformacion(nombreUsuarioActual);
                panelPrincipal.getChildren().add(root);
                break;
            case "Maestros":
                loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaMostrarInformacionMaestro.fxml"));
                root = (Parent) loader.load();
                VentanaMostrarInformacionMaestroController ventanaMostrarInformacionMaestro = loader.getController();
                ventanaMostrarInformacionMaestro.obtenerMaestro(maestro);
                ventanaMostrarInformacionMaestro.obtenerPanel(panelPrincipal);
                ventanaMostrarInformacionMaestro.llenarCamposInformacion(nombreUsuarioActual);
                panelPrincipal.getChildren().add(root);
                break;

            case "Clientes":
                loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaInformacionClientes.fxml"));
                root = (Parent) loader.load();
                VentanaInformacionClientesController ventanaMostrarInformacionCliente = loader.getController();
                ventanaMostrarInformacionCliente.obtenerCliente(cliente);
                ventanaMostrarInformacionCliente.obtenerPanel(panelPrincipal);
                ventanaMostrarInformacionCliente.llenarCamposInformacion(nombreUsuarioActual);
                panelPrincipal.getChildren().add(root);
                break;
            default:
                break;
        }
    }

    /**
     * Este método permite llenar los el panel de coincidencias con los datos de
     * los alumnos obtenidos de la búsqueda
     *
     * @param alumno contiene los datos el alumno
     */
    public void llenarDatosAlumno(Alumno alumno) {
        try {
            CodeSource direccion = PanelCoincidenciaController.class.getProtectionDomain().getCodeSource();
            File fileJar = new File(direccion.getLocation().toURI().getPath());
            File fileDir = fileJar.getParentFile();
            File fileProperties = new File(fileDir.getAbsolutePath());

            String rutaFoto = fileProperties.getAbsolutePath();

            this.alumno = alumno;
            String nombre = alumno.getNombre();
            String apellidos = alumno.getApellidos();
            etiquetaNombre.setText(nombre + " " + apellidos);
            if (alumno.getRutaFoto() != null) {
                Image foto = new Image("file:" + rutaFoto + "/imagenesAlumnos/" + alumno.getRutaFoto(), 100, 100, false, true, true);
                fotoPerfil.setImage(foto);
            }
        } catch (URISyntaxException ex) {
            Logger.getLogger(PanelCoincidenciaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Este método permite llenar los el panel de coincidencias con los datos de
     * los maestros obtenidos de la búsqueda
     *
     * @param maestro contiene los datos del maestro
     */
    public void llenarDatosMaestro(Maestro maestro) {
        try {
            CodeSource direccion = PanelCoincidenciaController.class.getProtectionDomain().getCodeSource();
            File fileJar = new File(direccion.getLocation().toURI().getPath());
            File fileDir = fileJar.getParentFile();
            File fileProperties = new File(fileDir.getAbsolutePath());

            String rutaFoto = fileProperties.getAbsolutePath();
            this.maestro = maestro;
            String nombre = maestro.getNombre();
            String apellidos = maestro.getApellidos();
            etiquetaNombre.setText(nombre + " " + apellidos);
            if (maestro.getRutaFoto() != null) {
                Image foto = new Image("file:" + rutaFoto + "/imagenesMaestros/" + maestro.getRutaFoto(), 100, 100, false, true, true);
                fotoPerfil.setImage(foto);
            }
        } catch (URISyntaxException ex) {
            Logger.getLogger(PanelCoincidenciaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Este método permite llenar los el panel de coincidencias con los datos de
     * los clientes obtenidos de la búsqueda
     *
     * @param cliente contiene los datos del cliente
     */
    public void llenarDatosCliente(Cliente cliente) {
        try {
            CodeSource direccion = PanelCoincidenciaController.class.getProtectionDomain().getCodeSource();
            File fileJar = new File(direccion.getLocation().toURI().getPath());
            File fileDir = fileJar.getParentFile();
            File fileProperties = new File(fileDir.getAbsolutePath());

            String rutaFoto = fileProperties.getAbsolutePath();
            this.cliente = cliente;
            String nombre = cliente.getNombre();
            String apellidos = cliente.getApellidos();
            etiquetaNombre.setText(nombre + " " + apellidos);
            if (cliente.getRutaFoto() != null) {
                Image foto = new Image("file:" + rutaFoto + "/imagenesClientes/" + cliente.getRutaFoto(), 100, 100, false, true, true);
                fotoPerfil.setImage(foto);
            }
        } catch (URISyntaxException ex) {
            Logger.getLogger(PanelCoincidenciaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
