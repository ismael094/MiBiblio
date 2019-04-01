/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ismael1
 */
@Entity
@Table(name = "reservas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reservas.findAll", query = "SELECT r FROM Reservas r")
    , @NamedQuery(name = "Reservas.findById", query = "SELECT r FROM Reservas r WHERE r.id = :id")
    , @NamedQuery(name = "Reservas.findByUsuario", query = "SELECT r FROM Reservas r WHERE r.usuario = :usuario")
    , @NamedQuery(name = "Reservas.findPenalizaciones", query = "SELECT r FROM Reservas r WHERE r.usuario = :usuario and r.estado = :pen")
    , @NamedQuery(name = "Reservas.findByFechaInicio", query = "SELECT r FROM Reservas r WHERE r.fechaInicio = :fechaInicio")
    , @NamedQuery(name = "Reservas.findByUsuarioIdLibro", query = "SELECT r FROM Reservas r where r.usuario = :usuario and r.idLibro = :id")
    , @NamedQuery(name = "Reservas.findByFechaEntrega", query = "SELECT r FROM Reservas r WHERE r.fechaEntrega = :fechaEntrega")
    , @NamedQuery(name = "Reservas.findByEstado", query = "SELECT r FROM Reservas r WHERE r.estado = :estado")})
public class Reservas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_entrega")
    @Temporal(TemporalType.DATE)
    private Date fechaEntrega;
    @Size(max = 1)
    @Column(name = "estado")
    private String estado;
    @Column(name = "penalizacion")
    private Integer penalizacion;
    @JoinColumn(name = "idLibro", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Libros idLibro;
    @JoinColumn(name = "usuario", referencedColumnName = "usuario")
    @ManyToOne(optional = false)
    private Usuarios usuario;

    public Reservas() {
    }

    public Reservas(Integer id) {
        this.id = id;
    }

    public Reservas(Integer id, Date fechaInicio, Date fechaEntrega) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaEntrega = fechaEntrega;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Integer getPenalizacion() {
        return penalizacion;
    }

    public void setPenalizacion(Integer penalizacion) {
        this.penalizacion = penalizacion;
    }
    
    

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Libros getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Libros idLibro) {
        this.idLibro = idLibro;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
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
        if (!(object instanceof Reservas)) {
            return false;
        }
        Reservas other = (Reservas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.Reservas[ id=" + id + " ]";
    }
    
}
