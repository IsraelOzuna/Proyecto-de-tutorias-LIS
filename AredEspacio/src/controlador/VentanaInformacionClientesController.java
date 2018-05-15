/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import persistencia.Cliente;

/**
 *
 * @author iro19
 */
public class VentanaInformacionClientesController {

    @FXML
    private AnchorPane panelTrasero;
    @FXML
    private AnchorPane panelInformacionAlumno;
    @FXML
    private Button botonEditar;
    @FXML
    private Button botonRegistrarPago;
    @FXML
    private ImageView imagenPerfil;
    @FXML
    private Label etiquetaNombre;
    @FXML
    private Label etiquetaCorreo;
    @FXML
    private Label etiquetaTelefono;
    @FXML
    private JFXButton botonCerrar;
    private Pane panelPrincipal;
    private Cliente cliente;

    public void obtenerPanel(Pane panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public void obtenerCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @FXML
    private void desplegarVentanaEditar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaEditarCliente.fxml"));
        Parent root = (Parent) loader.load();
        VentanaEditarClienteController ventanaEditar = loader.getController();
        ventanaEditar.llenarCampos(cliente);        
        panelPrincipal.getChildren().add(root);
    }

    @FXML
    private void cerrarDetalles(ActionEvent event) {
        panelTrasero.setVisible(false);
    }

    public void llenarCamposInformacion() {
        etiquetaNombre.setText(cliente.getNombre() + " " + cliente.getApellidos());
        etiquetaCorreo.setText(cliente.getCorreo());
        etiquetaTelefono.setText(cliente.getTelefono());        
        if (cliente.getRutaFoto() != null) {
            Image foto = new Image("file:"+ System.getProperty("user.dir") + "\\imagenesClientes\\" + cliente.getRutaFoto(), 100, 100, false, true, true);
            imagenPerfil.setImage(foto);
        }
    }

}
