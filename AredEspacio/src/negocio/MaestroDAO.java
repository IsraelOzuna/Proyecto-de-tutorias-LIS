/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import aredespacio.exceptions.PreexistingEntityException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import persistencia.MaestroJpaController;

/**
 *
 * @author Irdevelo
 */
public class MaestroDAO implements IMaestro {

    @Override
    public boolean registrarMaestro(Maestro maestro) {
        boolean maestroRegistradoExitosamente = true;

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        MaestroJpaController maestroJpaController = new MaestroJpaController(entityManagerFactory);

        persistencia.Maestro maestroNuevo = new persistencia.Maestro();

        maestroNuevo.setNombre(maestro.getNombre());
        maestroNuevo.setApellidos(maestro.getApellidos());
        maestroNuevo.setCorreoElectronico(maestro.getCorreoElectronico());
        maestroNuevo.setTelefono(maestro.getTelefono());
        maestroNuevo.setEstaActivo(maestro.getEstaActivo());
        maestroNuevo.setFechaCorte(maestro.getFechaCorte());
        maestroNuevo.setRutaFoto(maestro.getRutaFoto());
        maestroNuevo.setMensualidad(maestro.getMensualidad());
        maestroNuevo.setUsuario(maestro.getUsuario());

        try {
            maestroJpaController.create(maestroNuevo);

        } catch (Exception ex1) {
            maestroRegistradoExitosamente = false;
            Logger.getLogger(MaestroDAO.class.getName()).log(Level.SEVERE, null, ex1);
        }

        return true;
    }

}
