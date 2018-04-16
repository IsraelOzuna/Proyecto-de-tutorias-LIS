package controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import negocio.AlumnoDAO;
import persistencia.Alumno;
import persistencia.Grupo;

/**
 * FXML Controller class
 *
 * @author iro19
 */
public class VentanaRegistrarMensualidadAlumnoController implements Initializable {

    @FXML
    private Label etiquetaNombreAlumno;
    @FXML
    private JFXButton botonCancelar;
    @FXML
    private JFXButton botonAceptar;
    @FXML
    private Label etiquetaMonto;
    @FXML
    private JFXComboBox<?> comboGruposAlumno;
    @FXML
    private AnchorPane panelTrasero;
    private List<persistencia.Grupo> gruposAlumno;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void cerrarDetalles(ActionEvent event) {
        panelTrasero.setVisible(false);
    }
    
    private void llenarComboGrupo(List<?> grupos){        
        ObservableList<String> nombreGrupos = FXCollections.observableArrayList();
        nombreGrupos.addAll(obtenerNombreGrupos(gruposAlumno));
    }
    
    private ArrayList<String> obtenerNombreGrupos(List<persistencia.Grupo> gruposAlumno){        
        ArrayList<String> nombreGrupos = new ArrayList();
        AlumnoDAO alumnoDAO = new AlumnoDAO();
        Alumno alumno = new Alumno();        
        gruposAlumno = alumnoDAO.encontrarGruposAlumno(alumno.getIdAlumno());
        for(Grupo grupo:gruposAlumno){
            nombreGrupos.add(grupo.getNombreGrupo());
        }
        
        return nombreGrupos;
    }

}
