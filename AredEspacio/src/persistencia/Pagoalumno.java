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
 * @author iro19
 */
@Entity
@Table(name = "pagoalumno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pagoalumno.findAll", query = "SELECT p FROM Pagoalumno p")
    , @NamedQuery(name = "Pagoalumno.findByIdPago", query = "SELECT p FROM Pagoalumno p WHERE p.idPago = :idPago")
    , @NamedQuery(name = "Pagoalumno.findByFechaPago", query = "SELECT p FROM Pagoalumno p WHERE p.fechaPago = :fechaPago")
    , @NamedQuery(name = "Pagoalumno.findByCantidad", query = "SELECT p FROM Pagoalumno p WHERE p.cantidad = :cantidad")
    , @NamedQuery(name = "Pagoalumno.findByTipoPago", query = "SELECT p FROM Pagoalumno p WHERE p.tipoPago = :tipoPago")})
public class Pagoalumno implements Serializable {

    @JoinColumn(name = "idAlumno", referencedColumnName = "idAlumno")
    @ManyToOne
    private Alumno idAlumno;
    @JoinColumn(name = "idGrupo", referencedColumnName = "idGrupo")
    @ManyToOne
    private Grupo idGrupo;

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
    @Column(name = "tipoPago")
    private String tipoPago;

    public Pagoalumno() {
    }

    public Pagoalumno(Integer idPago) {
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

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
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
        if (!(object instanceof Pagoalumno)) {
            return false;
        }
        Pagoalumno other = (Pagoalumno) object;
        if ((this.idPago == null && other.idPago != null) || (this.idPago != null && !this.idPago.equals(other.idPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistencia.Pagoalumno[ idPago=" + idPago + " ]";
    }

    public Alumno getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Alumno idAlumno) {
        this.idAlumno = idAlumno;
    }

    public Grupo getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Grupo idGrupo) {
        this.idGrupo = idGrupo;
    }
    
}
