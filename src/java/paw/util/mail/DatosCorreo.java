package paw.util.mail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Set;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import paw.debug.Debug;
import paw.util.UtilesString;

/**
 * Clase para almacenar las características de un email (to, from, subject, 
 * adjuntos...). Permite añadir adjuntos y especificar si se trata de un correo
 * con cuerpo HTML (poniendo text/html como tipo MIME).
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: Universidad de La Rioja. Departamento de Matemáticas y Computación</p>
 * @author Francisco J. García Izquierdo
 */
public class DatosCorreo {

//  private String from;
  private InternetAddress from;
  private List<String> to;
  private String subject;
  private String body;
  private Map<String, String> adjuntos;
  private String mimeType;
  private List cc;
  private List bcc;
  private String charset;

  /**
   * Devuelve el conjunto de caracteres a utilizar para el texto del título y 
   * del cuerpo del mensaje
   *
   */
  public String getCharset() {
    return charset;
  }

  /**
   * Estableceel el conjunto de caracteres a utilizar para el texto del título y 
   * del cuerpo del mensaje
   */
  public void setCharset(String charset) {
    this.charset = charset;
  }


  /**
   * Crea un objeto DatosCorreo a partir de una lista de direcciones de 
   * destinatarios (Strings). Cada elemento de la lista debe ser una dirección 
   * de correo válida.
   * @param to Lista de Strings, cada uno una dirección de correo de un 
   * destinatario
   */
  public DatosCorreo(List<String> to) {
    Debug.prec(to, "Debes especificar los destinatarios");
    this.to = to;
    this.adjuntos = new HashMap<String, String>();
  }

  /**
   * Crea un objeto DatosCorreo a partir de un String con la dirección (o 
   * direcciones) del destinatario (o destinatarios). Si se especifican varias 
   * direcciones, éstas deben aparecer separadas por ;
   * 
   * @param to String con la dirección o direcciones (separadas por ;) de los 
   * destinatarios del mensaje
   */
  public DatosCorreo(String to) {
    this(UtilesString.extraerSubstringSinRepes(to, ";"));
  }

  /**
   * Crea un objeto DatosCorreo. Ver la explicación de los parámetros
   * @param from String con la dirección del que envía el mensaje
   * @param to Lista de Strings, cada uno una dirección de correo de un 
   * destinatario
   * @param subject resumen del mensaje
   * @param body contenido del mensaje
   * @param fichero ruta del fcihero que contiene el contenido adjunto que se 
   * quiere enviar 
   */
  public DatosCorreo(String from, List to, String subject, String body, String fichero) throws AddressException {
    this(to);
    setFrom(from);
    setSubject(subject);
    setBody(body);
    if (fichero != null) {
      String fich = fichero;
      String nombreFichero = extraerNombreFichero(fichero);
      setAdjunto(nombreFichero, fich);
    }
  }

  /**
   * Crea un objeto DatosCorreo. Ver la explicación de los parámetros
   * @param from String con la dirección del que envía el mensaje
   * @param to String con la dirección o direcciones (separadas por ;) de los 
   * destinatarios del mensaje
   * @param subject resumen del mensaje
   * @param body contenido del mensaje
   * @param fichero ruta del fcihero que contiene el contenido adjunto que se 
   * quiere enviar 
   */
  public DatosCorreo(String from, String to, String subject, String body, String fichero) throws AddressException {
    this(from, UtilesString.extraerSubstringSinRepes(to, ";"), subject, body, fichero);
  }

  /**
   * Crea un objeto DatosCorreo. Ver la explicación de los parámetros
   * @param from String con la dirección del que envía el mensaje
   * @param to Lista de Strings, cada uno una dirección de correo de un 
   * destinatario
   * @param subject resumen del mensaje
   * @param body contenido del mensaje
   */
  public DatosCorreo(String from, List to, String subject, String body) throws AddressException {
    this(from, to, subject, body, null);
  }

