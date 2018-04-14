package negocio;

import java.util.List;

public interface IAlumno {
    public boolean registrarAlumno(Alumno alumno);
    public boolean editarAlumno(persistencia.Alumno alumno);
    public List<persistencia.Alumno> buscarAlumno(String nombre);
}
