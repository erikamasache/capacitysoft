/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bean.sector;

import com.bean.carrera.CarreraBean;
import com.jpa.controllers.SectorJpaController;
import com.jpa.controllers.exceptions.RollbackFailureException;
import com.jpa.entities.Carrera;
import com.jpa.entities.Sector;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.naming.NamingException;


/**
 *
 * @author ERIKA
 */
@ManagedBean
@RequestScoped
public class SectorBean implements Serializable{

    private Sector sector;
    private SectorJpaController sectorJpaController;
    private List<Sector> listaSectores;
    private Sector[] sectores;
    private List<Carrera> carreras;
    
    @ManagedProperty("#{carreraBean}")
    private CarreraBean carreraBean;
    
    @ManagedProperty("#{editarSectorBean}")
    private EditarSectorBean editarSectorBean;

    public SectorBean() throws NamingException {
        sector = new Sector();
        sectorJpaController = new SectorJpaController();
    }
    
    public void cargarSector(){
        listaSectores = sectorJpaController.findSectorEntities();
        sectores = new Sector[listaSectores.size()];
        for (int i = 0; i < listaSectores.size(); i++) {
            sectores[i]= listaSectores.get(i);
        }        
    }
    
    public void guardarSector() throws Exception{
        sectorJpaController.create(sector);
    }
    
    public void editarSector() throws RollbackFailureException, Exception{
        editarSectorBean.editarSector();
    }
    
    public void eliminarSector() throws RollbackFailureException, Exception{
        editarSectorBean.eliminarSector();
    }
    
    // setters y getters
    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public SectorJpaController getSectorJpaController() {
        return sectorJpaController;
    }

    public void setSectorJpaController(SectorJpaController sectorJpaController) {
        this.sectorJpaController = sectorJpaController;
    }

    public List<Sector> getListaSectores() {
        cargarSector();
        return listaSectores;
    }

    public void setListaSectores(List<Sector> listaSectores) {
        this.listaSectores = listaSectores;
    }

    public Sector[] getSectores() {
        cargarSector();
        return sectores;
    }

    public void setSectores(Sector[] sectores) {
        this.sectores = sectores;
    }

    public EditarSectorBean getEditarSectorBean() {
        return editarSectorBean;
    }

    public void setEditarSectorBean(EditarSectorBean editarSectorBean) {
        this.editarSectorBean = editarSectorBean;
    }

    public CarreraBean getCarreraBean() {
        return carreraBean;
    }

    public void setCarreraBean(CarreraBean carreraBean) {
        this.carreraBean = carreraBean;
    }
    
}
