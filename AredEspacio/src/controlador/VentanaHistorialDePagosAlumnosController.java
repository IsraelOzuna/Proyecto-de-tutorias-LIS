package controlador;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import persistencia.Alumno;
import persistencia.Pagoalumno;

/**
 * Este controlador es usado para manipular la interfaz gráfica para consultar
 * el historial de pago de los alumnos
 *
 * @author Irvin Vera
 * @version 1.0 / 5 de junio de 2018
 */
public class VentanaHistorialDePagosAlumnosController implements Initializable {

    private Pane panelPrincipal;
    @FXML
    private Label etiquetaNombreAlumno;
    @FXML
    private Button botonRegresar;
    @FXML
    private TableColumn<persistencia.Pagoalumno, String> columnaFechaPago;
    @FXML
    private TableColumn<persistencia.Pagoalumno, Double> columnaCantidad;

    private String nombreAlumno;
    @FXML
    private TableView<persistencia.Pagoalumno> tablaPagos;
    private String rutaFoto;
    @FXML
    private AnchorPane panelTrasero;
    @FXML
    private ImageView imagenPerfil;

    private Alumno alumno;

    /**
     * Este método permite obtener el panel sobre el cual se mostrará la ventana
     * del hostorial de pago de los alumnos
     *
     * @param panelPrincipal sobre el cual se mostrará la ventana del hostorial
     * de pago de los alumnos
     * @param nombreAlumno nombre del alumno
     * @param rutaFoto ruta de almacenamiento de la doto del alumno
     */
    public void obtenerDatos(Pane panelPrincipal, String nombreAlumno, String rutaFoto) {
        this.panelPrincipal = panelPrincipal;
        this.nombreAlumno = nombreAlumno;
        this.rutaFoto = rutaFoto;

    }

    /**
     * Este método servie para llenar la tabla con los pagos de los alumnos
     *
     * @param pagosAlumnos una lista que contiene la informacción de los pagos
     * del alumno
     */
    public void llenarTablaPagos(List<Pagoalumno> pagosAlumnos) {
        try {
            etiquetaNombreAlumno.setText(nombreAlumno);

            CodeSource direccion = VentanaHistorialDePagosAlumnosController.class.getProtectionDomain().getCodeSource();
            File fileJar = new File(direccion.getLocation().toURI().getPath());
            File fileDir = fileJar.getParentFile();
            File fileProperties = new File(fileDir.getAbsolutePath());

            String ruta = fileProperties.getAbsolutePath();

            if (rutaFoto != null) {
                Image foto = new Image("file:" + ruta + "/imagenesAlumnos/" + rutaFoto, 100, 100, false, true, true);
                imagenPerfil.setImage(foto);
            }

            columnaFechaPago.setCellValueFactory(new PropertyValueFactory<persistencia.Pagoalumno, String>("formatoFecha"));

            columnaCantidad.setCellValueFactory(new PropertyValueFactory<persistencia.Pagoalumno, Double>("cantidad"));

            tablaPagos.setItems(FXCollections.observableList(pagosAlumnos));
        } catch (URISyntaxException ex) {
            Logger.getLogger(VentanaHistorialDePagosAlumnosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void cerrarVentanaHistorialDePagos(ActionEvent event) {
        panelTrasero.setVisible(false);
    }

}
