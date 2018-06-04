package controlador;

import com.jfoenix.controls.JFXComboBox;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * Este controlador es usado para registrar las mensualidades de los alumnos
 *
 * @author Israel Reyes Ozuna
 * @version 1.0 / 5 de junio de 2018
 */
public class VentanaRegistrarMensualidadAlumnoController implements Initializable {

    @FXML
    private Label etiquetaNombreAlumno;
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

                    if (aplicarPromocion(event)) {
                        pagoAlumno.setCantidad(montoDescueto);
                    } else {
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

    /**
     * Este método carga la foto y el nombre del alumno seleccionado
     *
     * @param rutaFotoAlumno ruta de donde se obtendrá la foto del alumno
     * @param nombreAlumno que se mostrará
     * @param apellidosAlumno que se mostrará
     * @param idAlumno para encontrar el alumno y registrarle un pago
     * @since 1.0 / 5 de junio de 2018
     */
    public void llenarDatos(String rutaFotoAlumno, String nombreAlumno, String apellidosAlumno, int idAlumno) {
        try {
            this.idAlumno = idAlumno;
            etiquetaNombreAlumno.setText(nombreAlumno + " " + apellidosAlumno);
            CodeSource direccion = VentanaRegistrarMensualidadAlumnoController.class.getProtectionDomain().getCodeSource();
            File fileJar = new File(direccion.getLocation().toURI().getPath());
            File fileDir = fileJar.getParentFile();
            File fileProperties = new File(fileDir.getAbsolutePath());

            String rutaFoto = fileProperties.getAbsolutePath();
            if (rutaFotoAlumno != null) {
                Image foto = new Image("file:" + rutaFoto + "/imagenesAlumnos/" + rutaFotoAlumno, 100, 100, false, true, true);
                imagenPerfil.setImage(foto);
            }
            llenarComboPromocion();
            llenarComboGrupos();
        } catch (URISyntaxException ex) {
            Logger.getLogger(VentanaRegistrarMensualidadAlumnoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Este método carga los grupos en los que se encuentra inscrito el alumno
     * para seleccionar a cual se le hará el pago
     *
     * @param gruposAlumno lista de grupos encontrados que están relacionados
     * con el alumno
     * @return List con todos grupos obtenidos
     * @since 1.0 / 5 de junio de 2018
     */
    public ArrayList<String> obtenerNombreGrupos(List<String> gruposAlumno) {
        ArrayList<String> nombreGrupos = new ArrayList();
        AlumnoDAO alumnoDAO = new AlumnoDAO();

        gruposAlumno = alumnoDAO.encontrarGruposAlumno(idAlumno);
        for (int i = 0; i < gruposAlumno.size(); i++) {
            nombreGrupos.add(gruposAlumno.get(i));
        }
        return nombreGrupos;
    }

    /**
     * Este método carga el nombre de los grupos en donde el alumno está
     * inscrito
     *
     * @since 1.0 / 5 de junio de 2018
     */
    public void llenarComboGrupos() {
        ObservableList<String> nombreGrupos = FXCollections.observableArrayList();
        nombreGrupos.addAll(obtenerNombreGrupos(gruposAlumno));
        comboGruposAlumno.setItems(nombreGrupos);
    }

    /**
     * Este método carga las promociones que pueden ser aplicadas para las
     * mensualidades
     *
     * @since 1.0 / 5 de junio de 2018
     */
    public void llenarComboPromocion() {
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
                aplicoPromocion = true;
            }
        }
        return aplicoPromocion;
    }
}
