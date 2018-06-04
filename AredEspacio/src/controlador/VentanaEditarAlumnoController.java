package controlador;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.security.CodeSource;
import java.sql.Date;
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
import negocio.AlumnoDAO;
import negocio.Utileria;
import persistencia.Alumno;

/**
 * Este controlador es usado para modificar los datos de algún alumno
 *
 * @author Israel Reyes Ozuna
 * @version 1.0 / 5 de junio de 2018
 */
public class VentanaEditarAlumnoController implements Initializable {

    @FXML
    private TextField campoNombre;
    @FXML
    private TextField campoApellidos;
    @FXML
    private DatePicker campoFechaNacimiennto;
    @FXML
    private TextField campoCorreo;
    @FXML
    private TextField campoTelefono;
    @FXML
    private ImageView fotoSeleccionada;
    @FXML
    private AnchorPane panelPrincipal;
    private Alumno alumno;
    private String nombreFoto;
    @FXML
    private Label etiquetaErrorNombre;
    @FXML
    private Label etiquetaErrorApellidos;
    @FXML
    private Label etiquetaErrorFecha;
    @FXML
    private Label etiquetaErrorCorreo;
    @FXML
    private Label etiquetaErrorTelefono;
    private String rutaOrigen;
    private String rutaNueva;
    private String nombreUsuarioActual;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nombreFoto = "";
    }

    @FXML
    private void guardarNuevosDatos(ActionEvent event) throws IOException {
        limpiarEtiquetas();
        if (!existenCamposVacios(campoNombre, campoApellidos, campoCorreo, campoTelefono, campoFechaNacimiennto)) {
            if (!existenCamposExcedidos(campoNombre, campoApellidos, campoCorreo, campoTelefono)) {
                if (Utileria.validarCorreo(campoCorreo.getText().trim())) {
                    if (esTelefonoValido(campoTelefono.getText().trim())) {
                        AlumnoDAO nuevosDatosAlumno = new AlumnoDAO();

                        alumno.setNombre(campoNombre.getText().trim());
                        alumno.setApellidos(campoApellidos.getText().trim());
                        alumno.setCorreoElectronico(campoCorreo.getText().trim());
                        alumno.setFechaNacimiento(Date.valueOf(campoFechaNacimiennto.getValue()));
                        alumno.setTelefono(campoTelefono.getText().trim());
                        alumno.setRutaFoto(nombreFoto);

                        if (nuevosDatosAlumno.editarAlumno(alumno)) {
                            DialogosController.mostrarMensajeInformacion("Guardado", "Alumno modificado", "El alumno ha sido modificado exitosamente");
                            desplegarVentanaBusquedaAlumno();
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
    private void cancelarRegistro(ActionEvent event) throws IOException {
        if (existenCamposVacios(campoNombre, campoApellidos, campoCorreo, campoTelefono, campoFechaNacimiennto)) {
            desplegarVentanaBusquedaAlumno();
        } else {
            if (DialogosController.mostrarMensajeCambios("Salir", "La información se perderá", "La información en los campos que ingresó se perderá")) {
                desplegarVentanaBusquedaAlumno();
            }
        }
    }

    @FXML
    private String seleccionarImagen(ActionEvent event) throws IOException {
        File fileJar;
        File fileDir = null;
        File directorio = null;
        FileChooser explorador = new FileChooser();
        explorador.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("*.png", "*.jpg"));
        File archivoSeleccionado = explorador.showOpenDialog(null);

        try {
            CodeSource direccion = VentanaEditarAlumnoController.class.getProtectionDomain().getCodeSource();
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
     * Este método carga los datos existentes del alumno a editar
     *
     * @param alumno El alumno con todos los atributos actuales
     * @param nombreUsuario usuario el cual realiza la modificación
     * @since 1.0 / 5 de junio de 2018
     */
    public void llenarCampos(Alumno alumno, String nombreUsuario) {
        try {
            nombreUsuarioActual = nombreUsuario;
            this.alumno = alumno;
            campoNombre.setText(alumno.getNombre());
            campoApellidos.setText(alumno.getApellidos());
            campoCorreo.setText(alumno.getCorreoElectronico());
            campoFechaNacimiennto.setValue(Utileria.mostrarFechaNacimiento(alumno.getFechaNacimiento()));
            campoTelefono.setText(alumno.getTelefono());

            CodeSource direccion = VentanaEditarAlumnoController.class.getProtectionDomain().getCodeSource();
            File fileJar = new File(direccion.getLocation().toURI().getPath());
            File fileDir = fileJar.getParentFile();
            File fileProperties = new File(fileDir.getAbsolutePath());

            String rutaFoto = fileProperties.getAbsolutePath();
            if (alumno.getRutaFoto() != null) {
                nombreFoto = alumno.getRutaFoto();
                Image foto = new Image("file:" + rutaFoto + "/imagenesAlumnos/" + alumno.getRutaFoto(), 100, 100, false, true, true);
                fotoSeleccionada.setImage(foto);
            }
        } catch (URISyntaxException ex) {
            Logger.getLogger(VentanaEditarAlumnoController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    public boolean esTelefonoValido(String telefono) {
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
    public void desplegarVentanaBusquedaAlumno() throws IOException {
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
    public void limpiarEtiquetas() {
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
