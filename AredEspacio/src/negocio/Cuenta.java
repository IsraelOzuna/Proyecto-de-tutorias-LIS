package negocio;

/**
 * Esta clase contiene todos los atributos necesarios para manipular una cuenta
 * en el sistema
 *
 * @author Irvin Vera
 * @version 1.0 / 5 de junio de 2018
 */
public class Cuenta {

    private String usuario;
    private String contraseña;
    private String tipoCuenta;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

}
