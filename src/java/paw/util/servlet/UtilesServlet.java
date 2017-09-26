package paw.util.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;

/**
 * Varias utilidades para utilizarlas en los servlets
 * <p>Copyright: Copyright (c) 2012-2017</p>
 * <p>Company: Universidad de La Rioja. Departamento de Matemáticas y Computación</p>
 * @author Francisco J. Garcia Izquierdo
 * @version 3.0
 */
public class UtilesServlet {

  /**
   * <p>DEPENDENCIAS: para usar esta clase se debe disponer de alguna versión 
   * de:</p>
   * <ul>
   * <li>Apache Commons BeanUtils 
   * (<a href="http://commons.apache.org/beanutils/">http://commons.apache.org/beanutils/</a>).
   * En concreto commons-beanutils.jar.</li>
   * <li>Apache Commons Logging 
   * (<a href="http://commons.apache.org/logging/">http://commons.apache.org/logging/</a>).
   * En concreto commons-logging.jar.</li>
   * </ul>
   * 
   * <p>Este m&eacute;todo trata de construir un objeto-bean directamente 
   * tomando los valores de los parámetros de un petición (POST o GET, da 
   * igual). 
   * 
   * <p>El m&eacute;todo toma los par&aacute;metros de la petici&oacute;n y usa 
   * los valores de todos aquellos que tengan como nombre el nombre de alguna 
   * propiedad del bean para inicializar el bean. Es decir, el método espera que
   * los nombres de los parámetros de la petición coincidan con los nombres de 
   * las propiedades del bean, aunque de no coincidir también se puede usar con 
   * un poco más de configuración (argumento opcional <code>mappings</code>).
   * 
   * <p>El m&eacute;todo hace las conversiones de tipos necesarias: <ul>
   * <li>Si un par&aacute;metro no se puede convertir, se ignora.</li> 
   * <li>Si algún parámetro es la cadena vacia "", o cualquier cadena 
   * compuesta solo por espacios en blanco, la propiedad tomará valor 
   * nulo.</li> 
   * <li>Tambi&eacute;n se puede utilizar  para inicializar propiedades de tipo 
   * array, para lo que es necesario un  par&aacute;metro multivaluado.</li></ul></p>
   * 
   * <p><i>Ejemplo 1. Todos los nombres de parámetros coinciden con los nombres de 
   * las propiedades del bean a construir</i>
   * <p>Por ejemplo la clase:<br />
   * <pre><b>package</b>  pru;
<b>public</b>  <b>class</b> MiBean {
  <b>private</b> String a;
  <b>private</b> <b>int</b> b;<br />&nbsp; <b>private</b> <b>boolean</b> c;<br />&nbsp; <b>private</b> String[] d;<br />
   *&nbsp; <b>public</b> String getA() { <b>return</b> a; }<br />&nbsp; <b>public</b> <b>int</b> getB() { <b>return</b> b; }<br />&nbsp; <b>public</b> <b>boolean</b> isC() { <b>return</b> c; }</p><p>&nbsp; <b>public</b> <b>void</b> setA(String a) { <b>this</b>.a = a; }<br />&nbsp; <b>public</b> <b>void</b> setB(<b>int</b> b) { <b>this</b>.b = b; }<br />&nbsp; <b>public</b> <b>void</b> setC(<b>boolean</b> c) { <b>this</b>.c = c; }</p><p>&nbsp; <b>public</b> String[] getD() { <b>return</b> d; }<br />&nbsp; <b>public</b> <b>void</b> setD(String[] d) { <b>this</b>.d = d; }<br />&nbsp; <b>public</b> String getD(<b>int</b> index) { <b>return</b>  <b>this</b>.d[index]; }<br />&nbsp; <b>public</b> <b>void</b> setD(<b>int</b> index, String newD) {  <b>this</b>.d[index] = newD; }<br />}</pre>
   * 
   * <p>Supongamos que tenemos un formulario con los  siguientes campos:
   * <pre>  &lt;input type=&quot;text&quot;  name=&quot;a&quot; value=&quot;&quot; /&gt;
  &lt;input type=&quot;text&quot; name=&quot;b&quot; value=&quot;&quot; /&gt;
  &lt;input type=&quot;radio&quot; name=&quot;c&quot; value=&quot;1&quot; /&gt; Si 
  &lt;input type=&quot;radio&quot; name=&quot;c&quot; value=&quot;0&quot; /&gt; No
  &lt;input type=&quot;checkbox&quot; name=&quot;d&quot; value=&quot;UNO&quot; /&gt; UNO 
  &lt;input type=&quot;checkbox&quot; name=&quot;d&quot; value=&quot;DOS&quot; /&gt; DOS </pre>
   * 
   * <p>El anterior formulario puede usarse para enviar datos al servidor y 
   * usarlos para crear objetos de la clase <code>pru.MiBean</code>.
   * 
   * <p>Como todos los nombres de los parámetros coinciden con los nombres de 
   * las propiedades del bean, para crear un objeto de tipo 
   * <code>pru.MiBean</code> basta con hacer una llamada como la siguiente desde 
   * un m&eacute;todo de un servlet (por ejemplo doGet)</p>
   * 
   * <p>  <code>pru.MiBean bean  = (pru.MiBean) UtilesServlet.populateBean(&quot;pru.MiBean&quot;, request);</code>
   * 
   * <p><b>Cuando no coinciden todos los nombres de los parámetros y las 
   * propiedades del bean</b>
   * 
   * <p>Tambi&eacute;n es possible mapear los par&aacute;metros de la 
   * petici&oacute;n con las propiedades del bean aunque no se llamen igual. 
   * Para ello, en la llamada del m&eacute;todo se debe especificar un par de 
   * Strings (array de dos  Strings) por cada parámetro-propiedad que no 
   * coincida. Cada par de strings especifica primero el nombre del 
   * par&aacute;metro y luego el nombre de la  propiedad que ha de 
   * inicializarse con el valor del par&aacute;metro.
   * 
   * <p><i>Ejemplo 2. NO todos los nombres de parámetros coinciden con los 
   * nombres de las propiedades del bean a construir</i>
   * 
   * <p>Por ejemplo si el formulario del ejemplo anterior fuese:</p>
   * 
   * <pre>  &lt;input type=&quot;text&quot;  name=&quot;aa&quot; value=&quot;&quot; /&gt;
  &lt;input type=&quot;text&quot; name=&quot;bb&quot; value=&quot;&quot; /&gt;
  &lt;input type=&quot;radio&quot; name=&quot;c&quot; value=&quot;1&quot; /&gt; Si 
  &lt;input type=&quot;radio&quot; name=&quot;c&quot; value=&quot;0&quot; /&gt; No
  &lt;input type=&quot;checkbox&quot; name=&quot;d&quot; value=&quot;UNO&quot; /&gt; UNO 
  &lt;input type=&quot;checkbox&quot; name=&quot;d&quot; value=&quot;DOS&quot; /&gt; DOS </pre>
  * 
  * <p> Notar que hay dos parámetros (<code>aa</code> y <code>bb</code>) que no 
  * coinciden con propiedades del bean (se quieren emplear esos parámetros para
  * inicializar las propiedades <code>a</code> y <code>b</code>).
  * 
   * <p>Por ello, para inicializar el bean usaremos un par de parametros extra 
   * como <code>mappings</code>:<p/>
   * 
  <pre>  String []  pa ={&quot;aa&quot;,&quot;a&quot;};
  String []  pb ={&quot;bb&quot;,&quot;b&quot;};
  MiBean pru.bean  = (pru.MiBean) UtilesServlet.populateBean(&quot;pru.MiBean&quot;, request, pa, pb);</pre>
  *  
   * @param className (<code>String</code>) nombre de la clase del Bean a poblar
   * @param request objeto <code>HttpServletRequest</code> del que se recogen los
   *        parámetros de la petición
   * @param mappings Argumento de longitud variable (puede haber 0 o N). Cada 
   *        uno es un array de 2 strings. Cada uno especifica una 
   *        correspondencia entre un nombre de parámetro y la propiedad del bean 
   *        que inicializará (la componente 0 del array es el nombre del 
   *        parámetro a utilizar; la componente 1 del array es el nombre de la 
   *        propiedad que ha de inicializarse con el valor del parámetro).
   *        
   * @return un bean inicializado con los valores de los parametros de request
   * @throws ServletException encapsulando cualquier problema que se haya tenido
   *         creando o inicializando las propiedades del Bean
   */
  public static Object populateBean(String className, HttpServletRequest request,
          String[]... mappings) throws ServletException {
    try {
      return populateBean(Class.forName(className), request, mappings);
    } catch (ClassNotFoundException ex) {
      throw new ServletException(ex);
    }
//    try {
//      Object bean = Class.forName(className).newInstance();
//      // Si uso request.getParameterMap() luego no puedo añadir cosas a ese Map, 
//      // porque lo devuelve inmutable
////      Map params = request.getParameterMap();
//      Map params = new HashMap(10);
//      Enumeration<String> pEnum = request.getParameterNames();
//      while (pEnum.hasMoreElements()) {
//        String pName = pEnum.nextElement();
//        String[] pValue = request.getParameterValues(pName);
//        List<String> pValueL = new ArrayList<String>(pValue.length);
//        for (String pv : pValue) {
//           if (pv.trim().length()!=0) 
//             pValueL.add(pv);
//        }        
//        if (pValueL.size()>0)
//          params.put(pName, pValueL.toArray(new String[pValueL.size()]));
//      }
//      if (mappings != null) {
//        for (String[] m : mappings) {
//          String[] pValue = request.getParameterValues(m[0]);
//          params.put(m[1], pValue);
//        }
//      }
//      BeanUtils.populate(bean, params);
//      return bean;
//    } catch (InstantiationException ex) {
//      throw new ServletException(ex);
//    } catch (ClassNotFoundException ex) {
//      throw new ServletException(ex);
//    } catch (IllegalAccessException ex) {
//      throw new ServletException(ex);
//    } catch (InvocationTargetException ex) {
//      throw new ServletException(ex);
//    }
  }
  
  
  public static Object populateBean(Class theClass, HttpServletRequest request,
          String[]... mappings) throws ServletException {    
    try {
      Object bean = theClass.newInstance();
      // Si uso request.getParameterMap() luego no puedo añadir cosas a ese Map, 
      // porque lo devuelve inmutable
//      Map params = request.getParameterMap();
      Map params = new HashMap(10);
      Enumeration<String> pEnum = request.getParameterNames();
      while (pEnum.hasMoreElements()) {
        String pName = pEnum.nextElement();
        String[] pValue = request.getParameterValues(pName);
        List<String> pValueL = new ArrayList<String>(pValue.length);
        for (String pv : pValue) {
           if (pv.trim().length()!=0) 
             pValueL.add(pv);
        }        
        if (pValueL.size()>0)
          params.put(pName, pValueL.toArray(new String[pValueL.size()]));
      }
      if (mappings != null) {
        for (String[] m : mappings) {
          String[] pValue = request.getParameterValues(m[0]);
          params.put(m[1], pValue);
        }
      }
      BeanUtils.populate(bean, params);
      return bean;
    } catch (InstantiationException ex) {
      throw new ServletException(ex);
    } catch (IllegalAccessException ex) {
      throw new ServletException(ex);
    } catch (InvocationTargetException ex) {
      throw new ServletException(ex);
    }
  }

  /**
   * Método de utilidad para realizar forward desde un servlet con una única 
   * llamada
   * @param request
   * @param response
   * @param destino
   * @throws ServletException
   * @throws IOException 
   */
  public static void doForward(HttpServletRequest request, 
                               HttpServletResponse response, 
                               String destino) 
          throws ServletException, IOException {
    RequestDispatcher rd = request.getRequestDispatcher(destino);
    rd.forward(request, response);
  }
}
