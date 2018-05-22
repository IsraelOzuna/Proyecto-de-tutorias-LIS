package negocio;

import java.util.Date;

/**
 *
 * @author iro19
 */
public class PagoMensualidadAlumno {
    private Date fechaPagoInscripcion;
    private char tipoPago;
    private Double cantidad;
    private int idGrupo;
    private int idAlumno;

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }    

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }    

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
}
