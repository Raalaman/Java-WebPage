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
import paw.bd.GestorBD;
import paw.bd.GestorBDPedidos;
import paw.model.Cliente;
import paw.model.ExcepcionDeAplicacion;
import paw.model.PedidoEnRealizacion;
import paw.util.UtilesString;
import paw.util.servlet.UtilesServlet;

/**
 *
 * @author raalaman
 */
public class Login extends HttpServlet {

    private GestorBD gbd;
    private GestorBDPedidos gbdPedido;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UtilesServlet.doForward(request, response, "entrada.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            HttpSession sesion = request.getSession();
            String vista = (String) sesion.getAttribute("returnURL");
            sesion.removeAttribute("returnURL");
            if (UtilesString.isVacia(vista)) {
                vista = "clientes/AreaCliente";
            }
            String user = request.getParameter("usr");
            if (user == null || user.trim().length() == 0) {
                vista = "entrada.jsp";
            }
            String pass = request.getParameter("pwd");
            if (pass == null || pass.trim().length() == 0) {
                vista = "entrada.jsp";
            }
            if (vista.compareTo("entrada.jsp") != 0 && gbd.comprobarLogin(user, pass)) {
                Cliente cliente =gbd.getClienteByUserName(user);
                sesion.setAttribute("Cliente", gbd.getClienteByUserName(user));
                PedidoEnRealizacion pedidoRealizacion = gbdPedido.getPedidoEnRealizacion(cliente.getCodigo());
                if (pedidoRealizacion != null) {
                    sesion.setAttribute("pedidoRealizacion", pedidoRealizacion);
                }
                response.sendRedirect(response.encodeRedirectURL(vista));
                return;
            } else {
                request.setAttribute("error", "El usuario  o  la  contraseña  son  incorrectos ");
                vista = "entrada.jsp";
            }
            UtilesServlet.doForward(request, response, vista);

        } catch (ExcepcionDeAplicacion ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("link", "index.jsp");
            throw new ServletException(ex);
        }
    }

    @Override
    public void init() throws ServletException {
        try {
            gbd = new GestorBD();
            gbdPedido = new GestorBDPedidos();
        } catch (NumberFormatException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }

    }

}
