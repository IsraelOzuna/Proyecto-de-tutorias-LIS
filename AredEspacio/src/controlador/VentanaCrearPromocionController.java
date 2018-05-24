/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import negocio.PromocionDAO;
import persistencia.Alumno;
import persistencia.Promocion;

/**
 * FXML Controller class
 *
 * @author Renato
 */
public class VentanaCrearPromocionController implements Initializable {

    @FXML
    private TextField campoNombrePromocion;
    @FXML
    private TextField campoDescripcion;
    @FXML
    private TextField campoMonto;
    @FXML
    private JFXButton botonRegistrarPromocion;
    @FXML
    private JFXButton botonCancelar;
    
    private Alumno alumnoInscribir;
    @FXML
    private AnchorPane panelPrincipal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void iniciarVentanaDesdeInscripcion(persistencia.Alumno alumnoSeleccionado){
        alumnoInscribir=alumnoSeleccionado;
    }

    @FXML
    private void registrarPromocion(ActionEvent event)throws IOException {
        PromocionDAO promocionDAO = new PromocionDAO();
        Promocion nuevaPromocion = new Promocion();
        String nombrePromocion=campoNombrePromocion.getText();
        String descripcionPromocion = campoDescripcion.getText();
        int porcentaje= Integer.parseInt(campoMonto.getText());
        nuevaPromocion.setNombrePromocion(nombrePromocion);
        nuevaPromocion.setDescripcion(descripcionPromocion);
        nuevaPromocion.setPorcentajeDescuento(porcentaje);
        if(promocionYaExiste()){
            DialogosController.mostrarMensajeInformacion("Error", "Se ha detectado un problema y la promocion no fue creada", "Por favor consulte al encargado del sistema");
        }
        
        if(promocionDAO.registrarPromocion(nuevaPromocion)){
            DialogosController.mostrarMensajeInformacion("Creada", "La promocion fué creada de manera exitosa", "Ahora podrá aplicar está promoción cuando lo desee");
            FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaInscribirAlumno.fxml"));
            Parent root = (Parent) loader.load();
            VentanaInscribirAlumnoController ventanaInscribir = loader.getController();
            ventanaInscribir.llenarCampos(alumnoInscribir);        
            panelPrincipal.getChildren().add(root); 
        }else{
            DialogosController.mostrarMensajeInformacion("Error", "Se ha detectado un problema y la promocion no fue creada", "Por favor consulte al encargado del sistema");
        }
        
    }

    @FXML
    private void regresarPantallaAnterior(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaInscribirAlumno.fxml"));
        Parent root = (Parent) loader.load();
        VentanaInscribirAlumnoController ventanaInscribir = loader.getController();
        ventanaInscribir.llenarCampos(alumnoInscribir);        
        panelPrincipal.getChildren().add(root);
        
    }
    
    private boolean promocionYaExiste(){
        boolean existe=true;
        return existe;
    }
    
}
