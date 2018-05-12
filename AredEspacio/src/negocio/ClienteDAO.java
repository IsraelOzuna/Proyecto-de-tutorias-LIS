package negocio;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import persistencia.ClienteJpaController;

/**
 *
 * @author Israel Reyes Ozuna
 */
public class ClienteDAO implements ICliente {

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

    @Override
    public boolean editarCliente(persistencia.Cliente cliente) {
        boolean datosModificacdosExitosamente = false;
        if (cliente != null) {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
            ClienteJpaController clienteJpaController = new ClienteJpaController(entityManagerFactory);

            persistencia.Cliente clienteEditado = cliente;

            clienteEditado.setNombre(cliente.getNombre());
            clienteEditado.setApellidos(cliente.getApellidos());
            clienteEditado.setCorreo(cliente.getCorreo());
            clienteEditado.setTelefono(cliente.getTelefono());
            clienteEditado.setRutaFoto(cliente.getRutaFoto());

            try {
                clienteJpaController.edit(clienteEditado);
                datosModificacdosExitosamente = true;
            } catch (Exception ex) {
                Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
        return datosModificacdosExitosamente;
    }

    @Override
    public List<persistencia.Cliente> buscarCliente(String nombre) {
        List<persistencia.Cliente> clienteEncontrados = null;

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        ClienteJpaController clienteJpaController = new ClienteJpaController(entityManagerFactory);

        persistencia.Cliente cliente = new persistencia.Cliente();

        cliente.setNombre(nombre);

        try {
            clienteEncontrados = clienteJpaController.obtenerClientes(nombre);

        } catch (Exception ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clienteEncontrados;
    }

    @Override
    public List<persistencia.Cliente> buscarTodosLosClientes() {
        List<persistencia.Cliente> clienteEncontrados = null;

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        ClienteJpaController clienteJpaController = new ClienteJpaController(entityManagerFactory);

        try {
            clienteEncontrados = clienteJpaController.findClienteEntities();

        } catch (Exception ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clienteEncontrados;

    }
}
