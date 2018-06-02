package controlador;

import com.jfoenix.controls.JFXButton;
import java.awt.Color;
import java.awt.Insets;
import java.awt.Panel;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.PopupWindow;
import javax.swing.BorderFactory;
import negocio.CuentaDAO;
import negocio.MaestroDAO;
import negocio.Notificador;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.textfield.TextFields;
import persistencia.Cuenta;



public class VentanaMenuDirectorController implements Initializable {

    @FXML
    private JFXButton botonGrupos;
    @FXML
    private JFXButton botonAlumnos;
    @FXML
    private JFXButton botonRecursosReportes;
    @FXML
    private JFXButton botonMaestros;
    @FXML
    private JFXButton botonRenta;
    @FXML
    private Label etiquetaNombreUsuario;
    @FXML
    private Pane panelPrincipal;
    @FXML
    private JFXButton botonClientes;
    @FXML
    private JFXButton botonAnuncios;
    @FXML
    private Button botonNotificaciones;

    
    
    
    
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    public void desplegarVentanaBusquedaAlumno(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaBuscar.fxml"));
        Parent root = (Parent) loader.load();
        VentanaBuscarController ventanaBuscar = loader.getController();
        ventanaBuscar.obtenerSeccion("Alumnos", panelPrincipal, etiquetaNombreUsuario.getText());
        panelPrincipal.getChildren().clear();
        panelPrincipal.getChildren().add(root);
    }

    @FXML
    public void desplegarVentanaBusquedaMaestro(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaBuscar.fxml"));
        Parent root = (Parent) loader.load();
        VentanaBuscarController ventanaBuscar = loader.getController();
        ventanaBuscar.obtenerSeccion("Maestros", panelPrincipal, etiquetaNombreUsuario.getText());
        panelPrincipal.getChildren().clear();
        panelPrincipal.getChildren().add(root);
    }

    @FXML
    public void desplegarVentanaBusquedaRenta(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaRentas.fxml"));
        Parent root = (Parent) loader.load();
        VentanaRentasController ventanaRentas = loader.getController();
        ventanaRentas.obtenerPanel(panelPrincipal);
        ventanaRentas.llenarTablaRentas();
        panelPrincipal.getChildren().clear();
        panelPrincipal.getChildren().add(root);
    }

    @FXML
    public void desplegarVentanaBusquedaGrupos(ActionEvent event) throws IOException {
        MaestroDAO maestroDAO = new MaestroDAO();
        if (maestroDAO.obtenerNumeroMaestros() > 0) {
            FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaConsultarGrupos.fxml"));
            Parent root = (Parent) loader.load();
            VentanaConsultarGruposController ventanaConsultarGruposController = loader.getController();
            ventanaConsultarGruposController.iniciarVentana(etiquetaNombreUsuario.getText());
            panelPrincipal.getChildren().clear();
            panelPrincipal.getChildren().add(root);
        } else {
            DialogosController.mostrarMensajeInformacion("No hay maestros registrados", "No hay maestros registrados", "Debe haber al menos un maestro registrado para realizar esta acción");
        }
    }

    @FXML
    public void desplegarVentanaBusquedaClientes(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaBuscar.fxml"));
        Parent root = (Parent) loader.load();
        VentanaBuscarController ventanaBuscar = loader.getController();
        ventanaBuscar.obtenerSeccion("Clientes", panelPrincipal, etiquetaNombreUsuario.getText());
        panelPrincipal.getChildren().clear();
        panelPrincipal.getChildren().add(root);
    }

    @FXML
    public void desplegarVentanaFacebook(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaEgresosFb.fxml"));
        Parent root = (Parent) loader.load();
        VentanaEgresosFbController ventanaEgresosFb = loader.getController();
        panelPrincipal.getChildren().clear();
        panelPrincipal.getChildren().add(root);
    }

    @FXML
    private void desplegarVentanaRecursos(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaGenerarReporte.fxml"));
        Parent root = (Parent) loader.load();
        VentanaGenerarReporteController ventanaReporte = loader.getController();
        ventanaReporte.iniciarVentana();
        panelPrincipal.getChildren().clear();
        panelPrincipal.getChildren().add(root);
    }

    @FXML
    public void desplegarVentanaPromociones(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaBuscar.fxml"));
        Parent root = (Parent) loader.load();
        VentanaBuscarController ventanaBuscar = loader.getController();
        ventanaBuscar.obtenerSeccion("Promociones", panelPrincipal, etiquetaNombreUsuario.getText());
        ventanaBuscar.ocultarComponentes();
        panelPrincipal.getChildren().clear();
        panelPrincipal.getChildren().add(root);
    }
    
    public void obtenerUsuario(String usuario) {
        etiquetaNombreUsuario.setText(usuario);
    }

    public void ocultarBotones() {
        botonClientes.setVisible(false);
        botonMaestros.setVisible(false);
        botonRecursosReportes.setVisible(false);
        botonRenta.setVisible(false);
        botonAnuncios.setVisible(false);
    }

    @FXML
    private void mostrarNotificaciones(ActionEvent event) {
        CuentaDAO cuentaDAO = new CuentaDAO();
        Cuenta cuentaUsuario=cuentaDAO.obtenerCuenta(etiquetaNombreUsuario.getText());
        
        Notificador notificador = new Notificador();
        PopOver pop = new PopOver();

        VBox box = new VBox();
        box.setStyle("-fx-background-color: white;");

        if(cuentaUsuario.getTipoCuenta().equals("Director")){
            box.getChildren().addAll(notificador.generarNotificacionesDirector());
        }else{
            box.getChildren().addAll(notificador.generarNotificacionesMaestro(cuentaUsuario));
        }
        
        
        pop.setContentNode(box);
        pop.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
        pop.show(botonNotificaciones);
        
        
        
    }

}
