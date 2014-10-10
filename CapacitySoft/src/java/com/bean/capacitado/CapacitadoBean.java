/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean.capacitado;

import com.bean.sector.SectorBean;
import com.jpa.controllers.CapacitadoJpaController;
import com.jpa.controllers.exceptions.NonexistentEntityException;
import com.jpa.controllers.exceptions.RollbackFailureException;
import com.jpa.entities.Capacitado;
import com.jpa.entities.Capacitador;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;

/**
 *
 * @author ERIKA
 */
@ManagedBean
@RequestScoped
public class CapacitadoBean implements Serializable {

    private Capacitado capacitado;
    private CapacitadoJpaController capacitadoJpaController;
    private List<Capacitado> listaCapacitados;

    @ManagedProperty("#{editarCapacitadoBean}")
    private EditarCapacitadoBean editarCapacitadoBean;

    @ManagedProperty("#{sectorBean}")
    private SectorBean sectorBean;
    private int tipo;

    public CapacitadoBean() throws NamingException {
        capacitado = new Capacitado();
        capacitadoJpaController = new CapacitadoJpaController();
    }

    public void guardarCapacitado() throws Exception {
        if (capacitado.getTipoIdentificacion() == 1) {
            if (capacitado.validadorDeCedula(capacitado.getCedula())) {
                capacitado.setSectorid(sectorBean.getSector());
                capacitado.setTipo(tipo);
                capacitadoJpaController.create(capacitado);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "El Capacitado " + capacitado.getNombre()
                        + " ha sido creado correctamente", null));
                cargarCapacitados();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "El número de cédula de " + capacitado.getNombre()
                        + " es incorrecto", null));
            }
        } else {
            capacitado.setSectorid(sectorBean.getSector());
            capacitado.setTipo(tipo);
            capacitadoJpaController.create(capacitado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "El Capacitado " + capacitado.getNombre()
                    + " ha sido creado correctamente", null));
            cargarCapacitados();
        }
    }

    public void cargarCapacitados() {
        listaCapacitados = capacitadoJpaController.findCapacitadoEntities();
    }

    public void editarCapacitado() throws NonexistentEntityException, RollbackFailureException, Exception {
        editarCapacitadoBean.editarCapacitado(sectorBean.getSector());
//        editarCapacitadoBean.editarCapacitado();
    }

    public void eliminarCapacitado() throws NonexistentEntityException, RollbackFailureException, Exception {
        editarCapacitadoBean.eliminarCapacitado();
    }

    // setters y getters
    public Capacitado getCapacitado() {
        return capacitado;
    }

    public void setCapacitado(Capacitado capacitado) {
        this.capacitado = capacitado;
    }

    public CapacitadoJpaController getCapacitadoJpaController() {
        return capacitadoJpaController;
    }

    public void setCapacitadoJpaController(CapacitadoJpaController capacitadoJpaController) {
        this.capacitadoJpaController = capacitadoJpaController;
    }

    public List<Capacitado> getListaCapacitados() {
        cargarCapacitados();
        return listaCapacitados;
    }

    public void setListaCapacitados(List<Capacitado> listaCapacitados) {
        this.listaCapacitados = listaCapacitados;
    }

    public EditarCapacitadoBean getEditarCapacitadoBean() {
        return editarCapacitadoBean;
    }

    public void setEditarCapacitadoBean(EditarCapacitadoBean editarCapacitadoBean) {
        this.editarCapacitadoBean = editarCapacitadoBean;
    }

    public SectorBean getSectorBean() {
        return sectorBean;
    }

    public void setSectorBean(SectorBean sectorBean) {
        this.sectorBean = sectorBean;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

}
