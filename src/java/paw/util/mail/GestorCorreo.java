package paw.util.mail;


import paw.debug.Debug;
import paw.util.mail.conf.ConfiguracionCorreo;

import java.util.Date;
import java.util.Set;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Clase para envío de correos electrónicos.
 * Para su funcionamiento necesita varios jar:
 * <ul>
 * <li> activation.jar (JavaBeans Activation Framework) 
 *  http://www.oracle.com/technetwork/java/jaf11-139815.html. Este jar puede 
 *  omitirse si se trabaja con una versión de Java igual o superior a la 1.6. 
 * </li>
 * <li> mailapi.jar (Java Mail API) 
 * http://www.oracle.com/technetwork/java/index-138643.html 
 * </li>
 * <li>smtp.jar (Java Mail API)  
 * http://www.oracle.com/technetwork/java/index-138643.html 
 * </li>
 * <li>Los dos anteriores pueden sustituirse por mail.jar (Java Mail API)  
 * http://www.oracle.com/technetwork/java/index-138643.html, que los contiene a 
 * ambos
 * </li>
 * </ul>
 * La utilización de esta clase es muy sencilla. Un ejemplo puede aclararlo (en 
 * el se usa la configuración por defecto, que se basa en un fichero de 
 * propiedades que debe llamarse <code>mail.properties</code>). Se trata de enviar un mensaje
 * a <code>para@dominio2.es</code>, enviado por <code>de@dominio.es</code>
 * <pre>  DatosCorreo mail = new DatosCorreo("de@dominio.es", "para@dominio2.es", "Tema", "Contenido del mensaje...");
  GestorCorreo.envia(mail, ConfiguracionCorreo.getDefault());
 * </pre>
 * La clase, mediante los objetos <code>DatosCorreo</code> es capaz de enviar 
 * correos a varios destinatarios, con campos CC y CCO, con adjuntos y en formato 
 * texto o HTML.
 * <pre>  DatosCorreo mail = new DatosCorreo("para@dominio2.es");
  mail.setMimeType("text/html");
  mail.setSubject("Prueba de mensaje");
  mail.setBody("&lt;html&gt;&lt;body&gt;Espero que funcione el &lt;b&gt;mensajillo&gt;/b&gt;&lt;/body&lt;&gt;/html&gt;");
  GestorCorreo.envia(mail, ConfiguracionCorreo.getDefault());
 * </pre>
 * Si el texto del mensaje o su cuerpo contienen caracteres que no son US-ASCII
 * (como los caracteres acentuados en español o las ñ) es necesario indicarlo
 * mediante un charset adecuado, por ejemplo UTF-8. La clase 
 * <code>DatosCorreo</code> dispone de un método 
 * {@link DatosCorreo#setCharset(java.lang.String)} para especificar este 
 * charset.
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: Universidad de La Rioja. Departamento de Matemáticas y Computación</p>
 * @author Francisco J. García Izquierdo
 */
public class GestorCorreo {

  /**
   * Método de envío de correos electrónicos. Ver la descripción de los 
   * parámetros.
   * @param datosMail objeto que contiene las características de un email (to, 
   *                  cc, bcc, from, subject, contenido, tipo MIME del mensaje, 
   *                  adjuntos...)
   * @param config objeto que contiene la configuración de correo
   * @throws MessagingException 
   */
  public static void envia(DatosCorreo datosMail, ConfiguracionCorreo config) throws MessagingException {
    Debug.prec(datosMail, "Me estás mandando unos datos de correo nula");
    Debug.prec(config, "Me estás mandando una configuración de correo nula");
    boolean debug = true;
    
    // La configuración del envío de correos se hace con unas cuantas 
    // propiedades que se reciben en un objeto ConfiguracionCorreo

    // Obtengo una sesion para el envío de correos
    Session session = Session.getDefaultInstance(config.getProperties(), null);
    
    // Establezco el nivel de Debug (para desactivarlo poner debug = false)
    session.setDebug(debug);

    // Crea un message a partir de los datos del email. Message es la clase de
    // Java Mail que hay que usar para mandar un mensaje
    Message msg = creaMensaje(session, datosMail);

    // Tansport.send(msg); no funciona xq no se puede especificar los datos de
    // conexión
    Transport t = null;
    try {
      // Obtengo el transporte smtp para conectar con el servidor de correo 
      // saliente
      t = session.getTransport("smtp");
      // Conecto con el servidor de correo saliente
      t.connect(config.getHost(), config.getUser(), config.getPassword());
      // Envío el mensaje
      t.sendMessage(msg, msg.getAllRecipients());
    } catch (MessagingException mex) {
      Debug.warning("Error en el envio del mensaje " + datosMail);
      Debug.warning("GestorCorreo.send()" + mex);
      Exception ex = mex;
      do {
        if (ex instanceof SendFailedException) {
          SendFailedException sfex = (SendFailedException) ex;
          Address[] invalid = sfex.getInvalidAddresses();
          if (invalid != null) {
            Debug.warning("GestorCorreo.send() Invalid Addresses");
            if (invalid != null) {
              for (int i = 0; i < invalid.length; i++) {
                Debug.warning("GestorCorreo.send()" + invalid[i]);
              }
            }
          }
          Address[] validUnsent = sfex.getValidUnsentAddresses();
          if (validUnsent != null) {
            Debug.warning("MailManager.send() ValidUnsent Addresses");
            if (validUnsent != null) {
              for (int i = 0; i < validUnsent.length; i++) {
                Debug.warning("MailManager.send() " + validUnsent[i]);
              }
            }
          }
          Address[] validSent = sfex.getValidSentAddresses();
          if (validSent != null) {
            Debug.warning("MailManager.send() ValidSent Addresses");
            if (validSent != null) {
              for (int i = 0; i < validSent.length; i++) {
                Debug.warning("MailManager.send() " + validSent[i]);
              }
            }
          }
        }
      } while (ex instanceof MessagingException && (ex = ((MessagingException) ex).getNextException()) != null);
      throw mex;
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      if (t != null) t.close();
    }
  }

  private static Message creaMensaje(Session session, DatosCorreo datosMail)
          throws AddressException, MessagingException {
    MimeMessage msg;
    msg = new MimeMessage(session);

    if (datosMail.getFrom() != null) {
      msg.setFrom(datosMail.getFrom());
    }

    msg.setRecipients(Message.RecipientType.TO, datosMail.getTo());
    msg.setRecipients(Message.RecipientType.CC, datosMail.getCc());
    msg.setRecipients(Message.RecipientType.BCC, datosMail.getBcc());
    

    if (datosMail.getSubject() != null) {
      if (datosMail.getCharset() != null) 
        msg.setSubject(datosMail.getSubject(), datosMail.getCharset());
      else
        msg.setSubject(datosMail.getSubject());
    }

    msg.setSentDate(new Date());

    // Crear el contenedor de las attach del mensaje
    Multipart mp = new MimeMultipart();

    if (datosMail.getBody() != null) {
      MimeBodyPart mbp1 = new MimeBodyPart();
      if (datosMail.getMimeType() != null) {
        mbp1.setContent(datosMail.getBody(), datosMail.getMimeType());
      } else {
        if (datosMail.getCharset() != null) 
          mbp1.setText(datosMail.getBody(), datosMail.getCharset());
        else
          mbp1.setText(datosMail.getBody());
      }
      mp.addBodyPart(mbp1);
    }
    if (datosMail.hayAdjuntos()) {
      Set<String> adjuntos = datosMail.getNombresAdjuntos();
      for (String nombreFichero : adjuntos) {
        MimeBodyPart mbp2 = new MimeBodyPart();
        String adjunto = datosMail.getAdjunto(nombreFichero);
        if (adjunto != null) {
          mbp2.setDataHandler(new DataHandler(new FileDataSource(adjunto)));
          mbp2.setFileName(nombreFichero);
          mp.addBodyPart(mbp2);
        }
      }
    }
    msg.setContent(mp);
    return msg;
  }
}
