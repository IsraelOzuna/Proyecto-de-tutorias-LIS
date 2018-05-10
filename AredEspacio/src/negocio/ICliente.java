package negocio;
import java.util.List;
/**
 *
 * @author Israel Reyes Ozuna
 */
public interface ICliente {
    public boolean registrarCliente(Cliente cliente);
    public boolean editarCliente(persistencia.Cliente cliente);
    public List<persistencia.Cliente> buscarCliente(String nombre);
}
