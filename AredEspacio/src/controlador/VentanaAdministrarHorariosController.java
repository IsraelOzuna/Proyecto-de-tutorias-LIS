package controlador;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import negocio.GrupoDAO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import persistencia.Cuenta;
import persistencia.Grupo;
import persistencia.Horario;

public class VentanaAdministrarHorariosController implements Initializable {

    @FXML
    private ComboBox<String> comboGrupos;
    @FXML
    private TableView<Horario> tablaHorario;
    @FXML
    private TableColumn columnaHora;
    @FXML
    private TableColumn columnaDomingo;
    @FXML
    private TableColumn columnaLunes;
    @FXML
    private TableColumn columnaMartes;
    @FXML
    private TableColumn columnaMiercoles;
    @FXML
    private TableColumn columnaJueves;
    @FXML
    private TableColumn columnaViernes;
    @FXML
    private TableColumn columnaSabado;
    @FXML
    private JFXButton botonGuardar;
    ObservableList<Horario> horarios;
    private String rutaXML=System.getProperty("user.dir") + "/Archivos/Horarios.xml";
    private String nombreUsuario;
    @FXML
    private JFXButton botonRgresar;
    private static int fila;
    private static int columna;
    @FXML
    private AnchorPane panelAdministrarHorario;
    private String unidadPersistencia="AredEspacioPU";
    private String [] etiquetasHorasXML;
    private String [] horasNumericas;
 
