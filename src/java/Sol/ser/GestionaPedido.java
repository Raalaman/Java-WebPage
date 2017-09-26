/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sol.ser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import paw.bd.*;
import paw.model.*;
import paw.util.UtilesString;
import paw.util.servlet.*;

/**
 *
 * @author Manga
 */
public class GestionaPedido extends HttpServlet {

    private GestorBD gbd;
    private GestorBDPedidos gbdPedido;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Cliente cliente = (Cliente) session.getAttribute("Cliente");
            PedidoEnRealizacion pedidoRealizacion = (PedidoEnRealizacion) session.getAttribute("pedidoRealizacion");
            if (pedidoRealizacion == null) {
                pedidoRealizacion = gbdPedido.getPedidoEnRealizacion(cliente.getCodigo());
                if (pedidoRealizacion == null) {
                    pedidoRealizacion = new PedidoEnRealizacion(cliente);
                }
                session.setAttribute("pedidoRealizacion", pedidoRealizacion);
            }
            MiParameterParser parser = new MiParameterParser(request, "UTF-8");
            String accion = parser.getStringParameter("accion", null);
            gestionarAccion(accion, request, response, pedidoRealizacion, parser);
        } catch (ExcepcionDeAplicacion | UnsupportedEncodingException ex) {
            Logger.getLogger(GestionaPedido.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("link", "AreaCliente");
            throw new ServletException(ex);
        }
    }

    @Override
    public void init() throws ServletException {
        try {
            gbd = new GestorBD();
            gbdPedido = new GestorBDPedidos();
        } catch (NumberFormatException ex) {
            Logger.getLogger(GestionaPedido.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }

    }

    private void gestionarAccion(String accion, HttpServletRequest request, HttpServletResponse response, PedidoEnRealizacion pedidoRealizacion, MiParameterParser parser) throws IOException, ServletException, ExcepcionDeAplicacion {
        if (!UtilesString.isVacia(accion)) {
            if (accion.compareTo("Seguir comprando") == 0) {
                procesaParams(pedidoRealizacion, request);
                response.sendRedirect("../BuscarArticulos");
            } else if (accion.compareTo("Comprar") == 0) {
                gestionarCompra(parser, pedidoRealizacion, request, response);
            } else if (accion.compareTo("Quitar") == 0) {
                procesaParams(pedidoRealizacion, request);
                UtilesServlet.doForward(request, response, "pedidoRealizacion.jsp");
            } else if (accion.compareTo("Guardar pedido") == 0) {
                procesaParams(pedidoRealizacion, request);
                gbdPedido.grabaPedidoEnRealizacion(pedidoRealizacion);
                response.sendRedirect(response.encodeURL("AreaCliente"));
            } else if (accion.compareTo("Cerrar pedido") == 0) {
                procesaParams(pedidoRealizacion, request);
                HttpSession session = request.getSession();
                session.setAttribute("pedidoACerrar", pedidoRealizacion);
                request.setAttribute("msg", "Se ve a proceder a cerrar su pedido en realización. ¿Está usted seguro?");
                request.setAttribute("siURL", "CierraPedido");
                request.setAttribute("siParameter", "cerrar");
                request.setAttribute("noURL", "CierraPedido");
                request.setAttribute("noParameter", "cancelar");
                UtilesServlet.doForward(request, response, "confirmacion.jsp");
            } else if (accion.compareTo("Cancelar") == 0) {
                HttpSession session = request.getSession();
                session.setAttribute("pedidoACancelar", pedidoRealizacion);
                request.setAttribute("msg", "Se ve a proceder a eliminar su pedido en realización. ¿Está usted seguro?");
                request.setAttribute("siURL", "AnulaPedidoRealizacion");
                request.setAttribute("siParameter", "anular");
                request.setAttribute("noURL", "AnulaPedidoRealizacion");
                request.setAttribute("noParameter", "cancelar");
                UtilesServlet.doForward(request, response, "confirmacion.jsp");
            } else {
                request.setAttribute("link", "AreaCliente");
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "No deberías estar aqui");
            }
        }
    }

    private void gestionarCompra(MiParameterParser parser, PedidoEnRealizacion pedidoRealizacion, HttpServletRequest request, HttpServletResponse response) throws ServletException, ExcepcionDeAplicacion, IOException {
        String codigoArticulo = parser.getStringParameter("ca", null);
        if (!UtilesString.isVacia(codigoArticulo)) {
            String URL = parser.getStringParameter("dir", null);
                Boolean encontrado = false;
                for (LineaEnRealizacion lineaEnrealizacion : pedidoRealizacion.getLineas()) {
                    if (lineaEnrealizacion.getArticulo().getCodigo().compareTo(codigoArticulo) == 0) {
                        encontrado = true;
                        break;
                    }
                }
                if (!encontrado) {
                    Articulo articulo = gbd.getArticulo(codigoArticulo);
                    pedidoRealizacion.addLinea(articulo);
                }
            if (!UtilesString.isVacia(URL))
            {
                response.sendRedirect(URL);
                return;
            }
            UtilesServlet.doForward(request, response, "pedidoRealizacion.jsp");
        } else {
            request.setAttribute("link", "AreaCliente");
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "No deberías estar aqui");
        }
    }

    private void procesaParams(PedidoEnRealizacion pr, HttpServletRequest req) {
        ParameterParser pp = new ParameterParser(req);
        Enumeration<String> pnames = req.getParameterNames();
        List<String> lineasBorramos = new ArrayList<>();
        while (pnames.hasMoreElements()) {
            String pn = pnames.nextElement();
            if (pn.startsWith("CL_")) {
                String codigoLinea = pn.substring(3);
                pr.removeLinea(codigoLinea);
                lineasBorramos.add(codigoLinea);
            }
            if (pn.startsWith("C_")) {
                String cl = pn.substring(2);
                if (lineasBorramos.isEmpty() || !lineasBorramos.stream().anyMatch(x -> x.compareTo(cl) == 0)) {
                    LineaEnRealizacion lr = pr.getLinea(cl);
                    int cantidad = pp.getIntParameter(pn, 1);
                    lr.setCantidad(cantidad);
                }
            } else if (pn.startsWith("F_")) {
                String cl = pn.substring(2);
                if (lineasBorramos.isEmpty() || !lineasBorramos.stream().anyMatch(x -> x.compareTo(cl) == 0)) {
                    LineaEnRealizacion lr = pr.getLinea(cl);
                    Calendar fe = pp.getCalendarParameter(pn, "dd/MM/yyyy", Calendar.getInstance());
                    lr.setFechaEntregaDeseada(fe);
                }
            }

        }
    }

}
