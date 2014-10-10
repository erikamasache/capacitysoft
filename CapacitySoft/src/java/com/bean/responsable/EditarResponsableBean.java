/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bean.responsable;

import com.jpa.controllers.ResponsableJpaController;
import com.jpa.controllers.exceptions.RollbackFailureException;
import com.jpa.entities.Responsable;
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
public class EditarResponsableBean implements Serializable{

    private Responsable responsable;
    private ResponsableJpaController responsableJpaController;
    
    public EditarResponsableBean() throws NamingException{
        responsable = new Responsable();
        responsableJpaController = new ResponsableJpaController();
    }
    
    public void editarResponsable(Sector sector) throws RollbackFailureException, Exception{
       responsable.setSectorId(sector);
        System.out.println("sector "+ sector.getNombre());
       responsableJpaController.edit(responsable);
    }
    
    public void eliminarResponsable() throws RollbackFailureException, Exception{
        responsableJpaController.destroy(responsable.getId());
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
    
}
