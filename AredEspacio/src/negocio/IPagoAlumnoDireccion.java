package negocio;
import java.util.List;

/**
 *
 * @author Renato Vargas Gómez
 */
public interface IPagoAlumnoDireccion {
    /**
     * Este Metodo registra un pago que será guardado en la dirección
     * 
     * @param pago pago que será registrado en el sistema
     * @return boolean que valida que el registro se haya realizado de forma exitosa
     * @since 1.0 / 5 de junio de 2018
     **/
    boolean registrarPagoDireccion(persistencia.Pagoalumnodireccion pago);
    
    /**
     * Este Metodo obtiene los pagos realizados a la dirección
     * 
     * @param usuario el usuario que realiza la consulta para adquirir los pagos
     * a grupos de el usuario
     * @return List con todos los pagos realizados dirigidos a un grupo de un 
     * maestro
     * @since 1.0 / 5 de junio de 2018
     **/
    List<persistencia.Pagoalumnodireccion> obtenerPagos(persistencia.Cuenta usuario);
}
