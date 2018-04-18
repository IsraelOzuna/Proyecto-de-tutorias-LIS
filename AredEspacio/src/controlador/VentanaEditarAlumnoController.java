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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void guardarNuevosDatos(ActionEvent event) throws IOException {
        if (!existenCamposVacios(campoNombre, campoApellidos, campoCorreo, campoTelefono, campoFechaNacimiennto)) {
            if (!existenCamposExcedidos(campoNombre, campoApellidos, campoCorreo, campoTelefono)) {
                if (Utileria.validarCorreo(campoCorreo.getText())) {
                    AlumnoDAO nuevosDatosAlumno = new AlumnoDAO();

                    alumno.setNombre(campoNombre.getText());
                    alumno.setApellidos(campoApellidos.getText());
                    alumno.setCorreoElectronico(campoCorreo.getText());
                    alumno.setFechaNacimiento(Date.valueOf(campoFechaNacimiennto.getValue()));
                    alumno.setTelefono(campoTelefono.getText());
                    alumno.setRutaFoto(alumno.getRutaFoto());

                    if (nuevosDatosAlumno.editarAlumno(alumno)) {
                        DialogosController.mostrarMensajeInformacion("Guardado", "Alumno modificado", "El alumno ha sido modificado exitosamente");
                        desplegarVentanaBusquedaAlumno();
                    } else {
                        DialogosController.mostrarMensajeAdvertencia("Error", "Error al modificar", "Ha ocurrido un error. No se pudo modificar");
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
    public void cancelarRegistro(ActionEvent event) throws IOException {
        if(existenCamposVacios(campoNombre, campoApellidos, campoCorreo, campoTelefono, campoFechaNacimiennto)){
            desplegarVentanaBusquedaAlumno();
        }else{
            if(DialogosController.mostrarMensajeCambios("Salir", "La información se perderá", "La información en los campos que ingresó se perderá")){
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
            String rutaOrigen = archivoSeleccionado.getAbsolutePath();
            nombreFoto = archivoSeleccionado.getName();
            String rutaNueva = "C:\\Users\\iro19\\Documents\\GitHub\\Repositorio-Desarrollo-de-Software\\AredEspacio\\src\\imagenesAlumnos";
            StringBuilder comando = new StringBuilder();
            comando.append("copy ").append('"' + rutaOrigen + '"').append(" ").append('"' + rutaNueva + '"');
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", comando.toString());
            builder.redirectErrorStream(true);
            Process process = builder.start();
            alumno.setRutaFoto(nombreFoto);
        }

        if (alumno.getRutaFoto() != null) {
            Image foto = new Image("imagenesAlumnos/" + alumno.getRutaFoto(), 140, 140, false, true, true);
            fotoSeleccionada.setImage(foto);
        }
        return nombreFoto;
    }
    
    public void llenarCampos(Alumno alumno){
        this.alumno = alumno;
        campoNombre.setText(alumno.getNombre());
        campoApellidos.setText(alumno.getApellidos());
        campoCorreo.setText(alumno.getCorreoElectronico());
        campoFechaNacimiennto.setValue(Utileria.mostrarFechaNacimiento(alumno.getFechaNacimiento()));        
        campoTelefono.setText(alumno.getTelefono());
        if (alumno.getRutaFoto() != null) {
            Image foto = new Image("imagenesAlumnos/" + alumno.getRutaFoto(), 100, 100, false, true, true);
            fotoSeleccionada.setImage(foto);
        }
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

    public void desplegarVentanaBusquedaAlumno() throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaBuscar.fxml"));
        Parent root = (Parent) loader.load();
        VentanaBuscarController ventanaBuscar = loader.getController();
        ventanaBuscar.obtenerSeccion("Alumnos", panelPrincipal);
        panelPrincipal.getChildren().add(root);
    }
}
