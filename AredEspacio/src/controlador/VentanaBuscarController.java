package controlador;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
    private String seccion = "";
    private Pane panelCoincidencia;
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
    public void buscarCoincidencias() throws IOException {
        etiquetaNoCoincidencias.setText("");
        if (!campoBusqueda.getText().isEmpty()) {
            switch (seccion) {
                case "Alumnos":
                    AlumnoDAO alumnoDAO = new AlumnoDAO();
                    Alumno alumno = new Alumno();
                    List<persistencia.Alumno> alumnos = null;
                    alumnos = alumnoDAO.buscarAlumno(campoBusqueda.getText());

                    Pane panelAnterior = new Pane();

                    int contadorCoincidencias = 0;
                    if (alumnos.size() > 0) {
                        for (int i = 0; i < alumnos.size(); i++) {
                            FXMLLoader loader;
                            Parent root;
                            loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/PanelCoincidencia.fxml"));
                            panelCoincidencia = (Pane) loader.load();
                            PanelCoincidenciaController coincidenciasEncontradas = loader.getController();
                            coincidenciasEncontradas.obtenerSeccion(seccion, panelPrincipal);
                            if (contadorCoincidencias < 3) {
                                if (contadorCoincidencias == 0) {
                                    panelCoincidencia.setLayoutX(45);
                                    panelCoincidencia.setLayoutY(110);
                                } else {
                                    panelCoincidencia.relocate(panelAnterior.getLayoutX() + 380, 110);
                                }
                                contadorCoincidencias++;
                            } else {
                                contadorCoincidencias = 1;
                                panelCoincidencia.relocate(45, panelAnterior.getLayoutY() + 200);
                            }
                            coincidenciasEncontradas.llenarDatosAlumno(alumnos.get(i));
                            panelPrincipal.getChildren().add(panelCoincidencia);
                            panelAnterior = panelCoincidencia;
                        }
                    } else {
                        etiquetaNoCoincidencias.setText("Alumno no encontrado");
                    }
                    break;

                case "Maestros":
                    MaestroDAO maestroDAO = new MaestroDAO();
                    Maestro maestro = new Maestro();
                    List<persistencia.Maestro> maestros = null;
                    maestros = maestroDAO.buscarMaestro(campoBusqueda.getText());

                    persistencia.Maestro maestroCoincidencia;
                    Pane panel = new Pane();

                    int contadorCoincidenciasEncontradas = 0;
                    if (maestros.size() > 0) {
                        for (int i = 0; i < maestros.size(); i++) {
                            FXMLLoader loader;
                            Parent root;
                            loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/PanelCoincidencia.fxml"));
                            panelCoincidencia = (Pane) loader.load();
                            PanelCoincidenciaController coincidencias = loader.getController();
                            coincidencias.obtenerSeccion(seccion, panelPrincipal);
                            if (contadorCoincidenciasEncontradas < 3) {
                                if (contadorCoincidenciasEncontradas == 0) {
                                    panelCoincidencia.setLayoutX(45);
                                    panelCoincidencia.setLayoutY(110);

                                } else {
                                    panelCoincidencia.relocate(panel.getLayoutX() + 380, 110);
                                }
                                contadorCoincidenciasEncontradas++;
                            } else {
                                contadorCoincidenciasEncontradas = 1;
                                panelCoincidencia.relocate(45, panel.getLayoutY() + 200);
                            }
                            coincidencias.llenarDatosMaestro(maestros.get(i));
                            panelPrincipal.getChildren().add(panelCoincidencia);
                            panel = panelCoincidencia;
                        }
                    } else {
                        etiquetaNoCoincidencias.setText("Maestro no encontrado");
                    }
                    break;

                case "Clientes":
                    ClienteDAO clienteDAO = new ClienteDAO();
                    Cliente cliente = new Cliente();
                    List<persistencia.Cliente> clientes = null;
                    clientes = clienteDAO.buscarCliente(campoBusqueda.getText());

                    Pane panelA = new Pane();

                    int contadorCoincidenciasEncon = 0;
                    if (clientes.size() > 0) {
                        for (int i = 0; i < clientes.size(); i++) {
                            FXMLLoader loader;
                            Parent root;
                            loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/PanelCoincidencia.fxml"));
                            panelCoincidencia = (Pane) loader.load();
                            PanelCoincidenciaController coincidenciasEncontradas = loader.getController();
                            coincidenciasEncontradas.obtenerSeccion(seccion, panelPrincipal);
                            if (contadorCoincidenciasEncon < 3) {
                                if (contadorCoincidenciasEncon == 0) {
                                    panelCoincidencia.setLayoutX(45);
                                    panelCoincidencia.setLayoutY(110);
                                } else {
                                    panelCoincidencia.relocate(panelA.getLayoutX() + 380, 110);
                                }
                                contadorCoincidenciasEncon++;
                            } else {
                                contadorCoincidencias = 1;
                                panelCoincidencia.relocate(45, panelA.getLayoutY() + 200);
                            }
                            coincidenciasEncontradas.llenarDatosCliente(clientes.get(i));
                            panelPrincipal.getChildren().add(panelCoincidencia);
                            panelA = panelCoincidencia;
                        }
                    } else {
                        etiquetaNoCoincidencias.setText("Cliente no encontrado");
                    }
                    break;
                default:
                    break;
            }

        }
    }
}
