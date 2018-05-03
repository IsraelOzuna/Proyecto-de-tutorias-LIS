/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author iro19
 */
public class AlumnoJpaController implements Serializable {

    public AlumnoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Alumno alumno) {
        if (alumno.getGrupoCollection() == null) {
            alumno.setGrupoCollection(new ArrayList<Grupo>());
        }
        if (alumno.getPagoalumnoCollection() == null) {
            alumno.setPagoalumnoCollection(new ArrayList<Pagoalumno>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Grupo> attachedGrupoCollection = new ArrayList<Grupo>();
            for (Grupo grupoCollectionGrupoToAttach : alumno.getGrupoCollection()) {
                grupoCollectionGrupoToAttach = em.getReference(grupoCollectionGrupoToAttach.getClass(), grupoCollectionGrupoToAttach.getIdGrupo());
                attachedGrupoCollection.add(grupoCollectionGrupoToAttach);
            }
            alumno.setGrupoCollection(attachedGrupoCollection);
            Collection<Pagoalumno> attachedPagoalumnoCollection = new ArrayList<Pagoalumno>();
            for (Pagoalumno pagoalumnoCollectionPagoalumnoToAttach : alumno.getPagoalumnoCollection()) {
                pagoalumnoCollectionPagoalumnoToAttach = em.getReference(pagoalumnoCollectionPagoalumnoToAttach.getClass(), pagoalumnoCollectionPagoalumnoToAttach.getIdPago());
                attachedPagoalumnoCollection.add(pagoalumnoCollectionPagoalumnoToAttach);
            }
            alumno.setPagoalumnoCollection(attachedPagoalumnoCollection);
            em.persist(alumno);
            for (Grupo grupoCollectionGrupo : alumno.getGrupoCollection()) {
                grupoCollectionGrupo.getAlumnoCollection().add(alumno);
                grupoCollectionGrupo = em.merge(grupoCollectionGrupo);
            }
            for (Pagoalumno pagoalumnoCollectionPagoalumno : alumno.getPagoalumnoCollection()) {
                Alumno oldIdAlumnoOfPagoalumnoCollectionPagoalumno = pagoalumnoCollectionPagoalumno.getIdAlumno();
                pagoalumnoCollectionPagoalumno.setIdAlumno(alumno);
                pagoalumnoCollectionPagoalumno = em.merge(pagoalumnoCollectionPagoalumno);
                if (oldIdAlumnoOfPagoalumnoCollectionPagoalumno != null) {
                    oldIdAlumnoOfPagoalumnoCollectionPagoalumno.getPagoalumnoCollection().remove(pagoalumnoCollectionPagoalumno);
                    oldIdAlumnoOfPagoalumnoCollectionPagoalumno = em.merge(oldIdAlumnoOfPagoalumnoCollectionPagoalumno);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Alumno alumno) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alumno persistentAlumno = em.find(Alumno.class, alumno.getIdAlumno());
            Collection<Grupo> grupoCollectionOld = persistentAlumno.getGrupoCollection();
            Collection<Grupo> grupoCollectionNew = alumno.getGrupoCollection();
            Collection<Pagoalumno> pagoalumnoCollectionOld = persistentAlumno.getPagoalumnoCollection();
            Collection<Pagoalumno> pagoalumnoCollectionNew = alumno.getPagoalumnoCollection();
            Collection<Grupo> attachedGrupoCollectionNew = new ArrayList<Grupo>();
            for (Grupo grupoCollectionNewGrupoToAttach : grupoCollectionNew) {
                grupoCollectionNewGrupoToAttach = em.getReference(grupoCollectionNewGrupoToAttach.getClass(), grupoCollectionNewGrupoToAttach.getIdGrupo());
                attachedGrupoCollectionNew.add(grupoCollectionNewGrupoToAttach);
            }
            grupoCollectionNew = attachedGrupoCollectionNew;
            alumno.setGrupoCollection(grupoCollectionNew);
            Collection<Pagoalumno> attachedPagoalumnoCollectionNew = new ArrayList<Pagoalumno>();
            for (Pagoalumno pagoalumnoCollectionNewPagoalumnoToAttach : pagoalumnoCollectionNew) {
                pagoalumnoCollectionNewPagoalumnoToAttach = em.getReference(pagoalumnoCollectionNewPagoalumnoToAttach.getClass(), pagoalumnoCollectionNewPagoalumnoToAttach.getIdPago());
                attachedPagoalumnoCollectionNew.add(pagoalumnoCollectionNewPagoalumnoToAttach);
            }
            pagoalumnoCollectionNew = attachedPagoalumnoCollectionNew;
            alumno.setPagoalumnoCollection(pagoalumnoCollectionNew);
            alumno = em.merge(alumno);
            for (Grupo grupoCollectionOldGrupo : grupoCollectionOld) {
                if (!grupoCollectionNew.contains(grupoCollectionOldGrupo)) {
                    grupoCollectionOldGrupo.getAlumnoCollection().remove(alumno);
                    grupoCollectionOldGrupo = em.merge(grupoCollectionOldGrupo);
                }
            }
            for (Grupo grupoCollectionNewGrupo : grupoCollectionNew) {
                if (!grupoCollectionOld.contains(grupoCollectionNewGrupo)) {
                    grupoCollectionNewGrupo.getAlumnoCollection().add(alumno);
                    grupoCollectionNewGrupo = em.merge(grupoCollectionNewGrupo);
                }
            }
            for (Pagoalumno pagoalumnoCollectionOldPagoalumno : pagoalumnoCollectionOld) {
                if (!pagoalumnoCollectionNew.contains(pagoalumnoCollectionOldPagoalumno)) {
                    pagoalumnoCollectionOldPagoalumno.setIdAlumno(null);
                    pagoalumnoCollectionOldPagoalumno = em.merge(pagoalumnoCollectionOldPagoalumno);
                }
            }
            for (Pagoalumno pagoalumnoCollectionNewPagoalumno : pagoalumnoCollectionNew) {
                if (!pagoalumnoCollectionOld.contains(pagoalumnoCollectionNewPagoalumno)) {
                    Alumno oldIdAlumnoOfPagoalumnoCollectionNewPagoalumno = pagoalumnoCollectionNewPagoalumno.getIdAlumno();
                    pagoalumnoCollectionNewPagoalumno.setIdAlumno(alumno);
                    pagoalumnoCollectionNewPagoalumno = em.merge(pagoalumnoCollectionNewPagoalumno);
                    if (oldIdAlumnoOfPagoalumnoCollectionNewPagoalumno != null && !oldIdAlumnoOfPagoalumnoCollectionNewPagoalumno.equals(alumno)) {
                        oldIdAlumnoOfPagoalumnoCollectionNewPagoalumno.getPagoalumnoCollection().remove(pagoalumnoCollectionNewPagoalumno);
                        oldIdAlumnoOfPagoalumnoCollectionNewPagoalumno = em.merge(oldIdAlumnoOfPagoalumnoCollectionNewPagoalumno);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = alumno.getIdAlumno();
                if (findAlumno(id) == null) {
                    throw new NonexistentEntityException("The alumno with id " + id + " no longer exists.");
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
            Alumno alumno;
            try {
                alumno = em.getReference(Alumno.class, id);
                alumno.getIdAlumno();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The alumno with id " + id + " no longer exists.", enfe);
            }
            Collection<Grupo> grupoCollection = alumno.getGrupoCollection();
            for (Grupo grupoCollectionGrupo : grupoCollection) {
                grupoCollectionGrupo.getAlumnoCollection().remove(alumno);
                grupoCollectionGrupo = em.merge(grupoCollectionGrupo);
            }
            Collection<Pagoalumno> pagoalumnoCollection = alumno.getPagoalumnoCollection();
            for (Pagoalumno pagoalumnoCollectionPagoalumno : pagoalumnoCollection) {
                pagoalumnoCollectionPagoalumno.setIdAlumno(null);
                pagoalumnoCollectionPagoalumno = em.merge(pagoalumnoCollectionPagoalumno);
            }
            em.remove(alumno);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Alumno> findAlumnoEntities() {
        return findAlumnoEntities(true, -1, -1);
    }

    public List<Alumno> findAlumnoEntities(int maxResults, int firstResult) {
        return findAlumnoEntities(false, maxResults, firstResult);
    }

    private List<Alumno> findAlumnoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Alumno.class));
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

    public Alumno findAlumno(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Alumno.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlumnoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Alumno> rt = cq.from(Alumno.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Alumno> obtenerAlumnos(String nombre) {
        List<persistencia.Alumno> alumnos;
        String consulta = "Select a from Alumno a where a.nombre = :nombre";
        EntityManager em = getEntityManager();
        try {
            alumnos = em.createQuery(consulta).setParameter("nombre", nombre).getResultList();
        } finally {
            em.close();
        }
        return alumnos;
    }

    public List<Grupo> obtenerGruposAlumno(int idAlumno) {
        List<persistencia.Grupo> gruposAlumno = new ArrayList();
        String consulta = "select distinct a from Grupo a join a.alumnoCollection g where g.idAlumno = :idAlumno";
        EntityManager em = getEntityManager();
        try {
            gruposAlumno = em.createQuery(consulta).setParameter("idAlumno", idAlumno).getResultList();
        } finally {
            em.close();
        }
        return gruposAlumno;
    }
    
}
