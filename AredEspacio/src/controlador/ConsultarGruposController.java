/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Insets;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.control.cell.TextFieldTableCell;
import persistencia.Alumno;
import persistencia.Grupo;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import negocio.GrupoDAO;
import persistencia.Cuenta;

/**
 *
 * @author Equipo
 */
public class ConsultarGruposController implements Initializable {

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

    @FXML
    private AnchorPane panelConsultarGrupos;
    
    private void inicializarTablaGrupos(){
        columnaNombre.setCellValueFactory(new PropertyValueFactory<Grupo, String>("nombreGrupo"));
        grupos = FXCollections.observableArrayList();
        tablaGrupos.setItems(grupos);
    }
    
    public void iniciarVentana(){
        GrupoDAO grupoDAO = new GrupoDAO();
        Grupo grupo = new Grupo();
        List<Grupo> listaGrupos=null;
        Cuenta cuentaNueva = new Cuenta();
        listaGrupos=grupoDAO.adquirirGrupos(cuentaNueva);//////////////
        System.out.println("Lista de Grupos");
        System.out.println("\t"+listaGrupos);
        this.inicializarTablaGrupos();
        final ObservableList<Grupo> tablaGrupoSel = tablaGrupos.getSelectionModel().getSelectedItems();
        for(int i=0; i<listaGrupos.size(); i++){
            //Grupo g1 = new Grupo();
            //g1.setNombreGrupo("Grupo"+i);
            grupos.add(listaGrupos.get(i));
        }
        
        tablaGrupos.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaConsultarInformacionGrupo.fxml"));
                    try{
                        loader.load();
                    }catch(IOException ex){
                        Logger.getLogger (ConsultarGruposController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    VentanaConsultarInformacionGrupoController ventanaConsultarInformacionGrupoController = loader.getController();
                    ventanaConsultarInformacionGrupoController.establecerGrupo(tablaGrupos.getSelectionModel().getSelectedItem().getNombreGrupo());
                    Parent p = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(p));
                    stage.show();
                    //System.out.println(tablaGrupos.getSelectionModel().getSelectedItem().getNombreGrupo());
                }   
            });
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @FXML
    private void desplegarNuevoGrupo(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaCrearGrupo.fxml"));
        Parent root = (Parent) loader.load();
        CrearGrupoController crearGrupo = loader.getController();
        crearGrupo.iniciarVentana();
        //ventanaBuscar.obtenerSeccion("Alumnos", panelGrupos);
        panelConsultarGrupos.getChildren().add(root);
        
    }

    
    
    
}

