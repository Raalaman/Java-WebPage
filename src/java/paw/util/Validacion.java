/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paw.util;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import paw.bd.GestorBD;
import paw.model.ExcepcionDeAplicacion;

/**
 * Utilidades de validación.
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: Universidad de La Rioja. Departamento de Matemáticas y Computación</p>
 * @author Francisco J. García Izquierdo
 */
public class Validacion {
  
  // Tomado de http://www.mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/
  private static final String EMAIL_PATTERN =
          "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
  private static Pattern pattern;
  
  private static GestorBD gbd; 

  static {
    pattern = Pattern.compile(EMAIL_PATTERN);
    gbd = new GestorBD();
  }

  public static boolean isCPValido(String cp) {
    try {
      int cpi = Integer.parseInt(cp);
      try {
        return gbd.getAlmacenDeCP(cp) != null;
      } catch (ExcepcionDeAplicacion ex) {
        Logger.getLogger(Validacion.class.getName()).log(Level.SEVERE, null, ex);
        return cpi >= 1500 && cpi <= 52100;
      }
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
    return false;
  }

  public static boolean isEmailValido(String email) {
    Matcher matcher = pattern.matcher(email);
    return matcher.matches();
  }
}
