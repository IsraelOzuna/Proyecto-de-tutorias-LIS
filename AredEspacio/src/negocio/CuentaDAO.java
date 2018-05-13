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
        boolean cuentaRegistradaExitosamente = true;

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        CuentaJpaController cuentaJpaController = new CuentaJpaController(entityManagerFactory);

        persistencia.Cuenta cuentaNueva = new persistencia.Cuenta();

        cuentaNueva.setContrasena(cuenta.getContraseña());
        cuentaNueva.setTipoCuenta(cuenta.getTipoCuenta());
        cuentaNueva.setUsuario(cuenta.getUsuario());

        try {
            cuentaJpaController.create(cuentaNueva);

        } catch (Exception ex) {
            cuentaRegistradaExitosamente = false;
            Logger.getLogger(CuentaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return cuentaRegistradaExitosamente;
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

    @Override
    public String iniciarSesion(String nombreUsuario, String contrasena) {        
        String tipoInicioSesion = "";

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        CuentaJpaController cuentaJpaController = new CuentaJpaController(entityManagerFactory);

        try {
            persistencia.Cuenta cuenta = cuentaJpaController.findCuenta(nombreUsuario);
            if (cuenta != null) {
                if (cuenta.getTipoCuenta().equals("Director") && cuenta.getContrasena().equals(contrasena)) {
                    tipoInicioSesion = "Director";                    
                } else if (cuenta.getTipoCuenta().equals("Maestro") && cuenta.getContrasena().equals(contrasena)) {
                    tipoInicioSesion = "Maestro";                 
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Persistence.class.getName()).log(Level.SEVERE, null, ex);
        }

        return tipoInicioSesion;
    }

}
