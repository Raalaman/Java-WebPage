/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sol.ajax;

import Sol.ser.BuscarArticulos;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import paw.bd.GestorBD;
import paw.model.Almacen;
import paw.model.ExcepcionDeAplicacion;
import paw.util.UtilesString;

/**
 *
 * @author Manga
 */
@WebServlet(name = "GetCoordenadas", urlPatterns = {"/api/GetCoordenadas"})
public class GetCoordenadas extends HttpServlet {

    GestorBD gbd;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        String cp = request.getParameter("cp");
        if (!UtilesString.isVacia(cp)) {
            try {
                Almacen almacen = gbd.getAlmacenDeCP(cp);
                Gson gson=new Gson();
                response.getWriter().print(gson.toJson(almacen));
            } catch (ExcepcionDeAplicacion ex) {
                Logger.getLogger(GetCoordenadas.class.getName()).log(Level.SEVERE, null, ex);
                response.getWriter().print("error");
            }
        }else
        {
            response.getWriter().print("error");
        }
    }

    

    @Override
    public void init() throws ServletException {
        try {
            gbd = new GestorBD();
        } catch (NumberFormatException ex) {
            Logger.getLogger(GetCoordenadas.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }

    }
}
