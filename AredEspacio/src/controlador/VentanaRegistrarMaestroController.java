package controlador;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import negocio.CuentaDAO;
import negocio.Maestro;
import negocio.MaestroDAO;
import negocio.Utileria;
import persistencia.Cuenta;

/**
 * FXML Controller class
 *
 * @author Irdevelo
 */
public class VentanaRegistrarMaestroController implements Initializable {

    @FXML
    private Label etiquetaNombres;
    @FXML
    private Label etiquetaTelefono;
    @FXML
    private TextField campoNombre;
    @FXML
    private TextField campoCorreoElectronico;
    @FXML
    private TextField campoTelefono;
    @FXML
    private TextField campoCantidadAPagar;
    @FXML
    private TextField campoApellidos;
    @FXML
    private JFXButton botonSeleccionarImagen;
    @FXML
    private JFXButton botonRegistrar;
    @FXML
    private JFXButton botonCancelar;
    @FXML
    private Label etiquetaCorreoElectronico;
    @FXML
    private Label etiquetaApellidos;
    @FXML
    private Label etiquetaCantidadAPagar;
    @FXML
    private Label etiquetaCuenta;
    @FXML
    private ImageView imagenPerfil;
    @FXML
    private Label etiquetaUsuario;
    @FXML
    private Label etiquetaContraseña;
    @FXML
    private TextField campoUsuario;
    @FXML
    private Label etiquetaDatosPersonales;
    @FXML
    private PasswordField campoContraseña;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void realizarRegistro(ActionEvent event) {
        CuentaDAO cuentaDAO = new CuentaDAO();
        

        if (verificarCamposVacios(campoNombre, campoApellidos, campoCorreoElectronico, campoTelefono, campoCantidadAPagar, campoUsuario, campoContraseña)) {
            System.out.println("Campos Vacios");
        } else if (verificarLongitudExcedida(campoNombre, campoApellidos, campoCorreoElectronico, campoTelefono, campoUsuario)) {
            System.out.println("Algún campo excede el límite de caracteres permitidos");
        } else if (Utileria.validarCorreo(campoCorreoElectronico.getText())) {
            if (!cuentaDAO.verificarNombreUsuarioRepetido(campoUsuario.getText())) {

                Maestro maestro = new Maestro();
                MaestroDAO maestroDAO = new MaestroDAO();
                Cuenta cuenta = new Cuenta();

                maestro.setNombre(campoNombre.getText());
                maestro.setApellidos(campoApellidos.getText());
                maestro.setCorreoElectronico(campoCorreoElectronico.getText());
                maestro.setTelefono(campoTelefono.getText());
                maestro.setEstaActivo(0);
                maestro.setFechaCorte(null);
                maestro.setRutaFoto(null);
                maestro.setMensualidad(Double.parseDouble(campoCantidadAPagar.getText()));
                maestro.setUsuario(campoUsuario.getText());
                
                if(maestroDAO.registrarMaestro(maestro)){
                    
                    System.out.println("El maestro ha sido creado exitosamente");
                    cuenta.setUsuario(campoUsuario.getText());
                    try {
                        cuenta.setContrasena(Utileria.cifrarContrasena(campoContraseña.getText()));
                    } catch (NoSuchAlgorithmException ex) {
                        Logger.getLogger(VentanaRegistrarMaestroController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    cuenta.setTipoCuenta("Maestro");
                    
                    //Invertir, primero cuenta y después registrar maestro
                    
                    
                }else{
                    System.out.println("Hubo un problema con el registro");
                }

            } else {
                System.out.println("El nombre de usuario que desea ya existe");
            }

        } else {
            System.out.println("El formato de correo no es válido");
        }

    }

    public boolean verificarCamposVacios(TextField campoNombre, TextField campoApellidos, TextField campoCorreoElectronico, TextField campoTelefono, TextField campoCantidadAPagar, TextField campoUsuario, PasswordField campoContraseña) {
        boolean camposVacios = false;
        if (campoNombre.getText().isEmpty() || campoApellidos.getText().isEmpty() || campoCorreoElectronico.getText().isEmpty() || campoTelefono.getText().isEmpty() || campoCantidadAPagar.getText().isEmpty() || campoUsuario.getText().isEmpty() || campoContraseña.getText().isEmpty()) {
            camposVacios = true;
        }
        return camposVacios;
    }

    public boolean verificarLongitudExcedida(TextField campoNombre, TextField campoApellidos, TextField campoCorreoElectronico, TextField campoTelefono, TextField campoUsuario) {
        boolean longitudExcedida = false;
        if (campoNombre.getText().length() > 30 || campoApellidos.getText().length() > 30
                || campoCorreoElectronico.getText().length() > 320 || campoTelefono.getText().length() > 10 || campoUsuario.getText().length() > 30) {
            longitudExcedida = true;
        }
        return longitudExcedida;
    }

}
