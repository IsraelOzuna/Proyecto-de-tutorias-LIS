package controlador;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
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

    public void obtenerDatos(Pane panelPrincipal, String nombreAlumno, String rutaFoto) {
        this.panelPrincipal = panelPrincipal;
        this.nombreAlumno = nombreAlumno;
        this.rutaFoto = rutaFoto;
      
    }

    public void llenarTablaPagos(List<Pagoalumno> pagosAlumnos) {
        etiquetaNombreAlumno.setText(nombreAlumno);
        

        if (rutaFoto != null) {
            Image foto = new Image("file:" + System.getProperty("user.dir") + "/imagenesAlumnos/" + rutaFoto, 100, 100, false, true, true);
            imagenPerfil.setImage(foto);
        }

        columnaFechaPago.setCellValueFactory(new PropertyValueFactory<persistencia.Pagoalumno, String>("formatoFecha"));

        columnaCantidad.setCellValueFactory(new PropertyValueFactory<persistencia.Pagoalumno, Double>("cantidad"));

        tablaPagos.setItems(FXCollections.observableList(pagosAlumnos));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @FXML
    private void cerrarVentanaHistorialDePagos(ActionEvent event) {
        panelTrasero.setVisible(false);
    }

}
