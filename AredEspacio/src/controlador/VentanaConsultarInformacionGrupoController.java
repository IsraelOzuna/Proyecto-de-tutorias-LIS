
package controlador;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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
 * Este controlador es usado para establecer las acciones disponibles cuando se
 * consulta un horario y para presentar la información de dicho grupo
 *
 * @author Renato Vargas
 * @version 1.0 / 5 de junio de 2018
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
    String[] etiquetasHorasXML;
    String[] horasNumericas;
    @FXML
    private TableColumn columnaAlumnos;
    @FXML
    private Pane panelConsultarInformacion;
    @FXML
    private AnchorPane panelConsultarInfo;

    private int idGrupo;
    private String nombreGrupo;
    private String rutaXML;
    private String unidadPersistencia = "AredEspacioPU";
    @FXML
    private JFXButton botonRegistrarAsistencia;
    boolean esMaestro = false;
    private String nombreCuentaMaestro;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            CodeSource direccion = VentanaConsultarInformacionGrupoController.class.getProtectionDomain().getCodeSource();
            File fileJar = new File(direccion.getLocation().toURI().getPath());
            File fileDir = fileJar.getParentFile();
            File fileProperties = new File(fileDir.getAbsolutePath() + "/Archivos/Horarios.xml");
            rutaXML = fileProperties.getAbsolutePath();
        } catch (URISyntaxException ex) {
            Logger.getLogger(VentanaConsultarInformacionGrupoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Este método establece el tipo de valores que tendrá una tabla de horarios
     *
     * @since 1.0 / 5 de junio de 2018
     */
    public void iniciarTablaHorario() {
        columnaDia.setCellValueFactory(new PropertyValueFactory<Horario, String>("dia"));
        columnaHora.setCellValueFactory(new PropertyValueFactory<Horario, String>("hora"));
        horarios = FXCollections.observableArrayList();
        tablaHorario.setItems(horarios);
    }
    
    /**
     * Este método establece el tipo de valores que tendrá una tabla de alumnos
     *
     * @since 1.0 / 5 de junio de 2018
     */
    public void iniciarTablaAlumnos() {
        columnaAlumnos.setCellValueFactory(new PropertyValueFactory<Horario, String>("nombre"));
        alumnos = FXCollections.observableArrayList();
        tablaAlumnos.setItems(alumnos);
    }
    
    /**
     * Este método llenará una tabla en base a los valores de un archivo XML
     *
     * @param nombreGrupo grupo del que se adquiriran lso datos del horario
     * @since 1.0 / 5 de junio de 2018
     */
    public void llenarTablaHorario(String nombreGrupo) {
        int numeroClases = 0;
        Horario[] arregloHorarios;
        Horario[] horariosAgregar;

        try {
            File inputFile = new File(rutaXML);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("dia");
            numeroClases = conseguirNumeroDeClases(nList, nombreGrupo);
            arregloHorarios = new Horario[numeroClases];
            arregloHorarios = establecerArreglo(nList, numeroClases, nombreGrupo);
            for (int i = 0; i < numeroClases; i++) {
                horarios.add(arregloHorarios[i]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Este método llenará una tabla de alumnos basandose de los alumnos que 
     * están inscritos a un grupo
     *
     * @param nombreGrupo del que se adquiriran los alumnos
     * @since 1.0 / 5 de junio de 2018
     */
    public void llenarTablaAlumnos(String nombreGrupo) {
        GrupoDAO grupoDAO = new GrupoDAO(unidadPersistencia);
        List<Alumno> listaAlumnos = null;
        listaAlumnos = grupoDAO.obtenerAlumnos(idGrupo);
        for (int i = 0; i < listaAlumnos.size(); i++) {
            alumnos.add(listaAlumnos.get(i));
        }

    }
    
    /**
     * Este método adquiere el numero de clases que tendrá un grupo dentro de un
     * horario
     *
     * @param nList lista de nodos con los datos del xml
     * @param nombreGrupo grupo del que se adquirirán los horarios
     * @return int que representa el numero de clases
     * @since 1.0 / 5 de junio de 2018
     */
    public int conseguirNumeroDeClases(NodeList nList, String nombreGrupo) {
        int numeroClases = 0;
        String nombreElemento;
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                for (int i = 0; i < 30; i++) {
                    nombreElemento = eElement.getElementsByTagName(etiquetasHorasXML[i]).item(0).getTextContent();
                    if (nombreElemento.equals(nombreGrupo)) {
                        numeroClases++;
                    }
                }
            }
        }
        return numeroClases;

    }
    
    /**
     * Este método adquiere los valores que se añadiran a una tabla
     *
     * @param nList lsita con los datos de un archivo xml
     * @param numeroClases nuemro total de clases en un horario en base a un grupo
     * @param nombreGrupo grupo del que se realizará el establecimiento del arreglo
     * @return Horario[] que serán instroducidos en una tabla
     * @since 1.0 / 5 de junio de 2018
     */
    public Horario[] establecerArreglo(NodeList nList, int numeroClases, String nombreGrupo) {
        Horario[] arregloHorarios;
        arregloHorarios = new Horario[numeroClases];
        String nombreElemento;
        int contadorHorarios = 0;

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                for (int i = 0; i < 24; i++) {
                    nombreElemento = eElement.getElementsByTagName(etiquetasHorasXML[i]).item(0).getTextContent();
                    if (nombreElemento.equals(nombreGrupo)) {
                        arregloHorarios[contadorHorarios] = establecerElementoHorario(temp, horasNumericas[i]);
                        contadorHorarios++;
                    }
                }
            }
        }
        return arregloHorarios;
    }

    /**
     * Este método establece un día de acuerdo a un valor numerico
     *
     * @param temp valor nuerico del día
     * @return String del valor que tendrá el día
     * @since 1.0 / 5 de junio de 2018
     */
    public String establecerDia(int temp) {
        String dia = "";
        switch (temp) {
            case 0:
                dia = "Domingo";
                break;
            case 1:
                dia = "Lunes";
                break;
            case 2:
                dia = "Martes";
                break;
            case 3:
                dia = "Miercoles";
                break;
            case 4:
                dia = "Jueves";
                break;
            case 5:
                dia = "Viernes";
                break;
            case 6:
                dia = "Sabado";
                break;
        }
        return dia;
    }
    
    /**
     * Este método establece los valores que tendrá una fila de una tabla
     *
     * @param temp valor numerico del dia
     * @param horaNumerica valor de la cadena de la hora
     * @return Horario que será introducido a una tabla
     * @since 1.0 / 5 de junio de 2018
     */
    public Horario establecerElementoHorario(int temp, String horaNumerica) {
        Horario nuevoHorario = new Horario();
        nuevoHorario = new Horario();
        nuevoHorario.setDia(establecerDia(temp));
        nuevoHorario.setHora(horaNumerica);
        return nuevoHorario;

    }
    
    /**
     * Este método establece los valores de un areglo de etiquetas que será
     * recorrido durante el llenado de la tabla
     *
     * @return String[] que alamacena los valores de todas las etiquetas del xml
     * @since 1.0 / 5 de junio de 2018
     */
    public String[] establecerEtiquetas() {
        String[] etiquetas = new String[30];
        etiquetas[0] = "ocho-ochoMedia";
        etiquetas[1] = "ochoMedia-nueve";
        etiquetas[2] = "nueve-nueveMedia";
        etiquetas[3] = "nueveMedia-diez";
        etiquetas[4] = "diez-diezMedia";
        etiquetas[5] = "diezMedia-once";
        etiquetas[6] = "once-onceMedia";
        etiquetas[7] = "onceMedia-doce";
        etiquetas[8] = "doce-doceMedia";
        etiquetas[9] = "doceMedia-una";
        etiquetas[10] = "una-unaMedia";
        etiquetas[11] = "unaMedia-dos";
        etiquetas[12] = "dos-dosMedia";
        etiquetas[13] = "dosMedia-tres";
        etiquetas[14] = "tres-tresMedia";
        etiquetas[15] = "tresMedia-cuatro";
        etiquetas[16] = "cuatro-cuatroMedia";
        etiquetas[17] = "cuatroMedia-cinco";
        etiquetas[18] = "cinco-cincoMedia";
        etiquetas[19] = "cincoMedia-seis";
        etiquetas[20] = "seis-seisMedia";
        etiquetas[21] = "seisMedia-siete";
        etiquetas[22] = "siete-sieteMedia";
        etiquetas[23] = "sieteMedia-ocho";
        etiquetas[24] = "ocho-ochoMediaPm";
        etiquetas[25] = "ochoMedia-nuevePm";
        etiquetas[26] = "nueve-nueveMediaPm";
        etiquetas[27] = "nueveMedia-diezPm";
        etiquetas[28] = "diez-diezMediaPm";
        etiquetas[29] = "diezMedia-oncePm";
        return etiquetas;
    }

    /**
     * Este método establece los valores de un areglo de cadenas que 
     * representarán los valores numericos de las horas que se presentarán
     * en el horario.
     *
     * @return String[] donde se guardan los valores numericos de las horas
     * @since 1.0 / 5 de junio de 2018
     */
    public String[] establecerHoras() {
        String[] etiquetas = new String[30];
        etiquetas[0] = "8:00-8:30";
        etiquetas[1] = "8:30-9:00";
        etiquetas[2] = "9:00-9:30";
        etiquetas[3] = "9:30-10:00";
        etiquetas[4] = "10:00-10:30";
        etiquetas[5] = "10:30-11:00";
        etiquetas[6] = "11:00-11:30";
        etiquetas[7] = "11:30-12:00";
        etiquetas[8] = "12:00-12:30";
        etiquetas[9] = "12:30-13:00";
        etiquetas[10] = "13:00-13:30";
        etiquetas[11] = "13:30-14:00";
        etiquetas[12] = "14:00-14:30";
        etiquetas[13] = "14:30-15:00";
        etiquetas[14] = "15:00-15:30";
        etiquetas[15] = "15:30-16:00";
        etiquetas[16] = "16:00-16:30";
        etiquetas[17] = "16:30-17:00";
        etiquetas[18] = "17:00-17:30";
        etiquetas[19] = "17:30-18:00";
        etiquetas[20] = "18:00-18:30";
        etiquetas[21] = "18:30-19:00";
        etiquetas[22] = "19:00-19:30";
        etiquetas[23] = "19:30-20:00";
        etiquetas[24] = "20:00-20:30";
        etiquetas[25] = "20:30-21:00";
        etiquetas[26] = "21:00-21:30";
        etiquetas[27] = "21:30-22:00";
        etiquetas[28] = "22:00-22:30";
        etiquetas[29] = "22:30-23:00";
        return etiquetas;
    }
    
    /**
     * Este método establece los datos del grupo que serán mostrados
     *
     * @param idGrupo id del grupo que se consulta
     * @param nombreUsuarioActual datos del usuario actual para establecer
     * permisos
     * @since 1.0 / 5 de junio de 2018
     */
    public void establecerGrupo(int idGrupo, String nombreUsuarioActual) {
        nombreUsuario = nombreUsuarioActual;
        CuentaDAO cuentaDAO = new CuentaDAO();
        Cuenta cuentaAdquirirda = cuentaDAO.obtenerCuenta(nombreUsuario);
        if (cuentaAdquirirda.getTipoCuenta().equals("Maestro")) {
            botonEliminar.setVisible(false);
            botonEditar.setVisible(false);
            esMaestro = true;
        }
        this.idGrupo = idGrupo;
        GrupoDAO grupoDAO = new GrupoDAO(unidadPersistencia);
        Grupo grupoConsultado = new Grupo();
        grupoConsultado = grupoDAO.adquirirGrupo(idGrupo);
        nombreCuentaMaestro = grupoConsultado.getUsuario().getUsuario();
        nombreGrupo = grupoConsultado.getNombreGrupo();
        etiquetaNombreMaestro.setText(grupoConsultado.getUsuario().getUsuario());
        etiquetaPrecioInscripcion.setText(grupoConsultado.getInscripcion().toString());
        etiquetaPrecioMensualidad.setText(grupoConsultado.getMensualidad().toString());
        etiquetasHorasXML = new String[30];
        etiquetasHorasXML = establecerEtiquetas();
        horasNumericas = new String[30];
        horasNumericas = establecerHoras();
        this.iniciarTablaHorario();
        this.llenarTablaHorario(grupoConsultado.getNombreGrupo());
        this.iniciarTablaAlumnos();
        this.llenarTablaAlumnos(grupoConsultado.getNombreGrupo());
        final ObservableList<Alumno> tablaGrupoSel = tablaAlumnos.getSelectionModel().getSelectedItems();
        tablaAlumnos.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if ((tablaAlumnos.getSelectionModel().getSelectedItem() != null) && (!esMaestro)) {
                    FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaRegistrarPagoAlumnoDireccion.fxml"));
                    try {
                        Parent root = (Parent) loader.load();
                        VentanaRegistrarPagoAlumnoDireccionController ventanaRegistrarPago = loader.getController();
                        ventanaRegistrarPago.establecerDatos(tablaAlumnos.getSelectionModel().getSelectedItem(), nombreUsuario, idGrupo);
                        panelConsultarInfo.getChildren().add(root);
                    } catch (IOException ex) {
                        Logger.getLogger(VentanaConsultarGruposController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    @FXML
    private void desplegarVentanaEditar(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaEditarGrupo.fxml"));
        try {
            Parent root = (Parent) loader.load();
            VentanaEditarGrupoController editarGrupoController = loader.getController();
            editarGrupoController.establecerGrupo(idGrupo, nombreUsuario);////////manejar excepción en caso de estar vacio
            panelConsultarInfo.getChildren().add(root);
        } catch (IOException ex) {
            Logger.getLogger(VentanaConsultarGruposController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void eliminarGrupo(ActionEvent event) throws IOException {
        GrupoDAO nuevoGrupoDAO = new GrupoDAO(unidadPersistencia);
        CuentaDAO cuentaDAO = new CuentaDAO();
        MaestroDAO maestroDAO = new MaestroDAO();
        Cuenta cuentaMaestro = cuentaDAO.obtenerCuenta(nombreCuentaMaestro);
        List<Grupo> listaGrupos = null;
        listaGrupos = nuevoGrupoDAO.adquirirGrupos(cuentaMaestro);

        /////Dar de baja a todos los alumnos
        Grupo grupoEditar = nuevoGrupoDAO.adquirirGrupo(idGrupo);
        List<Alumno> listaAlumnos = nuevoGrupoDAO.obtenerAlumnos(idGrupo);
        if (!listaAlumnos.isEmpty()) {
            listaAlumnos.clear();
            grupoEditar.setAlumnoCollection(listaAlumnos);
            nuevoGrupoDAO.editarGrupo(grupoEditar);
        }

        //Desactivar Maestro
        int gruposActivos = 0;
        int idGrupoActivo = 0;
        if (cuentaMaestro.getTipoCuenta().equals("Maestro")) {
            String nombreMaestroEditar = maestroDAO.adquirirNombreMaestroPorNombreDeUsuario(nombreCuentaMaestro);
            Maestro maestroEditar = maestroDAO.adquirirMaestro(nombreCuentaMaestro);

            for (int i = 0; i < listaGrupos.size(); i++) {
                if (listaGrupos.get(i).getEstaActivo() == 1) {
                    gruposActivos++;
                    idGrupoActivo = listaGrupos.get(i).getIdGrupo();
                }
            }
            if (gruposActivos == 1) {
                if (idGrupoActivo == idGrupo) {
                    maestroEditar.setEstaActivo(0);
                    ////null
                    maestroDAO.editarMaestro(maestroEditar);
                }
            }
        }

        //Descativar Grupo
        Grupo grupoEliminar = new Grupo();
        grupoEliminar = nuevoGrupoDAO.adquirirGrupo(idGrupo);
        grupoEliminar.setEstaActivo(0);
        if (nuevoGrupoDAO.eliminarGrupo(grupoEliminar)) {
            eliminarHorarioGrupo();
            DialogosController.mostrarMensajeInformacion("Eliminado", "El grupo ha sido eliminado", "El grupo se desactivo corectamente");
            FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaConsultarGrupos.fxml"));
            Parent root = (Parent) loader.load();
            VentanaConsultarGruposController ventanaConsultarGruposController = loader.getController();
            ventanaConsultarGruposController.iniciarVentana(nombreUsuario);
            panelConsultarInfo.getChildren().add(root);
        } else {
            DialogosController.mostrarMensajeInformacion("Error", "Parece haber ocurrido un error y el grupo no pudó ser eliminado correctamente", "Por favor contecte al encargado del sistema");
        }
    }

    /**
     * Este metodo eliminar a un grupo de un Horario
     * 
     * @since 1.0 / 5 de junio de 2018
     */
    private void eliminarHorarioGrupo() {
        try {
            File inputFile = new File(rutaXML);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("dia");
            for (int i = 0; i < etiquetasHorasXML.length; i++) {
                actualizarFilaEliminar(nList, doc, etiquetasHorasXML[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Este método reestablece los valores que tendrá una fila de una tabla
     *
     * @since 1.0 / 5 de junio de 2018
     */
    public void actualizarFilaEliminar(NodeList nList, Document doc, String nombreFila) throws TransformerException {
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp); //cada item es un dia de la semana
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;

                if (eElement.getElementsByTagName(nombreFila).item(0).getTextContent().equals(nombreGrupo)) {
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
    private void desplegarVentanaRegistrarAsistencia(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaRegistrarAsistencia.fxml"));
        Parent root = (Parent) loader.load();
        VentanaRegistrarAsistenciaController ventanaRegistrarAsistenciaController = loader.getController();
        ventanaRegistrarAsistenciaController.iniciarVentana(idGrupo, nombreUsuario);
        panelConsultarInfo.getChildren().add(root);
    }
}
