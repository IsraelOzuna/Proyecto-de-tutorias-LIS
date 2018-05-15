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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
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

    Maestro maestro = new Maestro();
    @FXML
    private AnchorPane panelPrincipal;
    @FXML
    private Label etiquetaAdvertenciaNombre;
    @FXML
    private Label etiquetaAdvertenciaApellido;
    @FXML
    private Label etiquetaAdvertenciaCorreo;
    @FXML
    private Label etiquetaAdvertenciaTelefono;
    @FXML
    private Label etiquetaAdvertenciaCantidad;
    @FXML
    private Label etiquetaAdvertenciaUsuario;
    @FXML
    private Label etiquetaAdvertenciaContraseña;
    private String rutaOrigen;
    private String rutaNueva;
    private String rutaImagen;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rutaImagen = "";
    }

    @FXML
    private void realizarRegistro(ActionEvent event) throws NoSuchAlgorithmException, IOException {
        boolean cantidadCorrecta = true;
        CuentaDAO cuentaDAO = new CuentaDAO();
        MaestroDAO maestroDAO = new MaestroDAO();
        Cuenta cuenta = new Cuenta();

        if (!verificarCamposVacios(campoNombre, campoApellidos, campoCorreoElectronico, campoTelefono, campoCantidadAPagar, campoUsuario, campoContraseña)) {

            if (!verificarLongitudExcedida(campoNombre, campoApellidos, campoCorreoElectronico, campoTelefono, campoUsuario)) {
                if (Utileria.validarCorreo(campoCorreoElectronico.getText())) {

                    try {
                        maestro.setMensualidad(Double.parseDouble(campoCantidadAPagar.getText()));
                    } catch (NumberFormatException ex) {
                        DialogosController.mostrarMensajeInformacion("Dato incorrecto", "Las letras no son una cantidad", "Debe ingresar una cantidad numérica");
                        cantidadCorrecta = false;
                    }

                    if (cantidadCorrecta) {

                        if (!cuentaDAO.verificarNombreUsuarioRepetido(campoUsuario.getText())) {

                            StringBuilder comando = new StringBuilder();
                            comando.append("copy ").append('"' + rutaOrigen + '"').append(" ").append('"' + rutaNueva + '"');
                            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", comando.toString());
                            builder.redirectErrorStream(true);
                            Process process = builder.start();

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

                                }
                            } else {
                                DialogosController.mostrarMensajeInformacion("", "Registro no exitoso", "El maestro no se ha registrado correctamente");
                            }

                        } else {
                            DialogosController.mostrarMensajeInformacion("Usuario existente", "El nombre de usuario elegido ya se encuentra en uso", "El nombre de usuario elegido ya se encuentra en uso");
                        }
                    }

                } else {
                    DialogosController.mostrarMensajeInformacion("", "Correo no válido", "El correo ingresado no tiene un formato válido");
                }
            }
        }
    }

    public boolean verificarCamposVacios(TextField campoNombre, TextField campoApellidos, TextField campoCorreoElectronico, TextField campoTelefono, TextField campoCantidadAPagar, TextField campoUsuario, PasswordField campoContraseña) {
        boolean camposVacios = false;

        if (campoNombre.getText().trim().isEmpty()) {
            mandarAdvertencia(etiquetaAdvertenciaNombre);
            camposVacios = true;
        } else {
            desactivarAdvertencia(etiquetaAdvertenciaNombre);
        }

        if (campoApellidos.getText().trim().isEmpty()) {
            mandarAdvertencia(etiquetaAdvertenciaApellido);
            camposVacios = true;
        } else {
            desactivarAdvertencia(etiquetaAdvertenciaApellido);
        }

        if (campoCorreoElectronico.getText().trim().isEmpty()) {
            mandarAdvertencia(etiquetaAdvertenciaCorreo);
            camposVacios = true;
        } else {
            desactivarAdvertencia(etiquetaAdvertenciaCorreo);
        }

        if (campoTelefono.getText().trim().isEmpty()) {
            mandarAdvertencia(etiquetaAdvertenciaTelefono);
            camposVacios = true;
        } else {
            desactivarAdvertencia(etiquetaAdvertenciaTelefono);
        }

        if (campoCantidadAPagar.getText().trim().isEmpty()) {
            mandarAdvertencia(etiquetaAdvertenciaCantidad);
            camposVacios = true;
        } else {
            desactivarAdvertencia(etiquetaAdvertenciaCantidad);
        }

        if (campoUsuario.getText().trim().isEmpty()) {
            mandarAdvertencia(etiquetaAdvertenciaUsuario);
            camposVacios = true;
        } else {
            desactivarAdvertencia(etiquetaAdvertenciaUsuario);
        }

        if (campoContraseña.getText().trim().isEmpty()) {
            mandarAdvertencia(etiquetaAdvertenciaContraseña);
            camposVacios = true;
        } else {
            desactivarAdvertencia(etiquetaAdvertenciaContraseña);
        }

        if (camposVacios) {
            DialogosController.mostrarMensajeInformacion("Campo vacio", "Algún campo esta vacío", "Debe llenar todos los campos requeridos");
        }

        return camposVacios;
    }

    public boolean verificarLongitudExcedida(TextField campoNombre, TextField campoApellidos, TextField campoCorreoElectronico, TextField campoTelefono, TextField campoUsuario) {
        boolean longitudExcedida = false;

        if (campoNombre.getText().length() > 30) {
            mandarAdvertencia(etiquetaAdvertenciaNombre);
            longitudExcedida = true;
        } else {
            desactivarAdvertencia(etiquetaAdvertenciaNombre);
        }

        if (campoApellidos.getText().length() > 30) {
            mandarAdvertencia(etiquetaAdvertenciaApellido);
            longitudExcedida = true;
        } else {
            desactivarAdvertencia(etiquetaAdvertenciaApellido);
        }

        if (campoCorreoElectronico.getText().length() > 320) {
            mandarAdvertencia(etiquetaAdvertenciaCorreo);
            longitudExcedida = true;
        } else {
            desactivarAdvertencia(etiquetaAdvertenciaCorreo);
        }

        if (campoTelefono.getText().length() > 10) {
            mandarAdvertencia(etiquetaAdvertenciaTelefono);
            longitudExcedida = true;
        } else {
            desactivarAdvertencia(etiquetaAdvertenciaTelefono);
        }

        if (campoCantidadAPagar.getText().length() > 8) {
            mandarAdvertencia(etiquetaAdvertenciaCantidad);
            longitudExcedida = true;
        } else {
            desactivarAdvertencia(etiquetaAdvertenciaCantidad);
        }

        if (campoUsuario.getText().length() > 50) {
            mandarAdvertencia(etiquetaAdvertenciaUsuario);
            longitudExcedida = true;
        } else {
            desactivarAdvertencia(etiquetaAdvertenciaUsuario);
        }

        if (longitudExcedida) {
            DialogosController.mostrarMensajeInformacion("Campos excedidos", "Algún campo excede el límite de caracteres", "Revise el límite de caracteres permitidos");
        }

        return longitudExcedida;
    }

    @FXML
    private String elegirImagen(ActionEvent event) throws IOException {
        FileChooser explorador = new FileChooser();
        explorador.getExtensionFilters().addAll(new ExtensionFilter("*.png", "*.jpg"));
        File archivoSeleccionado = explorador.showOpenDialog(null);

        if (archivoSeleccionado != null) {

            rutaOrigen = archivoSeleccionado.getAbsolutePath();
            rutaImagen = archivoSeleccionado.getName();
            rutaNueva = System.getProperty("user.dir") + "\\imagenesMaestros";

            if (!rutaImagen.equals("")) {
                Image foto = new Image("file:" + rutaOrigen, 140, 140, false, true, true);
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

    public void mandarAdvertencia(Label etiqueta) {
        etiqueta.setText("*");
    }

    public void desactivarAdvertencia(Label etiqueta) {
        etiqueta.setText("");
    }

    public void limitarCaracteres(KeyEvent event, TextField campo, int caracteresMaximos) {
        if (campo.getText().length() >= caracteresMaximos) {
            event.consume();
        }
    }

    @FXML
    private void limitarCaracteresUsuario(KeyEvent event) {
        limitarCaracteres(event, campoUsuario, 50);
    }

    @FXML
    private void limitarCaracteresNombre(KeyEvent event) {
        limitarCaracteres(event, campoNombre, 30);
    }

    @FXML
    private void limitarCaracteresApellido(KeyEvent event) {
        limitarCaracteres(event, campoApellidos, 30);
    }

    @FXML
    private void limitarCaracteresCorreo(KeyEvent event) {
        limitarCaracteres(event, campoCorreoElectronico, 320);
    }

    @FXML
    private void limitarCaracteresTelefono(KeyEvent event) {
        limitarCaracteres(event, campoTelefono, 10);
    }

    @FXML
    private void limitarCaracteresCantidad(KeyEvent event) {
        limitarCaracteres(event, campoCantidadAPagar, 8);
    }

}
