/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sol.ser;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import paw.bd.GestorBD;
import paw.bd.GestorEditorBD;
import paw.model.Articulo;
import paw.model.ExcepcionDeAplicacion;
import paw.util.UtilesString;
import paw.util.servlet.UtilesServlet;

/**
 *
 * @author Manga
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1,
        maxFileSize = 1024 * 1024 * 1,
        maxRequestSize = 1024 * 1024 * 1 + 10 * 1024)
public class EditaArticulo extends HttpServlet {

    private GestorEditorBD gbd;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            request.setAttribute("tiposArticulos", gbd.getTiposArticulos());
            request.setAttribute("tiposFabricantes", gbd.getFabricantes());
            request.setAttribute("tiposArticulos", gbd.getTiposArticulos());
            Articulo articulo = gbd.getArticulo(request.getParameter("idArt"));
            request.setAttribute("nombre", articulo.getNombre());
            request.setAttribute("descripcion", articulo.getDescripcion());
            request.setAttribute("tipo", articulo.getTipo());
            request.setAttribute("fabricante", articulo.getFabricante());
            request.setAttribute("pvp", articulo.getPvp());
            request.setAttribute("foto", articulo.getFoto());
            UtilesServlet.doForward(request, response, "editaArticulo.jsp");
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
            Articulo articuloBD = gbd.getArticulo(articulo.getCodigo());
            List<String> mensajesError = new ArrayList<>();
            if (articulo.equals(articuloBD)) {
                mensajesError.add("No has introducido ningún cambio en el artículo, por lo tanto no se ha hecho nada.");
            }
            mensajesError = valida(articulo, mensajesError, request);
            if (mensajesError.isEmpty()) {
                Part part = request.getPart("fichFoto");
                String mime = part.getContentType();
                if (mime.split("/")[0].compareTo("image") == 0) {
                    String cd = part.getHeader("content-disposition");
                    String fName = cd.substring(cd.indexOf("filename=") + 10, cd.lastIndexOf("\""));
                    String tipo = articulo.getTipo();
                    String realPaht = request.getServletContext().getRealPath(File.separator + "img" + File.separator + "fotosElectr" + File.separator + tipo + File.separator + fName);
                    articulo.setFoto(tipo.concat("/").concat(fName));
                    try {
                        part.write(realPaht);
                        if(!gbd.editarArticulo(articulo))
                        {
                            mensajesError.add("No se ha editado el articulo");
                        }
                        UtilesServlet.doForward(request, response, "listadoArticulos.jsp");
                    } catch (IOException |ExcepcionDeAplicacion ex) {
                        part.delete();
                        mensajesError.add("No se ha editado el articulo");
                    }

                } else {
                    mensajesError.add("El archivo pasado como parámetro no es una imagen");
                }
            } else {
                request.setAttribute("tiposArticulos", gbd.getTiposArticulos());
                request.setAttribute("tiposFabricantes", gbd.getFabricantes());
                request.setAttribute("nombre", articulo.getNombre());
                request.setAttribute("descripcion", articulo.getDescripcion());
                request.setAttribute("tipo", articulo.getTipo());
                request.setAttribute("fabricante", articulo.getFabricante());
                request.setAttribute("pvp", articulo.getPvp());
                request.setAttribute("foto", articulo.getFoto()==null?articuloBD.getFoto():articulo.getFoto());
                UtilesServlet.doForward(request, response, "editaArticulo.jsp?idArt="+articulo.getCodigo());
            }
          request.setAttribute("errores", mensajesError);
        } catch (ExcepcionDeAplicacion | IllegalStateException ex) {
            Logger.getLogger(ExcepcionDeAplicacion.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }
    }

    @Override
    public void init() throws ServletException {
        try {
            gbd = new GestorEditorBD();
        } catch (NumberFormatException ex) {
            Logger.getLogger(BuscarArticulos.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex);
        }

    }

    private List<String> valida(Articulo arti, List<String> mensajesError, HttpServletRequest request) throws ExcepcionDeAplicacion, IOException, IllegalStateException, ServletException {
        if (mensajesError.isEmpty()) {
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
            if (request.getPart("fichFoto").getSize() == 0) {
                if (mensajesError.isEmpty()) {
                    mensajesError.add("Debes proporcionar valor para todos los campos requeridos");
                }
                mensajesError.add("Tienes que añadir una imagen al artículo");
            }
        }
        return mensajesError;
    }

}
