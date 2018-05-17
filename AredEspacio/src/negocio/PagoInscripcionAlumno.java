package negocio;

import java.util.Date;

public class PagoInscripcionAlumno {
    private Date fechaPagoInscripcion;
    private char tipoPago;
    private Double cantidad;
    private String nombreGrupo;
    private int idAlumno;

    /**
     * @return the fechaPagoInscripcion
     */
    public Date getFechaPagoInscripcion() {
        return fechaPagoInscripcion;
    }

    /**
     * @param fechaPagoInscripcion the fechaPagoInscripcion to set
     */
    public void setFechaPagoInscripcion(Date fechaPagoInscripcion) {
        this.fechaPagoInscripcion = fechaPagoInscripcion;
    }

    /**
     * @return the tipoPago
     */
    public char getTipoPago() {
        return tipoPago;
    }

    /**
     * @param tipoPago the tipoPago to set
     */
    public void setTipoPago(char tipoPago) {
        this.tipoPago = tipoPago;
    }

    /**
     * @return the cantidad
     */
    public Double getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the nombreGrupo
     */
    public String getNombreGrupo() {
        return nombreGrupo;
    }

    /**
     * @param nombreGrupo the nombreGrupo to set
     */
    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    /**
     * @return the idAlumno
     */
    public int getIdAlumno() {
        return idAlumno;
    }

    /**
     * @param idAlumno the idAlumno to set
     */
    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }
}
