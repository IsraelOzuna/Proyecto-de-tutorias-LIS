package controlador;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
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
import negocio.Utileria;
import persistencia.Alumno;
import persistencia.Pagoalumno;

/**
 * FXML Controller class
 *
 * @author Israel Reyes Ozuna
 */
public class VentanaInformacionAlumnosController implements Initializable {

    @FXML
    private ImageView imagenPerfil;
    @FXML
    private JFXButton botonCerrar;
    @FXML
    private Button botonDarBaja;
    @FXML
    private Button botonEditar;
    @FXML
    private Label etiquetaNombre;
    @FXML
    private Label etiquetaFechaNacimiento;
    @FXML
    private Label etiquetaCorreo;
    @FXML
    private Label etiquetaTelefono;
    @FXML
    private AnchorPane panelInformacionAlumno;
    @FXML
    private AnchorPane panelTrasero;
    @FXML
    private Button botonRegistrarPago;
    private Alumno alumno;
    private Pane panelPrincipal;
    @FXML
    private Button botonDarBaja1;
    @FXML
    private Button botonInscribir;

    public void obtenerAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public void obtenerPanel(Pane panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    public void cerrarDetalles(ActionEvent event) {
        panelTrasero.setVisible(false);
    }

    @FXML
    public void desplegarVentanaEditar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaEditarAlumno.fxml"));
        Parent root = (Parent) loader.load();
        VentanaEditarAlumnoController ventanaEditar = loader.getController();
        ventanaEditar.llenarCampos(alumno);
        panelPrincipal.getChildren().add(root);
    }

    public void llenarCamposInformacion() {
        Calendar fechaNacimiento = Calendar.getInstance();
        fechaNacimiento.setTime(alumno.getFechaNacimiento());
        int dia = fechaNacimiento.get(Calendar.DAY_OF_MONTH);
        int mes = fechaNacimiento.get(Calendar.MONTH);
        int anio = fechaNacimiento.get(Calendar.YEAR);

        etiquetaNombre.setText(alumno.getNombre() + " " + alumno.getApellidos());
        etiquetaCorreo.setText(alumno.getCorreoElectronico());
        etiquetaTelefono.setText(alumno.getTelefono());
        etiquetaFechaNacimiento.setText(dia + " de " + Utileria.convertirMeses(mes) + " de " + anio);
        if (alumno.getRutaFoto() != null) {
            Image foto = new Image("file:" + System.getProperty("user.dir") + "\\imagenesAlumnos\\" + alumno.getRutaFoto(), 100, 100, false, true, true);
            imagenPerfil.setImage(foto);
        }
    }

    @FXML
    public void desplegarVentanaRegistrarMensualidad(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaRegistrarMensualidadAlumno.fxml"));
        Parent root = (Parent) loader.load();
        VentanaRegistrarMensualidadAlumnoController ventanaRegistrarMensualidad = loader.getController();
        ventanaRegistrarMensualidad.llenarDatos(alumno.getRutaFoto(), alumno.getNombre(), alumno.getApellidos(), alumno.getIdAlumno());
        panelPrincipal.getChildren().add(root);
    }

    @FXML
    private void visualizarPagosAlumno(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaHistorialDePagosAlumnos.fxml"));
        Parent root = (Parent) loader.load();
        VentanaHistorialDePagosAlumnosController ventanaHistorialDePagos = loader.getController();
        String nombreCompletoAlumno = alumno.getNombre() + " " + alumno.getApellidos();
        ventanaHistorialDePagos.obtenerDatos(panelPrincipal, nombreCompletoAlumno, alumno.getRutaFoto());
        ArrayList<Pagoalumno> pagosAlumnos = new ArrayList(alumno.getPagoalumnoCollection());
        ventanaHistorialDePagos.llenarTablaPagos(pagosAlumnos);
        panelPrincipal.getChildren().add(root);
    }

    @FXML
    private void desplegarVentanaInscribirAlumno(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaInscribirAlumno.fxml"));
        Parent root = (Parent) loader.load();
        VentanaInscribirAlumnoController ventanaInscribir = loader.getController();
        ventanaInscribir.llenarCampos(alumno);        
        panelPrincipal.getChildren().add(root); 
    }
}
