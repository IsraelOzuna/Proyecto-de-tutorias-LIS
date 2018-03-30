package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import persistencia.Alumno;

/**
 * FXML Controller class
 *
 * @author Israel Reyes Ozuna
 */
public class PanelCoincidenciaController implements Initializable {

    @FXML
    private ImageView imagenUsuario;
    @FXML
    private Label etiquetaNombre;
    @FXML
    private Button botonVerDetalle;
    
    Pane panelDetalles = new Pane();
    Pane panelPrincipal = new Pane();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    public void desplegarDetallesAlumno(ActionEvent event) throws IOException {
        /*FXMLLoader loader;
        Parent root;
        loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaInformacionAlumnos.fxml"));
        panelDetalles = (Pane) loader.load();        
        panelDetalles.setLayoutX(397);
        panelDetalles.setLayoutY(133);
        panelPrincipal.getChildren().add(panelDetalles);                */
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaInformacionAlumnos.fxml"));
        Parent root = (Parent) loader.load();
        VentanaInformacionAlumnosController detalles = loader.getController();        
        panelPrincipal.getChildren().add(root);     
    }

    public void llenarDatos(Alumno alumno) {
        String nombre = alumno.getNombre();
        String apellidos = alumno.getApellidos();
        etiquetaNombre.setText(nombre + " " + apellidos);
    }

}
