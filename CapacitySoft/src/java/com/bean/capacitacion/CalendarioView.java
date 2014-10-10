/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bean.capacitacion;

import com.jpa.controllers.CapacitacionJpaController;
import com.jpa.entities.Capacitacion;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author ERIKA
 */
@ManagedBean
@SessionScoped
public class CalendarioView implements Serializable {

    private ScheduleModel eventModel;
    private ScheduleEvent event = new DefaultScheduleEvent();
    private Capacitacion capacitacion;
    private CapacitacionJpaController capacitacionJpaController;
    private List<Capacitacion> listaCapacitaciones;

    public CalendarioView() throws NamingException {
        capacitacionJpaController = new CapacitacionJpaController();
    }

    // se requiere especificar la zona horaria
    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
        listaCapacitaciones = capacitacionJpaController.findCapacitacionEntities();
//        eventModel.addEvent(new DefaultScheduleEvent("Evento1", previousDay8Pm(), previousDay11Pm()));

        listaCapacitaciones = capacitacionJpaController.findCapacitacionEntities();
        Capacitacion aux;
        for (int i = 0; i < listaCapacitaciones.size(); i++) {
            Calendar inicio = Calendar.getInstance();
            Calendar fin = Calendar.getInstance();
            aux = listaCapacitaciones.get(i);
            DefaultScheduleEvent evento = new DefaultScheduleEvent();
            evento.setTitle(aux.getTema());
            evento.setDescription(Integer.toString(aux.getId()));

            inicio.setTimeZone(TimeZone.getTimeZone("America/Guayaquil"));
            inicio.set(aux.getFechaInicio().getDate(), aux.getFechaInicio().getMonth(), aux.getFechaInicio().getYear(),
                    aux.getFechaInicio().getHours(), aux.getFechaInicio().getMinutes(), aux.getFechaInicio().getSeconds());
            fin.setTimeZone(TimeZone.getTimeZone("America/Guayaquil"));
            fin.set(aux.getFechaFin().getDate(), aux.getFechaFin().getMonth(), aux.getFechaFin().getYear(),
                    aux.getFechaFin().getHours(), aux.getFechaFin().getMinutes(), aux.getFechaFin().getSeconds());

            evento.setStartDate(inicio.getTime());
            evento.setEndDate(fin.getTime());

            eventModel.addEvent(evento);
        }
    }

    public Date getInitialDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY, calendar.get(Calendar.DATE), 0, 0, 0);

        return calendar.getTime();
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }

    public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
        System.out.println("evento " + event.getTitle());
        Capacitacion aux;
        String id;
        for (int i = 0; i < listaCapacitaciones.size(); i++) {
            aux = listaCapacitaciones.get(i);
            id = Integer.toString(aux.getId());
            if (id.equals(event.getDescription())) {
                capacitacion = aux;
            }
        }
    }

    public void addEvent(ActionEvent actionEvent) {
        if (event.getId() == null) {
            eventModel.addEvent(event);
        } else {
            eventModel.updateEvent(event);
        }

        event = new DefaultScheduleEvent();
    }

    /////
    private Calendar today() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);

        return calendar;
    }

    private Date previousDay8Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.DATE, t.get(Calendar.DATE));
        t.set(Calendar.HOUR, 8);

        return t.getTime();
    }

    private Date previousDay11Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.DATE, t.get(Calendar.DATE));
        t.set(Calendar.HOUR, 11);

        return t.getTime();
    }

    public void onDateSelect(SelectEvent selectEvent) {
        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
        System.out.println("hola " + event.getTitle());
    }

    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());

        addMessage(message);
    }

    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());

        addMessage(message);
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    //////
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
        return listaCapacitaciones;
    }

    public void setListaCapacitaciones(List<Capacitacion> listaCapacitaciones) {
        this.listaCapacitaciones = listaCapacitaciones;
    }

}
