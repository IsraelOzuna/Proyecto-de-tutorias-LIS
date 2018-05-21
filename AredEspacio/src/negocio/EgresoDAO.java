package negocio;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import persistencia.EgresoJpaController;
import persistencia.MaestroJpaController;
import persistencia.Maestro;

/**
 *
 * @author Israel Reyes Ozuna
 */
public class EgresoDAO implements IEgreso {

    @Override
    public boolean registrarRecursoFb(Egreso egresoFb, String usuarioMaestro) {
        boolean egresoFbRegistradoExitosamente = true;
        if(egresoFb != null){
            Maestro maestro;            
                        
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
            EgresoJpaController egresofbJpaController = new EgresoJpaController(entityManagerFactory);
            MaestroJpaController encontrarMaestro = new MaestroJpaController(entityManagerFactory);
            
            maestro = encontrarMaestro.findMaestro(usuarioMaestro);
            
            persistencia.Egreso nuevoEgresoFb = new persistencia.Egreso();
            
            nuevoEgresoFb.setCosto(egresoFb.getCosto());
            nuevoEgresoFb.setDescripcion(egresoFb.getDescripcion());
            nuevoEgresoFb.setFechaFin(egresoFb.getFechaFinPublicacion());
            nuevoEgresoFb.setFechaPublicacion(egresoFb.getFechaInicioPublicacion());
            nuevoEgresoFb.setFechaRegistro(egresoFb.getFechaRegistro());
            nuevoEgresoFb.setLink(egresoFb.getUrl());
            nuevoEgresoFb.setCreador(maestro);
            
            try{
            egresofbJpaController.create(nuevoEgresoFb);
            }catch(Exception ex){
                egresoFbRegistradoExitosamente = false;
                Logger.getLogger(EgresoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            egresoFbRegistradoExitosamente = false;
        }

        return egresoFbRegistradoExitosamente;
    }

}
