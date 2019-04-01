/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ismael1
 */
@Entity
@Table(name = "libros")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Libros.findAll", query = "SELECT l FROM Libros l")
    , @NamedQuery(name = "Libros.findById", query = "SELECT l FROM Libros l WHERE l.id = :id")
    , @NamedQuery(name = "Libros.findByTitle", query = "SELECT l FROM Libros l WHERE l.title = :title")
    , @NamedQuery(name = "Libros.findByAuthorName", query = "SELECT l FROM Libros l WHERE l.authorName = :authorName")
    , @NamedQuery(name = "Libros.findByIsbn", query = "SELECT l FROM Libros l WHERE l.isbn = :isbn")
    , @NamedQuery(name = "Libros.findByOclc", query = "SELECT l FROM Libros l WHERE l.oclc = :oclc")
    , @NamedQuery(name = "Libros.findByPublishYear", query = "SELECT l FROM Libros l WHERE l.publishYear = :publishYear")
    , @NamedQuery(name = "Libros.findBySubject", query = "SELECT l FROM Libros l WHERE l.subject = :subject")
    , @NamedQuery(name = "Libros.findByStock", query = "SELECT l FROM Libros l WHERE l.stock = :stock")
    , @NamedQuery(name = "Libros.findByPublisher", query = "SELECT l FROM Libros l WHERE l.publisher = :publisher")})
public class Libros implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "author_name")
    private String authorName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "isbn")
    private String isbn;
    @Basic(optional = false)
    @Size(min = 1, max = 20)
    @Column(name = "oclc")
    private String oclc;
    @Basic(optional = false)
    @Column(name = "publish_year")
    private int publishYear;
    @Basic(optional = false)
    @Size(min = 1, max = 255)
    @Column(name = "subject")
    private String subject;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stock")
    @OneToOne(cascade=CascadeType.MERGE)
    private int stock;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "publisher")
    private String publisher;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idLibro")
    private transient Collection<Reservas> reservasCollection;

    public Libros() {
    }

    public Libros(Integer id) {
        this.id = id;
    }

    public Libros(Integer id, String title, String authorName, String isbn, String oclc, int publishYear, String subject, int stock, String publisher) {
        this.id = id;
        this.title = title;
        this.authorName = authorName;
        this.isbn = isbn;
        this.oclc = oclc;
        this.publishYear = publishYear;
        this.subject = subject;
        this.stock = stock;
        this.publisher = publisher;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getOclc() {
        return oclc;
    }

    public void setOclc(String oclc) {
        this.oclc = oclc;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @XmlTransient
    public Collection<Reservas> getReservasCollection() {
        return reservasCollection;
    }

    public void setReservasCollection(Collection<Reservas> reservasCollection) {
        this.reservasCollection = reservasCollection;
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
        if (!(object instanceof Libros)) {
            return false;
        }
        Libros other = (Libros) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.Libros[ id=" + id + " ]";
    }
    
}
