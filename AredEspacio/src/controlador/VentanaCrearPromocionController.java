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
import javafx.scene.control.TextField;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
