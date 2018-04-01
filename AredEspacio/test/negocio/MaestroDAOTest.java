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
public class MaestroDAOTest {

    public MaestroDAOTest() {
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
    public void testRegistrarMaestro() {
        System.out.println("registrarMaestro");
        Maestro maestro = new Maestro();

        maestro.setNombre("Jose");
        maestro.setApellidos("Arcos");
        maestro.setCorreoElectronico("joseRamon@hotmail.com");
        maestro.setTelefono("2291827482");
        maestro.setFechaCorte(null);
        maestro.setRutaFoto(null);
        maestro.setEstaActivo(0);
        maestro.setMensualidad(600.00);
        maestro.setUsuario("joseRamon");
        MaestroDAO instance = new MaestroDAO();

        boolean expResult = true;
        boolean result = instance.registrarMaestro(maestro);
        assertEquals(expResult, result);
    }

    @Test
    public void testRegistrarMaestroFallido() {
        System.out.println("registrarMaestro");
        Maestro maestro = null;
        MaestroDAO instance = new MaestroDAO();
        boolean expResult = false;
        boolean result = instance.registrarMaestro(maestro);
        assertEquals(expResult, result);
    }

//    @Test
//    public void testBuscarMaestro(){
//        System.out.println("buscarMaestro");
//        String nombre = "ramon";
//        MaestroDAO instance = new MaestroDAO();
//        List<Maestro> expResult = null;
//        List<Maestro> result = instance.buscarMaestro(nombre);
//        assertEquals(expResult, result);
//       
//    }
//    
//        public void testBuscarMaestroFallido(){
//        System.out.println("buscarMaestro");
//        String nombre = "carlos";
//        MaestroDAO instance = new MaestroDAO();
//        List<Maestro> expResult = null;
//        List<Maestro> result = instance.buscarMaestro(nombre);
//        assertEquals(expResult, result);
//        
//    }

}