  /**
   * Crea un objeto DatosCorreo. Ver la explicación de los parámetros
   * @param from String con la dirección del que envía el mensaje
   * @param to String con la dirección o direcciones (separadas por ;) de los 
   * destinatarios del mensaje
   * @param subject resumen del mensaje
   * @param body contenido del mensaje
   */
  public DatosCorreo(String from, String to, String subject, String body) throws AddressException {
    this(from, to, subject, body, null);
  }

  /**
   * Establece la dirección del remitente del mensaje
   * @param from String remitente del mensaje
   */
  public void setFrom(String from) throws AddressException {
    InternetAddress f = new InternetAddress(from);
    this.from = f;
  }

  /**
   * Establece la dirección del remitente del mensaje
   * @param from String remitente del mensaje
   */
  public void setFrom(InternetAddress from) {
    this.from = from;
  }

//  /**
//   * Devuelve la dirección de correo del remitente del mensaje
//   */
//  public String getFrom() {
//    return this.from;
//  }

  /**
   * Devuelve la dirección de correo del remitente del mensaje
   */
  public InternetAddress getFrom() {
    return this.from;
  }

  /**
   * Devuelve un array de objetos InternetAddress, cada uno de los cuales es la
   * dirección de un destinatario del mensaje
   * @throws AddressException 
   */
  public InternetAddress[] getTo() throws AddressException {
    return (toAdresses(this.to));
  }

  /**
   * Permite establecer el resumen o título del mensaje
   * @param subject 
   */
  public void setSubject(String subject) {
    this.subject = subject;
  }

  /**
   * Devuelve el resumen o título del mensaje
   */
  public String getSubject() {
    return this.subject;
  }

  /**
   * Devuelve un array de objetos InternetAddress, cada uno de los cuales es la
   * dirección de un destinatario oculto (BCC) del mensaje
   * @throws AddressException 
   */
  public InternetAddress[] getBcc() throws AddressException {
    if (this.bcc == null) {
      return null;
    }
    return (toAdresses(this.bcc));
  }

  /**
   * Añade un destinatatio oculto a los destinatarios ocultos (BCC) del mensaje
   * @param bcc String con una única dirección de correo
   */
  public void addBcc(String bcc) {
    if (this.bcc == null) {
      this.bcc = new ArrayList();
    }
    this.bcc.add(bcc);
  }

  /**
   * Devuelve un array de objetos InternetAddress, cada uno de los cuales es la
   * dirección de un destinatario al que se envía una copia (CC) del mensaje
   * @throws AddressException 
   */
  public InternetAddress[] getCc() throws AddressException {
    if (this.cc == null) {
      return null;
    }
    return (toAdresses(this.cc));
  }

  /**
   * Añade un destinatatio a la lista destinatarios a los que se envía una copia 
   * (CC) del mensaje
   * @param cc String con una única dirección de correo
   */
  public void addCc(String cc) {
    if (this.cc == null) {
      this.cc = new ArrayList();
    }
    this.cc.add(cc);
  }

  /**
   * Permite establecer el contenido del mensaje
   * @param body String conteniendo texto plano o HTML (en este caso es 
   * necesario especificar como tipo MIME del mensaje text/html
   */
  public void setBody(String body) {
    this.body = body;
  }

  /**
   * Devuelve el contenido del mensaje
   */
  public String getBody() {
    return this.body;
  }

  /**
   * Añade un adjunto al mensaje
   * @param nombre Nombre del adjunto (nombre del fichero con el que se enviará 
   * el adjunto). Si el mensaje ya incluye un adjunto con ese nombre, el fichero 
   * existente será sustituido por el nuevo.
   * @param fichero ruta del fichero cuyo contenido se enviará como adjunto
   */
  public void setAdjunto(String nombre, String fichero) {
    this.adjuntos.put(nombre, fichero);
  }

