/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sol.ser;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import paw.util.servlet.UtilesServlet;

/**
 *
 * @author raalaman
 */
public class AreaCliente extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        proccessPeticion(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        proccessPeticion(request, response);
    }

    private void proccessPeticion(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        UtilesServlet.doForward(request, response, "index.jsp");
    }

}
