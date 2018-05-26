/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import negocio.CuentaDAO;
import negocio.GrupoDAO;
import negocio.MaestroDAO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import persistencia.Alumno;
import persistencia.Cuenta;
import persistencia.Grupo;
import persistencia.Horario;
import persistencia.Maestro;

/**
 * FXML Controller class
 *
 * @author Equipo
 */
public class VentanaConsultarInformacionGrupoController implements Initializable {

    @FXML
    private TableView<Alumno> tablaAlumnos;
    @FXML
    private TableView<Horario> tablaHorario;
    @FXML
    private Label etiquetaMaestro;
    @FXML
    private Label etiquetaInscripcion;
    @FXML
    private Label etiquetaMensualidad;
    @FXML
    private JFXButton botonEditar;
    private String nombreUsuario;
    @FXML
    private JFXButton botonEliminar;
    @FXML
    private JFXButton botonRegresar;
    @FXML
    private Label etiquetaPrecioInscripcion;
    @FXML
    private Label etiquetaPrecioMensualidad;
    @FXML
    private Label etiquetaNombreMaestro;
    @FXML
    private TableColumn columnaDia;
    @FXML
    private TableColumn columnaHora;
    
    ObservableList<Horario> horarios;
    ObservableList<Alumno> alumnos;
    String [] etiquetasHorasXML;
    String [] horasNumericas;
    @FXML
    private TableColumn columnaAlumnos;
    @FXML
    private Pane panelConsultarInformacion;
    @FXML
    private AnchorPane panelConsultarInfo;
    
