/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sol.ajax;

import Sol.ser.BuscarArticulos;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import paw.bd.GestorBD;
import paw.model.ExcepcionDeAplicacion;
import paw.util.UtilesString;

/**
 *
 * @author Manga
 */
public class GetStockArticulo extends HttpServlet {

    GestorBD gbd;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        String codigo = request.getParameter("cart");
        String resultado = "0";
        if (!UtilesString.isVacia(codigo)) {
            try {
                resultado = String.valueOf(gbd.getStockArticulo(codigo));
            } catch (ExcepcionDeAplicacion ex) {
                Logger.getLogger(GetStockArticulo.class.getName()).log(Level.SEVERE, null, ex);
                throw new ServletException(ex);
            }
        }
        response.getWriter().write(resultado);
    }

    

    @Override
    public void init() throws ServletException {
        try {
            gbd = new GestorBD();
        } catch (NumberFormatException ex) {
            Logger.getLogger(GetStockArticulo.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }

    }

}
