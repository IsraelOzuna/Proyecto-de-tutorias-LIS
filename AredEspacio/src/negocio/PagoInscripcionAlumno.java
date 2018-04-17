/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.util.Date;

/**
 *
 * @author iro19
 */
public class PagoInscripcionAlumno {
    private Date fechaPagoInscripcion;
    private char tipoPago;
    private Double cantidad;
    private String nombreGrupo;
    private int idAlumno;

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
