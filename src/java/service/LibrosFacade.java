/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import database.Libros;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author Ismael1
 */
@Stateless

public class LibrosFacade {
    @PersistenceContext(unitName="MiBiblioPU")
    private EntityManager em;   
        
    public Libros findBook(String isbn){
        TypedQuery<Libros> query =  em.createNamedQuery("Libros.findByIsbn", Libros.class).setParameter("isbn",isbn);
        List<Libros> lista = query.getResultList();
        if (lista == null || lista.isEmpty())
            return null;
        return lista.get(0);
    }
    
    public Libros findBookById(int id){
        TypedQuery<Libros> query =  em.createNamedQuery("Libros.findById", Libros.class).setParameter("id",id);
        return query.getResultList().get(0);
    }
    
    public void update(Libros libro){
        em.merge(libro);
        
        em.getEntityManagerFactory().getCache().evictAll();
        
    }
    
    public void create(Libros libro) {
        try {
            em.persist(libro);
            em.getEntityManagerFactory().getCache().evictAll();
        } catch (ConstraintViolationException e) {
            
            for (ConstraintViolation actual : e.getConstraintViolations()) {
                System.out.println(actual.toString());
            }
            System.out.println("==========================");
        }
    }
}
