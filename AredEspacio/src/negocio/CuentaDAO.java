/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import persistencia.CuentaJpaController;

/**
 *
 * @author Irdevelo
 */
public class CuentaDAO implements ICuenta {

    @Override
    public boolean crearCuenta(Cuenta cuenta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean verificarNombreUsuarioRepetido(String nombreUsuario) {
        boolean usuarioRepetido = false;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        CuentaJpaController cuentaJpaController = new CuentaJpaController(entityManagerFactory);

        try {
            persistencia.Cuenta cuenta = cuentaJpaController.findCuenta(nombreUsuario);
            if (cuenta != null) {
                usuarioRepetido = true;
            }
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(Persistence.class.getName()).log(Level.SEVERE, null, ex);
        }

        return usuarioRepetido;
    }

}
