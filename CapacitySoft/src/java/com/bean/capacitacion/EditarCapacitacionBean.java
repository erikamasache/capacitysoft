/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bean.capacitacion;

import com.jpa.controllers.CapacitacionJpaController;
import com.jpa.controllers.exceptions.NonexistentEntityException;
import com.jpa.controllers.exceptions.RollbackFailureException;
import com.jpa.entities.Capacitacion;
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
public class EditarCapacitacionBean implements Serializable{

    private Capacitacion capacitacion;
    private CapacitacionJpaController capacitacionJpaController;
            
    public EditarCapacitacionBean() throws NamingException {
        capacitacion = new Capacitacion();
        capacitacionJpaController = new CapacitacionJpaController();
    }

    public void editarCapacitacion() throws NonexistentEntityException, RollbackFailureException, Exception{
        capacitacionJpaController.edit(capacitacion);
    }
    
    public void eliminarCapacitacion() throws NonexistentEntityException, RollbackFailureException, Exception{
        capacitacionJpaController.destroy(capacitacion.getId());
    }

    public Capacitacion getCapacitacion() {
        return capacitacion;
    }

    public void setCapacitacion(Capacitacion capacitacion) {
        this.capacitacion = capacitacion;
    }

    public CapacitacionJpaController getCapacitacionJpaController() {
        return capacitacionJpaController;
    }

    public void setCapacitacionJpaController(CapacitacionJpaController capacitacionJpaController) {
        this.capacitacionJpaController = capacitacionJpaController;
    }
    
    
}
