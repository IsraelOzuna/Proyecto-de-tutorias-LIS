/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import negocio.Renta;

/**
 *
 * @author Irdevelo
 */
public class RentaDAOTest {

    public RentaDAOTest() {
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
    public void testRegistrarRenta() {
        System.out.println("registrarRenta");
        Renta renta = new Renta();
        String fecha = "13/12/2018";
        DateFormat formato = new SimpleDateFormat("YYY/MM/DD");
        Date fechaAMandar = null;
        try {
            fechaAMandar = formato.parse(fecha);
        } catch (ParseException ex) {
            Logger.getLogger(PagoMensualidadAlumnoDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        renta.setFecha(fechaAMandar);
        renta.setNombreCliente("Jesus amores");
        renta.setHoraInicio(Time.valueOf("15:00:00"));
        renta.setHoraFin(Time.valueOf("16:00:00"));
        renta.setCantidad(200.0);

        RentaDAO instance = new RentaDAO();
        boolean expResult = true;
        boolean result = instance.registrarRenta(renta);
        assertEquals(expResult, result);
    }

    @Test
    public void testRegistrarRentaFallido() {
        Renta renta = new Renta();

        renta.setNombreCliente(null);

        RentaDAO instance = new RentaDAO();
        boolean expResult = false;
        boolean result = instance.registrarRenta(renta);
        assertEquals(expResult, result);
    }

    @Test
    public void testEliminarRenta() {
        System.out.println("eliminarRenta");
        int id = 31;
        RentaDAO instance = new RentaDAO();
        boolean expResult = true;
        boolean result = instance.eliminarRenta(id);
        assertEquals(expResult, result);
       
    }

    @Test
    public void testEliminarRentaFallido() {
        System.out.println("eliminarRenta");
        int id = 0;
        RentaDAO instance = new RentaDAO();
        boolean expResult = false;
        boolean result = instance.eliminarRenta(id);
        assertEquals(expResult, result);
    }

}
