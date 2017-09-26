/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sol.ser;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import paw.bd.CriteriosArticulo;
import paw.bd.GestorBD;
import paw.bd.Paginador;
import paw.model.Articulo;
import paw.model.ExcepcionDeAplicacion;
import paw.util.servlet.UtilesServlet;

/**
 *
 * @author raalaman
 */
public class BuscarArticulos extends HttpServlet {

    private static int tamanioPagina = 15;
    private static GestorBD gbd = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        procesarPeticion(request,response);
        
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        procesarPeticion(request,response);
        
    }
    
    
    

    @Override
    public void init() throws ServletException {
        try {
            tamanioPagina = Integer.parseInt(this.getInitParameter("tamanioPagina"));
            gbd = new GestorBD();
        } catch (NumberFormatException ex) {
            Logger.getLogger(BuscarArticulos.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }

    }

    private void procesarPeticion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            CriteriosArticulo critArti = new CriteriosArticulo();
            String fabricante = request.getParameter("fabricante");
            String nombreArti = request.getParameter("nombreArti");
            String nombreCode = request.getParameter("nombreCode");
            String tipo = request.getParameter("tipo");
            Paginador paginador = null;
            String precio = request.getParameter("precio");
            int pag;
            try {
                pag = Integer.parseInt(request.getParameter("pag"));
            } catch (NumberFormatException ex) {
                pag = 1;
            }
            if (nombreArti != null && nombreArti.trim().length() != 0) {
                critArti.setNombre(nombreArti);
                request.setAttribute("nombreArti", nombreArti);
            }
            if (nombreCode != null && nombreCode.trim().length() != 0) {
                critArti.setCodigo(nombreCode);
                request.setAttribute("nombreCode", nombreCode);
            }
            if (fabricante != null && fabricante.trim().length() != 0) {
                critArti.setFabricante(fabricante);
                request.setAttribute("fabricante", fabricante);
            }
            if (tipo != null && tipo.trim().length() != 0) {
                critArti.setTipo(tipo);
                request.setAttribute("tipo", tipo);
            }
            if (precio != null && precio.trim().length() != 0) {
                critArti.setPrecio(precio);
                request.setAttribute("precio", precio);
            }

            if (fabricante != null || tipo != null || precio != null) {
                try {
                    paginador = gbd.getPaginadorArticulos(critArti, tamanioPagina);
                    if (paginador.getNumPaginas() != 0) {

                        if (pag > paginador.getNumPaginas()) {
                            pag = paginador.getNumPaginas();
                        } else if (pag < 1) {
                            pag = 1;
                        }
                        List<Articulo> listaArticulos = gbd.getArticulos(critArti, pag, tamanioPagina);
                        request.setAttribute("paginaActual", pag);
                        request.setAttribute("articulos", listaArticulos);
                        if (listaArticulos.size() == 1) {
                            response.sendRedirect("fichaArticulo.jsp?cart=" + listaArticulos.get(0).getCodigo());
                            return;
                        }
                    }
                } catch (ExcepcionDeAplicacion ex) {
                    Logger.getLogger(BuscarArticulos.class.getName()).log(Level.SEVERE, null, ex);
                }
                request.setAttribute("paginador", paginador);
            }
            request.setAttribute("tiposArticulos", gbd.getTiposArticulos());
            request.setAttribute("tiposFabricantes", gbd.getFabricantes());
            UtilesServlet.doForward(request, response, "catalogo.jsp");
        } catch (ExcepcionDeAplicacion ex) {
            Logger.getLogger(BuscarArticulos.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("link", "index.html");
            throw new ServletException(ex);
        }
    }

}
