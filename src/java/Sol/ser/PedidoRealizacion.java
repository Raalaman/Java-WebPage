/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sol.ser;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import paw.bd.GestorBDPedidos;
import paw.model.Cliente;
import paw.model.ExcepcionDeAplicacion;
import paw.model.PedidoEnRealizacion;
import paw.util.servlet.UtilesServlet;

/**
 *
 * @author Manga
 */
public class PedidoRealizacion extends HttpServlet {

    GestorBDPedidos gbd;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Cliente cliente = (Cliente) session.getAttribute("Cliente");
            PedidoEnRealizacion pedidoRealizacion = (PedidoEnRealizacion) session.getAttribute("pedidoRealizacion");
            if (pedidoRealizacion == null) {
                pedidoRealizacion = gbd.getPedidoEnRealizacion(cliente.getCodigo());
                if (pedidoRealizacion != null) {
                    session.setAttribute("pedidoRealizacion", pedidoRealizacion);
                }
            } 
            UtilesServlet.doForward(request, response, "pedidoRealizacion.jsp");
        } catch (ExcepcionDeAplicacion ex) {
            Logger.getLogger(PedidoRealizacion.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }

    }

    @Override
    public void init() throws ServletException {
        try {
            gbd = new GestorBDPedidos();
        } catch (NumberFormatException ex) {
            Logger.getLogger(PedidoRealizacion.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }

    }

}