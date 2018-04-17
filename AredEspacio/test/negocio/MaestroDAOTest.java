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

    /*   @Test
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
        maestro.setNombre("Jose");
        maestro.setApellidos("Arcos");
        maestro.setCorreoElectronico("joseRamon@hotmail.com");
        maestro.setTelefono("2291827482");
        maestro.setFechaCorte(null);
        maestro.setRutaFoto(null);
        maestro.setEstaActivo(0);
        maestro.setMensualidad(600.00);
        maestro.setUsuario(null);
        MaestroDAO instance = new MaestroDAO();
        boolean expResult = false;
        boolean result = instance.registrarMaestro(maestro);
        assertEquals(expResult, result);
    }

    @Test
    public void testBuscarMaestro() {
        System.out.println("buscarMaestroExitoso");
        String nombre = "Roberto";
        persistencia.Maestro maestroNuevo = new persistencia.Maestro();
        maestroNuevo.setNombre(nombre);
        maestroNuevo.setApellidos("Maldonado");
        maestroNuevo.setCorreoElectronico("robert@hotrmail.com");
        maestroNuevo.setEstaActivo(0);
        maestroNuevo.setFechaCorte(null);
        maestroNuevo.setRutaFoto("senor-lapiz.jpg");
        maestroNuevo.setMensualidad(400.00);
        maestroNuevo.setTelefono("2281729482");
        maestroNuevo.setUsuario("robert");
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

     */
    @Test
    public void testEditarMaestro() {
        System.out.println("editarMaestroExitoso");
        persistencia.Maestro maestroEditado = new persistencia.Maestro();

        maestroEditado.setNombre("Francisco");
        maestroEditado.setApellidos("Maldonado");
        maestroEditado.setCorreoElectronico("francisco@hotmail.com");
        maestroEditado.setEstaActivo(0);
        maestroEditado.setFechaCorte(null);
        maestroEditado.setRutaFoto("senor-lapiz.jpg");
        maestroEditado.setMensualidad(400.00);
        maestroEditado.setTelefono("2281735478");
        maestroEditado.setUsuario("robert");
        MaestroDAO instance = new MaestroDAO();
        boolean expResult = true;
        boolean result = instance.editarMaestro(maestroEditado);
        assertEquals(expResult, result);

    }

    @Test
    public void testEditarMaestroFallido() {
        System.out.println("editarMaestroFallido");
        persistencia.Maestro maestroEditado = new persistencia.Maestro();

        maestroEditado.setNombre(null);
        maestroEditado.setApellidos(null);
        maestroEditado.setCorreoElectronico("alan@hotmail.com");
        maestroEditado.setEstaActivo(0);
        maestroEditado.setFechaCorte(null);
        maestroEditado.setRutaFoto("senor-lapiz.jpg");
        maestroEditado.setMensualidad(400.00);
        maestroEditado.setTelefono("2281729482");
        maestroEditado.setUsuario(null);
        MaestroDAO instance = new MaestroDAO();
        boolean expResult = false;
        boolean result = instance.editarMaestro(maestroEditado);
        assertEquals(expResult, result);
    }

}
