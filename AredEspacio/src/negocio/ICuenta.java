package negocio;

/**
 *
 * @author Irdevelo
 */
public interface ICuenta {

    /**
     * Este método permite registrar una cuenta nueva en el sistema.
     *
     * @param cuenta contiene los datos de la cuenta
     * @return boolean que indica si la cuenta fue registrada correctamente
     */
    public boolean crearCuenta(Cuenta cuenta);

    /**
     * Este método permite verificar si un nombre de usuario elegido por el
     * usuario ya se encuentra registrado en els sitema
     *
     * @param nombreUsuario elegido por el usuario
     * @return boolean que indica si el nombre ya se encuentra en uso
     */
    public boolean verificarNombreUsuarioRepetido(String nombreUsuario);

    /**
     * Este método permite iniciar sesión a los usuarios
     *
     * @param nombreUsuario identificador único del usuario
     * @param contrasena elegida por el usuario
     * @return Una cadena que indica si el usuario que inicio sesión es un
     * Maestro o el Director
     */
    public String iniciarSesion(String nombreUsuario, String contrasena);

    /**
     * Este método permite obtener la cuenta de un maestro.
     *
     * @param nombreMaestro nombre del maestro
     * @return un objeto de tipo Cuenta que contiene los datos de la cuenta del
     * maestro
     */
    public persistencia.Cuenta obtenerCuentaMaestro(String nombreMaestro);

    /**
     * Este método permite obtener la cuenta de un maestro.
     *
     * @param nombreUsuario identificador único del usuario
     * @return un objeto de tipo Cuenta que contiene los datos de la cuenta del
     * maestro
     */
    public persistencia.Cuenta obtenerCuenta(String nombreUsuario);
}
