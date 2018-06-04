package controlador;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import negocio.ClienteDAO;
import negocio.Renta;
import negocio.RentaDAO;
import negocio.Utileria;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import persistencia.Horario;

/**
 * Este controlador es usado para manipular la interfaz gráfica al crear una
 * renta
 *
 * @author Irvin Vera
 * @version 1.0 / 5 de junio de 2018
 */
public class VentanaCrearRentaController implements Initializable {

    private Pane panelPrincipal;
    @FXML
    private DatePicker campoFecha;
    @FXML
    private TextField campoCantidad;
    @FXML
    private JFXButton botonRegistrar;
    @FXML
    private ComboBox<String> comboBoxClientes;
    @FXML
    private TableColumn<Horario, String> columnaHorarioGrupo;
    @FXML
    private TableColumn<Horario, String> columnaNombreGrupo;
    @FXML
    private TableColumn<persistencia.Renta, String> columnaHorarioRenta;
    @FXML
    private TableColumn<persistencia.Renta, String> columnaNombreClienteRenta;
    @FXML
    private TableColumn<persistencia.Renta, String> columnaHoraFin;
    String diaDeLaSemanaSeleccionado;
    ObservableList<Horario> horarios;
    @FXML
    private TableView<Horario> tablaGrupos;
    @FXML
    private TableView<persistencia.Renta> tablaRentas;
    private String rutaXML;
    @FXML
    private ComboBox<String> comboBoxHoraInicio;
    List<persistencia.Renta> listaRentas = null;
    List<persistencia.Cliente> clientes = null;
    @FXML
    private ComboBox<String> comboBoxHoraFin;

