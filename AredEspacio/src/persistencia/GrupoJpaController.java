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

    public void create(Grupo grupo) {
        if (grupo.getAlumnoCollection() == null) {
            grupo.setAlumnoCollection(new ArrayList<Alumno>());
        }
        if (grupo.getPagoalumnoCollection() == null) {
            grupo.setPagoalumnoCollection(new ArrayList<Pagoalumno>());
        }
        if (grupo.getAsistenciaCollection() == null) {
            grupo.setAsistenciaCollection(new ArrayList<Asistencia>());
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
            Collection<Asistencia> attachedAsistenciaCollection = new ArrayList<Asistencia>();
            for (Asistencia asistenciaCollectionAsistenciaToAttach : grupo.getAsistenciaCollection()) {
                asistenciaCollectionAsistenciaToAttach = em.getReference(asistenciaCollectionAsistenciaToAttach.getClass(), asistenciaCollectionAsistenciaToAttach.getIdAsistencia());
                attachedAsistenciaCollection.add(asistenciaCollectionAsistenciaToAttach);
            }
            grupo.setAsistenciaCollection(attachedAsistenciaCollection);
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
                Grupo oldIdGrupoOfPagoalumnoCollectionPagoalumno = pagoalumnoCollectionPagoalumno.getIdGrupo();
                pagoalumnoCollectionPagoalumno.setIdGrupo(grupo);
                pagoalumnoCollectionPagoalumno = em.merge(pagoalumnoCollectionPagoalumno);
                if (oldIdGrupoOfPagoalumnoCollectionPagoalumno != null) {
                    oldIdGrupoOfPagoalumnoCollectionPagoalumno.getPagoalumnoCollection().remove(pagoalumnoCollectionPagoalumno);
                    oldIdGrupoOfPagoalumnoCollectionPagoalumno = em.merge(oldIdGrupoOfPagoalumnoCollectionPagoalumno);
                }
            }
            for (Asistencia asistenciaCollectionAsistencia : grupo.getAsistenciaCollection()) {
                Grupo oldIdGrupoOfAsistenciaCollectionAsistencia = asistenciaCollectionAsistencia.getIdGrupo();
                asistenciaCollectionAsistencia.setIdGrupo(grupo);
                asistenciaCollectionAsistencia = em.merge(asistenciaCollectionAsistencia);
                if (oldIdGrupoOfAsistenciaCollectionAsistencia != null) {
                    oldIdGrupoOfAsistenciaCollectionAsistencia.getAsistenciaCollection().remove(asistenciaCollectionAsistencia);
                    oldIdGrupoOfAsistenciaCollectionAsistencia = em.merge(oldIdGrupoOfAsistenciaCollectionAsistencia);
                }
            }
            em.getTransaction().commit();
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
            Grupo persistentGrupo = em.find(Grupo.class, grupo.getIdGrupo());
            Cuenta usuarioOld = persistentGrupo.getUsuario();
            Cuenta usuarioNew = grupo.getUsuario();
            Collection<Alumno> alumnoCollectionOld = persistentGrupo.getAlumnoCollection();
            Collection<Alumno> alumnoCollectionNew = grupo.getAlumnoCollection();
            Collection<Pagoalumno> pagoalumnoCollectionOld = persistentGrupo.getPagoalumnoCollection();
            Collection<Pagoalumno> pagoalumnoCollectionNew = grupo.getPagoalumnoCollection();
            Collection<Asistencia> asistenciaCollectionOld = persistentGrupo.getAsistenciaCollection();
            Collection<Asistencia> asistenciaCollectionNew = grupo.getAsistenciaCollection();
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
            Collection<Asistencia> attachedAsistenciaCollectionNew = new ArrayList<Asistencia>();
            for (Asistencia asistenciaCollectionNewAsistenciaToAttach : asistenciaCollectionNew) {
                asistenciaCollectionNewAsistenciaToAttach = em.getReference(asistenciaCollectionNewAsistenciaToAttach.getClass(), asistenciaCollectionNewAsistenciaToAttach.getIdAsistencia());
                attachedAsistenciaCollectionNew.add(asistenciaCollectionNewAsistenciaToAttach);
            }
            asistenciaCollectionNew = attachedAsistenciaCollectionNew;
            grupo.setAsistenciaCollection(asistenciaCollectionNew);
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
                    pagoalumnoCollectionOldPagoalumno.setIdGrupo(null);
                    pagoalumnoCollectionOldPagoalumno = em.merge(pagoalumnoCollectionOldPagoalumno);
                }
            }
            for (Pagoalumno pagoalumnoCollectionNewPagoalumno : pagoalumnoCollectionNew) {
                if (!pagoalumnoCollectionOld.contains(pagoalumnoCollectionNewPagoalumno)) {
                    Grupo oldIdGrupoOfPagoalumnoCollectionNewPagoalumno = pagoalumnoCollectionNewPagoalumno.getIdGrupo();
                    pagoalumnoCollectionNewPagoalumno.setIdGrupo(grupo);
                    pagoalumnoCollectionNewPagoalumno = em.merge(pagoalumnoCollectionNewPagoalumno);
                    if (oldIdGrupoOfPagoalumnoCollectionNewPagoalumno != null && !oldIdGrupoOfPagoalumnoCollectionNewPagoalumno.equals(grupo)) {
                        oldIdGrupoOfPagoalumnoCollectionNewPagoalumno.getPagoalumnoCollection().remove(pagoalumnoCollectionNewPagoalumno);
                        oldIdGrupoOfPagoalumnoCollectionNewPagoalumno = em.merge(oldIdGrupoOfPagoalumnoCollectionNewPagoalumno);
                    }
                }
            }
            for (Asistencia asistenciaCollectionOldAsistencia : asistenciaCollectionOld) {
                if (!asistenciaCollectionNew.contains(asistenciaCollectionOldAsistencia)) {
                    asistenciaCollectionOldAsistencia.setIdGrupo(null);
                    asistenciaCollectionOldAsistencia = em.merge(asistenciaCollectionOldAsistencia);
                }
            }
            for (Asistencia asistenciaCollectionNewAsistencia : asistenciaCollectionNew) {
                if (!asistenciaCollectionOld.contains(asistenciaCollectionNewAsistencia)) {
                    Grupo oldIdGrupoOfAsistenciaCollectionNewAsistencia = asistenciaCollectionNewAsistencia.getIdGrupo();
                    asistenciaCollectionNewAsistencia.setIdGrupo(grupo);
                    asistenciaCollectionNewAsistencia = em.merge(asistenciaCollectionNewAsistencia);
                    if (oldIdGrupoOfAsistenciaCollectionNewAsistencia != null && !oldIdGrupoOfAsistenciaCollectionNewAsistencia.equals(grupo)) {
                        oldIdGrupoOfAsistenciaCollectionNewAsistencia.getAsistenciaCollection().remove(asistenciaCollectionNewAsistencia);
                        oldIdGrupoOfAsistenciaCollectionNewAsistencia = em.merge(oldIdGrupoOfAsistenciaCollectionNewAsistencia);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = grupo.getIdGrupo();
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

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Grupo grupo;
            try {
                grupo = em.getReference(Grupo.class, id);
                grupo.getIdGrupo();
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
                pagoalumnoCollectionPagoalumno.setIdGrupo(null);
                pagoalumnoCollectionPagoalumno = em.merge(pagoalumnoCollectionPagoalumno);
            }
            Collection<Asistencia> asistenciaCollection = grupo.getAsistenciaCollection();
            for (Asistencia asistenciaCollectionAsistencia : asistenciaCollection) {
                asistenciaCollectionAsistencia.setIdGrupo(null);
                asistenciaCollectionAsistencia = em.merge(asistenciaCollectionAsistencia);
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

    public Grupo findGrupo(Integer id) {
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
    
    public List<Alumno> obtenerListaAlumnos(int idGrupo){
        List<persistencia.Alumno> listaAlumnos=null;
        String consulta =   "select distinct a from Alumno a join a.grupoCollection g where g.idGrupo = :idGrupo";
        EntityManager em = getEntityManager();
        try {
            listaAlumnos = em.createQuery(consulta).setParameter("idGrupo", idGrupo).getResultList();
        } finally {
            em.close();
        }
        return listaAlumnos;
    }
    
    public int encontrarGrupoAlumno(String nombreGrupo){
        int idGrupo = 0;
        String consulta = "select g.idGrupo from Grupo g where g.nombreGrupo = :nombreGrupo";
        EntityManager em = getEntityManager();
        try {
            idGrupo = (Integer)em.createQuery(consulta).setParameter("nombreGrupo", nombreGrupo).getSingleResult();
        } finally {
            em.close();
        }
        return idGrupo;
    }
    
}