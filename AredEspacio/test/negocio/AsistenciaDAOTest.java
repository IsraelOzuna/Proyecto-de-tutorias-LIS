package negocio;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import persistencia.Asistencia;

/**
 *
 * @author Renato
 */

public class AsistenciaDAOTest {
    private static String unidadPersistenciaPruebas="PruebasAredEspacioPU";
    
    public AsistenciaDAOTest() {
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
    public void testObtenerAsistenciaFallido() {
        System.out.println("obtenerAsistencia");
        Date fecha = null;
        int idGrupo = 0;
        AsistenciaDAO instance = new AsistenciaDAO(unidadPersistenciaPruebas);
        List<Asistencia> expResult = null;
        List<Asistencia> result = instance.obtenerAsistencia(fecha, idGrupo);
        assertEquals(expResult, result);
    }


    @Test
    public void testObtenerListaPorGrupoFallido() {
        System.out.println("obtenerListaPorGrupo");
        List<Asistencia> listaPorFecha = null;
        int idGrupo = 0;
        AsistenciaDAO instance = new AsistenciaDAO(unidadPersistenciaPruebas);
        List<Asistencia> expResult = null;
        List<Asistencia> result = instance.obtenerListaPorGrupo(listaPorFecha, idGrupo);
        assertEquals(expResult, result);
    }

    /**
     * Test of obtenerListaPorAlumno method, of class AsistenciaDAO.
     */
    @Test
    public void testObtenerListaPorAlumnoFallido() {
        System.out.println("obtenerListaPorAlumno");
        List<Asistencia> listaPorGrupo = null;
        int idAlumno = 0;
        AsistenciaDAO instance = new AsistenciaDAO(unidadPersistenciaPruebas);
        List<Asistencia> expResult = null;
        List<Asistencia> result = instance.obtenerListaPorAlumno(listaPorGrupo, idAlumno);
        assertEquals(expResult, result);
    }

    /**
     * Test of RegistrarAsistencia method, of class AsistenciaDAO.
     */
    @Test
    public void testRegistrarAsistencia() {
        System.out.println("RegistrarAsistencia");
        int idAlumno = 100;
        int idGrupo = 100;
        Date fecha = null;
        String asistio = "";
        AsistenciaDAO instance = new AsistenciaDAO(unidadPersistenciaPruebas);
        boolean expResult = false;
        boolean result = instance.RegistrarAsistencia(idAlumno, idGrupo, fecha, asistio);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
