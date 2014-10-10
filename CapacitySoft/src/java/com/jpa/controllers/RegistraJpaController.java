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
import com.jpa.entities.Capacitacion;
import com.jpa.entities.Capacitado;
import com.jpa.entities.Registra;
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
public class RegistraJpaController implements Serializable {

     // Modificar Constructor
    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "TesisCapacitySoftPU")
    private EntityManagerFactory emf = null;
    InitialContext ic = new InitialContext();

    public  RegistraJpaController() throws NamingException {
        utx = (UserTransaction) ic.lookup("java:comp/UserTransaction");
        emf = Persistence
                .createEntityManagerFactory("TesisCapacitySoftPU");
    }
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Registra registra) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Capacitacion capacitacionid = registra.getCapacitacionid();
            if (capacitacionid != null) {
                capacitacionid = em.getReference(capacitacionid.getClass(), capacitacionid.getId());
                registra.setCapacitacionid(capacitacionid);
            }
            Capacitado capacitadoid = registra.getCapacitadoid();
            if (capacitadoid != null) {
                capacitadoid = em.getReference(capacitadoid.getClass(), capacitadoid.getId());
                registra.setCapacitadoid(capacitadoid);
            }
            em.persist(registra);
            if (capacitacionid != null) {
                capacitacionid.getRegistraList().add(registra);
                capacitacionid = em.merge(capacitacionid);
            }
            if (capacitadoid != null) {
                capacitadoid.getRegistraList().add(registra);
                capacitadoid = em.merge(capacitadoid);
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

    public void edit(Registra registra) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Registra persistentRegistra = em.find(Registra.class, registra.getId());
            Capacitacion capacitacionidOld = persistentRegistra.getCapacitacionid();
            Capacitacion capacitacionidNew = registra.getCapacitacionid();
            Capacitado capacitadoidOld = persistentRegistra.getCapacitadoid();
            Capacitado capacitadoidNew = registra.getCapacitadoid();
            if (capacitacionidNew != null) {
                capacitacionidNew = em.getReference(capacitacionidNew.getClass(), capacitacionidNew.getId());
                registra.setCapacitacionid(capacitacionidNew);
            }
            if (capacitadoidNew != null) {
                capacitadoidNew = em.getReference(capacitadoidNew.getClass(), capacitadoidNew.getId());
                registra.setCapacitadoid(capacitadoidNew);
            }
            registra = em.merge(registra);
            if (capacitacionidOld != null && !capacitacionidOld.equals(capacitacionidNew)) {
                capacitacionidOld.getRegistraList().remove(registra);
                capacitacionidOld = em.merge(capacitacionidOld);
            }
            if (capacitacionidNew != null && !capacitacionidNew.equals(capacitacionidOld)) {
                capacitacionidNew.getRegistraList().add(registra);
                capacitacionidNew = em.merge(capacitacionidNew);
            }
            if (capacitadoidOld != null && !capacitadoidOld.equals(capacitadoidNew)) {
                capacitadoidOld.getRegistraList().remove(registra);
                capacitadoidOld = em.merge(capacitadoidOld);
            }
            if (capacitadoidNew != null && !capacitadoidNew.equals(capacitadoidOld)) {
                capacitadoidNew.getRegistraList().add(registra);
                capacitadoidNew = em.merge(capacitadoidNew);
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
                Integer id = registra.getId();
                if (findRegistra(id) == null) {
                    throw new NonexistentEntityException("The registra with id " + id + " no longer exists.");
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
            Registra registra;
            try {
                registra = em.getReference(Registra.class, id);
                registra.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The registra with id " + id + " no longer exists.", enfe);
            }
            Capacitacion capacitacionid = registra.getCapacitacionid();
            if (capacitacionid != null) {
                capacitacionid.getRegistraList().remove(registra);
                capacitacionid = em.merge(capacitacionid);
            }
            Capacitado capacitadoid = registra.getCapacitadoid();
            if (capacitadoid != null) {
                capacitadoid.getRegistraList().remove(registra);
                capacitadoid = em.merge(capacitadoid);
            }
            em.remove(registra);
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

    public List<Registra> findRegistraEntities() {
        return findRegistraEntities(true, -1, -1);
    }

    public List<Registra> findRegistraEntities(int maxResults, int firstResult) {
        return findRegistraEntities(false, maxResults, firstResult);
    }

    private List<Registra> findRegistraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Registra.class));
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

    public Registra findRegistra(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Registra.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegistraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Registra> rt = cq.from(Registra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
