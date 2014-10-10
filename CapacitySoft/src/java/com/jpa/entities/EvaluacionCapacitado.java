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
@Table(name = "evaluacion_capacitado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EvaluacionCapacitado.findAll", query = "SELECT e FROM EvaluacionCapacitado e"),
    @NamedQuery(name = "EvaluacionCapacitado.findByIdEvaluacionCapacitado", query = "SELECT e FROM EvaluacionCapacitado e WHERE e.idEvaluacionCapacitado = :idEvaluacionCapacitado"),
    @NamedQuery(name = "EvaluacionCapacitado.findByCalificaci\u00f3nEstudiante", query = "SELECT e FROM EvaluacionCapacitado e WHERE e.calificaci\u00f3nEstudiante = :calificaci\u00f3nEstudiante"),
    @NamedQuery(name = "EvaluacionCapacitado.findByFecha", query = "SELECT e FROM EvaluacionCapacitado e WHERE e.fecha = :fecha"),
    @NamedQuery(name = "EvaluacionCapacitado.findByHora", query = "SELECT e FROM EvaluacionCapacitado e WHERE e.hora = :hora")})
public class EvaluacionCapacitado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEvaluacionCapacitado")
    private Integer idEvaluacionCapacitado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "calificaci\u00f3n_estudiante")
    private Double calificaciónEstudiante;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "hora")
    @Temporal(TemporalType.TIME)
    private Date hora;
    @JoinColumn(name = "Pregunta_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Pregunta preguntaid;
    @JoinColumn(name = "Capacitado_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Capacitado capacitadoid;
    @JoinColumn(name = "Respuesta_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Respuesta respuestaid;

    public EvaluacionCapacitado() {
    }

    public EvaluacionCapacitado(Integer idEvaluacionCapacitado) {
        this.idEvaluacionCapacitado = idEvaluacionCapacitado;
    }

    public Integer getIdEvaluacionCapacitado() {
        return idEvaluacionCapacitado;
    }

    public void setIdEvaluacionCapacitado(Integer idEvaluacionCapacitado) {
        this.idEvaluacionCapacitado = idEvaluacionCapacitado;
    }

    public Double getCalificaciónEstudiante() {
        return calificaciónEstudiante;
    }

    public void setCalificaciónEstudiante(Double calificaciónEstudiante) {
        this.calificaciónEstudiante = calificaciónEstudiante;
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

    public Pregunta getPreguntaid() {
        return preguntaid;
    }

    public void setPreguntaid(Pregunta preguntaid) {
        this.preguntaid = preguntaid;
    }

    public Capacitado getCapacitadoid() {
        return capacitadoid;
    }

    public void setCapacitadoid(Capacitado capacitadoid) {
        this.capacitadoid = capacitadoid;
    }

    public Respuesta getRespuestaid() {
        return respuestaid;
    }

    public void setRespuestaid(Respuesta respuestaid) {
        this.respuestaid = respuestaid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEvaluacionCapacitado != null ? idEvaluacionCapacitado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvaluacionCapacitado)) {
            return false;
        }
        EvaluacionCapacitado other = (EvaluacionCapacitado) object;
        if ((this.idEvaluacionCapacitado == null && other.idEvaluacionCapacitado != null) || (this.idEvaluacionCapacitado != null && !this.idEvaluacionCapacitado.equals(other.idEvaluacionCapacitado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jpa.entities.EvaluacionCapacitado[ idEvaluacionCapacitado=" + idEvaluacionCapacitado + " ]";
    }
    
}
