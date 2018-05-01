package controlador;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import negocio.GrupoDAO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import persistencia.Cuenta;
import persistencia.Grupo;
import persistencia.Horario;

/**
 * FXML Controller class
 *
 * @author Equipo
 */
public class VentanaEditarGrupoController implements Initializable {

    @FXML
    private Label etiquetaEditarGrupo;
    @FXML
    private Label etiquetaNombre;
    @FXML
    private Label etiquetaMensualidad;
    @FXML
    private Label etiquetaMaestro;
    @FXML
    private Label etiquetaInscripcion;
    @FXML
    private TextField campoInscripcion;
    @FXML
    private TextField campoMensualidad;
    @FXML
    private TextField campoNombre;
    @FXML
    private JFXButton botonGuardar;
    @FXML
    private JFXButton botonCancelar;
    @FXML
    private ComboBox<String> comboMaestro;
    @FXML
    private AnchorPane panelEditarGrupo;
   
    

    public void establecerGrupo(int idGrupo){
        ObservableList<String> maestros =FXCollections.observableArrayList();
        List<Cuenta> listaUsuarios=null;
        Grupo grupo;
        GrupoDAO grupoDAO = new GrupoDAO();
        grupo=grupoDAO.adquirirGrupo(idGrupo);
        listaUsuarios=grupoDAO.adquirirCuentas();
        campoNombre.setText(grupo.getNombreGrupo());
        campoInscripcion.setText(grupo.getInscripcion().toString());
        campoMensualidad.setText(grupo.getMensualidad().toString());
        for(int i=0; i<listaUsuarios.size(); i++){///////
            maestros.add(listaUsuarios.get(i).getUsuario());///////
        }
        comboMaestro.setItems(maestros);
        comboMaestro.setValue(grupo.getUsuario().getUsuario());
        campoNombre.setDisable(true);
        
        campoInscripcion.textProperty().addListener((observable, viejoValor, nuevoValor) -> {
            if (!nuevoValor.matches("\\d+\\.\\d*")) {
                campoInscripcion.setText(nuevoValor.replaceAll("[^\\d]", ""));
            }
        });
        campoMensualidad.textProperty().addListener((observable, viejoValor, nuevoValor) -> {
            if (!nuevoValor.matches("\\d+\\.\\d*")) {
                campoMensualidad.setText(nuevoValor.replaceAll("[^\\d]", ""));
            }
        });  
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void guardarGrupoEditado(ActionEvent event) throws IOException {
        if(!existenCamposVacios()){
            if(!existenCamposExcedidos()){
                GrupoDAO nuevoGrupoDAO = new GrupoDAO();
                Cuenta nuevaCuenta=new Cuenta();
                nuevaCuenta.setUsuario(comboMaestro.getValue());
                Grupo grupoEditar = new Grupo();
                grupoEditar.setNombreGrupo(campoNombre.getText());
                grupoEditar.setUsuario(nuevaCuenta);
                grupoEditar.setInscripcion(Double.parseDouble(campoInscripcion.getText()));
                grupoEditar.setMensualidad(Double.parseDouble(campoMensualidad.getText()));
                if(nuevoGrupoDAO.editarGrupo(grupoEditar)){
                    DialogosController.mostrarMensajeInformacion("Editado", "El grupo ha sido editado", "El grupo fué editado correctamente");
                    FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaConsultarGrupos.fxml"));
                    Parent root = (Parent) loader.load();
                    VentanaConsultarGruposController ventanaConsultarGruposController = loader.getController();
                    ventanaConsultarGruposController.iniciarVentana();
                    panelEditarGrupo.getChildren().add(root); 
                }else{
                    DialogosController.mostrarMensajeInformacion("Campos Excedidos", "Existen Campos que exceden el numero de caracteres validos", "ERRORel grupo no pu");
                }
            }else{
                DialogosController.mostrarMensajeInformacion("Campos Excedidos", "Existen Campos que exceden el numero de caracteres validos", "El nombre del grupo no puede rebasar los 100 caracteres, mientras que los campos de inscripcion y mensualidad no deben exceder los 10 caracteres");
            }
            
        }else{
             DialogosController.mostrarMensajeInformacion("Campos Vacios", "Existen Campos Vacios", "Por favor llene los campos con la información requerida");
        }
        
        
        
    }

    @FXML
    private void cancelarEdicion(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaConsultarGrupos.fxml"));
        Parent root = (Parent) loader.load();
        VentanaConsultarGruposController ventanaConsultarGruposController = loader.getController();
        ventanaConsultarGruposController.iniciarVentana();
        panelEditarGrupo.getChildren().add(root);
    }
    
    
    
    
    public boolean existenCamposExcedidos() {
        boolean campoExcedido = false;

        if (campoNombre.getText().length() > 100) {
            campoExcedido = true;
        } else if (campoMensualidad.getText().length() > 10) {
            campoExcedido = true;
        } else if (campoInscripcion.getText().length() > 10) {
            campoExcedido = true;
        } 
        return campoExcedido;
    }
    
    public boolean existenCamposVacios(){
        boolean camposVacios=false;
        if(campoInscripcion.getText().isEmpty()){
            camposVacios=true;
        }
        if(campoMensualidad.getText().isEmpty()){
            camposVacios=true;
        }
        if(campoNombre.getText().isEmpty()){
            camposVacios=true;
        }
        if(comboMaestro.getValue()==null){
            camposVacios=true;
        }
        
        return camposVacios;
    }
    
}
