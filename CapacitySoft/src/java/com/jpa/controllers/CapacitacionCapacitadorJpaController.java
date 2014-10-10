/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jpa.controllers;

import com.jpa.controllers.exceptions.NonexistentEntityException;
import com.jpa.controllers.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.jpa.entities.Capacitador;
import com.jpa.entities.Capacitacion;
import com.jpa.entities.CapacitacionCapacitador;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

import javax.annotation.Resource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

/**
 *
 * @author ERIKA
 */
public class CapacitacionCapacitadorJpaController implements Serializable {

     // Modificar Constructor
    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "TesisCapacitySoftPU")
    private EntityManagerFactory emf = null;
    InitialContext ic = new InitialContext();

    public  CapacitacionCapacitadorJpaController() throws NamingException {
        utx = (UserTransaction) ic.lookup("java:comp/UserTransaction");
        emf = Persistence
                .createEntityManagerFactory("TesisCapacitySoftPU");
    }
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CapacitacionCapacitador capacitacionCapacitador) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Capacitador capacitadorid = capacitacionCapacitador.getCapacitadorid();
            if (capacitadorid != null) {
                capacitadorid = em.getReference(capacitadorid.getClass(), capacitadorid.getId());
                capacitacionCapacitador.setCapacitadorid(capacitadorid);
            }
            Capacitacion capacitacionid = capacitacionCapacitador.getCapacitacionid();
            if (capacitacionid != null) {
                capacitacionid = em.getReference(capacitacionid.getClass(), capacitacionid.getId());
                capacitacionCapacitador.setCapacitacionid(capacitacionid);
            }
            em.persist(capacitacionCapacitador);
            if (capacitadorid != null) {
                capacitadorid.getCapacitacionCapacitadorList().add(capacitacionCapacitador);
                capacitadorid = em.merge(capacitadorid);
            }
            if (capacitacionid != null) {
                capacitacionid.getCapacitacionCapacitadorList().add(capacitacionCapacitador);
                capacitacionid = em.merge(capacitacionid);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CapacitacionCapacitador capacitacionCapacitador) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CapacitacionCapacitador persistentCapacitacionCapacitador = em.find(CapacitacionCapacitador.class, capacitacionCapacitador.getId());
            Capacitador capacitadoridOld = persistentCapacitacionCapacitador.getCapacitadorid();
            Capacitador capacitadoridNew = capacitacionCapacitador.getCapacitadorid();
            Capacitacion capacitacionidOld = persistentCapacitacionCapacitador.getCapacitacionid();
            Capacitacion capacitacionidNew = capacitacionCapacitador.getCapacitacionid();
            if (capacitadoridNew != null) {
                capacitadoridNew = em.getReference(capacitadoridNew.getClass(), capacitadoridNew.getId());
                capacitacionCapacitador.setCapacitadorid(capacitadoridNew);
            }
            if (capacitacionidNew != null) {
                capacitacionidNew = em.getReference(capacitacionidNew.getClass(), capacitacionidNew.getId());
                capacitacionCapacitador.setCapacitacionid(capacitacionidNew);
            }
            capacitacionCapacitador = em.merge(capacitacionCapacitador);
            if (capacitadoridOld != null && !capacitadoridOld.equals(capacitadoridNew)) {
                capacitadoridOld.getCapacitacionCapacitadorList().remove(capacitacionCapacitador);
                capacitadoridOld = em.merge(capacitadoridOld);
            }
            if (capacitadoridNew != null && !capacitadoridNew.equals(capacitadoridOld)) {
                capacitadoridNew.getCapacitacionCapacitadorList().add(capacitacionCapacitador);
                capacitadoridNew = em.merge(capacitadoridNew);
            }
            if (capacitacionidOld != null && !capacitacionidOld.equals(capacitacionidNew)) {
                capacitacionidOld.getCapacitacionCapacitadorList().remove(capacitacionCapacitador);
                capacitacionidOld = em.merge(capacitacionidOld);
            }
            if (capacitacionidNew != null && !capacitacionidNew.equals(capacitacionidOld)) {
                capacitacionidNew.getCapacitacionCapacitadorList().add(capacitacionCapacitador);
                capacitacionidNew = em.merge(capacitacionidNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = capacitacionCapacitador.getId();
                if (findCapacitacionCapacitador(id) == null) {
                    throw new NonexistentEntityException("The capacitacionCapacitador with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CapacitacionCapacitador capacitacionCapacitador;
            try {
                capacitacionCapacitador = em.getReference(CapacitacionCapacitador.class, id);
                capacitacionCapacitador.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The capacitacionCapacitador with id " + id + " no longer exists.", enfe);
            }
            Capacitador capacitadorid = capacitacionCapacitador.getCapacitadorid();
            if (capacitadorid != null) {
                capacitadorid.getCapacitacionCapacitadorList().remove(capacitacionCapacitador);
                capacitadorid = em.merge(capacitadorid);
            }
            Capacitacion capacitacionid = capacitacionCapacitador.getCapacitacionid();
            if (capacitacionid != null) {
                capacitacionid.getCapacitacionCapacitadorList().remove(capacitacionCapacitador);
                capacitacionid = em.merge(capacitacionid);
            }
            em.remove(capacitacionCapacitador);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CapacitacionCapacitador> findCapacitacionCapacitadorEntities() {
        return findCapacitacionCapacitadorEntities(true, -1, -1);
    }

    public List<CapacitacionCapacitador> findCapacitacionCapacitadorEntities(int maxResults, int firstResult) {
        return findCapacitacionCapacitadorEntities(false, maxResults, firstResult);
    }

    private List<CapacitacionCapacitador> findCapacitacionCapacitadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CapacitacionCapacitador.class));
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

    public CapacitacionCapacitador findCapacitacionCapacitador(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CapacitacionCapacitador.class, id);
        } finally {
            em.close();
        }
    }

    public int getCapacitacionCapacitadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CapacitacionCapacitador> rt = cq.from(CapacitacionCapacitador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
