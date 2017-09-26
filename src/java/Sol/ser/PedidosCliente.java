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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import paw.bd.GestorBDPedidos;
import paw.model.Cliente;
import paw.model.ExcepcionDeAplicacion;
import paw.util.UtilesString;
import paw.util.servlet.UtilesServlet;

/**
 *
 * @author raalaman
 */
public class PedidosCliente extends HttpServlet {

    GestorBDPedidos gbd;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session != null) {
            try {
                Cliente cliente = (Cliente) session.getAttribute("Cliente");
                request.setAttribute("PedidosPendientes", gbd.getPedidosPendientes(cliente.getCodigo()));
                request.setAttribute("PedidosCompletados", gbd.getPedidosCompletados(cliente.getCodigo()));
                request.setAttribute("PedidosAnulados", gbd.getPedidosAnulados(cliente.getCodigo()));
                UtilesServlet.doForward(request, response, "pedidosCliente.jsp");
            } catch (ExcepcionDeAplicacion ex) {
                Logger.getLogger(PedidosCliente.class.getName()).log(Level.SEVERE, null, ex);
                String query = request.getQueryString();
                request.setAttribute("link", request.getRequestURL() + (UtilesString.isVacia(query) ? "" : "?" + query));
                throw new ServletException(ex);
            }
        } else {
            request.setAttribute("link", "AreaCliente");
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "No existe sesión creada");
        }
    }

    @Override
    public void init() throws ServletException {
        try {
            gbd = new GestorBDPedidos();
        } catch (NumberFormatException ex) {
            Logger.getLogger(PedidosCliente.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }

    }

}
