/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bean.evaluacion;

import com.jpa.controllers.EvaluacionJpaController;
import com.jpa.controllers.exceptions.RollbackFailureException;
import com.jpa.entities.Evaluacion;
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
public class EditarEvaluacionBean implements Serializable{

    private Evaluacion evaluacion;
    private EvaluacionJpaController evaluacionJpaController;    
    
    public EditarEvaluacionBean() throws NamingException {
        evaluacion = new Evaluacion();
        evaluacionJpaController = new EvaluacionJpaController();
    }
    
    public void editarEvaluacion() throws RollbackFailureException, Exception{
        evaluacionJpaController.edit(evaluacion);
    }
    
    public void eliminarEvaluacion() throws RollbackFailureException, Exception{
        evaluacionJpaController.destroy(evaluacion.getId());
    }
    
    // setters y getters
    public Evaluacion getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
    }

    public EvaluacionJpaController getEvaluacionJpaController() {
        return evaluacionJpaController;
    }

    public void setEvaluacionJpaController(EvaluacionJpaController evaluacionJpaController) {
        this.evaluacionJpaController = evaluacionJpaController;
    }
    
}
