/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sol.ser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import paw.bd.GestorBD;
import paw.model.Articulo;
import paw.model.ExcepcionDeAplicacion;
import paw.util.UtilesString;
import paw.util.servlet.UtilesServlet;

/**
 *
 * @author raalaman
 */
@WebServlet(name = "AddArticulo", urlPatterns = {"/admin/AddArticulo"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1,
        maxFileSize = 1024 * 1024 * 1,
        maxRequestSize = 1024 * 1024 * 1 + 10 * 1024)
public class AddArticulo extends HttpServlet {

    private GestorBD gbd;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("tiposArticulos", gbd.getTiposArticulos());
            request.setAttribute("tiposFabricantes", gbd.getFabricantes());
            UtilesServlet.doForward(request, response, "nuevoArticulo.jsp");
        } catch (ExcepcionDeAplicacion ex) {
            Logger.getLogger(BuscarArticulos.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Articulo articulo = (Articulo) UtilesServlet.populateBean("paw.model.Articulo", request);
            List<String> errores = valida(articulo);
            if (request.getPart("fichFoto").getSize()== 0) {
                if (errores.isEmpty()) {
                    errores.add("Debes proporcionar valor para todos los campos requeridos");
                }
                errores.add("Tienes que añadir una imagen al artículo");
            } 
            if (errores.isEmpty()) {
                Part part = request.getPart("fichFoto");
                String mime = part.getContentType();
                if (mime.split("/")[0].compareTo("image")==0)
                {
                    String cd = part.getHeader("content-disposition"); 
                    String fName = cd.substring(cd.indexOf("filename=")+10,cd.lastIndexOf("\""));
                    String tipo=articulo.getTipo();
                    String realPaht=request.getServletContext().getRealPath(File.separator+"img"+File.separator+"fotosElectr"+File.separator+tipo+File.separator+fName);
                    articulo.setFoto(tipo.concat("/").concat(fName));
                    Articulo artiAlmacenado=gbd.insertaArticulo(articulo);
                    try
                    {
                        part.write(realPaht);
                        UtilesServlet.doForward(request, response, "listadoArticulos.jsp");
                    }catch(IOException ex)
                    {
                        gbd.borraArticulo(artiAlmacenado.getCodigo(), true);
                    }
                    
                    
                }else
                {
                    errores.add("El archivo pasado como parámetro no es una imagen");
                }
                } else {
                    request.setAttribute("tiposArticulos", gbd.getTiposArticulos());
                    request.setAttribute("tiposFabricantes", gbd.getFabricantes());
                    request.setAttribute("nombre", articulo.getNombre());
                    request.setAttribute("descripcion", articulo.getDescripcion());
                    request.setAttribute("tipo", articulo.getTipo());
                    request.setAttribute("fabricante", articulo.getFabricante());
                    request.setAttribute("pvp", articulo.getPvp()==0?null:articulo.getPvp());
                    request.setAttribute("errores", errores);
                    UtilesServlet.doForward(request, response, "nuevoArticulo.jsp");
                }
        } catch (ExcepcionDeAplicacion ex) {
            Logger.getLogger(AddArticulo.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }

    }

    @Override
    public void init() throws ServletException {
        try {
            gbd = new GestorBD();
        } catch (NumberFormatException ex) {
            Logger.getLogger(BuscarArticulos.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }

    }

    private List<String> valida(Articulo arti) throws ExcepcionDeAplicacion {
        List<String> mensajesError = new ArrayList<>();
        if (UtilesString.isVacia(arti.getNombre())
                || (arti.getPvp() == 0)
                || UtilesString.isVacia(arti.getFabricante())
                || UtilesString.isVacia(arti.getTipo())) {
            mensajesError.add("Debes proporcionar valor para todos los campos requeridos");
        }

        if (UtilesString.isVacia(arti.getNombre())) {
            mensajesError.add("Te falta por escribir el nombre");
        }
        if (UtilesString.isVacia(arti.getTipo())) {
            mensajesError.add("Te falta por elegir el tipo");
        }
        if (UtilesString.isVacia(arti.getFabricante())) {
            mensajesError.add("Te falta por elgir el fabricante");
        }
        if (arti.getPvp() <= 0) {
            mensajesError.add("Te falta por elgir el PVP");
            arti.setPvp(0);
        }

        return mensajesError;
    }

   
}
