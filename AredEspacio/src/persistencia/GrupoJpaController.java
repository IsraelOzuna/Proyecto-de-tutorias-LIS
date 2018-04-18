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
import persistencia.exceptions.PreexistingEntityException;

/**
 *
 * @author Renato
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
        if (grupo.getAlumnoCollection() == null) {
            grupo.setAlumnoCollection(new ArrayList<Alumno>());
        }
        if (grupo.getPagoalumnoCollection() == null) {
            grupo.setPagoalumnoCollection(new ArrayList<Pagoalumno>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuenta usuario = grupo.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getUsuario());
                grupo.setUsuario(usuario);
            }
            Collection<Alumno> attachedAlumnoCollection = new ArrayList<Alumno>();
            for (Alumno alumnoCollectionAlumnoToAttach : grupo.getAlumnoCollection()) {
                alumnoCollectionAlumnoToAttach = em.getReference(alumnoCollectionAlumnoToAttach.getClass(), alumnoCollectionAlumnoToAttach.getIdAlumno());
                attachedAlumnoCollection.add(alumnoCollectionAlumnoToAttach);
            }
            grupo.setAlumnoCollection(attachedAlumnoCollection);
            Collection<Pagoalumno> attachedPagoalumnoCollection = new ArrayList<Pagoalumno>();
            for (Pagoalumno pagoalumnoCollectionPagoalumnoToAttach : grupo.getPagoalumnoCollection()) {
                pagoalumnoCollectionPagoalumnoToAttach = em.getReference(pagoalumnoCollectionPagoalumnoToAttach.getClass(), pagoalumnoCollectionPagoalumnoToAttach.getIdPago());
                attachedPagoalumnoCollection.add(pagoalumnoCollectionPagoalumnoToAttach);
            }
            grupo.setPagoalumnoCollection(attachedPagoalumnoCollection);
            em.persist(grupo);
            if (usuario != null) {
                usuario.getGrupoCollection().add(grupo);
                usuario = em.merge(usuario);
            }
            for (Alumno alumnoCollectionAlumno : grupo.getAlumnoCollection()) {
                alumnoCollectionAlumno.getGrupoCollection().add(grupo);
                alumnoCollectionAlumno = em.merge(alumnoCollectionAlumno);
            }
            for (Pagoalumno pagoalumnoCollectionPagoalumno : grupo.getPagoalumnoCollection()) {
                Grupo oldNombreGrupoOfPagoalumnoCollectionPagoalumno = pagoalumnoCollectionPagoalumno.getNombreGrupo();
                pagoalumnoCollectionPagoalumno.setNombreGrupo(grupo);
                pagoalumnoCollectionPagoalumno = em.merge(pagoalumnoCollectionPagoalumno);
                if (oldNombreGrupoOfPagoalumnoCollectionPagoalumno != null) {
                    oldNombreGrupoOfPagoalumnoCollectionPagoalumno.getPagoalumnoCollection().remove(pagoalumnoCollectionPagoalumno);
                    oldNombreGrupoOfPagoalumnoCollectionPagoalumno = em.merge(oldNombreGrupoOfPagoalumnoCollectionPagoalumno);
                }
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
            Cuenta usuarioOld = persistentGrupo.getUsuario();
            Cuenta usuarioNew = grupo.getUsuario();
            Collection<Alumno> alumnoCollectionOld = persistentGrupo.getAlumnoCollection();
            Collection<Alumno> alumnoCollectionNew = grupo.getAlumnoCollection();
            Collection<Pagoalumno> pagoalumnoCollectionOld = persistentGrupo.getPagoalumnoCollection();
            Collection<Pagoalumno> pagoalumnoCollectionNew = grupo.getPagoalumnoCollection();
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getUsuario());
                grupo.setUsuario(usuarioNew);
            }
            Collection<Alumno> attachedAlumnoCollectionNew = new ArrayList<Alumno>();
            for (Alumno alumnoCollectionNewAlumnoToAttach : alumnoCollectionNew) {
                alumnoCollectionNewAlumnoToAttach = em.getReference(alumnoCollectionNewAlumnoToAttach.getClass(), alumnoCollectionNewAlumnoToAttach.getIdAlumno());
                attachedAlumnoCollectionNew.add(alumnoCollectionNewAlumnoToAttach);
            }
            alumnoCollectionNew = attachedAlumnoCollectionNew;
            grupo.setAlumnoCollection(alumnoCollectionNew);
            Collection<Pagoalumno> attachedPagoalumnoCollectionNew = new ArrayList<Pagoalumno>();
            for (Pagoalumno pagoalumnoCollectionNewPagoalumnoToAttach : pagoalumnoCollectionNew) {
                pagoalumnoCollectionNewPagoalumnoToAttach = em.getReference(pagoalumnoCollectionNewPagoalumnoToAttach.getClass(), pagoalumnoCollectionNewPagoalumnoToAttach.getIdPago());
                attachedPagoalumnoCollectionNew.add(pagoalumnoCollectionNewPagoalumnoToAttach);
            }
            pagoalumnoCollectionNew = attachedPagoalumnoCollectionNew;
            grupo.setPagoalumnoCollection(pagoalumnoCollectionNew);
            grupo = em.merge(grupo);
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getGrupoCollection().remove(grupo);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getGrupoCollection().add(grupo);
                usuarioNew = em.merge(usuarioNew);
            }
            for (Alumno alumnoCollectionOldAlumno : alumnoCollectionOld) {
                if (!alumnoCollectionNew.contains(alumnoCollectionOldAlumno)) {
                    alumnoCollectionOldAlumno.getGrupoCollection().remove(grupo);
                    alumnoCollectionOldAlumno = em.merge(alumnoCollectionOldAlumno);
                }
            }
            for (Alumno alumnoCollectionNewAlumno : alumnoCollectionNew) {
                if (!alumnoCollectionOld.contains(alumnoCollectionNewAlumno)) {
                    alumnoCollectionNewAlumno.getGrupoCollection().add(grupo);
                    alumnoCollectionNewAlumno = em.merge(alumnoCollectionNewAlumno);
                }
            }
            for (Pagoalumno pagoalumnoCollectionOldPagoalumno : pagoalumnoCollectionOld) {
                if (!pagoalumnoCollectionNew.contains(pagoalumnoCollectionOldPagoalumno)) {
                    pagoalumnoCollectionOldPagoalumno.setNombreGrupo(null);
                    pagoalumnoCollectionOldPagoalumno = em.merge(pagoalumnoCollectionOldPagoalumno);
                }
            }
            for (Pagoalumno pagoalumnoCollectionNewPagoalumno : pagoalumnoCollectionNew) {
                if (!pagoalumnoCollectionOld.contains(pagoalumnoCollectionNewPagoalumno)) {
                    Grupo oldNombreGrupoOfPagoalumnoCollectionNewPagoalumno = pagoalumnoCollectionNewPagoalumno.getNombreGrupo();
                    pagoalumnoCollectionNewPagoalumno.setNombreGrupo(grupo);
                    pagoalumnoCollectionNewPagoalumno = em.merge(pagoalumnoCollectionNewPagoalumno);
                    if (oldNombreGrupoOfPagoalumnoCollectionNewPagoalumno != null && !oldNombreGrupoOfPagoalumnoCollectionNewPagoalumno.equals(grupo)) {
                        oldNombreGrupoOfPagoalumnoCollectionNewPagoalumno.getPagoalumnoCollection().remove(pagoalumnoCollectionNewPagoalumno);
                        oldNombreGrupoOfPagoalumnoCollectionNewPagoalumno = em.merge(oldNombreGrupoOfPagoalumnoCollectionNewPagoalumno);
                    }
                }
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
            Cuenta usuario = grupo.getUsuario();
            if (usuario != null) {
                usuario.getGrupoCollection().remove(grupo);
                usuario = em.merge(usuario);
            }
            Collection<Alumno> alumnoCollection = grupo.getAlumnoCollection();
            for (Alumno alumnoCollectionAlumno : alumnoCollection) {
                alumnoCollectionAlumno.getGrupoCollection().remove(grupo);
                alumnoCollectionAlumno = em.merge(alumnoCollectionAlumno);
            }
            Collection<Pagoalumno> pagoalumnoCollection = grupo.getPagoalumnoCollection();
            for (Pagoalumno pagoalumnoCollectionPagoalumno : pagoalumnoCollection) {
                pagoalumnoCollectionPagoalumno.setNombreGrupo(null);
                pagoalumnoCollectionPagoalumno = em.merge(pagoalumnoCollectionPagoalumno);
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
    
    
    public List<Alumno> obtenerListaAlumnos(String nombreGrupo){
        List<persistencia.Alumno> listaAlumnos=null;
        String consulta =   "select distinct a from Alumno a join a.grupoCollection g where g.nombreGrupo = :nombreGrupo";
        EntityManager em = getEntityManager();
        try {
            listaAlumnos = em.createQuery(consulta).setParameter("nombreGrupo", nombreGrupo).getResultList();
        } finally {
            em.close();
        }
        return listaAlumnos;
    }
    
}
