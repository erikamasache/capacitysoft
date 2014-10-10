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
public class RecursoBean implements Serializable{

    private Recurso recurso;
    private RecursoJpaController recursoJpaController;
    private List<Recurso> listaRecursos;
    @ManagedProperty("#{editarRecursoBean}")
    private EditarRecursoBean editarRecursoBean;
    
    public RecursoBean() throws NamingException {
       recurso = new Recurso();
       recursoJpaController = new RecursoJpaController();
    }
    
    public void cargarRecurso(){
        listaRecursos = recursoJpaController.findRecursoEntities();
    }
    
    public void guardarRecurso() throws Exception{
        recursoJpaController.create(recurso);
    }
    
    public void editarRecurso() throws RollbackFailureException, Exception{
        editarRecursoBean.editarRecurso();
    }
    
    public void eliminarRecurso() throws RollbackFailureException, Exception{
        editarRecursoBean.eliminarRecurso();
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

    public List<Recurso> getListaRecursos() {
        cargarRecurso();
        return listaRecursos;
    }

    public void setListaRecursos(List<Recurso> listaRecursos) {
        this.listaRecursos = listaRecursos;
    }

    public EditarRecursoBean getEditarRecursoBean() {
        return editarRecursoBean;
    }

    public void setEditarRecursoBean(EditarRecursoBean editarRecursoBean) {
        this.editarRecursoBean = editarRecursoBean;
    }

}
