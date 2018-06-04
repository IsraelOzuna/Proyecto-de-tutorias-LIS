package negocio;

import java.util.List;
import persistencia.Pagomaestro;

/**
 *
 * @author Irvin Vera
 */
public interface IPagoMaestro {

    /**
     * Este método permite registrar un pago realizado por un maestro
     *
     * @param pagoMaestro contiene los datos del pago realizado por el maestro
     * @return boolean que indica i el pago fue registrado correctamente.
     */
    public boolean registrarPagoMaestro(PagoMaestro pagoMaestro);

    /**
     * Este método obtiene los pagos realizados por los maestros que
     * corresponden a un mes y un año específicos.
     *
     * @param allo espeficado por el usuario
     * @param mes espeficado por el usuario
     * @return una lista que contiene los pagos de los maestros a partir del mes
     * y año seleccionado
     */
    public List<Pagomaestro> obtenerPagosMaestros(int allo, int mes);

}
