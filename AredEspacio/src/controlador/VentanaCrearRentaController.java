/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Irdevelo
 */
public class VentanaCrearRentaController implements Initializable {

    private Pane panelPrincipal;

    public void obtenerPanel(Pane panelPrincipal) {
        this.panelPrincipal = panelPrincipal;

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
