/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jpa.controllers;

import com.jpa.controllers.exceptions.NonexistentEntityException;
import com.jpa.controllers.exceptions.RollbackFailureException;
import com.jpa.entities.Administrador;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.jpa.entities.Capacitador;
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
public class AdministradorJpaController implements Serializable {

    // Modificar Constructor
    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "TesisCapacitySoftPU")
    private EntityManagerFactory emf = null;
    InitialContext ic = new InitialContext();

    public  AdministradorJpaController() throws NamingException {
        utx = (UserTransaction) ic.lookup("java:comp/UserTransaction");
        emf = Persistence
                .createEntityManagerFactory("TesisCapacitySoftPU");
    }
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Administrador administrador) throws RollbackFailureException, Exception {
        if (administrador.getCapacitadorList() == null) {
            administrador.setCapacitadorList(new ArrayList<Capacitador>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Capacitador> attachedCapacitadorList = new ArrayList<Capacitador>();
            for (Capacitador capacitadorListCapacitadorToAttach : administrador.getCapacitadorList()) {
                capacitadorListCapacitadorToAttach = em.getReference(capacitadorListCapacitadorToAttach.getClass(), capacitadorListCapacitadorToAttach.getId());
                attachedCapacitadorList.add(capacitadorListCapacitadorToAttach);
            }
            administrador.setCapacitadorList(attachedCapacitadorList);
            em.persist(administrador);
            for (Capacitador capacitadorListCapacitador : administrador.getCapacitadorList()) {
                Administrador oldAdministradoridOfCapacitadorListCapacitador = capacitadorListCapacitador.getAdministradorid();
                capacitadorListCapacitador.setAdministradorid(administrador);
                capacitadorListCapacitador = em.merge(capacitadorListCapacitador);
                if (oldAdministradoridOfCapacitadorListCapacitador != null) {
                    oldAdministradoridOfCapacitadorListCapacitador.getCapacitadorList().remove(capacitadorListCapacitador);
                    oldAdministradoridOfCapacitadorListCapacitador = em.merge(oldAdministradoridOfCapacitadorListCapacitador);
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

    public void edit(Administrador administrador) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Administrador persistentAdministrador = em.find(Administrador.class, administrador.getId());
            List<Capacitador> capacitadorListOld = persistentAdministrador.getCapacitadorList();
            List<Capacitador> capacitadorListNew = administrador.getCapacitadorList();
            List<Capacitador> attachedCapacitadorListNew = new ArrayList<Capacitador>();
            for (Capacitador capacitadorListNewCapacitadorToAttach : capacitadorListNew) {
                capacitadorListNewCapacitadorToAttach = em.getReference(capacitadorListNewCapacitadorToAttach.getClass(), capacitadorListNewCapacitadorToAttach.getId());
                attachedCapacitadorListNew.add(capacitadorListNewCapacitadorToAttach);
            }
            capacitadorListNew = attachedCapacitadorListNew;
            administrador.setCapacitadorList(capacitadorListNew);
            administrador = em.merge(administrador);
            for (Capacitador capacitadorListOldCapacitador : capacitadorListOld) {
                if (!capacitadorListNew.contains(capacitadorListOldCapacitador)) {
                    capacitadorListOldCapacitador.setAdministradorid(null);
                    capacitadorListOldCapacitador = em.merge(capacitadorListOldCapacitador);
                }
            }
            for (Capacitador capacitadorListNewCapacitador : capacitadorListNew) {
                if (!capacitadorListOld.contains(capacitadorListNewCapacitador)) {
                    Administrador oldAdministradoridOfCapacitadorListNewCapacitador = capacitadorListNewCapacitador.getAdministradorid();
                    capacitadorListNewCapacitador.setAdministradorid(administrador);
                    capacitadorListNewCapacitador = em.merge(capacitadorListNewCapacitador);
                    if (oldAdministradoridOfCapacitadorListNewCapacitador != null && !oldAdministradoridOfCapacitadorListNewCapacitador.equals(administrador)) {
                        oldAdministradoridOfCapacitadorListNewCapacitador.getCapacitadorList().remove(capacitadorListNewCapacitador);
                        oldAdministradoridOfCapacitadorListNewCapacitador = em.merge(oldAdministradoridOfCapacitadorListNewCapacitador);
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
                Integer id = administrador.getId();
                if (findAdministrador(id) == null) {
                    throw new NonexistentEntityException("The administrador with id " + id + " no longer exists.");
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
            Administrador administrador;
            try {
                administrador = em.getReference(Administrador.class, id);
                administrador.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The administrador with id " + id + " no longer exists.", enfe);
            }
            List<Capacitador> capacitadorList = administrador.getCapacitadorList();
            for (Capacitador capacitadorListCapacitador : capacitadorList) {
                capacitadorListCapacitador.setAdministradorid(null);
                capacitadorListCapacitador = em.merge(capacitadorListCapacitador);
            }
            em.remove(administrador);
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

    public List<Administrador> findAdministradorEntities() {
        return findAdministradorEntities(true, -1, -1);
    }

    public List<Administrador> findAdministradorEntities(int maxResults, int firstResult) {
        return findAdministradorEntities(false, maxResults, firstResult);
    }

    private List<Administrador> findAdministradorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Administrador.class));
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

    public Administrador findAdministrador(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Administrador.class, id);
        } finally {
            em.close();
        }
    }

    public int getAdministradorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Administrador> rt = cq.from(Administrador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
