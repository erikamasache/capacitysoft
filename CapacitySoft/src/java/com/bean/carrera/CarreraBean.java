/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean.carrera;

import com.jpa.controllers.CarreraJpaController;
import com.jpa.controllers.exceptions.RollbackFailureException;
import com.jpa.entities.Carrera;
import com.jpa.entities.Sector;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.naming.NamingException;

/**
 *
 * @author ERIKA
 */
@ManagedBean
@SessionScoped
public class CarreraBean implements Serializable {

    private Carrera carrera;
    private List<Carrera> listaCarreras;
    private CarreraJpaController carreraJpaController;
    @ManagedProperty("#{editarCarreraBean}")
    private EditarCarreraBean editarCarreraBean;
    private Sector sector;
//    @ManagedProperty("#{sectorBean}")
//    private SectorBean sectorBean;
    private List<Carrera> aux;

    public CarreraBean() throws NamingException {
        carrera = new Carrera();
        carreraJpaController = new CarreraJpaController();
    }

    public String obtenerSector(Sector sect) throws Exception {
        sector = sect;
        return "Carrera.xhtml";
    }

    public void cargarCarrera() {
//        listaCarreras = carreraJpaController.findCarreraEntities();
        listaCarreras = new ArrayList<>();
        aux = carreraJpaController.findCarreraEntities();
        Carrera carr;
        for (int i = 0; i < aux.size(); i++) {
            carr = aux.get(i);            
            if (sector.equals(carr.getSectorId())) {
                listaCarreras.add(carr);
            }
        }
    }

    public void guardarCarrera() throws Exception {
        System.out.println("sector"+sector.getNombre());
        carrera.setSectorId(sector);
        carreraJpaController.create(carrera);
    }

    public void editarCarrera() throws RollbackFailureException, Exception {
        editarCarreraBean.editarCarrera();
    }

    public void eliminarCarrera() throws RollbackFailureException, Exception {
        editarCarreraBean.eliminarCarrera();
    }

    // setters y getters
    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public List<Carrera> getListaCarreras() {
        System.out.println("hola");
        cargarCarrera();
        return listaCarreras;
    }

    public void setListaCarreras(List<Carrera> listaCarreras) {
        this.listaCarreras = listaCarreras;
    }

    public CarreraJpaController getCarreraJpaController() {
        return carreraJpaController;
    }

    public void setCarreraJpaController(CarreraJpaController carreraJpaController) {
        this.carreraJpaController = carreraJpaController;
    }

    public EditarCarreraBean getEditarCarreraBean() {
        return editarCarreraBean;
    }

    public void setEditarCarreraBean(EditarCarreraBean editarCarreraBean) {
        this.editarCarreraBean = editarCarreraBean;
    }

//    public Sector getSector() {
//        return sector;
//    }
//
//    public void setSector(Sector sector) {
//        this.sector = sector;
//    }
//
//    public SectorBean getSectorBean() {
//        return sectorBean;
//    }
//
//    public void setSectorBean(SectorBean sectorBean) {
//        this.sectorBean = sectorBean;
//    }
//
//    public List<Carrera> getAux() {
//        return aux;
//    }
//
//    public void setAux(List<Carrera> aux) {
//        this.aux = aux;
//    }

}
