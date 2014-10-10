/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jpa.controllers;

import com.jpa.controllers.exceptions.NonexistentEntityException;
import com.jpa.controllers.exceptions.RollbackFailureException;
import com.jpa.entities.Asistencia;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.jpa.entities.Capacitacion;
import com.jpa.entities.Capacitado;
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
public class AsistenciaJpaController implements Serializable {

     // Modificar Constructor
    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "TesisCapacitySoftPU")
    private EntityManagerFactory emf = null;
    InitialContext ic = new InitialContext();

    public  AsistenciaJpaController() throws NamingException {
        utx = (UserTransaction) ic.lookup("java:comp/UserTransaction");
        emf = Persistence
                .createEntityManagerFactory("TesisCapacitySoftPU");
    }
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Asistencia asistencia) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Capacitacion capacitacionid = asistencia.getCapacitacionid();
            if (capacitacionid != null) {
                capacitacionid = em.getReference(capacitacionid.getClass(), capacitacionid.getId());
                asistencia.setCapacitacionid(capacitacionid);
            }
            Capacitado capacitadoid = asistencia.getCapacitadoid();
            if (capacitadoid != null) {
                capacitadoid = em.getReference(capacitadoid.getClass(), capacitadoid.getId());
                asistencia.setCapacitadoid(capacitadoid);
            }
            em.persist(asistencia);
            if (capacitacionid != null) {
                capacitacionid.getAsistenciaList().add(asistencia);
                capacitacionid = em.merge(capacitacionid);
            }
            if (capacitadoid != null) {
                capacitadoid.getAsistenciaList().add(asistencia);
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

    public void edit(Asistencia asistencia) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Asistencia persistentAsistencia = em.find(Asistencia.class, asistencia.getId());
            Capacitacion capacitacionidOld = persistentAsistencia.getCapacitacionid();
            Capacitacion capacitacionidNew = asistencia.getCapacitacionid();
            Capacitado capacitadoidOld = persistentAsistencia.getCapacitadoid();
            Capacitado capacitadoidNew = asistencia.getCapacitadoid();
            if (capacitacionidNew != null) {
                capacitacionidNew = em.getReference(capacitacionidNew.getClass(), capacitacionidNew.getId());
                asistencia.setCapacitacionid(capacitacionidNew);
            }
            if (capacitadoidNew != null) {
                capacitadoidNew = em.getReference(capacitadoidNew.getClass(), capacitadoidNew.getId());
                asistencia.setCapacitadoid(capacitadoidNew);
            }
            asistencia = em.merge(asistencia);
            if (capacitacionidOld != null && !capacitacionidOld.equals(capacitacionidNew)) {
                capacitacionidOld.getAsistenciaList().remove(asistencia);
                capacitacionidOld = em.merge(capacitacionidOld);
            }
            if (capacitacionidNew != null && !capacitacionidNew.equals(capacitacionidOld)) {
                capacitacionidNew.getAsistenciaList().add(asistencia);
                capacitacionidNew = em.merge(capacitacionidNew);
            }
            if (capacitadoidOld != null && !capacitadoidOld.equals(capacitadoidNew)) {
                capacitadoidOld.getAsistenciaList().remove(asistencia);
                capacitadoidOld = em.merge(capacitadoidOld);
            }
            if (capacitadoidNew != null && !capacitadoidNew.equals(capacitadoidOld)) {
                capacitadoidNew.getAsistenciaList().add(asistencia);
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
                Integer id = asistencia.getId();
                if (findAsistencia(id) == null) {
                    throw new NonexistentEntityException("The asistencia with id " + id + " no longer exists.");
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
            Asistencia asistencia;
            try {
                asistencia = em.getReference(Asistencia.class, id);
                asistencia.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asistencia with id " + id + " no longer exists.", enfe);
            }
            Capacitacion capacitacionid = asistencia.getCapacitacionid();
            if (capacitacionid != null) {
                capacitacionid.getAsistenciaList().remove(asistencia);
                capacitacionid = em.merge(capacitacionid);
            }
            Capacitado capacitadoid = asistencia.getCapacitadoid();
            if (capacitadoid != null) {
                capacitadoid.getAsistenciaList().remove(asistencia);
                capacitadoid = em.merge(capacitadoid);
            }
            em.remove(asistencia);
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

    public List<Asistencia> findAsistenciaEntities() {
        return findAsistenciaEntities(true, -1, -1);
    }

    public List<Asistencia> findAsistenciaEntities(int maxResults, int firstResult) {
        return findAsistenciaEntities(false, maxResults, firstResult);
    }

    private List<Asistencia> findAsistenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Asistencia.class));
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

    public Asistencia findAsistencia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Asistencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getAsistenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Asistencia> rt = cq.from(Asistencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
