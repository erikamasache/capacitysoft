package com.bean.capacitador;

import com.jpa.controllers.AdministradorJpaController;
import com.jpa.controllers.CapacitadorJpaController;
import com.jpa.controllers.exceptions.NonexistentEntityException;
import com.jpa.controllers.exceptions.RollbackFailureException;
import com.jpa.entities.Administrador;
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
 * @author ErikaM
 */
@ManagedBean
@RequestScoped
public class CapacitadorBean implements Serializable {

    private Administrador admin;
    private AdministradorJpaController administradorJpaController;
    private List<Administrador> listaAdministradores;

    private Capacitador capacitador;
    private List<Capacitador> listaCapacitadores;
    private CapacitadorJpaController capacitadorJpaController;

    @ManagedProperty("#{editarCapacitadorBean}")
    private EditarCapacitadorBean editarCapacitadorBean;
    

    // constructor instanciamos los objetos de las clases necesarias
    public CapacitadorBean() throws NamingException {
        capacitador = new Capacitador();
        capacitador.setTipoIdentificacion(1);
        capacitadorJpaController = new CapacitadorJpaController();
        admin = new Administrador();
        administradorJpaController = new AdministradorJpaController();
    }

    public final void cargar() throws NonexistentEntityException, RollbackFailureException, Exception {
        listaCapacitadores = capacitadorJpaController.findCapacitadorEntities();
        listaAdministradores = administradorJpaController.findAdministradorEntities();
    }

    public void guardarCapacitador() throws Exception {
        if (capacitador.getTipoIdentificacion() == 1) {
            if (capacitador.validadorDeCedula(capacitador.getCedula())) {
                admin = listaAdministradores.get(0);
                capacitador.setAdministradorid(admin);
                capacitadorJpaController.create(capacitador);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "El Capacitador " + capacitador.getNombre()
                        + " ha sido creado correctamente", null));
                capacitador = new Capacitador();
                cargar();
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "El número de cédula de " + capacitador.getNombre()
                        + " es incorrecto", null));
            }
        } else {
            admin = listaAdministradores.get(0);
            capacitador.setAdministradorid(admin);
            capacitadorJpaController.create(capacitador);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "El Capacitador " + capacitador.getNombre()
                    + " ha sido creado correctamente", null));
            capacitador = new Capacitador();
            cargar();
        }

    }

    public void editarCapacitador() throws NamingException, RollbackFailureException, Exception {
        editarCapacitadorBean.editarCapacitador();
    }

    public void eliminarCapacitador() throws NamingException, RollbackFailureException, Exception {
        editarCapacitadorBean.eliminarCapacitador();
    }
    
    public void reenviarContrasenia(){
        capacitador.enviarMail();
    }

// generacion de los set y get
    public Capacitador getCapacitador() {
        return capacitador;
    }

    public void setCapacitador(Capacitador capacitador) {
        this.capacitador = capacitador;
    }

    public List<Capacitador> getListaCapacitadores() throws RollbackFailureException, Exception {
        cargar();
        return listaCapacitadores;
    }

    public void setListaCapacitadores(List<Capacitador> listaCapacitadores) {
        this.listaCapacitadores = listaCapacitadores;
    }

    public CapacitadorJpaController getCapacitadorJpaController() {
        return capacitadorJpaController;
    }

    public void setCapacitadorJpaController(CapacitadorJpaController capacitadorJpaController) {
        this.capacitadorJpaController = capacitadorJpaController;
    }

    public EditarCapacitadorBean getEditarCapacitadorBean() {
        return editarCapacitadorBean;
    }

    public void setEditarCapacitadorBean(EditarCapacitadorBean editarCapacitadorBean) {
        this.editarCapacitadorBean = editarCapacitadorBean;
    }

}
