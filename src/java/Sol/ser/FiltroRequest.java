/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sol.ser;

import java.io.IOException;
import java.nio.charset.*;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *
 * @author Rafa
 */
public class FiltroRequest implements Filter {

    private static String encoding = "ISO-8859-1";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
            String parametro=filterConfig.getInitParameter("Encoding");
            if (Charset.isSupported(parametro))
            {
                encoding=parametro;
            } 
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
           request.setCharacterEncoding(encoding);
           chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
