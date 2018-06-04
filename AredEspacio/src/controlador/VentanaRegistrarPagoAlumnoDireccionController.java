package controlador;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import negocio.AlumnoDAO;
import negocio.CuentaDAO;
import negocio.GrupoDAO;
import negocio.MaestroDAO;
import negocio.PagoAlumnoDireccionDAO;
import negocio.Utileria;
import persistencia.Alumno;
import persistencia.Cuenta;
import persistencia.Grupo;

/**
 * Este controlador es usado para registrar las mensualidades de los alumnos
 *
 * @author Israel Reyes Ozuna
 * @version 1.0 / 5 de junio de 2018
 */
public class VentanaRegistrarPagoAlumnoDireccionController implements Initializable {

    @FXML
    private Label etiquetaMaestro;
    @FXML
    private DatePicker selectorFecha;
    @FXML
    private TextField CampoMontoRecibido;
    @FXML
    private Label etiquetaNombreGrupo;
    @FXML
    private Label etiquetaNombreAlumno;
    @FXML
    private AnchorPane panelRegistro;

    private int idGrupoSeleccionado;
    private int idAlumno;
    private String nombreUusarioActual;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    /**
     * Este método carga los datos del alumno que realizará el pago y en que
     * grupo va a pagar
     *
     * @param alumno que fue seleccionado en la tabla
     * @param nombreUsuario del director que realizará el registro del pago
     * @param idGrupo para saber a que grupo pertenece ese pago del alumno
     * @since 1.0 / 5 de junio de 2018
     */
    public void establecerDatos(Alumno alumno, String nombreUsuario, int idGrupo) {
        idAlumno = alumno.getIdAlumno();
        idGrupoSeleccionado = idGrupo;
        nombreUusarioActual = nombreUsuario;
        etiquetaNombreAlumno.setText(alumno.getNombre() + " " + alumno.getApellidos());
        GrupoDAO grupoDAO = new GrupoDAO();
        Grupo grupoSeleccionado = grupoDAO.adquirirGrupo(idGrupo);
        etiquetaNombreGrupo.setText(grupoSeleccionado.getNombreGrupo());
        MaestroDAO maestroDAO = new MaestroDAO();
        String maestroDeGrupo = maestroDAO.adquirirNombreMaestroPorNombreDeUsuario(grupoSeleccionado.getUsuario().getUsuario());
        etiquetaMaestro.setText(maestroDeGrupo);
        selectorFecha.setValue(LocalDate.now());
        selectorFecha.setEditable(false);
        CampoMontoRecibido.textProperty().addListener((observable, viejoValor, nuevoValor) -> {
            if (!nuevoValor.matches("\\d+\\.\\d*")) {
                CampoMontoRecibido.setText(nuevoValor.replaceAll("[^\\d]", ""));
            }
        });
    }

    @FXML
    private void registrarPago(ActionEvent event) {
        if (CampoMontoRecibido.getText().isEmpty()) {
            DialogosController.mostrarMensajeInformacion("Error", "Por favor ingrese un monto a pagar", "");
        } else if (selectorFecha.getValue() == null) {
            DialogosController.mostrarMensajeInformacion("Error", "Por favor ingrese una fecha", "");
        } else {
            persistencia.Pagoalumnodireccion pago = new persistencia.Pagoalumnodireccion();
            GrupoDAO grupoDAO = new GrupoDAO();
            CuentaDAO cuentaDAO = new CuentaDAO();
            AlumnoDAO alumnoDAO = new AlumnoDAO();
            Grupo grupoSeleccionado = grupoDAO.adquirirGrupo(idGrupoSeleccionado);
            Cuenta cuentaActual = cuentaDAO.obtenerCuenta(grupoSeleccionado.getUsuario().getUsuario());
            Alumno alumnoPago = alumnoDAO.adquirirAlumno(idAlumno);
            pago.setUsuario(cuentaActual);
            pago.setCantidad(Double.parseDouble(CampoMontoRecibido.getText()));
            pago.setFechaPago(Utileria.convertirFecha(selectorFecha.getValue()));
            pago.setIdAlumno(alumnoPago);
            pago.setIdGrupo(grupoSeleccionado);
            PagoAlumnoDireccionDAO pagoDAO = new PagoAlumnoDireccionDAO();
            if (pagoDAO.registrarPagoDireccion(pago)) {
                DialogosController.mostrarMensajeInformacion("Exito", "Se registro exitosamente el recibo de un pago", "El maestro respectivo recibirá una notificación de que la direccion recibio un pago el/ella");
                FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaConsultarInformacionGrupo.fxml"));
                try {
                    Parent root = (Parent) loader.load();
                    VentanaConsultarInformacionGrupoController ventanaConsultarInformacionGrupoController = loader.getController();
                    ventanaConsultarInformacionGrupoController.establecerGrupo(idGrupoSeleccionado, nombreUusarioActual);
                    panelRegistro.getChildren().add(root);
                } catch (IOException ex) {
                    Logger.getLogger(VentanaConsultarGruposController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                DialogosController.mostrarMensajeInformacion("Error", "Parece haber ocurrido un error", "Por favor contecte al encargado del sistema");
            }
        }

    }

    @FXML
    private void cancelarRegistro(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaConsultarInformacionGrupo.fxml"));
        try {
            Parent root = (Parent) loader.load();
            VentanaConsultarInformacionGrupoController ventanaConsultarInformacionGrupoController = loader.getController();
            ventanaConsultarInformacionGrupoController.establecerGrupo(idGrupoSeleccionado, nombreUusarioActual);
            panelRegistro.getChildren().add(root);
        } catch (IOException ex) {
            Logger.getLogger(VentanaConsultarGruposController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void limitarCaracteresCantidad(KeyEvent event) {
        char caracter = event.getCharacter().charAt(0);
        limitarCaracteres(event, CampoMontoRecibido, 4);
    }

    /**
     * Este método limita el campo del pago
     *
     * @param event
     * @param campo monto del pago
     * @param caracteresMaximos número maximo de números permitidos en el campo
     * @since 1.0 / 5 de junio de 2018
     */
    public void limitarCaracteres(KeyEvent event, TextField campo, int caracteresMaximos) {
        if (campo.getText().length() >= caracteresMaximos) {
            event.consume();
        }
    }

}
