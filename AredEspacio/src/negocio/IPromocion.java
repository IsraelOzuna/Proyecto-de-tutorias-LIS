package negocio;

import java.util.List;
import persistencia.Promocion;

/**
 *
 * @author Israel Reyes Ozuna
 */
public interface IPromocion {

    /**
     * Este método permite que se registren promociones para inscripción o
     * mensualidades de los alumnos
     *
     * @param nuevaPromocion
     * @return boolean que ayuda a saber si el registro se realizó con exito
     * @since 1.0 / 5 de junio de 2018
     */
    public boolean registrarPromocion(persistencia.Promocion nuevaPromocion);

    /**
     * Este método permite que recuperar las promociones de acuerdo a si son
     * aplicables a mensualidades o a inscripciones
     *
     * @param tipoPromocion para saber cuales recuperar, si de mensualidad o de
     * inscrpción
     * @return List con las promociones encontradas
     * @since 1.0 / 5 de junio de 2018
     */
    public List<persistencia.Promocion> consultarPromociones(String tipoPromocion);

    /**
     * Este método permite recuperar las promociones en el sistema pero con su
     * nombre de registro
     *
     * @param nombrePromocion
     * @return Promocion con todos los datos encontrados de esta promoción
     * @since 1.0 / 5 de junio de 2018
     */
    public Promocion adquirirPromocionPorNombre(String nombrePromocion);

    /**
     * Este método permite verificar si ya existen promociones con el mismo
     * nombre
     *
     * @param nombrePromocion para encontrar las promociones con ese nombre
     * @return boolean que ayuda a saber existe alguna promoción con el nombre
     * @since 1.0 / 5 de junio de 2018
     */
    public boolean promocionYaexistente(String nombrePromocion);

    /**
     * Este método permite recuperar todas las promociones que se encuentran
     * registradas en el sistema
     *
     * @return List con todas las promociones encontradas
     * @since 1.0 / 5 de junio de 2018
     */
    public List<persistencia.Promocion> obtenerPromociones();
}
