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
public class EvaluacionBean implements Serializable{

    private Evaluacion evaluacion;
    private List<Evaluacion> listaEvaluaciones;
    private EvaluacionJpaController evaluacionJpaController;
    @ManagedProperty("#{editaEvaluacionBean}")
    private EditarEvaluacionBean editarEvaluacionBean;
            
    public EvaluacionBean() throws NamingException {
        evaluacion = new Evaluacion();
        evaluacionJpaController = new EvaluacionJpaController();
    }
    
    public void cargarEvaluacion(){
        listaEvaluaciones = evaluacionJpaController.findEvaluacionEntities();
    }
    
    public void guardarEvaluacion() throws Exception{
        evaluacionJpaController.create(evaluacion);
    }
    
    public void editarEvaluacion() throws RollbackFailureException, Exception{
        editarEvaluacionBean.editarEvaluacion();
    }
    
    public void eliminarEvaluacion() throws RollbackFailureException, Exception{
        editarEvaluacionBean.eliminarEvaluacion();
    }

    // setters y getters
    public Evaluacion getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
    }

    public List<Evaluacion> getListaEvaluaciones() {
        cargarEvaluacion();
        return listaEvaluaciones;
    }

    public void setListaEvaluaciones(List<Evaluacion> listaEvaluaciones) {
        this.listaEvaluaciones = listaEvaluaciones;
    }

    public EvaluacionJpaController getEvaluacionJpaController() {
        return evaluacionJpaController;
    }

    public void setEvaluacionJpaController(EvaluacionJpaController evaluacionJpaController) {
        this.evaluacionJpaController = evaluacionJpaController;
    }

    public EditarEvaluacionBean getEditarEvaluacionBean() {
        return editarEvaluacionBean;
    }

    public void setEditarEvaluacionBean(EditarEvaluacionBean editarEvaluacionBean) {
        this.editarEvaluacionBean = editarEvaluacionBean;
    }
    
    
}
