/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean.capacitador;

import com.jpa.controllers.CapacitadorJpaController;
import com.jpa.controllers.exceptions.NonexistentEntityException;
import com.jpa.controllers.exceptions.RollbackFailureException;
import com.jpa.entities.Capacitador;
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
public class EditarCapacitadorBean implements Serializable {

    private Capacitador capacitador;
    private CapacitadorJpaController capacitadorJpaController;

    public EditarCapacitadorBean() throws NamingException {
        capacitador = new Capacitador();
        capacitadorJpaController = new CapacitadorJpaController();
    }

    public void editarCapacitador() throws NonexistentEntityException, RollbackFailureException, Exception {
        if (capacitador.getTipoIdentificacion() == 1) {
            if (capacitador.validadorDeCedula(capacitador.getCedula())) {
                capacitadorJpaController.edit(capacitador);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "El Capacitador " + capacitador.getNombre()
                        + " ha sido modificado correctamente", null));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "El número de cédula de " + capacitador.getNombre()
                        + " es incorrecto", null));
            }
        } else {
            capacitadorJpaController.edit(capacitador);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "El Capacitador " + capacitador.getNombre()
                    + " ha sido modificado correctamente", null));
        }
    }

    public void eliminarCapacitador() throws NonexistentEntityException, RollbackFailureException, Exception {
        capacitadorJpaController.destroy(capacitador.getId());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                "El Capacitador " + capacitador.getNombre()
                + " ha sido eliminado correctamente", null));
    }

    public void enviarCorreo() {
//        capacitadorJpaController.enviarCorreoElec(capacitador);
    }

    public Capacitador getCapacitador() {
        return capacitador;
    }

    public void setCapacitador(Capacitador capacitador) {
        this.capacitador = capacitador;
    }

    public CapacitadorJpaController getCapacitadorJpaController() {
        return capacitadorJpaController;
    }

    public void setCapacitadorJpaController(CapacitadorJpaController capacitadorJpaController) {
        this.capacitadorJpaController = capacitadorJpaController;
    }

}
