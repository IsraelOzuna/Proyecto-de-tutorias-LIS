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
public class EgresoDAOTest {
    
    public EgresoDAOTest() {
    }
        
    /**
     * Test of registrarEgresoFb method, of class EgresoDAO.
     */
    @Test
    public void testRegistrarEgresoFbExitoso() {        
        Egreso egresoFb = new Egreso();
        String fechaInicio = "2018/12/13";
        String fechaFin = "2018/12/31";
        DateFormat formato = new SimpleDateFormat("YYY/MM/DD");
        Date fechaInicioPub = null;
        Date fechaFinPub = null;
        
        try {
            fechaInicioPub = formato.parse(fechaInicio);
            fechaFinPub = formato.parse(fechaFin);
        } catch (ParseException ex) {
            Logger.getLogger(EgresoDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String usuarioMaestro = "elrevo";
        egresoFb.setCosto(500);
        egresoFb.setCreador(usuarioMaestro);
        egresoFb.setDescripcion("");
        egresoFb.setFechaFinPublicacion(fechaFinPub);
        egresoFb.setFechaInicioPublicacion(fechaInicioPub);
        egresoFb.setFechaRegistro(new Date());
        egresoFb.setUrl("https://www.youtube.com/watch?v=meq_UJn_H9I&start_radio=1&list=RDmeq_UJn_H9I");
        egresoFb.setNombreGasto("Anuncio de Facebook");
        EgresoDAO instance = new EgresoDAO();
        boolean expResult = true;
        boolean result = instance.registrarEgresoFb(egresoFb, usuarioMaestro);
        assertEquals(expResult, result);        
    }
    
    @Test
    public void registrarEgresoFbFallido(){
        Egreso egresoFb = null;
        String usuarioMaestro = "";
        EgresoDAO egresoDAO = new EgresoDAO();

        boolean expResult = false;
        boolean result = egresoDAO.registrarEgresoFb(egresoFb, usuarioMaestro);
        assertEquals(expResult, result);
    }
    
    @Test
    public void registrarEgresoVariadoExitoso(){
        Egreso egreso = new Egreso();
        egreso.setCosto(1500);
        egreso.setDescripcion("Se hizo la compra de 5 tubos para la clase de ballet");
        egreso.setNombreGasto("Tubos");
        egreso.setFechaRegistro(new Date());
        
        EgresoDAO instance = new EgresoDAO();
        boolean expResult = true;
        boolean result = instance.registrarEgresoVariado(egreso);
        assertEquals(expResult, result);     
    }
    
    @Test
    public void registrarEgresoVariadoFallido(){
        Egreso egreso = null;        
        EgresoDAO egresoDAO = new EgresoDAO();

        boolean expResult = false;
        boolean result = egresoDAO.registrarEgresoVariado(egreso);
        assertEquals(expResult, result);
    }
}
