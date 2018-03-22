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
import persistencia.Grupo;
import persistencia.Maestro;

/**
 *
 * @author Irdevelo
 */
public class GrupoJpaController implements Serializable {

    public GrupoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Grupo grupo) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Maestro idMaestro = grupo.getIdMaestro();
            if (idMaestro != null) {
                idMaestro = em.getReference(idMaestro.getClass(), idMaestro.getIdMaestro());
                grupo.setIdMaestro(idMaestro);
            }
            em.persist(grupo);
            if (idMaestro != null) {
                idMaestro.getGrupoCollection().add(grupo);
                idMaestro = em.merge(idMaestro);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGrupo(grupo.getNombreGrupo()) != null) {
                throw new PreexistingEntityException("Grupo " + grupo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Grupo grupo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Grupo persistentGrupo = em.find(Grupo.class, grupo.getNombreGrupo());
            Maestro idMaestroOld = persistentGrupo.getIdMaestro();
            Maestro idMaestroNew = grupo.getIdMaestro();
            if (idMaestroNew != null) {
                idMaestroNew = em.getReference(idMaestroNew.getClass(), idMaestroNew.getIdMaestro());
                grupo.setIdMaestro(idMaestroNew);
            }
            grupo = em.merge(grupo);
            if (idMaestroOld != null && !idMaestroOld.equals(idMaestroNew)) {
                idMaestroOld.getGrupoCollection().remove(grupo);
                idMaestroOld = em.merge(idMaestroOld);
            }
            if (idMaestroNew != null && !idMaestroNew.equals(idMaestroOld)) {
                idMaestroNew.getGrupoCollection().add(grupo);
                idMaestroNew = em.merge(idMaestroNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = grupo.getNombreGrupo();
                if (findGrupo(id) == null) {
                    throw new NonexistentEntityException("The grupo with id " + id + " no longer exists.");
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
            Grupo grupo;
            try {
                grupo = em.getReference(Grupo.class, id);
                grupo.getNombreGrupo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The grupo with id " + id + " no longer exists.", enfe);
            }
            Maestro idMaestro = grupo.getIdMaestro();
            if (idMaestro != null) {
                idMaestro.getGrupoCollection().remove(grupo);
                idMaestro = em.merge(idMaestro);
            }
            em.remove(grupo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Grupo> findGrupoEntities() {
        return findGrupoEntities(true, -1, -1);
    }

    public List<Grupo> findGrupoEntities(int maxResults, int firstResult) {
        return findGrupoEntities(false, maxResults, firstResult);
    }

    private List<Grupo> findGrupoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Grupo.class));
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

    public Grupo findGrupo(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Grupo.class, id);
        } finally {
            em.close();
        }
    }

    public int getGrupoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Grupo> rt = cq.from(Grupo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
