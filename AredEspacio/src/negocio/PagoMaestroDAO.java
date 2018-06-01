/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import persistencia.Pagoalumno;
import persistencia.PagoalumnoJpaController;
import persistencia.Pagomaestro;
import persistencia.PagomaestroJpaController;

/**
 *
 * @author Irdevelo
 */
public class PagoMaestroDAO implements IPagoMaestro {

    @Override
    public boolean registrarPagoMaestro(PagoMaestro pagoMaestro) {
        boolean registroPagoExitoso = false;

        if (pagoMaestro.getUsuario() != null) {
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

    @Override
    public List<Pagomaestro> obtenerPagosMaestros(int allo, int mes) {
        int alloRegistrado=0;
        int mesRegistrado=0;
        LocalDate localDate;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        PagomaestroJpaController pagomaestroController = new PagomaestroJpaController(entityManagerFactory);
        List<Pagomaestro> listaPagosTotales=pagomaestroController.findPagomaestroEntities();
        List<Pagomaestro> listaRegreso= new ArrayList();
        for(int i=0; i<listaPagosTotales.size(); i++){
            localDate=Utileria.mostrarFecha(listaPagosTotales.get(i).getFecha());       
            if((localDate.getYear()==allo) && (localDate.getMonthValue()==mes)){
                listaRegreso.add(listaPagosTotales.get(i));
            }
        }
        return listaRegreso;
    }
    


}



