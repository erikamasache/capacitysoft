/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bean.carrera;

import com.jpa.controllers.CarreraJpaController;
import com.jpa.controllers.exceptions.RollbackFailureException;
import com.jpa.entities.Carrera;
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
public class EditarCarreraBean implements Serializable{

    private Carrera carrera;
    private CarreraJpaController carreraJpaController;
    
    public EditarCarreraBean() throws NamingException {
        carrera = new Carrera();
        carreraJpaController= new CarreraJpaController();
    }
    
    public void editarCarrera() throws RollbackFailureException, Exception{
        System.out.println("editar "+carrera.getNombre());
        carreraJpaController.edit(carrera);
    }
    
    public void eliminarCarrera() throws RollbackFailureException, Exception{
        System.out.println("eliminar "+carrera.getNombre());
        carreraJpaController.destroy(carrera.getId());
    }
    
    // setters y getters
    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public CarreraJpaController getCarreraJpaController() {
        return carreraJpaController;
    }

    public void setCarreraJpaController(CarreraJpaController carreraJpaController) {
        this.carreraJpaController = carreraJpaController;
    }

}
