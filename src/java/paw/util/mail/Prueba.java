/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paw.util.mail;

import javax.mail.internet.InternetAddress;
import paw.util.mail.conf.ConfiguracionCorreo;
import paw.util.mail.conf.URMailConfiguration;

/**
 *
 * @author fgarcia
 */
public class Prueba {

  
  public static void main(String[] args) throws Exception {
//    String TO = "francisco.garcia@unirioja.es; frangaiz@gmail.com; forises@hotmail.com";
    String TO = "frangaiz@gmail.com";
    
    DatosCorreo mail = new DatosCorreo(TO);
    mail.setMimeType("text/html");
    mail.setSubject("Prueba de mensaje");
    mail.setBody("<html><body>Espero que funcione el <b>mensajillo</b></body></html>");
    mail.addCc("francisco.garcia@unirioja.es");
//    mail.setAdjunto("adj1.doc", "c:\\tmp\\novela.doc");
//    mail.setAdjunto("novela.doc","c:\\tmp\\novela.doc");
//    mail.setAdjunto("c:\\tmp\\novela.doc");
//    mail.setAdjunto("c:/tmp\\tmp/novela.doc");
    
    String FROM = "francisco.garcia@unirioja.es";
    mail.setFrom(new InternetAddress(FROM, "Francisco J. García Izquierdo"));
    GestorCorreo.envia(mail, ConfiguracionCorreo.getDefault());
    
//    // Para enviar con la UR es necesario poner el campo from
//    String FROM = "francisco.garcia@unirioja.es";
//    mail.setFrom(FROM);
//    GestorCorreo.envia(mail, new URMailConfiguration());
  }  
}
