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
 * @author Renato
 */
public class PagoalumnodireccionJpaController implements Serializable {

    public PagoalumnodireccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pagoalumnodireccion pagoalumnodireccion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumno idAlumno = pagoalumnodireccion.getIdAlumno();
            if (idAlumno != null) {
                idAlumno = em.getReference(idAlumno.getClass(), idAlumno.getIdAlumno());
                pagoalumnodireccion.setIdAlumno(idAlumno);
            }
            Cuenta usuario = pagoalumnodireccion.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getUsuario());
                pagoalumnodireccion.setUsuario(usuario);
            }
            Grupo idGrupo = pagoalumnodireccion.getIdGrupo();
            if (idGrupo != null) {
                idGrupo = em.getReference(idGrupo.getClass(), idGrupo.getIdGrupo());
                pagoalumnodireccion.setIdGrupo(idGrupo);
            }
            em.persist(pagoalumnodireccion);
            if (idAlumno != null) {
                idAlumno.getPagoalumnodireccionCollection().add(pagoalumnodireccion);
                idAlumno = em.merge(idAlumno);
            }
            if (usuario != null) {
                usuario.getPagoalumnodireccionCollection().add(pagoalumnodireccion);
                usuario = em.merge(usuario);
            }
            if (idGrupo != null) {
                idGrupo.getPagoalumnodireccionCollection().add(pagoalumnodireccion);
                idGrupo = em.merge(idGrupo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pagoalumnodireccion pagoalumnodireccion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pagoalumnodireccion persistentPagoalumnodireccion = em.find(Pagoalumnodireccion.class, pagoalumnodireccion.getIdPago());
            Alumno idAlumnoOld = persistentPagoalumnodireccion.getIdAlumno();
            Alumno idAlumnoNew = pagoalumnodireccion.getIdAlumno();
            Cuenta usuarioOld = persistentPagoalumnodireccion.getUsuario();
            Cuenta usuarioNew = pagoalumnodireccion.getUsuario();
            Grupo idGrupoOld = persistentPagoalumnodireccion.getIdGrupo();
            Grupo idGrupoNew = pagoalumnodireccion.getIdGrupo();
            if (idAlumnoNew != null) {
                idAlumnoNew = em.getReference(idAlumnoNew.getClass(), idAlumnoNew.getIdAlumno());
                pagoalumnodireccion.setIdAlumno(idAlumnoNew);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getUsuario());
                pagoalumnodireccion.setUsuario(usuarioNew);
            }
            if (idGrupoNew != null) {
                idGrupoNew = em.getReference(idGrupoNew.getClass(), idGrupoNew.getIdGrupo());
                pagoalumnodireccion.setIdGrupo(idGrupoNew);
            }
            pagoalumnodireccion = em.merge(pagoalumnodireccion);
            if (idAlumnoOld != null && !idAlumnoOld.equals(idAlumnoNew)) {
                idAlumnoOld.getPagoalumnodireccionCollection().remove(pagoalumnodireccion);
                idAlumnoOld = em.merge(idAlumnoOld);
            }
            if (idAlumnoNew != null && !idAlumnoNew.equals(idAlumnoOld)) {
                idAlumnoNew.getPagoalumnodireccionCollection().add(pagoalumnodireccion);
                idAlumnoNew = em.merge(idAlumnoNew);
            }
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getPagoalumnodireccionCollection().remove(pagoalumnodireccion);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getPagoalumnodireccionCollection().add(pagoalumnodireccion);
                usuarioNew = em.merge(usuarioNew);
            }
            if (idGrupoOld != null && !idGrupoOld.equals(idGrupoNew)) {
                idGrupoOld.getPagoalumnodireccionCollection().remove(pagoalumnodireccion);
                idGrupoOld = em.merge(idGrupoOld);
            }
            if (idGrupoNew != null && !idGrupoNew.equals(idGrupoOld)) {
                idGrupoNew.getPagoalumnodireccionCollection().add(pagoalumnodireccion);
                idGrupoNew = em.merge(idGrupoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pagoalumnodireccion.getIdPago();
                if (findPagoalumnodireccion(id) == null) {
                    throw new NonexistentEntityException("The pagoalumnodireccion with id " + id + " no longer exists.");
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
            Pagoalumnodireccion pagoalumnodireccion;
            try {
                pagoalumnodireccion = em.getReference(Pagoalumnodireccion.class, id);
                pagoalumnodireccion.getIdPago();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pagoalumnodireccion with id " + id + " no longer exists.", enfe);
            }
            Alumno idAlumno = pagoalumnodireccion.getIdAlumno();
            if (idAlumno != null) {
                idAlumno.getPagoalumnodireccionCollection().remove(pagoalumnodireccion);
                idAlumno = em.merge(idAlumno);
            }
            Cuenta usuario = pagoalumnodireccion.getUsuario();
            if (usuario != null) {
                usuario.getPagoalumnodireccionCollection().remove(pagoalumnodireccion);
                usuario = em.merge(usuario);
            }
            Grupo idGrupo = pagoalumnodireccion.getIdGrupo();
            if (idGrupo != null) {
                idGrupo.getPagoalumnodireccionCollection().remove(pagoalumnodireccion);
                idGrupo = em.merge(idGrupo);
            }
            em.remove(pagoalumnodireccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pagoalumnodireccion> findPagoalumnodireccionEntities() {
        return findPagoalumnodireccionEntities(true, -1, -1);
    }

    public List<Pagoalumnodireccion> findPagoalumnodireccionEntities(int maxResults, int firstResult) {
        return findPagoalumnodireccionEntities(false, maxResults, firstResult);
    }

    private List<Pagoalumnodireccion> findPagoalumnodireccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pagoalumnodireccion.class));
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

    public Pagoalumnodireccion findPagoalumnodireccion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pagoalumnodireccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getPagoalumnodireccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pagoalumnodireccion> rt = cq.from(Pagoalumnodireccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Pagoalumnodireccion>  obtenerPagosPorUsuario(Cuenta usuario){
        List<Pagoalumnodireccion> listaPagos;
        EntityManager em = getEntityManager();
        String consulta = "select a from Pagoalumnodireccion a where a.usuario =:usuario";
        try {
            listaPagos = em.createQuery(consulta).setParameter("usuario", usuario).getResultList();
        } finally {
            em.close();
        }
        
        return listaPagos;
    }
    
}
