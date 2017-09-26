package paw.util.mail.conf;

public class URMailConfiguration extends ConfiguracionCorreo {

  public String getHost() {
    return "smtp.unirioja.es";
  }

  public String getPort() {
    return "587";
  }

  public String getEnableStartTLS() {
    return "true";
  }

  public String getUser() {
    throw new java.lang.IllegalArgumentException("Debes especificar un valor de usuario que devolver");
//    return "... pon tu usuario aqui ...";
  }

  public String getPassword() {
    throw new java.lang.IllegalArgumentException("Debes especificar un valor de contraseña que devolver");
//    return "... pon tu contraseña aqui ...";
  }

  public String getAuth() {
    return "true";// Si en la UR se pone a true da error
  }
}
