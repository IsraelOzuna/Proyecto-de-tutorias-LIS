package negocio;

/**
 *
 * @author iro19
 */
public interface IPagoInscripcionAlumno {
    public boolean registrarInscripcion(PagoInscripcionAlumno inscripcionAlumno, int idAlumno, String nombreGrupo);
}
