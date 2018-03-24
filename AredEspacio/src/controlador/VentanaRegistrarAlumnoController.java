/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author iro19
 */
public class VentanaRegistrarAlumnoController implements Initializable {

    @FXML
    private JFXButton botonSeleccionarImagen;
    @FXML
    private Label etiquetaNombre;
    @FXML
    private Label etiquetaApellidos;
    @FXML
    private Label etiquetaFechaNacimiento;
    @FXML
    private Label etiquetaCorreo;
    @FXML
    private Label etiquetaTelefono;
    @FXML
    private TextField campoNombre;
    @FXML
    private TextField campoApellidos;
    @FXML
    private TextField campoCorreo;
    @FXML
    private TextField campoTelefono;
    @FXML
    private DatePicker campoFechaNacimiennto;
    @FXML
    private JFXButton botonCancelar;
    @FXML
    private JFXButton botonRegistrar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void registrarNuevoAlumno(ActionEvent event) {
        if (!existenCamposVacios(campoNombre, campoApellidos, campoCorreo, campoTelefono, campoFechaNacimiennto)) {
            if (!existenCamposExcedidos(campoNombre, campoApellidos, campoCorreo, campoTelefono)) {

            }
        } else {
            DialogosController.mostrarMensajeInformacion("Campo vacio", "Alugún campo esta vacío", "Debe llenar todos los campos requeridos");
        }
    }

    public boolean existenCamposVacios(TextField campoNombre, TextField campoApellidos, TextField campoCorreo, TextField campoTelefono, DatePicker campoFechaNacimiento) {
        boolean camposVacios = false;

        if (campoNombre.getText().isEmpty()) {
            camposVacios = true;
        } else if (campoApellidos.getText().isEmpty()) {
            camposVacios = true;
        } else if (campoCorreo.getText().isEmpty()) {
            camposVacios = true;
        } else if (campoTelefono.getText().isEmpty()) {
            camposVacios = true;
        } else if (campoFechaNacimiento.getValue() == null) {
            camposVacios = true;
        }

        return camposVacios;
    }

    public boolean existenCamposExcedidos(TextField campoNombre, TextField campoApellidos, TextField campoCorreo, TextField campoTelefono) {
        boolean campoExcedido = false;

        if (campoNombre.getText().length() > 30) {            
            campoExcedido = true;
            DialogosController.mostrarMensajeInformacion("Campo excedido", "Campo nombre excedido", "El campo de nombre no puede contener mas de 30 caracteres");
        } else if (campoApellidos.getText().length() > 30) {
            campoExcedido = true;
            DialogosController.mostrarMensajeInformacion("Campo excedido", "Campo apellidos excedido", "El campo de apellidos no puede contener mas de 30 caracteres");
        } else if (campoCorreo.getText().length() > 320) {
            campoExcedido = true;
            DialogosController.mostrarMensajeInformacion("Campo excedido", "Campo correo excedido", "El campo de correo no puede contener mas de 320 caracteres");
        } else if (campoTelefono.getText().length() > 10) {
            campoExcedido = true;
            DialogosController.mostrarMensajeInformacion("Campo excedido", "Campo telefono excedido", "El campo de telefono no puede contener mas de 10 caracteres");
        }

        return campoExcedido;
    }
}
