package negocio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import persistencia.Alumno;
import persistencia.Cuenta;
import persistencia.CuentaJpaController;
import persistencia.Grupo;
import persistencia.GrupoJpaController;
import persistencia.exceptions.IllegalOrphanException;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author Renato
 */
public class GrupoDAOTest {
    private static String unidadPersistenciaPruebas="PruebasAredEspacioPU";
    private static Cuenta usuario = new Cuenta();
    public GrupoDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        //Este usuario ya se encunetra registado en la base de datos
        usuario.setUsuario("cuentaPruebaDirector");
        usuario.setContrasena("contrasenaPrueba");
        usuario.setTipoCuenta("Director");
    }
    
    @AfterClass
    public static void tearDownClass() throws IllegalOrphanException, NonexistentEntityException {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() throws NonexistentEntityException {
        //Destruye al grupo que fue creado durante las pruebas
        List<Grupo> listaGrupos;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(unidadPersistenciaPruebas, null);//Cambiar a "AredEspacioPU"
        GrupoJpaController grupoJpaController = new GrupoJpaController(entityManagerFactory);
        listaGrupos=grupoJpaController.findGrupoEntities();
        for(int i=0; i<listaGrupos.size(); i++){
            if(listaGrupos.get(i).getNombreGrupo().equals("GrupoDePruebaUnidad")){
                grupoJpaController.destroy(listaGrupos.get(i).getIdGrupo());
            }
        }
    }

    @Test
    public void testCrearGrupoExitoso() throws NonexistentEntityException {
       
        System.out.println("crearGrupo: ");
        Grupo nuevoGrupo = new Grupo();
        nuevoGrupo.setNombreGrupo("GrupoDePruebaUnidad");
        nuevoGrupo.setUsuario(usuario);
        nuevoGrupo.setMensualidad(3200.0);
        nuevoGrupo.setInscripcion(340.0);
        nuevoGrupo.setFechaPago(new Date());
        GrupoDAO instance = new GrupoDAO(unidadPersistenciaPruebas);
        boolean expResult = true;
        boolean result = instance.crearGrupo(nuevoGrupo);
        assertEquals(expResult, result);  
    }
    
    @Test
    public void testAdquirirGrupoExitoso() throws NonexistentEntityException {
        System.out.println("adquirirGrupoExitoso");
        GrupoDAO instance = new GrupoDAO(unidadPersistenciaPruebas);
        
        Grupo grupoComparar = new Grupo();
        grupoComparar.setIdGrupo(1);
        grupoComparar.setNombreGrupo("grupo1");
        grupoComparar.setUsuario(usuario);
        grupoComparar.setFechaPago(null);
        grupoComparar.setEstaActivo(1);
        grupoComparar.setMensualidad(100.0);
        grupoComparar.setInscripcion(100.0);
        
        Grupo grupoAdquirido;
        grupoAdquirido=instance.adquirirGrupo(1);
        assertEquals(grupoComparar, grupoAdquirido);
    }
    
    @Test
    public void testCrearGrupoFallido() {
        System.out.println("crearGrupoFallido");
        Grupo nuevoGrupo = new Grupo();
        GrupoDAO instance = new GrupoDAO(unidadPersistenciaPruebas);
        boolean expResult = false;
        boolean result = instance.crearGrupo(nuevoGrupo);
        assertEquals(expResult, result); 
    }
    
    
    @Test 
    public void testEliminarGrupoExitoso() throws NonexistentEntityException{// el grupo no se elimina, se desactiva
        System.out.println("crearGrupo: ");
        Grupo nuevoGrupo = new Grupo();
        nuevoGrupo.setNombreGrupo("GrupoDePruebaUnidad");
        nuevoGrupo.setUsuario(usuario);
        nuevoGrupo.setMensualidad(3200.0);
        nuevoGrupo.setInscripcion(340.0);
        nuevoGrupo.setFechaPago(new Date());
        GrupoDAO instance = new GrupoDAO(unidadPersistenciaPruebas);
        instance.crearGrupo(nuevoGrupo);
        boolean expResult = true;
        boolean result = instance.eliminarGrupo(nuevoGrupo);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testAdquirirGrupoFallido() {
        System.out.println("adquirirGrupoFallido");
        int idGrupo = 0;
        GrupoDAO instance = new GrupoDAO(unidadPersistenciaPruebas);
        Grupo expResult = null;
        Grupo result = instance.adquirirGrupo(idGrupo);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testEditarGrupoExitoso() throws NonexistentEntityException {
        GrupoDAO instance = new GrupoDAO(unidadPersistenciaPruebas);
        Grupo grupoEditar = new Grupo();
        Grupo grupoAdquirido=instance.adquirirGrupo(1);
        grupoEditar = grupoAdquirido;
        grupoEditar.setMensualidad(100.0);
        
        boolean expResult=true;
        boolean result=instance.editarGrupo(grupoEditar);
        assertEquals(expResult, result);
    }  
    
    @Test
    public void testEditarGrupoFallido() {
        GrupoDAO instance = new GrupoDAO(unidadPersistenciaPruebas);
        Grupo grupoEditar = new Grupo();
        boolean expResult=false;
        boolean result=instance.editarGrupo(grupoEditar);
        assertEquals(expResult, result);
    }
    
     @Test
    public void testAdquirirGruposExitoso() {
        GrupoDAO grupoDAO = new GrupoDAO(unidadPersistenciaPruebas);
        Cuenta cuentaPruebaDirector = new Cuenta();
        Cuenta cuentaPruebaMaestro = new Cuenta();
        cuentaPruebaDirector.setUsuario("cuentaPruebaDirector");
        cuentaPruebaDirector.setContrasena("ontrasenaPrueba");
        cuentaPruebaDirector.setTipoCuenta("director");
        
        cuentaPruebaMaestro.setUsuario("cuentaPruebaMaestro");
        cuentaPruebaMaestro.setContrasena("contrasenaPrueba");
        cuentaPruebaMaestro.setTipoCuenta("maestro");
        
        List<Grupo> listaGruposPredeterminados = new ArrayList<Grupo>();
        Grupo grupo1 = new Grupo();
        Grupo grupo2 = new Grupo();
        Grupo grupo3 = new Grupo();
        Grupo grupo4 = new Grupo();
        Grupo grupo5 = new Grupo();
        
        grupo1.setIdGrupo(1);
        grupo2.setIdGrupo(2);
        grupo3.setIdGrupo(3);
        grupo4.setIdGrupo(4);
        grupo5.setIdGrupo(5);
        
        grupo1.setNombreGrupo("grupo1");
        grupo2.setNombreGrupo("grupo2");
        grupo3.setNombreGrupo("grupo3");
        grupo4.setNombreGrupo("grupo4");
        grupo5.setNombreGrupo("grupo5");
        
        grupo1.setUsuario(cuentaPruebaDirector);
        grupo2.setUsuario(cuentaPruebaMaestro);
        grupo3.setUsuario(cuentaPruebaDirector);
        grupo4.setUsuario(cuentaPruebaMaestro);
        grupo5.setUsuario(cuentaPruebaDirector);
        
        grupo1.setFechaPago(null);
        grupo2.setFechaPago(null);
        grupo3.setFechaPago(null);
        grupo4.setFechaPago(null);
        grupo5.setFechaPago(null);
        
        grupo1.setEstaActivo(1);
        grupo2.setEstaActivo(1);
        grupo3.setEstaActivo(1);
        grupo4.setEstaActivo(1);
        grupo5.setEstaActivo(1);
        
        grupo1.setMensualidad(100.0);
        grupo2.setMensualidad(100.0);
        grupo3.setMensualidad(100.0);
        grupo4.setMensualidad(100.0);
        grupo5.setMensualidad(100.0);
        
        grupo1.setInscripcion(100.0);
        grupo2.setInscripcion(100.0);
        grupo3.setInscripcion(100.0);
        grupo4.setInscripcion(100.0);
        grupo5.setInscripcion(100.0);
        
        listaGruposPredeterminados.add(grupo1);
        listaGruposPredeterminados.add(grupo2);
        listaGruposPredeterminados.add(grupo3);
        listaGruposPredeterminados.add(grupo4);
        listaGruposPredeterminados.add(grupo5);
        List<Grupo> listaGruposAdquiridos=grupoDAO.adquirirGrupos(cuentaPruebaDirector);
        assertEquals(listaGruposPredeterminados, listaGruposAdquiridos);
    }
    
    

}
