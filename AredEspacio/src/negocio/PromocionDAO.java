/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import persistencia.Promocion;
import persistencia.PromocionJpaController;

/**
 *
 * @author Renato
 */
public class PromocionDAO implements IPromocion{

    @Override
    public boolean registrarPromocion(Promocion nuevaPromocion) {
        boolean promocionRegistradaExitosamente = true;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        PromocionJpaController promocionJpaController = new PromocionJpaController(entityManagerFactory);
        promocionJpaController.create(nuevaPromocion);
        promocionRegistradaExitosamente=true;
        return promocionRegistradaExitosamente;
    }

    @Override
    public List<Promocion> consultarPromociones() {
        List<Promocion> promociones = new ArrayList();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        PromocionJpaController promocionJpaController = new PromocionJpaController(entityManagerFactory);
        promociones=promocionJpaController.findPromocionEntities();
        return promociones;
    }
    
    
}
