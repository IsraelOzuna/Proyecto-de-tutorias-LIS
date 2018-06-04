package controlador;

import com.jfoenix.controls.JFXButton;
import java.io.File;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.security.CodeSource;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import negocio.CuentaDAO;
import negocio.Maestro;
import negocio.MaestroDAO;
import negocio.Utileria;
import negocio.Cuenta;

/**
 * Este controlador es usado para manipular la interfaz gráfica al registrar a
 * un maestro
 *
 * @author Irvin Vera
 * @version 1.0 / 5 de junio de 2018
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
    private String nombreUsuarioActual;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rutaImagen = "";
    }

    /**
     * Este método obtiene el usuario en el cambio de ventanas
     *
     * @param nombreUsuario
     */
    public void llenarDatos(String nombreUsuario) {
        nombreUsuarioActual = nombreUsuario;
    }

    @FXML
    private void realizarRegistro(ActionEvent event) throws NoSuchAlgorithmException, IOException {
        boolean cantidadCorrecta = true;
        CuentaDAO cuentaDAO = new CuentaDAO();
        MaestroDAO maestroDAO = new MaestroDAO();
        Cuenta cuenta = new Cuenta();

        if (!verificarCamposVacios(campoNombre, campoApellidos, campoCorreoElectronico, campoTelefono, campoCantidadAPagar, campoUsuario, campoContraseña)) {

            if (!verificarLongitudExcedida(campoNombre, campoApellidos, campoCorreoElectronico, campoTelefono, campoUsuario)) {
                if (Utileria.validarCorreo(campoCorreoElectronico.getText().trim())) {

                    try {
                        maestro.setMensualidad(Double.parseDouble(campoCantidadAPagar.getText()));
                    } catch (NumberFormatException ex) {
                        DialogosController.mostrarMensajeInformacion("Dato incorrecto", "Las letras no son una cantidad", "Debe ingresar una cantidad numérica");
                        cantidadCorrecta = false;
                    }

                    if (cantidadCorrecta) {

                        if (esTelefonoValido(campoTelefono.getText())) {

                            if (!cuentaDAO.verificarNombreUsuarioRepetido(campoUsuario.getText().trim())) {

                                cuenta.setTipoCuenta("Maestro");
                                cuenta.setUsuario(campoUsuario.getText().trim());
                                cuenta.setContraseña(Utileria.cifrarContrasena(campoContraseña.getText()));
                                if (cuentaDAO.crearCuenta(cuenta)) {

                                    maestro.setNombre(campoNombre.getText().trim());
                                    maestro.setApellidos(campoApellidos.getText().trim());
                                    maestro.setCorreoElectronico(campoCorreoElectronico.getText().trim());
                                    maestro.setTelefono(campoTelefono.getText().trim());
                                    maestro.setEstaActivo(0);
                                    maestro.setFechaCorte(null);
                                    maestro.setRutaFoto(rutaImagen);
                                    maestro.setUsuario(campoUsuario.getText().trim());

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
                    }

                } else {
                    DialogosController.mostrarMensajeInformacion("", "Correo no válido", "El correo ingresado no tiene un formato válido");
                }
            }
        }
    }

    /**
     * Este método permite verificar si alguno de los campos esta vacio
     *
     * @param campoNombre campo donde el usuario ingresa el nombre del nuevo
     * maestro
     * @param campoApellidos campo donde el usuario ingresa los apellidos del
     * nuevo maestro
     * @param campoCorreoElectronico campo donde el usuario ingresa el correo
     * electrónico del nuevo maestro
     * @param campoTelefono campo donde el usuario ingresa el teléfono del nuevo
     * maestro
     * @param campoCantidadAPagar campo donde el usuario ingresa lo que el nuevo
     * maestro deberá pagar mensualmente
     * @param campoUsuario campo donde el usuario ingresa el identificador único
     * del nuevo maestro
     * @param campoContraseña campo donde el usuario ingresa la contraseña de la
     * cuenta del nuevo maestro
     * @return un boolean que indica si el alguno de los campos está vacío
     */
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

    /**
     * Este método verifica si alguno de los capos excede el límite permitido
     *
     * @param campoNombre campo donde el usuario ingresa el nombre del nuevo
     * maestro
     * @param campoApellidos campo donde el usuario ingresa los apellidos del
     * nuevo maestro
     * @param campoCorreoElectronico campo donde el usuario ingresa el correo
     * electrónico del nuevo maestro
     * @param campoTelefono campo donde el usuario ingresa el teléfono del nuevo
     * maestro
     * @param campoUsuario campo donde el usuario ingresa el identificador único
     * del nuevo maestro
     * @return boolean que indica si uno de los campos excede la logitud
     * permitida
     */
    public boolean verificarLongitudExcedida(TextField campoNombre, TextField campoApellidos, TextField campoCorreoElectronico, TextField campoTelefono, TextField campoUsuario) {
        boolean longitudExcedida = false;

        if (campoNombre.getText().trim().length() > 30) {
            mandarAdvertencia(etiquetaAdvertenciaNombre);
            longitudExcedida = true;
        } else {
            desactivarAdvertencia(etiquetaAdvertenciaNombre);
        }

        if (campoApellidos.getText().trim().length() > 30) {
            mandarAdvertencia(etiquetaAdvertenciaApellido);
            longitudExcedida = true;
        } else {
            desactivarAdvertencia(etiquetaAdvertenciaApellido);
        }

        if (campoCorreoElectronico.getText().trim().length() > 320) {
            mandarAdvertencia(etiquetaAdvertenciaCorreo);
            longitudExcedida = true;
        } else {
            desactivarAdvertencia(etiquetaAdvertenciaCorreo);
        }

        if (campoTelefono.getText().trim().length() > 10) {
            mandarAdvertencia(etiquetaAdvertenciaTelefono);
            longitudExcedida = true;
        } else {
            desactivarAdvertencia(etiquetaAdvertenciaTelefono);
        }

        if (campoCantidadAPagar.getText().trim().length() > 8) {
            mandarAdvertencia(etiquetaAdvertenciaCantidad);
            longitudExcedida = true;
        } else {
            desactivarAdvertencia(etiquetaAdvertenciaCantidad);
        }

        if (campoUsuario.getText().trim().length() > 50) {
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
        File fileJar;
        File fileDir = null;
        File directorio = null;
        FileChooser explorador = new FileChooser();
        explorador.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("*.png", "*.jpg"));
        File archivoSeleccionado = explorador.showOpenDialog(null);

        try {
            CodeSource direccion = VentanaRegistrarMaestroController.class.getProtectionDomain().getCodeSource();
            fileJar = new File(direccion.getLocation().toURI().getPath());
            fileDir = fileJar.getParentFile();
            directorio = new File(fileDir.getAbsolutePath() + "/imagenesMaestros/");
        } catch (URISyntaxException ex) {
            Logger.getLogger(VentanaConsultarInformacionGrupoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (archivoSeleccionado != null) {
            rutaOrigen = archivoSeleccionado.getAbsolutePath();
            rutaImagen = archivoSeleccionado.getName();

            if (!directorio.exists()) {
                directorio.mkdir();
            }

            rutaNueva = directorio.getAbsolutePath();
            directorio = new File(fileDir.getAbsolutePath() + "/imagenesMaestros/" + rutaImagen);

            if (!rutaImagen.equals("")) {
                Image foto = new Image("file:" + rutaOrigen, 140, 140, false, true, true);
                imagenPerfil.setImage(foto);
            }
            try {
                if (!directorio.exists()) {
                    Files.copy(archivoSeleccionado.toPath(), directorio.toPath());
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return rutaImagen;
    }

    @FXML
    private void desplegarVentanaBuscar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaBuscar.fxml"));
        Parent root = (Parent) loader.load();
        VentanaBuscarController ventanaBuscar = loader.getController();
        ventanaBuscar.obtenerSeccion("Maestros", panelPrincipal, nombreUsuarioActual);
        panelPrincipal.getChildren().add(root);
    }

    /**
     * Este método muestra una señalización a una etiqueta como advertencia
     *
     * @param etiqueta a la que se desea hacer referencia
     */
    public void mandarAdvertencia(Label etiqueta) {
        etiqueta.setText("*");
    }

    /**
     * Este método quita la señalización a una etiqueta como advertencia
     *
     * @param etiqueta a la que se desea hacer referencia
     */
    public void desactivarAdvertencia(Label etiqueta) {
        etiqueta.setText("");
    }

    /**
     * Este método permite limitar los caracteres permitidos en un campo de
     * texto
     *
     * @param event Ingreso de un valor del usuario
     * @param campo que se desea limitar
     * @param caracteresMaximos numero de caracteres permitidos en ese campo
     */
    public void limitarCaracteres(KeyEvent event, TextField campo, int caracteresMaximos) {
        if (campo.getText().trim().length() >= caracteresMaximos) {
            event.consume();
        }
    }

    @FXML
    private void limitarCaracteresUsuario(KeyEvent event) {
        limitarCaracteres(event, campoUsuario, 50);
    }

    @FXML
    private void limitarCaracteresNombre(KeyEvent event) {
        char caracter = event.getCharacter().charAt(0);
        limitarCaracteres(event, campoNombre, 30);
        if (!(Character.isLetter(caracter) || Character.isSpaceChar(caracter))) {
            event.consume();
        }
    }

    @FXML
    private void limitarCaracteresApellido(KeyEvent event) {
        char caracter = event.getCharacter().charAt(0);
        limitarCaracteres(event, campoApellidos, 30);
        if (!(Character.isLetter(caracter) || Character.isSpaceChar(caracter))) {
            event.consume();
        }
    }

    @FXML
    private void limitarCaracteresCorreo(KeyEvent event) {
        char caracter = event.getCharacter().charAt(0);
        limitarCaracteres(event, campoCorreoElectronico, 320);
        if (Character.isSpaceChar(caracter)) {
            event.consume();
        }
    }

    @FXML
    private void limitarCaracteresTelefono(KeyEvent event) {
        limitarCaracteres(event, campoTelefono, 10);
        char caracter = event.getCharacter().charAt(0);
        if (!Character.isDigit(caracter)) {
            event.consume();
        }
    }

    /**
     * Este método corrobora si el telefono ingresado tiene el formato correcto
     *
     * @param telefono ingresado por el usuario
     * @return boolean indica si el formato del telefono es correcto
     */
    private boolean esTelefonoValido(String telefono) {
        boolean telefonoValido = true;
        for (int i = 0; i < telefono.length(); i++) {
            char caracter = telefono.charAt(i);
            if (!Character.isDigit(caracter)) {
                telefonoValido = false;
            }
        }

        if (!telefonoValido) {
            DialogosController.mostrarMensajeInformacion("", "Telefono incorrecto", "El número de teléfono no puede contener letras");
        }

        return telefonoValido;
    }

    @FXML
    private void limitarCaracteresCantidad(KeyEvent event) {
        char caracter = event.getCharacter().charAt(0);
        limitarCaracteres(event, campoCantidadAPagar, 5);
        if (!Character.isDigit(caracter)) {
            event.consume();
        }
    }

}
