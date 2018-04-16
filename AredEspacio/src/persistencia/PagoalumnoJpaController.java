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
public class PagoalumnoJpaController implements Serializable {

    public PagoalumnoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pagoalumno pagoalumno) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumno idAlumno = pagoalumno.getIdAlumno();
            if (idAlumno != null) {
                idAlumno = em.getReference(idAlumno.getClass(), idAlumno.getIdAlumno());
                pagoalumno.setIdAlumno(idAlumno);
            }
            Grupo nombreGrupo = pagoalumno.getNombreGrupo();
            if (nombreGrupo != null) {
                nombreGrupo = em.getReference(nombreGrupo.getClass(), nombreGrupo.getNombreGrupo());
                pagoalumno.setNombreGrupo(nombreGrupo);
            }
            em.persist(pagoalumno);
            if (idAlumno != null) {
                idAlumno.getPagoalumnoCollection().add(pagoalumno);
                idAlumno = em.merge(idAlumno);
            }
            if (nombreGrupo != null) {
                nombreGrupo.getPagoalumnoCollection().add(pagoalumno);
                nombreGrupo = em.merge(nombreGrupo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pagoalumno pagoalumno) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pagoalumno persistentPagoalumno = em.find(Pagoalumno.class, pagoalumno.getIdPago());
            Alumno idAlumnoOld = persistentPagoalumno.getIdAlumno();
            Alumno idAlumnoNew = pagoalumno.getIdAlumno();
            Grupo nombreGrupoOld = persistentPagoalumno.getNombreGrupo();
            Grupo nombreGrupoNew = pagoalumno.getNombreGrupo();
            if (idAlumnoNew != null) {
                idAlumnoNew = em.getReference(idAlumnoNew.getClass(), idAlumnoNew.getIdAlumno());
                pagoalumno.setIdAlumno(idAlumnoNew);
            }
            if (nombreGrupoNew != null) {
                nombreGrupoNew = em.getReference(nombreGrupoNew.getClass(), nombreGrupoNew.getNombreGrupo());
                pagoalumno.setNombreGrupo(nombreGrupoNew);
            }
            pagoalumno = em.merge(pagoalumno);
            if (idAlumnoOld != null && !idAlumnoOld.equals(idAlumnoNew)) {
                idAlumnoOld.getPagoalumnoCollection().remove(pagoalumno);
                idAlumnoOld = em.merge(idAlumnoOld);
            }
            if (idAlumnoNew != null && !idAlumnoNew.equals(idAlumnoOld)) {
                idAlumnoNew.getPagoalumnoCollection().add(pagoalumno);
                idAlumnoNew = em.merge(idAlumnoNew);
            }
            if (nombreGrupoOld != null && !nombreGrupoOld.equals(nombreGrupoNew)) {
                nombreGrupoOld.getPagoalumnoCollection().remove(pagoalumno);
                nombreGrupoOld = em.merge(nombreGrupoOld);
            }
            if (nombreGrupoNew != null && !nombreGrupoNew.equals(nombreGrupoOld)) {
                nombreGrupoNew.getPagoalumnoCollection().add(pagoalumno);
                nombreGrupoNew = em.merge(nombreGrupoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pagoalumno.getIdPago();
                if (findPagoalumno(id) == null) {
                    throw new NonexistentEntityException("The pagoalumno with id " + id + " no longer exists.");
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
            Pagoalumno pagoalumno;
            try {
                pagoalumno = em.getReference(Pagoalumno.class, id);
                pagoalumno.getIdPago();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pagoalumno with id " + id + " no longer exists.", enfe);
            }
            Alumno idAlumno = pagoalumno.getIdAlumno();
            if (idAlumno != null) {
                idAlumno.getPagoalumnoCollection().remove(pagoalumno);
                idAlumno = em.merge(idAlumno);
            }
            Grupo nombreGrupo = pagoalumno.getNombreGrupo();
            if (nombreGrupo != null) {
                nombreGrupo.getPagoalumnoCollection().remove(pagoalumno);
                nombreGrupo = em.merge(nombreGrupo);
            }
            em.remove(pagoalumno);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pagoalumno> findPagoalumnoEntities() {
        return findPagoalumnoEntities(true, -1, -1);
    }

    public List<Pagoalumno> findPagoalumnoEntities(int maxResults, int firstResult) {
        return findPagoalumnoEntities(false, maxResults, firstResult);
    }

    private List<Pagoalumno> findPagoalumnoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pagoalumno.class));
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

    public Pagoalumno findPagoalumno(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pagoalumno.class, id);
        } finally {
            em.close();
        }
    }

    public int getPagoalumnoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pagoalumno> rt = cq.from(Pagoalumno.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
