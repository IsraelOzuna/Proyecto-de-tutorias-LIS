package negocio;

import java.util.List;

/**
 *
 * @author Israel Reyes Ozuna
 */

public interface IAlumno {
    public boolean registrarAlumno(Alumno alumno);
    public boolean editarAlumno(persistencia.Alumno alumno);
    public List<persistencia.Alumno> buscarAlumno(String nombre);
    public List<String> encontrarGruposAlumno(int idAlumno);
    public persistencia.Alumno adquirirAlumno(int idAlumno);
    public List<persistencia.Alumno> obtenerAlumnos();

}
