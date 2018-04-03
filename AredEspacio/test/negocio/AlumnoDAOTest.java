package negocio;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author Israel Reyes Ozuna
 */
public class AlumnoDAOTest {

    public AlumnoDAOTest() {
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
     * Test of registrarAlumno method, of class AlumnoDAO.
     */
    @Test
    public void testRegistrarAlumnoExitoso() throws ParseException {
        Alumno alumno = new Alumno();
        String fecha = "13/12/1993";
        DateFormat formato = new SimpleDateFormat("DD/MM/YYY");
        Date fechaNacimiento = formato.parse(fecha);

        alumno.setNombre("Esmeralda");
        alumno.setApellidos("Ortiz");
        alumno.setFechaNacimiento(fechaNacimiento);
        alumno.setCorreoElectronico("esmeraldaVera@gmail.com");
        alumno.setTelefono("2281828384");        
        alumno.setRutaFoto(null);        
        AlumnoDAO alumnoDAO = new AlumnoDAO();

        boolean expResult = true;
        boolean result = alumnoDAO.registrarAlumno(alumno);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testRegistrarAlumnoFallido() {
        Alumno alumno = null;      
        AlumnoDAO alumnoDAO = new AlumnoDAO();

        boolean expResult = false;
        boolean result = alumnoDAO.registrarAlumno(alumno);
        assertEquals(expResult, result);
    }

    /**
     * Test of buscarAlumno method, of class AlumnoDAO.
     */
//    @Test
//    public void testBuscarAlumnoExitoso() {
//        String nombre = "Esmeralda";
//        AlumnoDAO alumnoDAO = new AlumnoDAO();
//        List<persistencia.Alumno> expResult = new ArrayList<persistencia.Alumno>();
//        List<persistencia.Alumno> result = alumnoDAO.buscarAlumno(nombre);
//        assertEquals(expResult, result);
//    }
    
    @Test
    public void testBuscarAlumnoFallido() {
        String nombre = "Julio";
        AlumnoDAO alumnoDAO = new AlumnoDAO();
        List<persistencia.Alumno> expResult = new ArrayList<persistencia.Alumno>();
        List<persistencia.Alumno> result = alumnoDAO.buscarAlumno(nombre);
        assertEquals(expResult, result);
    }

}