    public void inicializarTablaHorario(){
        
        columnaHora.setCellValueFactory(new PropertyValueFactory<Horario, String>("hora"));
        columnaDomingo.setCellValueFactory(new PropertyValueFactory<Horario, String>("domingo"));
        columnaLunes.setCellValueFactory(new PropertyValueFactory<Horario, String>("lunes"));
        columnaMartes.setCellValueFactory(new PropertyValueFactory<Horario, String>("martes"));
        columnaMiercoles.setCellValueFactory(new PropertyValueFactory<Horario, String>("miercoles"));
        columnaJueves.setCellValueFactory(new PropertyValueFactory<Horario, String>("jueves"));
        columnaViernes.setCellValueFactory(new PropertyValueFactory<Horario, String>("viernes"));
        columnaSabado.setCellValueFactory(new PropertyValueFactory<Horario, String>("sabado"));
        horarios = FXCollections.observableArrayList();
        tablaHorario.setItems(horarios);
        tablaHorario.getSelectionModel().setCellSelectionEnabled(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }  
    
    public void iniciarVentana(String nombreUsuarioActual){
        nombreUsuario=nombreUsuarioActual;
        ObservableList<String> grupos =FXCollections.observableArrayList();
        List<Grupo> listaGrupos=null;
        GrupoDAO grupoDAO = new GrupoDAO(unidadPersistencia);
        Cuenta usuario = new Cuenta();
        listaGrupos=grupoDAO.adquirirGrupos(usuario);
        etiquetasHorasXML= new String[30];
        etiquetasHorasXML=establecerEtiquetas();
        horasNumericas= new String [30];
        horasNumericas= establecerHoras();
        for(int i=0; i<listaGrupos.size(); i++){
            if(listaGrupos.get(i).getEstaActivo()==1){
                grupos.add(listaGrupos.get(i).getNombreGrupo());
            } 
        }
        comboGrupos.setItems(grupos);
        this.inicializarTablaHorario();
        this.llenarTabla();
        tablaHorario.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //int columna;
                boolean clickIzquierdo=false;
                boolean clickDerecho=false;
                if(event.isPrimaryButtonDown()){
                    clickIzquierdo=true;
                    clickDerecho=false;
                }else if(event.isSecondaryButtonDown()){
                    clickIzquierdo=false;
                    clickDerecho=true;
                }
                boolean banderaEditar=true;
                String nombreGrupoElegido=comboGrupos.getValue();
                

                fila=tablaHorario.getFocusModel().getFocusedCell().getRow();
                columna=tablaHorario.getFocusModel().getFocusedCell().getColumn();
                if(columna!=0 && clickIzquierdo){
                    Horario nuevoHorario= new Horario();
                    switch(columna){
                        case 1:
                            nuevoHorario=tablaHorario.getSelectionModel().getSelectedItem();;//////////
                            if (nombreGrupoElegido!=null){
                                nuevoHorario.setDomingo(nombreGrupoElegido);
                            }
                        break;
                        case 2:

                            nuevoHorario=tablaHorario.getSelectionModel().getSelectedItem();;//////////
                            if (nombreGrupoElegido!=null){
                                nuevoHorario.setLunes(nombreGrupoElegido);
                            }

                        break;
                        case 3:

                            nuevoHorario=tablaHorario.getSelectionModel().getSelectedItem();;//////////
                            if (nombreGrupoElegido!=null){
                                nuevoHorario.setMartes(nombreGrupoElegido);
                            }

                        break;
                        case 4:

                            nuevoHorario=tablaHorario.getSelectionModel().getSelectedItem();;//////////
                            if (nombreGrupoElegido!=null){
                                nuevoHorario.setMiercoles(nombreGrupoElegido);
                            }

                        break;
                        case 5:

                            nuevoHorario=tablaHorario.getSelectionModel().getSelectedItem();;//////////
                            if (nombreGrupoElegido!=null){
                                nuevoHorario.setJueves(nombreGrupoElegido);
                            }
                        break;
                        case 6:

                            nuevoHorario=tablaHorario.getSelectionModel().getSelectedItem();;//////////
                            if (nombreGrupoElegido!=null){
                                nuevoHorario.setViernes(nombreGrupoElegido);
                            }
                        break;
                        case 7:
                            nuevoHorario=tablaHorario.getSelectionModel().getSelectedItem();;//////////
                            if (nombreGrupoElegido!=null){
                                nuevoHorario.setSabado(nombreGrupoElegido);
                            }
                        break;

                    }
                    horarios.set(fila, nuevoHorario);
                    
                    
                }else if(columna!=0 && clickDerecho){
                    Horario nuevoHorario= new Horario();
                    switch(columna){
                        case 1:
                            nuevoHorario=tablaHorario.getSelectionModel().getSelectedItem();
                            nuevoHorario.setDomingo("Disponible");
                        break;
                        case 2:
                            nuevoHorario=tablaHorario.getSelectionModel().getSelectedItem();
                            nuevoHorario.setLunes("Disponible");
                        break;
                        case 3:
                            nuevoHorario=tablaHorario.getSelectionModel().getSelectedItem();
                            nuevoHorario.setMartes("Disponible");
                        break;
                        case 4:
                            nuevoHorario=tablaHorario.getSelectionModel().getSelectedItem();
                            nuevoHorario.setMiercoles("Disponible");
                        break;
                        case 5:
                            nuevoHorario=tablaHorario.getSelectionModel().getSelectedItem();
                            nuevoHorario.setJueves("Disponible");
                        break;
                        case 6:
                            nuevoHorario=tablaHorario.getSelectionModel().getSelectedItem();
                            nuevoHorario.setViernes("Disponible");
                        break;
                        case 7:
                            nuevoHorario=tablaHorario.getSelectionModel().getSelectedItem();
                            nuevoHorario.setSabado("Disponible");
                        break;
                    }
                    horarios.set(fila, nuevoHorario);
                }
            }   
            });
        
        
        
    }
    
    public void llenarTabla(){
        try{
            File inputFile = new File(rutaXML);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("dia");
            for (int i = 0; i < 30; i++) {
                Horario h1 = new Horario();
                h1=establecerFila(i, h1, nList);
                horarios.add(h1);            
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public Horario establecerFila(int i, Horario h1, NodeList nList){
        String nombreEtiqueta="";
        h1.setHora(horasNumericas[i]);
        nombreEtiqueta=etiquetasHorasXML[i];

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                
                switch(eElement.getAttribute("nombre")){
                    case "Domingo":
                        h1.setDia(eElement.getAttribute("nombre"));
                        h1.setDomingo(eElement.getElementsByTagName(nombreEtiqueta).item(0).getTextContent());
                    break;
                    case "Lunes":
                        h1.setDia(eElement.getAttribute("nombre"));
                        h1.setLunes(eElement.getElementsByTagName(nombreEtiqueta).item(0).getTextContent());
                    break;
                    case "Martes":
                        h1.setDia(eElement.getAttribute("nombre"));
                        h1.setMartes(eElement.getElementsByTagName(nombreEtiqueta).item(0).getTextContent());
                    break;
                    case "Miercoles":
                        h1.setDia(eElement.getAttribute("nombre"));
                        h1.setMiercoles(eElement.getElementsByTagName(nombreEtiqueta).item(0).getTextContent());
                    break;
                    case "Jueves":
                        h1.setDia(eElement.getAttribute("nombre"));
                        h1.setJueves(eElement.getElementsByTagName(nombreEtiqueta).item(0).getTextContent());
                    break;
                    case "Viernes":
                        h1.setDia(eElement.getAttribute("nombre"));
                        h1.setViernes(eElement.getElementsByTagName(nombreEtiqueta).item(0).getTextContent());
                    break;
                    case "Sabado":
                        h1.setDia(eElement.getAttribute("nombre"));
                        h1.setSabado(eElement.getElementsByTagName(nombreEtiqueta).item(0).getTextContent());
                    break;

                }

            }
        }
        return h1;
    }

    @FXML
    private void regresarPantallaAnterior(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaConsultarGrupos.fxml"));
        Parent root = (Parent) loader.load();
        VentanaConsultarGruposController ventanaConsultarGruposController = loader.getController();
        ventanaConsultarGruposController.iniciarVentana(nombreUsuario);
        panelAdministrarHorario.getChildren().add(root);
        
    }

    @FXML
    private void guardarHorario(ActionEvent event) throws IOException {
        ObservableList<Horario> lista=tablaHorario.getItems();
        //System.out.println();
        for(int i=0; i<30; i++){
            //System.out.println(lista.get(0).getHora());
            Horario horarioActualizar= lista.get(i);
           
            try {
                    File inputFile = new File(rutaXML);
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    Document doc = dBuilder.parse(inputFile);
                    doc.getDocumentElement().normalize();
                    NodeList nList = doc.getElementsByTagName("dia");
                    actualizarFila(nList, doc, etiquetasHorasXML[i], horarioActualizar);
            }catch (Exception e) {
               e.printStackTrace();
            }
        }
        DialogosController.mostrarMensajeInformacion("Modificado", "El horario del grupo ha sido modificado", "");
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaConsultarGrupos.fxml"));
        Parent root = (Parent) loader.load();
        VentanaConsultarGruposController ventanaConsultarGruposController = loader.getController();
        ventanaConsultarGruposController.iniciarVentana(nombreUsuario);
        panelAdministrarHorario.getChildren().add(root); 
        
    }
    public void actualizarFila(NodeList nList,  Document doc, String nombreFila, Horario horarioActualizar) throws TransformerException{
        
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp); //cada item es un dia de la semana
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                switch(temp){
                    case 0:
                        eElement.getElementsByTagName(nombreFila).item(0).setTextContent(horarioActualizar.getDomingo());
                    break;
                    case 1:
                      eElement.getElementsByTagName(nombreFila).item(0).setTextContent(horarioActualizar.getLunes());
                    break;
                    case 2:
                      eElement.getElementsByTagName(nombreFila).item(0).setTextContent(horarioActualizar.getMartes());
                    break;
                    case 3:
                      eElement.getElementsByTagName(nombreFila).item(0).setTextContent(horarioActualizar.getMiercoles());
                    break;
                    case 4:
                      eElement.getElementsByTagName(nombreFila).item(0).setTextContent(horarioActualizar.getJueves());
                    break;
                    case 5:
                      eElement.getElementsByTagName(nombreFila).item(0).setTextContent(horarioActualizar.getViernes());
                    break;
                    case 6:
                      eElement.getElementsByTagName(nombreFila).item(0).setTextContent(horarioActualizar.getSabado());
                    break;
                }
                
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(rutaXML));
                transformer.transform(source, result);
            }
        }
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

    
}
