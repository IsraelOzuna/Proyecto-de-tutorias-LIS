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
import persistencia.PagomaestroJpaController;

/**
 *
 * @author Irdevelo
 */
public class PagoMaestroDAO implements IPagoMaestro {

    @Override
    public boolean registrarPagoMaestro(PagoMaestro pagoMaestro) {
        boolean registroPagoExitoso = false;

        if (pagoMaestro != null) {
            registroPagoExitoso = true;
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);

            PagomaestroJpaController pagomaestroController = new PagomaestroJpaController(entityManagerFactory);

            persistencia.Pagomaestro pagoNuevo = new persistencia.Pagomaestro();
            pagoNuevo.setFecha(pagoMaestro.getFecha());
            pagoNuevo.setCantidad(pagoMaestro.getCantidad());
            pagoNuevo.setUsuario(pagoMaestro.getUsuario());

            try {
                pagomaestroController.create(pagoNuevo);

            } catch (Exception ex) {
                registroPagoExitoso = false;
                Logger.getLogger(PagoMaestroDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return registroPagoExitoso;
    }

}
