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
import persistencia.Pagoalumnodireccion;

/**
 *
 * @author Renato
 */
public class PagoAlumnoDireccionDAOTest {
    private static String unidadPersistenciaPruebas="AredEspacioPU";
    private static persistencia.Cuenta usuario = new persistencia.Cuenta();
    
    public PagoAlumnoDireccionDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        usuario.setUsuario("cuentaPruebaMaestro");
        //usuario.setContrasena("contrasenaPrueba");
        //usuario.setTipoCuenta("Maestro");
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
     * Test of registrarPagoDireccion method, of class PagoAlumnoDireccionDAO.
     */
    @Test
    public void testRegistrarPagoDireccionFallido() {
        System.out.println("registrarPagoDireccion");
        Pagoalumnodireccion pago = null;
        PagoAlumnoDireccionDAO instance = new PagoAlumnoDireccionDAO(unidadPersistenciaPruebas);
        boolean expResult = false;
        boolean result = instance.registrarPagoDireccion(pago);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testRegistrarPagoDireccionExitoso() {
        System.out.println("registrarPagoDireccion");
        persistencia.Alumno alumno=new persistencia.Alumno();
        alumno.setIdAlumno(48);
        persistencia.Grupo grupo = new persistencia.Grupo();
        grupo.setIdGrupo(5);
        Pagoalumnodireccion pago = new Pagoalumnodireccion();
        pago.setCantidad(100.0);
        pago.setFechaPago(new Date());
        pago.setIdAlumno(alumno);
        pago.setIdGrupo(grupo);
        pago.setUsuario(usuario);
        
        PagoAlumnoDireccionDAO instance = new PagoAlumnoDireccionDAO(unidadPersistenciaPruebas);
        boolean expResult = true;
        boolean result = instance.registrarPagoDireccion(pago);
        assertEquals(expResult, result);
    }
}
