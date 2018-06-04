package negocio;

import java.util.List;

/**
 *
 * @author Israel Reyes Ozuna
 */
public interface IPagoMensualidadAlumno {

    /**
     * Este método permite que se registren pagos de mensualidad de los alumnos
     * en algún gurpo
     *
     * @param inscripcionAlumno que contiene todo lo necesario para el registro
     * @param idAlumno identificador del alumno que se registró
     * @param nombreGrupo para encontrar el grupo al que se le realizó el pago
     * @return boolean que ayuda a saber si el registro se realizó con exito
     * @since 1.0 / 5 de junio de 2018
     */
    public boolean registrarMensualidad(PagoMensualidadAlumno inscripcionAlumno, int idAlumno, String nombreGrupo);

    /**
     * Este método permite encontrar todos los pagos que el alumno ha realizado
     *
     * @param idAlumno para encontrar unicamente los pagos de un alumno
     * @return List con los pagos del alumno encontrados
     * @since 1.0 / 5 de junio de 2018
     */
    public List<persistencia.Pagoalumno> obtenerPagosDeAlumno(int idAlumno);
}
