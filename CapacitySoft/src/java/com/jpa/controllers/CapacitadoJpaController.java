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
import com.jpa.entities.Carrera;
import com.jpa.entities.Capacitador;
import com.jpa.entities.Sector;
import com.jpa.entities.Registra;
import java.util.ArrayList;
import java.util.List;
import com.jpa.entities.EvaluacionCapacitado;
import com.jpa.entities.Asistencia;
import com.jpa.entities.Capacitado;
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
public class CapacitadoJpaController implements Serializable {

     // Modificar Constructor
    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "TesisCapacitySoftPU")
    private EntityManagerFactory emf = null;
    InitialContext ic = new InitialContext();

    public  CapacitadoJpaController() throws NamingException {
        utx = (UserTransaction) ic.lookup("java:comp/UserTransaction");
        emf = Persistence
                .createEntityManagerFactory("TesisCapacitySoftPU");
    }
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Capacitado capacitado) throws RollbackFailureException, Exception {
        if (capacitado.getRegistraList() == null) {
            capacitado.setRegistraList(new ArrayList<Registra>());
        }
        if (capacitado.getEvaluacionCapacitadoList() == null) {
            capacitado.setEvaluacionCapacitadoList(new ArrayList<EvaluacionCapacitado>());
        }
        if (capacitado.getAsistenciaList() == null) {
            capacitado.setAsistenciaList(new ArrayList<Asistencia>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Carrera carreraId = capacitado.getCarreraId();
            if (carreraId != null) {
                carreraId = em.getReference(carreraId.getClass(), carreraId.getId());
                capacitado.setCarreraId(carreraId);
            }
            Capacitador capacitadorid = capacitado.getCapacitadorid();
            if (capacitadorid != null) {
                capacitadorid = em.getReference(capacitadorid.getClass(), capacitadorid.getId());
                capacitado.setCapacitadorid(capacitadorid);
            }
            Sector sectorid = capacitado.getSectorid();
            if (sectorid != null) {
                sectorid = em.getReference(sectorid.getClass(), sectorid.getId());
                capacitado.setSectorid(sectorid);
            }
            List<Registra> attachedRegistraList = new ArrayList<Registra>();
            for (Registra registraListRegistraToAttach : capacitado.getRegistraList()) {
                registraListRegistraToAttach = em.getReference(registraListRegistraToAttach.getClass(), registraListRegistraToAttach.getId());
                attachedRegistraList.add(registraListRegistraToAttach);
            }
            capacitado.setRegistraList(attachedRegistraList);
            List<EvaluacionCapacitado> attachedEvaluacionCapacitadoList = new ArrayList<EvaluacionCapacitado>();
            for (EvaluacionCapacitado evaluacionCapacitadoListEvaluacionCapacitadoToAttach : capacitado.getEvaluacionCapacitadoList()) {
                evaluacionCapacitadoListEvaluacionCapacitadoToAttach = em.getReference(evaluacionCapacitadoListEvaluacionCapacitadoToAttach.getClass(), evaluacionCapacitadoListEvaluacionCapacitadoToAttach.getIdEvaluacionCapacitado());
                attachedEvaluacionCapacitadoList.add(evaluacionCapacitadoListEvaluacionCapacitadoToAttach);
            }
            capacitado.setEvaluacionCapacitadoList(attachedEvaluacionCapacitadoList);
            List<Asistencia> attachedAsistenciaList = new ArrayList<Asistencia>();
            for (Asistencia asistenciaListAsistenciaToAttach : capacitado.getAsistenciaList()) {
                asistenciaListAsistenciaToAttach = em.getReference(asistenciaListAsistenciaToAttach.getClass(), asistenciaListAsistenciaToAttach.getId());
                attachedAsistenciaList.add(asistenciaListAsistenciaToAttach);
            }
            capacitado.setAsistenciaList(attachedAsistenciaList);
            em.persist(capacitado);
            if (carreraId != null) {
                carreraId.getCapacitadoList().add(capacitado);
                carreraId = em.merge(carreraId);
            }
            if (capacitadorid != null) {
                capacitadorid.getCapacitadoList().add(capacitado);
                capacitadorid = em.merge(capacitadorid);
            }
            if (sectorid != null) {
                sectorid.getCapacitadoList().add(capacitado);
                sectorid = em.merge(sectorid);
            }
            for (Registra registraListRegistra : capacitado.getRegistraList()) {
                Capacitado oldCapacitadoidOfRegistraListRegistra = registraListRegistra.getCapacitadoid();
                registraListRegistra.setCapacitadoid(capacitado);
                registraListRegistra = em.merge(registraListRegistra);
                if (oldCapacitadoidOfRegistraListRegistra != null) {
                    oldCapacitadoidOfRegistraListRegistra.getRegistraList().remove(registraListRegistra);
                    oldCapacitadoidOfRegistraListRegistra = em.merge(oldCapacitadoidOfRegistraListRegistra);
                }
            }
            for (EvaluacionCapacitado evaluacionCapacitadoListEvaluacionCapacitado : capacitado.getEvaluacionCapacitadoList()) {
                Capacitado oldCapacitadoidOfEvaluacionCapacitadoListEvaluacionCapacitado = evaluacionCapacitadoListEvaluacionCapacitado.getCapacitadoid();
                evaluacionCapacitadoListEvaluacionCapacitado.setCapacitadoid(capacitado);
                evaluacionCapacitadoListEvaluacionCapacitado = em.merge(evaluacionCapacitadoListEvaluacionCapacitado);
                if (oldCapacitadoidOfEvaluacionCapacitadoListEvaluacionCapacitado != null) {
                    oldCapacitadoidOfEvaluacionCapacitadoListEvaluacionCapacitado.getEvaluacionCapacitadoList().remove(evaluacionCapacitadoListEvaluacionCapacitado);
                    oldCapacitadoidOfEvaluacionCapacitadoListEvaluacionCapacitado = em.merge(oldCapacitadoidOfEvaluacionCapacitadoListEvaluacionCapacitado);
                }
            }
            for (Asistencia asistenciaListAsistencia : capacitado.getAsistenciaList()) {
                Capacitado oldCapacitadoidOfAsistenciaListAsistencia = asistenciaListAsistencia.getCapacitadoid();
                asistenciaListAsistencia.setCapacitadoid(capacitado);
                asistenciaListAsistencia = em.merge(asistenciaListAsistencia);
                if (oldCapacitadoidOfAsistenciaListAsistencia != null) {
                    oldCapacitadoidOfAsistenciaListAsistencia.getAsistenciaList().remove(asistenciaListAsistencia);
                    oldCapacitadoidOfAsistenciaListAsistencia = em.merge(oldCapacitadoidOfAsistenciaListAsistencia);
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

    public void edit(Capacitado capacitado) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Capacitado persistentCapacitado = em.find(Capacitado.class, capacitado.getId());
            Carrera carreraIdOld = persistentCapacitado.getCarreraId();
            Carrera carreraIdNew = capacitado.getCarreraId();
            Capacitador capacitadoridOld = persistentCapacitado.getCapacitadorid();
            Capacitador capacitadoridNew = capacitado.getCapacitadorid();
            Sector sectoridOld = persistentCapacitado.getSectorid();
            Sector sectoridNew = capacitado.getSectorid();
            List<Registra> registraListOld = persistentCapacitado.getRegistraList();
            List<Registra> registraListNew = capacitado.getRegistraList();
            List<EvaluacionCapacitado> evaluacionCapacitadoListOld = persistentCapacitado.getEvaluacionCapacitadoList();
            List<EvaluacionCapacitado> evaluacionCapacitadoListNew = capacitado.getEvaluacionCapacitadoList();
            List<Asistencia> asistenciaListOld = persistentCapacitado.getAsistenciaList();
            List<Asistencia> asistenciaListNew = capacitado.getAsistenciaList();
            List<String> illegalOrphanMessages = null;
            for (Registra registraListOldRegistra : registraListOld) {
                if (!registraListNew.contains(registraListOldRegistra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Registra " + registraListOldRegistra + " since its capacitadoid field is not nullable.");
                }
            }
            for (EvaluacionCapacitado evaluacionCapacitadoListOldEvaluacionCapacitado : evaluacionCapacitadoListOld) {
                if (!evaluacionCapacitadoListNew.contains(evaluacionCapacitadoListOldEvaluacionCapacitado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EvaluacionCapacitado " + evaluacionCapacitadoListOldEvaluacionCapacitado + " since its capacitadoid field is not nullable.");
                }
            }
            for (Asistencia asistenciaListOldAsistencia : asistenciaListOld) {
                if (!asistenciaListNew.contains(asistenciaListOldAsistencia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Asistencia " + asistenciaListOldAsistencia + " since its capacitadoid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (carreraIdNew != null) {
                carreraIdNew = em.getReference(carreraIdNew.getClass(), carreraIdNew.getId());
                capacitado.setCarreraId(carreraIdNew);
            }
            if (capacitadoridNew != null) {
                capacitadoridNew = em.getReference(capacitadoridNew.getClass(), capacitadoridNew.getId());
                capacitado.setCapacitadorid(capacitadoridNew);
            }
            if (sectoridNew != null) {
                sectoridNew = em.getReference(sectoridNew.getClass(), sectoridNew.getId());
                capacitado.setSectorid(sectoridNew);
            }
            List<Registra> attachedRegistraListNew = new ArrayList<Registra>();
            for (Registra registraListNewRegistraToAttach : registraListNew) {
                registraListNewRegistraToAttach = em.getReference(registraListNewRegistraToAttach.getClass(), registraListNewRegistraToAttach.getId());
                attachedRegistraListNew.add(registraListNewRegistraToAttach);
            }
            registraListNew = attachedRegistraListNew;
            capacitado.setRegistraList(registraListNew);
            List<EvaluacionCapacitado> attachedEvaluacionCapacitadoListNew = new ArrayList<EvaluacionCapacitado>();
            for (EvaluacionCapacitado evaluacionCapacitadoListNewEvaluacionCapacitadoToAttach : evaluacionCapacitadoListNew) {
                evaluacionCapacitadoListNewEvaluacionCapacitadoToAttach = em.getReference(evaluacionCapacitadoListNewEvaluacionCapacitadoToAttach.getClass(), evaluacionCapacitadoListNewEvaluacionCapacitadoToAttach.getIdEvaluacionCapacitado());
                attachedEvaluacionCapacitadoListNew.add(evaluacionCapacitadoListNewEvaluacionCapacitadoToAttach);
            }
            evaluacionCapacitadoListNew = attachedEvaluacionCapacitadoListNew;
            capacitado.setEvaluacionCapacitadoList(evaluacionCapacitadoListNew);
            List<Asistencia> attachedAsistenciaListNew = new ArrayList<Asistencia>();
            for (Asistencia asistenciaListNewAsistenciaToAttach : asistenciaListNew) {
                asistenciaListNewAsistenciaToAttach = em.getReference(asistenciaListNewAsistenciaToAttach.getClass(), asistenciaListNewAsistenciaToAttach.getId());
                attachedAsistenciaListNew.add(asistenciaListNewAsistenciaToAttach);
            }
            asistenciaListNew = attachedAsistenciaListNew;
            capacitado.setAsistenciaList(asistenciaListNew);
            capacitado = em.merge(capacitado);
            if (carreraIdOld != null && !carreraIdOld.equals(carreraIdNew)) {
                carreraIdOld.getCapacitadoList().remove(capacitado);
                carreraIdOld = em.merge(carreraIdOld);
            }
            if (carreraIdNew != null && !carreraIdNew.equals(carreraIdOld)) {
                carreraIdNew.getCapacitadoList().add(capacitado);
                carreraIdNew = em.merge(carreraIdNew);
            }
            if (capacitadoridOld != null && !capacitadoridOld.equals(capacitadoridNew)) {
                capacitadoridOld.getCapacitadoList().remove(capacitado);
                capacitadoridOld = em.merge(capacitadoridOld);
            }
            if (capacitadoridNew != null && !capacitadoridNew.equals(capacitadoridOld)) {
                capacitadoridNew.getCapacitadoList().add(capacitado);
                capacitadoridNew = em.merge(capacitadoridNew);
            }
            if (sectoridOld != null && !sectoridOld.equals(sectoridNew)) {
                sectoridOld.getCapacitadoList().remove(capacitado);
                sectoridOld = em.merge(sectoridOld);
            }
            if (sectoridNew != null && !sectoridNew.equals(sectoridOld)) {
                sectoridNew.getCapacitadoList().add(capacitado);
                sectoridNew = em.merge(sectoridNew);
            }
            for (Registra registraListNewRegistra : registraListNew) {
                if (!registraListOld.contains(registraListNewRegistra)) {
                    Capacitado oldCapacitadoidOfRegistraListNewRegistra = registraListNewRegistra.getCapacitadoid();
                    registraListNewRegistra.setCapacitadoid(capacitado);
                    registraListNewRegistra = em.merge(registraListNewRegistra);
                    if (oldCapacitadoidOfRegistraListNewRegistra != null && !oldCapacitadoidOfRegistraListNewRegistra.equals(capacitado)) {
                        oldCapacitadoidOfRegistraListNewRegistra.getRegistraList().remove(registraListNewRegistra);
                        oldCapacitadoidOfRegistraListNewRegistra = em.merge(oldCapacitadoidOfRegistraListNewRegistra);
                    }
                }
            }
            for (EvaluacionCapacitado evaluacionCapacitadoListNewEvaluacionCapacitado : evaluacionCapacitadoListNew) {
                if (!evaluacionCapacitadoListOld.contains(evaluacionCapacitadoListNewEvaluacionCapacitado)) {
                    Capacitado oldCapacitadoidOfEvaluacionCapacitadoListNewEvaluacionCapacitado = evaluacionCapacitadoListNewEvaluacionCapacitado.getCapacitadoid();
                    evaluacionCapacitadoListNewEvaluacionCapacitado.setCapacitadoid(capacitado);
                    evaluacionCapacitadoListNewEvaluacionCapacitado = em.merge(evaluacionCapacitadoListNewEvaluacionCapacitado);
                    if (oldCapacitadoidOfEvaluacionCapacitadoListNewEvaluacionCapacitado != null && !oldCapacitadoidOfEvaluacionCapacitadoListNewEvaluacionCapacitado.equals(capacitado)) {
                        oldCapacitadoidOfEvaluacionCapacitadoListNewEvaluacionCapacitado.getEvaluacionCapacitadoList().remove(evaluacionCapacitadoListNewEvaluacionCapacitado);
                        oldCapacitadoidOfEvaluacionCapacitadoListNewEvaluacionCapacitado = em.merge(oldCapacitadoidOfEvaluacionCapacitadoListNewEvaluacionCapacitado);
                    }
                }
            }
            for (Asistencia asistenciaListNewAsistencia : asistenciaListNew) {
                if (!asistenciaListOld.contains(asistenciaListNewAsistencia)) {
                    Capacitado oldCapacitadoidOfAsistenciaListNewAsistencia = asistenciaListNewAsistencia.getCapacitadoid();
                    asistenciaListNewAsistencia.setCapacitadoid(capacitado);
                    asistenciaListNewAsistencia = em.merge(asistenciaListNewAsistencia);
                    if (oldCapacitadoidOfAsistenciaListNewAsistencia != null && !oldCapacitadoidOfAsistenciaListNewAsistencia.equals(capacitado)) {
                        oldCapacitadoidOfAsistenciaListNewAsistencia.getAsistenciaList().remove(asistenciaListNewAsistencia);
                        oldCapacitadoidOfAsistenciaListNewAsistencia = em.merge(oldCapacitadoidOfAsistenciaListNewAsistencia);
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
                Integer id = capacitado.getId();
                if (findCapacitado(id) == null) {
                    throw new NonexistentEntityException("The capacitado with id " + id + " no longer exists.");
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
            Capacitado capacitado;
            try {
                capacitado = em.getReference(Capacitado.class, id);
                capacitado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The capacitado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Registra> registraListOrphanCheck = capacitado.getRegistraList();
            for (Registra registraListOrphanCheckRegistra : registraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Capacitado (" + capacitado + ") cannot be destroyed since the Registra " + registraListOrphanCheckRegistra + " in its registraList field has a non-nullable capacitadoid field.");
            }
            List<EvaluacionCapacitado> evaluacionCapacitadoListOrphanCheck = capacitado.getEvaluacionCapacitadoList();
            for (EvaluacionCapacitado evaluacionCapacitadoListOrphanCheckEvaluacionCapacitado : evaluacionCapacitadoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Capacitado (" + capacitado + ") cannot be destroyed since the EvaluacionCapacitado " + evaluacionCapacitadoListOrphanCheckEvaluacionCapacitado + " in its evaluacionCapacitadoList field has a non-nullable capacitadoid field.");
            }
            List<Asistencia> asistenciaListOrphanCheck = capacitado.getAsistenciaList();
            for (Asistencia asistenciaListOrphanCheckAsistencia : asistenciaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Capacitado (" + capacitado + ") cannot be destroyed since the Asistencia " + asistenciaListOrphanCheckAsistencia + " in its asistenciaList field has a non-nullable capacitadoid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Carrera carreraId = capacitado.getCarreraId();
            if (carreraId != null) {
                carreraId.getCapacitadoList().remove(capacitado);
                carreraId = em.merge(carreraId);
            }
            Capacitador capacitadorid = capacitado.getCapacitadorid();
            if (capacitadorid != null) {
                capacitadorid.getCapacitadoList().remove(capacitado);
                capacitadorid = em.merge(capacitadorid);
            }
            Sector sectorid = capacitado.getSectorid();
            if (sectorid != null) {
                sectorid.getCapacitadoList().remove(capacitado);
                sectorid = em.merge(sectorid);
            }
            em.remove(capacitado);
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

    public List<Capacitado> findCapacitadoEntities() {
        return findCapacitadoEntities(true, -1, -1);
    }

    public List<Capacitado> findCapacitadoEntities(int maxResults, int firstResult) {
        return findCapacitadoEntities(false, maxResults, firstResult);
    }

    private List<Capacitado> findCapacitadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Capacitado.class));
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

    public Capacitado findCapacitado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Capacitado.class, id);
        } finally {
            em.close();
        }
    }

    public int getCapacitadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Capacitado> rt = cq.from(Capacitado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
