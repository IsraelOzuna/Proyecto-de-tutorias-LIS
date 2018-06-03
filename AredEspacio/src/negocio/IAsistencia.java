package negocio;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Renato 
 */
public interface IAsistencia {
    public List<persistencia.Asistencia> obtenerAsistencia(Date fecha, int idGrupo);
    public boolean RegistrarAsistencia(int idAlumno, int idGrupo, Date fecha, String asistio);
}
