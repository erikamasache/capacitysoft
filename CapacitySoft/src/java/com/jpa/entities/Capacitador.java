/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jpa.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ERIKA
 */
@Entity
@Table(name = "capacitador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Capacitador.findAll", query = "SELECT c FROM Capacitador c"),
    @NamedQuery(name = "Capacitador.findById", query = "SELECT c FROM Capacitador c WHERE c.id = :id"),
    @NamedQuery(name = "Capacitador.findByNombre", query = "SELECT c FROM Capacitador c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Capacitador.findByApellido", query = "SELECT c FROM Capacitador c WHERE c.apellido = :apellido"),
    @NamedQuery(name = "Capacitador.findByTipoIdentificacion", query = "SELECT c FROM Capacitador c WHERE c.tipoIdentificacion = :tipoIdentificacion"),
    @NamedQuery(name = "Capacitador.findByCedula", query = "SELECT c FROM Capacitador c WHERE c.cedula = :cedula"),
    @NamedQuery(name = "Capacitador.findByFechaNacimiento", query = "SELECT c FROM Capacitador c WHERE c.fechaNacimiento = :fechaNacimiento"),
    @NamedQuery(name = "Capacitador.findByDireccion", query = "SELECT c FROM Capacitador c WHERE c.direccion = :direccion"),
    @NamedQuery(name = "Capacitador.findByTelefonoDomicilio", query = "SELECT c FROM Capacitador c WHERE c.telefonoDomicilio = :telefonoDomicilio"),
    @NamedQuery(name = "Capacitador.findByCelular", query = "SELECT c FROM Capacitador c WHERE c.celular = :celular"),
    @NamedQuery(name = "Capacitador.findByCorreo", query = "SELECT c FROM Capacitador c WHERE c.correo = :correo"),
    @NamedQuery(name = "Capacitador.findByCargo", query = "SELECT c FROM Capacitador c WHERE c.cargo = :cargo"),
    @NamedQuery(name = "Capacitador.findByFechaIngreso", query = "SELECT c FROM Capacitador c WHERE c.fechaIngreso = :fechaIngreso"),
    @NamedQuery(name = "Capacitador.findByTelefonoOficina", query = "SELECT c FROM Capacitador c WHERE c.telefonoOficina = :telefonoOficina"),
    @NamedQuery(name = "Capacitador.findByExtTelefonoOficina", query = "SELECT c FROM Capacitador c WHERE c.extTelefonoOficina = :extTelefonoOficina"),
    @NamedQuery(name = "Capacitador.findByContrasenia", query = "SELECT c FROM Capacitador c WHERE c.contrasenia = :contrasenia")})
public class Capacitador implements Serializable {

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
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
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
    @Column(name = "fecha_ingreso")
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso;
    @Size(max = 13)
    @Column(name = "telefono_oficina")
    private String telefonoOficina;
    @Column(name = "ext_telefono_oficina")
    private Integer extTelefonoOficina;
    @Size(max = 256)
    @Column(name = "contrasenia")
    private String contrasenia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "capacitadorid")
    private List<CapacitacionCapacitador> capacitacionCapacitadorList;
    @JoinColumn(name = "Administrador_id", referencedColumnName = "id")
    @ManyToOne
    private Administrador administradorid;
    @OneToMany(mappedBy = "capacitadorid")
    private List<Capacitado> capacitadoList;

    public Capacitador() {
    }

    public Capacitador(Integer id) {
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

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
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
    public List<CapacitacionCapacitador> getCapacitacionCapacitadorList() {
        return capacitacionCapacitadorList;
    }

    public void setCapacitacionCapacitadorList(List<CapacitacionCapacitador> capacitacionCapacitadorList) {
        this.capacitacionCapacitadorList = capacitacionCapacitadorList;
    }

    public Administrador getAdministradorid() {
        return administradorid;
    }

    public void setAdministradorid(Administrador administradorid) {
        this.administradorid = administradorid;
    }

    @XmlTransient
    public List<Capacitado> getCapacitadoList() {
        return capacitadoList;
    }

    public void setCapacitadoList(List<Capacitado> capacitadoList) {
        this.capacitadoList = capacitadoList;
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
        if (!(object instanceof Capacitador)) {
            return false;
        }
        Capacitador other = (Capacitador) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jpa.entities.Capacitador[ id=" + id + " ]";
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
    
    //Se debe tener una cuenta institucional dedicada para recuperacion de contraseÃƒÂ±as de capacitadores
    public boolean enviarMail() {
        
        contrasenia="contrasenia generada";
              
        try {
            // Propiedades de la conexiÃƒÂ³n
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.auth", "true");

            // Preparamos la sesion
            Session session = Session.getDefaultInstance(props);

            //Recoger los datos
            String str_De = "m.erika.05.22@gmail.com";
            String str_PwRemitente = "katy1438";
            String str_Para = "ekmasachem@unl.edu.ec";
            String str_Asunto = "CapacitySoft";
            String str_Mensaje = "CAPACITYSOFT \n correo electrónico: " + str_Para
                    + " \n contraseña: " + contrasenia + "";
            //Obtenemos los destinatarios
            String destinos[] = str_Para.split(",");

            // Construimos el mensaje
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(str_De));

    //Otra forma de especificar las direcciones de email 
            //a quienes se enviar el correo electronico
            Address[] receptores = new Address[destinos.length];
            int j = 0;
            while (j < destinos.length) {
                receptores[j] = new InternetAddress(destinos[j]);
                j++;
            }

            //receptores.
            message.addRecipients(Message.RecipientType.TO, receptores);
            message.setSubject(str_Asunto);
            message.setText(str_Mensaje);

            // Lo enviamos.
            Transport t = session.getTransport("smtp");
            t.connect(str_De, str_PwRemitente);
            t.sendMessage(message, message.getRecipients(Message.RecipientType.TO));

            // Cierre de la conexion.
            t.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }    
    }

    

}
