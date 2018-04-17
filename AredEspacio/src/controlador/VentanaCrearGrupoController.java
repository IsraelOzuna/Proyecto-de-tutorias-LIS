package controlador;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import negocio.CuentaDAO;
import negocio.GrupoDAO;
import negocio.MaestroDAO;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import persistencia.Cuenta;
import persistencia.Grupo;
import persistencia.Horario;
import persistencia.Maestro;

public class VentanaCrearGrupoController implements Initializable {

    @FXML
    private JFXButton botonGurdar;
    @FXML
    private JFXButton botonCancelar;
    @FXML
    private TableView<Horario> tablaHorario;
    @FXML
    private Label etiquetaCrearGrupo;
    @FXML
    private Label etiquetaNombre;
    @FXML
    private Label etiquetaPrecioMensualidad;
    @FXML
    private Label etiquetaInscripción;
    @FXML
    private TextField campoMensualidad;
    @FXML
    private TextField campoInscripcion;
    @FXML
    private TableColumn columnaHorario;
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
    private TextField campoNombreGrupo;
    @FXML
    private ComboBox<String> comboBoxMaestro;
    @FXML
    private AnchorPane panelCrearGrupos;
    private static int dobleClick=0;
    private static int fila;
    private static int columna;
    
    ObservableList<Horario> horarios;
    
    @FXML
    private Label etiquetaMaestro;
 
