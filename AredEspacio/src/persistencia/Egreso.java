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
@Table(name = "egreso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Egreso.findAll", query = "SELECT e FROM Egreso e")
    , @NamedQuery(name = "Egreso.findByIdEgreso", query = "SELECT e FROM Egreso e WHERE e.idEgreso = :idEgreso")
    , @NamedQuery(name = "Egreso.findByFechaRegistro", query = "SELECT e FROM Egreso e WHERE e.fechaRegistro = :fechaRegistro")
    , @NamedQuery(name = "Egreso.findByCosto", query = "SELECT e FROM Egreso e WHERE e.costo = :costo")
    , @NamedQuery(name = "Egreso.findByDescripcion", query = "SELECT e FROM Egreso e WHERE e.descripcion = :descripcion")
    , @NamedQuery(name = "Egreso.findByFechaPublicacion", query = "SELECT e FROM Egreso e WHERE e.fechaPublicacion = :fechaPublicacion")
    , @NamedQuery(name = "Egreso.findByFechaFin", query = "SELECT e FROM Egreso e WHERE e.fechaFin = :fechaFin")
    , @NamedQuery(name = "Egreso.findByLink", query = "SELECT e FROM Egreso e WHERE e.link = :link")
    , @NamedQuery(name = "Egreso.findByNombreGasto", query = "SELECT e FROM Egreso e WHERE e.nombreGasto = :nombreGasto")})
public class Egreso implements Serializable {

    @JoinColumn(name = "creador", referencedColumnName = "usuario")
    @ManyToOne
    private Maestro creador;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEgreso")
    private Integer idEgreso;
    @Column(name = "fechaRegistro")
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "costo")
    private Double costo;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fechaPublicacion")
    @Temporal(TemporalType.DATE)
    private Date fechaPublicacion;
    @Column(name = "fechaFin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Column(name = "link")
    private String link;
    @Column(name = "nombreGasto")
    private String nombreGasto;

    public Egreso() {
    }

    public Egreso(Integer idEgreso) {
        this.idEgreso = idEgreso;
    }

    public Integer getIdEgreso() {
        return idEgreso;
    }

    public void setIdEgreso(Integer idEgreso) {
        this.idEgreso = idEgreso;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNombreGasto() {
        return nombreGasto;
    }

    public void setNombreGasto(String nombreGasto) {
        this.nombreGasto = nombreGasto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEgreso != null ? idEgreso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Egreso)) {
            return false;
        }
        Egreso other = (Egreso) object;
        if ((this.idEgreso == null && other.idEgreso != null) || (this.idEgreso != null && !this.idEgreso.equals(other.idEgreso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistencia.Egreso[ idEgreso=" + idEgreso + " ]";
    }

    public Maestro getCreador() {
        return creador;
    }

    public void setCreador(Maestro creador) {
        this.creador = creador;
    }
    
}
