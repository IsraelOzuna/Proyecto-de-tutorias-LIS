package negocio;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    @Test
    public void testBuscarAlumnoExitoso() {        
        persistencia.Alumno alumno = new persistencia.Alumno();
        String fecha = "20/03/2018";
        DateFormat formato = new SimpleDateFormat("YYYY/MM/DD");
        Date fechaNacimiento = null;
        try {
            fechaNacimiento = formato.parse(fecha);
        } catch (ParseException ex) {
            Logger.getLogger(AlumnoDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        alumno.setIdAlumno(17);
        alumno.setNombre("Irvin");
        alumno.setApellidos("Vera");
        alumno.setCorreoElectronico("irvin@gmail.com");
        alumno.setTelefono("2281767676");
        alumno.setFechaNacimiento(fechaNacimiento);       
        alumno.setRutaFoto("estudiantes-educación-muchacho-en-vidrios-niño-del-alumno-del-alumno-51535682.jpg");
        AlumnoDAO alumnoDAO = new AlumnoDAO();
        List<persistencia.Alumno> expResult = new ArrayList<>();
        expResult.add(alumno);
        List<persistencia.Alumno> result = alumnoDAO.buscarAlumno("Irvin");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testBuscarAlumnoFallido() {
        String nombre = "Julio";
        AlumnoDAO alumnoDAO = new AlumnoDAO();
        List<persistencia.Alumno> expResult = new ArrayList<>();
        List<persistencia.Alumno> result = alumnoDAO.buscarAlumno(nombre);
        assertEquals(expResult, result);
    }

    @Test
    public void editarAlumnoExitoso(){
        persistencia.Alumno alumno = new persistencia.Alumno();
        String fecha = "20/03/2018";
        DateFormat formato = new SimpleDateFormat("YYYY/MM/DD");
        Date fechaNacimiento = null;
        try {
            fechaNacimiento = formato.parse(fecha);
        } catch (ParseException ex) {
            Logger.getLogger(AlumnoDAOTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        alumno.setIdAlumno(17);
        alumno.setNombre("Irvin");
        alumno.setApellidos("Lopez");
        alumno.setCorreoElectronico("irvin@gmail.com");
        alumno.setTelefono("2281767676");
        alumno.setFechaNacimiento(fechaNacimiento);       
        alumno.setRutaFoto("estudiantes-educación-muchacho-en-vidrios-niño-del-alumno-del-alumno-51535682.jpg");
        AlumnoDAO alumnoDAO = new AlumnoDAO();
        boolean expResult = true;        
        boolean result = alumnoDAO.editarAlumno(alumno);
        assertEquals(expResult, result);
    }
    
    @Test
    public void editarAlumnoExitosoFallido(){
        persistencia.Alumno alumno = null;        
        AlumnoDAO alumnoDAO = new AlumnoDAO();
        boolean expResult = false;        
        boolean result = alumnoDAO.editarAlumno(alumno);
        assertEquals(expResult, result);
    }
    
    @Test
    public void encontrarGruposAlumnoExitoso(){
        persistencia.Alumno alumno = new persistencia.Alumno();               
        alumno.setIdAlumno(17);
        AlumnoDAO alumnoDAO = new AlumnoDAO();
        List<String> expResult = new ArrayList<>();        
        List<String> result = alumnoDAO.encontrarGruposAlumno(alumno.getIdAlumno());
        assertEquals(expResult, result);
    }
    
    @Test
    public void encontrarGruposAlumnoFallido(){
        persistencia.Alumno alumno = new persistencia.Alumno();               
        alumno.setIdAlumno(20);
        AlumnoDAO alumnoDAO = new AlumnoDAO();
        List<String> expResult = new ArrayList<>();        
        List<String> result = alumnoDAO.encontrarGruposAlumno(alumno.getIdAlumno());
        assertEquals(expResult, result);
    }
}
