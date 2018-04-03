/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import org.junit.Test;

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
        System.out.println("registrarMaestroExitoso");
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
        System.out.println("registrarMaestroFallido");
        Maestro maestro = new Maestro();
        MaestroDAO instance = new MaestroDAO();
        boolean expResult = true;
        boolean result = instance.registrarMaestro(maestro);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testBuscarMaestro() {
        System.out.println("buscarMaestroExitoso");
        String nombre = "Juan Carlos";
        persistencia.Maestro maestroNuevo = new persistencia.Maestro();
        maestroNuevo.setNombre(nombre);
        maestroNuevo.setApellidos("Perez Arriaga");
        maestroNuevo.setCorreoElectronico("elrevo@hotmail.com");
        maestroNuevo.setEstaActivo(0);
        maestroNuevo.setFechaCorte(null);
        maestroNuevo.setRutaFoto("9mle9ePd_400x400.jpg ");
        maestroNuevo.setMensualidad(500.00);
        maestroNuevo.setTelefono("228172902");
        maestroNuevo.setUsuario("elrevo");
        MaestroDAO instance = new MaestroDAO();
        List<persistencia.Maestro> expResult = new ArrayList<>();
        expResult.add(maestroNuevo);
        List<persistencia.Maestro> result = instance.buscarMaestro(nombre);
        assertEquals(expResult, result);
        
    }
    
    @Test
    public void testBuscarMaestroFallido() {
        System.out.println("buscarMaestroFallido");
        String nombre = "carlos";
        MaestroDAO instance = new MaestroDAO();
        List<persistencia.Maestro> expResult = new ArrayList<persistencia.Maestro>();
        List<persistencia.Maestro> result = instance.buscarMaestro(nombre);
        assertEquals(expResult, result);
        
    }
    
}
