package controlador;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class VentanaBuscarController implements Initializable {

    @FXML
    private JFXButton botonRegistrarNuevo;
    @FXML
    private Label etiquetaIdentidicador;

    private String seccion = "";
    
    private Pane panelPrincipal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void obtenerSeccion(String seccion, Pane panelPrincipal) {
        this.seccion = seccion;
        this.panelPrincipal = panelPrincipal;
        etiquetaIdentidicador.setText(seccion);
    }

    @FXML
    public void desplegarNuevoRegistro() throws IOException {
        FXMLLoader loader;
        Parent root;
        switch (seccion) {
            case "Alumnos":
                loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaRegistrarAlumno.fxml"));
                root = (Parent) loader.load();
                VentanaRegistrarAlumnoController ventanaRegistrarAlumno = loader.getController();                
                panelPrincipal.getChildren().add(root);
                break;
            case "Maestros":
                loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaRegistrarMaestro.fxml"));
                root = (Parent) loader.load();
                VentanaRegistrarMaestroController ventanaRegistrarMaestro = loader.getController();                
                panelPrincipal.getChildren().add(root);
                break;
            case "Rentas":
                break;
            default:
                break;
        }
    }
}
