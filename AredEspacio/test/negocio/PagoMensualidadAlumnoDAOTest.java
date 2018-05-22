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
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author iro19
 */
public class PagoMensualidadAlumnoDAOTest {

    public PagoMensualidadAlumnoDAOTest() {
    }

    /**
     * Test of registrarInscripcion method, of class PagoMensualidadAlumnoDAO.
     */
    @Test
    public void testRegistrarInscripcionExitoso() {
        String fecha = "2018/12/13";
        DateFormat formato = new SimpleDateFormat("YYY/MM/DD");
        Date fechaNacimiento = null;
        try {
            fechaNacimiento = formato.parse(fecha);
        } catch (ParseException ex) {
            Logger.getLogger(PagoMensualidadAlumnoDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        PagoMensualidadAlumno pagoAlumno = new PagoMensualidadAlumno();
        persistencia.Alumno alumno = new persistencia.Alumno();
        String nombreGrupo = "Salsa";
        alumno.setIdAlumno(18);        
        persistencia.Grupo grupo = new persistencia.Grupo();        
        grupo.setNombreGrupo(nombreGrupo);
        pagoAlumno.setCantidad(500.00);
        pagoAlumno.setIdAlumno(alumno.getIdAlumno());
        pagoAlumno.setFechaPagoInscripcion(fechaNacimiento);
        pagoAlumno.setIdGrupo(2);
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
