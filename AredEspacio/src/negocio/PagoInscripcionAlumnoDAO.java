package negocio;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import persistencia.AlumnoJpaController;
import persistencia.Grupo;
import persistencia.GrupoJpaController;
import persistencia.PagoalumnoJpaController;

public class PagoInscripcionAlumnoDAO implements IPagoInscripcionAlumno{
    String unidadPersistencia="AredEspacioPU";
    
    public PagoInscripcionAlumnoDAO(){
        
    }
    
    public PagoInscripcionAlumnoDAO(String unidadPersistencia){
        this.unidadPersistencia=unidadPersistencia;
    }
    @Override
    public boolean registrarInscripcion(double monto, int idAlumno, String nombreGrupo, Date fecha) {///Guardar grupo
    boolean pagoInscripcionExitoso = true;
        persistencia.Alumno alumno = null;
        Grupo grupo = null;            
        int idGrupo;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(unidadPersistencia, null);
        PagoalumnoJpaController pagoInscripcionController = new PagoalumnoJpaController(entityManagerFactory);
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
        pagoNuevo.setFechaPago(fecha);
        pagoNuevo.setCantidad(monto);
        pagoNuevo.setIdAlumno(alumno);
        pagoNuevo.setIdGrupo(grupo);
        pagoNuevo.setTipoPago("0");//////el cero es de inscripcion
        try {
            pagoInscripcionController.create(pagoNuevo);
        } catch (Exception ex) {
            pagoInscripcionExitoso = false;
            Logger.getLogger(PagoMensualidadAlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return pagoInscripcionExitoso;
    }
    
    
}
