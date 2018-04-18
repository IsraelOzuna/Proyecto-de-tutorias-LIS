/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import negocio.MaestroDAO;
import negocio.Utileria;
import persistencia.Maestro;

/**
 * FXML Controller class
 *
 * @author Irdevelo
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

    private String nombreFoto;

    public void obtenerMaestro(Maestro maestro) {
        this.maestro = maestro;

        llenarCamposInformacion();

    }

    public void obtenerPanel(Pane panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void cerrarVentanaEditarInformacionMaestro(ActionEvent event) throws IOException {
        desplegarVentanaBusquedaAlumno();
    }

    @FXML
    private void guardarInformacionMaestroEditada(ActionEvent event) throws IOException {
        boolean cantidadCorrecta = true;
        if (!existenCamposVacios(campoNombre, campoApellidos, campoCorreoElectronico, campoTelefono, campoCantidadAPagar)) {
            if (!verificarLongitudExcedida(campoNombre, campoApellidos, campoCorreoElectronico, campoTelefono)) {
                if (Utileria.validarCorreo(campoCorreoElectronico.getText())) {
                    Maestro maestroNuevo = new Maestro();
                    try {
                        maestroNuevo.setMensualidad(Double.parseDouble(campoCantidadAPagar.getText()));
                    } catch (NumberFormatException ex) {
                        cantidadCorrecta = false;
                        DialogosController.mostrarMensajeInformacion("Dato incorrecto", "Las letras no son una cantidad", "Debe ingresar una cantidad numérica");
                    }
                    if (cantidadCorrecta) {

                        MaestroDAO maestroDAO = new MaestroDAO();

                        maestroNuevo.setNombre(campoNombre.getText());
                        maestroNuevo.setApellidos(campoApellidos.getText());
                        maestroNuevo.setCorreoElectronico(campoCorreoElectronico.getText());
                        maestroNuevo.setTelefono(campoTelefono.getText());
                        maestroNuevo.setRutaFoto(maestro.getRutaFoto());
                        maestroNuevo.setEstaActivo(maestro.getEstaActivo());
                        maestroNuevo.setFechaCorte(maestro.getFechaCorte());
                        maestroNuevo.setUsuario(maestro.getUsuario());

                        if (maestroDAO.editarMaestro(maestroNuevo)) {
                            DialogosController.mostrarMensajeInformacion("Guardado", "Maestro modificado", "El maestro ha sido modificado exitosamente");
                            desplegarVentanaBusquedaAlumno();
                        } else {
                            DialogosController.mostrarMensajeAdvertencia("Error", "Error al modificar", "Ha ocurrido un error. No se pudo modificar");
                        }
                    }
                } else {
                    DialogosController.mostrarMensajeInformacion("", "Correo no válido", "El correo ingresado no tiene un formato válido");
                }

            } else {
                DialogosController.mostrarMensajeInformacion("Campos excedidos", "Algún campo excede el límite de caracteres", "Revise el límite de caracteres permitidos");
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
            String rutaNueva = "C:\\Users\\irdev\\OneDrive\\Documentos\\GitHub\\Repositorio-Desarrollo-de-Software\\AredEspacio\\src\\imagenesMaestros";
            StringBuilder comando = new StringBuilder();
            comando.append("copy ").append('"' + rutaOrigen + '"').append(" ").append('"' + rutaNueva + '"');
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", comando.toString());
            builder.redirectErrorStream(true);
            Process process = builder.start();
            maestro.setRutaFoto(nombreFoto);
        }

        if (maestro.getRutaFoto() != null) {
            Image foto = new Image("imagenesMaestros/" + maestro.getRutaFoto(), 140, 140, false, true, true);
            imagenPerfil.setImage(foto);
        }
        return nombreFoto;
    }

    public boolean verificarLongitudExcedida(TextField campoNombre, TextField campoApellidos, TextField campoCorreoElectronico, TextField campoTelefono) {
        boolean longitudExcedida = false;
        if (campoNombre.getText().length() > 30 || campoApellidos.getText().length() > 30
                || campoCorreoElectronico.getText().length() > 320 || campoTelefono.getText().length() > 10) {
            longitudExcedida = true;
        }
        return longitudExcedida;
    }

    public boolean existenCamposVacios(TextField campoNombre, TextField campoApellidos, TextField campoCorreo, TextField campoTelefono, TextField campoCantidadAPagar) {
        boolean camposVacios = false;

        if (campoNombre.getText().isEmpty()) {
            camposVacios = true;
        } else if (campoApellidos.getText().isEmpty()) {
            camposVacios = true;
        } else if (campoCorreo.getText().isEmpty()) {
            camposVacios = true;
        } else if (campoTelefono.getText().isEmpty()) {
            camposVacios = true;
        } else if (campoCantidadAPagar.getText().isEmpty()) {
            camposVacios = true;
        }
        return camposVacios;
    }

    public void llenarCamposInformacion() {
        campoNombre.setText(maestro.getNombre());
        campoApellidos.setText(maestro.getApellidos());
        campoCorreoElectronico.setText(maestro.getCorreoElectronico());
        campoTelefono.setText(maestro.getTelefono());
        campoCantidadAPagar.setText(String.valueOf(maestro.getMensualidad()));

        if (maestro.getRutaFoto() != null) {
            Image foto = new Image("imagenesMaestros/" + maestro.getRutaFoto(), 100, 100, false, true, true);
            imagenPerfil.setImage(foto);
        }

    }

    public void desplegarVentanaBusquedaAlumno() throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaBuscar.fxml"));
        Parent root = (Parent) loader.load();
        VentanaBuscarController ventanaBuscar = loader.getController();
        ventanaBuscar.obtenerSeccion("Maestros", panelPrincipal);
        panelPrincipal.getChildren().add(root);
    }

}
