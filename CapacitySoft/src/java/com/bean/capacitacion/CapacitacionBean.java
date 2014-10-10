/**
 * Clase CapacitacionBean esta clase sirve para acceder al conrolador y 
 * contiene los metodos Guardar, Editar, Eliminar 
 */

package com.bean.capacitacion;

import com.jpa.controllers.CapacitacionJpaController;
import com.jpa.controllers.exceptions.NonexistentEntityException;
import com.jpa.controllers.exceptions.RollbackFailureException;
import com.jpa.entities.Capacitacion;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.naming.NamingException;

/**
 *
 * @author ERIKA
 */
@ManagedBean
@RequestScoped
public class CapacitacionBean implements Serializable{

    private Capacitacion capacitacion;
    private CapacitacionJpaController capacitacionJpaController;
    private List<Capacitacion> listaCapacitaciones;
    @ManagedProperty("#{editarCapacitacionBean}")
    private EditarCapacitacionBean editarCapacitacionBean;
    
    public CapacitacionBean() throws NamingException {
        capacitacion = new Capacitacion();
        capacitacionJpaController = new CapacitacionJpaController();
    }
    
    public void cargarCapacitacion(){
        listaCapacitaciones = capacitacionJpaController.findCapacitacionEntities();
    }
    
    public void guardarCapacitacion() throws Exception{
        capacitacionJpaController.create(capacitacion);
    }
    
    public void editarCapacitacion() throws NonexistentEntityException, RollbackFailureException, Exception{
        editarCapacitacionBean.editarCapacitacion();
    }
    
    public void eliminarCapacitacion() throws NonexistentEntityException, RollbackFailureException, Exception{
        editarCapacitacionBean.eliminarCapacitacion();
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

    public List<Capacitacion> getListaCapacitaciones() {
        cargarCapacitacion();
        return listaCapacitaciones;
    }

    public void setListaCapacitaciones(List<Capacitacion> listaCapacitaciones) {
        this.listaCapacitaciones = listaCapacitaciones;
    }

    public EditarCapacitacionBean getEditarCapacitacionBean() {
        return editarCapacitacionBean;
    }

    public void setEditarCapacitacionBean(EditarCapacitacionBean editarCapacitacionBean) {
        this.editarCapacitacionBean = editarCapacitacionBean;
    }
    
    
}