    private int idGrupo;
    private String nombreGrupo;
    private String rutaXML=System.getProperty("user.dir") + "\\Archivos\\Horarios.xml";
    private String unidadPersistencia="AredEspacioPU";
    @FXML
    private JFXButton botonRegistrarAsistencia;
    boolean esMaestro=false;
    private String nombreCuentaMaestro;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }   
    
    public void iniciarTablaHorario(){
        columnaDia.setCellValueFactory(new PropertyValueFactory<Horario, String>("dia"));
        columnaHora.setCellValueFactory(new PropertyValueFactory<Horario, String>("hora"));
        horarios = FXCollections.observableArrayList();
        tablaHorario.setItems(horarios);
    }
    
    public void iniciarTablaAlumnos(){
        columnaAlumnos.setCellValueFactory(new PropertyValueFactory<Horario, String>("nombre"));
        alumnos = FXCollections.observableArrayList();
        tablaAlumnos.setItems(alumnos);
    }
    
    public void llenarTablaHorario(String nombreGrupo){
        int numeroClases=0;
        Horario[] arregloHorarios;
        Horario[] horariosAgregar;
        
        
        try{
            File inputFile = new File(rutaXML);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("dia");
            numeroClases=conseguirNumeroDeClases(nList, nombreGrupo);
            arregloHorarios = new Horario[numeroClases];
            arregloHorarios=establecerArreglo(nList, numeroClases, nombreGrupo);
            for(int i=0; i<numeroClases; i++){
                horarios.add(arregloHorarios[i]);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void llenarTablaAlumnos(String nombreGrupo){
        GrupoDAO grupoDAO = new GrupoDAO(unidadPersistencia);
        List<Alumno> listaAlumnos=null;
        listaAlumnos=grupoDAO.obtenerAlumnos(idGrupo);
        for(int i=0; i<listaAlumnos.size(); i++){
            alumnos.add(listaAlumnos.get(i));
        }
        
        
        
        
    }
    
    public int conseguirNumeroDeClases( NodeList nList, String nombreGrupo){
        int numeroClases=0;
        String nombreElemento;
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                for(int i=0; i<30; i++){
                    nombreElemento=eElement.getElementsByTagName(etiquetasHorasXML[i]).item(0).getTextContent();
                    if(nombreElemento.equals(nombreGrupo)){
                        numeroClases++;
                    }
                }
            }
        }
        return numeroClases;
    
    }
    
    public Horario[] establecerArreglo(NodeList nList, int numeroClases, String nombreGrupo){ 
        Horario[] arregloHorarios;
        arregloHorarios=new Horario[numeroClases];
        String nombreElemento;
        int contadorHorarios=0;
        
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                for(int i=0; i<24; i++){
                    nombreElemento=eElement.getElementsByTagName(etiquetasHorasXML[i]).item(0).getTextContent();
                    if(nombreElemento.equals(nombreGrupo)){
                        arregloHorarios[contadorHorarios]=establecerElementoHorario(temp, horasNumericas[i]);
                        contadorHorarios++;
                    } 
                }
            }           
        }           
        return arregloHorarios;
    }
    
    public String establecerDia(int temp){
        String dia="";
        switch(temp){
            case 0:
                dia="Domingo";
            break;
            case 1:
                dia="Lunes";
            break;
            case 2:
                dia="Martes";
            break;
            case 3:
                dia="Miercoles";
            break;
            case 4:
                dia="Jueves";
            break;
            case 5:
                dia="Viernes";
            break;
            case 6:
                dia="Sabado";
            break;
        }
        return dia;
    }
    
    public Horario establecerElementoHorario(int temp, String horaNumerica){
        Horario nuevoHorario = new Horario();
        nuevoHorario=new Horario();
        nuevoHorario.setDia(establecerDia(temp));
        nuevoHorario.setHora(horaNumerica);
        return nuevoHorario;
        
    }
    
    public String[] establecerEtiquetas(){
        String[] etiquetas = new String[30];
        etiquetas[0]="ocho-ochoMedia";
        etiquetas[1]="ochoMedia-nueve";
        etiquetas[2]="nueve-nueveMedia";
        etiquetas[3]="nueveMedia-diez";
        etiquetas[4]="diez-diezMedia";
        etiquetas[5]="diezMedia-once";
        etiquetas[6]="once-onceMedia";
        etiquetas[7]="onceMedia-doce";
        etiquetas[8]="doce-doceMedia";
        etiquetas[9]="doceMedia-una";
        etiquetas[10]="una-unaMedia";
        etiquetas[11]="unaMedia-dos";
        etiquetas[12]="dos-dosMedia";
        etiquetas[13]="dosMedia-tres";
        etiquetas[14]="tres-tresMedia";
        etiquetas[15]="tresMedia-cuatro";
        etiquetas[16]="cuatro-cuatroMedia";
        etiquetas[17]="cuatroMedia-cinco";
        etiquetas[18]="cinco-cincoMedia";
        etiquetas[19]="cincoMedia-seis";
        etiquetas[20]="seis-seisMedia";
        etiquetas[21]="seisMedia-siete";
        etiquetas[22]="siete-sieteMedia";
        etiquetas[23]="sieteMedia-ocho";
        etiquetas[24]="ocho-ochoMediaPm";
        etiquetas[25]="ochoMedia-nuevePm";
        etiquetas[26]="nueve-nueveMediaPm";
        etiquetas[27]="nueveMedia-diezPm";
        etiquetas[28]="diez-diezMediaPm";
        etiquetas[29]="diezMedia-oncePm";
        return etiquetas;
    }
    
    public String[] establecerHoras(){
        String[] etiquetas = new String[30];
        etiquetas[0]="8:00-8:30";
        etiquetas[1]="8:30-9:00";
        etiquetas[2]="9:00-9:30";
        etiquetas[3]="9:30-10:00";
        etiquetas[4]="10:00-10:30";
        etiquetas[5]="10:30-11:00";
        etiquetas[6]="11:00-11:30";
        etiquetas[7]="11:30-12:00";
        etiquetas[8]="12:00-12:30";
        etiquetas[9]="12:30-13:00";
        etiquetas[10]="13:00-13:30";
        etiquetas[11]="13:30-14:00";
        etiquetas[12]="14:00-14:30";
        etiquetas[13]="14:30-15:00";
        etiquetas[14]="15:00-15:30";
        etiquetas[15]="15:30-16:00";
        etiquetas[16]="16:00-16:30";
        etiquetas[17]="16:30-17:00";
        etiquetas[18]="17:00-17:30";
        etiquetas[19]="17:30-18:00";
        etiquetas[20]="18:00-18:30";
        etiquetas[21]="18:30-19:00";
        etiquetas[22]="19:00-19:30";
        etiquetas[23]="19:30-20:00";
        etiquetas[24]="20:00-20:30";
        etiquetas[25]="20:30-21:00";
        etiquetas[26]="21:00-21:30";
        etiquetas[27]="21:30-22:00";
        etiquetas[28]="22:00-22:30";
        etiquetas[29]="22:30-23:00";
        return etiquetas;
    }
    
    public void establecerGrupo(int idGrupo, String nombreUsuarioActual){
        nombreUsuario=nombreUsuarioActual;
        CuentaDAO cuentaDAO = new CuentaDAO();
        Cuenta cuentaAdquirirda=cuentaDAO.obtenerCuenta(nombreUsuario);
        if(cuentaAdquirirda.getTipoCuenta().equals("Maestro")){
            botonEliminar.setVisible(false);
            botonEditar.setVisible(false);
            esMaestro=true;
        }
        this.idGrupo=idGrupo;
        GrupoDAO grupoDAO = new GrupoDAO(unidadPersistencia);
        Grupo grupoConsultado= new Grupo();
        grupoConsultado=grupoDAO.adquirirGrupo(idGrupo);
        nombreCuentaMaestro=grupoConsultado.getUsuario().getUsuario();
        nombreGrupo=grupoConsultado.getNombreGrupo();
        etiquetaNombreMaestro.setText(grupoConsultado.getUsuario().getUsuario());
        etiquetaPrecioInscripcion.setText(grupoConsultado.getInscripcion().toString());
        etiquetaPrecioMensualidad.setText(grupoConsultado.getMensualidad().toString());
        etiquetasHorasXML= new String[30];
        etiquetasHorasXML=establecerEtiquetas();
        horasNumericas= new String [30];
        horasNumericas= establecerHoras();
        this.iniciarTablaHorario();
        this.llenarTablaHorario(grupoConsultado.getNombreGrupo());
        this.iniciarTablaAlumnos();
        this.llenarTablaAlumnos(grupoConsultado.getNombreGrupo()); 
        final ObservableList<Alumno> tablaGrupoSel = tablaAlumnos.getSelectionModel().getSelectedItems();
        tablaAlumnos.setOnMousePressed(new EventHandler<MouseEvent>() {
        @Override
            public void handle(MouseEvent event) {
                if((tablaAlumnos.getSelectionModel().getSelectedItem()!=null) &&(!esMaestro) ){
                    FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaRegistrarPagoAlumnoDireccion.fxml"));
                    try{
                        Parent root = (Parent) loader.load();
                        VentanaRegistrarPagoAlumnoDireccionController ventanaRegistrarPago = loader.getController();
                        ventanaRegistrarPago.establecerDatos(tablaAlumnos.getSelectionModel().getSelectedItem(), nombreUsuario, idGrupo);
                        panelConsultarInfo.getChildren().add(root);
                    }catch(IOException ex){
                        Logger.getLogger (VentanaConsultarGruposController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }   
        });
    }

    @FXML
    private void desplegarVentanaEditar(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaEditarGrupo.fxml"));
        try{
            Parent root = (Parent) loader.load();
            VentanaEditarGrupoController editarGrupoController = loader.getController();
            editarGrupoController.establecerGrupo(idGrupo, nombreUsuario);////////manejar excepción en caso de estar vacio
            panelConsultarInfo.getChildren().add(root);
        }catch(IOException ex){
            Logger.getLogger (VentanaConsultarGruposController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /*Stage stage = (Stage) panelConsultarInfo.getScene().getWindow();
        stage.close();*/
        
        
    }

    @FXML
    private void eliminarGrupo(ActionEvent event) throws IOException {
        GrupoDAO nuevoGrupoDAO = new GrupoDAO(unidadPersistencia);
        CuentaDAO cuentaDAO = new CuentaDAO();
        MaestroDAO maestroDAO = new MaestroDAO();
        Cuenta cuentaMaestro = cuentaDAO.obtenerCuenta(nombreCuentaMaestro);
        List<Grupo> listaGrupos=null;
        listaGrupos=nuevoGrupoDAO.adquirirGrupos(cuentaMaestro);
        
        /////Dar de baja a todos los alumnos
        Grupo grupoEditar = nuevoGrupoDAO.adquirirGrupo(idGrupo);
        List<Alumno> listaAlumnos = nuevoGrupoDAO.obtenerAlumnos(idGrupo);
        if(!listaAlumnos.isEmpty()){
            listaAlumnos.clear();
            grupoEditar.setAlumnoCollection(listaAlumnos);
            nuevoGrupoDAO.editarGrupo(grupoEditar);
        }
        
        //Desactivar Maestro
        int gruposActivos=0;
        int idGrupoActivo=0;
        if(cuentaMaestro.getTipoCuenta().equals("Maestro")){
            String nombreMaestroEditar =maestroDAO.adquirirNombreMaestroPorNombreDeUsuario(nombreCuentaMaestro);
            Maestro maestroEditar = maestroDAO.adquirirMaestro(nombreCuentaMaestro);
            
            

            for(int i=0; i<listaGrupos.size(); i++){
                if(listaGrupos.get(i).getEstaActivo()==1){
                    gruposActivos++;
                    idGrupoActivo=listaGrupos.get(i).getIdGrupo();
                }
            }
            if(gruposActivos==1){
                if(idGrupoActivo==idGrupo){
                    maestroEditar.setEstaActivo(0);
                    ////null
                    maestroDAO.editarMaestro(maestroEditar); 
                }
            } 
        }
        
        //Descativar Grupo
        Grupo grupoEliminar = new Grupo();
        grupoEliminar=nuevoGrupoDAO.adquirirGrupo(idGrupo);
        grupoEliminar.setEstaActivo(0);
        if(nuevoGrupoDAO.eliminarGrupo(grupoEliminar)){
            eliminarHorarioGrupo();
            DialogosController.mostrarMensajeInformacion("Eliminado", "El grupo ha sido eliminado", "El grupo se desactivo corectamente");
            FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaConsultarGrupos.fxml"));
            Parent root = (Parent) loader.load();
            VentanaConsultarGruposController ventanaConsultarGruposController = loader.getController();
            ventanaConsultarGruposController.iniciarVentana(nombreUsuario);
            panelConsultarInfo.getChildren().add(root); 
        }else{
            DialogosController.mostrarMensajeInformacion("Error", "Parece haber ocurrido un error y el grupo no pudó ser eliminado correctamente", "Por favor contecte al encargado del sistema");
        }
    }
    

    private void eliminarHorarioGrupo(){
        try {
            File inputFile = new File(rutaXML);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("dia");
            for(int i =0; i<etiquetasHorasXML.length; i++){
                actualizarFilaEliminar(nList, doc, etiquetasHorasXML[i]);
            }    
        }catch (Exception e) {
           e.printStackTrace();
        }
        
        
    }
    public void actualizarFilaEliminar(NodeList nList,  Document doc, String nombreFila) throws TransformerException{
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp); //cada item es un dia de la semana
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;


                if(eElement.getElementsByTagName(nombreFila).item(0).getTextContent().equals(nombreGrupo)){
                    eElement.getElementsByTagName(nombreFila).item(0).setTextContent("Disponible");
                }
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(rutaXML));
                transformer.transform(source, result);
            }
        }
    }

    @FXML
    private void regresarPantallaAnterior(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaConsultarGrupos.fxml"));
        Parent root = (Parent) loader.load();
        VentanaConsultarGruposController ventanaConsultarGruposController = loader.getController();
        ventanaConsultarGruposController.iniciarVentana(nombreUsuario);
        panelConsultarInfo.getChildren().add(root);
    }

    @FXML
    private void desplegarVentanaRegistrarAsistencia(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaRegistrarAsistencia.fxml"));
        Parent root = (Parent) loader.load();
        VentanaRegistrarAsistenciaController ventanaRegistrarAsistenciaController = loader.getController();
        ventanaRegistrarAsistenciaController.iniciarVentana(idGrupo, nombreUsuario);
        panelConsultarInfo.getChildren().add(root);
    }
}
