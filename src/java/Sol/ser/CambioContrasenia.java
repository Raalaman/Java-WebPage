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
import paw.model.ExcepcionDeAplicacion;
import paw.util.UtilesString;
import paw.util.servlet.MiParameterParser;
import paw.util.servlet.UtilesServlet;

/**
 *
 * @author Manga
 */
public class CambioContrasenia extends HttpServlet {

    private GestorBD gbd;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            MiParameterParser parser = new MiParameterParser(request, "UTF-8");
            String codigo = parser.getStringParameter("cc", null);
            String usuario = gbd.getUserNameDeRecuerdo(codigo);
            if (usuario == null) {
                request.setAttribute("link", "index.html");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Petición de cambio de contraseña inválida. Es posible que el código de cambio haya expirado. Vuelva a solicitar el cambio");
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("usuario", usuario);
                session.setAttribute("codigo", codigo);
                UtilesServlet.doForward(request, response, "cambioContrasenia.jsp");
            }
        } catch (ExcepcionDeAplicacion ex) {
            Logger.getLogger(CambioContrasenia.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("link", "index.html");
            throw new ServletException(ex);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MiParameterParser parser = new MiParameterParser(request, "UTF-8");
        String pwd = parser.getStringParameter("pwd", null);
        String rpwd = parser.getStringParameter("rpwd", null);
        HttpSession session = request.getSession();
        String usuario = (String) session.getAttribute("usuario");
        String codigo = (String) session.getAttribute("codigo");
        session.removeAttribute("usuario");
        session.removeAttribute("codigo");
        try {
            if (pwd.compareTo(rpwd) == 0 && !UtilesString.isVacia(usuario) && !UtilesString.isVacia(codigo)) {
                gbd.cambiaContrasenia(usuario, pwd);
                codigo = null;
                response.sendRedirect("clientes/AreaCliente");
            } else {
                request.setAttribute("link", "index.jsp");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Petición de cambio de contraseña inválida. Es posible que el código de cambio haya expirado. Vuelva a solicitar el cambio de nuevo");
            }
        } catch (ExcepcionDeAplicacion ex) {
            Logger.getLogger(CambioContrasenia.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("link", "index.jsp");
            throw new ServletException(ex);
        } finally {
            session.invalidate();
            if (!UtilesString.isVacia(codigo)) {
                try {
                    gbd.borraRecuerdoContrasenia(codigo);
                } catch (ExcepcionDeAplicacion ex) {
                    Logger.getLogger(CambioContrasenia.class.getName()).log(Level.SEVERE, null, ex);
                    request.setAttribute("link", "index.jsp");
                    throw new ServletException(ex);
                }
            }

        }

    }

    @Override
    public void init() throws ServletException {
        try {
            gbd = new GestorBD();
        } catch (NumberFormatException ex) {
            Logger.getLogger(CambioContrasenia.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }

    }

}
