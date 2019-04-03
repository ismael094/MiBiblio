/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import com.google.gson.Gson;
import database.Compras;
import database.Libros;
import database.Reservas;
import database.Usuarios;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.xml.ws.WebServiceRef;
import static md5.MD5.getMD5;
import org.json.JSONArray;
import org.json.JSONObject;
import proveedorcliente.ProveedorWS_Service;
import service.ComprasFacade;
//import proveedorclient.ProveedorWS_Service;
import service.LibrosFacade;
import service.ReservasFacade;
import service.UsuariosPrueba;

/**
 *
 * @author Ismael1
 */
@WebService(serviceName = "MiBibliotecaWS")
public class MiBibliotecaWS {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/ws.docencia.ces.siani.es/a01/Proveedor/ProveedorWS.wsdl")
    private ProveedorWS_Service service;

    @EJB
    UsuariosPrueba usuarios;
    @EJB
    LibrosFacade libros;
    @EJB
    ReservasFacade reservas;
    @EJB
    ComprasFacade compras;
    
    @WebMethod(operationName = "login")
    public boolean login(@WebParam(name = "usuario") String usuario, @WebParam(name = "pass") String pass) {
        return usuarios.isRegister(usuario, getMD5(pass));
    }
    
    @WebMethod(operationName = "registro")
    public boolean registro(@WebParam(name = "usuario") String usuario, @WebParam(name = "pass") String pass, @WebParam(name = "nombre") String nombre, @WebParam(name = "direccion") String direccion, @WebParam(name = "dni") Integer dni) {
        Usuarios a = usuarios.findUser(usuario);
        if (a != null)
            return false;
        return usuarios.register(usuario, getMD5(pass),nombre,direccion,dni);
    }
    
    @WebMethod(operationName = "gettipo")
    public int getTipo(@WebParam(name = "usuario") String usuario) {
        return usuarios.findUser(usuario).getTipo();
    }
    
    @WebMethod(operationName = "reservar")
    public boolean reservar(@WebParam(name = "usuario") String usuario, @WebParam(name = "isbn") String isbn,@WebParam(name = "fecha_inicio") String fecha_incio, @WebParam(name = "fecha_entrega") String fecha_entrega) {
        Libros libroEntity = libros.findBook(isbn);
        if (libroEntity.getStock() == 0) {
            return false;
        }
        Usuarios usuarioEntity = usuarios.findUser(usuario);
        
        Reservas reservasEntity = new Reservas();
        reservasEntity.setIdLibro(libroEntity);
        reservasEntity.setUsuario(usuarioEntity);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            reservasEntity.setFechaInicio(formatter.parse(fecha_incio));
            reservasEntity.setFechaEntrega(formatter.parse(fecha_entrega));

        } catch (Exception e) {
            ;
        } 
        reservasEntity.setEstado("E");
        reservas.createReservas(reservasEntity);
        libroEntity.setStock(libroEntity.getStock()-1);
        libros.update(libroEntity);
        
        return true;
    }
    
    @WebMethod(operationName = "misreservas")
    public String misreservas(@WebParam(name = "usuario") String usuario) {
        Usuarios user = usuarios.findUser(usuario);
        List<Reservas> reservasList = reservas.findReservas(user);
        //String res = "{ \"books\" : [";
        Gson g = new Gson();
        /*for (Reservas r : reservasList) {
            res+=g.toJson(r)+",";
        }*/
        return g.toJson(reservasList);
    }
    
    
    @WebMethod(operationName = "penalizaciones")
    public String penalizaciones(@WebParam(name = "usuario") String usuario) {
        Usuarios user = usuarios.findUser(usuario);
        List<Reservas> reservasList = reservas.findPenalizaciones(user);
        //String res = "{ \"books\" : [";
        Gson g = new Gson();
        /*for (Reservas r : reservasList) {
            res+=g.toJson(r)+",";
        }*/
        return g.toJson(reservasList);
    }
    
    @WebMethod(operationName = "reservasActivas")
    public String reservasActivas() {
        List<Reservas> reservasList = reservas.findActiveReserva();
        Gson g = new Gson();
        return g.toJson(reservasList);
    }
    
    @WebMethod(operationName = "devolver")
    public boolean devolver(@WebParam(name = "id") int id) {
        Reservas r = reservas.findById(id);
        Date date = new Date();
        int diff = date.compareTo(r.getFechaEntrega());
        if (diff > 0) {
            r.setFechaEntrega(date);
            r.setEstado("P");
            r.setPenalizacion(diff*3);
        } else {
            r.setEstado("D");
        }
        
        reservas.update(r);
        r.getIdLibro().setStock(r.getIdLibro().getStock()+1);
        libros.update(r.getIdLibro());
        return true;
    }
    
    @WebMethod(operationName = "comprar")
    public boolean comprar(@WebParam(name = "isbn") java.lang.String isbn, @WebParam(name = "cantidad") int cantidad) {
        proveedorcliente.ProveedorWS port = service.getProveedorWSPort();
        String book = port.comprar(isbn, cantidad);
        if (book.length() > 0) {
            Libros l = libros.findBook(isbn);
            JSONObject obj = new JSONObject(book);
            JSONArray rest = obj.getJSONArray("books");
            Gson g = new Gson();
            Libros libroEntity = g.fromJson(rest.getJSONObject(0).toString(), Libros.class);
            if (l == null) {
                libroEntity.setStock(cantidad);
                
                libros.create(libroEntity);
                Libros libroDB = libros.findBook(libroEntity.getIsbn());
                System.out.println(libroDB);
                Compras compra = new Compras();
                compra.setIdLibro(libroDB);
                compra.setCantidad(cantidad);
                compras.create(compra);
                
            } else {
                l.setStock(l.getStock()+cantidad);
                libros.update(l);
            }
            return true;
        }
        return false;
    }
    
    @WebMethod(operationName = "compras")
    public String compras() {
        List<Compras> comprasList = compras.getCompras();
        Gson g = new Gson();
        return g.toJson(comprasList);
    }
}
