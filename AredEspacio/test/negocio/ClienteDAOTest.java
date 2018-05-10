package negocio;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Israel Reyes Ozuna
 */
public class ClienteDAOTest {
        
    /**
     * Test of registrarCliente method, of class ClienteDAO.
     */
    
    @Test
    public void testRegistrarClienteExitoso() {
        Cliente cliente = new Cliente();

        cliente.setNombre("Raymundo");
        cliente.setApellidos("Perez");        
        cliente.setCorreo("blueteam@gmail.com");
        cliente.setTelefono("2281746372");        
        cliente.setRutaFoto(null);        
        ClienteDAO clienteDAO = new ClienteDAO();

        boolean expResult = true;
        boolean result = clienteDAO.registrarCliente(cliente);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testRegistrarClienteFallido() {
        Cliente cliente = null;      
        ClienteDAO clienteDAO = new ClienteDAO();

        boolean expResult = false;
        boolean result = clienteDAO.registrarCliente(cliente);
        assertEquals(expResult, result);
    }

    /**
     * Test of editarCliente method, of class ClienteDAO.
     */
    @Test
    public void testEditarClienteExitoso() {
        persistencia.Cliente cliente = new persistencia.Cliente();
       
        cliente.setIdCliente(2);
        cliente.setNombre("Pedro");
        cliente.setApellidos("Lopez");
        cliente.setCorreo("irvin@gmail.com");
        cliente.setTelefono("2281767676");               
        cliente.setRutaFoto("9mle9ePd_400x400.jpg");
        ClienteDAO clienteDAO = new ClienteDAO();
        boolean expResult = true;        
        boolean result = clienteDAO.editarCliente(cliente);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testEditarClienteFallido() {
        persistencia.Cliente cliente = null;        
        ClienteDAO clienteDAO = new ClienteDAO();
        boolean expResult = false;        
        boolean result = clienteDAO.editarCliente(cliente);
        assertEquals(expResult, result);
    }

    /**
     * Test of buscarCliente method, of class ClienteDAO.
     */
    @Test
    public void testBuscarClienteExitoso() {
        persistencia.Cliente cliente = new persistencia.Cliente();
        cliente.setIdCliente(3);
        cliente.setNombre("Sheccid");
        cliente.setApellidos("Roque");
        cliente.setCorreo("sheccid@gmail.com");
        cliente.setTelefono("5949832489");    
        cliente.setRutaFoto("9mle9ePd_400x400.jpg");
        ClienteDAO clienteDAO = new ClienteDAO();
        List<persistencia.Cliente> expResult = new ArrayList<>();
        expResult.add(cliente);
        List<persistencia.Cliente> result = clienteDAO.buscarCliente(cliente.getNombre());
        assertEquals(expResult, result);
    }
    
    @Test
    public void testBuscarClienteFallido(){
        String nombre = "Julio";
        ClienteDAO clienteDAO = new ClienteDAO();
        List<persistencia.Cliente> expResult = new ArrayList<>();
        List<persistencia.Cliente> result = clienteDAO.buscarCliente(nombre);
        assertEquals(expResult, result);
    }
    
}
