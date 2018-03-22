/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Irdevelo
 */
@Entity
@Table(name = "cuenta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cuenta.findAll", query = "SELECT c FROM Cuenta c"),
    @NamedQuery(name = "Cuenta.findByUsuario", query = "SELECT c FROM Cuenta c WHERE c.usuario = :usuario"),
    @NamedQuery(name = "Cuenta.findByContrasena", query = "SELECT c FROM Cuenta c WHERE c.contrasena = :contrasena"),
    @NamedQuery(name = "Cuenta.findByTipoCuenta", query = "SELECT c FROM Cuenta c WHERE c.tipoCuenta = :tipoCuenta")})
public class Cuenta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "usuario")
    private String usuario;
    @Column(name = "contrasena")
    private String contrasena;
    @Column(name = "tipoCuenta")
    private String tipoCuenta;
    @JoinColumn(name = "idMaestro", referencedColumnName = "idMaestro")
    @ManyToOne(optional = false)
    private Maestro idMaestro;

    public Cuenta() {
    }

    public Cuenta(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public Maestro getIdMaestro() {
        return idMaestro;
    }

    public void setIdMaestro(Maestro idMaestro) {
        this.idMaestro = idMaestro;
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
        if (!(object instanceof Cuenta)) {
            return false;
        }
        Cuenta other = (Cuenta) object;
        if ((this.usuario == null && other.usuario != null) || (this.usuario != null && !this.usuario.equals(other.usuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "aredespacio.Cuenta[ usuario=" + usuario + " ]";
    }
    
}
