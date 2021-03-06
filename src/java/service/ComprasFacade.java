/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import database.Compras;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author entrar
 */
@Stateless
public class ComprasFacade {
    @PersistenceContext(unitName="MiBiblioPU")
    private EntityManager em;   
        
    public List<Compras> getCompras(){
        em.getEntityManagerFactory().getCache().evictAll();
        TypedQuery<Compras> query =  em.createNamedQuery("Compras.find", Compras.class);
        List<Compras> lista = query.getResultList();
        if (lista == null || lista.isEmpty())
            return null;
        return lista;
    }
    
    public void create(Compras compra) {
        try {
            em.persist(compra);
            em.getEntityManagerFactory().getCache().evictAll();
        } catch (ConstraintViolationException e) {
            ;
        }
    }
    
    

}
