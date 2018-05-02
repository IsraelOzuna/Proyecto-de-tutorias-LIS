/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author iro19
 */
public class PagomaestroJpaController implements Serializable {

    public PagomaestroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pagomaestro pagomaestro) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(pagomaestro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pagomaestro pagomaestro) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            pagomaestro = em.merge(pagomaestro);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pagomaestro.getIdPago();
                if (findPagomaestro(id) == null) {
                    throw new NonexistentEntityException("The pagomaestro with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pagomaestro pagomaestro;
            try {
                pagomaestro = em.getReference(Pagomaestro.class, id);
                pagomaestro.getIdPago();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pagomaestro with id " + id + " no longer exists.", enfe);
            }
            em.remove(pagomaestro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pagomaestro> findPagomaestroEntities() {
        return findPagomaestroEntities(true, -1, -1);
    }

    public List<Pagomaestro> findPagomaestroEntities(int maxResults, int firstResult) {
        return findPagomaestroEntities(false, maxResults, firstResult);
    }

    private List<Pagomaestro> findPagomaestroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pagomaestro.class));
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

    public Pagomaestro findPagomaestro(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pagomaestro.class, id);
        } finally {
            em.close();
        }
    }

    public int getPagomaestroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pagomaestro> rt = cq.from(Pagomaestro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
