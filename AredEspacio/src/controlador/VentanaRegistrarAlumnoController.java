package controlador;

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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import negocio.Alumno;
import negocio.AlumnoDAO;
import negocio.Utileria;

/**
 * Este controlador es usado para realizar registros de un alumno nuevo
 *
 * @author Israel Reyes Ozuna
 * @version 1.0 / 5 de junio de 2018
 */
public class VentanaRegistrarAlumnoController implements Initializable {

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
    private ImageView fotoSeleccionada;
    @FXML
    private AnchorPane panelPrincipal;

    private String nombreFoto;
    private Alumno nuevoAlumno = new Alumno();
    @FXML
    private Label etiquetaErrorNombre;
    @FXML
    private Label etiquetaErrorApellidos;
    @FXML
    private Label etiquetaErrorCorreo;
    @FXML
    private Label etiquetaErrorFecha;
    @FXML
    private Label etiquetaErrorTelefono;
    private String rutaOrigen;
    private String rutaNueva;
    private String nombreUsuarioActual;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nombreFoto = "";
    }

    /**
     * Este método permite obtener el usuario que registrara un nuevo alumno
     *
     * @param nombreUsuario quien hará el registro
     * @since 1.0 / 5 de junio de 2018
     */
    public void obtenerUsuario(String nombreUsuario) {
        nombreUsuarioActual = nombreUsuario;
    }

    @FXML
    public void registrarNuevoAlumno(ActionEvent event) throws IOException {
        limpiarEtiquetas();
        if (!existenCamposVacios(campoNombre, campoApellidos, campoCorreo, campoTelefono, campoFechaNacimiennto)) {
            if (!existenCamposExcedidos(campoNombre, campoApellidos, campoCorreo, campoTelefono)) {
                if (Utileria.validarCorreo(campoCorreo.getText().trim())) {
                    if (esTelefonoValido(campoTelefono.getText().trim())) {
                        AlumnoDAO nuevoAlumnoDAO = new AlumnoDAO();
                        nuevoAlumno = new Alumno();
                        nuevoAlumno.setNombre(campoNombre.getText().trim());
                        nuevoAlumno.setApellidos(campoApellidos.getText().trim());
                        nuevoAlumno.setCorreoElectronico(campoCorreo.getText().trim());
                        nuevoAlumno.setFechaNacimiento(Utileria.convertirFechaNacimiento(campoFechaNacimiennto.getValue()));
                        nuevoAlumno.setTelefono(campoTelefono.getText().trim());
                        nuevoAlumno.setRutaFoto(nombreFoto);

                        if (nuevoAlumnoDAO.registrarAlumno(nuevoAlumno)) {
                            DialogosController.mostrarMensajeInformacion("Guardado", "Alumno registrado", "El alumno ha sido registrado exitosamente");
                            desplegarVentanaBusquedaAlumno();
                        } else {
                            DialogosController.mostrarMensajeAdvertencia("Error", "Error al registrar", "Ha ocurrido un error. No se pudo registrar");
                        }
                    }
                } else {
                    etiquetaErrorCorreo.setText("Formato de correo no valido");
                }
            }
        }
    }

    @FXML
    public void cancelarRegistro(ActionEvent event) throws IOException {
        if (existenCamposVacios(campoNombre, campoApellidos, campoCorreo, campoTelefono, campoFechaNacimiennto)) {
            desplegarVentanaBusquedaAlumno();
        } else {
            if (DialogosController.mostrarMensajeCambios("Salir", "La información se perderá", "La información en los campos que ingresó se perderá")) {
                desplegarVentanaBusquedaAlumno();
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
            CodeSource direccion = VentanaRegistrarAlumnoController.class.getProtectionDomain().getCodeSource();
            fileJar = new File(direccion.getLocation().toURI().getPath());
            fileDir = fileJar.getParentFile();
            directorio = new File(fileDir.getAbsolutePath() + "/imagenesAlumnos/");
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
            directorio = new File(fileDir.getAbsolutePath() + "/imagenesAlumnos/" + nombreFoto);

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

    /**
     * Este método permite que no se dejen campos obligatorios del alumno en
     * blanco
     *
     * @param campoNombre nombre del alumno
     * @param campoApellidos apellidos del alumno
     * @param campoCorreo correo del alumno
     * @param campoTelefono telefono del alumno
     * @param campoFechaNacimiento fecha de nacimiento del alumno
     * @return boolean que verifica si existen o no campos vacíos
     * @since 1.0 / 5 de junio de 2018
     */
    public boolean existenCamposVacios(TextField campoNombre, TextField campoApellidos, TextField campoCorreo, TextField campoTelefono, DatePicker campoFechaNacimiento) {
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
        if (campoFechaNacimiento.getValue() == null) {
            camposVacios = true;
            etiquetaErrorFecha.setText("Campo obligatorio");
        }
        return camposVacios;
    }

    /**
     * Este método verifica que los datos ingresados por el usuario no excedan
     * el limite para poder almacenarlos correctamente
     *
     * @param campoNombre nombre del alumno
     * @param campoApellidos apellidos del alumno
     * @param campoCorreo correo del alumno
     * @param campoTelefono telefono del alumno
     * @return boolean que verifica si existen o no campos excedidos
     * @since 1.0 / 5 de junio de 2018
     */
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

    /**
     * Este método verifica que los caracteres ingresados en el télefono sean
     * únicamente números
     *
     * @param telefono cadena ingresada por el usuario en el campo del telefono
     * @return boolean que verifica si existen o no campos caracteres no
     * numericos
     * @since 1.0 / 5 de junio de 2018
     */
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

    /**
     * Este método lanza la ventana de los alumnos cuando la edición se completó
     *
     * @since 1.0 / 5 de junio de 2018
     */
    private void desplegarVentanaBusquedaAlumno() throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaBuscar.fxml"));
        Parent root = (Parent) loader.load();
        VentanaBuscarController ventanaBuscar = loader.getController();
        ventanaBuscar.obtenerSeccion("Alumnos", panelPrincipal, nombreUsuarioActual);
        panelPrincipal.getChildren().add(root);
    }

    /**
     * Este método quita el contenido de las etiquetas que se muestran en caso
     * de algún error
     *
     * @since 1.0 / 5 de junio de 2018
     */
    private void limpiarEtiquetas() {
        etiquetaErrorApellidos.setText("");
        etiquetaErrorCorreo.setText("");
        etiquetaErrorFecha.setText("");
        etiquetaErrorNombre.setText("");
        etiquetaErrorTelefono.setText("");
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
}
