package negocio;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import persistencia.AlumnoJpaController;
import persistencia.Grupo;
import persistencia.GrupoJpaController;
import persistencia.Pagoalumno;

public class AlumnoDAO implements IAlumno {

    @Override
    public boolean registrarAlumno(Alumno alumno) {
        boolean alumnoRegistradoExitosamente = false;
        if (alumno != null) {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
            AlumnoJpaController alumnoJpaController = new AlumnoJpaController(entityManagerFactory);

            persistencia.Alumno nuevoAlumno = new persistencia.Alumno();

            nuevoAlumno.setNombre(alumno.getNombre());
            nuevoAlumno.setApellidos(alumno.getApellidos());
            nuevoAlumno.setCorreoElectronico(alumno.getCorreoElectronico());
            nuevoAlumno.setFechaNacimiento(alumno.getFechaNacimiento());
            nuevoAlumno.setTelefono(alumno.getTelefono());
            nuevoAlumno.setRutaFoto(alumno.getRutaFoto());

            try {
                alumnoJpaController.create(nuevoAlumno);
                alumnoRegistradoExitosamente = true;
            } catch (Exception ex) {
                alumnoRegistradoExitosamente = false;
                Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return alumnoRegistradoExitosamente;
    }

    @Override
    public boolean editarAlumno(persistencia.Alumno alumno) {
        boolean datosModificacdosExitosamente = false;
        if (alumno != null) {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
            AlumnoJpaController alumnoJpaController = new AlumnoJpaController(entityManagerFactory);

            persistencia.Alumno alumnoEditado = alumno;

            alumnoEditado.setNombre(alumno.getNombre());
            alumnoEditado.setApellidos(alumno.getApellidos());
            alumnoEditado.setCorreoElectronico(alumno.getCorreoElectronico());
            alumnoEditado.setFechaNacimiento(alumno.getFechaNacimiento());
            alumnoEditado.setTelefono(alumno.getTelefono());
            alumnoEditado.setRutaFoto(alumno.getRutaFoto());

            try {
                alumnoJpaController.edit(alumnoEditado);
                datosModificacdosExitosamente = true;
            } catch (Exception ex) {
                Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return datosModificacdosExitosamente;
    }

    @Override
    public List<persistencia.Alumno> buscarAlumno(String nombre) {
        List<persistencia.Alumno> alumnosEncontrados = null;

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        AlumnoJpaController alumnoJpaController = new AlumnoJpaController(entityManagerFactory);

        persistencia.Alumno alumno = new persistencia.Alumno();

        alumno.setNombre(nombre);

        try {
            alumnosEncontrados = alumnoJpaController.obtenerAlumnos(nombre);

        } catch (Exception ex) {
            Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return alumnosEncontrados;
    }

    @Override
    public List<String> encontrarGruposAlumno(int idAlumno) {
        List<persistencia.Grupo> gruposEncontrados;
        List<String> gruposAlumno = new ArrayList();

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        AlumnoJpaController alumnoJpaController = new AlumnoJpaController(entityManagerFactory);

        gruposEncontrados = alumnoJpaController.obtenerGruposAlumno(idAlumno);

        for (int i = 0; i < gruposEncontrados.size(); i++) {
            gruposAlumno.add(gruposEncontrados.get(i).getNombreGrupo());
        }

        return gruposAlumno;
    }

    @Override
    public persistencia.Alumno adquirirAlumno(int idAlumno) {
        persistencia.Alumno alumnoEncontrado = new persistencia.Alumno();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);//Cambiar a "AredEspacioPU"
        AlumnoJpaController alumnoJpaController = new AlumnoJpaController(entityManagerFactory);
        try {
            alumnoEncontrado = alumnoJpaController.findAlumno(idAlumno);
        } catch (Exception ex) {
            Logger.getLogger(GrupoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return alumnoEncontrado;
    }

}
