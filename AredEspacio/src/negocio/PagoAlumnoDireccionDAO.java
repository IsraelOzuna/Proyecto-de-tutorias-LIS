/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import persistencia.Pagoalumnodireccion;
import persistencia.PagoalumnodireccionJpaController;


/**
 *
 * @author Renato
 */
public class PagoAlumnoDireccionDAO implements IPagoAlumnoDireccion{

    @Override
    public boolean registrarPagoDireccion(Pagoalumnodireccion pago) {
        boolean pagoRegistrado=false;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        PagoalumnodireccionJpaController pagoController;
        pagoController = new PagoalumnodireccionJpaController(entityManagerFactory);
        pagoController.create(pago);
        pagoRegistrado=true;
        return pagoRegistrado;
    }
    
}
