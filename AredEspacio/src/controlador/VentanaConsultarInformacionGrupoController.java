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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private String rutaXML="C:\\Users\\Renato\\Documents\\NetBeansProjects\\AredEspacio\\src\\Archivos\\Horarios.xml";

    

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
        GrupoDAO grupoDAO = new GrupoDAO();
        List<Alumno> listaAlumnos=null;
        listaAlumnos=grupoDAO.obtenerAlumnos(nombreGrupo);
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
                    nombreElemento=eElement.getElementsByTagName("ocho-ochoMedia").item(0).getTextContent();
                    //System.out.println(eElement.getElementsByTagName("ocho-ochoMedia").item(0).getTextContent());
                    if(nombreElemento.equals(nombreGrupo)){
                        numeroClases++;
                    }
                    nombreElemento=eElement.getElementsByTagName("ochoMedia-nueve").item(0).getTextContent();
                    if(nombreElemento.equals(nombreGrupo)){
                        numeroClases++;
                    }
                    nombreElemento=eElement.getElementsByTagName("nueve-nueveMedia").item(0).getTextContent();
                    if(nombreElemento.equals(nombreGrupo)){
                        numeroClases++;
                    }
                    nombreElemento=eElement.getElementsByTagName("nueveMedia-diez").item(0).getTextContent();
                    if(nombreElemento.equals(nombreGrupo)){
                        numeroClases++;
                    }
                    nombreElemento=eElement.getElementsByTagName("diez-diezMedia").item(0).getTextContent();
                    if(nombreElemento.equals(nombreGrupo)){
                        numeroClases++;
                    }
                    nombreElemento=eElement.getElementsByTagName("diezMedia-once").item(0).getTextContent();
                    if(nombreElemento.equals(nombreGrupo)){
                        numeroClases++;
                    }
                    nombreElemento=eElement.getElementsByTagName("once-onceMedia").item(0).getTextContent();
                    if(nombreElemento.equals(nombreGrupo)){
                        numeroClases++;
                    }
                    nombreElemento=eElement.getElementsByTagName("onceMedia-doce").item(0).getTextContent();
                    if(nombreElemento.equals(nombreGrupo)){
                        numeroClases++;
                    }
                    nombreElemento=eElement.getElementsByTagName("doce-doceMedia").item(0).getTextContent();
                    if(nombreElemento.equals(nombreGrupo)){
                        numeroClases++;
                    }
                    nombreElemento=eElement.getElementsByTagName("doceMedia-una").item(0).getTextContent();
                    if(nombreElemento.equals(nombreGrupo)){
                        numeroClases++;
                    }
                    nombreElemento=eElement.getElementsByTagName("una-unaMedia").item(0).getTextContent();
                    if(nombreElemento.equals(nombreGrupo)){
                        numeroClases++;
                    }
                    nombreElemento=eElement.getElementsByTagName("unaMedia-dos").item(0).getTextContent();
                    if(nombreElemento.equals(nombreGrupo)){
                        numeroClases++;
                    }
                    nombreElemento=eElement.getElementsByTagName("dos-dosMedia").item(0).getTextContent();
                    if(nombreElemento.equals(nombreGrupo)){
                        numeroClases++;
                    }
                    nombreElemento=eElement.getElementsByTagName("dosMedia-tres").item(0).getTextContent();
                    if(nombreElemento.equals(nombreGrupo)){
                        numeroClases++;
                    }
                    nombreElemento=eElement.getElementsByTagName("tres-tresMedia").item(0).getTextContent();
                    if(nombreElemento.equals(nombreGrupo)){
                        numeroClases++;
                    }
                    nombreElemento=eElement.getElementsByTagName("tresMedia-cuatro").item(0).getTextContent();
                    if(nombreElemento.equals(nombreGrupo)){
                        numeroClases++;
                    }
                    nombreElemento=eElement.getElementsByTagName("cuatro-cuatroMedia").item(0).getTextContent();
                    if(nombreElemento.equals(nombreGrupo)){
                        numeroClases++;
                    }
                    nombreElemento=eElement.getElementsByTagName("cuatroMedia-cinco").item(0).getTextContent();
                    if(nombreElemento.equals(nombreGrupo)){
                        numeroClases++;
                    }
                    nombreElemento=eElement.getElementsByTagName("cinco-cincoMedia").item(0).getTextContent();
                    if(nombreElemento.equals(nombreGrupo)){
                        numeroClases++;
                    }
                    nombreElemento=eElement.getElementsByTagName("cincoMedia-seis").item(0).getTextContent();
                    if(nombreElemento.equals(nombreGrupo)){
                        numeroClases++;
                    }
                    nombreElemento=eElement.getElementsByTagName("seis-seisMedia").item(0).getTextContent();
                    if(nombreElemento.equals(nombreGrupo)){
                        numeroClases++;
                    }
                    nombreElemento=eElement.getElementsByTagName("seisMedia-siete").item(0).getTextContent();
                    if(nombreElemento.equals(nombreGrupo)){
                        numeroClases++;
                    }
                    nombreElemento=eElement.getElementsByTagName("siete-sieteMedia").item(0).getTextContent();
                    if(nombreElemento.equals(nombreGrupo)){
                        numeroClases++;
                    }
                    nombreElemento=eElement.getElementsByTagName("sieteMedia-ocho").item(0).getTextContent();
                    if(nombreElemento.equals(nombreGrupo)){
                        numeroClases++;
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
        String[] etiquetas = new String[24];
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
        return etiquetas;
    }
    
    public String[] establecerHoras(){
        String[] etiquetas = new String[24];
        etiquetas[0]="8:00-8:30";
        etiquetas[1]="8:30-9:00";
        etiquetas[2]="9:00-9:30";
        etiquetas[3]="9:30-10:00";
        etiquetas[4]="10:00-10:30";
        etiquetas[5]="10:30-11:00";
        etiquetas[6]="11:00-11:30";
        etiquetas[7]="11:30-12:00";
        etiquetas[8]="12:00-12:30";
        etiquetas[9]="12:30-1:00";
        etiquetas[10]="1:00-1:30";
        etiquetas[11]="1:30-2:00";
        etiquetas[12]="2:00-2:30";
        etiquetas[13]="2:30-3:00";
        etiquetas[14]="3:00-3:30";
        etiquetas[15]="3:30-4:00";
        etiquetas[16]="4:00-4:30";
        etiquetas[17]="4:30-5:00";
        etiquetas[18]="5:00-5:30";
        etiquetas[19]="5:30-6:00";
        etiquetas[20]="6:00-6:30";
        etiquetas[21]="6:30-7:00";
        etiquetas[22]="7:00-7:30";
        etiquetas[23]="7:30-8:00";
        return etiquetas;
    }
    
    public void establecerGrupo(int idGrupo){
        this.idGrupo=idGrupo;
        GrupoDAO grupoDAO = new GrupoDAO();
        Grupo grupoConsultado= new Grupo();
        grupoConsultado=grupoDAO.adquirirGrupo(idGrupo);
        etiquetaNombreMaestro.setText(grupoConsultado.getUsuario().getUsuario());
        etiquetaPrecioInscripcion.setText(grupoConsultado.getInscripcion().toString());
        etiquetaPrecioMensualidad.setText(grupoConsultado.getMensualidad().toString());
        etiquetasHorasXML= new String[24];
        etiquetasHorasXML=establecerEtiquetas();
        horasNumericas= new String [24];
        horasNumericas= establecerHoras();
        this.iniciarTablaHorario();
        this.llenarTablaHorario(grupoConsultado.getNombreGrupo());
        this.iniciarTablaAlumnos();
        this.llenarTablaAlumnos(grupoConsultado.getNombreGrupo()); 
    }

    @FXML
    private void desplegarVentanaEditar(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaEditarGrupo.fxml"));
        try{
            Parent root = (Parent) loader.load();
            VentanaEditarGrupoController editarGrupoController = loader.getController();
            editarGrupoController.establecerGrupo(idGrupo);////////manejar excepción en caso de estar vacio
            panelConsultarInfo.getChildren().add(root);
        }catch(IOException ex){
            Logger.getLogger (VentanaConsultarGruposController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /*Stage stage = (Stage) panelConsultarInfo.getScene().getWindow();
        stage.close();*/
        
        
    }

    @FXML
    private void eliminarGrupo(ActionEvent event) throws IOException {
        GrupoDAO nuevoGrupoDAO = new GrupoDAO();
        Grupo grupoEliminar = new Grupo();
        //grupoEliminar=nuevoGrupoDAO.adquirirGrupo(nombreGrupo);
        grupoEliminar.setEstaActivo(0);
        if(nuevoGrupoDAO.eliminarGrupo(grupoEliminar)){
            eliminarHorarioGrupo();
            DialogosController.mostrarMensajeInformacion("Eliminado", "El grupo ha sido eliminado", "El grupo se desactivo corectamente");
            FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaConsultarGrupos.fxml"));
            Parent root = (Parent) loader.load();
            VentanaConsultarGruposController ventanaConsultarGruposController = loader.getController();
            ventanaConsultarGruposController.iniciarVentana();
            panelConsultarInfo.getChildren().add(root); 
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


                if(eElement.getElementsByTagName(nombreFila).item(0).getTextContent().equals(idGrupo)){
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
        ventanaConsultarGruposController.iniciarVentana();
        panelConsultarInfo.getChildren().add(root);
    }
}