    /**
     * Este método permite obtener como referencia el panel de la pantalla
     * anterior en donde se mostrara la ventana de crear renta
     *
     * @param panelPrincipal sobre el que se mostrarán la ventana de crear renta
     */
    public void obtenerPanel(Pane panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            CodeSource direccion = VentanaCrearRentaController.class.getProtectionDomain().getCodeSource();
            File fileJar = new File(direccion.getLocation().toURI().getPath());
            File fileDir = fileJar.getParentFile();
            File fileProperties = new File(fileDir.getAbsolutePath() + "/Archivos/Horarios.xml");
            rutaXML = fileProperties.getAbsolutePath();
            llenarComboHorasInicio();
            llenarComboHorasFin();
            llenarComboClientes();
            botonRegistrar.setDisable(true);
        } catch (URISyntaxException ex) {
            Logger.getLogger(VentanaCrearRentaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void registrarRenta(ActionEvent event) {
        boolean cantidadIngresadaCorrecta = true;
        if (campoCantidad.getText().isEmpty() || campoFecha.getValue() == null) {
            DialogosController.mostrarMensajeInformacion("", "Campo vacios", "Debe completar todos los campos para poder registrar una renta");
        } else {
            try {
                Double.parseDouble(campoCantidad.getText());
            } catch (NumberFormatException ex) {
                cantidadIngresadaCorrecta = false;
            }

            if (cantidadIngresadaCorrecta) {
                if (campoCantidad.getText().length() <= 5) {

                    if (Double.parseDouble(campoCantidad.getText()) > 0) {

                        String horaInicioRenta = comboBoxHoraInicio.getSelectionModel().getSelectedItem();
                        String horaFinRenta = comboBoxHoraFin.getSelectionModel().getSelectedItem();

                        int horaInicio = generarCoordenada(horaInicioRenta);
                        int horaFin = generarCoordenada(horaFinRenta);

                        if (horaInicio >= horaFin) {
                            DialogosController.mostrarMensajeInformacion("", "Error en la elección de horario", "No se puede elegir una hora igual o anterior a la hora de inicio");
                        } else if (revisarDsiponibilidadDeHorario(horaInicio, horaFin)) {

                            RentaDAO rentaDAO = new RentaDAO();
                            Renta renta = new Renta();
                            renta.setCantidad(Double.parseDouble(campoCantidad.getText()));
                            renta.setNombreCliente(comboBoxClientes.getSelectionModel().getSelectedItem());
                            renta.setFecha(Utileria.convertirFecha(campoFecha.getValue()));
                            renta.setHoraInicio(Time.valueOf(horaInicioRenta + ":00"));
                            renta.setHoraFin(Time.valueOf(horaFinRenta + ":00"));

                            if (rentaDAO.registrarRenta(renta)) {
                                DialogosController.mostrarMensajeInformacion("", "Registro de renta exitoso", "La renta se ha registrado correctamente");
                                try {
                                    regresarVentanaAnterior();
                                } catch (IOException ex) {
                                    Logger.getLogger(VentanaCrearRentaController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                                DialogosController.mostrarMensajeInformacion("", "Registro de renta fallido", "La renta no se se ha registrado correctamente");
                            }
                        }
                    } else {
                        DialogosController.mostrarMensajeInformacion("", "Numero negativo", "La cantidad a cobrar no puede ser un número negativo");
                    }
                } else {
                    DialogosController.mostrarMensajeInformacion("", "Excede el límite permitido", "El campo de cantidad no debe exceder los 5 caracteres");
                }

            } else {
                DialogosController.mostrarMensajeInformacion("Dato incorrecto", "Las letras no son una cantidad", "Debe ingresar una cantidad numérica");
            }
        }
    }

    /**
     * Este método permite evaluar si el horario elegido para una renta está
     * disponible
     *
     * @param horaInicio de la renta
     * @param horaFin de la renta
     * @return boolean que indica si el horario elegido está disponible
     */
    public boolean revisarDsiponibilidadDeHorario(int horaInicio, int horaFin) {
        boolean horarioDisponible = true;

        if (horaFin == 30) {
            horaFin = 29;
        }

        for (int i = horaInicio; i < horaFin; i++) {
            Horario horario;
            horario = horarios.get(i);
            if (!horario.getDia().equals("Disponible")) {
                horarioDisponible = false;
            }
        }

        if (!horarioDisponible) {
            DialogosController.mostrarMensajeInformacion("Choque de horarios", "El horario elegido está ocupado", "Revise cuidadosamente los horarios disponibles");
        }

        return horarioDisponible;
    }

    @FXML
    private void consultarHorariosDelDia(ActionEvent event) {
        if (campoFecha.getValue() == null) {
            DialogosController.mostrarMensajeInformacion("Campo fecha vacío", "Seleccione una fecha", "Debe ingresar una fecha para consultar los horarios del dia ");
        } else {
            diaDeLaSemanaSeleccionado = Utileria.convertirDia(campoFecha.getValue().getDayOfWeek().toString());
            inicializarTablaHorario();
            llenarTablaRentas();
            llenarTablaHorario();
            marcarNoDisponibles();

            for (int i = 0; i < horarios.size(); i++) {
                Horario horario;
                horario = horarios.get(i);
            }
        }
        botonRegistrar.setDisable(false);

    }

    /**
     * Este método permite llenar la tabla de las rentas al seleccionar una
     * fecha
     */
    public void llenarTablaRentas() {
        RentaDAO rentaDAO = new RentaDAO();
        listaRentas = rentaDAO.obtenerRentasPorFecha(Utileria.convertirFecha(campoFecha.getValue()));
        columnaHorarioRenta.setCellValueFactory(new PropertyValueFactory<persistencia.Renta, String>("FormatoHoraInicio"));
        columnaNombreClienteRenta.setCellValueFactory(new PropertyValueFactory<persistencia.Renta, String>("nombreCliente"));
        columnaHoraFin.setCellValueFactory(new PropertyValueFactory<persistencia.Renta, String>("FormatoHoraFin"));
        tablaRentas.setItems(FXCollections.observableList(listaRentas));
    }

    /**
     * Este método permite inicializar la tabla de los horarios indicando que
     * tipo de objeto recibirá
     */
    public void inicializarTablaHorario() {
        columnaHorarioGrupo.setCellValueFactory(new PropertyValueFactory<Horario, String>("hora"));
        columnaNombreGrupo.setCellValueFactory(new PropertyValueFactory<Horario, String>("dia"));
        horarios = FXCollections.observableArrayList();
        tablaGrupos.setItems(horarios);
    }

    /**
     * Este método permite llenar el combo de los clientes registrados en el
     * sistema
     */
    public void llenarComboClientes() {
        ObservableList<String> clientesCombo = FXCollections.observableArrayList();
        ClienteDAO clienteDAO = new ClienteDAO();
        clientes = clienteDAO.buscarTodosLosClientes();
        for (int i = 0; i < this.clientes.size(); i++) {
            clientesCombo.add(clientes.get(i).getNombre() + " " + clientes.get(i).getApellidos());
        }
        comboBoxClientes.setItems(clientesCombo);
    }

    /**
     * Este método permite llenar la tabla del horario de la semana al
     * seleccionar una día
     */
    public void llenarTablaHorario() {
        try {
            File inputFile = new File(rutaXML);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("dia");
            for (int i = 0; i < 29; i++) {
                Horario h1 = new Horario();
                h1 = establecerFila(i, h1, nList);
                horarios.add(h1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Este método permite marcar en la tabla de horarios como NO DISPONIBLE las
     * horas que está ocupadas por una renta
     */
    public void marcarNoDisponibles() {
        for (int i = 0; i < listaRentas.size(); i++) {

            columnaHoraFin.getCellData(i);
            String[] horaPartidaInicio = columnaHorarioRenta.getCellData(i).split(":");
            String horaArmadaInicio = horaPartidaInicio[0] + ":" + horaPartidaInicio[1];
            int horaInicio = generarCoordenada(horaArmadaInicio);

            String[] horaPartidaFin = columnaHoraFin.getCellData(i).split(":");
            String horaArmadaFin = horaPartidaFin[0] + ":" + horaPartidaFin[1];
            int horaFin = generarCoordenada(horaArmadaFin);

            for (int j = horaInicio; j < horaFin; j++) {
                Horario horario;
                horario = horarios.get(j);
                horario.setDia("No disponible");
            }
        }

    }

    /**
     * Este método llena un combo con las horas del día para iniciar una renta
     */
    public void llenarComboHorasInicio() {
        ObservableList<String> horas = FXCollections.observableArrayList();
        horas.addAll("08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30");
        comboBoxHoraInicio.setItems(horas);
    }

    /**
     *
     * Este método llena un combo con las horas del día para finalizar una renta
     *
     */
    public void llenarComboHorasFin() {
        ObservableList<String> horas = FXCollections.observableArrayList();
        horas.addAll("08:00", "08:30", "09:00", "09:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00");
        comboBoxHoraFin.setItems(horas);
    }

    /**
     * Este método permite llenar una fila con los datos del horario
     * correspondiente a la fila
     *
     * @param i contador de la fila en la que va
     * @param h1 objeto que contiene los datos del horario
     * @param nList nodo en el que buscará en el documento de los horarios
     * @return un objeto con los datos del horario
     */
    public Horario establecerFila(int i, Horario h1, NodeList nList) {
        String nombreEtiqueta = "";
        switch (i) {
            case 0:
                h1.setHora("8:00-8:30");
                nombreEtiqueta = "ocho-ochoMedia";
                break;
            case 1:
                h1.setHora("8:30-9:00");
                nombreEtiqueta = "ochoMedia-nueve";
                break;
            case 2:
                h1.setHora("9:00-9:30");
                nombreEtiqueta = "nueve-nueveMedia";
                break;
            case 3:
                h1.setHora("9:30-10:00");
                nombreEtiqueta = "nueveMedia-diez";
                break;
            case 4:
                h1.setHora("10:00-10:30");
                nombreEtiqueta = "diez-diezMedia";
                break;
            case 5:
                h1.setHora("10:30-11:00");
                nombreEtiqueta = "diezMedia-once";
                break;
            case 6:
                h1.setHora("11:00-11:30");
                nombreEtiqueta = "once-onceMedia";
                break;
            case 7:
                h1.setHora("11:30-12:00");
                nombreEtiqueta = "onceMedia-doce";
                break;
            case 8:
                h1.setHora("12:00-12:30");
                nombreEtiqueta = "doce-doceMedia";
                break;
            case 9:
                h1.setHora("12:30-13:00");
                nombreEtiqueta = "doceMedia-una";
                break;
            case 10:
                h1.setHora("13:00-13:30");
                nombreEtiqueta = "una-unaMedia";
                break;
            case 11:
                h1.setHora("13:30-14:00");
                nombreEtiqueta = "unaMedia-dos";
                break;
            case 12:
                h1.setHora("14:00-14:30");
                nombreEtiqueta = "dos-dosMedia";
                break;
            case 13:
                h1.setHora("14:30-15:00");
                nombreEtiqueta = "dosMedia-tres";
                break;
            case 14:
                h1.setHora("15:00-15:30");
                nombreEtiqueta = "tres-tresMedia";
                break;
            case 15:
                h1.setHora("15:30-16:00");
                nombreEtiqueta = "tresMedia-cuatro";
                break;
            case 16:
                h1.setHora("16:00-16:30");
                nombreEtiqueta = "cuatro-cuatroMedia";
                break;
            case 17:
                h1.setHora("16:30-17:00");
                nombreEtiqueta = "cuatroMedia-cinco";
                break;
            case 18:
                h1.setHora("17:00-17:30");
                nombreEtiqueta = "cinco-cincoMedia";
                break;
            case 19:
                h1.setHora("17:30-18:00");
                nombreEtiqueta = "cincoMedia-seis";
                break;
            case 20:
                h1.setHora("18:00.18:30");
                nombreEtiqueta = "seis-seisMedia";
                break;
            case 21:
                h1.setHora("18:30-19:00");
                nombreEtiqueta = "seisMedia-siete";
                break;
            case 22:
                h1.setHora("19:00-19:30");
                nombreEtiqueta = "siete-sieteMedia";
                break;
            case 23:
                h1.setHora("19:30-20:00");
                nombreEtiqueta = "sieteMedia-ocho";
                break;
            case 24:
                h1.setHora("20:00-20:30");
                nombreEtiqueta = "cuatroMedia-cinco";
                break;
            case 25:
                h1.setHora("20:30-21:00");
                nombreEtiqueta = "cinco-cincoMedia";
                break;
            case 26:
                h1.setHora("21:00-21:30");
                nombreEtiqueta = "cincoMedia-seis";
                break;
            case 27:
                h1.setHora("21:30.22:00");
                nombreEtiqueta = "seis-seisMedia";
                break;
            case 28:
                h1.setHora("22:00-22:30");
                nombreEtiqueta = "seisMedia-siete";
                break;
            case 29:
                h1.setHora("22:30-23:00");
                nombreEtiqueta = "siete-sieteMedia";
                break;

        }

        Node nNode = nList.item(generarDia(diaDeLaSemanaSeleccionado));
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) nNode;
            switch (diaDeLaSemanaSeleccionado) {
                case "Domingo":
                    h1.setDia(eElement.getElementsByTagName(nombreEtiqueta).item(0).getTextContent());
                    break;
                case "Lunes":
                    h1.setDia(eElement.getElementsByTagName(nombreEtiqueta).item(0).getTextContent());
                    break;
                case "Martes":
                    h1.setDia(eElement.getElementsByTagName(nombreEtiqueta).item(0).getTextContent());
                    break;
                case "Miercoles":
                    h1.setDia(eElement.getElementsByTagName(nombreEtiqueta).item(0).getTextContent());
                    break;
                case "Jueves":
                    h1.setDia(eElement.getElementsByTagName(nombreEtiqueta).item(0).getTextContent());
                    break;
                case "Viernes":
                    h1.setDia(eElement.getElementsByTagName(nombreEtiqueta).item(0).getTextContent());
                    break;
                case "Sabado":
                    h1.setDia(eElement.getElementsByTagName(nombreEtiqueta).item(0).getTextContent());
                    break;
            }
        }
        return h1;
    }

    @FXML
    private void cerrarVentanaCrearRenta(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaRentas.fxml"));
        Parent root = (Parent) loader.load();
        VentanaRentasController ventanaRentas = loader.getController();
        ventanaRentas.obtenerPanel(panelPrincipal);
        ventanaRentas.llenarTablaRentas();
        panelPrincipal.getChildren().add(root);
    }

    /**
     * Este método permite generar un número a partir de una hora seleccionada
     *
     * @param horaRenta hora seleccionada por el usuario
     * @return un valor entero que corresponde a la hora seleccionada
     */
    public int generarCoordenada(String horaRenta) {
        int coordenada;
        Map<String, Integer> coordenadas = new HashMap<>();
        coordenadas.put("08:00", 0);
        coordenadas.put("08:30", 1);
        coordenadas.put("09:00", 2);
        coordenadas.put("09:30", 3);
        coordenadas.put("10:00", 4);
        coordenadas.put("10:30", 5);
        coordenadas.put("11:00", 6);
        coordenadas.put("11:30", 7);
        coordenadas.put("12:00", 8);
        coordenadas.put("12:30", 9);
        coordenadas.put("13:00", 10);
        coordenadas.put("13:30", 11);
        coordenadas.put("14:00", 12);
        coordenadas.put("14:30", 13);
        coordenadas.put("15:00", 14);
        coordenadas.put("15:30", 15);
        coordenadas.put("16:00", 16);
        coordenadas.put("16:30", 17);
        coordenadas.put("17:00", 18);
        coordenadas.put("17:30", 19);
        coordenadas.put("18:00", 20);
        coordenadas.put("18:30", 21);
        coordenadas.put("19:00", 22);
        coordenadas.put("19:30", 23);
        coordenadas.put("20:00", 24);
        coordenadas.put("20:30", 25);
        coordenadas.put("21:00", 26);
        coordenadas.put("21:30", 27);
        coordenadas.put("22:00", 28);
        coordenadas.put("22:30", 29);
        coordenadas.put("23:00", 30);

        coordenada = coordenadas.get(horaRenta);
        return coordenada;
    }

    /**
     * Este método permite generar valor a partir de un dia selccionado
     *
     * @param elementoSeleccionado dia de la semana seleccionado
     * @return un valor entero que está asociado al dia seleccionado
     */
    public int generarDia(String elementoSeleccionado) {
        int dia = 0;
        switch (elementoSeleccionado) {
            case "Domingo":
                dia = 0;
                break;
            case "Lunes":
                dia = 1;
                break;

            case "Martes":
                dia = 2;
                break;

            case "Miercoles":
                dia = 3;
                break;

            case "Jueves":
                dia = 4;
                break;

            case "Viernes":
                dia = 5;
                break;

            case "Sabado":
                dia = 6;
                break;
        }
        return dia;
    }

    /**
     * Este método permite limitar los caracteres permitidos en un campo de
     * texto
     *
     * @param event Ingreso de un valor del usuario
     * @param campo que se desea limitar
     * @param caracteresMaximos numero de caracteres permitidos en ese campo
     */
    public void limitarCaracteres(KeyEvent event, TextField campo, int caracteresMaximos) {
        if (campo.getText().length() >= caracteresMaximos) {
            event.consume();
        }
    }

    @FXML
    private void limitarCaracteresCantidad(KeyEvent event) {
        char caracter = event.getCharacter().charAt(0);
        limitarCaracteres(event, campoCantidad, 5);
        if (!Character.isDigit(caracter)) {
            event.consume();
        }
    }

    /**
     * Este método permite regresar a la ventana anterior
     *
     *
     */
    public void regresarVentanaAnterior() throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaRentas.fxml"));
        Parent root = (Parent) loader.load();
        VentanaRentasController ventanaRentas = loader.getController();
        ventanaRentas.obtenerPanel(panelPrincipal);
        ventanaRentas.llenarTablaRentas();
        panelPrincipal.getChildren().add(root);

    }

}
