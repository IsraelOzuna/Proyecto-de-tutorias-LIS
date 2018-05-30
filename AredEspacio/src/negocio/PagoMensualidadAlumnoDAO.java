package negocio;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import persistencia.PagoalumnoJpaController;
import persistencia.Alumno;
import persistencia.AlumnoJpaController;
import persistencia.Grupo;
import persistencia.GrupoJpaController;
import persistencia.Pagoalumno;

/**
 *
 * @author iro19
 */
public class PagoMensualidadAlumnoDAO implements IPagoMensualidadAlumno {

    @Override
    public boolean registrarMensualidad(PagoMensualidadAlumno pagoMensualidad, int idAlumno, String nombreGrupo) {
        boolean pagoInscripcionExitoso = true;

        if (pagoMensualidad != null) {
            Alumno alumno = null;
            Grupo grupo = null;
            int idGrupo;
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
            PagoalumnoJpaController pagoMensualidadController = new PagoalumnoJpaController(entityManagerFactory);
            AlumnoJpaController encontrarAlumno = new AlumnoJpaController(entityManagerFactory);
            GrupoJpaController encontrarGrupo = new GrupoJpaController(entityManagerFactory);

            try {
                alumno = encontrarAlumno.findAlumno(idAlumno);
                idGrupo = encontrarGrupo.encontrarGrupoAlumno(nombreGrupo);
                grupo = encontrarGrupo.findGrupo(idGrupo);
            } catch (Exception ex) {
                Logger.getLogger(PagoMensualidadAlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            persistencia.Pagoalumno pagoNuevo = new persistencia.Pagoalumno();
            pagoNuevo.setFechaPago(pagoMensualidad.getFechaPagoInscripcion());
            pagoNuevo.setCantidad(pagoMensualidad.getCantidad());
            pagoNuevo.setIdAlumno(alumno);
            pagoNuevo.setIdGrupo(grupo);
            pagoNuevo.setTipoPago("1");
            try {
                pagoMensualidadController.create(pagoNuevo);
            } catch (Exception ex) {
                pagoInscripcionExitoso = false;
                Logger.getLogger(PagoMensualidadAlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            pagoInscripcionExitoso = false;
        }
        return pagoInscripcionExitoso;
    }

    @Override
    public List<Pagoalumno> obtenerPagosDeAlumno(int idAlumno) {
        List<Pagoalumno> pagos;

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        PagoalumnoJpaController pagoMensualidadController = new PagoalumnoJpaController(entityManagerFactory);
        
        pagos = pagoMensualidadController.obtenerPagosPorAlumno(idAlumno);
        
        return pagos;
    }
}
