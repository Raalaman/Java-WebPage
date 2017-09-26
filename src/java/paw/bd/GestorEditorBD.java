/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paw.bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import paw.model.Articulo;
import paw.model.ExcepcionDeAplicacion;

/**
 *
 * @author Manga
 */
public class GestorEditorBD extends GestorBD {

    public GestorEditorBD() {
        super();
    }

    public Boolean editarArticulo(Articulo arti) throws ExcepcionDeAplicacion {
        Connection con = null;
        Boolean resultado = false;
        try {
            con = ConnectionManager.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE articulo SET  NOMBRE = ?, PVP= ?, TIPO =?, FABRICANTE =?, foto=?, descripcion=? WHERE CODIGO = ?");
           
            ps.setString(1, arti.getNombre());
            ps.setDouble(2, arti.getPvp());
            ps.setString(3, arti.getTipo());
            ps.setString(4, arti.getFabricante());
            ps.setString(5, arti.getFoto());
            ps.setString(6, arti.getDescripcion());
            ps.setString(7, arti.getCodigo());
            resultado=ps.executeUpdate()>0;
        } catch (SQLException e) {
            throw new ExcepcionDeAplicacion(e);
        } finally {
            try {
                if (con != null) {
                    ConnectionManager.returnConnection(con);
                }
            } catch (SQLException ex) {
                throw new ExcepcionDeAplicacion(ex);
            }
        }
        return resultado;
    }
}
