/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import aredespacio.exceptions.NonexistentEntityException;
import aredespacio.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import persistencia.Cuenta;
import persistencia.Maestro;

/**
 *
 * @author Irdevelo
 */
public class CuentaJpaController implements Serializable {

    public CuentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cuenta cuenta) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Maestro idMaestro = cuenta.getIdMaestro();
            if (idMaestro != null) {
                idMaestro = em.getReference(idMaestro.getClass(), idMaestro.getIdMaestro());
                cuenta.setIdMaestro(idMaestro);
            }
            em.persist(cuenta);
            if (idMaestro != null) {
                idMaestro.getCuentaCollection().add(cuenta);
                idMaestro = em.merge(idMaestro);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCuenta(cuenta.getUsuario()) != null) {
                throw new PreexistingEntityException("Cuenta " + cuenta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cuenta cuenta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuenta persistentCuenta = em.find(Cuenta.class, cuenta.getUsuario());
            Maestro idMaestroOld = persistentCuenta.getIdMaestro();
            Maestro idMaestroNew = cuenta.getIdMaestro();
            if (idMaestroNew != null) {
                idMaestroNew = em.getReference(idMaestroNew.getClass(), idMaestroNew.getIdMaestro());
                cuenta.setIdMaestro(idMaestroNew);
            }
            cuenta = em.merge(cuenta);
            if (idMaestroOld != null && !idMaestroOld.equals(idMaestroNew)) {
                idMaestroOld.getCuentaCollection().remove(cuenta);
                idMaestroOld = em.merge(idMaestroOld);
            }
            if (idMaestroNew != null && !idMaestroNew.equals(idMaestroOld)) {
                idMaestroNew.getCuentaCollection().add(cuenta);
                idMaestroNew = em.merge(idMaestroNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = cuenta.getUsuario();
                if (findCuenta(id) == null) {
                    throw new NonexistentEntityException("The cuenta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuenta cuenta;
            try {
                cuenta = em.getReference(Cuenta.class, id);
                cuenta.getUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuenta with id " + id + " no longer exists.", enfe);
            }
            Maestro idMaestro = cuenta.getIdMaestro();
            if (idMaestro != null) {
                idMaestro.getCuentaCollection().remove(cuenta);
                idMaestro = em.merge(idMaestro);
            }
            em.remove(cuenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cuenta> findCuentaEntities() {
        return findCuentaEntities(true, -1, -1);
    }

    public List<Cuenta> findCuentaEntities(int maxResults, int firstResult) {
        return findCuentaEntities(false, maxResults, firstResult);
    }

    private List<Cuenta> findCuentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cuenta.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Cuenta findCuenta(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cuenta.class, id);
        } finally {
            em.close();
        }
    }

    public int getCuentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cuenta> rt = cq.from(Cuenta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
