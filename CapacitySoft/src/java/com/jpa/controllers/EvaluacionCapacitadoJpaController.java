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
import com.jpa.entities.Pregunta;
import com.jpa.entities.Capacitado;
import com.jpa.entities.EvaluacionCapacitado;
import com.jpa.entities.Respuesta;
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
public class EvaluacionCapacitadoJpaController implements Serializable {

     // Modificar Constructor
    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "TesisCapacitySoftPU")
    private EntityManagerFactory emf = null;
    InitialContext ic = new InitialContext();

    public  EvaluacionCapacitadoJpaController() throws NamingException {
        utx = (UserTransaction) ic.lookup("java:comp/UserTransaction");
        emf = Persistence
                .createEntityManagerFactory("TesisCapacitySoftPU");
    }
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EvaluacionCapacitado evaluacionCapacitado) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Pregunta preguntaid = evaluacionCapacitado.getPreguntaid();
            if (preguntaid != null) {
                preguntaid = em.getReference(preguntaid.getClass(), preguntaid.getId());
                evaluacionCapacitado.setPreguntaid(preguntaid);
            }
            Capacitado capacitadoid = evaluacionCapacitado.getCapacitadoid();
            if (capacitadoid != null) {
                capacitadoid = em.getReference(capacitadoid.getClass(), capacitadoid.getId());
                evaluacionCapacitado.setCapacitadoid(capacitadoid);
            }
            Respuesta respuestaid = evaluacionCapacitado.getRespuestaid();
            if (respuestaid != null) {
                respuestaid = em.getReference(respuestaid.getClass(), respuestaid.getId());
                evaluacionCapacitado.setRespuestaid(respuestaid);
            }
            em.persist(evaluacionCapacitado);
            if (preguntaid != null) {
                preguntaid.getEvaluacionCapacitadoList().add(evaluacionCapacitado);
                preguntaid = em.merge(preguntaid);
            }
            if (capacitadoid != null) {
                capacitadoid.getEvaluacionCapacitadoList().add(evaluacionCapacitado);
                capacitadoid = em.merge(capacitadoid);
            }
            if (respuestaid != null) {
                respuestaid.getEvaluacionCapacitadoList().add(evaluacionCapacitado);
                respuestaid = em.merge(respuestaid);
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

    public void edit(EvaluacionCapacitado evaluacionCapacitado) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            EvaluacionCapacitado persistentEvaluacionCapacitado = em.find(EvaluacionCapacitado.class, evaluacionCapacitado.getIdEvaluacionCapacitado());
            Pregunta preguntaidOld = persistentEvaluacionCapacitado.getPreguntaid();
            Pregunta preguntaidNew = evaluacionCapacitado.getPreguntaid();
            Capacitado capacitadoidOld = persistentEvaluacionCapacitado.getCapacitadoid();
            Capacitado capacitadoidNew = evaluacionCapacitado.getCapacitadoid();
            Respuesta respuestaidOld = persistentEvaluacionCapacitado.getRespuestaid();
            Respuesta respuestaidNew = evaluacionCapacitado.getRespuestaid();
            if (preguntaidNew != null) {
                preguntaidNew = em.getReference(preguntaidNew.getClass(), preguntaidNew.getId());
                evaluacionCapacitado.setPreguntaid(preguntaidNew);
            }
            if (capacitadoidNew != null) {
                capacitadoidNew = em.getReference(capacitadoidNew.getClass(), capacitadoidNew.getId());
                evaluacionCapacitado.setCapacitadoid(capacitadoidNew);
            }
            if (respuestaidNew != null) {
                respuestaidNew = em.getReference(respuestaidNew.getClass(), respuestaidNew.getId());
                evaluacionCapacitado.setRespuestaid(respuestaidNew);
            }
            evaluacionCapacitado = em.merge(evaluacionCapacitado);
            if (preguntaidOld != null && !preguntaidOld.equals(preguntaidNew)) {
                preguntaidOld.getEvaluacionCapacitadoList().remove(evaluacionCapacitado);
                preguntaidOld = em.merge(preguntaidOld);
            }
            if (preguntaidNew != null && !preguntaidNew.equals(preguntaidOld)) {
                preguntaidNew.getEvaluacionCapacitadoList().add(evaluacionCapacitado);
                preguntaidNew = em.merge(preguntaidNew);
            }
            if (capacitadoidOld != null && !capacitadoidOld.equals(capacitadoidNew)) {
                capacitadoidOld.getEvaluacionCapacitadoList().remove(evaluacionCapacitado);
                capacitadoidOld = em.merge(capacitadoidOld);
            }
            if (capacitadoidNew != null && !capacitadoidNew.equals(capacitadoidOld)) {
                capacitadoidNew.getEvaluacionCapacitadoList().add(evaluacionCapacitado);
                capacitadoidNew = em.merge(capacitadoidNew);
            }
            if (respuestaidOld != null && !respuestaidOld.equals(respuestaidNew)) {
                respuestaidOld.getEvaluacionCapacitadoList().remove(evaluacionCapacitado);
                respuestaidOld = em.merge(respuestaidOld);
            }
            if (respuestaidNew != null && !respuestaidNew.equals(respuestaidOld)) {
                respuestaidNew.getEvaluacionCapacitadoList().add(evaluacionCapacitado);
                respuestaidNew = em.merge(respuestaidNew);
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
                Integer id = evaluacionCapacitado.getIdEvaluacionCapacitado();
                if (findEvaluacionCapacitado(id) == null) {
                    throw new NonexistentEntityException("The evaluacionCapacitado with id " + id + " no longer exists.");
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
            EvaluacionCapacitado evaluacionCapacitado;
            try {
                evaluacionCapacitado = em.getReference(EvaluacionCapacitado.class, id);
                evaluacionCapacitado.getIdEvaluacionCapacitado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The evaluacionCapacitado with id " + id + " no longer exists.", enfe);
            }
            Pregunta preguntaid = evaluacionCapacitado.getPreguntaid();
            if (preguntaid != null) {
                preguntaid.getEvaluacionCapacitadoList().remove(evaluacionCapacitado);
                preguntaid = em.merge(preguntaid);
            }
            Capacitado capacitadoid = evaluacionCapacitado.getCapacitadoid();
            if (capacitadoid != null) {
                capacitadoid.getEvaluacionCapacitadoList().remove(evaluacionCapacitado);
                capacitadoid = em.merge(capacitadoid);
            }
            Respuesta respuestaid = evaluacionCapacitado.getRespuestaid();
            if (respuestaid != null) {
                respuestaid.getEvaluacionCapacitadoList().remove(evaluacionCapacitado);
                respuestaid = em.merge(respuestaid);
            }
            em.remove(evaluacionCapacitado);
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

    public List<EvaluacionCapacitado> findEvaluacionCapacitadoEntities() {
        return findEvaluacionCapacitadoEntities(true, -1, -1);
    }

    public List<EvaluacionCapacitado> findEvaluacionCapacitadoEntities(int maxResults, int firstResult) {
        return findEvaluacionCapacitadoEntities(false, maxResults, firstResult);
    }

    private List<EvaluacionCapacitado> findEvaluacionCapacitadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EvaluacionCapacitado.class));
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

    public EvaluacionCapacitado findEvaluacionCapacitado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EvaluacionCapacitado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEvaluacionCapacitadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EvaluacionCapacitado> rt = cq.from(EvaluacionCapacitado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
