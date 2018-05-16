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
import javafx.scene.layout.Pane;
import negocio.PagoMensualidadAlumnoDAO;

public class VentanaHistorialDePagosAlumnosController implements Initializable {

    private Pane panelPrincipal;
    @FXML
    private Label etiquetaNombreAlumno;
    @FXML
    private Button botonRegresar;
    @FXML
    private TableColumn<persistencia.Pagoalumno, String> columnaNombreGrupo;
    @FXML
    private TableColumn<persistencia.Pagoalumno, String> columnaFechaPago;
    @FXML
    private TableColumn<persistencia.Pagoalumno, Double> columnaCantidad;

    private String nombreAlumno;
    @FXML
    private TableView<persistencia.Pagoalumno> tablaPagos;

    public void obtenerDatos(Pane panelPrincipal, String nombreAlumno) {
        this.panelPrincipal = panelPrincipal;
        this.nombreAlumno = nombreAlumno;
    }

    public void llenarTablaPagos(int id) {

        List<persistencia.Pagoalumno> listaPagos = null;
        PagoMensualidadAlumnoDAO pagoMensualidadAlumno = new PagoMensualidadAlumnoDAO();
        listaPagos = pagoMensualidadAlumno.obtenerPagosAlumno(id);

      //  columnaNombreGrupo.setCellValueFactory(new PropertyValueFactory<persistencia.Pagoalumno, String>("nombreGrupo"));

        columnaFechaPago.setCellValueFactory(new PropertyValueFactory<persistencia.Pagoalumno, String>("formatoFecha"));

        columnaCantidad.setCellValueFactory(new PropertyValueFactory<persistencia.Pagoalumno, Double>("cantidad"));
        
        tablaPagos.setItems(FXCollections.observableList(listaPagos));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        etiquetaNombreAlumno.setText(nombreAlumno);
    }

    @FXML
    private void editarRenta(ActionEvent event) {
    }

}
