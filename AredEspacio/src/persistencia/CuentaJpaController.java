/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import aredespacio.exceptions.IllegalOrphanException;
import aredespacio.exceptions.NonexistentEntityException;
import aredespacio.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import persistencia.Maestro;
import persistencia.Grupo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import persistencia.Cuenta;

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
        if (cuenta.getGrupoCollection() == null) {
            cuenta.setGrupoCollection(new ArrayList<Grupo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Maestro maestro = cuenta.getMaestro();
            if (maestro != null) {
                maestro = em.getReference(maestro.getClass(), maestro.getUsuario());
                cuenta.setMaestro(maestro);
            }
            Collection<Grupo> attachedGrupoCollection = new ArrayList<Grupo>();
            for (Grupo grupoCollectionGrupoToAttach : cuenta.getGrupoCollection()) {
                grupoCollectionGrupoToAttach = em.getReference(grupoCollectionGrupoToAttach.getClass(), grupoCollectionGrupoToAttach.getNombreGrupo());
                attachedGrupoCollection.add(grupoCollectionGrupoToAttach);
            }
            cuenta.setGrupoCollection(attachedGrupoCollection);
            em.persist(cuenta);
            if (maestro != null) {
                Cuenta oldCuentaOfMaestro = maestro.getCuenta();
                if (oldCuentaOfMaestro != null) {
                    oldCuentaOfMaestro.setMaestro(null);
                    oldCuentaOfMaestro = em.merge(oldCuentaOfMaestro);
                }
                maestro.setCuenta(cuenta);
                maestro = em.merge(maestro);
            }
            for (Grupo grupoCollectionGrupo : cuenta.getGrupoCollection()) {
                Cuenta oldUsuarioOfGrupoCollectionGrupo = grupoCollectionGrupo.getUsuario();
                grupoCollectionGrupo.setUsuario(cuenta);
                grupoCollectionGrupo = em.merge(grupoCollectionGrupo);
                if (oldUsuarioOfGrupoCollectionGrupo != null) {
                    oldUsuarioOfGrupoCollectionGrupo.getGrupoCollection().remove(grupoCollectionGrupo);
                    oldUsuarioOfGrupoCollectionGrupo = em.merge(oldUsuarioOfGrupoCollectionGrupo);
                }
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

    public void edit(Cuenta cuenta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuenta persistentCuenta = em.find(Cuenta.class, cuenta.getUsuario());
            Maestro maestroOld = persistentCuenta.getMaestro();
            Maestro maestroNew = cuenta.getMaestro();
            Collection<Grupo> grupoCollectionOld = persistentCuenta.getGrupoCollection();
            Collection<Grupo> grupoCollectionNew = cuenta.getGrupoCollection();
            List<String> illegalOrphanMessages = null;
            if (maestroOld != null && !maestroOld.equals(maestroNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Maestro " + maestroOld + " since its cuenta field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (maestroNew != null) {
                maestroNew = em.getReference(maestroNew.getClass(), maestroNew.getUsuario());
                cuenta.setMaestro(maestroNew);
            }
            Collection<Grupo> attachedGrupoCollectionNew = new ArrayList<Grupo>();
            for (Grupo grupoCollectionNewGrupoToAttach : grupoCollectionNew) {
                grupoCollectionNewGrupoToAttach = em.getReference(grupoCollectionNewGrupoToAttach.getClass(), grupoCollectionNewGrupoToAttach.getNombreGrupo());
                attachedGrupoCollectionNew.add(grupoCollectionNewGrupoToAttach);
            }
            grupoCollectionNew = attachedGrupoCollectionNew;
            cuenta.setGrupoCollection(grupoCollectionNew);
            cuenta = em.merge(cuenta);
            if (maestroNew != null && !maestroNew.equals(maestroOld)) {
                Cuenta oldCuentaOfMaestro = maestroNew.getCuenta();
                if (oldCuentaOfMaestro != null) {
                    oldCuentaOfMaestro.setMaestro(null);
                    oldCuentaOfMaestro = em.merge(oldCuentaOfMaestro);
                }
                maestroNew.setCuenta(cuenta);
                maestroNew = em.merge(maestroNew);
            }
            for (Grupo grupoCollectionOldGrupo : grupoCollectionOld) {
                if (!grupoCollectionNew.contains(grupoCollectionOldGrupo)) {
                    grupoCollectionOldGrupo.setUsuario(null);
                    grupoCollectionOldGrupo = em.merge(grupoCollectionOldGrupo);
                }
            }
            for (Grupo grupoCollectionNewGrupo : grupoCollectionNew) {
                if (!grupoCollectionOld.contains(grupoCollectionNewGrupo)) {
                    Cuenta oldUsuarioOfGrupoCollectionNewGrupo = grupoCollectionNewGrupo.getUsuario();
                    grupoCollectionNewGrupo.setUsuario(cuenta);
                    grupoCollectionNewGrupo = em.merge(grupoCollectionNewGrupo);
                    if (oldUsuarioOfGrupoCollectionNewGrupo != null && !oldUsuarioOfGrupoCollectionNewGrupo.equals(cuenta)) {
                        oldUsuarioOfGrupoCollectionNewGrupo.getGrupoCollection().remove(grupoCollectionNewGrupo);
                        oldUsuarioOfGrupoCollectionNewGrupo = em.merge(oldUsuarioOfGrupoCollectionNewGrupo);
                    }
                }
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

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            Maestro maestroOrphanCheck = cuenta.getMaestro();
            if (maestroOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cuenta (" + cuenta + ") cannot be destroyed since the Maestro " + maestroOrphanCheck + " in its maestro field has a non-nullable cuenta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Grupo> grupoCollection = cuenta.getGrupoCollection();
            for (Grupo grupoCollectionGrupo : grupoCollection) {
                grupoCollectionGrupo.setUsuario(null);
                grupoCollectionGrupo = em.merge(grupoCollectionGrupo);
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
