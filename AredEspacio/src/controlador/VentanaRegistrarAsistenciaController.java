/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import negocio.AsistenciaDAO;
import negocio.GrupoDAO;
import negocio.Utileria;
import persistencia.Alumno;
import persistencia.Asistencia;
import persistencia.Grupo;
import persistencia.Horario;

/**
 * FXML Controller class
 *
 * @author Renato
 */
public class VentanaRegistrarAsistenciaController implements Initializable {

    @FXML
    private DatePicker selectorFecha;
    @FXML
    private TableView<negocio.Asistencia> tablaAsistencia;
    ObservableList<negocio.Asistencia> listaAsistencia;
    @FXML
    private TableColumn columnaAlumno;
    @FXML
    private TableColumn columnaAsistencia;
    @FXML
    private Label etiquetaNombreGrupo;
    private String unidadPersistencia="AredEspacioPU";
    private int idGrupoGlobal;
    @FXML
    private JFXButton botonRegistrarAsistencia;
    @FXML
    private JFXButton botonRegresar;
    @FXML
    private AnchorPane panelAsistencia;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void inicializarTablaAsistencia(){
        columnaAlumno.setCellValueFactory(new PropertyValueFactory<negocio.Asistencia, String>("nombreAlumno"));
        columnaAsistencia.setCellValueFactory(new PropertyValueFactory<negocio.Asistencia, CheckBox>("asistio"));
        listaAsistencia = FXCollections.observableArrayList();
        tablaAsistencia.setItems(listaAsistencia);
    }
    
    public void iniciarVentana(int idGrupo){
        GrupoDAO grupoDAO = new GrupoDAO(unidadPersistencia);
        Grupo grupo = grupoDAO.adquirirGrupo(idGrupo);
        etiquetaNombreGrupo.setText(grupo.getNombreGrupo());
        inicializarTablaAsistencia();
        iniciarTablaAsistencia(idGrupo);
    }
    
    public void iniciarTablaAsistencia (int idGrupo){
        idGrupoGlobal=idGrupo;
        GrupoDAO grupoDAO = new GrupoDAO(unidadPersistencia);
        selectorFecha.setValue(LocalDate.now());
        AsistenciaDAO asistenciaDAO = new AsistenciaDAO();
        List<Alumno> listaAlumnos=new ArrayList();
        listaAlumnos=grupoDAO.obtenerAlumnos(idGrupo);
        List<persistencia.Asistencia> listaPersistenciaAsistencia = new ArrayList();
        listaPersistenciaAsistencia=asistenciaDAO.obtenerAsistencia(Utileria.convertirFecha(selectorFecha.getValue()), idGrupo);
        for(int i=0; i<listaAlumnos.size(); i++){
            int idAlumno=listaAlumnos.get(i).getIdAlumno();
            String nombreAlumno=listaAlumnos.get(i).getNombre();
            CheckBox check1 = new CheckBox();
            check1.setSelected(llenarCheckBox(listaAlumnos.get(i).getIdAlumno(), listaPersistenciaAsistencia));
            negocio.Asistencia nuevaAsistencia = new negocio.Asistencia(idAlumno, nombreAlumno, check1);
            listaAsistencia.add(nuevaAsistencia);
        }
    }
    
    public boolean llenarCheckBox(int idAlumno, List<persistencia.Asistencia>lista){
        boolean campoLleno = false;
        AsistenciaDAO asistenciaDAO = new AsistenciaDAO();
        for(int i=0; i<lista.size();i++){
            if(lista.get(i).getIdAlumno().getIdAlumno().equals(idAlumno)){
                System.out.println("Asistio: "+lista.get(i).getAsistio());
                if(lista.get(i).getAsistio().equals("1")){
                    campoLleno=true;
                }else{
                    campoLleno=false;
                }
                break;
            }
        }
        //campoLleno=asistenciaDAO.obtenerAsistencia(fechaActual, idAlumno, idGrupo);
        return campoLleno;   
    }
    

    @FXML
    private void actualizarTabla(ActionEvent event) {
        for(int c=0; c<listaAsistencia.size();c++){
            listaAsistencia.remove(c);
        }
        
        GrupoDAO grupoDAO = new GrupoDAO(unidadPersistencia);
        AsistenciaDAO asistenciaDAO = new AsistenciaDAO();
        List<Alumno> listaAlumnos=new ArrayList();
        listaAlumnos=grupoDAO.obtenerAlumnos(idGrupoGlobal);
        List<persistencia.Asistencia> listaPersistenciaAsistencia = new ArrayList();
        listaPersistenciaAsistencia=asistenciaDAO.obtenerAsistencia(Utileria.convertirFecha(selectorFecha.getValue()), idGrupoGlobal);
        for(int i=0; i<listaAlumnos.size(); i++){
            CheckBox check1 = new CheckBox();
            check1.setSelected(llenarCheckBox(listaAlumnos.get(i).getIdAlumno(), listaPersistenciaAsistencia));
            int idAlumno=listaAlumnos.get(i).getIdAlumno();
            String nombreAlumno=listaAlumnos.get(i).getNombre();
            negocio.Asistencia nuevaAsistencia = new negocio.Asistencia(idAlumno,nombreAlumno, check1);
            listaAsistencia.add(nuevaAsistencia);
        }
    }

    @FXML
    private void registrarAsistencia(ActionEvent event) {
        System.out.println("RegistrarAsistencia");
        String nombreAlumno;
        int idAlumno;
        String asistio="";
        GrupoDAO grupoDAO = new GrupoDAO(unidadPersistencia);
        
        AsistenciaDAO asistenciaDAO = new AsistenciaDAO();
        List<Alumno> listaAlumnos=new ArrayList();
        listaAlumnos=grupoDAO.obtenerAlumnos(idGrupoGlobal);
        for(int i=0; i<listaAlumnos.size();i++){
            idAlumno=listaAsistencia.get(i).getIdAlumno();
            if(listaAsistencia.get(i).getAsistio().isSelected()){
                asistio="1";
            }else{
                asistio="0";
            }
            asistenciaDAO.RegistrarAsistencia(idAlumno, idGrupoGlobal, Utileria.convertirFecha(selectorFecha.getValue()), asistio);
        }
        DialogosController.mostrarMensajeInformacion("Registrado", "La lista de asistencia para la fecha seleccionada se ha registrado", "");
    }

    @FXML
    private void regresarPantallaAnterior(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaConsultarInformacionGrupo.fxml"));
        Parent root = (Parent) loader.load();
        VentanaConsultarInformacionGrupoController ventanaConsultarInformacionGrupoController = loader.getController();
        ventanaConsultarInformacionGrupoController.establecerGrupo(idGrupoGlobal);
        panelAsistencia.getChildren().add(root);
    }
    
}
