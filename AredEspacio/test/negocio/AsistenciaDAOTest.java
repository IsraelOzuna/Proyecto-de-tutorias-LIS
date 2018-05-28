package negocio;
import static java.lang.reflect.Array.set;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import persistencia.Asistencia;
import persistencia.Grupo;

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
    public void testObtenerAsistenciaExitoso() {
        System.out.println("obtenerAsistencia");
        persistencia.Alumno nuevoAlumno = new persistencia.Alumno();
        nuevoAlumno.setIdAlumno(1);
        Grupo nuevoGrupo = new Grupo();
        nuevoGrupo.setIdGrupo(2);
        Date fecha = new GregorianCalendar(2018, Calendar.MAY, 10).getTime();
        int idGrupo = 2;
        Asistencia asistenciaComprobar = new Asistencia();
        asistenciaComprobar.setAsistio("1");
        asistenciaComprobar.setFecha(fecha);
        asistenciaComprobar.setIdAlumno(nuevoAlumno);
        asistenciaComprobar.setIdGrupo(nuevoGrupo);
        asistenciaComprobar.setIdAsistencia(3);
        AsistenciaDAO instance = new AsistenciaDAO(unidadPersistenciaPruebas);
        List<Asistencia> expResult = new ArrayList<>();
        expResult.add(asistenciaComprobar);
        List<Asistencia> result = instance.obtenerAsistencia(fecha, idGrupo);
        assertEquals(expResult, result);
    }

    @Test
    public void testRegistrarAsistenciaExitoso() {
        System.out.println("RegistrarAsistencia");
        int idAlumno = 1;
        int idGrupo = 2;
        Date fecha = new Date();
        String asistio = "1";
        AsistenciaDAO instance = new AsistenciaDAO(unidadPersistenciaPruebas);
        boolean expResult = true;
        boolean result = instance.RegistrarAsistencia(idAlumno, idGrupo, fecha, asistio);
        assertEquals(expResult, result);
    }
    
}
