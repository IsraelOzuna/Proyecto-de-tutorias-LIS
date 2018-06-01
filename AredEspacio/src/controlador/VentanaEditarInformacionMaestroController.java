package controlador;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;
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

    public void obtenerMaestro(Maestro maestro, String nombreUsuario) {
        this.maestro = maestro;

        llenarCamposInformacion(nombreUsuario);

    }

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
        FileChooser explorador = new FileChooser();
        explorador.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("*.png", "*.jpg"));
        File archivoSeleccionado = explorador.showOpenDialog(null);
        File directorio = new File(System.getProperty("user.dir") + "/imagenesMaestros");

        if (archivoSeleccionado != null) {
            rutaOrigen = archivoSeleccionado.getAbsolutePath();
            nombreFoto = archivoSeleccionado.getName();

            if (!directorio.exists()) {
                directorio.mkdir();
            }
            rutaNueva = System.getProperty("user.dir") + "/imagenesMaestros";
        }

        if (nombreFoto != null) {
            Image foto = new Image("file:" + rutaOrigen, 140, 140, false, true, true);
            imagenPerfil.setImage(foto);
        }

        directorio = new File(System.getProperty("user.dir") + "/imagenesMaestros/" + nombreFoto);

        try {
            if (!directorio.exists()) {
                Files.copy(archivoSeleccionado.toPath(), directorio.toPath());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return nombreFoto;
    }

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

    public void llenarCamposInformacion(String nombreUsuario) {
        nombreUsuarioActual = nombreUsuario;
        campoNombre.setText(maestro.getNombre());
        campoApellidos.setText(maestro.getApellidos());
        campoCorreoElectronico.setText(maestro.getCorreoElectronico());
        campoTelefono.setText(maestro.getTelefono());
        campoCantidadAPagar.setText(String.valueOf(maestro.getMensualidad()));

        if (maestro.getRutaFoto() != null) {
            nombreFoto = maestro.getRutaFoto();
            Image foto = new Image("file:" + System.getProperty("user.dir") + "/imagenesMaestros/" + maestro.getRutaFoto(), 100, 100, false, true, true);
            imagenPerfil.setImage(foto);
        }

    }

    public void desplegarVentanaBusqueda() throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaBuscar.fxml"));
        Parent root = (Parent) loader.load();
        VentanaBuscarController ventanaBuscar = loader.getController();
        ventanaBuscar.obtenerSeccion("Maestros", panelPrincipal, nombreUsuarioActual);
        panelPrincipal.getChildren().add(root);
    }

    public void mandarAdvertencia(Label etiqueta) {
        etiqueta.setText("*");
    }

    public void desactivarAdvertencia(Label etiqueta) {
        etiqueta.setText("");
    }

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
