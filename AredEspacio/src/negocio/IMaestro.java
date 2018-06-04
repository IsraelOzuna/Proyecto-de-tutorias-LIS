package negocio;

import java.util.Date;
import java.util.List;

public interface IMaestro {

    /**
     * Este método permite registrar a un maestro en el sistema.
     *
     * @param maestro que contiene los datos del maestro que se registrará.
     * @return boolean que indica si el maestro fue registrar correctamente
     */
    public boolean registrarMaestro(Maestro maestro);

    /**
     * Este método permite modificar los datos de un maestro.
     *
     * @param maestro que contiene los datos modificados del maestro
     * @return boolean que indica el la modificación de los datos del maestro
     * fue correcta.
     */
    public boolean editarMaestro(persistencia.Maestro maestro);

    /**
     * Estem método permite buscar los maestros que coincidan con el nombre
     * buscado
     *
     * @param nombre del maestro
     * @return Una lista que contiene las coincidencias de los maestros con ese
     * nombre
     */
    public List<persistencia.Maestro> buscarMaestro(String nombre);

    /**
     * Este método permite obtener el número de maestros registrados en el
     * sistema
     *
     * @return un entero que indica el número de maestrsos registrados
     */
    public int obtenerNumeroMaestros();

    /**
     * Este métoo permite obtener todos los maestros registrados en el sistema
     *
     * @return una lista que contiene los datos de todos los maestros
     * registrados en el sistema
     */
    public List<persistencia.Maestro> adquirirMaestros();

    /**
     * Este método permite obtener el nombre del maestro a través de su
     * identificador únivo de usuario
     *
     * @param nombreUsuario identificador único del usuario
     * @return una cadena que contiene el nombre del maestro
     */
    public String adquirirNombreMaestroPorNombreDeUsuario(String nombreUsuario);

    /**
     * Este método permite obtener los datos de un maestro a partir de su nombre
     *
     * @param nombreMaestro nombre del maestro elegido
     * @return Un objeto que contiene los datos del maestro
     */
    public persistencia.Maestro adquirirMaestro(String nombreMaestro);

    /**
     * Este método permite obtener los maestros a partir de su fecha de corte
     *
     * @param fechaCorte fecha mensual en que deben de pagar
     * @return Una lista con los maestros que su fecha de corte coincide con la
     * de interés
     */
    public List<persistencia.Maestro> adquirirMaestrosPorFechaCorte(Date fechaCorte);
}
