/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author iro19
 */
public class VentanaInformacionAlumnosController implements Initializable {

    @FXML
    private AnchorPane panelConsultarAlumno;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
