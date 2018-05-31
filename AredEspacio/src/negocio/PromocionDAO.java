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
public class PromocionDAO implements IPromocion {

    String unidadPersistencia = "AredEspacioPU";

    public PromocionDAO() {

    }

    public PromocionDAO(String unidadPersistencia) {
        this.unidadPersistencia = unidadPersistencia;
    }

    @Override
    public boolean registrarPromocion(Promocion nuevaPromocion) {
        boolean promocionRegistradaExitosamente = true;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(unidadPersistencia, null);
        PromocionJpaController promocionJpaController = new PromocionJpaController(entityManagerFactory);
        promocionJpaController.create(nuevaPromocion);
        promocionRegistradaExitosamente = true;
        return promocionRegistradaExitosamente;
    }

    @Override
    public List<Promocion> consultarPromociones(String tipoPromocion) {
        List<Promocion> promociones = new ArrayList();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(unidadPersistencia, null);
        PromocionJpaController promocionJpaController = new PromocionJpaController(entityManagerFactory);
        switch (tipoPromocion) {
            case "Mensualidad":
                promociones = promocionJpaController.obtenerPromocionesMensualidad(tipoPromocion);
                break;
            case "Inscripcion":
                promociones = promocionJpaController.obtenerPromocionesInscripcion(tipoPromocion);
                break;
            default:
                promociones = promocionJpaController.findPromocionEntities();
                break;
        }
        return promociones;
    }

    @Override
    public Promocion adquirirPromocionPorNombre(String nombrePromocion) {
        List<Promocion> promociones = new ArrayList();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(unidadPersistencia, null);
        PromocionJpaController promocionJpaController = new PromocionJpaController(entityManagerFactory);
        promociones = promocionJpaController.findPromocionEntities();
        Promocion promocionAdquirida = new Promocion();
        for (int i = 0; i < promociones.size(); i++) {
            if (promociones.get(i).getNombrePromocion().equals(nombrePromocion)) {
                promocionAdquirida = promociones.get(i);
            }
        }
        return promocionAdquirida;
    }

    @Override
    public boolean promocionYaexistente(String nombrePromocion) {
        boolean existe = false;
        List<Promocion> promociones = new ArrayList();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(unidadPersistencia, null);
        PromocionJpaController promocionJpaController = new PromocionJpaController(entityManagerFactory);
        promociones = promocionJpaController.findPromocionEntities();
        Promocion promo = new Promocion();
        for (int i = 0; i < promociones.size(); i++) {
            if (promociones.get(i).getNombrePromocion().equals(nombrePromocion)) {
                existe = true;
                break;
            }
        }
        return existe;
    }

    @Override
    public List<persistencia.Promocion> obtenerPromociones() {
        List<persistencia.Promocion> lsitaPromociones;
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("AredEspacioPU", null);
        PromocionJpaController maestroJpaController = new PromocionJpaController(entityManagerFactory);
        lsitaPromociones = maestroJpaController.findPromocionEntities();
        return lsitaPromociones;
    }
}
