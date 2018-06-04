package negocio;

import java.util.List;

/**
 *
 * @author Israel Reyes Ozuna
 */
public interface IAlumno {

    /**
     * Este método permite que se ingresen nuevos alumnos al sistema
     *
     * @param alumno nuevo que es registrado en el sistema
     * @return boolean que ayuda a saber si el registro se realizó con exito
     * @since 1.0 / 5 de junio de 2018
     */
    public boolean registrarAlumno(Alumno alumno);

    /**
     * Este método permite modificar los datos de algún alumno
     *
     * @param alumno el cual los datos serán modificados
     * @return boolean que ayuda a saber si las modificaciones se realizaron con
     * exito
     * @since 1.0 / 5 de junio de 2018
     */
    public boolean editarAlumno(persistencia.Alumno alumno);

    /**
     * Este método ayuda a recuperar una lista de alumnos con un nombre
     * ingresado
     *
     * @param nombre del alumno que desea encontrar en el sistema
     * @return List de alumnos con los que coincide el nombre ingresado
     * @since 1.0 / 5 de junio de 2018
     */
    public List<persistencia.Alumno> buscarAlumno(String nombre);

    /**
     * Este método encuentra todos los grupos a los cuales un alumno está
     * inscrito
     *
     * @param idAlumno que es el identificador único que el sistema le otorga a
     * cada uno
     * @return List de los grupos dónde el alumno se encuentra inscrito
     * @since 1.0 / 5 de junio de 2018
     */
    public List<String> encontrarGruposAlumno(int idAlumno);

    /**
     * Este método encuentra un alumno especificado a través de su identificador
     *
     * @param idAlumno que es el identificador único que el sistema le otorga a
     * cada uno
     * @return Alumno con todos sus atributos
     * @since 1.0 / 5 de junio de 2018
     */
    public persistencia.Alumno adquirirAlumno(int idAlumno);

    /**
     * Este método encuentra a todos los alumnos en el sistema
     *
     * @return List con la información de todos los alumnos encontrados
     * @since 1.0 / 5 de junio de 2018
     */
    public List<persistencia.Alumno> obtenerAlumnos();
}
