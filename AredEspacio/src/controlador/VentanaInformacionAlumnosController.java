package controlador;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import persistencia.Alumno;

/**
 * FXML Controller class
 *
 * @author iro19
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

    public void llenarCamposInformacion() {
        etiquetaNombre.setText(alumno.getNombre() + " " + alumno.getApellidos());
        etiquetaCorreo.setText(alumno.getCorreoElectronico());
        etiquetaTelefono.setText(alumno.getTelefono());
        etiquetaFechaNacimiento.setText(String.valueOf(alumno.getFechaNacimiento()));
        if (alumno.getRutaFoto() != null) {
            Image foto = new Image("imagenesAlumnos/" + alumno.getRutaFoto(), 100, 100, false, true, true);
            imagenPerfil.setImage(foto);
        }
    }
}
