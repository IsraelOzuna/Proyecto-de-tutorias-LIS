package negocio;

import java.util.Date;

/**
 * Esta clase contiene todos los atributos necesarios para manipular el pago de
 * un maestro en el sistema
 *
 * @author Irvin Vera
 * @version 1.0 / 5 de junio de 2018
 */
public class PagoMaestro {

    private String usuario;
    private Date fecha;
    private Double cantidad;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

}
