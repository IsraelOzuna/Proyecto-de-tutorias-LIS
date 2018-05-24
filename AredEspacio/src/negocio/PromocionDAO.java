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
    String unidadPersistencia="AredEspacioPU";
    
    public PromocionDAO (){
        
    }
    
    public PromocionDAO (String unidadPersistencia){
        this.unidadPersistencia=unidadPersistencia;
    }
    @Override
    public boolean registrarPromocion(Promocion nuevaPromocion) {
        boolean promocionRegistradaExitosamente = true;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(unidadPersistencia, null);
        PromocionJpaController promocionJpaController = new PromocionJpaController(entityManagerFactory);
        promocionJpaController.create(nuevaPromocion);
        promocionRegistradaExitosamente=true;
        return promocionRegistradaExitosamente;
    }

    @Override
    public List<Promocion> consultarPromociones() {
        List<Promocion> promociones = new ArrayList();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(unidadPersistencia, null);
        PromocionJpaController promocionJpaController = new PromocionJpaController(entityManagerFactory);
        promociones=promocionJpaController.findPromocionEntities();
        return promociones;
    }

    @Override
    public Promocion adquirirPromocionPorNombre(String nombrePromocion) {
        List<Promocion> promociones = new ArrayList();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(unidadPersistencia, null);
        PromocionJpaController promocionJpaController = new PromocionJpaController(entityManagerFactory);
        promociones=promocionJpaController.findPromocionEntities();
        Promocion promocionAdquirida= new Promocion();
        for(int i = 0; i<promociones.size(); i++){
            if(promociones.get(i).getNombrePromocion().equals(nombrePromocion)){
                promocionAdquirida=promociones.get(i);
            }   
        }
        return promocionAdquirida;
    }
    
    
    
}