  /**
   * Añade un adjunto al mensaje. Se usará como nombre del adjunto el mismo 
   * nombre del fichero. Si ya existiese un adjunto con ese nombre, se 
   * modificará el nombre añadiéndole un sufijo numérico.
   * @param fichero String, ruta del fichero cuyo contenido se enviará como 
   * adjunto
   */
  public void setAdjunto(String fichero) {
    String nombre = extraerNombreFichero(fichero);
    if (this.adjuntos.containsKey(nombre)) {
      String extension = "";
      String soloNombre = "";
      int i = 1;
      int iExt = nombre.lastIndexOf(".");
      if (iExt != -1) {
        extension = nombre.substring(iExt + 1);
        soloNombre = nombre.substring(0, iExt);
      } else {
        soloNombre = nombre;
      }
      while (this.adjuntos.containsKey(nombre)) {
        nombre = soloNombre + "_" + (i++) + "." + extension;
      }
    }
    this.adjuntos.put(nombre, fichero);
  }

  /**
   * Devuelve la ruta de fichero asociada a un determinado nombre de fichero que 
   * se pasa como parámetro
   * @param nombre nombre del adjunto
   */
  public String getAdjunto(String nombre) {
    return (this.adjuntos.get(nombre)).toString();
  }

  /**
   * Devuelve true si el mensaje contiene adjuntos
   */
  public boolean hayAdjuntos() {
    return !this.adjuntos.isEmpty();
  }

  /**
   * Devuelve un Set con los nombres de los ficheros adjuntos añadidos al 
   * mensaje 
   */
  public Set<String> getNombresAdjuntos() {
    return this.adjuntos.keySet();
  }

  /**
   * Permite indicar el tipo de contenido del cuerpo del mensaje
   * @param mimeType tipo MIME correspondiente al cuerpo del mensaje
   */
  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  /**
   * Devuelve el tipo MIME del cuerpo del mensaje
   */
  public String getMimeType() {
    return mimeType;
  }

  public String toString() {
    return "Mail From: " + getFrom() + "\n"
            + "     To: " + this.to + "\n"
            + "     Subject: " + getSubject() + "\n"
            + "     Body: " + getBody()
            + (hayAdjuntos() ? "\n     Ficheros: " + this.adjuntos.keySet() : "");
  }

  private static InternetAddress[] toAdresses(List dirs) throws AddressException {
    List<InternetAddress> address = new ArrayList();
    for (int i = 0; i < dirs.size(); i++) {
      try {
        address.add(new InternetAddress((String) dirs.get(i)));
      } catch(AddressException e) {
        System.err.println(e.getClass()+":"+e.getMessage()+"(\""+dirs.get(i)+"\")");
      }
    }
    return address.toArray(new InternetAddress[0]);
  }

  /**
   * Devuelve el nombre de un fichero de un path determinado (pPathFichero)
   * @param     pPathFichero  path del fichero
   */
  private static String extraerNombreFichero(String pPathFichero) {
    pPathFichero = pPathFichero.replace('\\', '/');
    String nombre = null;
    if (pPathFichero != null && !pPathFichero.trim().equals("")) {
      int pos = pPathFichero.lastIndexOf("/");
      nombre = pPathFichero.substring(pos + 1, pPathFichero.length());
    }
    return nombre;
  }

//  public static void main(String[] args) {
//    String fichero = "c:\\tmp\\cosa.txt";
//    List ya = Arrays.asList("cosa.txt", "cosa_1.txt", "cosa_2.txt");
//    String nombre = extraerNombreFichero(fichero);
//    if (ya.contains(nombre)) {
//      String extension = "";
//      String soloNombre = "";
//      int i = 1;
//      int iExt = nombre.lastIndexOf(".");
//      if (iExt != -1) {
//        extension = nombre.substring(iExt + 1);
//        soloNombre = nombre.substring(0, iExt);
//      } else {
//        soloNombre = nombre;
//      }
//      while (ya.contains(nombre)) {
//        nombre = soloNombre + "_" + (i++) + "." + extension;
//      }
//    }
//    System.out.println(nombre);
//  }
}
