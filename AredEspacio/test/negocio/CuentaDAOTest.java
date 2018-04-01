/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Irdevelo
 */
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
        System.out.println("crearCuenta");
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
        System.out.println("crearCuenta");
        Cuenta cuenta = new Cuenta();
        CuentaDAO instance = new CuentaDAO();
        boolean expResult = false;
        boolean result = instance.crearCuenta(cuenta);
        assertEquals(expResult, result);
    }

    @Test
    public void testVerificarNombreUsuarioRepetido() {
        System.out.println("verificarNombreUsuarioRepetido");
        String nombreUsuario = "elrevo";
        CuentaDAO instance = new CuentaDAO();
        boolean expResult = true;
        boolean result = instance.verificarNombreUsuarioRepetido(nombreUsuario);
        assertEquals(expResult, result);
    }

    @Test
    public void testVerificarNombreUsuarioRepetidoFallido() {
        System.out.println("verificarNombreUsuarioRepetido");
        String nombreUsuario = "Luis";
        CuentaDAO instance = new CuentaDAO();
        boolean expResult = false;
        boolean result = instance.verificarNombreUsuarioRepetido(nombreUsuario);
        assertEquals(expResult, result);
    }

}
