package controlador;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.security.CodeSource;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import negocio.MaestroDAO;
import negocio.Utileria;
import persistencia.Maestro;

/**
 * Este controlador es usado para manipular la interfaz gráfica al editar la
 * información de un maestro
 *
 * @author Irvin Vera
 * @version 1.0 / 5 de junio de 2018
 */
public class VentanaEditarInformacionMaestroController implements Initializable {

    @FXML
    private Label etiquetaNombres;
    @FXML
    private TextField campoNombre;
    @FXML
    private Label etiquetaApellidos;
    @FXML
    private TextField campoApellidos;
    @FXML
    private Label etiquetaCorreoElectronico;
    @FXML
    private TextField campoCorreoElectronico;
    @FXML
    private Label etiquetaTelefono;
    @FXML
    private TextField campoTelefono;
    @FXML
    private Label etiquetaCantidadAPagar;
    @FXML
    private TextField campoCantidadAPagar;
    @FXML
    private Label etiquetaDatosPersonales;
    @FXML
    private ImageView imagenPerfil;
    @FXML
    private JFXButton botonSeleccionarImagen;
    @FXML
    private JFXButton botonCancelar;
    @FXML
    private JFXButton botonRegistrar;

    private Maestro maestro;

    private Pane panelPrincipal;
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
    private String rutaOrigen;
    private String rutaNueva;
    private String nombreFoto;
    private String nombreUsuarioActual;

    /**
     * Este método permite obtener los datos del maestro que se desea modificar
     *
     * @param maestro contiene los dato del maestro
     * @param nombreUsuario identificador único de las pesona que está ocupado
     * el sistema
     */
    public void obtenerMaestro(Maestro maestro, String nombreUsuario) {
        this.maestro = maestro;

        llenarCamposInformacion(nombreUsuario);
    }

    /**
     * Este método permite obtener como referencia el panel de la pantalla
     * anterior en donde se mostrara la ventana de editar maestro
     *
     * @param panelPrincipal sobre el que se mostrarán la ventana de editar
     * renta
     *
     */
    public void obtenerPanel(Pane panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nombreFoto = "";

    }

    @FXML
    private void cerrarVentanaEditarInformacionMaestro(ActionEvent event) throws IOException {
        desplegarVentanaBusqueda();
    }

    @FXML
    private void guardarInformacionMaestroEditada(ActionEvent event) throws IOException {
        boolean cantidadCorrecta = true;
        if (!existenCamposVacios(campoNombre, campoApellidos, campoCorreoElectronico, campoTelefono, campoCantidadAPagar)) {
            if (!verificarLongitudExcedida(campoNombre, campoApellidos, campoCorreoElectronico, campoTelefono, campoCantidadAPagar)) {
                if (Utileria.validarCorreo(campoCorreoElectronico.getText().trim())) {
                    Maestro maestroNuevo = new Maestro();
                    try {
                        maestroNuevo.setMensualidad(Double.parseDouble(campoCantidadAPagar.getText()));
                    } catch (NumberFormatException ex) {
                        cantidadCorrecta = false;
                        DialogosController.mostrarMensajeInformacion("Dato incorrecto", "Las letras no son una cantidad", "Debe ingresar una cantidad numérica");
                    }
                    if (cantidadCorrecta) {

                        if (esTelefonoValido(campoTelefono.getText())) {
                            MaestroDAO maestroDAO = new MaestroDAO();

                            maestroNuevo.setNombre(campoNombre.getText().trim());
                            maestroNuevo.setApellidos(campoApellidos.getText().trim());
                            maestroNuevo.setCorreoElectronico(campoCorreoElectronico.getText().trim());
                            maestroNuevo.setTelefono(campoTelefono.getText().trim());
                            maestroNuevo.setRutaFoto(nombreFoto);
                            maestroNuevo.setEstaActivo(maestro.getEstaActivo());
                            maestroNuevo.setFechaCorte(maestro.getFechaCorte());
                            maestroNuevo.setUsuario(maestro.getUsuario());

                            if (maestroDAO.editarMaestro(maestroNuevo)) {
                                DialogosController.mostrarMensajeInformacion("Guardado", "Maestro modificado", "El maestro ha sido modificado exitosamente");
                                desplegarVentanaBusqueda();
                            } else {
                                DialogosController.mostrarMensajeAdvertencia("Error", "Error al modificar", "Ha ocurrido un error. No se pudo modificar");
                            }
                        }
                    }
                } else {
                    DialogosController.mostrarMensajeInformacion("", "Correo no válido", "El correo ingresado no tiene un formato válido");
                }
            }

        }
    }

