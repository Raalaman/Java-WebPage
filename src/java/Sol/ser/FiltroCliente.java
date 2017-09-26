/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sol.ser;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import paw.model.Cliente;
import paw.util.UtilesString;

/**
 *
 * @author Manga
 */
public class FiltroCliente implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        request.setCharacterEncoding("UTF-8");
        HttpSession sesion = req.getSession();
        Cliente cliente = (Cliente) sesion.getAttribute("Cliente");
        if (cliente == null) {
            String query=req.getQueryString();
            String returnURL = req.getRequestURL() +  (UtilesString.isVacia(query) ? "" : "?" +query);
            sesion.setAttribute("returnURL", returnURL);
            resp.sendRedirect(resp.encodeURL(req.getContextPath() + "/Login"));
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig filterConfig) {
        
    }
}
