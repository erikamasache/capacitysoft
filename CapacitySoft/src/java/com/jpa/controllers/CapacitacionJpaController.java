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
import com.jpa.entities.Evaluacion;
import com.jpa.entities.Sector;
import com.jpa.entities.CapacitacionCapacitador;
import java.util.ArrayList;
import java.util.List;
import com.jpa.entities.Registra;
import com.jpa.entities.Asistencia;
import com.jpa.entities.Capacitacion;
import com.jpa.entities.Recurso;
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
public class CapacitacionJpaController implements Serializable {

     // Modificar Constructor
    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "TesisCapacitySoftPU")
    private EntityManagerFactory emf = null;
    InitialContext ic = new InitialContext();

    public  CapacitacionJpaController() throws NamingException {
        utx = (UserTransaction) ic.lookup("java:comp/UserTransaction");
        emf = Persistence
                .createEntityManagerFactory("TesisCapacitySoftPU");
    }
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Capacitacion capacitacion) throws RollbackFailureException, Exception {
        if (capacitacion.getCapacitacionCapacitadorList() == null) {
            capacitacion.setCapacitacionCapacitadorList(new ArrayList<CapacitacionCapacitador>());
        }
        if (capacitacion.getRegistraList() == null) {
            capacitacion.setRegistraList(new ArrayList<Registra>());
        }
        if (capacitacion.getAsistenciaList() == null) {
            capacitacion.setAsistenciaList(new ArrayList<Asistencia>());
        }
        if (capacitacion.getRecursoList() == null) {
            capacitacion.setRecursoList(new ArrayList<Recurso>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Evaluacion evaluacionId = capacitacion.getEvaluacionId();
            if (evaluacionId != null) {
                evaluacionId = em.getReference(evaluacionId.getClass(), evaluacionId.getId());
                capacitacion.setEvaluacionId(evaluacionId);
            }
            Sector sectorId = capacitacion.getSectorId();
            if (sectorId != null) {
                sectorId = em.getReference(sectorId.getClass(), sectorId.getId());
                capacitacion.setSectorId(sectorId);
            }
            List<CapacitacionCapacitador> attachedCapacitacionCapacitadorList = new ArrayList<CapacitacionCapacitador>();
            for (CapacitacionCapacitador capacitacionCapacitadorListCapacitacionCapacitadorToAttach : capacitacion.getCapacitacionCapacitadorList()) {
                capacitacionCapacitadorListCapacitacionCapacitadorToAttach = em.getReference(capacitacionCapacitadorListCapacitacionCapacitadorToAttach.getClass(), capacitacionCapacitadorListCapacitacionCapacitadorToAttach.getId());
                attachedCapacitacionCapacitadorList.add(capacitacionCapacitadorListCapacitacionCapacitadorToAttach);
            }
            capacitacion.setCapacitacionCapacitadorList(attachedCapacitacionCapacitadorList);
            List<Registra> attachedRegistraList = new ArrayList<Registra>();
            for (Registra registraListRegistraToAttach : capacitacion.getRegistraList()) {
                registraListRegistraToAttach = em.getReference(registraListRegistraToAttach.getClass(), registraListRegistraToAttach.getId());
                attachedRegistraList.add(registraListRegistraToAttach);
            }
            capacitacion.setRegistraList(attachedRegistraList);
            List<Asistencia> attachedAsistenciaList = new ArrayList<Asistencia>();
            for (Asistencia asistenciaListAsistenciaToAttach : capacitacion.getAsistenciaList()) {
                asistenciaListAsistenciaToAttach = em.getReference(asistenciaListAsistenciaToAttach.getClass(), asistenciaListAsistenciaToAttach.getId());
                attachedAsistenciaList.add(asistenciaListAsistenciaToAttach);
            }
            capacitacion.setAsistenciaList(attachedAsistenciaList);
            List<Recurso> attachedRecursoList = new ArrayList<Recurso>();
            for (Recurso recursoListRecursoToAttach : capacitacion.getRecursoList()) {
                recursoListRecursoToAttach = em.getReference(recursoListRecursoToAttach.getClass(), recursoListRecursoToAttach.getId());
                attachedRecursoList.add(recursoListRecursoToAttach);
            }
            capacitacion.setRecursoList(attachedRecursoList);
            em.persist(capacitacion);
            if (evaluacionId != null) {
                evaluacionId.getCapacitacionList().add(capacitacion);
                evaluacionId = em.merge(evaluacionId);
            }
            if (sectorId != null) {
                sectorId.getCapacitacionList().add(capacitacion);
                sectorId = em.merge(sectorId);
            }
            for (CapacitacionCapacitador capacitacionCapacitadorListCapacitacionCapacitador : capacitacion.getCapacitacionCapacitadorList()) {
                Capacitacion oldCapacitacionidOfCapacitacionCapacitadorListCapacitacionCapacitador = capacitacionCapacitadorListCapacitacionCapacitador.getCapacitacionid();
                capacitacionCapacitadorListCapacitacionCapacitador.setCapacitacionid(capacitacion);
                capacitacionCapacitadorListCapacitacionCapacitador = em.merge(capacitacionCapacitadorListCapacitacionCapacitador);
                if (oldCapacitacionidOfCapacitacionCapacitadorListCapacitacionCapacitador != null) {
                    oldCapacitacionidOfCapacitacionCapacitadorListCapacitacionCapacitador.getCapacitacionCapacitadorList().remove(capacitacionCapacitadorListCapacitacionCapacitador);
                    oldCapacitacionidOfCapacitacionCapacitadorListCapacitacionCapacitador = em.merge(oldCapacitacionidOfCapacitacionCapacitadorListCapacitacionCapacitador);
                }
            }
            for (Registra registraListRegistra : capacitacion.getRegistraList()) {
                Capacitacion oldCapacitacionidOfRegistraListRegistra = registraListRegistra.getCapacitacionid();
                registraListRegistra.setCapacitacionid(capacitacion);
                registraListRegistra = em.merge(registraListRegistra);
                if (oldCapacitacionidOfRegistraListRegistra != null) {
                    oldCapacitacionidOfRegistraListRegistra.getRegistraList().remove(registraListRegistra);
                    oldCapacitacionidOfRegistraListRegistra = em.merge(oldCapacitacionidOfRegistraListRegistra);
                }
            }
            for (Asistencia asistenciaListAsistencia : capacitacion.getAsistenciaList()) {
                Capacitacion oldCapacitacionidOfAsistenciaListAsistencia = asistenciaListAsistencia.getCapacitacionid();
                asistenciaListAsistencia.setCapacitacionid(capacitacion);
                asistenciaListAsistencia = em.merge(asistenciaListAsistencia);
                if (oldCapacitacionidOfAsistenciaListAsistencia != null) {
                    oldCapacitacionidOfAsistenciaListAsistencia.getAsistenciaList().remove(asistenciaListAsistencia);
                    oldCapacitacionidOfAsistenciaListAsistencia = em.merge(oldCapacitacionidOfAsistenciaListAsistencia);
                }
            }
            for (Recurso recursoListRecurso : capacitacion.getRecursoList()) {
                Capacitacion oldCapacitacionidOfRecursoListRecurso = recursoListRecurso.getCapacitacionid();
                recursoListRecurso.setCapacitacionid(capacitacion);
                recursoListRecurso = em.merge(recursoListRecurso);
                if (oldCapacitacionidOfRecursoListRecurso != null) {
                    oldCapacitacionidOfRecursoListRecurso.getRecursoList().remove(recursoListRecurso);
                    oldCapacitacionidOfRecursoListRecurso = em.merge(oldCapacitacionidOfRecursoListRecurso);
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

    public void edit(Capacitacion capacitacion) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Capacitacion persistentCapacitacion = em.find(Capacitacion.class, capacitacion.getId());
            Evaluacion evaluacionIdOld = persistentCapacitacion.getEvaluacionId();
            Evaluacion evaluacionIdNew = capacitacion.getEvaluacionId();
            Sector sectorIdOld = persistentCapacitacion.getSectorId();
            Sector sectorIdNew = capacitacion.getSectorId();
            List<CapacitacionCapacitador> capacitacionCapacitadorListOld = persistentCapacitacion.getCapacitacionCapacitadorList();
            List<CapacitacionCapacitador> capacitacionCapacitadorListNew = capacitacion.getCapacitacionCapacitadorList();
            List<Registra> registraListOld = persistentCapacitacion.getRegistraList();
            List<Registra> registraListNew = capacitacion.getRegistraList();
            List<Asistencia> asistenciaListOld = persistentCapacitacion.getAsistenciaList();
            List<Asistencia> asistenciaListNew = capacitacion.getAsistenciaList();
            List<Recurso> recursoListOld = persistentCapacitacion.getRecursoList();
            List<Recurso> recursoListNew = capacitacion.getRecursoList();
            List<String> illegalOrphanMessages = null;
            for (CapacitacionCapacitador capacitacionCapacitadorListOldCapacitacionCapacitador : capacitacionCapacitadorListOld) {
                if (!capacitacionCapacitadorListNew.contains(capacitacionCapacitadorListOldCapacitacionCapacitador)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CapacitacionCapacitador " + capacitacionCapacitadorListOldCapacitacionCapacitador + " since its capacitacionid field is not nullable.");
                }
            }
            for (Registra registraListOldRegistra : registraListOld) {
                if (!registraListNew.contains(registraListOldRegistra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Registra " + registraListOldRegistra + " since its capacitacionid field is not nullable.");
                }
            }
            for (Asistencia asistenciaListOldAsistencia : asistenciaListOld) {
                if (!asistenciaListNew.contains(asistenciaListOldAsistencia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Asistencia " + asistenciaListOldAsistencia + " since its capacitacionid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (evaluacionIdNew != null) {
                evaluacionIdNew = em.getReference(evaluacionIdNew.getClass(), evaluacionIdNew.getId());
                capacitacion.setEvaluacionId(evaluacionIdNew);
            }
            if (sectorIdNew != null) {
                sectorIdNew = em.getReference(sectorIdNew.getClass(), sectorIdNew.getId());
                capacitacion.setSectorId(sectorIdNew);
            }
            List<CapacitacionCapacitador> attachedCapacitacionCapacitadorListNew = new ArrayList<CapacitacionCapacitador>();
            for (CapacitacionCapacitador capacitacionCapacitadorListNewCapacitacionCapacitadorToAttach : capacitacionCapacitadorListNew) {
                capacitacionCapacitadorListNewCapacitacionCapacitadorToAttach = em.getReference(capacitacionCapacitadorListNewCapacitacionCapacitadorToAttach.getClass(), capacitacionCapacitadorListNewCapacitacionCapacitadorToAttach.getId());
                attachedCapacitacionCapacitadorListNew.add(capacitacionCapacitadorListNewCapacitacionCapacitadorToAttach);
            }
            capacitacionCapacitadorListNew = attachedCapacitacionCapacitadorListNew;
            capacitacion.setCapacitacionCapacitadorList(capacitacionCapacitadorListNew);
            List<Registra> attachedRegistraListNew = new ArrayList<Registra>();
            for (Registra registraListNewRegistraToAttach : registraListNew) {
                registraListNewRegistraToAttach = em.getReference(registraListNewRegistraToAttach.getClass(), registraListNewRegistraToAttach.getId());
                attachedRegistraListNew.add(registraListNewRegistraToAttach);
            }
            registraListNew = attachedRegistraListNew;
            capacitacion.setRegistraList(registraListNew);
            List<Asistencia> attachedAsistenciaListNew = new ArrayList<Asistencia>();
            for (Asistencia asistenciaListNewAsistenciaToAttach : asistenciaListNew) {
                asistenciaListNewAsistenciaToAttach = em.getReference(asistenciaListNewAsistenciaToAttach.getClass(), asistenciaListNewAsistenciaToAttach.getId());
                attachedAsistenciaListNew.add(asistenciaListNewAsistenciaToAttach);
            }
            asistenciaListNew = attachedAsistenciaListNew;
            capacitacion.setAsistenciaList(asistenciaListNew);
            List<Recurso> attachedRecursoListNew = new ArrayList<Recurso>();
            for (Recurso recursoListNewRecursoToAttach : recursoListNew) {
                recursoListNewRecursoToAttach = em.getReference(recursoListNewRecursoToAttach.getClass(), recursoListNewRecursoToAttach.getId());
                attachedRecursoListNew.add(recursoListNewRecursoToAttach);
            }
            recursoListNew = attachedRecursoListNew;
            capacitacion.setRecursoList(recursoListNew);
            capacitacion = em.merge(capacitacion);
            if (evaluacionIdOld != null && !evaluacionIdOld.equals(evaluacionIdNew)) {
                evaluacionIdOld.getCapacitacionList().remove(capacitacion);
                evaluacionIdOld = em.merge(evaluacionIdOld);
            }
            if (evaluacionIdNew != null && !evaluacionIdNew.equals(evaluacionIdOld)) {
                evaluacionIdNew.getCapacitacionList().add(capacitacion);
                evaluacionIdNew = em.merge(evaluacionIdNew);
            }
            if (sectorIdOld != null && !sectorIdOld.equals(sectorIdNew)) {
                sectorIdOld.getCapacitacionList().remove(capacitacion);
                sectorIdOld = em.merge(sectorIdOld);
            }
            if (sectorIdNew != null && !sectorIdNew.equals(sectorIdOld)) {
                sectorIdNew.getCapacitacionList().add(capacitacion);
                sectorIdNew = em.merge(sectorIdNew);
            }
            for (CapacitacionCapacitador capacitacionCapacitadorListNewCapacitacionCapacitador : capacitacionCapacitadorListNew) {
                if (!capacitacionCapacitadorListOld.contains(capacitacionCapacitadorListNewCapacitacionCapacitador)) {
                    Capacitacion oldCapacitacionidOfCapacitacionCapacitadorListNewCapacitacionCapacitador = capacitacionCapacitadorListNewCapacitacionCapacitador.getCapacitacionid();
                    capacitacionCapacitadorListNewCapacitacionCapacitador.setCapacitacionid(capacitacion);
                    capacitacionCapacitadorListNewCapacitacionCapacitador = em.merge(capacitacionCapacitadorListNewCapacitacionCapacitador);
                    if (oldCapacitacionidOfCapacitacionCapacitadorListNewCapacitacionCapacitador != null && !oldCapacitacionidOfCapacitacionCapacitadorListNewCapacitacionCapacitador.equals(capacitacion)) {
                        oldCapacitacionidOfCapacitacionCapacitadorListNewCapacitacionCapacitador.getCapacitacionCapacitadorList().remove(capacitacionCapacitadorListNewCapacitacionCapacitador);
                        oldCapacitacionidOfCapacitacionCapacitadorListNewCapacitacionCapacitador = em.merge(oldCapacitacionidOfCapacitacionCapacitadorListNewCapacitacionCapacitador);
                    }
                }
            }
            for (Registra registraListNewRegistra : registraListNew) {
                if (!registraListOld.contains(registraListNewRegistra)) {
                    Capacitacion oldCapacitacionidOfRegistraListNewRegistra = registraListNewRegistra.getCapacitacionid();
                    registraListNewRegistra.setCapacitacionid(capacitacion);
                    registraListNewRegistra = em.merge(registraListNewRegistra);
                    if (oldCapacitacionidOfRegistraListNewRegistra != null && !oldCapacitacionidOfRegistraListNewRegistra.equals(capacitacion)) {
                        oldCapacitacionidOfRegistraListNewRegistra.getRegistraList().remove(registraListNewRegistra);
                        oldCapacitacionidOfRegistraListNewRegistra = em.merge(oldCapacitacionidOfRegistraListNewRegistra);
                    }
                }
            }
            for (Asistencia asistenciaListNewAsistencia : asistenciaListNew) {
                if (!asistenciaListOld.contains(asistenciaListNewAsistencia)) {
                    Capacitacion oldCapacitacionidOfAsistenciaListNewAsistencia = asistenciaListNewAsistencia.getCapacitacionid();
                    asistenciaListNewAsistencia.setCapacitacionid(capacitacion);
                    asistenciaListNewAsistencia = em.merge(asistenciaListNewAsistencia);
                    if (oldCapacitacionidOfAsistenciaListNewAsistencia != null && !oldCapacitacionidOfAsistenciaListNewAsistencia.equals(capacitacion)) {
                        oldCapacitacionidOfAsistenciaListNewAsistencia.getAsistenciaList().remove(asistenciaListNewAsistencia);
                        oldCapacitacionidOfAsistenciaListNewAsistencia = em.merge(oldCapacitacionidOfAsistenciaListNewAsistencia);
                    }
                }
            }
            for (Recurso recursoListOldRecurso : recursoListOld) {
                if (!recursoListNew.contains(recursoListOldRecurso)) {
                    recursoListOldRecurso.setCapacitacionid(null);
                    recursoListOldRecurso = em.merge(recursoListOldRecurso);
                }
            }
            for (Recurso recursoListNewRecurso : recursoListNew) {
                if (!recursoListOld.contains(recursoListNewRecurso)) {
                    Capacitacion oldCapacitacionidOfRecursoListNewRecurso = recursoListNewRecurso.getCapacitacionid();
                    recursoListNewRecurso.setCapacitacionid(capacitacion);
                    recursoListNewRecurso = em.merge(recursoListNewRecurso);
                    if (oldCapacitacionidOfRecursoListNewRecurso != null && !oldCapacitacionidOfRecursoListNewRecurso.equals(capacitacion)) {
                        oldCapacitacionidOfRecursoListNewRecurso.getRecursoList().remove(recursoListNewRecurso);
                        oldCapacitacionidOfRecursoListNewRecurso = em.merge(oldCapacitacionidOfRecursoListNewRecurso);
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
                Integer id = capacitacion.getId();
                if (findCapacitacion(id) == null) {
                    throw new NonexistentEntityException("The capacitacion with id " + id + " no longer exists.");
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
            Capacitacion capacitacion;
            try {
                capacitacion = em.getReference(Capacitacion.class, id);
                capacitacion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The capacitacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<CapacitacionCapacitador> capacitacionCapacitadorListOrphanCheck = capacitacion.getCapacitacionCapacitadorList();
            for (CapacitacionCapacitador capacitacionCapacitadorListOrphanCheckCapacitacionCapacitador : capacitacionCapacitadorListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Capacitacion (" + capacitacion + ") cannot be destroyed since the CapacitacionCapacitador " + capacitacionCapacitadorListOrphanCheckCapacitacionCapacitador + " in its capacitacionCapacitadorList field has a non-nullable capacitacionid field.");
            }
            List<Registra> registraListOrphanCheck = capacitacion.getRegistraList();
            for (Registra registraListOrphanCheckRegistra : registraListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Capacitacion (" + capacitacion + ") cannot be destroyed since the Registra " + registraListOrphanCheckRegistra + " in its registraList field has a non-nullable capacitacionid field.");
            }
            List<Asistencia> asistenciaListOrphanCheck = capacitacion.getAsistenciaList();
            for (Asistencia asistenciaListOrphanCheckAsistencia : asistenciaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Capacitacion (" + capacitacion + ") cannot be destroyed since the Asistencia " + asistenciaListOrphanCheckAsistencia + " in its asistenciaList field has a non-nullable capacitacionid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Evaluacion evaluacionId = capacitacion.getEvaluacionId();
            if (evaluacionId != null) {
                evaluacionId.getCapacitacionList().remove(capacitacion);
                evaluacionId = em.merge(evaluacionId);
            }
            Sector sectorId = capacitacion.getSectorId();
            if (sectorId != null) {
                sectorId.getCapacitacionList().remove(capacitacion);
                sectorId = em.merge(sectorId);
            }
            List<Recurso> recursoList = capacitacion.getRecursoList();
            for (Recurso recursoListRecurso : recursoList) {
                recursoListRecurso.setCapacitacionid(null);
                recursoListRecurso = em.merge(recursoListRecurso);
            }
            em.remove(capacitacion);
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

    public List<Capacitacion> findCapacitacionEntities() {
        return findCapacitacionEntities(true, -1, -1);
    }

    public List<Capacitacion> findCapacitacionEntities(int maxResults, int firstResult) {
        return findCapacitacionEntities(false, maxResults, firstResult);
    }

    private List<Capacitacion> findCapacitacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Capacitacion.class));
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

    public Capacitacion findCapacitacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Capacitacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getCapacitacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Capacitacion> rt = cq.from(Capacitacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
