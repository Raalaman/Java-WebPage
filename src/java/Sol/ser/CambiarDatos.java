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
import paw.util.UtilesString;
import paw.util.Validacion;
import paw.util.servlet.UtilesServlet;

/**
 *
 * @author Manga
 */
public class CambiarDatos extends HttpServlet {

    GestorBD gbd;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        if (session != null) {
            Cliente cliente = (Cliente) session.getAttribute("Cliente");
            request.setAttribute("Cliente", cliente);
            request.setAttribute("Direccion", cliente.getDireccion());
            UtilesServlet.doForward(request, response, "cambiarDatos.jsp");
        } else {
            request.setAttribute("link", "AreaCliente");
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "No existe sesión creada");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            HttpSession session = request.getSession(false);
            if (session != null) {
                Cliente cliente = (Cliente) UtilesServlet.populateBean("paw.model.Cliente", request);
                Direccion direccion = (Direccion) UtilesServlet.populateBean("paw.model.Direccion", request);
                Cliente clienteSesion = (Cliente) session.getAttribute("Cliente");
                cliente.setCodigo(clienteSesion.getCodigo());
                cliente.setDireccion(direccion);
                List<String> errores = valida(cliente);
                if (errores.isEmpty()) {

                    if (gbd.editaCliente(cliente)) {
                        session.setAttribute("Cliente", cliente);
                        response.sendRedirect(response.encodeURL("AreaCliente"));
                    } else {
                        request.setAttribute("link", "AreaCliente");
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ha habido problemas al actualizar la base de datos");
                    }

                } else {
                    request.setAttribute("Cliente", clienteSesion);
                    request.setAttribute("Direccion", clienteSesion.getDireccion());
                    request.setAttribute("ClienteCambio", cliente);
                    request.setAttribute("DireccionCambio", direccion);
                    request.setAttribute("errores", errores);
                    UtilesServlet.doForward(request, response, "cambiarDatos.jsp");
                }
            } else {
                request.setAttribute("link", "AreaCliente");
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "No existe sesión creada");
            }
        } catch (ExcepcionDeAplicacion ex) {
            Logger.getLogger(CambiarDatos.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("link", "AreaCliente");
            throw new ServletException(ex);
        }

    }

    @Override
    public void init() throws ServletException {
        try {
            gbd = new GestorBD();

        } catch (NumberFormatException ex) {
            Logger.getLogger(BuscarArticulos.class
                    .getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }

    }

    /**
     * Realiza las validaciones para los campos de edicion de cliente
     *
     * @param cli objeto paw.model.Cliente con los datos leÃ­dos del formulario
     * @param gbd objeto GestorBD para ser usado en las comprobaciones que
     * requieran de conexiÃ³n a al BD
     * @return Una lista de String con mensajes de error correspondientes a las
     * reglas de validaciÃ³n que no se cumplen
     * @throws ExcepcionDeAplicacion
     */
    private List<String> valida(Cliente cli) throws ExcepcionDeAplicacion {
        List<String> mensajesError = new ArrayList<>();
        if (UtilesString.isVacia(cli.getNombre())
                || UtilesString.isVacia(cli.getCif())
                || UtilesString.isVacia(cli.getDireccion().getCalle())
                || UtilesString.isVacia(cli.getDireccion().getCiudad())
                || UtilesString.isVacia(cli.getDireccion().getProvincia())
                || UtilesString.isVacia(cli.getDireccion().getCp())
                || UtilesString.isVacia(cli.getEmail())) {
            mensajesError.add("Debes proporcionar valor para todos los campos requeridos");
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

}
