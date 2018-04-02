/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import persistencia.Cuenta;
import persistencia.Grupo;

/**
 *
 * @author Equipo
 */
public class GrupoDAOTest {
    
    public GrupoDAOTest() {
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

    /**
     * Test of adquirirGrupos method, of class GrupoDAO.
     */
    /*@Test  No hay un Valor esperado, la idea es que la prueba funcione sin necesidad de alterar, consultar o eliminar elementos 
    de la base de datos, se espera que la prueba sea exitosa sin importar los cambios que se hagan a la base de datos, dichos criterios
    no aplican aqui
    public void testAdquirirGrupos() {
        System.out.println("adquirirGrupos");
        Cuenta usuario = null;
        GrupoDAO instance = new GrupoDAO();
        List<Grupo> expResult = null;
        List<Grupo> result = instance.adquirirGrupos(usuario);
        assertEquals(expResult, result);
    }*/

    /**
     * Test of crearGrupo method, of class GrupoDAO.
     */
    @Test
    public void testCrearGrupoExitoso() {
        System.out.println("crearGrupo");
        Cuenta usuario = new Cuenta();
        usuario.setUsuario("Renato");
        //usuario.setContrasena("renato123");
        Grupo nuevoGrupo = new Grupo();
        nuevoGrupo.setNombreGrupo("GrupoDePrueba");
        nuevoGrupo.setUsuario(usuario);
        nuevoGrupo.setMensualidad(3200.0);
        nuevoGrupo.setInscripcion(340.0);
        GrupoDAO instance = new GrupoDAO();
        boolean expResult = true;
        boolean result = instance.crearGrupo(nuevoGrupo);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testAdquirirGrupoExitoso() {
        System.out.println("adquirirGrupo");
        String nombreGrupo = "GrupoDePrueba";
        Grupo compararGrupo = new Grupo();
        compararGrupo.setNombreGrupo("GrupoDePrueba");
        GrupoDAO instance = new GrupoDAO();
        Grupo expResult = compararGrupo;
        Grupo result = instance.adquirirGrupo(nombreGrupo);
        assertEquals(expResult, result);
        instance.eliminarGrupo(compararGrupo);
    }
    

    
    @Test
    public void testCrearGrupoFallido() {
        System.out.println("crearGrupo");
        Grupo nuevoGrupo = new Grupo();
        GrupoDAO instance = new GrupoDAO();
        boolean expResult = false;
        boolean result = instance.crearGrupo(nuevoGrupo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    @Test
    public void testAdquirirGrupoFallido() {
        System.out.println("adquirirGrupo");
        String nombreGrupo = "";
        GrupoDAO instance = new GrupoDAO();
        Grupo expResult = null;
        Grupo result = instance.adquirirGrupo(nombreGrupo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
}
