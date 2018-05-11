package negocio;
import java.util.List;
import persistencia.Grupo;
public interface IGrupo {
    public List<persistencia.Grupo> adquirirGrupos (persistencia.Cuenta usuario);
    public boolean crearGrupo (Grupo nuevoGrupo);
    public List<persistencia.Cuenta> adquirirCuentas ();
    public Grupo adquirirGrupo (int idGrupo);
    public boolean eliminarGrupo(Grupo grupoEliminar);
    public boolean editarGrupo(Grupo grupoEditar);
    public List<persistencia.Alumno> obtenerAlumnos(int idGrupo);
}