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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * @author Irdevelo
 */
@Entity
@Table(name = "maestro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Maestro.findAll", query = "SELECT m FROM Maestro m"),
    @NamedQuery(name = "Maestro.findByIdMaestro", query = "SELECT m FROM Maestro m WHERE m.idMaestro = :idMaestro"),
    @NamedQuery(name = "Maestro.findByNombre", query = "SELECT m FROM Maestro m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "Maestro.findByApellidos", query = "SELECT m FROM Maestro m WHERE m.apellidos = :apellidos"),
    @NamedQuery(name = "Maestro.findByCorreoElectronico", query = "SELECT m FROM Maestro m WHERE m.correoElectronico = :correoElectronico"),
    @NamedQuery(name = "Maestro.findByTelefono", query = "SELECT m FROM Maestro m WHERE m.telefono = :telefono"),
    @NamedQuery(name = "Maestro.findByEstaActivo", query = "SELECT m FROM Maestro m WHERE m.estaActivo = :estaActivo"),
    @NamedQuery(name = "Maestro.findByFechaCorte", query = "SELECT m FROM Maestro m WHERE m.fechaCorte = :fechaCorte"),
    @NamedQuery(name = "Maestro.findByRutaFoto", query = "SELECT m FROM Maestro m WHERE m.rutaFoto = :rutaFoto")})
public class Maestro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idMaestro")
    private Integer idMaestro;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "correoElectronico")
    private String correoElectronico;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "estaActivo")
    private Integer estaActivo;
    @Column(name = "fechaCorte")
    @Temporal(TemporalType.DATE)
    private Date fechaCorte;
    @Column(name = "rutaFoto")
    private String rutaFoto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMaestro")
    private Collection<Cuenta> cuentaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMaestro")
    private Collection<Grupo> grupoCollection;

    public Maestro() {
    }

    public Maestro(Integer idMaestro) {
        this.idMaestro = idMaestro;
    }

    public Integer getIdMaestro() {
        return idMaestro;
    }

    public void setIdMaestro(Integer idMaestro) {
        this.idMaestro = idMaestro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Integer getEstaActivo() {
        return estaActivo;
    }

    public void setEstaActivo(Integer estaActivo) {
        this.estaActivo = estaActivo;
    }

    public Date getFechaCorte() {
        return fechaCorte;
    }

    public void setFechaCorte(Date fechaCorte) {
        this.fechaCorte = fechaCorte;
    }

    public String getRutaFoto() {
        return rutaFoto;
    }

    public void setRutaFoto(String rutaFoto) {
        this.rutaFoto = rutaFoto;
    }

    @XmlTransient
    public Collection<Cuenta> getCuentaCollection() {
        return cuentaCollection;
    }

    public void setCuentaCollection(Collection<Cuenta> cuentaCollection) {
        this.cuentaCollection = cuentaCollection;
    }

    @XmlTransient
    public Collection<Grupo> getGrupoCollection() {
        return grupoCollection;
    }

    public void setGrupoCollection(Collection<Grupo> grupoCollection) {
        this.grupoCollection = grupoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMaestro != null ? idMaestro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Maestro)) {
            return false;
        }
        Maestro other = (Maestro) object;
        if ((this.idMaestro == null && other.idMaestro != null) || (this.idMaestro != null && !this.idMaestro.equals(other.idMaestro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "aredespacio.Maestro[ idMaestro=" + idMaestro + " ]";
    }
    
}
