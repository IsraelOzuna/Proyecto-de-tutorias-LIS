package controlador;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import negocio.Alumno;
import negocio.AlumnoDAO;
import negocio.ClienteDAO;
import negocio.MaestroDAO;
import negocio.PromocionDAO;
import persistencia.Cliente;
import persistencia.Maestro;

/**
 * Este controlador es usado para desplegar una lista de alumnos, clientes,
 * maestros o promociones según sea el caso y también para realizar busquedas
 * especificas de alguno de estos
 *
 * @author Israel Reyes Ozuna
 * @version 1.0 / 5 de junio de 2018
 */
public class VentanaBuscarController implements Initializable {

    @FXML
    private Label etiquetaIdentidicador;
    @FXML
    private JFXButton botonBuscar;
    @FXML
    private TextField campoBusqueda;
    @FXML
    private Label etiquetaNoCoincidencias;
    @FXML
    private GridPane gridCoincidencias;
    @FXML
    private TableView tabla;
    @FXML
    private TableColumn<?, String> columnaNombre;
    @FXML
    private TableColumn<?, String> columnaApellidos;
    @FXML
    private TableColumn<?, String> columnaCorreoElectronico;
    @FXML
    private TableColumn<?, String> columnaTelefono;
    @FXML
    private TableView tablaPromociones;
    @FXML
    private TableColumn<?, String> columnaNombrePromocion;
    @FXML
    private TableColumn<?, String> columnaDescripcion;
    @FXML
    private TableColumn<?, String> columnaTipoPromocion;
    private String seccion = "";
    private Pane panelPrincipal;
    String nombreUsuarioActual;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tabla.setVisible(false);
        tablaPromociones.setVisible(false);
    }

    /**
     * Este método ayuda a saber que es lo que el usuario quiere buscar en el
     * sistema
     *
     * @param seccion el nombre de la sección dónde el usuario quiere buscar
     * @param panelPrincipal panel para insertar los componentes
     * @param nombreUsuario usuario el cual realiza las busquedas
     * @since 1.0 / 5 de junio de 2018
     */
    public void obtenerSeccion(String seccion, Pane panelPrincipal, String nombreUsuario) {
        nombreUsuarioActual = nombreUsuario;
        this.seccion = seccion;
        this.panelPrincipal = panelPrincipal;
        etiquetaIdentidicador.setText(seccion);
        llenarTabla();

    }

    /**
     * Este método llena la tabla en donde se visualizaran los alumnos,
     * maestros, clientes o promociones
     *
     * @since 1.0 / 5 de junio de 2018
     */
    public void llenarTabla() {
        switch (seccion) {
            case "Alumnos":
                List<persistencia.Alumno> alumnos;
                AlumnoDAO alumnoDAO = new AlumnoDAO();

                alumnos = alumnoDAO.obtenerAlumnos();

                columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

                columnaApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));

                columnaCorreoElectronico.setCellValueFactory(new PropertyValueFactory<>("correoElectronico"));

                columnaTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

                tabla.setItems(FXCollections.observableList(alumnos));
                tabla.setVisible(true);
                break;

            case "Maestros":
                List<persistencia.Maestro> maestros;
                MaestroDAO maestroDAO = new MaestroDAO();

                maestros = maestroDAO.adquirirMaestros();

                columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

                columnaApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));

                columnaCorreoElectronico.setCellValueFactory(new PropertyValueFactory<>("correoElectronico"));

                columnaTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

                tabla.setItems(FXCollections.observableList(maestros));
                tabla.setVisible(true);
                break;

            case "Clientes":
                List<persistencia.Cliente> clientes;
                ClienteDAO clienteDAO = new ClienteDAO();

                clientes = clienteDAO.buscarTodosLosClientes();

                columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

                columnaApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));

                columnaCorreoElectronico.setCellValueFactory(new PropertyValueFactory<>("correo"));

                columnaTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

                tabla.setItems(FXCollections.observableList(clientes));
                tabla.setVisible(true);
                break;

            case "Promociones":
                List<persistencia.Promocion> promociones;
                PromocionDAO promocionDAO = new PromocionDAO();

                promociones = promocionDAO.obtenerPromociones();

                columnaNombrePromocion.setCellValueFactory(new PropertyValueFactory<>("nombrePromocion"));
                columnaDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
                columnaTipoPromocion.setCellValueFactory(new PropertyValueFactory<>("tipoPromocion"));

                tablaPromociones.setItems(FXCollections.observableList(promociones));
                tablaPromociones.setVisible(true);
                break;
        }

    }

    @FXML
    private void desplegarNuevoRegistro() throws IOException {
        FXMLLoader loader;
        Parent root;
        switch (seccion) {
            case "Alumnos":
                loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaRegistrarAlumno.fxml"));
                root = (Parent) loader.load();
                VentanaRegistrarAlumnoController ventanaRegistrarAlumno = loader.getController();
                ventanaRegistrarAlumno.obtenerUsuario(nombreUsuarioActual);
                panelPrincipal.getChildren().add(root);
                break;
            case "Maestros":
                loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaRegistrarMaestro.fxml"));
                root = (Parent) loader.load();
                VentanaRegistrarMaestroController ventanaRegistrarMaestro = loader.getController();
                ventanaRegistrarMaestro.llenarDatos(nombreUsuarioActual);
                panelPrincipal.getChildren().add(root);
                break;

            case "Clientes":
                loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaRegistrarCliente.fxml"));
                root = (Parent) loader.load();
                VentanaRegistrarClienteController ventanaRegistrarCliente = loader.getController();
                ventanaRegistrarCliente.obtenerUsuario(nombreUsuarioActual);
                panelPrincipal.getChildren().add(root);
                break;

            case "Promociones":
                loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaCrearPromocion.fxml"));
                root = (Parent) loader.load();
                VentanaCrearPromocionController ventanaCrearPromocion = loader.getController();
                ventanaCrearPromocion.iniciarVentanaDesdeInscripcion(etiquetaIdentidicador.getText(), null, nombreUsuarioActual);
                panelPrincipal.getChildren().clear();
                panelPrincipal.getChildren().add(root);
            default:
                break;
        }
    }

    @FXML
    private void buscarCoincidencias() {
        tabla.setVisible(false);
        tablaPromociones.setVisible(false);
        etiquetaNoCoincidencias.setText("");
        gridCoincidencias.getChildren().clear();
        if (!campoBusqueda.getText().isEmpty()) {
            switch (seccion) {
                case "Alumnos":
                    mostrarCoincidenciasAlumno();
                    break;

                case "Maestros":
                    mostrarCoincidenciasMaestro();
                    break;

                case "Clientes":
                    mostrarCoincidenciasCliente();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Este método ayuda llenar el panel de las busquedas con los alumnos que se
     * han encontrado con el nombre que fue ingresado
     *
     * @since 1.0 / 5 de junio de 2018
     */
    public void mostrarCoincidenciasAlumno() {
        AlumnoDAO alumnoDAO = new AlumnoDAO();
        Alumno alumno = new Alumno();
        List<persistencia.Alumno> alumnos = null;
        alumnos = alumnoDAO.buscarAlumno(campoBusqueda.getText());

        int contadorCoincidencias = 0;
        if (alumnos.size() > 0) {
            int filas = alumnos.size() / 3;
            if (alumnos.size() % 3 != 0) {
                filas = ((alumnos.size() / 3) + 1);
            }
            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < 3; j++) {
                    if (contadorCoincidencias < alumnos.size()) {
                        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/PanelCoincidencia.fxml"));
                        Parent root;

                        try {
                            root = (Parent) loader.load();
                            PanelCoincidenciaController controlador = loader.getController();
                            controlador.obtenerSeccion(seccion, panelPrincipal, nombreUsuarioActual);
                            controlador.llenarDatosAlumno(alumnos.get(contadorCoincidencias));
                            contadorCoincidencias++;
                            gridCoincidencias.add(root, j, i);
                        } catch (IOException ex) {
                            Logger.getLogger(VentanaBuscarController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        } else {
            etiquetaNoCoincidencias.setText("Alumno no encontrado");
        }
    }

    /**
     * Este método ayuda llenar el panel de las busquedas con los Maestros que
     * se han encontrado con el nombre que fue ingresado
     *
     * @since 1.0 / 5 de junio de 2018
     */
    public void mostrarCoincidenciasMaestro() {
        MaestroDAO maestroDAO = new MaestroDAO();
        Maestro maestro = new Maestro();
        List<persistencia.Maestro> maestros = null;
        maestros = maestroDAO.buscarMaestro(campoBusqueda.getText());

        int contadorCoincidenciasEncontradas = 0;
        if (maestros.size() > 0) {
            int filas = maestros.size() / 3;
            if (maestros.size() % 3 != 0) {
                filas = ((maestros.size() / 3) + 1);
            }
            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < 3; j++) {
                    if (contadorCoincidenciasEncontradas < maestros.size()) {
                        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/PanelCoincidencia.fxml"));
                        Parent root;

                        try {
                            root = (Parent) loader.load();
                            PanelCoincidenciaController controlador = loader.getController();
                            controlador.obtenerSeccion(seccion, panelPrincipal, nombreUsuarioActual);
                            controlador.llenarDatosMaestro(maestros.get(contadorCoincidenciasEncontradas));
                            contadorCoincidenciasEncontradas++;
                            gridCoincidencias.add(root, j, i);
                        } catch (IOException ex) {
                            Logger.getLogger(VentanaBuscarController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        } else {
            tabla.setVisible(false);
            etiquetaNoCoincidencias.setText("Maestro no encontrado");
        }
    }

    /**
     * Este método ayuda llenar el panel de las busquedas con los clientes que
     * se han encontrado con el nombre que fue ingresado
     *
     * @since 1.0 / 5 de junio de 2018
     */
    public void mostrarCoincidenciasCliente() {
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = new Cliente();
        List<persistencia.Cliente> clientes = null;
        clientes = clienteDAO.buscarCliente(campoBusqueda.getText());

        int contadorCoincidenciasEncon = 0;
        if (clientes.size() > 0) {
            int filas = clientes.size() / 3;
            if (clientes.size() % 3 != 0) {
                filas = ((clientes.size() / 3) + 1);
            }
            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < 3; j++) {
                    if (contadorCoincidenciasEncon < clientes.size()) {
                        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/PanelCoincidencia.fxml"));
                        Parent root;

                        try {
                            root = (Parent) loader.load();
                            PanelCoincidenciaController controlador = loader.getController();
                            controlador.obtenerSeccion(seccion, panelPrincipal, nombreUsuarioActual);
                            controlador.llenarDatosCliente(clientes.get(contadorCoincidenciasEncon));
                            contadorCoincidenciasEncon++;
                            gridCoincidencias.add(root, j, i);
                        } catch (IOException ex) {
                            Logger.getLogger(VentanaBuscarController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        } else {
            etiquetaNoCoincidencias.setText("Cliente no encontrado");
        }
    }

    /**
     * Este método oculta funcionalidades al maestro que son únicas del Director
     *
     * @since 1.0 / 5 de junio de 2018
     */
    public void ocultarComponentes() {
        botonBuscar.setVisible(false);
        campoBusqueda.setVisible(false);
    }
}
