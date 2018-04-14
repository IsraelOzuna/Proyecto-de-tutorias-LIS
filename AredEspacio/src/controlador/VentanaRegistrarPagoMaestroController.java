/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.jfoenix.controls.JFXButton;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import persistencia.Maestro;

public class VentanaRegistrarPagoMaestroController implements Initializable {

    @FXML
    private ImageView imagenPerfil;
    @FXML
    private Label etiquetaMontoAPagar;
    @FXML
    private TextField campoCantidadPagada;
    @FXML
    private JFXButton botonCancelar;
    @FXML
    private JFXButton botonAceptar;
    @FXML
    private Label etiquetaNombre;
    private Pane panelPrincipal;
    private Maestro maestro;
    @FXML
    private AnchorPane panelRegistroPago;

    public void obtenerMaestro(Maestro maestro) {
        this.maestro = maestro;
        try {
            llenarCamposInformacion();
        } catch (MalformedURLException ex) {
            Logger.getLogger(VentanaRegistrarPagoMaestroController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void obtenerPanel(Pane panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void llenarCamposInformacion() throws MalformedURLException {
        etiquetaNombre.setText(maestro.getNombre() + " " + maestro.getApellidos());
        if (maestro.getRutaFoto() != null) {
            Image foto = new Image("imagenesMaestros/" + maestro.getRutaFoto(), 100, 100, false, true, true);
            imagenPerfil.setImage(foto);
        }
    }      

    @FXML
    private void cerrarVentanaRegistroPago(ActionEvent event) {
        panelRegistroPago.setVisible(false);
    }

    @FXML
    private void registrarPago(ActionEvent event) {
        boolean cantidadCorrecta = true;
        if(campoCantidadPagada.getText().isEmpty()){
         DialogosController.mostrarMensajeInformacion("Campo vacio", "El campo de cantidad esta vacío", "Debe ingresar una cantidad");
        }else{
            try{
        Double.parseDouble(campoCantidadPagada.getText());
            }catch(NumberFormatException ex){
                cantidadCorrecta = false;
             DialogosController.mostrarMensajeInformacion("Dato incorrecto", "Las letras no son una cantidad", "Debe ingresar una cantidad numérica");
            }
            
            if(cantidadCorrecta){
                System.out.println("Registrar pago");
            }
        }
    }

 
}
