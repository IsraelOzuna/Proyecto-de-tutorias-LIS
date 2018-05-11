/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Renato
 */
public class VentanaRegistrarAsistenciaController implements Initializable {

    @FXML
    private ComboBox<String> comboGrupo;
    @FXML
    private DatePicker selectorFecha;
    @FXML
    private TableView<?> tablaAsistencia;
    
    // Clase entidad asistencia idGrupo 
    @FXML
    private TableColumn columnaAlumno;
    @FXML
    private TableColumn columnaAsistencia;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
