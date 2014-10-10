/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jpa.controllers;

import com.jpa.controllers.exceptions.IllegalOrphanException;
import com.jpa.controllers.exceptions.NonexistentEntityException;
import com.jpa.controllers.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.jpa.entities.Pregunta;
import com.jpa.entities.EvaluacionCapacitado;
import com.jpa.entities.Respuesta;
import java.util.ArrayList;
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
public class RespuestaJpaController implements Serializable {

     // Modificar Constructor
    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "TesisCapacitySoftPU")
    private EntityManagerFactory emf = null;
    InitialContext ic = new InitialContext();

    public  RespuestaJpaController() throws NamingException {
        utx = (UserTransaction) ic.lookup("java:comp/UserTransaction");
        emf = Persistence
                .createEntityManagerFactory("TesisCapacitySoftPU");
    }
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Respuesta respuesta) throws RollbackFailureException, Exception {
        if (respuesta.getEvaluacionCapacitadoList() == null) {
            respuesta.setEvaluacionCapacitadoList(new ArrayList<EvaluacionCapacitado>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Pregunta preguntaId = respuesta.getPreguntaId();
            if (preguntaId != null) {
                preguntaId = em.getReference(preguntaId.getClass(), preguntaId.getId());
                respuesta.setPreguntaId(preguntaId);
            }
            List<EvaluacionCapacitado> attachedEvaluacionCapacitadoList = new ArrayList<EvaluacionCapacitado>();
            for (EvaluacionCapacitado evaluacionCapacitadoListEvaluacionCapacitadoToAttach : respuesta.getEvaluacionCapacitadoList()) {
                evaluacionCapacitadoListEvaluacionCapacitadoToAttach = em.getReference(evaluacionCapacitadoListEvaluacionCapacitadoToAttach.getClass(), evaluacionCapacitadoListEvaluacionCapacitadoToAttach.getIdEvaluacionCapacitado());
                attachedEvaluacionCapacitadoList.add(evaluacionCapacitadoListEvaluacionCapacitadoToAttach);
            }
            respuesta.setEvaluacionCapacitadoList(attachedEvaluacionCapacitadoList);
            em.persist(respuesta);
            if (preguntaId != null) {
                preguntaId.getRespuestaList().add(respuesta);
                preguntaId = em.merge(preguntaId);
            }
            for (EvaluacionCapacitado evaluacionCapacitadoListEvaluacionCapacitado : respuesta.getEvaluacionCapacitadoList()) {
                Respuesta oldRespuestaidOfEvaluacionCapacitadoListEvaluacionCapacitado = evaluacionCapacitadoListEvaluacionCapacitado.getRespuestaid();
                evaluacionCapacitadoListEvaluacionCapacitado.setRespuestaid(respuesta);
                evaluacionCapacitadoListEvaluacionCapacitado = em.merge(evaluacionCapacitadoListEvaluacionCapacitado);
                if (oldRespuestaidOfEvaluacionCapacitadoListEvaluacionCapacitado != null) {
                    oldRespuestaidOfEvaluacionCapacitadoListEvaluacionCapacitado.getEvaluacionCapacitadoList().remove(evaluacionCapacitadoListEvaluacionCapacitado);
                    oldRespuestaidOfEvaluacionCapacitadoListEvaluacionCapacitado = em.merge(oldRespuestaidOfEvaluacionCapacitadoListEvaluacionCapacitado);
                }
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

    public void edit(Respuesta respuesta) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Respuesta persistentRespuesta = em.find(Respuesta.class, respuesta.getId());
            Pregunta preguntaIdOld = persistentRespuesta.getPreguntaId();
            Pregunta preguntaIdNew = respuesta.getPreguntaId();
            List<EvaluacionCapacitado> evaluacionCapacitadoListOld = persistentRespuesta.getEvaluacionCapacitadoList();
            List<EvaluacionCapacitado> evaluacionCapacitadoListNew = respuesta.getEvaluacionCapacitadoList();
            List<String> illegalOrphanMessages = null;
            for (EvaluacionCapacitado evaluacionCapacitadoListOldEvaluacionCapacitado : evaluacionCapacitadoListOld) {
                if (!evaluacionCapacitadoListNew.contains(evaluacionCapacitadoListOldEvaluacionCapacitado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EvaluacionCapacitado " + evaluacionCapacitadoListOldEvaluacionCapacitado + " since its respuestaid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (preguntaIdNew != null) {
                preguntaIdNew = em.getReference(preguntaIdNew.getClass(), preguntaIdNew.getId());
                respuesta.setPreguntaId(preguntaIdNew);
            }
            List<EvaluacionCapacitado> attachedEvaluacionCapacitadoListNew = new ArrayList<EvaluacionCapacitado>();
            for (EvaluacionCapacitado evaluacionCapacitadoListNewEvaluacionCapacitadoToAttach : evaluacionCapacitadoListNew) {
                evaluacionCapacitadoListNewEvaluacionCapacitadoToAttach = em.getReference(evaluacionCapacitadoListNewEvaluacionCapacitadoToAttach.getClass(), evaluacionCapacitadoListNewEvaluacionCapacitadoToAttach.getIdEvaluacionCapacitado());
                attachedEvaluacionCapacitadoListNew.add(evaluacionCapacitadoListNewEvaluacionCapacitadoToAttach);
            }
            evaluacionCapacitadoListNew = attachedEvaluacionCapacitadoListNew;
            respuesta.setEvaluacionCapacitadoList(evaluacionCapacitadoListNew);
            respuesta = em.merge(respuesta);
            if (preguntaIdOld != null && !preguntaIdOld.equals(preguntaIdNew)) {
                preguntaIdOld.getRespuestaList().remove(respuesta);
                preguntaIdOld = em.merge(preguntaIdOld);
            }
            if (preguntaIdNew != null && !preguntaIdNew.equals(preguntaIdOld)) {
                preguntaIdNew.getRespuestaList().add(respuesta);
                preguntaIdNew = em.merge(preguntaIdNew);
            }
            for (EvaluacionCapacitado evaluacionCapacitadoListNewEvaluacionCapacitado : evaluacionCapacitadoListNew) {
                if (!evaluacionCapacitadoListOld.contains(evaluacionCapacitadoListNewEvaluacionCapacitado)) {
                    Respuesta oldRespuestaidOfEvaluacionCapacitadoListNewEvaluacionCapacitado = evaluacionCapacitadoListNewEvaluacionCapacitado.getRespuestaid();
                    evaluacionCapacitadoListNewEvaluacionCapacitado.setRespuestaid(respuesta);
                    evaluacionCapacitadoListNewEvaluacionCapacitado = em.merge(evaluacionCapacitadoListNewEvaluacionCapacitado);
                    if (oldRespuestaidOfEvaluacionCapacitadoListNewEvaluacionCapacitado != null && !oldRespuestaidOfEvaluacionCapacitadoListNewEvaluacionCapacitado.equals(respuesta)) {
                        oldRespuestaidOfEvaluacionCapacitadoListNewEvaluacionCapacitado.getEvaluacionCapacitadoList().remove(evaluacionCapacitadoListNewEvaluacionCapacitado);
                        oldRespuestaidOfEvaluacionCapacitadoListNewEvaluacionCapacitado = em.merge(oldRespuestaidOfEvaluacionCapacitadoListNewEvaluacionCapacitado);
                    }
                }
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
                Integer id = respuesta.getId();
                if (findRespuesta(id) == null) {
                    throw new NonexistentEntityException("The respuesta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Respuesta respuesta;
            try {
                respuesta = em.getReference(Respuesta.class, id);
                respuesta.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The respuesta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<EvaluacionCapacitado> evaluacionCapacitadoListOrphanCheck = respuesta.getEvaluacionCapacitadoList();
            for (EvaluacionCapacitado evaluacionCapacitadoListOrphanCheckEvaluacionCapacitado : evaluacionCapacitadoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Respuesta (" + respuesta + ") cannot be destroyed since the EvaluacionCapacitado " + evaluacionCapacitadoListOrphanCheckEvaluacionCapacitado + " in its evaluacionCapacitadoList field has a non-nullable respuestaid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Pregunta preguntaId = respuesta.getPreguntaId();
            if (preguntaId != null) {
                preguntaId.getRespuestaList().remove(respuesta);
                preguntaId = em.merge(preguntaId);
            }
            em.remove(respuesta);
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

    public List<Respuesta> findRespuestaEntities() {
        return findRespuestaEntities(true, -1, -1);
    }

    public List<Respuesta> findRespuestaEntities(int maxResults, int firstResult) {
        return findRespuestaEntities(false, maxResults, firstResult);
    }

    private List<Respuesta> findRespuestaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Respuesta.class));
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

    public Respuesta findRespuesta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Respuesta.class, id);
        } finally {
            em.close();
        }
    }

    public int getRespuestaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Respuesta> rt = cq.from(Respuesta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
