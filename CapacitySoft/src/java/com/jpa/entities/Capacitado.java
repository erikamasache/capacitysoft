/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jpa.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ERIKA
 */
@Entity
@Table(name = "capacitado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Capacitado.findAll", query = "SELECT c FROM Capacitado c"),
    @NamedQuery(name = "Capacitado.findById", query = "SELECT c FROM Capacitado c WHERE c.id = :id"),
    @NamedQuery(name = "Capacitado.findByNombre", query = "SELECT c FROM Capacitado c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Capacitado.findByApellido", query = "SELECT c FROM Capacitado c WHERE c.apellido = :apellido"),
    @NamedQuery(name = "Capacitado.findByTipoIdentificacion", query = "SELECT c FROM Capacitado c WHERE c.tipoIdentificacion = :tipoIdentificacion"),
    @NamedQuery(name = "Capacitado.findByCedula", query = "SELECT c FROM Capacitado c WHERE c.cedula = :cedula"),
    @NamedQuery(name = "Capacitado.findByNumHistoriaClinica", query = "SELECT c FROM Capacitado c WHERE c.numHistoriaClinica = :numHistoriaClinica"),
    @NamedQuery(name = "Capacitado.findByDireccion", query = "SELECT c FROM Capacitado c WHERE c.direccion = :direccion"),
    @NamedQuery(name = "Capacitado.findByTelefonoDomicilio", query = "SELECT c FROM Capacitado c WHERE c.telefonoDomicilio = :telefonoDomicilio"),
    @NamedQuery(name = "Capacitado.findByCelular", query = "SELECT c FROM Capacitado c WHERE c.celular = :celular"),
    @NamedQuery(name = "Capacitado.findByCorreo", query = "SELECT c FROM Capacitado c WHERE c.correo = :correo"),
    @NamedQuery(name = "Capacitado.findByCargo", query = "SELECT c FROM Capacitado c WHERE c.cargo = :cargo"),
    @NamedQuery(name = "Capacitado.findByTipo", query = "SELECT c FROM Capacitado c WHERE c.tipo = :tipo"),
    @NamedQuery(name = "Capacitado.findByTelefonoOficina", query = "SELECT c FROM Capacitado c WHERE c.telefonoOficina = :telefonoOficina"),
    @NamedQuery(name = "Capacitado.findByExtTelefonoOficina", query = "SELECT c FROM Capacitado c WHERE c.extTelefonoOficina = :extTelefonoOficina"),
    @NamedQuery(name = "Capacitado.findByContrasenia", query = "SELECT c FROM Capacitado c WHERE c.contrasenia = :contrasenia")})
