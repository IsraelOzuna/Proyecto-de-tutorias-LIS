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
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import persistencia.exceptions.IllegalOrphanException;
import persistencia.exceptions.NonexistentEntityException;
import persistencia.exceptions.PreexistingEntityException;

/**
 *
 * @author iro19
 */
public class MaestroJpaController implements Serializable {

    public MaestroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Maestro maestro) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Cuenta cuentaOrphanCheck = maestro.getCuenta();
        if (cuentaOrphanCheck != null) {
            Maestro oldMaestroOfCuenta = cuentaOrphanCheck.getMaestro();
            if (oldMaestroOfCuenta != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Cuenta " + cuentaOrphanCheck + " already has an item of type Maestro whose cuenta column cannot be null. Please make another selection for the cuenta field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuenta cuenta = maestro.getCuenta();
            if (cuenta != null) {
                cuenta = em.getReference(cuenta.getClass(), cuenta.getUsuario());
                maestro.setCuenta(cuenta);
            }
            em.persist(maestro);
            if (cuenta != null) {
                cuenta.setMaestro(maestro);
                cuenta = em.merge(cuenta);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMaestro(maestro.getUsuario()) != null) {
                throw new PreexistingEntityException("Maestro " + maestro + " already exists.", ex);
            }
            throw ex;
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
            Maestro persistentMaestro = em.find(Maestro.class, maestro.getUsuario());
            Cuenta cuentaOld = persistentMaestro.getCuenta();
            Cuenta cuentaNew = maestro.getCuenta();
            List<String> illegalOrphanMessages = null;
            if (cuentaNew != null && !cuentaNew.equals(cuentaOld)) {
                Maestro oldMaestroOfCuenta = cuentaNew.getMaestro();
                if (oldMaestroOfCuenta != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Cuenta " + cuentaNew + " already has an item of type Maestro whose cuenta column cannot be null. Please make another selection for the cuenta field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (cuentaNew != null) {
                cuentaNew = em.getReference(cuentaNew.getClass(), cuentaNew.getUsuario());
                maestro.setCuenta(cuentaNew);
            }
            maestro = em.merge(maestro);
            if (cuentaOld != null && !cuentaOld.equals(cuentaNew)) {
                cuentaOld.setMaestro(null);
                cuentaOld = em.merge(cuentaOld);
            }
            if (cuentaNew != null && !cuentaNew.equals(cuentaOld)) {
                cuentaNew.setMaestro(maestro);
                cuentaNew = em.merge(cuentaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = maestro.getUsuario();
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

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Maestro maestro;
            try {
                maestro = em.getReference(Maestro.class, id);
                maestro.getUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The maestro with id " + id + " no longer exists.", enfe);
            }
            Cuenta cuenta = maestro.getCuenta();
            if (cuenta != null) {
                cuenta.setMaestro(null);
                cuenta = em.merge(cuenta);
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

    public Maestro findMaestro(String id) {
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
    
    public List<Maestro> obtenerMaestros(String nombre) {        
        List<persistencia.Maestro> maestros;
        String consulta = "Select a from Maestro a where a.nombre = :nombre";
        EntityManager em = getEntityManager();
        try {
            maestros = em.createQuery(consulta).setParameter("nombre", nombre).getResultList();
        } finally {
            em.close();
        }        
        return maestros;
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
