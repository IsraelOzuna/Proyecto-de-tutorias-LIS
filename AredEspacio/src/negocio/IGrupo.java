package negocio;
import java.util.List;
import persistencia.Grupo;
public interface IGrupo {
    public List<persistencia.Grupo> adquirirGrupos (persistencia.Cuenta usuario);
    public boolean crearGrupo (Grupo nuevoGrupo);
    public List<persistencia.Cuenta> adquirirCuentas ();
    public Grupo adquirirGrupo (String nombreGrupo);
}
