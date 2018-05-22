/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import negocio.AlumnoDAO;
import negocio.GrupoDAO;
import negocio.PagoInscripcionAlumnoDAO;
import negocio.PromocionDAO;
import persistencia.Alumno;
import persistencia.Cuenta;
import persistencia.Grupo;
import persistencia.Promocion;

/**
 * FXML Controller class
 *
 * @author Renato
 */
public class VentanaInscribirAlumnoController implements Initializable {

    @FXML
    private Label etiquetaNombreAlumno;
    @FXML
    private ComboBox<String> comboGrupo;
    @FXML
    private ComboBox<String> comboPromocion;
    @FXML
    private JFXButton botonCrearPromo;
    @FXML
    private Label etiquetaMontoInscripcion;
    @FXML
    private JFXButton botonInscribir;
    @FXML
    private JFXButton botonCancelar;
    private String unidadPersistencia="AredEspacioPU";
    private int idAlumnoInscribir;
    private Alumno alumnoInscribir;
    @FXML
    private AnchorPane panelPrincipal;
    private double montoInscripcion;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void llenarCampos(Alumno alumno){
        etiquetaNombreAlumno.setText(alumno.getNombre()+" "+alumno.getApellidos());
        idAlumnoInscribir=alumno.getIdAlumno();
        alumnoInscribir=alumno;
        ObservableList<String> grupos =FXCollections.observableArrayList();
        List<Grupo> listaGrupos=null;
        Cuenta usuario = new Cuenta();
        GrupoDAO grupoDAO = new GrupoDAO(unidadPersistencia);
        listaGrupos=grupoDAO.adquirirGrupos(usuario);
        for(int i=0; i<listaGrupos.size(); i++){
            if(listaGrupos.get(i).getEstaActivo()==1){
                grupos.add(listaGrupos.get(i).getNombreGrupo());
            } 
        }
        comboGrupo.setItems(grupos);
        
        PromocionDAO promocionDAO = new PromocionDAO();
        List<Promocion> promociones = new ArrayList();
        promociones=promocionDAO.consultarPromociones();
        ObservableList<String> promocionesCombo =FXCollections.observableArrayList();
        for(int i=0; i<promociones.size(); i++){
            promocionesCombo.add(promociones.get(i).getNombrePromocion());
        }
        comboPromocion.setItems(promocionesCombo);
        

        
    }

    @FXML
    private void desplegarVentanaCrearPromocion(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaCrearPromocion.fxml"));
        Parent root = (Parent) loader.load();
        VentanaCrearPromocionController ventanaPromocion = loader.getController();
        ventanaPromocion.iniciarVentanaDesdeInscripcion(alumnoInscribir);
        panelPrincipal.getChildren().add(root); 
    }

    @FXML
    private void inscribirAlumno(ActionEvent event) throws IOException{
        boolean realizarInscripcion=false;
        GrupoDAO grupoDAO = new GrupoDAO(unidadPersistencia);
        AlumnoDAO alumnoDAO = new AlumnoDAO();
        String nombreGrupoElegido=comboGrupo.getValue();
        Grupo grupoElegido=obtenerGrupo(nombreGrupoElegido);
        List<Alumno> listaAlumnos = grupoDAO.obtenerAlumnos(grupoElegido.getIdGrupo());
        for(int i=0; i<listaAlumnos.size();i++){
            if(Objects.equals(listaAlumnos.get(i).getIdAlumno(), alumnoInscribir.getIdAlumno())){
                realizarInscripcion=false;
                i=listaAlumnos.size();
            }else{
                realizarInscripcion=true;
            }
        }
        if(realizarInscripcion==true){
            listaAlumnos.add(alumnoInscribir);
            grupoElegido.setAlumnoCollection(listaAlumnos);
            if(grupoDAO.editarGrupo(grupoElegido)){
                if(registrarPagoInscripcion(montoInscripcion, nombreGrupoElegido)){
                    DialogosController.mostrarMensajeInformacion("Inscrito", "El alumno fue inscrito de forma correcta al grupo", "");
                    FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaBuscar.fxml"));
                    Parent root = (Parent) loader.load();
                    VentanaBuscarController ventanaBuscar = loader.getController();
                    ventanaBuscar.obtenerSeccion("Alumnos", panelPrincipal);
                    panelPrincipal.getChildren().add(root);
                }else{
                    DialogosController.mostrarMensajeInformacion("Error", "Ocurrio un error y no se pudo realizar el pago de la isncripción", "");
                }
            }else{
                DialogosController.mostrarMensajeInformacion("Error", "Ocurrio un error y no se pudo realizar la inscripción", "");
            } 
        }else{
            DialogosController.mostrarMensajeInformacion("Alumno ya inscrito", "El alumno ya esta inscrito a ese grupo, por favor elija otro grupo", "");
        }
    }

    @FXML
    private void cancelarInscripcion(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaBuscar.fxml"));
        Parent root = (Parent) loader.load();
        VentanaBuscarController ventanaBuscar = loader.getController();
        ventanaBuscar.obtenerSeccion("Alumnos", panelPrincipal);
        panelPrincipal.getChildren().add(root);
    }

    @FXML
    private void cambiarMonto(ActionEvent event) {
        String nombreGrupoElegido=comboGrupo.getValue();
        Grupo grupoElegido=obtenerGrupo(nombreGrupoElegido);
        etiquetaMontoInscripcion.setText(grupoElegido.getInscripcion().toString());
        montoInscripcion=grupoElegido.getInscripcion();
        
    }
    
    public Grupo obtenerGrupo(String nombreGrupo){
        GrupoDAO grupoDAO = new GrupoDAO(unidadPersistencia);
        List<Grupo> listaGrupos;
        Grupo grupoObtenido = new Grupo();
        Cuenta usuario = new Cuenta();
        listaGrupos=grupoDAO.adquirirGrupos(usuario);
        for(int i=0; i<listaGrupos.size(); i++){
            if((listaGrupos.get(i).getNombreGrupo().equals(nombreGrupo)) && (listaGrupos.get(i).getEstaActivo()==1)){
                grupoObtenido=listaGrupos.get(i);
            }
        } 
        return grupoObtenido;
    }
    
    public boolean registrarPagoInscripcion(double monto, String nombreGrupo ){
        boolean pagoRegistrado=false;
        PagoInscripcionAlumnoDAO pagoInscripcion = new PagoInscripcionAlumnoDAO();
        pagoInscripcion.registrarInscripcion(monto, idAlumnoInscribir, nombreGrupo, new Date());
        pagoRegistrado=true;
        return pagoRegistrado;
    }
    
}
