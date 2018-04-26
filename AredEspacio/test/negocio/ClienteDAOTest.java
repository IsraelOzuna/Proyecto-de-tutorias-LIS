package negocio;

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
//    @Test
//    public void testEditarCliente() {
//        System.out.println("editarCliente");
//        Cliente cliente = null;
//        ClienteDAO instance = new ClienteDAO();
//        boolean expResult = false;
//        boolean result = instance.editarCliente(cliente);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of buscarCliente method, of class ClienteDAO.
//     */
//    @Test
//    public void testBuscarCliente() {
//        System.out.println("buscarCliente");
//        String nombre = "";
//        ClienteDAO instance = new ClienteDAO();
//        List<Cliente> expResult = null;
//        List<Cliente> result = instance.buscarCliente(nombre);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
