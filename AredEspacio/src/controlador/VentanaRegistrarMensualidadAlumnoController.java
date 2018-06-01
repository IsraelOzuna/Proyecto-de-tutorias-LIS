package controlador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import negocio.AlumnoDAO;
import negocio.PagoMensualidadAlumnoDAO;
import negocio.PagoMensualidadAlumno;
import negocio.PromocionDAO;
import persistencia.Promocion;

/**
 * FXML Controller class
 *
 * @author Israel Reyes Ozuna
 */
public class VentanaRegistrarMensualidadAlumnoController implements Initializable {

    @FXML
    private Label etiquetaNombreAlumno;
    @FXML
    private JFXButton botonCancelar;
    @FXML
    private JFXButton botonAceptar;
    @FXML
    private Label etiquetaMonto;
    @FXML
    private JFXComboBox<String> comboGruposAlumno;
    @FXML
    private AnchorPane panelTrasero;
    @FXML
    private ImageView imagenPerfil;
    @FXML
    private TextField campoMontoPagar;
    private List<String> gruposAlumno;
    private int idAlumno;
    @FXML
    private Label etiquetaErrorGrupo;
    @FXML
    private Label etiquetaErrorMonto;
    @FXML
    private JFXComboBox<?> comboPromocion;
    @FXML
    private Label etiquetaMontoDescuento;    
    private double montoDescueto;
    @FXML
    private Label etiquetaDescuento;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @FXML
    private void registrarMensualidad(ActionEvent event) {
        etiquetaErrorGrupo.setText("");
        etiquetaErrorMonto.setText("");
        etiquetaMontoDescuento.setText("");
        boolean cantidadValida = true;
        Date fechaPago = new Date();
        if (comboGruposAlumno.getSelectionModel().getSelectedItem() != null) {
            if (!campoMontoPagar.getText().trim().isEmpty()) {
                try {
                    Double.parseDouble(campoMontoPagar.getText());
                } catch (NumberFormatException ex) {
                    etiquetaErrorMonto.setText("Ingresa una cantidad valida");
                    cantidadValida = false;
                }
                if (cantidadValida) {
                    PagoMensualidadAlumno pagoAlumno = new PagoMensualidadAlumno();
                    PagoMensualidadAlumnoDAO pagoInscripcion = new PagoMensualidadAlumnoDAO();

                    if(aplicarPromocion(event)){
                        pagoAlumno.setCantidad(montoDescueto);
                    }else{
                        pagoAlumno.setCantidad(Double.parseDouble(campoMontoPagar.getText().trim()));
                    }
                    
                    pagoAlumno.setFechaPagoInscripcion(fechaPago);
                    pagoAlumno.setIdAlumno(idAlumno);
                    pagoAlumno.setTipoPago('1');

                    if (pagoInscripcion.registrarMensualidad(pagoAlumno, idAlumno, comboGruposAlumno.getValue())) {
                        DialogosController.mostrarMensajeInformacion("", "Registro de pago exitoso", "El pago se ha registrado correctamente");
                        panelTrasero.setVisible(false);
                    } else {
                        DialogosController.mostrarMensajeAdvertencia("Error", "Error al registrar", "El pago no se pudo registrar");
                    }
                }
            } else {
                etiquetaErrorMonto.setText("Campo obligatorio");
            }
        } else {
            etiquetaErrorGrupo.setText("Campo obligatorio");
        }
    }

    @FXML
    private void cerrarDetalles(ActionEvent event) {
        panelTrasero.setVisible(false);
    }

    public void llenarDatos(String rutaFotoAlumno, String nombreAlumno, String apellidosAlumno, int idAlumno) {
        this.idAlumno = idAlumno;
        etiquetaNombreAlumno.setText(nombreAlumno + " " + apellidosAlumno);
        if (rutaFotoAlumno != null) {
            Image foto = new Image("file:" + System.getProperty("user.dir") + "/imagenesAlumnos/" + rutaFotoAlumno, 100, 100, false, true, true);
            imagenPerfil.setImage(foto);
        }
        llenarComboPromocion();
        llenarComboGrupos();
    }

    private ArrayList<String> obtenerNombreGrupos(List<String> gruposAlumno) {
        ArrayList<String> nombreGrupos = new ArrayList();
        AlumnoDAO alumnoDAO = new AlumnoDAO();

        gruposAlumno = alumnoDAO.encontrarGruposAlumno(idAlumno);
        for (int i = 0; i < gruposAlumno.size(); i++) {
            nombreGrupos.add(gruposAlumno.get(i));
        }
        return nombreGrupos;
    }

    private void llenarComboGrupos() {
        ObservableList<String> nombreGrupos = FXCollections.observableArrayList();
        nombreGrupos.addAll(obtenerNombreGrupos(gruposAlumno));
        comboGruposAlumno.setItems(nombreGrupos);
    }

    private void llenarComboPromocion() {
        PromocionDAO promocionDAO = new PromocionDAO();
        List<Promocion> promociones;
        promociones = promocionDAO.consultarPromociones("Mensualidad");
        ObservableList promocionesCombo = FXCollections.observableArrayList();
        promocionesCombo.add(0, comboPromocion.getPromptText());
        for (int i = 0; i < promociones.size(); i++) {
            promocionesCombo.add(promociones.get(i).getNombrePromocion());
        }
        comboPromocion.setItems(promocionesCombo);
    }

    @FXML
    private void limitarCampoMonto(KeyEvent event) {
        char caracter = event.getCharacter().charAt(0);

        if (campoMontoPagar.getText().trim().length() >= 10 || !Character.isDigit(caracter)) {
            event.consume();
        }
    }

    @FXML
    private boolean aplicarPromocion(ActionEvent event) {
        boolean aplicoPromocion = false;
        etiquetaMontoDescuento.setText("");
        etiquetaDescuento.setVisible(false);
        if (comboPromocion.getSelectionModel().getSelectedItem() != null && !comboPromocion.getSelectionModel().getSelectedItem().equals(comboPromocion.getPromptText())) {
            if (!campoMontoPagar.getText().equals("")) {
                etiquetaDescuento.setVisible(true);
                montoDescueto = Double.parseDouble(campoMontoPagar.getText());
                PromocionDAO promocionDAO = new PromocionDAO();
                Promocion promocionSeleccionada;
                promocionSeleccionada = promocionDAO.adquirirPromocionPorNombre((String) comboPromocion.getValue());
                double valor = promocionSeleccionada.getPorcentajeDescuento();
                double descuento = montoDescueto * (valor / 100);
                montoDescueto = montoDescueto - descuento;
                etiquetaMontoDescuento.setText(Double.toString(montoDescueto));
                aplicoPromocion= true;
            }
        }
        return aplicoPromocion;
    }
}
