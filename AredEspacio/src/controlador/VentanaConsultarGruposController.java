package controlador;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import persistencia.Grupo;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import negocio.CuentaDAO;
import negocio.GrupoDAO;
import persistencia.Cuenta;
/**
 * Este controlador es usado para mostrar los grupos que se encuentran activos
 * en el sistema
 *
 * @author Renato Vargas
 * @version 1.0 / 5 de junio de 2018
 */

public class VentanaConsultarGruposController implements Initializable {

    @FXML
    private Label etiquetaGrupos;
    @FXML
    private TableView<Grupo> tablaGrupos;
    @FXML
    private TableColumn columnaNombre;
    @FXML
    private TableColumn columnaAlumnos;
    ObservableList<Grupo> grupos;
    @FXML
    private Button botonCrearGrupo;
    private String usuario;
    @FXML
    private AnchorPane panelConsultarGrupos;
    @FXML
    private JFXButton botonAdministrarHorarios;
    private String unidadPersistencia="AredEspacioPU";

    
    /**
     * Este método establece el tipo de valores que tendrá una tabla de grupos
     *
     * @since 1.0 / 5 de junio de 2018
     */
    private void inicializarTablaGrupos(){
        columnaNombre.setCellValueFactory(new PropertyValueFactory<Grupo, String>("nombreGrupo"));
        grupos = FXCollections.observableArrayList();
        tablaGrupos.setItems(grupos);
        
    }
    
    /**
     * Este método establece los grupos que se mostraran en cada fila dentro de 
     * la tabla.
     *
     * @param nombreUsuario para generar la siguiente y la anteriro ventana
     * @since 1.0 / 5 de junio de 2018
     */
    public void iniciarVentana(String nombreUsuario){
        usuario=nombreUsuario;
        GrupoDAO grupoDAO = new GrupoDAO(unidadPersistencia);
        List<Cuenta> cuentas = new ArrayList();
        cuentas=grupoDAO.adquirirCuentas();
        CuentaDAO cuentaDAO = new CuentaDAO();
        Cuenta usuarioActual=new Cuenta();
        usuarioActual=cuentaDAO.obtenerCuenta(nombreUsuario);
        
        if(usuarioActual.getTipoCuenta().equals("Maestro")){
            botonAdministrarHorarios.setVisible(false);
            botonCrearGrupo.setVisible(false);
        }
        
        Grupo grupo = new Grupo();
        List<Grupo> listaGrupos=null;
        listaGrupos=grupoDAO.adquirirGrupos(usuarioActual);//////////////
        this.inicializarTablaGrupos();
        final ObservableList<Grupo> tablaGrupoSel = tablaGrupos.getSelectionModel().getSelectedItems();
        if(listaGrupos!=null){
            for(int i=0; i<listaGrupos.size(); i++){
                if(listaGrupos.get(i).getEstaActivo()==1){
                    grupos.add(listaGrupos.get(i));
                }
                
            }
        }        
        
        
        tablaGrupos.setOnMousePressed(new EventHandler<MouseEvent>() {
        @Override
            public void handle(MouseEvent event) {
                if(tablaGrupos.getSelectionModel().getSelectedItem()!=null){
                    FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaConsultarInformacionGrupo.fxml"));
                    try{
                        Parent root = (Parent) loader.load();
                        VentanaConsultarInformacionGrupoController ventanaConsultarInformacionGrupoController = loader.getController();
                        ventanaConsultarInformacionGrupoController.establecerGrupo(tablaGrupos.getSelectionModel().getSelectedItem().getIdGrupo(), usuario);////////manejar excepción en caso de estar vacio
                        panelConsultarGrupos.getChildren().add(root);
                    }catch(IOException ex){
                        Logger.getLogger (VentanaConsultarGruposController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }   
        });
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @FXML
    private void desplegarVentanaCrearGrupo(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaCrearGrupo.fxml"));
        Parent root = (Parent) loader.load();
        VentanaCrearGrupoController crearGrupo = loader.getController();
        crearGrupo.iniciarVentana(usuario);
        panelConsultarGrupos.getChildren().add(root);
    } 

    @FXML
    private void desplegarVentanaAdministrarHorarios(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaAdministrarHorarios.fxml"));
        Parent root = (Parent) loader.load();
        VentanaAdministrarHorariosController administrarGrupo = loader.getController();
        administrarGrupo.iniciarVentana(usuario);
        panelConsultarGrupos.getChildren().add(root);
        
    }
}
