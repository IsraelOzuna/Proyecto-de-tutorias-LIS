package negocio;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import persistencia.PagoalumnoJpaController;
import persistencia.Alumno;
import persistencia.AlumnoJpaController;
import persistencia.Grupo;
import persistencia.GrupoJpaController;

/**
 *
 * @author iro19
 */
public class PagoInscripcionAlumnoDAO implements IPagoInscripcionAlumno {

    @Override
    public boolean registrarInscripcion(PagoInscripcionAlumno inscripcionAlumno, int idAlumno, String nombreGrupo) {
        boolean pagoInscripcionExitoso = true;

        if (inscripcionAlumno != null) {
            Alumno alumno = null;
            Grupo grupo = null;
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
            PagoalumnoJpaController pagoMensualidadController = new PagoalumnoJpaController(entityManagerFactory);
            AlumnoJpaController encontrarAlumno = new AlumnoJpaController(entityManagerFactory);
            GrupoJpaController encontrarGrupo = new GrupoJpaController(entityManagerFactory);

            try {
                alumno = encontrarAlumno.findAlumno(idAlumno);
                grupo = encontrarGrupo.findGrupo(nombreGrupo);
            } catch (Exception ex) {
                Logger.getLogger(PagoInscripcionAlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            persistencia.Pagoalumno pagoNuevo = new persistencia.Pagoalumno();
            pagoNuevo.setFechaPago(inscripcionAlumno.getFechaPagoInscripcion());
            pagoNuevo.setCantidad(inscripcionAlumno.getCantidad());
            pagoNuevo.setIdAlumno(alumno);
            pagoNuevo.setNombreGrupo(grupo);
            pagoNuevo.setTipoPago("1");
            try {
                pagoMensualidadController.create(pagoNuevo);
            } catch (Exception ex) {
                pagoInscripcionExitoso = false;
                Logger.getLogger(PagoInscripcionAlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            pagoInscripcionExitoso = false;
        }
        return pagoInscripcionExitoso;
    }

}
