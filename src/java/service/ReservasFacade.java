/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import database.Reservas;
import database.Usuarios;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Ismael1
 */
@Stateless
public class ReservasFacade {
    @PersistenceContext(unitName="MiBiblioPU")
    private EntityManager em;   
        
    public void createReservas(Reservas reserva){
        em.persist(reserva);
        em.getEntityManagerFactory().getCache().evictAll();
    }
    
    public List<Reservas> findActiveReserva(){
        em.getEntityManagerFactory().getCache().evictAll();
        TypedQuery<Reservas> query =  em.createNamedQuery("Reservas.findByEstado", Reservas.class).setParameter("estado","E");
        return query.getResultList();
    }
    
    public List<Reservas> findReservas(Usuarios user){
        em.getEntityManagerFactory().getCache().evictAll();
        TypedQuery<Reservas> query =  em.createNamedQuery("Reservas.findByUsuario", Reservas.class).setParameter("usuario",user);
        return query.getResultList();
    }
    
    public List<Reservas> findPenalizaciones(Usuarios user){
        em.getEntityManagerFactory().getCache().evictAll();
        TypedQuery<Reservas> query =  em.createNamedQuery("Reservas.findPenalizaciones", Reservas.class).setParameter("usuario",user).setParameter("pen","P");
        return query.getResultList();
    }
    
    public Reservas findReservasById(String user, int id){
        em.getEntityManagerFactory().getCache().evictAll();
        TypedQuery<Reservas> query =  em.createNamedQuery("Reservas.findByUsuarioIdLibro", Reservas.class).setParameter("usuario",user).setParameter("id",id);
        return query.getResultList().get(0);
    }
    
    public Reservas findById(int id){
        em.getEntityManagerFactory().getCache().evictAll();
        TypedQuery<Reservas> query =  em.createNamedQuery("Reservas.findById", Reservas.class).setParameter("id",id);
        return query.getResultList().get(0);
    }
    
    public void update(Reservas r){
        em.merge(r);
        em.getEntityManagerFactory().getCache().evictAll();
    }
}
