/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import database.Usuarios;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Ismael1
 */
@Stateless
public class UsuariosPrueba {
    @PersistenceContext(unitName="MiBiblioPU")
    private EntityManager em;   
        
    public boolean isRegister(String usuario,String pass){
        TypedQuery<Usuarios> query =  em.createNamedQuery("Usuarios.login", Usuarios.class).setParameter("usuario",usuario).setParameter("pass",pass);
        return query.getResultList().size() > 0;
    }
    
    public boolean register(String usuario,String pass,String nombre,String direccion,int dni){
        Usuarios a = new Usuarios();
        a.setDireccion(direccion);
        a.setDni(dni);
        a.setNombre(nombre);
        a.setPass(pass);
        a.setUsuario(usuario);
        a.setTipo(1);
        em.persist(a);
        return true;
    }
    
    public Usuarios findUser(String usuario){
        TypedQuery<Usuarios> query =  em.createNamedQuery("Usuarios.findByUsuario", Usuarios.class).setParameter("usuario",usuario);
        List<Usuarios> a = query.getResultList();
        if (a.size() > 0) {
            return a.get(0);
        } else {
            return null;
        }
    }
}
