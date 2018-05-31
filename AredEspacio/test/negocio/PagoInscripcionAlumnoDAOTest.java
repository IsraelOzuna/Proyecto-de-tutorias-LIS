/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Renato
 */
public class PagoInscripcionAlumnoDAOTest {
    private static String unidadPersistenciaPruebas="AredEspacioPU";
    
    public PagoInscripcionAlumnoDAOTest() {
        
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
    public void testRegistrarInscripcionExitoso() {
        PagoInscripcionAlumnoDAO instance = new PagoInscripcionAlumnoDAO(unidadPersistenciaPruebas);
        boolean expResult = true;
        boolean result = instance.registrarInscripcion(100.0, 48, "grupo2", new Date());
        assertEquals(expResult, result); 
    }   
    
    @Test
    public void testRegistrarInscripcionFallido() {
        PagoInscripcionAlumnoDAO instance = new PagoInscripcionAlumnoDAO(unidadPersistenciaPruebas);
        boolean expResult = false;
        boolean result = instance.registrarInscripcion(100.0, 1, "grupoNulo", new Date());
        assertEquals(expResult, result); 
    }   
    
    
}
