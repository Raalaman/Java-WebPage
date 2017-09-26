package paw.util;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import paw.debug.Debug;

/**
 * Diversas utilidades de Strings
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: Universidad de La Rioja. Departamento de Matemáticas y Computación</p>
 * @author Francisco J. García Izquierdo
 */
public class UtilesString {
  /**
   * Devuelve true si la cadena es null o solo blancos
   */
  public static boolean isVacia(String cadena) {
    return cadena == null || cadena.trim().length() == 0;
  }
  
  /**
   * Devuelve un hash del string enviado como parámetro, usando el algoritmo
   * indicado (MD5 o SHA)
   * @param password
   * @param algoritmo
   */
  public static String codificaContrasenia(String password, String algoritmo) {
    byte[] sinCodificar = password.getBytes();
    MessageDigest md = null;
    try {
      md = MessageDigest.getInstance(algoritmo);
    } catch (Exception e) {
      Debug.severe(UtilesString.class.getName(), null, e);
      return password;
    }
    md.reset();
    
    md.update(sinCodificar);
    
    byte[] codificada = md.digest();
    StringBuffer buf = new StringBuffer();
    for (int i = 0; i < codificada.length; i++) {
      if (((int) codificada[i] & 0xff) < 0x10) {
        buf.append("0");
      }
      buf.append(Long.toString((int) codificada[i] & 0xff, 16));
    }
    return buf.toString();
  }

  /**
   * Devuelve una lista de strings conteniendo las partes en las que se puede 
   * partir una cadena de texto usando como separador una determinada expresión
   * regular. La lista no contiene repetidos.
   * @param texto
   * @param regExp
   * @see java.lang.String#split(java.lang.String) 
   */
  public static List<String> extraerSubstringSinRepes(String texto, String regExp) {
    if (texto == null) {
      return null;
    }

    String[] partes = texto.split(regExp);
    for (int i = 0; i < partes.length; i++) {
      partes[i] = partes[i].trim();
    }
    
    List<String> partesL = new ArrayList<String>(partes.length);
    // Arrays.asList(partes);
    for (int i = 0; i < partes.length; i++) {
      String t = partes[i];
      if (!partesL.contains(t))
        partesL.add(t);
    }
    
    return partesL;    
  }

  /**
   * Devuelve el nombre de un fichero de un path determinado (pPathFichero)
   * @param     pPathFichero  path del fichero
   */
  public static String extraerNombreFichero(String pPathFichero) {
    pPathFichero = pPathFichero.replace('\\', '/');
    String nombre = null;
    if (pPathFichero != null && !pPathFichero.trim().equals("")) {
      int pos = pPathFichero.lastIndexOf("/");
      nombre = pPathFichero.substring(pos + 1, pPathFichero.length());
    }
    return nombre;
  }
}
