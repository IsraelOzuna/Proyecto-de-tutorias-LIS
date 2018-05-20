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

/**
 * FXML Controller class
 *
 * @author iro19
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
    private Label etiquetaFechaPago;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void registrarMensualidad(ActionEvent event) {
        boolean cantidadValida = true;
        Date fechaPago = new Date();
        if (comboGruposAlumno.getSelectionModel().getSelectedItem() != null && !campoMontoPagar.getText().trim().isEmpty()) {
            try {
                Double.parseDouble(campoMontoPagar.getText());
            } catch (NumberFormatException ex) {
                DialogosController.mostrarMensajeInformacion("Invalido", "Cantidad invalida", "Ingresa una cantidad valida");
                cantidadValida = false;
            }
            if (cantidadValida) {
                PagoMensualidadAlumno pagoAlumno = new PagoMensualidadAlumno();
                PagoMensualidadAlumnoDAO pagoInscripcion = new PagoMensualidadAlumnoDAO();
                
                pagoAlumno.setCantidad(Double.parseDouble(campoMontoPagar.getText().trim()));
                pagoAlumno.setFechaPagoInscripcion(fechaPago);
                pagoAlumno.setIdAlumno(idAlumno);
                pagoAlumno.setNombreGrupo(comboGruposAlumno.getValue());
                pagoAlumno.setTipoPago('1');
                
                if(pagoInscripcion.registrarMensualidad(pagoAlumno, idAlumno, comboGruposAlumno.getValue())){
                    DialogosController.mostrarMensajeInformacion("", "Registro de pago exitoso", "El pago se ha registrado correctamente");
                    panelTrasero.setVisible(false);
                }
            }
        }else{
            DialogosController.mostrarMensajeAdvertencia("", "Campos vacios", "Llene ambos campos");
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
            Image foto = new Image("file:"+ System.getProperty("user.dir") +"\\imagenesAlumnos\\" + rutaFotoAlumno, 100, 100, false, true, true);
            imagenPerfil.setImage(foto);
        }
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

    @FXML
    private void limitarCampoMonto(KeyEvent event) {
        char caracter = event.getCharacter().charAt(0);

        if (campoMontoPagar.getText().trim().length() >= 10 || !Character.isDigit(caracter)) {
            event.consume();
        }
    }
}
