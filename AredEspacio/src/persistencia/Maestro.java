
package persistencia;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
    @NamedQuery(name = "Maestro.findByNombre", query = "SELECT m FROM Maestro m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "Maestro.findByApellidos", query = "SELECT m FROM Maestro m WHERE m.apellidos = :apellidos"),
    @NamedQuery(name = "Maestro.findByCorreoElectronico", query = "SELECT m FROM Maestro m WHERE m.correoElectronico = :correoElectronico"),
    @NamedQuery(name = "Maestro.findByTelefono", query = "SELECT m FROM Maestro m WHERE m.telefono = :telefono"),
    @NamedQuery(name = "Maestro.findByEstaActivo", query = "SELECT m FROM Maestro m WHERE m.estaActivo = :estaActivo"),
    @NamedQuery(name = "Maestro.findByFechaCorte", query = "SELECT m FROM Maestro m WHERE m.fechaCorte = :fechaCorte"),
    @NamedQuery(name = "Maestro.findByRutaFoto", query = "SELECT m FROM Maestro m WHERE m.rutaFoto = :rutaFoto"),
    @NamedQuery(name = "Maestro.findByMensualidad", query = "SELECT m FROM Maestro m WHERE m.mensualidad = :mensualidad"),
    @NamedQuery(name = "Maestro.findByUsuario", query = "SELECT m FROM Maestro m WHERE m.usuario = :usuario")})
public class Maestro implements Serializable {

    @OneToMany(mappedBy = "creador")
    private Collection<Egreso> egresoCollection;

    private static final long serialVersionUID = 1L;
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
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "mensualidad")
    private Double mensualidad;
    @Id
    @Basic(optional = false)
    @Column(name = "usuario")
    private String usuario;
    @JoinColumn(name = "usuario", referencedColumnName = "usuario", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Cuenta cuenta;

    public Maestro() {
    }

    public Maestro(String usuario) {
        this.usuario = usuario;
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

    public Double getMensualidad() {
        return mensualidad;
    }

    public void setMensualidad(Double mensualidad) {
        this.mensualidad = mensualidad;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuario != null ? usuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Maestro)) {
            return false;
        }
        Maestro other = (Maestro) object;
        if ((this.usuario == null && other.usuario != null) || (this.usuario != null && !this.usuario.equals(other.usuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistencia.Maestro[ usuario=" + usuario + " ]";
    }

    @XmlTransient
    public Collection<Egreso> getEgresoCollection() {
        return egresoCollection;
    }

    public void setEgresoCollection(Collection<Egreso> egresoCollection) {
        this.egresoCollection = egresoCollection;
    }
    
}
