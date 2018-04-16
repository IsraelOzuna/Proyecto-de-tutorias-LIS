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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Irdevelo
 */
@Entity
@Table(name = "pagomaestro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pagomaestro.findAll", query = "SELECT p FROM Pagomaestro p"),
    @NamedQuery(name = "Pagomaestro.findByIdPago", query = "SELECT p FROM Pagomaestro p WHERE p.idPago = :idPago"),
    @NamedQuery(name = "Pagomaestro.findByUsuario", query = "SELECT p FROM Pagomaestro p WHERE p.usuario = :usuario"),
    @NamedQuery(name = "Pagomaestro.findByCantidad", query = "SELECT p FROM Pagomaestro p WHERE p.cantidad = :cantidad"),
    @NamedQuery(name = "Pagomaestro.findByFecha", query = "SELECT p FROM Pagomaestro p WHERE p.fecha = :fecha")})
public class Pagomaestro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPago")
    private Integer idPago;
    @Column(name = "usuario")
    private String usuario;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cantidad")
    private Double cantidad;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    public Pagomaestro() {
    }

    public Pagomaestro(Integer idPago) {
        this.idPago = idPago;
    }

    public Integer getIdPago() {
        return idPago;
    }

    public void setIdPago(Integer idPago) {
        this.idPago = idPago;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
        if (!(object instanceof Pagomaestro)) {
            return false;
        }
        Pagomaestro other = (Pagomaestro) object;
        if ((this.idPago == null && other.idPago != null) || (this.idPago != null && !this.idPago.equals(other.idPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistencia.Pagomaestro[ idPago=" + idPago + " ]";
    }
    
}