    @FXML
    public String seleccionarImagen(ActionEvent event) throws IOException {
        File fileJar;
        File fileDir = null;
        File directorio = null;
        FileChooser explorador = new FileChooser();
        explorador.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("*.png", "*.jpg"));
        File archivoSeleccionado = explorador.showOpenDialog(null);

        try {
            CodeSource direccion = VentanaEditarInformacionMaestroController.class.getProtectionDomain().getCodeSource();
            fileJar = new File(direccion.getLocation().toURI().getPath());
            fileDir = fileJar.getParentFile();
            directorio = new File(fileDir.getAbsolutePath() + "/imagenesMaestros/");
        } catch (URISyntaxException ex) {
            Logger.getLogger(VentanaConsultarInformacionGrupoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (archivoSeleccionado != null) {
            rutaOrigen = archivoSeleccionado.getAbsolutePath();
            nombreFoto = archivoSeleccionado.getName();

            if (!directorio.exists()) {
                directorio.mkdir();
            }

            rutaNueva = directorio.getAbsolutePath();
            directorio = new File(fileDir.getAbsolutePath() + "/imagenesMaestros/" + nombreFoto);

            if (!nombreFoto.equals("")) {
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

        return nombreFoto;
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
     * @param campoCantidadAPagar campo donde el usuario ingresa la cantidad que
     * el maestro pagará mensualmente
     *
     * @return boolean que indica si uno de los campos excede la logitud
     * permitida
     */
    public boolean verificarLongitudExcedida(TextField campoNombre, TextField campoApellidos, TextField campoCorreoElectronico, TextField campoTelefono, TextField campoCantidadAPagar) {
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

        if (longitudExcedida) {
            DialogosController.mostrarMensajeInformacion("Campos excedidos", "Algún campo excede el límite de caracteres", "Revise el límite de caracteres permitidos");
        }

        return longitudExcedida;
    }

    /**
     * Este método permite verificar si alguno de los campos esta vacio
     *
     * @param campoNombre campo donde el usuario ingresa el nombre del nuevo
     * maestro
     * @param campoApellidos campo donde el usuario ingresa los apellidos del
     * nuevo maestro
     * @param campoCorreo campo donde el usuario ingresa el correo electrónico
     * del nuevo maestro
     * @param campoTelefono campo donde el usuario ingresa el teléfono del nuevo
     * maestro
     * @param campoCantidadAPagar campo donde el usuario ingresa lo que el nuevo
     * maestro deberá pagar mensualmente
     *
     * @return un boolean que indica si el alguno de los campos está vacío
     */
    public boolean existenCamposVacios(TextField campoNombre, TextField campoApellidos, TextField campoCorreo, TextField campoTelefono, TextField campoCantidadAPagar) {
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

        if (campoCorreo.getText().trim().isEmpty()) {
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

        if (camposVacios) {
            DialogosController.mostrarMensajeInformacion("Campo vacio", "Algún campo esta vacío", "Debe llenar todos los campos requeridos");
        }

        return camposVacios;
    }

    /**
     * Este método carga los datos del maestro antes de modificarlos
     *
     * @param nombreUsuario identificador único del maestro que se modificará
     */
    public void llenarCamposInformacion(String nombreUsuario) {
        try {
            nombreUsuarioActual = nombreUsuario;
            campoNombre.setText(maestro.getNombre());
            campoApellidos.setText(maestro.getApellidos());
            campoCorreoElectronico.setText(maestro.getCorreoElectronico());
            campoTelefono.setText(maestro.getTelefono());
            campoCantidadAPagar.setText(String.valueOf(maestro.getMensualidad()));

            CodeSource direccion = VentanaEditarInformacionMaestroController.class.getProtectionDomain().getCodeSource();
            File fileJar = new File(direccion.getLocation().toURI().getPath());
            File fileDir = fileJar.getParentFile();
            File fileProperties = new File(fileDir.getAbsolutePath());

            String rutaFoto = fileProperties.getAbsolutePath();

            if (maestro.getRutaFoto() != null) {
                nombreFoto = maestro.getRutaFoto();
                Image foto = new Image("file:" + rutaFoto + "/imagenesMaestros/" + maestro.getRutaFoto(), 100, 100, false, true, true);
                imagenPerfil.setImage(foto);
            }
        } catch (URISyntaxException ex) {
            Logger.getLogger(VentanaEditarInformacionMaestroController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Este método permite regresar a la ventana de búsqueda
     *
     */
    public void desplegarVentanaBusqueda() throws IOException {
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

    @FXML
    private void limitarCaracteresCantidad(KeyEvent event) {
        char caracter = event.getCharacter().charAt(0);
        limitarCaracteres(event, campoCantidadAPagar, 8);
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

}
