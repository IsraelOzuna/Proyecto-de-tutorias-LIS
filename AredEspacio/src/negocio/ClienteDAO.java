package negocio;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import persistencia.ClienteJpaController;

/**
 *
 * @author Israel Reyes Ozuna
 */
public class ClienteDAO implements ICliente{   
    @Override
    public boolean registrarCliente(Cliente cliente) {
        boolean clienteRegistradoExitosamente = false;
        if (cliente != null) {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
            ClienteJpaController alumnoJpaController = new ClienteJpaController(entityManagerFactory);

            persistencia.Cliente nuevoCliente = new persistencia.Cliente();

            nuevoCliente.setNombre(cliente.getNombre());
            nuevoCliente.setApellidos(cliente.getApellidos());
            nuevoCliente.setCorreo(cliente.getCorreo());            
            nuevoCliente.setTelefono(cliente.getTelefono());
            nuevoCliente.setRutaFoto(cliente.getRutaFoto());

            try {
                alumnoJpaController.create(nuevoCliente);
                clienteRegistradoExitosamente = true;
            } catch (Exception ex) {
                clienteRegistradoExitosamente = false;
                Logger.getLogger(AlumnoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return clienteRegistradoExitosamente;
    }           
}