    public void inicializarTablaHorario(){
        columnaHorario.setCellValueFactory(new PropertyValueFactory<Horario, String>("hora"));
        columnaDomingo.setCellValueFactory(new PropertyValueFactory<Horario, String>("domingo"));
        columnaLunes.setCellValueFactory(new PropertyValueFactory<Horario, String>("lunes"));
        columnaMartes.setCellValueFactory(new PropertyValueFactory<Horario, String>("martes"));
        columnaMiercoles.setCellValueFactory(new PropertyValueFactory<Horario, String>("miercoles"));
        columnaJueves.setCellValueFactory(new PropertyValueFactory<Horario, String>("jueves"));
        columnaViernes.setCellValueFactory(new PropertyValueFactory<Horario, String>("viernes"));
        columnaSabado.setCellValueFactory(new PropertyValueFactory<Horario, String>("sabado"));
        horarios = FXCollections.observableArrayList();
        //alumnos = FXCollections.observableArrayList();
        tablaHorario.setItems(horarios);
        //tablaHorario.setEditable(true);
        tablaHorario.getSelectionModel().setCellSelectionEnabled(true);
       
        
    }
    public void llenarTabla(){
        try{
            //File inputFile = new File("/Archivos/Horarios.xml");
            //File inputFile = new File("C:\\Users\\Renato\\Documents\\NetBeansProjects\\AredEspacio\\src\\Archivos\\Horarios.xml");
            File inputFile = new File("C:\\Users\\iro19\\Documents\\GitHub\\Repositorio-Desarrollo-de-Software\\AredEspacio\\src\\Archivos\\Horarios.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("dia");
            for (int i = 0; i < 24; i++) {
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
        switch(i){
            case 0:
                h1.setHora("8:00-8:30");
                nombreEtiqueta="ocho-ochoMedia";
            break;
            case 1:
                h1.setHora("8:30-9:00");
                nombreEtiqueta="ochoMedia-nueve";
            break;
            case 2:
                h1.setHora("9:00-9:00");
                nombreEtiqueta="nueve-nueveMedia";
            break;
            case 3:
                h1.setHora("9:30-10:00");
                nombreEtiqueta="nueveMedia-diez";
            break;
            case 4:
                h1.setHora("10:00-10:30");
                nombreEtiqueta="diez-diezMedia";
            break;
            case 5:
                h1.setHora("10:30-11:00");
                nombreEtiqueta="diezMedia-once";
            break;
            case 6:
                h1.setHora("11:00-11:30");
                nombreEtiqueta="once-onceMedia";
            break;
            case 7:
                h1.setHora("11:30-12:00");
                nombreEtiqueta="onceMedia-doce";
            break;
            case 8:
                h1.setHora("12:00-12:30");
                nombreEtiqueta="doce-doceMedia";
            break;
            case 9:
                h1.setHora("12:30-13:00");
                nombreEtiqueta="doceMedia-una";
            break;
            case 10:
                h1.setHora("13:00-13:30");
                nombreEtiqueta="una-unaMedia";
            break;
            case 11:
                h1.setHora("13:30-14:00");
                nombreEtiqueta="unaMedia-dos";
            break;
            case 12:
                h1.setHora("14:00-14:30");
                nombreEtiqueta="dos-dosMedia";
            break;
            case 13:
                h1.setHora("14:30-15:00");
                nombreEtiqueta="dosMedia-tres";
            break;
            case 14:
                h1.setHora("15:00-15:30");
                nombreEtiqueta="tres-tresMedia";
            break;
            case 15:
                h1.setHora("15:30-16:00");
                nombreEtiqueta="tresMedia-cuatro";
            break;
            case 16:
                h1.setHora("16:00-16:30");
                nombreEtiqueta="cuatro-cuatroMedia";
            break;
            case 17:
                h1.setHora("16:30-17:00");
                nombreEtiqueta="cuatroMedia-cinco";
            break;
            case 18:
                h1.setHora("17:00-17:30");
                nombreEtiqueta="cinco-cincoMedia";
            break;
            case 19:
                h1.setHora("17:30-18:00");
                nombreEtiqueta="cincoMedia-seis";
            break;
            case 20:
                h1.setHora("18:00-18:30");
                nombreEtiqueta="seis-seisMedia";
            break;
            case 21:
                h1.setHora("18:30-19:00");
                nombreEtiqueta="seisMedia-siete";
            break;
            case 22:
                h1.setHora("19:00-19:30");
                nombreEtiqueta="siete-sieteMedia";
            break;
            case 23:
                h1.setHora("19:30-20:00");
                nombreEtiqueta="sieteMedia-ocho";
            break;
        }
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
    
    public void iniciarVentana(){
        ObservableList<String> maestros =FXCollections.observableArrayList();
        List<Cuenta> listaUsuarios=null;
        GrupoDAO grupoDAO = new GrupoDAO();/////////
        listaUsuarios=grupoDAO.adquirirCuentas();//////
        for(int i=0; i<listaUsuarios.size(); i++){///////
            maestros.add(listaUsuarios.get(i).getUsuario());///////
        }
        comboBoxMaestro.setItems(maestros);
        this.inicializarTablaHorario();
        this.llenarTabla();
        
        campoInscripcion.textProperty().addListener((observable, viejoValor, nuevoValor) -> {
            if (!nuevoValor.matches("\\d+\\.\\d*")) {
                campoInscripcion.setText(nuevoValor.replaceAll("[^\\d]", ""));
            }
        });
        
        
        campoMensualidad.textProperty().addListener((observable, viejoValor, nuevoValor) -> {
            if (!nuevoValor.matches("\\d+\\.\\d*")) {
                campoMensualidad.setText(nuevoValor.replaceAll("[^\\d]", ""));
            }
        });

        tablaHorario.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    //int columna;
                    
                    boolean banderaEditar=true;
                    
                    fila=tablaHorario.getFocusModel().getFocusedCell().getRow();
                    columna=tablaHorario.getFocusModel().getFocusedCell().getColumn();
                    if(columna!=0){
                        Horario nuevoHorario= new Horario();
                        switch(columna){
                            case 1:
                                nuevoHorario=tablaHorario.getSelectionModel().getSelectedItem();;//////////
                                if(nuevoHorario.getDomingo().equals("Disponible")){
                                    nuevoHorario.setDomingo("Ocupado");
                                }else if(nuevoHorario.getDomingo().equals("Ocupado")){
                                    nuevoHorario.setDomingo("Disponible");
                                }


                            break;
                            case 2:

                                nuevoHorario=tablaHorario.getSelectionModel().getSelectedItem();
                                if(nuevoHorario.getLunes().equals("Disponible")){
                                    nuevoHorario.setLunes("Ocupado");
                                }else if(nuevoHorario.getLunes().equals("Ocupado")){
                                    nuevoHorario.setLunes("Disponible");
                                }

                            break;
                            case 3:

                                nuevoHorario=tablaHorario.getSelectionModel().getSelectedItem();
                                if(nuevoHorario.getMartes().equals("Disponible")){
                                    nuevoHorario.setMartes("Ocupado");
                                }else if(nuevoHorario.getMartes().equals("Ocupado")){
                                    nuevoHorario.setMartes("Disponible");
                                }

                            break;
                            case 4:

                                nuevoHorario=tablaHorario.getSelectionModel().getSelectedItem();
                                if(nuevoHorario.getMiercoles().equals("Disponible")){
                                    nuevoHorario.setMiercoles("Ocupado");
                                }else if(nuevoHorario.getMiercoles().equals("Ocupado")){
                                    nuevoHorario.setMiercoles("Disponible");
                                }

                            break;
                            case 5:

                                nuevoHorario=tablaHorario.getSelectionModel().getSelectedItem();
                                if(nuevoHorario.getJueves().equals("Disponible")){
                                    nuevoHorario.setJueves("Ocupado");
                                }else if(nuevoHorario.getJueves().equals("Ocupado")){
                                    nuevoHorario.setJueves("Disponible");
                                }
                            break;
                            case 6:

                                nuevoHorario=tablaHorario.getSelectionModel().getSelectedItem();
                                if(nuevoHorario.getViernes().equals("Disponible")){
                                    nuevoHorario.setViernes("Ocupado");
                                }else if(nuevoHorario.getViernes().equals("Ocupado")){
                                    nuevoHorario.setViernes("Disponible");
                                }
                            break;
                            case 7:

                                nuevoHorario=tablaHorario.getSelectionModel().getSelectedItem();
                                if(nuevoHorario.getSabado().equals("Disponible")){
                                    nuevoHorario.setSabado("Ocupado");
                                }else if(nuevoHorario.getSabado().equals("Ocupado")){
                                    nuevoHorario.setSabado("Disponible");
                                }
                            break;

                        }
                        horarios.set(fila, nuevoHorario);
                    }
                    
                    
                    
                    //TODO CAMBIAR EL XML, COMPROBAR QUE EL CAMPO ESTÁ DISPONIBLE O QUE NO ES UN HORARIO
                    
                    
                }   
            });
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
    }    

    @FXML
    private void registrarGrupo(ActionEvent event) throws IOException {
        
        if(!existenCamposVacios()){
            if(!existenCamposExcedidos()){
                GrupoDAO nuevoGrupoDAO = new GrupoDAO();
                Cuenta nuevaCuenta=new Cuenta();
                nuevaCuenta.setUsuario(comboBoxMaestro.getValue());
                Grupo nuevoGrupo = new Grupo();
                nuevoGrupo.setNombreGrupo(campoNombreGrupo.getText());
                nuevoGrupo.setUsuario(nuevaCuenta);
                nuevoGrupo.setInscripcion(Double.parseDouble(campoInscripcion.getText()));
                nuevoGrupo.setMensualidad(Double.parseDouble(campoMensualidad.getText()));
                nuevoGrupo.setFechaPago(new Date());
                if(nuevoGrupoDAO.crearGrupo(nuevoGrupo)){
                    registrarHorarioGrupo();
                    FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaConsultarGrupos.fxml"));
                    Parent root = (Parent) loader.load();
                    VentanaConsultarGruposController ventanaConsultarGruposController = loader.getController();
                    ventanaConsultarGruposController.iniciarVentana();
                    panelCrearGrupos.getChildren().add(root); 
                }
            }else{
                DialogosController.mostrarMensajeInformacion("Campos Excedidos", "Existen Campos que exceden el numero de caracteres validos", "El nombre del grupo no puede rebasar los 100 caracteres, mientras que los campos de inscripcion y mensualidad no deben exceder los 10 caracteres");
            }
            
        }else{
             DialogosController.mostrarMensajeInformacion("Campos Vacios", "Existen Campos Vacios", "Por favor llene los campos con la información requerida");
        }
        
    }

    @FXML
    private void cancelarRegistro(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaConsultarGrupos.fxml"));
            Parent root = (Parent) loader.load();
            VentanaConsultarGruposController ventanaConsultarGruposController = loader.getController();
            ventanaConsultarGruposController.iniciarVentana();
            panelCrearGrupos.getChildren().add(root); 
    }
    
    public boolean existenCamposVacios(){
        boolean camposVacios=false;
        if(campoInscripcion.getText().isEmpty()){
            camposVacios=true;
        }
        if(campoMensualidad.getText().isEmpty()){
            camposVacios=true;
        }
        if(campoNombreGrupo.getText().isEmpty()){
            camposVacios=true;
        }
        if(comboBoxMaestro.getValue()==null){
            camposVacios=true;
        }
        
        return camposVacios;
    }
    
    public void registrarHorarioGrupo(){
        ObservableList<Horario> lista=tablaHorario.getItems();
        //System.out.println();
        for(int i=0; i<24; i++){
            //System.out.println(lista.get(0).getHora());
            Horario horarioActualizar= lista.get(i);
           
            try {
                    //File inputFile = new File("C:\\Users\\Renato\\Documents\\NetBeansProjects\\AredEspacio\\src\\Archivos\\Horarios.xml");
                    File inputFile = new File("C:\\Users\\iro19\\Documents\\GitHub\\Repositorio-Desarrollo-de-Software\\AredEspacio\\src\\Archivos\\Horarios.xml");
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    Document doc = dBuilder.parse(inputFile);
                    doc.getDocumentElement().normalize();
                    NodeList nList = doc.getElementsByTagName("dia");
                    switch(i){
                        case 0:
                                    actualizarFila(nList, doc, "ocho-ochoMedia", horarioActualizar);
                        break;
                        case 1:
                                    actualizarFila(nList, doc, "ochoMedia-nueve", horarioActualizar);
                        break;
                        case 2:
                                    actualizarFila(nList, doc, "nueve-nueveMedia", horarioActualizar);
                        break;
                        case 3:
                                    actualizarFila(nList, doc, "nueveMedia-diez", horarioActualizar);
                        break;
                        case 4:
                                    actualizarFila(nList, doc, "diez-diezMedia", horarioActualizar);
                        break;
                        case 5:
                                    actualizarFila(nList, doc, "diezMedia-once", horarioActualizar);
                        break;
                        case 6:
                                    actualizarFila(nList, doc, "once-onceMedia", horarioActualizar);
                        break;
                        case 7:
                                    actualizarFila(nList, doc, "onceMedia-doce", horarioActualizar);
                        break;
                        case 8:
                                    actualizarFila(nList, doc, "doce-doceMedia", horarioActualizar);
                        break;
                        case 9:
                                    actualizarFila(nList, doc, "doceMedia-una", horarioActualizar);
                        break;
                        case 10:
                                    actualizarFila(nList, doc, "una-unaMedia", horarioActualizar);
                        break;
                        case 11:
                                    actualizarFila(nList, doc, "unaMedia-dos", horarioActualizar);
                        break;
                        case 12:
                                    actualizarFila(nList, doc, "dos-dosMedia", horarioActualizar);
                        break;
                        case 13:
                                    actualizarFila(nList, doc, "dosMedia-tres", horarioActualizar);
                        break;
                        case 14:
                                    actualizarFila(nList, doc, "tres-tresMedia", horarioActualizar);
                        break;
                        case 15:
                                    actualizarFila(nList, doc, "tresMedia-cuatro", horarioActualizar);
                        break;
                        case 16:
                                    actualizarFila(nList, doc, "cuatro-cuatroMedia", horarioActualizar);
                        break;
                        case 17:
                                    actualizarFila(nList, doc, "cuatroMedia-cinco", horarioActualizar);
                        break;
                        case 18:
                                    actualizarFila(nList, doc, "cinco-cincoMedia", horarioActualizar);
                        break;
                        case 19:
                                    actualizarFila(nList, doc, "cincoMedia-seis", horarioActualizar);
                        break;
                        case 20:
                                    actualizarFila(nList, doc, "seis-seisMedia", horarioActualizar);
                        break;
                        case 21:
                                    actualizarFila(nList, doc, "seisMedia-siete", horarioActualizar);
                        break;
                        case 22:
                                    actualizarFila(nList, doc, "siete-sieteMedia", horarioActualizar);
                        break;
                        case 23:
                                    actualizarFila(nList, doc, "sieteMedia-ocho", horarioActualizar);
                        break;
                    }   
                    
            }catch (Exception e) {
               e.printStackTrace();
            }
        }
    }
    
    public void actualizarFila(NodeList nList,  Document doc, String nombreFila, Horario horarioActualizar) throws TransformerException{
        
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp); //cada item es un dia de la semana
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                switch(temp){
                    case 0:
                        if(horarioActualizar.getDomingo().equals("Ocupado")){
                            eElement.getElementsByTagName(nombreFila).item(0).setTextContent(campoNombreGrupo.getText());
                        }else{
                        //System.out.println("Fila: "+nombreFila+" Columna: "+temp+" Contenido a poner: "+horarioActualizar.getDomingo());
                            eElement.getElementsByTagName(nombreFila).item(0).setTextContent(horarioActualizar.getDomingo());
                        }
                    break;
                    case 1:
                      if(horarioActualizar.getLunes().equals("Ocupado")){
                            eElement.getElementsByTagName(nombreFila).item(0).setTextContent(campoNombreGrupo.getText());
                        }else{
                            eElement.getElementsByTagName(nombreFila).item(0).setTextContent(horarioActualizar.getLunes());
                        }
                    break;
                    case 2:
                      if(horarioActualizar.getMartes().equals("Ocupado")){
                            eElement.getElementsByTagName(nombreFila).item(0).setTextContent(campoNombreGrupo.getText());
                        }else{
                            eElement.getElementsByTagName(nombreFila).item(0).setTextContent(horarioActualizar.getMartes());
                        }
                    break;
                    case 3:
                      if(horarioActualizar.getMiercoles().equals("Ocupado")){
                            eElement.getElementsByTagName(nombreFila).item(0).setTextContent(campoNombreGrupo.getText());
                        }else{
                            eElement.getElementsByTagName(nombreFila).item(0).setTextContent(horarioActualizar.getMiercoles());
                        }
                    break;
                    case 4:
                      if(horarioActualizar.getJueves().equals("Ocupado")){
                            eElement.getElementsByTagName(nombreFila).item(0).setTextContent(campoNombreGrupo.getText());
                        }else{
                            eElement.getElementsByTagName(nombreFila).item(0).setTextContent(horarioActualizar.getJueves());
                        }
                    break;
                    case 5:
                      if(horarioActualizar.getViernes().equals("Ocupado")){
                            eElement.getElementsByTagName(nombreFila).item(0).setTextContent(campoNombreGrupo.getText());
                        }else{
                            eElement.getElementsByTagName(nombreFila).item(0).setTextContent(horarioActualizar.getViernes());
                        }
                    break;
                    case 6:
                      if(horarioActualizar.getSabado().equals("Ocupado")){
                            eElement.getElementsByTagName(nombreFila).item(0).setTextContent(campoNombreGrupo.getText());
                        }else{
                            eElement.getElementsByTagName(nombreFila).item(0).setTextContent(horarioActualizar.getSabado());
                        }
                    break;
                }
                
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                //StreamResult result = new StreamResult(new File("C:\\Users\\Renato\\Documents\\NetBeansProjects\\AredEspacio\\src\\Archivos\\Horarios.xml"));
                StreamResult result = new StreamResult(new File ("C:\\Users\\iro19\\Documents\\GitHub\\Repositorio-Desarrollo-de-Software\\AredEspacio\\src\\Archivos\\Horarios.xml"));
                transformer.transform(source, result);
            }
        }
    } 
    
    public boolean existenCamposExcedidos() {
        boolean campoExcedido = false;

        if (campoNombreGrupo.getText().length() > 100) {
            campoExcedido = true;
        } else if (campoMensualidad.getText().length() > 10) {
            campoExcedido = true;
        } else if (campoInscripcion.getText().length() > 10) {
            campoExcedido = true;
        } 
        return campoExcedido;
    }
    
    
}
