
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
import paw.model.ExcepcionDeAplicacion;
import paw.model.PedidoEnRealizacion;
import paw.util.UtilesString;
import paw.util.servlet.MiParameterParser;

/**
 *
 * @author raalaman
 */
public class AnulaPedidoRealizacion extends HttpServlet {

    GestorBDPedidos gbd;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            PedidoEnRealizacion pedidoACancelar = (PedidoEnRealizacion) session.getAttribute("pedidoACancelar");
            if (pedidoACancelar != null) {
                session.removeAttribute("pedidoACancelar");
                MiParameterParser parser = new MiParameterParser(request, "UTF-8");
                String accion = parser.getStringParameter("accion", null);
                if (!UtilesString.isVacia(accion)) {
                    if (accion.compareTo("anular") == 0) {
                        session.removeAttribute("pedidoRealizacion");
                        gbd.anulaPedido(pedidoACancelar);
                        response.sendRedirect("AreaCliente");
                    } else {
                        response.sendRedirect(response.encodeRedirectURL("PedidoRealizacion"));
                    }

                } else {
                    request.setAttribute("link", "AreaClientes");
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "La aplicación no puede determinar la accion");
                }

            } else {
                request.setAttribute("link", "AreaClientes");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "La aplicación no puede determinar el pedido a anular");
            }
        } catch (ExcepcionDeAplicacion ex) {
            Logger.getLogger(CierraPedido.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("link", "AreaCliente");
            throw new ServletException(ex);
        }
    }


    @Override
    public void init() throws ServletException {
        try {
            gbd = new GestorBDPedidos();
        } catch (NumberFormatException ex) {
            Logger.getLogger(CierraPedido.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }
    }
}

    


