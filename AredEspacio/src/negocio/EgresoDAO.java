package negocio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    public boolean registrarEgresoFb(Egreso egresoFb, String usuarioMaestro) {
        boolean egresoFbRegistradoExitosamente = true;
        if (egresoFb != null) {
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
            nuevoEgresoFb.setNombreGasto("Anuncio de Facebook");

            try {
                egresofbJpaController.create(nuevoEgresoFb);
            } catch (Exception ex) {
                egresoFbRegistradoExitosamente = false;
                Logger.getLogger(EgresoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            egresoFbRegistradoExitosamente = false;
        }

        return egresoFbRegistradoExitosamente;
    }

    @Override
    public boolean registrarEgresoVariado(Egreso egresoVariado) {
        boolean egresoRegistradoExitosamente = true;
        if (egresoVariado != null) {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
            EgresoJpaController egresoJpaController = new EgresoJpaController(entityManagerFactory);
            persistencia.Egreso nuevoEgreso = new persistencia.Egreso();

            nuevoEgreso.setCosto(egresoVariado.getCosto());
            nuevoEgreso.setFechaRegistro(egresoVariado.getFechaRegistro());
            nuevoEgreso.setDescripcion(egresoVariado.getDescripcion());
            nuevoEgreso.setNombreGasto(egresoVariado.getNombreGasto());

            try {
                egresoJpaController.create(nuevoEgreso);
            } catch (Exception ex) {
                egresoRegistradoExitosamente = false;
                Logger.getLogger(EgresoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            egresoRegistradoExitosamente = false;
        }
        return egresoRegistradoExitosamente;
    }

    @Override
    public List<persistencia.Egreso> obtenerTodosLosEgresos(int allo, int mes) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        EgresoJpaController egresoJpaController = new EgresoJpaController(entityManagerFactory);
        LocalDate localDate;
        List<persistencia.Egreso> listaEgresosTotales=egresoJpaController.findEgresoEntities();
        List<persistencia.Egreso> listaRegreso= new ArrayList();
        for(int i=0; i<listaEgresosTotales.size(); i++){
            localDate=Utileria.mostrarFecha(listaEgresosTotales.get(i).getFechaRegistro());       
            if((localDate.getYear()==allo) && (localDate.getMonthValue()==mes)){
                listaRegreso.add(listaEgresosTotales.get(i));
            }
        }
        
        return listaRegreso;
        
        
    }

}
