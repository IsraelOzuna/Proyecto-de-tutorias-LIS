package negocio;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CuentaDAOTest {

    public CuentaDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCrearCuenta() {
        System.out.println("crearCuentaExitoso");
        Cuenta cuenta = new Cuenta();
        cuenta.setUsuario("Julian");
        cuenta.setContraseña("AS87SDJH7SA9KJ67");
        cuenta.setTipoCuenta("Maestro");
        CuentaDAO instance = new CuentaDAO();
        boolean expResult = true;
        boolean result = instance.crearCuenta(cuenta);
        assertEquals(expResult, result);
    }

    @Test
    public void testCrearCuentaFallido() {
        System.out.println("crearCuentaFallido");
        Cuenta cuenta = new Cuenta();
        CuentaDAO instance = new CuentaDAO();
        boolean expResult = false;
        boolean result = instance.crearCuenta(cuenta);
        assertEquals(expResult, result);
    }

    @Test
    public void testVerificarNombreUsuarioRepetido() {
        System.out.println("verificarNombreUsuarioRepetidoExitoso");
        String nombreUsuario = "elrevo";
        CuentaDAO instance = new CuentaDAO();
        boolean expResult = true;
        boolean result = instance.verificarNombreUsuarioRepetido(nombreUsuario);
        assertEquals(expResult, result);
    }

    @Test
    public void testVerificarNombreUsuarioRepetidoFallido() {
        System.out.println("verificarNombreUsuarioRepetidoFallido");
        String nombreUsuario = "Luis";
        CuentaDAO instance = new CuentaDAO();
        boolean expResult = false;
        boolean result = instance.verificarNombreUsuarioRepetido(nombreUsuario);
        assertEquals(expResult, result);
    }

    /**
     * Test of iniciarSesion method, of class CuentaDAO.
     */
    @Test
    public void testIniciarSesionDirector() {
        String nombreUsuario = "Xavier";
        String contrasena = null;
        try {
            contrasena = Utileria.cifrarContrasena("s13zw98");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CuentaDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        CuentaDAO instance = new CuentaDAO();
        String expResult = "Director";
        String result = instance.iniciarSesion(nombreUsuario, contrasena);
        assertEquals(expResult, result);        
    }
    
    @Test
    public void testIniciarSesionMaestro() {
        String nombreUsuario = "elrevo";
        String contrasena = null;
        try {
            contrasena = Utileria.cifrarContrasena("elrevo");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CuentaDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        CuentaDAO instance = new CuentaDAO();
        String expResult = "Maestro";
        String result = instance.iniciarSesion(nombreUsuario, contrasena);
        assertEquals(expResult, result);        
    }
    
    @Test
    public void testIniciarSesionFallido() {
        String nombreUsuario = "elrevo";
        String contrasena = null;
        try {
            contrasena = Utileria.cifrarContrasena("elrevo1");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CuentaDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        CuentaDAO instance = new CuentaDAO();
        String expResult = "";
        String result = instance.iniciarSesion(nombreUsuario, contrasena);
        assertEquals(expResult, result);        
    }
}
