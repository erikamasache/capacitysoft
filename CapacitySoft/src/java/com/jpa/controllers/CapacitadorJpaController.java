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
import com.jpa.entities.Administrador;
import com.jpa.entities.CapacitacionCapacitador;
import java.util.ArrayList;
import java.util.List;
import com.jpa.entities.Capacitado;
import com.jpa.entities.Capacitador;
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
public class CapacitadorJpaController implements Serializable {

     // Modificar Constructor
    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "TesisCapacitySoftPU")
    private EntityManagerFactory emf = null;
    InitialContext ic = new InitialContext();

    public  CapacitadorJpaController() throws NamingException {
        utx = (UserTransaction) ic.lookup("java:comp/UserTransaction");
        emf = Persistence
                .createEntityManagerFactory("TesisCapacitySoftPU");
    }
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Capacitador capacitador) throws RollbackFailureException, Exception {
        if (capacitador.getCapacitacionCapacitadorList() == null) {
            capacitador.setCapacitacionCapacitadorList(new ArrayList<CapacitacionCapacitador>());
        }
        if (capacitador.getCapacitadoList() == null) {
            capacitador.setCapacitadoList(new ArrayList<Capacitado>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Administrador administradorid = capacitador.getAdministradorid();
            if (administradorid != null) {
                administradorid = em.getReference(administradorid.getClass(), administradorid.getId());
                capacitador.setAdministradorid(administradorid);
            }
            List<CapacitacionCapacitador> attachedCapacitacionCapacitadorList = new ArrayList<CapacitacionCapacitador>();
            for (CapacitacionCapacitador capacitacionCapacitadorListCapacitacionCapacitadorToAttach : capacitador.getCapacitacionCapacitadorList()) {
                capacitacionCapacitadorListCapacitacionCapacitadorToAttach = em.getReference(capacitacionCapacitadorListCapacitacionCapacitadorToAttach.getClass(), capacitacionCapacitadorListCapacitacionCapacitadorToAttach.getId());
                attachedCapacitacionCapacitadorList.add(capacitacionCapacitadorListCapacitacionCapacitadorToAttach);
            }
            capacitador.setCapacitacionCapacitadorList(attachedCapacitacionCapacitadorList);
            List<Capacitado> attachedCapacitadoList = new ArrayList<Capacitado>();
            for (Capacitado capacitadoListCapacitadoToAttach : capacitador.getCapacitadoList()) {
                capacitadoListCapacitadoToAttach = em.getReference(capacitadoListCapacitadoToAttach.getClass(), capacitadoListCapacitadoToAttach.getId());
                attachedCapacitadoList.add(capacitadoListCapacitadoToAttach);
            }
            capacitador.setCapacitadoList(attachedCapacitadoList);
            em.persist(capacitador);
            if (administradorid != null) {
                administradorid.getCapacitadorList().add(capacitador);
                administradorid = em.merge(administradorid);
            }
            for (CapacitacionCapacitador capacitacionCapacitadorListCapacitacionCapacitador : capacitador.getCapacitacionCapacitadorList()) {
                Capacitador oldCapacitadoridOfCapacitacionCapacitadorListCapacitacionCapacitador = capacitacionCapacitadorListCapacitacionCapacitador.getCapacitadorid();
                capacitacionCapacitadorListCapacitacionCapacitador.setCapacitadorid(capacitador);
                capacitacionCapacitadorListCapacitacionCapacitador = em.merge(capacitacionCapacitadorListCapacitacionCapacitador);
                if (oldCapacitadoridOfCapacitacionCapacitadorListCapacitacionCapacitador != null) {
                    oldCapacitadoridOfCapacitacionCapacitadorListCapacitacionCapacitador.getCapacitacionCapacitadorList().remove(capacitacionCapacitadorListCapacitacionCapacitador);
                    oldCapacitadoridOfCapacitacionCapacitadorListCapacitacionCapacitador = em.merge(oldCapacitadoridOfCapacitacionCapacitadorListCapacitacionCapacitador);
                }
            }
            for (Capacitado capacitadoListCapacitado : capacitador.getCapacitadoList()) {
                Capacitador oldCapacitadoridOfCapacitadoListCapacitado = capacitadoListCapacitado.getCapacitadorid();
                capacitadoListCapacitado.setCapacitadorid(capacitador);
                capacitadoListCapacitado = em.merge(capacitadoListCapacitado);
                if (oldCapacitadoridOfCapacitadoListCapacitado != null) {
                    oldCapacitadoridOfCapacitadoListCapacitado.getCapacitadoList().remove(capacitadoListCapacitado);
                    oldCapacitadoridOfCapacitadoListCapacitado = em.merge(oldCapacitadoridOfCapacitadoListCapacitado);
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

    public void edit(Capacitador capacitador) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Capacitador persistentCapacitador = em.find(Capacitador.class, capacitador.getId());
            Administrador administradoridOld = persistentCapacitador.getAdministradorid();
            Administrador administradoridNew = capacitador.getAdministradorid();
            List<CapacitacionCapacitador> capacitacionCapacitadorListOld = persistentCapacitador.getCapacitacionCapacitadorList();
            List<CapacitacionCapacitador> capacitacionCapacitadorListNew = capacitador.getCapacitacionCapacitadorList();
            List<Capacitado> capacitadoListOld = persistentCapacitador.getCapacitadoList();
            List<Capacitado> capacitadoListNew = capacitador.getCapacitadoList();
            List<String> illegalOrphanMessages = null;
            for (CapacitacionCapacitador capacitacionCapacitadorListOldCapacitacionCapacitador : capacitacionCapacitadorListOld) {
                if (!capacitacionCapacitadorListNew.contains(capacitacionCapacitadorListOldCapacitacionCapacitador)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CapacitacionCapacitador " + capacitacionCapacitadorListOldCapacitacionCapacitador + " since its capacitadorid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (administradoridNew != null) {
                administradoridNew = em.getReference(administradoridNew.getClass(), administradoridNew.getId());
                capacitador.setAdministradorid(administradoridNew);
            }
            List<CapacitacionCapacitador> attachedCapacitacionCapacitadorListNew = new ArrayList<CapacitacionCapacitador>();
            for (CapacitacionCapacitador capacitacionCapacitadorListNewCapacitacionCapacitadorToAttach : capacitacionCapacitadorListNew) {
                capacitacionCapacitadorListNewCapacitacionCapacitadorToAttach = em.getReference(capacitacionCapacitadorListNewCapacitacionCapacitadorToAttach.getClass(), capacitacionCapacitadorListNewCapacitacionCapacitadorToAttach.getId());
                attachedCapacitacionCapacitadorListNew.add(capacitacionCapacitadorListNewCapacitacionCapacitadorToAttach);
            }
            capacitacionCapacitadorListNew = attachedCapacitacionCapacitadorListNew;
            capacitador.setCapacitacionCapacitadorList(capacitacionCapacitadorListNew);
            List<Capacitado> attachedCapacitadoListNew = new ArrayList<Capacitado>();
            for (Capacitado capacitadoListNewCapacitadoToAttach : capacitadoListNew) {
                capacitadoListNewCapacitadoToAttach = em.getReference(capacitadoListNewCapacitadoToAttach.getClass(), capacitadoListNewCapacitadoToAttach.getId());
                attachedCapacitadoListNew.add(capacitadoListNewCapacitadoToAttach);
            }
            capacitadoListNew = attachedCapacitadoListNew;
            capacitador.setCapacitadoList(capacitadoListNew);
            capacitador = em.merge(capacitador);
            if (administradoridOld != null && !administradoridOld.equals(administradoridNew)) {
                administradoridOld.getCapacitadorList().remove(capacitador);
                administradoridOld = em.merge(administradoridOld);
            }
            if (administradoridNew != null && !administradoridNew.equals(administradoridOld)) {
                administradoridNew.getCapacitadorList().add(capacitador);
                administradoridNew = em.merge(administradoridNew);
            }
            for (CapacitacionCapacitador capacitacionCapacitadorListNewCapacitacionCapacitador : capacitacionCapacitadorListNew) {
                if (!capacitacionCapacitadorListOld.contains(capacitacionCapacitadorListNewCapacitacionCapacitador)) {
                    Capacitador oldCapacitadoridOfCapacitacionCapacitadorListNewCapacitacionCapacitador = capacitacionCapacitadorListNewCapacitacionCapacitador.getCapacitadorid();
                    capacitacionCapacitadorListNewCapacitacionCapacitador.setCapacitadorid(capacitador);
                    capacitacionCapacitadorListNewCapacitacionCapacitador = em.merge(capacitacionCapacitadorListNewCapacitacionCapacitador);
                    if (oldCapacitadoridOfCapacitacionCapacitadorListNewCapacitacionCapacitador != null && !oldCapacitadoridOfCapacitacionCapacitadorListNewCapacitacionCapacitador.equals(capacitador)) {
                        oldCapacitadoridOfCapacitacionCapacitadorListNewCapacitacionCapacitador.getCapacitacionCapacitadorList().remove(capacitacionCapacitadorListNewCapacitacionCapacitador);
                        oldCapacitadoridOfCapacitacionCapacitadorListNewCapacitacionCapacitador = em.merge(oldCapacitadoridOfCapacitacionCapacitadorListNewCapacitacionCapacitador);
                    }
                }
            }
            for (Capacitado capacitadoListOldCapacitado : capacitadoListOld) {
                if (!capacitadoListNew.contains(capacitadoListOldCapacitado)) {
                    capacitadoListOldCapacitado.setCapacitadorid(null);
                    capacitadoListOldCapacitado = em.merge(capacitadoListOldCapacitado);
                }
            }
            for (Capacitado capacitadoListNewCapacitado : capacitadoListNew) {
                if (!capacitadoListOld.contains(capacitadoListNewCapacitado)) {
                    Capacitador oldCapacitadoridOfCapacitadoListNewCapacitado = capacitadoListNewCapacitado.getCapacitadorid();
                    capacitadoListNewCapacitado.setCapacitadorid(capacitador);
                    capacitadoListNewCapacitado = em.merge(capacitadoListNewCapacitado);
                    if (oldCapacitadoridOfCapacitadoListNewCapacitado != null && !oldCapacitadoridOfCapacitadoListNewCapacitado.equals(capacitador)) {
                        oldCapacitadoridOfCapacitadoListNewCapacitado.getCapacitadoList().remove(capacitadoListNewCapacitado);
                        oldCapacitadoridOfCapacitadoListNewCapacitado = em.merge(oldCapacitadoridOfCapacitadoListNewCapacitado);
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
                Integer id = capacitador.getId();
                if (findCapacitador(id) == null) {
                    throw new NonexistentEntityException("The capacitador with id " + id + " no longer exists.");
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
            Capacitador capacitador;
            try {
                capacitador = em.getReference(Capacitador.class, id);
                capacitador.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The capacitador with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<CapacitacionCapacitador> capacitacionCapacitadorListOrphanCheck = capacitador.getCapacitacionCapacitadorList();
            for (CapacitacionCapacitador capacitacionCapacitadorListOrphanCheckCapacitacionCapacitador : capacitacionCapacitadorListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Capacitador (" + capacitador + ") cannot be destroyed since the CapacitacionCapacitador " + capacitacionCapacitadorListOrphanCheckCapacitacionCapacitador + " in its capacitacionCapacitadorList field has a non-nullable capacitadorid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Administrador administradorid = capacitador.getAdministradorid();
            if (administradorid != null) {
                administradorid.getCapacitadorList().remove(capacitador);
                administradorid = em.merge(administradorid);
            }
            List<Capacitado> capacitadoList = capacitador.getCapacitadoList();
            for (Capacitado capacitadoListCapacitado : capacitadoList) {
                capacitadoListCapacitado.setCapacitadorid(null);
                capacitadoListCapacitado = em.merge(capacitadoListCapacitado);
            }
            em.remove(capacitador);
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

    public List<Capacitador> findCapacitadorEntities() {
        return findCapacitadorEntities(true, -1, -1);
    }

    public List<Capacitador> findCapacitadorEntities(int maxResults, int firstResult) {
        return findCapacitadorEntities(false, maxResults, firstResult);
    }

    private List<Capacitador> findCapacitadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Capacitador.class));
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

    public Capacitador findCapacitador(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Capacitador.class, id);
        } finally {
            em.close();
        }
    }

    public int getCapacitadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Capacitador> rt = cq.from(Capacitador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
