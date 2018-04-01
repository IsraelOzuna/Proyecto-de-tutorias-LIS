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
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import negocio.GrupoDAO;
import persistencia.Grupo;

/**
 * FXML Controller class
 *
 * @author Equipo
 */
public class VentanaConsultarInformacionGrupoController implements Initializable {

    @FXML
    private TableView<?> tablaAlumnos;
    @FXML
    private TableView<?> tablaHorario;
    @FXML
    private Label etiquetaMaestro;
    @FXML
    private Label etiquetaInscripcion;
    @FXML
    private Label etiquetaMensualidad;
    @FXML
    private JFXButton botonEditar;
    @FXML
    private JFXButton botonEliminar;
    @FXML
    private JFXButton botonRegresar;
    @FXML
    private Label etiquetaPrecioInscripcion;
    @FXML
    private Label etiquetaPrecioMensualidad;
    @FXML
    private Label etiquetaNombreMaestro;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }   
    
    public void establecerGrupo(String nombreGrupo){
        GrupoDAO grupoDAO = new GrupoDAO();
        Grupo grupoConsultado= new Grupo();
        grupoConsultado=grupoDAO.adquirirGrupo(nombreGrupo);
        etiquetaNombreMaestro.setText(grupoConsultado.getUsuario().getUsuario());
        etiquetaPrecioInscripcion.setText(grupoConsultado.getInscripcion().toString());
        etiquetaPrecioMensualidad.setText(grupoConsultado.getMensualidad().toString());
    }
    
}
