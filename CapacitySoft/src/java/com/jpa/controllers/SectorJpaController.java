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
import com.jpa.entities.Responsable;
import java.util.ArrayList;
import java.util.List;
import com.jpa.entities.Capacitacion;
import com.jpa.entities.Carrera;
import com.jpa.entities.Capacitado;
import com.jpa.entities.Sector;
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
public class SectorJpaController implements Serializable {

     // Modificar Constructor
    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "TesisCapacitySoftPU")
    private EntityManagerFactory emf = null;
    InitialContext ic = new InitialContext();

    public  SectorJpaController() throws NamingException {
        utx = (UserTransaction) ic.lookup("java:comp/UserTransaction");
        emf = Persistence
                .createEntityManagerFactory("TesisCapacitySoftPU");
    }
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sector sector) throws RollbackFailureException, Exception {
        if (sector.getResponsableList() == null) {
            sector.setResponsableList(new ArrayList<Responsable>());
        }
        if (sector.getCapacitacionList() == null) {
            sector.setCapacitacionList(new ArrayList<Capacitacion>());
        }
        if (sector.getCarreraList() == null) {
            sector.setCarreraList(new ArrayList<Carrera>());
        }
        if (sector.getCapacitadoList() == null) {
            sector.setCapacitadoList(new ArrayList<Capacitado>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Responsable> attachedResponsableList = new ArrayList<Responsable>();
            for (Responsable responsableListResponsableToAttach : sector.getResponsableList()) {
                responsableListResponsableToAttach = em.getReference(responsableListResponsableToAttach.getClass(), responsableListResponsableToAttach.getId());
                attachedResponsableList.add(responsableListResponsableToAttach);
            }
            sector.setResponsableList(attachedResponsableList);
            List<Capacitacion> attachedCapacitacionList = new ArrayList<Capacitacion>();
            for (Capacitacion capacitacionListCapacitacionToAttach : sector.getCapacitacionList()) {
                capacitacionListCapacitacionToAttach = em.getReference(capacitacionListCapacitacionToAttach.getClass(), capacitacionListCapacitacionToAttach.getId());
                attachedCapacitacionList.add(capacitacionListCapacitacionToAttach);
            }
            sector.setCapacitacionList(attachedCapacitacionList);
            List<Carrera> attachedCarreraList = new ArrayList<Carrera>();
            for (Carrera carreraListCarreraToAttach : sector.getCarreraList()) {
                carreraListCarreraToAttach = em.getReference(carreraListCarreraToAttach.getClass(), carreraListCarreraToAttach.getId());
                attachedCarreraList.add(carreraListCarreraToAttach);
            }
            sector.setCarreraList(attachedCarreraList);
            List<Capacitado> attachedCapacitadoList = new ArrayList<Capacitado>();
            for (Capacitado capacitadoListCapacitadoToAttach : sector.getCapacitadoList()) {
                capacitadoListCapacitadoToAttach = em.getReference(capacitadoListCapacitadoToAttach.getClass(), capacitadoListCapacitadoToAttach.getId());
                attachedCapacitadoList.add(capacitadoListCapacitadoToAttach);
            }
            sector.setCapacitadoList(attachedCapacitadoList);
            em.persist(sector);
            for (Responsable responsableListResponsable : sector.getResponsableList()) {
                Sector oldSectorIdOfResponsableListResponsable = responsableListResponsable.getSectorId();
                responsableListResponsable.setSectorId(sector);
                responsableListResponsable = em.merge(responsableListResponsable);
                if (oldSectorIdOfResponsableListResponsable != null) {
                    oldSectorIdOfResponsableListResponsable.getResponsableList().remove(responsableListResponsable);
                    oldSectorIdOfResponsableListResponsable = em.merge(oldSectorIdOfResponsableListResponsable);
                }
            }
            for (Capacitacion capacitacionListCapacitacion : sector.getCapacitacionList()) {
                Sector oldSectorIdOfCapacitacionListCapacitacion = capacitacionListCapacitacion.getSectorId();
                capacitacionListCapacitacion.setSectorId(sector);
                capacitacionListCapacitacion = em.merge(capacitacionListCapacitacion);
                if (oldSectorIdOfCapacitacionListCapacitacion != null) {
                    oldSectorIdOfCapacitacionListCapacitacion.getCapacitacionList().remove(capacitacionListCapacitacion);
                    oldSectorIdOfCapacitacionListCapacitacion = em.merge(oldSectorIdOfCapacitacionListCapacitacion);
                }
            }
            for (Carrera carreraListCarrera : sector.getCarreraList()) {
                Sector oldSectorIdOfCarreraListCarrera = carreraListCarrera.getSectorId();
                carreraListCarrera.setSectorId(sector);
                carreraListCarrera = em.merge(carreraListCarrera);
                if (oldSectorIdOfCarreraListCarrera != null) {
                    oldSectorIdOfCarreraListCarrera.getCarreraList().remove(carreraListCarrera);
                    oldSectorIdOfCarreraListCarrera = em.merge(oldSectorIdOfCarreraListCarrera);
                }
            }
            for (Capacitado capacitadoListCapacitado : sector.getCapacitadoList()) {
                Sector oldSectoridOfCapacitadoListCapacitado = capacitadoListCapacitado.getSectorid();
                capacitadoListCapacitado.setSectorid(sector);
                capacitadoListCapacitado = em.merge(capacitadoListCapacitado);
                if (oldSectoridOfCapacitadoListCapacitado != null) {
                    oldSectoridOfCapacitadoListCapacitado.getCapacitadoList().remove(capacitadoListCapacitado);
                    oldSectoridOfCapacitadoListCapacitado = em.merge(oldSectoridOfCapacitadoListCapacitado);
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

    public void edit(Sector sector) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Sector persistentSector = em.find(Sector.class, sector.getId());
            List<Responsable> responsableListOld = persistentSector.getResponsableList();
            List<Responsable> responsableListNew = sector.getResponsableList();
            List<Capacitacion> capacitacionListOld = persistentSector.getCapacitacionList();
            List<Capacitacion> capacitacionListNew = sector.getCapacitacionList();
            List<Carrera> carreraListOld = persistentSector.getCarreraList();
            List<Carrera> carreraListNew = sector.getCarreraList();
            List<Capacitado> capacitadoListOld = persistentSector.getCapacitadoList();
            List<Capacitado> capacitadoListNew = sector.getCapacitadoList();
            List<Responsable> attachedResponsableListNew = new ArrayList<Responsable>();
            for (Responsable responsableListNewResponsableToAttach : responsableListNew) {
                responsableListNewResponsableToAttach = em.getReference(responsableListNewResponsableToAttach.getClass(), responsableListNewResponsableToAttach.getId());
                attachedResponsableListNew.add(responsableListNewResponsableToAttach);
            }
            responsableListNew = attachedResponsableListNew;
            sector.setResponsableList(responsableListNew);
            List<Capacitacion> attachedCapacitacionListNew = new ArrayList<Capacitacion>();
            for (Capacitacion capacitacionListNewCapacitacionToAttach : capacitacionListNew) {
                capacitacionListNewCapacitacionToAttach = em.getReference(capacitacionListNewCapacitacionToAttach.getClass(), capacitacionListNewCapacitacionToAttach.getId());
                attachedCapacitacionListNew.add(capacitacionListNewCapacitacionToAttach);
            }
            capacitacionListNew = attachedCapacitacionListNew;
            sector.setCapacitacionList(capacitacionListNew);
            List<Carrera> attachedCarreraListNew = new ArrayList<Carrera>();
            for (Carrera carreraListNewCarreraToAttach : carreraListNew) {
                carreraListNewCarreraToAttach = em.getReference(carreraListNewCarreraToAttach.getClass(), carreraListNewCarreraToAttach.getId());
                attachedCarreraListNew.add(carreraListNewCarreraToAttach);
            }
            carreraListNew = attachedCarreraListNew;
            sector.setCarreraList(carreraListNew);
            List<Capacitado> attachedCapacitadoListNew = new ArrayList<Capacitado>();
            for (Capacitado capacitadoListNewCapacitadoToAttach : capacitadoListNew) {
                capacitadoListNewCapacitadoToAttach = em.getReference(capacitadoListNewCapacitadoToAttach.getClass(), capacitadoListNewCapacitadoToAttach.getId());
                attachedCapacitadoListNew.add(capacitadoListNewCapacitadoToAttach);
            }
            capacitadoListNew = attachedCapacitadoListNew;
            sector.setCapacitadoList(capacitadoListNew);
            sector = em.merge(sector);
            for (Responsable responsableListOldResponsable : responsableListOld) {
                if (!responsableListNew.contains(responsableListOldResponsable)) {
                    responsableListOldResponsable.setSectorId(null);
                    responsableListOldResponsable = em.merge(responsableListOldResponsable);
                }
            }
            for (Responsable responsableListNewResponsable : responsableListNew) {
                if (!responsableListOld.contains(responsableListNewResponsable)) {
                    Sector oldSectorIdOfResponsableListNewResponsable = responsableListNewResponsable.getSectorId();
                    responsableListNewResponsable.setSectorId(sector);
                    responsableListNewResponsable = em.merge(responsableListNewResponsable);
                    if (oldSectorIdOfResponsableListNewResponsable != null && !oldSectorIdOfResponsableListNewResponsable.equals(sector)) {
                        oldSectorIdOfResponsableListNewResponsable.getResponsableList().remove(responsableListNewResponsable);
                        oldSectorIdOfResponsableListNewResponsable = em.merge(oldSectorIdOfResponsableListNewResponsable);
                    }
                }
            }
            for (Capacitacion capacitacionListOldCapacitacion : capacitacionListOld) {
                if (!capacitacionListNew.contains(capacitacionListOldCapacitacion)) {
                    capacitacionListOldCapacitacion.setSectorId(null);
                    capacitacionListOldCapacitacion = em.merge(capacitacionListOldCapacitacion);
                }
            }
            for (Capacitacion capacitacionListNewCapacitacion : capacitacionListNew) {
                if (!capacitacionListOld.contains(capacitacionListNewCapacitacion)) {
                    Sector oldSectorIdOfCapacitacionListNewCapacitacion = capacitacionListNewCapacitacion.getSectorId();
                    capacitacionListNewCapacitacion.setSectorId(sector);
                    capacitacionListNewCapacitacion = em.merge(capacitacionListNewCapacitacion);
                    if (oldSectorIdOfCapacitacionListNewCapacitacion != null && !oldSectorIdOfCapacitacionListNewCapacitacion.equals(sector)) {
                        oldSectorIdOfCapacitacionListNewCapacitacion.getCapacitacionList().remove(capacitacionListNewCapacitacion);
                        oldSectorIdOfCapacitacionListNewCapacitacion = em.merge(oldSectorIdOfCapacitacionListNewCapacitacion);
                    }
                }
            }
            for (Carrera carreraListOldCarrera : carreraListOld) {
                if (!carreraListNew.contains(carreraListOldCarrera)) {
                    carreraListOldCarrera.setSectorId(null);
                    carreraListOldCarrera = em.merge(carreraListOldCarrera);
                }
            }
            for (Carrera carreraListNewCarrera : carreraListNew) {
                if (!carreraListOld.contains(carreraListNewCarrera)) {
                    Sector oldSectorIdOfCarreraListNewCarrera = carreraListNewCarrera.getSectorId();
                    carreraListNewCarrera.setSectorId(sector);
                    carreraListNewCarrera = em.merge(carreraListNewCarrera);
                    if (oldSectorIdOfCarreraListNewCarrera != null && !oldSectorIdOfCarreraListNewCarrera.equals(sector)) {
                        oldSectorIdOfCarreraListNewCarrera.getCarreraList().remove(carreraListNewCarrera);
                        oldSectorIdOfCarreraListNewCarrera = em.merge(oldSectorIdOfCarreraListNewCarrera);
                    }
                }
            }
            for (Capacitado capacitadoListOldCapacitado : capacitadoListOld) {
                if (!capacitadoListNew.contains(capacitadoListOldCapacitado)) {
                    capacitadoListOldCapacitado.setSectorid(null);
                    capacitadoListOldCapacitado = em.merge(capacitadoListOldCapacitado);
                }
            }
            for (Capacitado capacitadoListNewCapacitado : capacitadoListNew) {
                if (!capacitadoListOld.contains(capacitadoListNewCapacitado)) {
                    Sector oldSectoridOfCapacitadoListNewCapacitado = capacitadoListNewCapacitado.getSectorid();
                    capacitadoListNewCapacitado.setSectorid(sector);
                    capacitadoListNewCapacitado = em.merge(capacitadoListNewCapacitado);
                    if (oldSectoridOfCapacitadoListNewCapacitado != null && !oldSectoridOfCapacitadoListNewCapacitado.equals(sector)) {
                        oldSectoridOfCapacitadoListNewCapacitado.getCapacitadoList().remove(capacitadoListNewCapacitado);
                        oldSectoridOfCapacitadoListNewCapacitado = em.merge(oldSectoridOfCapacitadoListNewCapacitado);
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
                Integer id = sector.getId();
                if (findSector(id) == null) {
                    throw new NonexistentEntityException("The sector with id " + id + " no longer exists.");
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
            Sector sector;
            try {
                sector = em.getReference(Sector.class, id);
                sector.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sector with id " + id + " no longer exists.", enfe);
            }
            List<Responsable> responsableList = sector.getResponsableList();
            for (Responsable responsableListResponsable : responsableList) {
                responsableListResponsable.setSectorId(null);
                responsableListResponsable = em.merge(responsableListResponsable);
            }
            List<Capacitacion> capacitacionList = sector.getCapacitacionList();
            for (Capacitacion capacitacionListCapacitacion : capacitacionList) {
                capacitacionListCapacitacion.setSectorId(null);
                capacitacionListCapacitacion = em.merge(capacitacionListCapacitacion);
            }
            List<Carrera> carreraList = sector.getCarreraList();
            for (Carrera carreraListCarrera : carreraList) {
                carreraListCarrera.setSectorId(null);
                carreraListCarrera = em.merge(carreraListCarrera);
            }
            List<Capacitado> capacitadoList = sector.getCapacitadoList();
            for (Capacitado capacitadoListCapacitado : capacitadoList) {
                capacitadoListCapacitado.setSectorid(null);
                capacitadoListCapacitado = em.merge(capacitadoListCapacitado);
            }
            em.remove(sector);
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

    public List<Sector> findSectorEntities() {
        return findSectorEntities(true, -1, -1);
    }

    public List<Sector> findSectorEntities(int maxResults, int firstResult) {
        return findSectorEntities(false, maxResults, firstResult);
    }

    private List<Sector> findSectorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sector.class));
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

    public Sector findSector(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sector.class, id);
        } finally {
            em.close();
        }
    }

    public int getSectorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sector> rt = cq.from(Sector.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
