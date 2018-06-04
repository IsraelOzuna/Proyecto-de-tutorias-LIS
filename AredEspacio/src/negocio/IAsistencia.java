package negocio;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Renato Vargas Gómez
 */
public interface IAsistencia {
    /**
     * Este método adquiere la lista de asistencia en base a una fehca y un id de grupo
     *
     * @param fecha fecha de la asistencia
     * @param idGrupo grupo del que se desea obtener la asistencia
     * @return List lista de asistencia adquirida
     * @since 1.0 / 5 de junio de 2018
     */
    public List<persistencia.Asistencia> obtenerAsistencia(Date fecha, int idGrupo);
    
    /**
     * Este método permite que se registre la asistencia de un alumno
     *
     * @param idAlumno alumno cuya asistencia se registrará
     * @param idGrupo grupo en el cual se registrará la asistencia de dicho alumno
     * @param fecha fecha de la asistencia
     * @param asistio cadena que permite conocer si el alumno asistió o no
     * @return boolean que ayuda a saber si el registro se realizó con exito
     * @since 1.0 / 5 de junio de 2018
     */
    public boolean RegistrarAsistencia(int idAlumno, int idGrupo, Date fecha, String asistio);
}
