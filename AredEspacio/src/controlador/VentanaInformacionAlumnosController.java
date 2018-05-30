package controlador;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import negocio.AlumnoDAO;
import negocio.GrupoDAO;
import negocio.PagoMensualidadAlumnoDAO;
import negocio.Utileria;
import persistencia.Alumno;
import persistencia.Cuenta;
import persistencia.Grupo;
import persistencia.Pagoalumno;

/**
 * FXML Controller class
 *
 * @author Israel Reyes Ozuna
 */
public class VentanaInformacionAlumnosController implements Initializable {

    @FXML
    private ImageView imagenPerfil;
    @FXML
    private JFXButton botonCerrar;
    @FXML
    private Button botonDarBaja;
    @FXML
    private Button botonEditar;
    @FXML
    private Label etiquetaNombre;
    @FXML
    private Label etiquetaFechaNacimiento;
    @FXML
    private Label etiquetaCorreo;
    @FXML
    private Label etiquetaTelefono;
    @FXML
    private AnchorPane panelInformacionAlumno;
    @FXML
    private AnchorPane panelTrasero;
    @FXML
    private Button botonRegistrarPago;
    private Alumno alumno;
    private Pane panelPrincipal;
    @FXML
    private Button botonDarBaja1;
    @FXML
    private Button botonInscribir;
    List<String> grupos;
    private String nombreUsuarioActual;

    public void obtenerAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public void obtenerPanel(Pane panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    public void cerrarDetalles(ActionEvent event) {
        panelTrasero.setVisible(false);
    }

    @FXML
    public void desplegarVentanaEditar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaEditarAlumno.fxml"));
        Parent root = (Parent) loader.load();
        VentanaEditarAlumnoController ventanaEditar = loader.getController();
        ventanaEditar.llenarCampos(alumno, nombreUsuarioActual);
        panelPrincipal.getChildren().add(root);
    }

    public void llenarCamposInformacion(String nombreUsuario) {
        nombreUsuarioActual = nombreUsuario;
        Calendar fechaNacimiento = Calendar.getInstance();
        fechaNacimiento.setTime(alumno.getFechaNacimiento());
        int dia = fechaNacimiento.get(Calendar.DAY_OF_MONTH);
        int mes = fechaNacimiento.get(Calendar.MONTH);
        int anio = fechaNacimiento.get(Calendar.YEAR);

        etiquetaNombre.setText(alumno.getNombre() + " " + alumno.getApellidos());
        etiquetaCorreo.setText(alumno.getCorreoElectronico());
        etiquetaTelefono.setText(alumno.getTelefono());
        etiquetaFechaNacimiento.setText(dia + " de " + Utileria.convertirMeses(mes) + " de " + anio);
        if (alumno.getRutaFoto() != null) {
            Image foto = new Image("file:" + System.getProperty("user.dir") + "\\imagenesAlumnos\\" + alumno.getRutaFoto(), 100, 100, false, true, true);
            imagenPerfil.setImage(foto);
        }
    }

    @FXML
    public void desplegarVentanaRegistrarMensualidad(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaRegistrarMensualidadAlumno.fxml"));
        Parent root = (Parent) loader.load();
        VentanaRegistrarMensualidadAlumnoController ventanaRegistrarMensualidad = loader.getController();
        ventanaRegistrarMensualidad.llenarDatos(alumno.getRutaFoto(), alumno.getNombre(), alumno.getApellidos(), alumno.getIdAlumno());
        panelPrincipal.getChildren().add(root);
    }

    @FXML
    private void visualizarPagosAlumno(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaHistorialDePagosAlumnos.fxml"));
        Parent root = (Parent) loader.load();
        VentanaHistorialDePagosAlumnosController ventanaHistorialDePagos = loader.getController();
        String nombreCompletoAlumno = alumno.getNombre() + " " + alumno.getApellidos();
        PagoMensualidadAlumnoDAO pago = new PagoMensualidadAlumnoDAO();
        List<Pagoalumno> pagosAlumnos = pago.obtenerPagosDeAlumno(alumno.getIdAlumno());
        ventanaHistorialDePagos.obtenerDatos(panelPrincipal, nombreCompletoAlumno, alumno.getRutaFoto());
        ventanaHistorialDePagos.llenarTablaPagos(pagosAlumnos);
        panelPrincipal.getChildren().add(root);
    }

    @FXML
    private void desplegarVentanaInscribirAlumno(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(VentanaMenuDirectorController.class.getResource("/vista/VentanaInscribirAlumno.fxml"));
        Parent root = (Parent) loader.load();
        VentanaInscribirAlumnoController ventanaInscribir = loader.getController();
        ventanaInscribir.llenarCampos(alumno, nombreUsuarioActual);
        panelPrincipal.getChildren().add(root);
    }

    @FXML
    private void darDeBajaAlumno(ActionEvent event) {
        List<String> gruposAlumno = new ArrayList<>();
        gruposAlumno.addAll(obtenerNombreGrupos(grupos));

        ChoiceDialog<String> opcionesGruposAlumno = new ChoiceDialog<>("", gruposAlumno);
        opcionesGruposAlumno.setTitle("Dar de baja");
        opcionesGruposAlumno.setHeaderText("Grupos del alumno");
        opcionesGruposAlumno.setContentText("Selecciona el grupo donde desea dar de baja al alumno:" + "\n");
        Optional<String> resultado = opcionesGruposAlumno.showAndWait();

        if (resultado.isPresent() && !resultado.get().equals("")) {
            GrupoDAO grupoDAO = new GrupoDAO("AredEspacioPU");
            Grupo grupo = obtenerGrupo(opcionesGruposAlumno.getSelectedItem());
            List<Alumno> listaAlumnos = grupoDAO.obtenerAlumnos(grupo.getIdGrupo());

            listaAlumnos.remove(alumno);
            grupo.setAlumnoCollection(listaAlumnos);
            if (grupoDAO.editarGrupo(grupo)) {
                DialogosController.mostrarMensajeInformacion("Guardado", "Baja realizada", "El alumno fue dado de baja correctamente");
            } else {
                DialogosController.mostrarMensajeAdvertencia("Error", "No se pudo dar de baja", "El alumno no fue dado de baja");
            }

        }
    }

    public Grupo obtenerGrupo(String nombreGrupo) {
        GrupoDAO grupoDAO = new GrupoDAO("AredEspacioPU");
        List<Grupo> listaGrupos;
        Grupo grupoObtenido = new Grupo();
        Cuenta usuario = new Cuenta();
        listaGrupos = grupoDAO.adquirirGrupos(usuario);
        for (int i = 0; i < listaGrupos.size(); i++) {
            if ((listaGrupos.get(i).getNombreGrupo().equals(nombreGrupo)) && (listaGrupos.get(i).getEstaActivo() == 1)) {
                grupoObtenido = listaGrupos.get(i);
            }
        }
        return grupoObtenido;
    }

    private ArrayList<String> obtenerNombreGrupos(List<String> grupos) {
        ArrayList<String> nombreGrupos = new ArrayList();
        AlumnoDAO alumnoDAO = new AlumnoDAO();

        grupos = alumnoDAO.encontrarGruposAlumno(alumno.getIdAlumno());
        for (int i = 0; i < grupos.size(); i++) {
            nombreGrupos.add(grupos.get(i));
        }
        return nombreGrupos;
    }
}
