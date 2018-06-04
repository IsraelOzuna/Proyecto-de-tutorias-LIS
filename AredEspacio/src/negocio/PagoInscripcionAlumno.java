package negocio;
import java.util.Date;
/**
 * Esta clase contiene todos los atributos necesarios para manipular un pago de
 * inscripción en el sistema
 *
 * @author Renato Vargas Gómez
 * @version 1.0 / 5 de junio de 2018
 */
public class PagoInscripcionAlumno {
    private Date fechaPagoInscripcion;
    private char tipoPago;
    private Double cantidad;
    private String nombreGrupo;
    private int idAlumno;

    public Date getFechaPagoInscripcion() {
        return fechaPagoInscripcion;
    }

    public void setFechaPagoInscripcion(Date fechaPagoInscripcion) {
        this.fechaPagoInscripcion = fechaPagoInscripcion;
    }

    public char getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(char tipoPago) {
        this.tipoPago = tipoPago;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }
}
