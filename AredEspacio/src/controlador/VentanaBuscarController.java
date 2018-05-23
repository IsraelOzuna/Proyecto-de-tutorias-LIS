package controlador;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import negocio.Alumno;
import negocio.AlumnoDAO;
import negocio.ClienteDAO;
import negocio.MaestroDAO;
import persistencia.Cliente;
import persistencia.Maestro;

public class VentanaBuscarController implements Initializable {

    @FXML
    private JFXButton botonRegistrarNuevo;
    @FXML
    private Label etiquetaIdentidicador;
    @FXML
    private AnchorPane panelBuscar;
    @FXML
    private JFXButton botonBuscar;
    @FXML
    private TextField campoBusqueda;
    @FXML
    private Label etiquetaNoCoincidencias;
    @FXML
    private ScrollPane scrollCoincidencias;
    @FXML
    private GridPane gridCoincidencias;
    private String seccion = "";
    private Pane panelPrincipal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void obtenerSeccion(String seccion, Pane panelPrincipal) {
        this.seccion = seccion;
        this.panelPrincipal = panelPrincipal;
        etiquetaIdentidicador.setText(seccion);
    }

    @FXML
    public void desplegarNuevoRegistro() throws IOException {
        FXMLLoader loader;
        Parent root;
        switch (seccion) {
            case "Alumnos":
                loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaRegistrarAlumno.fxml"));
                root = (Parent) loader.load();
                VentanaRegistrarAlumnoController ventanaRegistrarAlumno = loader.getController();
                panelPrincipal.getChildren().add(root);
                break;
            case "Maestros":
                loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaRegistrarMaestro.fxml"));
                root = (Parent) loader.load();
                VentanaRegistrarMaestroController ventanaRegistrarMaestro = loader.getController();
                panelPrincipal.getChildren().add(root);
                break;

            case "Clientes":
                loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaRegistrarCliente.fxml"));
                root = (Parent) loader.load();
                VentanaRegistrarClienteController ventanaRegistrarCliente = loader.getController();
                panelPrincipal.getChildren().add(root);
                break;
            default:
                break;
        }
    }

    @FXML
    public void buscarCoincidencias() {
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
                            controlador.obtenerSeccion(seccion, panelPrincipal);
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
                            controlador.obtenerSeccion(seccion, panelPrincipal);
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
            etiquetaNoCoincidencias.setText("Maestro no encontrado");
        }
    }

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
                            controlador.obtenerSeccion(seccion, panelPrincipal);
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
}
