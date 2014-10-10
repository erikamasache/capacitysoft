/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bean.recurso;

import com.jpa.controllers.RecursoJpaController;
import com.jpa.controllers.exceptions.RollbackFailureException;
import com.jpa.entities.Recurso;
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
public class EditarRecursoBean implements Serializable{

    private Recurso recurso;
    private RecursoJpaController recursoJpaController;
    
    public EditarRecursoBean() throws NamingException {
        recurso =  new Recurso();
        recursoJpaController = new RecursoJpaController();
    }
    
    public void editarRecurso() throws RollbackFailureException, Exception{
        recursoJpaController.edit(recurso);
    }
    
    public void eliminarRecurso() throws RollbackFailureException, Exception{
        recursoJpaController.destroy(recurso.getId());
    }
    
    // setters y getters
    public Recurso getRecurso() {
        return recurso;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }

    public RecursoJpaController getRecursoJpaController() {
        return recursoJpaController;
    }

    public void setRecursoJpaController(RecursoJpaController recursoJpaController) {
        this.recursoJpaController = recursoJpaController;
    }
    
}
