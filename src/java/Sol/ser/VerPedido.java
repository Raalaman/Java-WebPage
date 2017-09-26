/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sol.ser;

import com.google.gson.Gson;
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
import paw.model.Pedido;
import paw.util.UtilesString;
import paw.util.servlet.MiParameterParser;
import paw.util.servlet.UtilesServlet;

/**
 *
 * @author Manga
 */
public class VerPedido extends HttpServlet {

    GestorBDPedidos gbd;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Boolean esAjax=false;
            String headerX = request.getHeader("X-Requested-With");
            if (!UtilesString.isVacia(headerX))
            {
                esAjax=headerX.compareToIgnoreCase("XMLHttpRequest") == 0;
            }
            MiParameterParser parser = new MiParameterParser(request, "UTF-8");
            String codigo = parser.getStringParameter("cp", null);
            HttpSession session = request.getSession();
            if (esAjax) {
                if (!UtilesString.isVacia(codigo)) {
                    Pedido pedido = gbd.getPedido(codigo);
                    if (pedido != null) {
                        Cliente cliente = (Cliente) session.getAttribute("Cliente");
                        if (gbd.getPedidos(cliente.getCodigo()).stream().anyMatch(x -> x.getCodigo().compareTo(codigo) == 0)) {
                            Gson gson = new Gson();
                            String json= gson.toJson(pedido);
                            response.getWriter().write(json);
                        } else {
                            request.setAttribute("link", "../Salir");
                            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Usted no está autorizado para consultar esta información");
                        }
                    } else {
                        request.setAttribute("link", "AreaCliente");
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Código de pedido inválido");
                    }
                } else {
                    request.setAttribute("link", "../Salir");
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "No deberías estar aquí");
                }
            } else {
                if (!UtilesString.isVacia(codigo)) {
                    Pedido pedido = gbd.getPedido(codigo);
                    if (pedido != null) {
                        Cliente cliente = (Cliente) session.getAttribute("Cliente");
                        if (gbd.getPedidos(cliente.getCodigo()).stream().anyMatch(x -> x.getCodigo().compareTo(codigo) == 0)) {
                            request.setAttribute("Pedido", pedido);
                            UtilesServlet.doForward(request, response, "verPedido.jsp");
                        } else {
                            request.setAttribute("link", "../Salir");
                            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Usted no está autorizado para consultar esta información");
                        }
                    } else {
                        request.setAttribute("link", "AreaCliente");
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Código de pedido inválido");
                    }
                } else {
                    request.setAttribute("link", "../Salir");
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "No deberías estar aquí");
                }
            }
        } catch (ExcepcionDeAplicacion ex) {
            Logger.getLogger(VerPedido.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("link", "AreaCliente");
            throw new ServletException(ex);
        }
    }

    @Override
    public void init() throws ServletException {
        try {
            gbd = new GestorBDPedidos();
        } catch (NumberFormatException ex) {
            Logger.getLogger(VerPedido.class
                    .getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }

    }

}
