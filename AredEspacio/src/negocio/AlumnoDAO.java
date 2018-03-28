package negocio;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import persistencia.AlumnoJpaController;

public class AlumnoDAO implements IAlumno {

    @Override
    public boolean registrarAlumno(Alumno alumno) {
        boolean alumnoRegistradoExitosamente = true;
        
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        AlumnoJpaController alumnoJpaController = new AlumnoJpaController(entityManagerFactory);
        
        persistencia.Alumno nuevoAlumno = new persistencia.Alumno();
        
        nuevoAlumno.setNombre(alumno.getNombre());
        nuevoAlumno.setApellidos(alumno.getApellidos());
        nuevoAlumno.setCorreoElectronico(alumno.getCorreoElectronico());
        nuevoAlumno.setFechaNacimiento(alumno.getFechaNacimiento());
        nuevoAlumno.setTelefono(alumno.getTelefono());
        nuevoAlumno.setRutaFoto(alumno.getRutaFoto());
        
        try{
        alumnoJpaController.create(nuevoAlumno);        
        }catch(Exception ex){
            alumnoRegistradoExitosamente = false;            
            Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return alumnoRegistradoExitosamente;
    }
    
}
