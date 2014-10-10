/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bean.responsable;

import com.bean.sector.SectorBean;
import com.jpa.controllers.ResponsableJpaController;
import com.jpa.controllers.exceptions.RollbackFailureException;
import com.jpa.entities.Responsable;
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
public class ResponsableBean implements Serializable{

    private Responsable responsable;
    private ResponsableJpaController responsableJpaController;
    private List<Responsable> listaResponsables;
    @ManagedProperty("#{sectorBean}")
    private SectorBean sectorBean;   
    
    @ManagedProperty("#{editarResponsableBean}")
    private EditarResponsableBean editarResponsableBean;
    
    public ResponsableBean() throws NamingException {
        responsable = new Responsable();
        responsableJpaController = new ResponsableJpaController();
    }
        
    public void cargarResponsable(){
        listaResponsables = responsableJpaController.findResponsableEntities();
    }

    public void guardarResponsable() throws Exception{        
        responsable.setSectorId(sectorBean.getSector());
        responsableJpaController.create(responsable);
    }
    
    public void editarResponsable() throws RollbackFailureException, Exception{
       editarResponsableBean.editarResponsable(sectorBean.getSector());
    }
    
    public void eliminarResponsable() throws RollbackFailureException, Exception{
        editarResponsableBean.eliminarResponsable();
    }

    
    // setters y getters
    public Responsable getResponsable() {
        return responsable;
    }

    public void setResponsable(Responsable responsable) {
        this.responsable = responsable;
    }

    public ResponsableJpaController getResponsableJpaController() {
        return responsableJpaController;
    }

    public void setResponsableJpaController(ResponsableJpaController responsableJpaController) {
        this.responsableJpaController = responsableJpaController;
    }

    public List<Responsable> getListaResponsables() {
        cargarResponsable();
        return listaResponsables;
    }

    public void setListaResponsables(List<Responsable> listaResponsables) {
        this.listaResponsables = listaResponsables;
    }

    public EditarResponsableBean getEditarResponsableBean() {
        return editarResponsableBean;
    }

    public void setEditarResponsableBean(EditarResponsableBean editarResponsableBean) {
        this.editarResponsableBean = editarResponsableBean;
    }

    public SectorBean getSectorBean() {
        return sectorBean;
    }

    public void setSectorBean(SectorBean sectorBean) {
        this.sectorBean = sectorBean;
    }

}
