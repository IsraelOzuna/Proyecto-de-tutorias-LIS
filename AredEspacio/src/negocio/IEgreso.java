package negocio;

import java.util.List;

/**
 *
 * @author Israel Reyes Ozuna
 */
public interface IEgreso {

    /**
     * Este método permite que se registren egresos que son consecuencia de
     * anuncios que estarán en la red social Facebook
     *
     * @param recursoFb con todos los atributos necesarios para registrarlo
     * @param usuarioMaestro El maestro el cual realizó el registro del anuncio
     * @return boolean que ayuda a saber si el registro se realizó con exito
     * @since 1.0 / 5 de junio de 2018
     */
    public boolean registrarEgresoFb(Egreso recursoFb, String usuarioMaestro);

    /**
     * Este método permite que se registren gastos de algún otro tipo
     *
     * @param egresoVariado con todos loa atributos necesarios para su registro
     * @return boolean que ayuda a saber si el registro se realizó con exito
     * @since 1.0 / 5 de junio de 2018
     */
    public boolean registrarEgresoVariado(Egreso egresoVariado);

    /**
     * Este método permite buscar los egresos que se han registrado en el
     * sistema
     *
     * @param allo año el cual se desea saber los egresos
     * @param mes el cual se desea saber los egresos
     * @return List con todos los egresos de ese mes y año
     * @since 1.0 / 5 de junio de 2018
     */
    public List<persistencia.Egreso> obtenerTodosLosEgresos(int allo, int mes);
}
