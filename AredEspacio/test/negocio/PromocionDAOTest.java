/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import persistencia.Promocion;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author Renato
 */
public class PromocionDAOTest {
    private static String unidadPersistenciaPruebas="PruebasAredEspacioPU";
    
    public PromocionDAOTest() {
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
     * Test of registrarPromocion method, of class PromocionDAO.
     */
    @Test
    public void testRegistrarPromocionExitoso() throws NonexistentEntityException {
        System.out.println("registrarPromocion");
        Promocion nuevaPromocion = new Promocion();
        PromocionDAO instance = new PromocionDAO(unidadPersistenciaPruebas);
        nuevaPromocion.setNombrePromocion("PromocionPrueba");
        nuevaPromocion.setDescripcion("Nueva Descripcion");
        nuevaPromocion.setPorcentajeDescuento(25);
        boolean expResult = true;
        boolean result = instance.registrarPromocion(nuevaPromocion);
        assertEquals(expResult, result);
        //Eliminar Promocion
        Promocion promocionEliminar = instance.adquirirPromocionPorNombre("PromocionPrueba");
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(unidadPersistenciaPruebas, null);
        persistencia.PromocionJpaController controlador = new persistencia.PromocionJpaController(entityManagerFactory);
        controlador.destroy(promocionEliminar.getIdPromocion());
    }

    /**
     * Test of consultarPromociones method, of class PromocionDAO.
     */
    @Test
    public void testConsultarPromocionesExitoso() {
        System.out.println("consultarPromociones");
        PromocionDAO instance = new PromocionDAO(unidadPersistenciaPruebas);
        List<Promocion> listaPromociones= new ArrayList();
        Promocion promocion1=new Promocion();
        Promocion promocion2=new Promocion();
        Promocion promocion3=new Promocion();
        
        promocion1.setIdPromocion(1);
        promocion1.setNombrePromocion("promocion1");
        promocion1.setDescripcion("descripcion detallada");
        promocion1.setPorcentajeDescuento(25);
        
        promocion2.setIdPromocion(2);
        promocion2.setNombrePromocion("promocion2");
        promocion2.setDescripcion("descripcion detallada");
        promocion2.setPorcentajeDescuento(50);
        
        promocion3.setIdPromocion(3);
        promocion3.setNombrePromocion("promocion3");
        promocion3.setDescripcion("descripcion detallada");
        promocion3.setPorcentajeDescuento(25);
        

        
        listaPromociones.add(promocion1);
        listaPromociones.add(promocion2);
        listaPromociones.add(promocion3);
        
        
        List<Promocion> expResult = listaPromociones;
        
        List<Promocion> result = instance.consultarPromociones();
        assertEquals(expResult, result);
    }

    /**
     * Test of adquirirPromocionPorNombre method, of class PromocionDAO.
     */
    @Test
    public void testAdquirirPromocionPorNombre() {
        System.out.println("adquirirPromocionPorNombre");
        Promocion promocion1=new Promocion();
        promocion1.setIdPromocion(1);
        promocion1.setNombrePromocion("promocion1");
        promocion1.setDescripcion("descripcion detallada");
        promocion1.setPorcentajeDescuento(25);
        PromocionDAO instance = new PromocionDAO(unidadPersistenciaPruebas);
        Promocion expResult = promocion1;
        Promocion result = instance.adquirirPromocionPorNombre("promocion1");
        assertEquals(expResult, result);
    }
    
    
}
