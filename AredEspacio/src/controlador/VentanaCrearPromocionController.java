package controlador;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import negocio.PromocionDAO;
import persistencia.Alumno;
import persistencia.Promocion;

/**
 * Este controlador es usado para crear una nueva promoción dentro del sistema
 * verificando su existencia y validez
 *
 * @author Renato Vargas
 * @version 1.0 / 5 de junio de 2018
 */
public class VentanaCrearPromocionController implements Initializable {

    @FXML
    private TextField campoNombrePromocion;
    @FXML
    private TextField campoDescripcion;
    @FXML
    private TextField campoMonto;
    @FXML
    private JFXButton botonRegistrarPromocion;
    @FXML
    private JFXButton botonCancelar;

    private Alumno alumnoInscribir;
    @FXML
    private AnchorPane panelPrincipal;
    private String pantallaAnterior;
    private String nombreUsuarioActual;
    @FXML
    private ComboBox<?> comboPromocion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList promociones = FXCollections.observableArrayList();
        promociones.addAll("Inscripcion", "Mensualidad");
        comboPromocion.setItems(promociones);
    }

    /**
     * Este método establece los datos que se utilizaran para regresar a la
     * pantalla anterior
     *
     * @param pantallaAnterior la cadena que determina de que ventana se proviene
     * @param alumnoSeleccionado el alumno con el que se decidio realizar el 
     * registro de la promoción
     * @param nombreUsuario el nombre del usuario actual para limitar sus capacidades
     * @since 1.0 / 5 de junio de 2018
     */
    public void iniciarVentanaDesdeInscripcion(String pantallaAnterior, persistencia.Alumno alumnoSeleccionado, String nombreUsuario) {
        nombreUsuarioActual = nombreUsuario;
        this.pantallaAnterior = pantallaAnterior;
        alumnoInscribir = alumnoSeleccionado;
        if (pantallaAnterior.equals("Promociones")) {
            botonCancelar.setVisible(false);
        }
    }

    
    @FXML
    private void registrarPromocion(ActionEvent event) throws IOException {
        if (camposLlenos()) {
            if (camposValidos()) {
                PromocionDAO promocionDAO = new PromocionDAO();
                Promocion nuevaPromocion = new Promocion();
                String nombrePromocion = campoNombrePromocion.getText();
                String descripcionPromocion = campoDescripcion.getText();
                int porcentaje = Integer.parseInt(campoMonto.getText());
                nuevaPromocion.setNombrePromocion(nombrePromocion);
                nuevaPromocion.setDescripcion(descripcionPromocion);
                nuevaPromocion.setPorcentajeDescuento(porcentaje);
                nuevaPromocion.setTipoPromocion((String) comboPromocion.getValue());
                if (promocionYaExiste(nombrePromocion)) {
                    DialogosController.mostrarMensajeInformacion("Error", "Una promoción con ese nombre ya existe", "Por favor ingrese un nuevo nombre");
                } else {
                    if (promocionDAO.registrarPromocion(nuevaPromocion)) {
                        DialogosController.mostrarMensajeInformacion("Creada", "La promocion fué creada de manera exitosa", "Ahora podrá aplicar está promoción cuando lo desee");
                        if (pantallaAnterior.equals("inscripcion")) {
                            FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaInscribirAlumno.fxml"));
                            Parent root = (Parent) loader.load();
                            VentanaInscribirAlumnoController ventanaInscribir = loader.getController();
                            ventanaInscribir.llenarCampos(alumnoInscribir, nombreUsuarioActual);
                            panelPrincipal.getChildren().add(root);
                        } else {
                            limpiarCampos();
                        }
                    } else {
                        DialogosController.mostrarMensajeInformacion("Error", "Se ha detectado un problema y la promocion no fue creada", "Por favor consulte al encargado del sistema");
                    }
                }
            }
        }
    }

    @FXML
    private void regresarPantallaAnterior(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaInscribirAlumno.fxml"));
        Parent root = (Parent) loader.load();
        VentanaInscribirAlumnoController ventanaInscribir = loader.getController();
        ventanaInscribir.llenarCampos(alumnoInscribir, nombreUsuarioActual);
        panelPrincipal.getChildren().add(root);

    }

    /**
     * Este método reestablece los campos de la ventana
     *
     * @since 1.0 / 5 de junio de 2018
     */
    public void limpiarCampos() {
        campoDescripcion.setText("");
        campoMonto.setText("");
        campoNombrePromocion.setText("");
        comboPromocion.setValue(null);
    }

    private boolean promocionYaExiste(String nombre) {
        boolean existe = false;
        PromocionDAO promocionDAO = new PromocionDAO();
        existe = promocionDAO.promocionYaexistente(nombre);
        return existe;
    }

    private boolean camposLlenos() {
        boolean camposLlenados = false;
        if (campoNombrePromocion.getText().equals("")) {
            DialogosController.mostrarMensajeInformacion("Error", "Por favor ingrese un nombre para la promocion", "");
            camposLlenados = false;
        } else if (campoDescripcion.getText().equals("")) {
            DialogosController.mostrarMensajeInformacion("Error", "Por favor ingrese una descripcion", "");
            camposLlenados = false;
        } else if (campoMonto.getText().equals("")) {
            DialogosController.mostrarMensajeInformacion("Error", "Por favor ingrese un monto a descontar", "");
            camposLlenados = false;

        } else if (comboPromocion.getSelectionModel().getSelectedItem() == null) {
            DialogosController.mostrarMensajeInformacion("Error", "Por favor ingrese un tipo de promoción", "");
            camposLlenados = false;
        } else {
            camposLlenados = true;
        }
        return camposLlenados;
    }

    private boolean camposValidos() {
        boolean camposValidos = false;
        int porcentaje = Integer.parseInt(campoMonto.getText());
        if (campoNombrePromocion.getLength() > 30) {
            camposValidos = false;
            DialogosController.mostrarMensajeInformacion("Error", "El nombre de la promoción no puede rebasar 30 caracteres", "Por favor considere cambiar el nombre de la promocion");
        } else if (campoDescripcion.getLength() > 240) {
            camposValidos = false;
            DialogosController.mostrarMensajeInformacion("Error", "La descripción de la promoción no puede rebasar los 240 carcateres", "Por favor considere una descripcion más corta");
        } else if ((porcentaje < 1) || (porcentaje > 100)) {
            camposValidos = false;
            DialogosController.mostrarMensajeInformacion("Error", "El valor a descontar debe ir del 1% al 100%, no puede ser mayor o menor a ese rango", "Por favor cambié el monto");
        } else {
            camposValidos = true;
        }
        return camposValidos;
    }

    @FXML
    private void limitarCaracteres(KeyEvent event) {
        char caracter = event.getCharacter().charAt(0);
        limitarCaracteres(event, campoMonto, 3);
        if (!Character.isDigit(caracter)) {
            event.consume();
        }
    }

    /**
     * Este método limita el nuero de caracteres que pueden ser ingresados
     *
     * @param event evento que se realizó en el campo
     * @param campo campo al que se le ingresaran los datos
     * @param caracteresMaximos numero maximo de caracteres aceptados
     * @since 1.0 / 5 de junio de 2018
     */
    public void limitarCaracteres(KeyEvent event, TextField campo, int caracteresMaximos) {
        if (campo.getText().length() >= caracteresMaximos) {
            event.consume();
        }
    }

}
