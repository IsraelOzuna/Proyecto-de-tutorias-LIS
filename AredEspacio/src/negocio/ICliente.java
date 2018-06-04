package negocio;

import java.util.List;

/**
 *
 * @author Israel Reyes Ozuna
 */
public interface ICliente {

    /**
     * Este método permite que se ingresen nuevos clientes al sistema
     *
     * @param cliente nuevo que es registrado en el sistema
     * @return boolean que ayuda a saber si el registro se realizó con exito
     * @since 1.0 / 5 de junio de 2018
     */
    public boolean registrarCliente(Cliente cliente);

    /**
     * Este método permite modificar los datos de algún cliente
     *
     * @param cliente el cual los datos serán modificados
     * @return boolean que ayuda a saber si las modificaciones se realizaron con
     * exito
     * @since 1.0 / 5 de junio de 2018
     */
    public boolean editarCliente(persistencia.Cliente cliente);

    /**
     * Este método ayuda a recuperar una lista de clientes con un nombre
     * ingresado
     *
     * @param nombre del cliente que desea encontrar en el sistema
     * @return List de clientes con los que coincide el nombre ingresado
     * @since 1.0 / 5 de junio de 2018
     */
    public List<persistencia.Cliente> buscarCliente(String nombre);

    /**
     * Este método encuentra a todos los clientes en el sistema
     *
     * @return List con la información de todos los clientes encontrados
     * @since 1.0 / 5 de junio de 2018
     */
    public List<persistencia.Cliente> buscarTodosLosClientes();
}
