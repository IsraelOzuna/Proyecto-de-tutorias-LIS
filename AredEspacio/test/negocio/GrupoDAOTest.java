package negocio;

import java.util.Date;
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

    @Test
    public void testCrearGrupoExitoso() {
        System.out.println("crearGrupo");
        Cuenta usuario = new Cuenta();
        usuario.setUsuario("Renato");
        Grupo nuevoGrupo = new Grupo();
        nuevoGrupo.setNombreGrupo("GrupoDePrueba");
        nuevoGrupo.setUsuario(usuario);
        nuevoGrupo.setMensualidad(3200.0);
        nuevoGrupo.setInscripcion(340.0);
        nuevoGrupo.setFechaPago(new Date());
        GrupoDAO instance = new GrupoDAO();
        boolean expResult = true;
        boolean result = instance.crearGrupo(nuevoGrupo);
        //System.out.println(expResult+" "+ result);
        assertEquals(expResult, result);
        instance.eliminarGrupo(nuevoGrupo);
    }
    
    @Test
    public void testAdquirirGrupoExitoso() {
        System.out.println("adquirirGrupoExitoso");
        String nombreGrupo = "GrupoDePrueba";
        
        Cuenta usuario = new Cuenta();
        usuario.setUsuario("Renato");
        
        Grupo nuevoGrupo = new Grupo();
        nuevoGrupo.setNombreGrupo("GrupoDePrueba");
        nuevoGrupo.setUsuario(usuario);
        nuevoGrupo.setMensualidad(3200.0);
        nuevoGrupo.setInscripcion(340.0);
        
        
        
        Grupo compararGrupo = new Grupo();
        compararGrupo.setNombreGrupo("GrupoDePrueba");
        compararGrupo.setUsuario(usuario);
        compararGrupo.setMensualidad(3200.0);
        compararGrupo.setInscripcion(340.0);
        
        GrupoDAO instance = new GrupoDAO();
        Grupo expResult = compararGrupo;
        instance.crearGrupo(nuevoGrupo);
        Grupo result = instance.adquirirGrupo(nombreGrupo);
        assertEquals(expResult, result);
        instance.eliminarGrupo(nuevoGrupo);
    }
    
    @Test
    public void testCrearGrupoFallido() {
        System.out.println("crearGrupoFallido");
        Grupo nuevoGrupo = new Grupo();
        GrupoDAO instance = new GrupoDAO();
        boolean expResult = false;
        boolean result = instance.crearGrupo(nuevoGrupo);
        //System.out.println(expResult+" "+ result);
        assertEquals(expResult, result);
    }
    
    @Test 
    public void testEliminarGrupoExitoso(){
        System.out.println("eliminarGrupoExitoso");
        String nombreGrupo="GrupoDePrueba";
        
        Cuenta usuario = new Cuenta();
        usuario.setUsuario("Renato");
        
        Grupo grupoEliminar = new Grupo();
        grupoEliminar.setUsuario(usuario);
        grupoEliminar.setMensualidad(3200.0);
        grupoEliminar.setInscripcion(340.0);
        grupoEliminar.setNombreGrupo(nombreGrupo);
        
        GrupoDAO instance = new GrupoDAO();
        boolean expResult = true;
        instance.crearGrupo(grupoEliminar);
        boolean result = instance.eliminarGrupo(grupoEliminar);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testAdquirirGrupoFallido() {
        System.out.println("adquirirGrupoFallido");
        String nombreGrupo = "";
        GrupoDAO instance = new GrupoDAO();
        Grupo expResult = null;
        Grupo result = instance.adquirirGrupo(nombreGrupo);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testEditarGrupoExitoso() {
        System.out.println("editarGrupoExitoso");
        Cuenta usuario = new Cuenta();
        usuario.setUsuario("Renato");
        Grupo nuevoGrupo = new Grupo();
        nuevoGrupo.setNombreGrupo("GrupoDePrueba");
        nuevoGrupo.setUsuario(usuario);
        nuevoGrupo.setMensualidad(3200.0);
        nuevoGrupo.setInscripcion(340.0);
        nuevoGrupo.setFechaPago(new Date());
        GrupoDAO instance = new GrupoDAO();
        boolean expResult = true;
        instance.crearGrupo(nuevoGrupo);
        nuevoGrupo.setMensualidad(20.0);
        boolean result = instance.editarGrupo(nuevoGrupo);
        assertEquals(expResult, result);
        instance.eliminarGrupo(nuevoGrupo);
    }  
    
    @Test
    public void testEditarGrupoFallido() {
        System.out.println("editarGrupoExitoso");
        Cuenta usuario = new Cuenta();
        usuario.setUsuario("Renato");
        Grupo nuevoGrupo = new Grupo();
        nuevoGrupo.setNombreGrupo("GrupoDePrueba");
        nuevoGrupo.setUsuario(usuario);
        nuevoGrupo.setMensualidad(3200.0);
        nuevoGrupo.setInscripcion(340.0);
        nuevoGrupo.setFechaPago(new Date());
        GrupoDAO instance = new GrupoDAO();
        boolean expResult = false;
        nuevoGrupo.setMensualidad(20.0);
        boolean result = instance.editarGrupo(nuevoGrupo);
        assertEquals(expResult, result);
    }
}
