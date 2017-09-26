package paw.util;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * <p>
 * Use objects of this class to validate ReCaptcha v2. Dependency: json-java
 * (http://www.json.org/java/). Download jar at http://www.java2s.com/Code/Jar/j/Downloadjavajsonjar.htm
 * <p/>
 * <p>
 * Copyright: Copyright (c) 2015</p>
 * <p>
 * Company: Universidad de La Rioja. Departamento de Matemáticas y
 * Computación</p>
 *
 * @author Francisco J. García Izquierdo
 * @version 1.0
 */
public class ReCaptchaValidator {

  public static final String SCRIPT_URL = "//www.google.com/recaptcha/api.js";
  public static final String VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
  protected String siteKey;
  protected String secret;

  /**
   * Creates a captcha validator using the siteKey y the secret provided by the 
   * reCaptcha registration process. See https://www.google.com/recaptcha/intro/index.html
   * @param siteKey
   * @param secret 
   */
  public ReCaptchaValidator(String siteKey, String secret) {
    if (siteKey == null || siteKey.isEmpty()) {
      throw new IllegalArgumentException("Invalid siteKey");
    }
    if (secret == null || secret.isEmpty()) {
      throw new IllegalArgumentException("Invalid secret");
    }
    this.siteKey = siteKey;
    this.secret = secret;
  }

  /**
   * Verify the validity of the fulfilled captcha. If the captcha is valid, the 
   * method returns true. If the captcha does not validate, then an exception 
   * ReCaptchaException is thrown with information about the validation error.
   * @param request HttpServletRequest corresponding to the form submition where 
   * the captcha is included
   * @return true if captcha validates
   * @throws IOException connection to ReCaptcha server problems
   * @throws ReCaptchaException if the captcha does not validate
   */
  public boolean verifyResponse(HttpServletRequest request) throws IOException, ReCaptchaException {
    String reCaptchaParam = request.getParameter("g-recaptcha-response");
    String remoteAddr = request.getRemoteAddr();
    Logger.getLogger(ReCaptchaValidator.class.getName()).log(Level.INFO, "g-recaptcha-response=[" + reCaptchaParam + "]");
    if (reCaptchaParam == null || reCaptchaParam.trim().length() == 0) {
      throw new ReCaptchaException("missing-input-response");
    }
    String qS = "secret=" + secret + "&response=" + reCaptchaParam + "&remoteip=" + remoteAddr;
    URL url = new URL(VERIFY_URL+"?"+qS);
    // No funciona con POST, aunqeu diga en la documentación que debe ser un mensaje POST
//    URL url = new URL(VERIFY_URL);
//    URLConnection con = url.openConnection();
//    con.setDoOutput(true);
//    PrintWriter ps = new PrintWriter(new OutputStreamWriter(con.getOutputStream()));
//    ps.println(qS);
//    ps.flush();

    String line, response = "";

    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
    while ((line = reader.readLine()) != null) {
      response += line;
    }
    Logger.getLogger(ReCaptchaValidator.class.getName()).log(Level.INFO, "server response=[" + response + "]");

    // No deserializa bien el array de strings de los errores
//    ReCaptchaResponse status = new Gson().fromJson(response, ReCaptchaResponse.class);
    boolean success = false;
    String errorCode = null;
    try {
      JSONObject json = new JSONObject(response);
      success = json.getBoolean("success");
      JSONArray errorCodes = json.optJSONArray("error-codes");
      errorCode = (errorCodes != null && errorCodes.length() > 0) ? errorCodes.join(",") : null;
    } catch (JSONException ex) {
      success = false;
      errorCode = "Error parsing JSON response";
    }
    if (!success) {
      throw new ReCaptchaException(errorCode);
    }
    return success;
  }

  
//  public static class ReCaptchaResponse {
//
//    private boolean success;
//    private String errorCode;
//
//    protected ReCaptchaResponse(boolean success, String errorCode) {
//      this.success = success;
//      this.errorCode = errorCode;
//    }
//
//    /**
//     * The reCaptcha error message.
//     *
//     * not-reachable missing-input-secret invalid-input-secret
//     * missing-input-response invalid-input-response
//     *
//     * @return
//     */
//    public String getErrorCode() {
//      return errorCode;
//    }
//
//    /**
//     * True if captcha is "success".
//     *
//     * @return
//     */
//    public boolean isSuccess() {
//      return success;
//    }
//  }
//  
//      private static String join(List<String> array, String separator) {
//        if (array == null) {
//            return null;
//        }
//        final StringBuilder buf = new StringBuilder();
//        for (int i = 0; i < array.size(); i++) {
//            if (i > 0) {
//                buf.append(separator);
//            }
//            if (array.get(i) != null) {
//                buf.append(array.get(i));
//            }
//        }
//        return buf.toString();
//    }
}
