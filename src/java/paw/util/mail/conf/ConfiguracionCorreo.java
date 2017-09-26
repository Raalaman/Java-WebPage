package paw.util.mail.conf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import paw.debug.Debug;

/**
 * Clase abstracta para contener la configuración de correo (servidor smtp, 
 * usuario...).
 * <p> Se puede trabajar con ella de dos modos:
 * <ol>
 * <li>Por herencia, creando una  clase que en cada método devuelva el valor 
 * adecuado de configuración</li>
 * <li>Usando la configuración por defecto, basada en un fichero de propiedades
 * llamado mail.properties que debe estar disponible en la raiz de las clases de
 * la aplicación. Debe contener las propiedades:
 * <ul>
 * <li>mail.smtp.host, servidor smtp</li>
 * <li>mail.smtp.port, puerto del servidor smtp</li>
 * <li>mail.smtp.auth, si hay que autenticar el envío con usuario y contraseña</li>
 * <li>mail.smtp.user, nombre del usuario de correo a emplear</li>
 * <li>mail.smtp.password, contraseña del usuario de correo a emplear</li>
 * <li>mail.smtp.starttls.enable, si hay que usar TLS</li>
 * </ul>
 * </li>
 * </ol>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: Universidad de La Rioja. Departamento de Matemáticas y Computación</p>
 * @author Francisco J. García Izquierdo
 */
public abstract class ConfiguracionCorreo {

  /**
   * Servidor SMTP
   */
  abstract public String getHost();

  /**
   * Puerto del servidor
   */
  abstract public String getPort();

  /**
   * Usar TLS si está disponible. Devolver "true" o  "false"
   */
  abstract public String getEnableStartTLS();

  /**
   * Nombre de usuario en el servidor
   */
  abstract public String getUser();

  /**
   * Contraseña del usuario
   */
  abstract public String getPassword();

  /**
   * Si requiere o no usuario y password para conectarse. Devolver "true" o 
   * "false"
   */
  abstract public String getAuth();

  public Properties getProperties() {
    Properties props = new Properties();
    if (getHost() != null) {
      props.setProperty("mail.smtp.host", getHost());
    }
    if (getEnableStartTLS() != null) {
      props.setProperty("mail.smtp.starttls.enable", getEnableStartTLS());
    }
    if (getPort() != null) {
      props.setProperty("mail.smtp.port", getPort());
    }
    if (getUser() != null) {
      props.setProperty("mail.smtp.user", getUser());
    }
    if (getPassword() != null) {
      props.setProperty("mail.smtp.password", getPassword());
    }
    if (getAuth() != null) {
      props.setProperty("mail.smtp.auth", getAuth());
    }

    return props;
  }

  /**
   * Devuelve un objeto ConfiguracionCorreo basado en un fichero de propiedades
   * que debe llamarse mail.properties y estar en la raiz de las clases de la
   * aplicación.
   * 
   */
  public static ConfiguracionCorreo getDefault() {
    ConfiguracionCorreo cc = null;
    try {
      final Properties config = new Properties();
      InputStream prIS = ConfiguracionCorreo.class.getResourceAsStream("/mail.properties");
      if (prIS != null) {
        config.load(prIS);

        cc = new ConfiguracionCorreo() {

          @Override
          public String getHost() {
            return config.getProperty("mail.smtp.host");
          }

          @Override
          public String getPort() {
            return config.getProperty("mail.smtp.port");
          }

          @Override
          public String getEnableStartTLS() {
            return config.getProperty("mail.smtp.starttls.enable");
          }

          @Override
          public String getUser() {
            return config.getProperty("mail.smtp.user");
          }

          @Override
          public String getPassword() {
            return config.getProperty("mail.smtp.password");
          }

          @Override
          public String getAuth() {
            return config.getProperty("mail.smtp.auth");
          }
        };
      } else {
        Debug.log(ConfiguracionCorreo.class.getName(), Level.SEVERE, "No pudo cargarse el fichero de propiedades mail.properties (situalo en la raiz)");
      }
    } catch (IOException ex) {
      Debug.log(ConfiguracionCorreo.class.getName(), Level.SEVERE, "No pudo cargarse el fichero de propiedades mail.properties (situalo en la raiz)", ex);
    }
    return cc;
  }
}
