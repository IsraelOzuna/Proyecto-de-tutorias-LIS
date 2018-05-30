package negocio;

import java.util.List;

/**
 *
 * @author iro19
 */
public interface IPagoMensualidadAlumno {

    public boolean registrarMensualidad(PagoMensualidadAlumno inscripcionAlumno, int idAlumno, String nombreGrupo);

    public List<persistencia.Pagoalumno> obtenerPagosDeAlumno(int idAlumno);
}
