/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Renato
 */
@Entity
@Table(name = "pagoalumnodireccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pagoalumnodireccion.findAll", query = "SELECT p FROM Pagoalumnodireccion p")
    , @NamedQuery(name = "Pagoalumnodireccion.findByIdPago", query = "SELECT p FROM Pagoalumnodireccion p WHERE p.idPago = :idPago")
    , @NamedQuery(name = "Pagoalumnodireccion.findByFechaPago", query = "SELECT p FROM Pagoalumnodireccion p WHERE p.fechaPago = :fechaPago")
    , @NamedQuery(name = "Pagoalumnodireccion.findByCantidad", query = "SELECT p FROM Pagoalumnodireccion p WHERE p.cantidad = :cantidad")})
public class Pagoalumnodireccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPago")
    private Integer idPago;
    @Column(name = "fechaPago")
    @Temporal(TemporalType.DATE)
    private Date fechaPago;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cantidad")
    private Double cantidad;
    @JoinColumn(name = "idAlumno", referencedColumnName = "idAlumno")
    @ManyToOne
    private Alumno idAlumno;
    @JoinColumn(name = "usuario", referencedColumnName = "usuario")
    @ManyToOne
    private Cuenta usuario;
    @JoinColumn(name = "idGrupo", referencedColumnName = "idGrupo")
    @ManyToOne
    private Grupo idGrupo;

    public Pagoalumnodireccion() {
    }

    public Pagoalumnodireccion(Integer idPago) {
        this.idPago = idPago;
    }

    public Integer getIdPago() {
        return idPago;
    }

    public void setIdPago(Integer idPago) {
        this.idPago = idPago;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Alumno getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Alumno idAlumno) {
        this.idAlumno = idAlumno;
    }

    public Cuenta getUsuario() {
        return usuario;
    }

    public void setUsuario(Cuenta usuario) {
        this.usuario = usuario;
    }

    public Grupo getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Grupo idGrupo) {
        this.idGrupo = idGrupo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPago != null ? idPago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pagoalumnodireccion)) {
            return false;
        }
        Pagoalumnodireccion other = (Pagoalumnodireccion) object;
        if ((this.idPago == null && other.idPago != null) || (this.idPago != null && !this.idPago.equals(other.idPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistencia.Pagoalumnodireccion[ idPago=" + idPago + " ]";
    }
    
}
