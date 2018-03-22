/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import aredespacio.exceptions.IllegalOrphanException;
import aredespacio.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import persistencia.Cuenta;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import persistencia.Grupo;
import persistencia.Maestro;

/**
 *
 * @author Irdevelo
 */
public class MaestroJpaController implements Serializable {

    public MaestroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Maestro maestro) {
        if (maestro.getCuentaCollection() == null) {
            maestro.setCuentaCollection(new ArrayList<Cuenta>());
        }
        if (maestro.getGrupoCollection() == null) {
            maestro.setGrupoCollection(new ArrayList<Grupo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Cuenta> attachedCuentaCollection = new ArrayList<Cuenta>();
            for (Cuenta cuentaCollectionCuentaToAttach : maestro.getCuentaCollection()) {
                cuentaCollectionCuentaToAttach = em.getReference(cuentaCollectionCuentaToAttach.getClass(), cuentaCollectionCuentaToAttach.getUsuario());
                attachedCuentaCollection.add(cuentaCollectionCuentaToAttach);
            }
            maestro.setCuentaCollection(attachedCuentaCollection);
            Collection<Grupo> attachedGrupoCollection = new ArrayList<Grupo>();
            for (Grupo grupoCollectionGrupoToAttach : maestro.getGrupoCollection()) {
                grupoCollectionGrupoToAttach = em.getReference(grupoCollectionGrupoToAttach.getClass(), grupoCollectionGrupoToAttach.getNombreGrupo());
                attachedGrupoCollection.add(grupoCollectionGrupoToAttach);
            }
            maestro.setGrupoCollection(attachedGrupoCollection);
            em.persist(maestro);
            for (Cuenta cuentaCollectionCuenta : maestro.getCuentaCollection()) {
                Maestro oldIdMaestroOfCuentaCollectionCuenta = cuentaCollectionCuenta.getIdMaestro();
                cuentaCollectionCuenta.setIdMaestro(maestro);
                cuentaCollectionCuenta = em.merge(cuentaCollectionCuenta);
                if (oldIdMaestroOfCuentaCollectionCuenta != null) {
                    oldIdMaestroOfCuentaCollectionCuenta.getCuentaCollection().remove(cuentaCollectionCuenta);
                    oldIdMaestroOfCuentaCollectionCuenta = em.merge(oldIdMaestroOfCuentaCollectionCuenta);
                }
            }
            for (Grupo grupoCollectionGrupo : maestro.getGrupoCollection()) {
                Maestro oldIdMaestroOfGrupoCollectionGrupo = grupoCollectionGrupo.getIdMaestro();
                grupoCollectionGrupo.setIdMaestro(maestro);
                grupoCollectionGrupo = em.merge(grupoCollectionGrupo);
                if (oldIdMaestroOfGrupoCollectionGrupo != null) {
                    oldIdMaestroOfGrupoCollectionGrupo.getGrupoCollection().remove(grupoCollectionGrupo);
                    oldIdMaestroOfGrupoCollectionGrupo = em.merge(oldIdMaestroOfGrupoCollectionGrupo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Maestro maestro) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Maestro persistentMaestro = em.find(Maestro.class, maestro.getIdMaestro());
            Collection<Cuenta> cuentaCollectionOld = persistentMaestro.getCuentaCollection();
            Collection<Cuenta> cuentaCollectionNew = maestro.getCuentaCollection();
            Collection<Grupo> grupoCollectionOld = persistentMaestro.getGrupoCollection();
            Collection<Grupo> grupoCollectionNew = maestro.getGrupoCollection();
            List<String> illegalOrphanMessages = null;
            for (Cuenta cuentaCollectionOldCuenta : cuentaCollectionOld) {
                if (!cuentaCollectionNew.contains(cuentaCollectionOldCuenta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cuenta " + cuentaCollectionOldCuenta + " since its idMaestro field is not nullable.");
                }
            }
            for (Grupo grupoCollectionOldGrupo : grupoCollectionOld) {
                if (!grupoCollectionNew.contains(grupoCollectionOldGrupo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Grupo " + grupoCollectionOldGrupo + " since its idMaestro field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Cuenta> attachedCuentaCollectionNew = new ArrayList<Cuenta>();
            for (Cuenta cuentaCollectionNewCuentaToAttach : cuentaCollectionNew) {
                cuentaCollectionNewCuentaToAttach = em.getReference(cuentaCollectionNewCuentaToAttach.getClass(), cuentaCollectionNewCuentaToAttach.getUsuario());
                attachedCuentaCollectionNew.add(cuentaCollectionNewCuentaToAttach);
            }
            cuentaCollectionNew = attachedCuentaCollectionNew;
            maestro.setCuentaCollection(cuentaCollectionNew);
            Collection<Grupo> attachedGrupoCollectionNew = new ArrayList<Grupo>();
            for (Grupo grupoCollectionNewGrupoToAttach : grupoCollectionNew) {
                grupoCollectionNewGrupoToAttach = em.getReference(grupoCollectionNewGrupoToAttach.getClass(), grupoCollectionNewGrupoToAttach.getNombreGrupo());
                attachedGrupoCollectionNew.add(grupoCollectionNewGrupoToAttach);
            }
            grupoCollectionNew = attachedGrupoCollectionNew;
            maestro.setGrupoCollection(grupoCollectionNew);
            maestro = em.merge(maestro);
            for (Cuenta cuentaCollectionNewCuenta : cuentaCollectionNew) {
                if (!cuentaCollectionOld.contains(cuentaCollectionNewCuenta)) {
                    Maestro oldIdMaestroOfCuentaCollectionNewCuenta = cuentaCollectionNewCuenta.getIdMaestro();
                    cuentaCollectionNewCuenta.setIdMaestro(maestro);
                    cuentaCollectionNewCuenta = em.merge(cuentaCollectionNewCuenta);
                    if (oldIdMaestroOfCuentaCollectionNewCuenta != null && !oldIdMaestroOfCuentaCollectionNewCuenta.equals(maestro)) {
                        oldIdMaestroOfCuentaCollectionNewCuenta.getCuentaCollection().remove(cuentaCollectionNewCuenta);
                        oldIdMaestroOfCuentaCollectionNewCuenta = em.merge(oldIdMaestroOfCuentaCollectionNewCuenta);
                    }
                }
            }
            for (Grupo grupoCollectionNewGrupo : grupoCollectionNew) {
                if (!grupoCollectionOld.contains(grupoCollectionNewGrupo)) {
                    Maestro oldIdMaestroOfGrupoCollectionNewGrupo = grupoCollectionNewGrupo.getIdMaestro();
                    grupoCollectionNewGrupo.setIdMaestro(maestro);
                    grupoCollectionNewGrupo = em.merge(grupoCollectionNewGrupo);
                    if (oldIdMaestroOfGrupoCollectionNewGrupo != null && !oldIdMaestroOfGrupoCollectionNewGrupo.equals(maestro)) {
                        oldIdMaestroOfGrupoCollectionNewGrupo.getGrupoCollection().remove(grupoCollectionNewGrupo);
                        oldIdMaestroOfGrupoCollectionNewGrupo = em.merge(oldIdMaestroOfGrupoCollectionNewGrupo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = maestro.getIdMaestro();
                if (findMaestro(id) == null) {
                    throw new NonexistentEntityException("The maestro with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Maestro maestro;
            try {
                maestro = em.getReference(Maestro.class, id);
                maestro.getIdMaestro();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The maestro with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Cuenta> cuentaCollectionOrphanCheck = maestro.getCuentaCollection();
            for (Cuenta cuentaCollectionOrphanCheckCuenta : cuentaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Maestro (" + maestro + ") cannot be destroyed since the Cuenta " + cuentaCollectionOrphanCheckCuenta + " in its cuentaCollection field has a non-nullable idMaestro field.");
            }
            Collection<Grupo> grupoCollectionOrphanCheck = maestro.getGrupoCollection();
            for (Grupo grupoCollectionOrphanCheckGrupo : grupoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Maestro (" + maestro + ") cannot be destroyed since the Grupo " + grupoCollectionOrphanCheckGrupo + " in its grupoCollection field has a non-nullable idMaestro field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(maestro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Maestro> findMaestroEntities() {
        return findMaestroEntities(true, -1, -1);
    }

    public List<Maestro> findMaestroEntities(int maxResults, int firstResult) {
        return findMaestroEntities(false, maxResults, firstResult);
    }

    private List<Maestro> findMaestroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Maestro.class));
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

    public Maestro findMaestro(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Maestro.class, id);
        } finally {
            em.close();
        }
    }

    public int getMaestroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Maestro> rt = cq.from(Maestro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
