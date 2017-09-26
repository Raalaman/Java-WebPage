/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sol.ser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import paw.bd.GestorBD;
import paw.model.Cliente;
import paw.model.Direccion;
import paw.model.ExcepcionDeAplicacion;
import paw.util.ReCaptchaException;
import paw.util.ReCaptchaValidator;
import paw.util.UtilesString;
import paw.util.Validacion;
import paw.util.servlet.MiParameterParser;
import paw.util.servlet.UtilesServlet;
import  paw.util.ReCaptchaException;


/**
 *
 * @author raalaman
 */
public class NuevoCliente extends HttpServlet {

    private GestorBD gbd;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UtilesServlet.doForward(request, response, "nuevoCliente.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ReCaptchaValidator capctha = new ReCaptchaValidator("6Lf1WyAUAAAAAGch-0--zMcPXBdIvpVnFQW32DoU", "6Lf1WyAUAAAAAHJqIT5ZKqKEHai1HfGeYzwPqjcJ");
            request.setCharacterEncoding("UTF-8");
            MiParameterParser parser = new MiParameterParser(request, "UTF-8");
            Cliente cliente = (Cliente) UtilesServlet.populateBean("paw.model.Cliente", request);
            Direccion direccion = (Direccion) UtilesServlet.populateBean("paw.model.Direccion", request);
            cliente.setDireccion(direccion);
            String usr = parser.getStringParameter("login", null);
            String pwd = parser.getStringParameter("pwd", null);
            String rpwd = parser.getStringParameter("rpwd", null);
            int privacidadOK = parser.getIntParameter("privacidad", -1);
            List<String> errores = valida(cliente, usr, pwd, rpwd, privacidadOK);
            try {
                if (!capctha.verifyResponse(request)) {
                    errores.add("Es necesario que superes el desafio del captcha");
                }
            } catch (ReCaptchaException ex) {
                     errores.add("Parece que ha habido problemas con el captcha, reintentalo de nuevo");
            }

            if (errores.isEmpty()) {
                if (UtilesString.isVacia(cliente.getCodigo())) {
                    //por si existiera un sesión, la borro.
                    HttpSession session = request.getSession(false);
                    if (session != null) {
                        session.invalidate();
                    }
                    gbd.insertaCliente(cliente, usr, pwd);
                    /*
                    Es más sensato que hagan Login, por la existencia de bots.
                    HttpSession nuevaSesion = request.getSession();
                    nuevaSesion.setAttribute("Cliente", gbd.insertaCliente(cliente, usr, pwd));
                     */
                    response.sendRedirect("clientes/AreaCliente");
                } else {
                    throw new ServletException("Usuario ya existente en base de datos");
                }
            } else {
                request.setAttribute("Cliente", cliente);
                request.setAttribute("Direccion", direccion);
                request.setAttribute("login", usr);
                request.setAttribute("pwd", pwd);
                request.setAttribute("rpwd", rpwd);
                request.setAttribute("privacidadOK", privacidadOK);
                request.setAttribute("errores", errores);
                UtilesServlet.doForward(request, response, "nuevoCliente.jsp");
            }
        } catch (ExcepcionDeAplicacion ex) {
            Logger.getLogger(NuevoCliente.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("link", "index.html");
            throw new ServletException(ex);
        }
    }

    /**
     * Realiza las validaciones para los campos del formulario de registro de
     * nuevo cliente
     *
     * @param cli objeto paw.model.Cliente con los datos leÃ­dos del formulario
     * @param usr valor del campo "nombre de usuario" del formulario
     * @param pwd valor del campo "contraseÃ±a" del formulario
     * @param rpwd valor del campo "Repita contraseÃ±a" del formulario
     * @param privacidadOK debe tener valor 1 si la casilla de "PolÃ­tica de
     * privacidad" estÃ¡ marcada
     * @param gbd objeto GestorBD para ser usado en las comprobaciones que
     * requieran de conexiÃ³n a al BD
     * @return Una lista de String con mensajes de error correspondientes a las
     * reglas de validaciÃ³n que no se cumplen
     * @throws ExcepcionDeAplicacion
     */
    private List<String> valida(Cliente cli, String usr, String pwd, String rpwd, int privacidadOK) throws ExcepcionDeAplicacion {
        List<String> mensajesError = new ArrayList<>();
        if (UtilesString.isVacia(cli.getNombre())
                || UtilesString.isVacia(cli.getCif())
                || UtilesString.isVacia(cli.getDireccion().getCalle())
                || UtilesString.isVacia(cli.getDireccion().getCiudad())
                || UtilesString.isVacia(cli.getDireccion().getProvincia())
                || UtilesString.isVacia(cli.getDireccion().getCp())
                || UtilesString.isVacia(cli.getEmail())
                || UtilesString.isVacia(usr)
                || UtilesString.isVacia(pwd)
                || UtilesString.isVacia(rpwd)) {
            mensajesError.add("Debes proporcionar valor para todos los campos requeridos");
        }

        if (privacidadOK != 1) {
            mensajesError.add("Debes leer la cláusula de privacidad y marcar la casilla correspondiente ");
        }

        if (!UtilesString.isVacia(pwd) && !UtilesString.isVacia(rpwd) && !pwd.equals(rpwd)) {
            mensajesError.add("Las contraseñas son diferentes");
        }

        if (!UtilesString.isVacia(usr) && !usr.trim().equals(usr)) {
            mensajesError.add("El nombre de usuario tiene espacios en blanco por delante o detrás ");
        }

        if (!UtilesString.isVacia(usr) && gbd.getClienteByUserName(usr) != null) {
            mensajesError.add("Ya existe un usuario en la BD con ese nombre de usuario ");
        }

        if (!UtilesString.isVacia(cli.getCif()) && gbd.getClienteByCIF(cli.getCif()) != null) {
            mensajesError.add("Ya existe un usuario en la BD con ese CIF");
            cli.setCif(null);
        }

        if (!UtilesString.isVacia(cli.getEmail()) && !Validacion.isEmailValido(cli.getEmail())) {
            mensajesError.add("El email no parece una dirección de correo válida ");
            cli.setEmail(null);
        }
        if (!UtilesString.isVacia(cli.getDireccion().getCp()) && !Validacion.isCPValido(cli.getDireccion().getCp())) {
            mensajesError.add("El CP no parece un código postal válido ");
            cli.getDireccion().setCp(null);
        }
        return mensajesError;
    }

    @Override
    public void init() throws ServletException {
        try {
            gbd = new GestorBD();
        } catch (NumberFormatException ex) {
            Logger.getLogger(NuevoCliente.class.getName()).log(Level.SEVERE, null, ex);
            // request.setAttribute("link", "index.html");
            throw new ServletException(ex);
        }

    }

}
