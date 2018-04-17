package controlador;

import com.jfoenix.controls.JFXButton;
import java.io.File;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.swing.JFileChooser;
import negocio.CuentaDAO;
import negocio.Maestro;
import negocio.MaestroDAO;
import negocio.Utileria;
import negocio.Cuenta;

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

    String rutaImagen = null;
    Maestro maestro = new Maestro();
    @FXML
    private AnchorPane panelPrincipal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void realizarRegistro(ActionEvent event) throws NoSuchAlgorithmException {
        boolean cantidadCorrecta = true;
        CuentaDAO cuentaDAO = new CuentaDAO();
        MaestroDAO maestroDAO = new MaestroDAO();
        Cuenta cuenta = new Cuenta();

        if (verificarCamposVacios(campoNombre, campoApellidos, campoCorreoElectronico, campoTelefono, campoCantidadAPagar, campoUsuario, campoContraseña)) {
            DialogosController.mostrarMensajeInformacion("Campo vacio", "Algún campo esta vacío", "Debe llenar todos los campos requeridos");
        } else if (verificarLongitudExcedida(campoNombre, campoApellidos, campoCorreoElectronico, campoTelefono, campoUsuario)) {
            DialogosController.mostrarMensajeInformacion("Campos excedidos", "Algún campo excede el límite de caracteres", "Revise el límite de caracteres permitidos");
        } else if (Utileria.validarCorreo(campoCorreoElectronico.getText())) {
            if (!cuentaDAO.verificarNombreUsuarioRepetido(campoUsuario.getText())) {

                try {
                    maestro.setMensualidad(Double.parseDouble(campoCantidadAPagar.getText()));
                } catch (NumberFormatException ex) {
                    DialogosController.mostrarMensajeInformacion("Dato incorrecto", "Las letras no son una cantidad", "Debe ingresar una cantidad numérica");
                    cantidadCorrecta = false;
                }

                if (cantidadCorrecta) {

                    cuenta.setTipoCuenta("Maestro");
                    cuenta.setUsuario(campoUsuario.getText());
                    cuenta.setContraseña(Utileria.cifrarContrasena(campoContraseña.getText()));
                    if (cuentaDAO.crearCuenta(cuenta)) {

                        maestro.setNombre(campoNombre.getText());
                        maestro.setApellidos(campoApellidos.getText());
                        maestro.setCorreoElectronico(campoCorreoElectronico.getText());
                        maestro.setTelefono(campoTelefono.getText());
                        maestro.setEstaActivo(0);
                        maestro.setFechaCorte(null);
                        maestro.setRutaFoto(rutaImagen);
                        maestro.setUsuario(campoUsuario.getText());

                        if (maestroDAO.registrarMaestro(maestro)) {
                            DialogosController.mostrarMensajeInformacion("Registro exitoso", "El maestro se ha registrado correctamente", "El maestro se ha registrado correctamente");
                            campoApellidos.clear();
                            campoCantidadAPagar.clear();
                            campoContraseña.clear();
                            campoCorreoElectronico.clear();
                            campoNombre.clear();
                            campoTelefono.clear();
                            campoUsuario.clear();
                            imagenPerfil.setImage(null);

                        } else {
                            DialogosController.mostrarMensajeInformacion("", "Registro no exitoso", "El maestro no se ha registrado correctamente");
                        }

                    } else {
                        DialogosController.mostrarMensajeInformacion("", "Registro no exitoso", "El maestro no se ha registrado correctamente");
                    }

                }

            } else {
                DialogosController.mostrarMensajeInformacion("Usuario existente", "El nombre de usuario elegido ya se encuentra en uso", "El nombre de usuario elegido ya se encuentra en uso");
            }

        } else {
            DialogosController.mostrarMensajeInformacion("", "Correo no válido", "El correo ingresado no tiene un formato válido");
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

    @FXML
    private String elegirImagen(ActionEvent event) throws IOException {
        FileChooser explorador = new FileChooser();
        explorador.getExtensionFilters().addAll(new ExtensionFilter("*.png", "*.jpg"));
        File archivoSeleccionado = explorador.showOpenDialog(null);

        if (archivoSeleccionado != null) {
            String rutaOrigen = archivoSeleccionado.getAbsolutePath();
            String nombreArchivo = archivoSeleccionado.getName();
            //String rutaNueva = "C:\\Users\\irdev\\OneDrive\\Documentos\\GitHub\\Repositorio-Desarrollo-de-Software\\AredEspacio\\src\\imagenesMaestros";
            String rutaNueva = "C:\\Users\\iro19\\Documents\\GitHub\\Repositorio-Desarrollo-de-Software\\AredEspacio\\src\\imagenesAlumnos";
            StringBuilder comando = new StringBuilder();
            comando.append("copy ").append('"' + rutaOrigen + '"').append(" ").append('"' + rutaNueva + '"');
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", comando.toString());
            builder.redirectErrorStream(true);
            Process process = builder.start();
            rutaImagen = nombreArchivo;
            maestro.setRutaFoto(rutaImagen);

            if (maestro.getRutaFoto() != null) {
                Image foto = new Image("imagenesMaestros/" + maestro.getRutaFoto(), 140, 140, false, true, true);
                imagenPerfil.setImage(foto);
            }

        }

        return rutaImagen;
    }

    @FXML
    private void desplegarVentanaBuscar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaBuscar.fxml"));
        Parent root = (Parent) loader.load();
        VentanaBuscarController ventanaBuscar = loader.getController();
        ventanaBuscar.obtenerSeccion("Maestros", panelPrincipal);
        panelPrincipal.getChildren().add(root);
    }
}
