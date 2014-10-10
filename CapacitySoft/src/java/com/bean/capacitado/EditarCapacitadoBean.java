/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean.capacitado;

import com.jpa.controllers.CapacitadoJpaController;
import com.jpa.controllers.exceptions.NonexistentEntityException;
import com.jpa.controllers.exceptions.RollbackFailureException;
import com.jpa.entities.Capacitado;
import com.jpa.entities.Sector;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;

/**
 *
 * @author ERIKA
 */
@ManagedBean
@SessionScoped
public class EditarCapacitadoBean implements Serializable {

    private Capacitado capacitado;
    private CapacitadoJpaController capacitadoJpaController;

    public EditarCapacitadoBean() throws NamingException {
        capacitado = new Capacitado();
        capacitadoJpaController = new CapacitadoJpaController();
    }

    public void inicializarCapacitado() {
        capacitado = capacitadoJpaController.findCapacitado(1);
    }

    public void editarCapacitado(Sector sector) throws NonexistentEntityException, RollbackFailureException, Exception {
        if (capacitado.getTipoIdentificacion() == 1) {
            if (capacitado.validadorDeCedula(capacitado.getCedula())) {
                capacitado.setSectorid(sector);
                capacitadoJpaController.edit(capacitado);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "El Capacitado " + capacitado.getNombre()
                        + " ha sido modificado correctamente", null));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "El número de cédula de " + capacitado.getNombre()
                        + " es incorrecto", null));
            }
        } else {
            capacitado.setSectorid(sector);
            capacitadoJpaController.edit(capacitado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "El Capacitado " + capacitado.getNombre()
                    + " ha sido modificado correctamente", null));
        }
    }

    public void eliminarCapacitado() throws NonexistentEntityException, RollbackFailureException, Exception {
        capacitadoJpaController.destroy(capacitado.getId());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "El Capacitado " + capacitado.getNombre()
                + " ha sido eliminado correctamente", null));
    }

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

}
