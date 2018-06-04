package negocio;

import java.sql.Time;
import java.util.Date;

/**
 * Esta clase contiene todos los atributos necesarios para manipular las rentas
 * en el sistema
 *
 * @author Irvin Vera
 * @version 1.0 / 5 de junio de 2018
 */
public class Renta {

    private String nombreCliente;

    private Date fecha;

    private Double cantidad;

    private Time horaInicio;

    private Time horaFin;

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
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

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Time getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
    }

}
