package negocio;

import static java.time.temporal.TemporalQueries.localDate;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import persistencia.Renta;
import persistencia.RentaJpaController;
import persistencia.exceptions.NonexistentEntityException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

public class RentaDAO implements IRenta {

    @Override
    public List<persistencia.Renta> obtenerRentas() {
        List<persistencia.Renta> listaRentas = null;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        RentaJpaController rentaJpaController = new RentaJpaController(entityManagerFactory);

        persistencia.Renta nuevaRenta = new persistencia.Renta();

        try {

            listaRentas = rentaJpaController.findRentaEntities();

        } catch (Exception ex) {

            Logger.getLogger(RentaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaRentas;

    }

    @Override
    public List<Renta> obtenerRentasPorFecha(Date fecha) {
        List<persistencia.Renta> listaRentas = null;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        RentaJpaController rentaJpaController = new RentaJpaController(entityManagerFactory);

        persistencia.Renta nuevaRenta = new persistencia.Renta();

        try {

            listaRentas = rentaJpaController.obtenerRentasPorFecha(fecha);

        } catch (Exception ex) {

            Logger.getLogger(RentaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaRentas;

    }

    @Override
    public boolean registrarRenta(negocio.Renta renta) {
        boolean registroRentaExitoso = false;

        if (renta.getNombreCliente() != null) {
            registroRentaExitoso = true;
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
            RentaJpaController rentaJpaController = new RentaJpaController(entityManagerFactory);

            persistencia.Renta nuevaRenta = new persistencia.Renta();

            nuevaRenta.setCantidad(renta.getCantidad());
            nuevaRenta.setFecha(renta.getFecha());
            nuevaRenta.setHoraInicio(renta.getHoraInicio());
            nuevaRenta.setHoraFin(renta.getHoraFin());
            nuevaRenta.setNombreCliente(renta.getNombreCliente());

            try {
                rentaJpaController.create(nuevaRenta);
            } catch (Exception ex1) {
                registroRentaExitoso = false;
                Logger.getLogger(RentaDAO.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return registroRentaExitoso;
    }

    @Override
    public boolean eliminarRenta(int id) {
        boolean eliminacionCorrecta = true;

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        RentaJpaController rentaJpaController = new RentaJpaController(entityManagerFactory);

        try {
            rentaJpaController.destroy(id);
        } catch (NonexistentEntityException ex) {
            eliminacionCorrecta = false;
            Logger.getLogger(RentaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return eliminacionCorrecta;
    }

    @Override
    public boolean editarRenta(negocio.Renta renta, int idRenta) {
        boolean modificaciónCorrecta = true;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        RentaJpaController rentaJpaController = new RentaJpaController(entityManagerFactory);

        persistencia.Renta nuevaRenta = new persistencia.Renta();
        nuevaRenta.setCantidad(renta.getCantidad());
        nuevaRenta.setFecha(renta.getFecha());
        nuevaRenta.setHoraInicio(renta.getHoraInicio());
        nuevaRenta.setHoraFin(renta.getHoraFin());
        nuevaRenta.setNombreCliente(renta.getNombreCliente());
        nuevaRenta.setIdRenta(idRenta);

        try {
            rentaJpaController.edit(nuevaRenta);
        } catch (Exception ex) {
            modificaciónCorrecta = false;
            Logger.getLogger(RentaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return modificaciónCorrecta;
    }

    @Override
    public List<Renta> obtenerRentasPorMesAllo(int mes, int allo) {
        List<persistencia.Renta> listaRentas = null;
        List<persistencia.Renta> listaRegreso= new ArrayList();
        LocalDate localDate;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        RentaJpaController rentaJpaController = new RentaJpaController(entityManagerFactory);
        persistencia.Renta nuevaRenta = new persistencia.Renta();
        
        try {

            listaRentas = rentaJpaController.findRentaEntities();
            
            
            
            for(int i=0; i<listaRentas.size(); i++){
            localDate=Utileria.mostrarFecha(listaRentas.get(i).getFecha());       
            if((localDate.getYear()==allo) && (localDate.getMonthValue()==mes)){
                listaRegreso.add(listaRentas.get(i));
            }
        }

        } catch (Exception ex) {

            Logger.getLogger(RentaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaRegreso;
    }

}