public class Capacitado implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 45)
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "tipo_identificacion")
    private Integer tipoIdentificacion;
    @Size(max = 12)
    @Column(name = "cedula")
    private String cedula;
    @Size(max = 45)
    @Column(name = "num_historia_clinica")
    private String numHistoriaClinica;
    @Size(max = 45)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 13)
    @Column(name = "telefono_domicilio")
    private String telefonoDomicilio;
    @Size(max = 13)
    @Column(name = "celular")
    private String celular;
    @Size(max = 45)
    @Column(name = "correo")
    private String correo;
    @Size(max = 45)
    @Column(name = "cargo")
    private String cargo;
    @Column(name = "tipo")
    private Integer tipo;
    @Size(max = 13)
    @Column(name = "telefono_oficina")
    private String telefonoOficina;
    @Column(name = "ext_telefono_oficina")
    private Integer extTelefonoOficina;
    @Size(max = 256)
    @Column(name = "contrasenia")
    private String contrasenia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "capacitadoid")
    private List<Registra> registraList;
    @JoinColumn(name = "carrera_id", referencedColumnName = "id")
    @ManyToOne
    private Carrera carreraId;
    @JoinColumn(name = "Capacitador_id", referencedColumnName = "id")
    @ManyToOne
    private Capacitador capacitadorid;
    @JoinColumn(name = "Sector_id", referencedColumnName = "id")
    @ManyToOne
    private Sector sectorid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "capacitadoid")
    private List<EvaluacionCapacitado> evaluacionCapacitadoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "capacitadoid")
    private List<Asistencia> asistenciaList;

    public Capacitado() {
    }

    public Capacitado(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(Integer tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNumHistoriaClinica() {
        return numHistoriaClinica;
    }

    public void setNumHistoriaClinica(String numHistoriaClinica) {
        this.numHistoriaClinica = numHistoriaClinica;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefonoDomicilio() {
        return telefonoDomicilio;
    }

    public void setTelefonoDomicilio(String telefonoDomicilio) {
        this.telefonoDomicilio = telefonoDomicilio;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getTelefonoOficina() {
        return telefonoOficina;
    }

    public void setTelefonoOficina(String telefonoOficina) {
        this.telefonoOficina = telefonoOficina;
    }

    public Integer getExtTelefonoOficina() {
        return extTelefonoOficina;
    }

    public void setExtTelefonoOficina(Integer extTelefonoOficina) {
        this.extTelefonoOficina = extTelefonoOficina;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    @XmlTransient
    public List<Registra> getRegistraList() {
        return registraList;
    }

    public void setRegistraList(List<Registra> registraList) {
        this.registraList = registraList;
    }

    public Carrera getCarreraId() {
        return carreraId;
    }

    public void setCarreraId(Carrera carreraId) {
        this.carreraId = carreraId;
    }

    public Capacitador getCapacitadorid() {
        return capacitadorid;
    }

    public void setCapacitadorid(Capacitador capacitadorid) {
        this.capacitadorid = capacitadorid;
    }

    public Sector getSectorid() {
        return sectorid;
    }

    public void setSectorid(Sector sectorid) {
        this.sectorid = sectorid;
    }

    @XmlTransient
    public List<EvaluacionCapacitado> getEvaluacionCapacitadoList() {
        return evaluacionCapacitadoList;
    }

    public void setEvaluacionCapacitadoList(List<EvaluacionCapacitado> evaluacionCapacitadoList) {
        this.evaluacionCapacitadoList = evaluacionCapacitadoList;
    }

    @XmlTransient
    public List<Asistencia> getAsistenciaList() {
        return asistenciaList;
    }

    public void setAsistenciaList(List<Asistencia> asistenciaList) {
        this.asistenciaList = asistenciaList;
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
        if (!(object instanceof Capacitado)) {
            return false;
        }
        Capacitado other = (Capacitado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jpa.entities.Capacitado[ id=" + id + " ]";
    }
 
    public boolean validadorDeCedula(String cedula) {
        boolean cedulaCorrecta = false;

        try {

            if (cedula.length() == 10) // ConstantesApp.LongitudCedula
            {
                int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
                if (tercerDigito < 6) {
            // Coeficientes de validacion cedula
            // El decimo digito se lo considera digito verificador
                    int[] coefValCedula = {2, 1, 2, 1, 2, 1, 2, 1, 2};
                    int verificador = Integer.parseInt(cedula.substring(9, 10));
                    int suma = 0;
                    int digito = 0;
                    for (int i = 0; i < (cedula.length() - 1); i++) {
                        digito = Integer.parseInt(cedula.substring(i, i + 1)) * coefValCedula[i];
                        suma += ((digito % 10) + (digito / 10));
                    }

                    if ((suma % 10 == 0) && (suma % 10 == verificador)) {
                        cedulaCorrecta = true;
                    } else if ((10 - (suma % 10)) == verificador) {
                        cedulaCorrecta = true;
                    } else {
                        cedulaCorrecta = false;
                    }
                } else {
                    cedulaCorrecta = false;
                }
            } else {
                cedulaCorrecta = false;
            }
        } catch (NumberFormatException nfe) {
            cedulaCorrecta = false;
        } catch (Exception err) {
            System.out.println("Una excepcion ocurrio en el proceso de validacion de la cedula del capacitador");
            cedulaCorrecta = false;
        }

        if (!cedulaCorrecta) {
//            System.out.println("La CÃ©dula ingresada es Incorrecta");
        }
        return cedulaCorrecta;
    }
}
