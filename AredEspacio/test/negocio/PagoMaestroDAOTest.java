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
public class PagoMaestroDAOTest {

    public PagoMaestroDAOTest() {
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
    public void testRegistrarPagoMaestro() {
        System.out.println("registrarPagoMaestroExitoso");
        PagoMaestro pagoMaestro = new PagoMaestro();
        pagoMaestro.setCantidad(600.0);
        pagoMaestro.setFecha(null);
        pagoMaestro.setUsuario("robert");
        PagoMaestroDAO instance = new PagoMaestroDAO();
        boolean expResult = true;
        boolean result = instance.registrarPagoMaestro(pagoMaestro);
        assertEquals(expResult, result);

    }

    @Test
    public void testRegistrarPagoMaestroFallido() {
        System.out.println("registrarPagoMaestroFallido");
        PagoMaestro pagoMaestro = new PagoMaestro();
         pagoMaestro.setCantidad(null);
        pagoMaestro.setFecha(null);
        pagoMaestro.setUsuario(null);
        PagoMaestroDAO instance = new PagoMaestroDAO();
        boolean expResult = false;
        boolean result = instance.registrarPagoMaestro(pagoMaestro);
        assertEquals(expResult, result);

    }

}
