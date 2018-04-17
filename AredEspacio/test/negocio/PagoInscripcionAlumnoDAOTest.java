/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author iro19
 */
public class PagoInscripcionAlumnoDAOTest {

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

    /**
     * Test of registrarInscripcion method, of class PagoMensualidadAlumnoDAO.
     */
    @Test
    public void testRegistrarInscripcionExitoso() {
        String fecha = "13/12/2018";
        DateFormat formato = new SimpleDateFormat("YYY/MM/DD");
        Date fechaNacimiento = null;
        try {
            fechaNacimiento = formato.parse(fecha);
        } catch (ParseException ex) {
            Logger.getLogger(PagoInscripcionAlumnoDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        PagoMensualidadAlumno pagoAlumno = new PagoMensualidadAlumno();
        persistencia.Alumno alumno = new persistencia.Alumno();
        alumno.setIdAlumno(17);
        persistencia.Grupo grupo = new persistencia.Grupo();
        grupo.setNombreGrupo("Danza arabe");
        pagoAlumno.setCantidad(500.00);
        pagoAlumno.setIdAlumno(alumno.getIdAlumno());
        pagoAlumno.setFechaPagoInscripcion(fechaNacimiento);
        pagoAlumno.setNombreGrupo(grupo.getNombreGrupo());
        pagoAlumno.setTipoPago('1');

        PagoMensualidadAlumnoDAO pagoInscripcion = new PagoMensualidadAlumnoDAO();

        boolean expResult = true;
        boolean result = pagoInscripcion.registrarMensualidad(pagoAlumno, alumno.getIdAlumno(), grupo.getNombreGrupo());
        assertEquals(expResult, result);
    }

    @Test
    public void testRegistrarInscripcionFallido() {
        PagoMensualidadAlumno pagoInscripcion = null;
        int idAlumno = 0;
        String nombreGrupo = null;
        PagoMensualidadAlumnoDAO pagoInscripcionAlumnoDAO = new PagoMensualidadAlumnoDAO();

        boolean expResult = false;
        boolean result = pagoInscripcionAlumnoDAO.registrarMensualidad(pagoInscripcion, idAlumno, nombreGrupo);
        assertEquals(expResult, result);
    }

}
