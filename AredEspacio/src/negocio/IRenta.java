package negocio;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Irvin Vera
 */
public interface IRenta {

    /**
     * Este método permite obtener todas la rentas registradas en el sistema.
     *
     * @return Una lista que contiene todas las rentas existentes
     */
    public List<persistencia.Renta> obtenerRentas();

    /**
     * Este método permite obtener todas las rentas registradas de una fecha en
     * especial.
     *
     * @param fecha la cual es establecida por el usuario para visualizar las
     * rentas registradas ese día.
     * @return Una lista que contiene todas las rentas existentes en esa fecha.
     */
    public List<persistencia.Renta> obtenerRentasPorFecha(Date fecha);

    /**
     * Este método adquiere todas las rentas que corresponde a un mes y un año
     * específicos indepedientemente del día.
     *
     * @param mes elegido por el usuario
     * @param allo elegido por el usuario
     * @return Una lista que contiene las rentas del mes y año especificados
     */
    public List<persistencia.Renta> obtenerRentasPorMesAllo(int mes, int allo);

    /**
     * Este método permite registrar una renta en el sistema.
     *
     * @param renta nueva que es registrada en el sistema
     * @return boolean que indica si la renta fue registrada correctamente
     */
    public boolean registrarRenta(Renta renta);

    /**
     * Este método permite eliminar una renta que está registrada en el sistema
     *
     * @param id de la renta que se desea eliminar
     * @return boolean que indica si la renta fue eliminada correctamente
     */
    public boolean eliminarRenta(int id);

    /**
     * Este método permite editar los datos de una renta registrada en el
     * sistema
     *
     * @param renta que contiene los nuevos datos ya editados por el usuario
     * @param idRenta identificador único de la renta que se desea modificar
     * @return boolean que indica si los datos de la renta fueron modificados
     * correctamente.
     */
    public boolean editarRenta(Renta renta, int idRenta);

}
