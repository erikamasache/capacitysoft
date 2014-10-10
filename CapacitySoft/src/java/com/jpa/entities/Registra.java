/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jpa.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ERIKA
 */
@Entity
@Table(name = "registra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Registra.findAll", query = "SELECT r FROM Registra r"),
    @NamedQuery(name = "Registra.findById", query = "SELECT r FROM Registra r WHERE r.id = :id"),
    @NamedQuery(name = "Registra.findByFecha", query = "SELECT r FROM Registra r WHERE r.fecha = :fecha"),
    @NamedQuery(name = "Registra.findByHora", query = "SELECT r FROM Registra r WHERE r.hora = :hora")})
public class Registra implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "hora")
    @Temporal(TemporalType.TIME)
    private Date hora;
    @JoinColumn(name = "Capacitacion_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Capacitacion capacitacionid;
    @JoinColumn(name = "Capacitado_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Capacitado capacitadoid;

    public Registra() {
    }

    public Registra(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public Capacitacion getCapacitacionid() {
        return capacitacionid;
    }

    public void setCapacitacionid(Capacitacion capacitacionid) {
        this.capacitacionid = capacitacionid;
    }

    public Capacitado getCapacitadoid() {
        return capacitadoid;
    }

    public void setCapacitadoid(Capacitado capacitadoid) {
        this.capacitadoid = capacitadoid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Registra)) {
            return false;
        }
        Registra other = (Registra) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jpa.entities.Registra[ id=" + id + " ]";
    }
    
}
