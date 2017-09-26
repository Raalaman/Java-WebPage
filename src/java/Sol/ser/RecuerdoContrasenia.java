/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sol.ser;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import paw.bd.GestorBD;
import paw.model.ExcepcionDeAplicacion;
import paw.util.UtilesString;
import paw.util.mail.DatosCorreo;
import paw.util.mail.GestorCorreo;
import paw.util.mail.conf.ConfiguracionCorreo;
import paw.util.servlet.MiParameterParser;
import paw.util.servlet.UtilesServlet;

/**
 *
 * @author raalaman
 */
public class RecuerdoContrasenia extends HttpServlet {

    GestorBD gbd;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UtilesServlet.doForward(request, response, "recuerdo.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            MiParameterParser parser = new MiParameterParser(request, "UTF-8");
            String usr = parser.getStringParameter("usr", null);
            if (UtilesString.isVacia(usr) || !gbd.comprobarNombreUsuario(usr)) {
                request.setAttribute("error", "El nombre de usuario no existe, intentalo de nuevo");
                UtilesServlet.doForward(request, response, "recuerdo.jsp");
            } else {
                response.sendRedirect("clientes/AreaCliente");
                String codigo = UUID.randomUUID().toString();
                DatosCorreo mensaje = new DatosCorreo(gbd.getClienteByUserName(usr).getEmail());
                mensaje.setCharset("UTF-8");
                mensaje.setMimeType("text/plain;charset=UTF-8");
                mensaje.setFrom("raalaman@unirioja.es");
                mensaje.setSubject("Cambio de contraseña electrosa");
                StringBuilder stb = new StringBuilder();
                stb.append("Cambio de contraseña en electrosa.com");
                stb.append(System.lineSeparator());
                stb.append(System.lineSeparator());
                stb.append("Usa el siguiente enlace para acceder a una página donde podrás cambiar tu contraseña: ");
                stb.append(System.lineSeparator());
                stb.append(request.getScheme()).append("://").append(request.getServerName()).append(":").append(request.getServerPort())
                        .append(request.getContextPath()).append("/CambioContrasenia?cc=").append(codigo);
                mensaje.setBody(stb.toString());
                GestorCorreo.envia(mensaje, ConfiguracionCorreo.getDefault());
                gbd.insertaRecuerdoContrasenia(usr, codigo);
            }
        } catch (ExcepcionDeAplicacion | MessagingException ex) {
            Logger.getLogger(RecuerdoContrasenia.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("link", "index.html");
            throw new ServletException(ex);
        }
    }

    @Override
    public void init() throws ServletException {
        try {
            gbd = new GestorBD();
        } catch (NumberFormatException ex) {
            Logger.getLogger(RecuerdoContrasenia.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }

    }
}
