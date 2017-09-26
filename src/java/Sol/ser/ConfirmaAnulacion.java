package Sol.ser;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import paw.model.Pedido;
import paw.util.UtilesString;
import paw.util.servlet.MiParameterParser;

/**
 *
 * @author Manga
 */
public class ConfirmaAnulacion extends HttpServlet {

    GestorBDPedidos gbd;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            MiParameterParser parser = new MiParameterParser(request, "UTF-8");
            String codigo = parser.getStringParameter("cp", null);
            HttpSession session = request.getSession();
            if (!UtilesString.isVacia(codigo)) {
                Pedido pedido = gbd.getPedido(codigo);
                tratarPedido(pedido, session, codigo, request, response);
            } else {
                request.setAttribute("link", "../Salir");
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "No deberías estar aquí");
            }
        } catch (ExcepcionDeAplicacion ex) {
            Logger.getLogger(VerPedido.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("link", "AreaCliente");
            throw new ServletException(ex);
        }
    }

    private void tratarPedido(Pedido pedido, HttpSession session, String codigo, HttpServletRequest request, HttpServletResponse response) throws ServletException, ExcepcionDeAplicacion, IOException {
        if (pedido != null) {
            Cliente cliente = (Cliente) session.getAttribute("Cliente");
            if (gbd.getPedidos(cliente.getCodigo()).stream().anyMatch(x -> x.getCodigo().compareTo(codigo) == 0)) {
                if (!pedido.isCursado()) {
                    gbd.anulaPedido(pedido);
                    response.sendRedirect(response.encodeURL("PedidosCliente"));
                } else {
                    request.setAttribute("link", "AreaCliente");
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "“El pedido está cursado. No puede anularlo");
                }

            } else {
                request.setAttribute("link", "../Salir");
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Usted no está autorizado para consultar esta información");
            }
        } else {
            request.setAttribute("link", "AreaCliente");
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Código de pedido inválido");
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
