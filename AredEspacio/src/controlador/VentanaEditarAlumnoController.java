package controlador;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
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
 * FXML Controller class
 *
 * @author Israel Reyes Ozuna
 */
public class VentanaEditarAlumnoController implements Initializable {

    @FXML
    private Label etiquetaNombre;
    @FXML
    private TextField campoNombre;
    @FXML
    private Label etiquetaApellidos;
    @FXML
    private TextField campoApellidos;
    @FXML
    private DatePicker campoFechaNacimiennto;
    @FXML
    private Label etiquetaFechaNacimiento;
    @FXML
    private Label etiquetaCorreo;
    @FXML
    private TextField campoCorreo;
    @FXML
    private Label etiquetaTelefono;
    @FXML
    private TextField campoTelefono;
    @FXML
    private JFXButton botonGuardar;
    @FXML
    private JFXButton botonCancelar;
    @FXML
    private ImageView fotoSeleccionada;
    @FXML
    private JFXButton botonSeleccionarImagen;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nombreFoto = "";
    }

    @FXML
    public void guardarNuevosDatos(ActionEvent event) throws IOException {
        limpiarEtiquetas();
        if (!existenCamposVacios(campoNombre, campoApellidos, campoCorreo, campoTelefono, campoFechaNacimiennto)) {
            if (!existenCamposExcedidos(campoNombre, campoApellidos, campoCorreo, campoTelefono)) {
                if (Utileria.validarCorreo(campoCorreo.getText().trim())) {
                    if (esTelefonoValido(campoTelefono.getText().trim())) {
                        StringBuilder comando = new StringBuilder();
                        comando.append("copy ").append('"' + rutaOrigen + '"').append(" ").append('"' + rutaNueva + '"');
                        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", comando.toString());
                        builder.redirectErrorStream(true);
                        Process process = builder.start();

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
        FileChooser explorador = new FileChooser();
        explorador.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("*.png", "*.jpg"));
        File archivoSeleccionado = explorador.showOpenDialog(null);

        if (archivoSeleccionado != null) {
            rutaOrigen = archivoSeleccionado.getAbsolutePath();
            nombreFoto = archivoSeleccionado.getName();
            rutaNueva = System.getProperty("user.dir") + "\\imagenesAlumnos";
            
            if (!nombreFoto.equals("")) {
                Image foto = new Image("file:" + rutaOrigen, 140, 140, false, true, true);
                fotoSeleccionada.setImage(foto);
            }
        }

        return nombreFoto;
    }

    public void llenarCampos(Alumno alumno) {
        this.alumno = alumno;
        campoNombre.setText(alumno.getNombre());
        campoApellidos.setText(alumno.getApellidos());
        campoCorreo.setText(alumno.getCorreoElectronico());
        campoFechaNacimiennto.setValue(Utileria.mostrarFechaNacimiento(alumno.getFechaNacimiento()));
        campoTelefono.setText(alumno.getTelefono());
        if (alumno.getRutaFoto() != null) {
            nombreFoto = alumno.getRutaFoto();
            Image foto = new Image("file:" + System.getProperty("user.dir") + "\\imagenesAlumnos\\" + alumno.getRutaFoto(), 100, 100, false, true, true);
            fotoSeleccionada.setImage(foto);
        }
    }

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

    public void desplegarVentanaBusquedaAlumno() throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaBuscar.fxml"));
        Parent root = (Parent) loader.load();
        VentanaBuscarController ventanaBuscar = loader.getController();
        ventanaBuscar.obtenerSeccion("Alumnos", panelPrincipal);
        panelPrincipal.getChildren().add(root);
    }

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
