/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Renato
 */
@Entity
@Table(name = "grupo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grupo.findAll", query = "SELECT g FROM Grupo g")
    , @NamedQuery(name = "Grupo.findByNombreGrupo", query = "SELECT g FROM Grupo g WHERE g.nombreGrupo = :nombreGrupo")
    , @NamedQuery(name = "Grupo.findByFechaPago", query = "SELECT g FROM Grupo g WHERE g.fechaPago = :fechaPago")
    , @NamedQuery(name = "Grupo.findByMensualidad", query = "SELECT g FROM Grupo g WHERE g.mensualidad = :mensualidad")
    , @NamedQuery(name = "Grupo.findByInscripcion", query = "SELECT g FROM Grupo g WHERE g.inscripcion = :inscripcion")})
public class Grupo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idGrupo")
    private Integer idGrupo;
    @Column(name = "estaActivo")
    private Integer estaActivo;
    @OneToMany(mappedBy = "nombreGrupo")
    private Collection<Pagoalumno> pagoalumnoCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "nombreGrupo")
    private String nombreGrupo;
    @Column(name = "fechaPago")
    @Temporal(TemporalType.DATE)
    private Date fechaPago;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "mensualidad")
    private Double mensualidad;
    @Column(name = "inscripcion")
    private Double inscripcion;
    @ManyToMany(mappedBy = "grupoCollection")
    private Collection<Alumno> alumnoCollection;
    @JoinColumn(name = "usuario", referencedColumnName = "usuario")
    @ManyToOne
    private Cuenta usuario;

    public Grupo() {
    }

    public Grupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Double getMensualidad() {
        return mensualidad;
    }

    public void setMensualidad(Double mensualidad) {
        this.mensualidad = mensualidad;
    }

    public Double getInscripcion() {
        return inscripcion;
    }

    public void setInscripcion(Double inscripcion) {
        this.inscripcion = inscripcion;
    }

    @XmlTransient
    public Collection<Alumno> getAlumnoCollection() {
        return alumnoCollection;
    }

    public void setAlumnoCollection(Collection<Alumno> alumnoCollection) {
        this.alumnoCollection = alumnoCollection;
    }

    public Cuenta getUsuario() {
        return usuario;
    }

    public void setUsuario(Cuenta usuario) {
        this.usuario = usuario;
    }
    
    @XmlTransient
    public Collection<Pagoalumno> getPagoalumnoCollection() {
        return pagoalumnoCollection;
    }

    public void setPagoalumnoCollection(Collection<Pagoalumno> pagoalumnoCollection) {
        this.pagoalumnoCollection = pagoalumnoCollection;
    }

    public Integer getEstaActivo() {
        return estaActivo;
    }

    public void setEstaActivo(Integer estaActivo) {
        this.estaActivo = estaActivo;
    }

    public Grupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGrupo != null ? idGrupo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grupo)) {
            return false;
        }
        Grupo other = (Grupo) object;
        if ((this.idGrupo == null && other.idGrupo != null) || (this.idGrupo != null && !this.idGrupo.equals(other.idGrupo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistencia.Grupo[ idGrupo=" + idGrupo + " ]";
    }
    
}
