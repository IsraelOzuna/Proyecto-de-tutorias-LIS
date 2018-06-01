package controlador;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.Initializable;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import persistencia.Cliente;
import negocio.ClienteDAO;
import negocio.Utileria;

/**
 *
 * @author iro19
 */
public class VentanaEditarClienteController implements Initializable {

    @FXML
    private AnchorPane panelPrincipal;
    @FXML
    private ImageView fotoSeleccionada;
    @FXML
    private JFXButton botonSeleccionarImagen;
    @FXML
    private Label etiquetaNombre;
    @FXML
    private TextField campoNombre;
    @FXML
    private Label etiquetaApellidos;
    @FXML
    private TextField campoApellidos;
    @FXML
    private Label etiquetaCorreo;
    @FXML
    private TextField campoCorreo;
    @FXML
    private Label etiquetaTelefono;
    @FXML
    private TextField campoTelefono;
    @FXML
    private JFXButton botonRegistrar;
    @FXML
    private JFXButton botonCancelar;
    @FXML
    private Label etiquetaErrorNombre;
    @FXML
    private Label etiquetaErrorApellidos;
    @FXML
    private Label etiquetaErrorCorreo;
    @FXML
    private Label etiquetaErrorTelefono;

    private String nombreFoto;
    private Cliente cliente;
    private String rutaOrigen;
    private String rutaNueva;
    private String nombreUsuarioActual;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nombreFoto = "";
    }

    @FXML
    private void editarCliente(ActionEvent event) throws IOException {
        limpiarEtiquetas();
        if (!existenCamposVacios(campoNombre, campoApellidos, campoCorreo, campoTelefono)) {
            if (!existenCamposExcedidos(campoNombre, campoApellidos, campoCorreo, campoTelefono)) {
                if (Utileria.validarCorreo(campoCorreo.getText().trim())) {
                    if (esTelefonoValido(campoTelefono.getText().trim())) {
                       ClienteDAO nuevosDatosCliente = new ClienteDAO();

                        cliente.setNombre(campoNombre.getText().trim());
                        cliente.setApellidos(campoApellidos.getText().trim());
                        cliente.setCorreo(campoCorreo.getText().trim());
                        cliente.setTelefono(campoTelefono.getText().trim());
                        cliente.setRutaFoto(nombreFoto);

                        if (nuevosDatosCliente.editarCliente(cliente)) {
                            DialogosController.mostrarMensajeInformacion("Guardado", "Cliente Modificado", "El cliente ha sido modificado exitosamente");
                            desplegarVentanaBusquedaCliente();
                        } else {
                            DialogosController.mostrarMensajeAdvertencia("Error", "Error al modificar", "Ha ocurrido un error. No se pudo modificar");
                        }
                    }
                } else {
                    etiquetaErrorCorreo.setText("Formato de correo no valido");
                }
            }
        }
    }

    @FXML
    private String seleccionarImagen(ActionEvent event) throws IOException {
        FileChooser explorador = new FileChooser();
        explorador.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("*.png", "*.jpg"));
        File archivoSeleccionado = explorador.showOpenDialog(null);
        File directorio = new File(System.getProperty("user.dir") + "/imagenesClientes");

        if (archivoSeleccionado != null) {
            rutaOrigen = archivoSeleccionado.getAbsolutePath();
            nombreFoto = archivoSeleccionado.getName();

            if (!directorio.exists()) {
                directorio.mkdir();
            }

            rutaNueva = System.getProperty("user.dir") + "/imagenesClientes";
            directorio = new File(System.getProperty("user.dir") + "/imagenesClientes/" + nombreFoto);

            if (!nombreFoto.equals("")) {
                Image foto = new Image("file:" + rutaOrigen, 140, 140, false, true, true);
                fotoSeleccionada.setImage(foto);
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

    @FXML
    private void limitarNombre(KeyEvent event) {
        char caracter = event.getCharacter().charAt(0);
        if (campoNombre.getText().trim().length() >= 30 || !(Character.isLetter(caracter) || Character.isSpaceChar(caracter))) {
            event.consume();
        }
    }

    @FXML
    private void limitarApellidos(KeyEvent event) {
        char caracter = event.getCharacter().charAt(0);
        if (campoApellidos.getText().trim().length() >= 30 || !(Character.isLetter(caracter) || Character.isSpaceChar(caracter))) {
            event.consume();
        }
    }

    @FXML
    private void limitarCorreo(KeyEvent event) {
        if (campoCorreo.getText().trim().length() >= 320) {
            event.consume();
        }
    }

    @FXML
    private void limitarTelefono(KeyEvent event) {
        char caracter = event.getCharacter().charAt(0);

        if (campoTelefono.getText().trim().length() >= 10 || !Character.isDigit(caracter)) {
            event.consume();
        }
    }

    @FXML
    private void cancelarRegistro(ActionEvent event) throws IOException {
        if (existenCamposVacios(campoNombre, campoApellidos, campoCorreo, campoTelefono)) {
            desplegarVentanaBusquedaCliente();
        } else {
            if (DialogosController.mostrarMensajeCambios("Salir", "La información se perderá", "La información en los campos que ingresó se perderá")) {
                desplegarVentanaBusquedaCliente();
            }
        }
    }

    public boolean existenCamposVacios(TextField campoNombre, TextField campoApellidos, TextField campoCorreo, TextField campoTelefono) {
        boolean camposVacios = false;

        if (campoNombre.getText().trim().isEmpty()) {
            camposVacios = true;
            etiquetaErrorNombre.setText("Campo obligatorio");
        }
        if (campoApellidos.getText().trim().isEmpty()) {
            camposVacios = true;
            etiquetaErrorApellidos.setText("Campo obligatorio");
        }
        if (campoCorreo.getText().trim().isEmpty()) {
            camposVacios = true;
            etiquetaErrorCorreo.setText("Campo obligatorio");
        }
        if (campoTelefono.getText().trim().isEmpty()) {
            camposVacios = true;
            etiquetaErrorTelefono.setText("Campo obligatorio");
        }
        return camposVacios;
    }

    public boolean existenCamposExcedidos(TextField campoNombre, TextField campoApellidos, TextField campoCorreo, TextField campoTelefono) {
        boolean campoExcedido = false;

        if (campoNombre.getText().trim().length() > 30) {
            campoExcedido = true;
            etiquetaErrorNombre.setText("No puede contener más de 30 caracteres");
        }
        if (campoApellidos.getText().trim().length() > 30) {
            campoExcedido = true;
            etiquetaErrorApellidos.setText("No puede contener más de 30 caracteres");
        }
        if (campoCorreo.getText().trim().length() > 320) {
            campoExcedido = true;
            etiquetaErrorCorreo.setText("No puede contener más de 320 caracteres");
        }
        if (campoTelefono.getText().trim().length() > 10) {
            campoExcedido = true;
            etiquetaErrorTelefono.setText("No puede contener más de 10 digitos");
        }
        return campoExcedido;
    }

    private void desplegarVentanaBusquedaCliente() throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaBuscar.fxml"));
        Parent root = (Parent) loader.load();
        VentanaBuscarController ventanaBuscar = loader.getController();
        ventanaBuscar.obtenerSeccion("Clientes", panelPrincipal, nombreUsuarioActual);
        panelPrincipal.getChildren().add(root);
    }

    public void llenarCampos(Cliente cliente, String nombreUsuario) {
        nombreUsuarioActual = nombreUsuario;
        this.cliente = cliente;
        campoNombre.setText(cliente.getNombre());
        campoApellidos.setText(cliente.getApellidos());
        campoCorreo.setText(cliente.getCorreo());
        campoTelefono.setText(cliente.getTelefono());
        if (cliente.getRutaFoto() != null) {
            nombreFoto = cliente.getRutaFoto();
            Image foto = new Image("file:" + System.getProperty("user.dir") + "/imagenesClientes/" + cliente.getRutaFoto(), 100, 100, false, true, true);
            fotoSeleccionada.setImage(foto);
        }
    }

    private boolean esTelefonoValido(String telefono) {
        boolean telefonoValido = true;
        for (int i = 0; i < telefono.length(); i++) {
            char caracter = telefono.charAt(i);
            if (!Character.isDigit(caracter)) {
                telefonoValido = false;
                etiquetaErrorTelefono.setText("El telefono no puede contener letras");
            }
        }
        return telefonoValido;
    }

    private void limpiarEtiquetas() {
        etiquetaErrorApellidos.setText("");
        etiquetaErrorCorreo.setText("");
        etiquetaErrorNombre.setText("");
        etiquetaErrorTelefono.setText("");
    }
}
