package controlador;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import negocio.Alumno;
import negocio.AlumnoDAO;
import negocio.Utileria;

/**
 * FXML Controller class
 *
 * @author Israel Reyes Ozuna
 */
public class VentanaRegistrarAlumnoController implements Initializable {

    @FXML
    private JFXButton botonSeleccionarImagen;
    @FXML
    private Label etiquetaNombre;
    @FXML
    private Label etiquetaApellidos;
    @FXML
    private Label etiquetaFechaNacimiento;
    @FXML
    private Label etiquetaCorreo;
    @FXML
    private Label etiquetaTelefono;
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
    private JFXButton botonCancelar;
    @FXML
    private JFXButton botonRegistrar;
    @FXML
    private ImageView fotoSeleccionada;

    private String nombreFoto = "";
    private Alumno nuevoAlumno = new Alumno();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void registrarNuevoAlumno(ActionEvent event) {
        if (!existenCamposVacios(campoNombre, campoApellidos, campoCorreo, campoTelefono, campoFechaNacimiennto)) {
            if (!existenCamposExcedidos(campoNombre, campoApellidos, campoCorreo, campoTelefono)) {
                if (Utileria.validarCorreo(campoCorreo.getText())) {
                    AlumnoDAO nuevoAlumnoDAO = new AlumnoDAO();

                    nuevoAlumno = new Alumno();
                    nuevoAlumno.setNombre(campoNombre.getText());
                    nuevoAlumno.setApellidos(campoApellidos.getText());
                    nuevoAlumno.setCorreoElectronico(campoCorreo.getText());
                    nuevoAlumno.setFechaNacimiento(nuevoAlumno.convertirFechaNacimiento(campoFechaNacimiennto.getValue()));
                    nuevoAlumno.setTelefono(campoTelefono.getText());
                    nuevoAlumno.setRutaFoto(nombreFoto);

                    if (nuevoAlumnoDAO.registrarAlumno(nuevoAlumno)) {
                        DialogosController.mostrarMensajeInformacion("Guardado", "Alumno registrado", "El alumno ha sido registrado exitosamente");
                        campoApellidos.setText("");
                        campoCorreo.setText("");
                        campoFechaNacimiennto.setValue(null);
                        campoNombre.setText("");
                        campoTelefono.setText("");
                    } else {
                        DialogosController.mostrarMensajeAdvertencia("Error", "Error al registrar", "Ha ocurrido un error. No se pudo registrar");
                    }
                } else {
                    DialogosController.mostrarMensajeInformacion("", "Correo no válido", "El correo ingresado no tiene un formato válido");
                }
            }
        } else {
            DialogosController.mostrarMensajeInformacion("Campo vacio", "Alugún campo esta vacío", "Debe llenar todos los campos requeridos");
        }
    }

    @FXML
    public String seleccionarImagen(ActionEvent event) throws IOException {
        FileChooser explorador = new FileChooser();
        explorador.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("*.png", "*.jpg"));
        File archivoSeleccionado = explorador.showOpenDialog(null);

        if (archivoSeleccionado != null) {
            String rutaOrigen = archivoSeleccionado.getAbsolutePath();
            nombreFoto = archivoSeleccionado.getName();
            String rutaNueva = "C:\\Users\\iro19\\Documents\\GitHub\\Repositorio-Desarrollo-de-Software\\AredEspacio\\src\\imagenesAlumnos";
            StringBuilder comando = new StringBuilder();
            comando.append("copy ").append('"' + rutaOrigen + '"').append(" ").append('"' + rutaNueva + '"');
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", comando.toString());
            builder.redirectErrorStream(true);
            Process process = builder.start();
            nuevoAlumno.setRutaFoto(nombreFoto);
        }

        if (nuevoAlumno.getRutaFoto() != null) {
            Image foto = new Image("imagenesAlumnos/" + nuevoAlumno.getRutaFoto(), 140, 140, false, true, true);
            fotoSeleccionada.setImage(foto);
        }
        return nombreFoto;
    }

    public boolean existenCamposVacios(TextField campoNombre, TextField campoApellidos, TextField campoCorreo, TextField campoTelefono, DatePicker campoFechaNacimiento) {
        boolean camposVacios = false;

        if (campoNombre.getText().isEmpty()) {
            camposVacios = true;
        } else if (campoApellidos.getText().isEmpty()) {
            camposVacios = true;
        } else if (campoCorreo.getText().isEmpty()) {
            camposVacios = true;
        } else if (campoTelefono.getText().isEmpty()) {
            camposVacios = true;
        } else if (campoFechaNacimiento.getValue() == null) {
            camposVacios = true;
        }
        return camposVacios;
    }

    public boolean existenCamposExcedidos(TextField campoNombre, TextField campoApellidos, TextField campoCorreo, TextField campoTelefono) {
        boolean campoExcedido = false;

        if (campoNombre.getText().length() > 30) {
            campoExcedido = true;
            DialogosController.mostrarMensajeInformacion("Campo excedido", "Campo nombre excedido", "El campo de nombre no puede contener mas de 30 caracteres");
        } else if (campoApellidos.getText().length() > 30) {
            campoExcedido = true;
            DialogosController.mostrarMensajeInformacion("Campo excedido", "Campo apellidos excedido", "El campo de apellidos no puede contener mas de 30 caracteres");
        } else if (campoCorreo.getText().length() > 320) {
            campoExcedido = true;
            DialogosController.mostrarMensajeInformacion("Campo excedido", "Campo correo excedido", "El campo de correo no puede contener mas de 320 caracteres");
        } else if (campoTelefono.getText().length() > 10) {
            campoExcedido = true;
            DialogosController.mostrarMensajeInformacion("Campo excedido", "Campo telefono excedido", "El campo de telefono no puede contener mas de 10 caracteres");
        }
        return campoExcedido;
    }
}
