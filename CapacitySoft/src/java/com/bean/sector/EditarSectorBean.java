/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bean.sector;

import com.jpa.controllers.SectorJpaController;
import com.jpa.controllers.exceptions.RollbackFailureException;
import com.jpa.entities.Sector;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.NamingException;

/**
 *
 * @author ERIKA
 */
@ManagedBean
@SessionScoped
public class EditarSectorBean implements Serializable{
    
    private Sector sector;
    private SectorJpaController sectorJpaController;
    
    public EditarSectorBean() throws NamingException{
        sector = new Sector();
        sectorJpaController = new SectorJpaController();
    }
    
    public void editarSector() throws RollbackFailureException, Exception{
        sectorJpaController.edit(sector);
    }
    
    public void eliminarSector() throws RollbackFailureException, Exception{
        sectorJpaController.destroy(sector.getId());
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
    
}
