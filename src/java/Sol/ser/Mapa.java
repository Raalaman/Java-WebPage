/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sol.ser;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import paw.bd.GestorBD;
import paw.model.Almacen;
import paw.model.ExcepcionDeAplicacion;
import paw.util.Validacion;
import paw.util.servlet.MiParameterParser;
import paw.util.servlet.UtilesServlet;

/**
 *
 * @author Manga
 */
public class Mapa extends HttpServlet {

    GestorBD gbd;
    Almacen almacenPrincipal;
    List<Almacen> almacenesNorte;
    List<Almacen> almacenesSur;
    List<Almacen> almacenesEste;
    List<Almacen> almacenesOeste;
    List<Almacen> almacenesCentro;
    List<Almacen> listaAlmacenes;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setAttibutesForward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setAttibutesForward(request, response);
    }

    private void setAttibutesForward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("almacenPrincipal", almacenPrincipal);
        request.setAttribute("almacenesNorte", almacenesNorte);
        request.setAttribute("almacenesSur", almacenesSur);
        request.setAttribute("almacenesEste", almacenesEste);
        request.setAttribute("almacenesOeste", almacenesOeste);
        request.setAttribute("almacenesCentro", almacenesCentro);
        request.setAttribute("listaAlmacenes", listaAlmacenes);
        UtilesServlet.doForward(request, response, "donde.jsp");
    }

    @Override
    public void init() throws ServletException {
        try {
            gbd = new GestorBD();
            almacenPrincipal = gbd.getAlmacenDeCP("28001");
            listaAlmacenes = gbd.getAlmacenes();
            almacenesNorte = listaAlmacenes.stream().filter(x -> x.getZona().compareTo("Norte") == 0).collect(Collectors.toList());
            almacenesSur = listaAlmacenes.stream().filter(x -> x.getZona().compareTo("Sur") == 0).collect(Collectors.toList());
            almacenesEste = listaAlmacenes.stream().filter(x -> x.getZona().compareTo("Este") == 0).collect(Collectors.toList());
            almacenesOeste = listaAlmacenes.stream().filter(x -> x.getZona().compareTo("Oeste") == 0).collect(Collectors.toList());
            almacenesCentro = listaAlmacenes.stream().filter(x -> x.getZona().compareTo("Centro") == 0).collect(Collectors.toList());
        } catch (NumberFormatException ex) {
            Logger.getLogger(BuscarArticulos.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        } catch (ExcepcionDeAplicacion ex) {
            Logger.getLogger(Mapa.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }

    }
}
