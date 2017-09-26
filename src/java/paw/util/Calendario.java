package paw.util;

import java.util.Calendar;

import paw.model.ExcepcionDeAplicacion;
import paw.bd.GestorBD;

/**
 * Clase que porporciona funcionalidad para el control de fechas en la aplicación
 * de la empresa ELECTROSA. Incluye la gestión de los días festivos
 * <p>Copyright: Copyright (c) 2003 Dpto. Matemáticas y Computación.</p>
 * <p>Company: Universidad de La Rioja.</p>
 * @author Francisco José García Izquierdo
 * @version 2.0
 */
public class Calendario {
  private static GestorBD gbd = new GestorBD();

  /**
   * Indica si un día es festivo en la empresa ELECTROSA. Se consideran festivos
   * los sábados, los domingos y los dñias especialmente distinguidos y
   * almacenados a tal fín en una tabla de festividades de la BD de ELECTROSA.
   * @param fecha día a comprobar
   * @return true si el día es festivo
   * @throws SQLException si hay algún problema al consultar la tabla de
   *                      festividades
   */
  public static boolean isFestivo(Calendar fecha) throws ExcepcionDeAplicacion {
    return isSabado(fecha) || isDomingo(fecha) || gbd.isFestivo(fecha);
  }

  /**
   * Indica si una fecha corresponde a un sábado
   * @param fecha día a comprobar
   * @return true si fecha es sábado
   */
  public static boolean isSabado(Calendar fecha) {
    return fecha.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY;
  }

  /**
   * Indica si una fecha corresponde a un domingo
   * @param fecha día a comprobar
   * @return true si fecha es domingo
   */
  public static boolean isDomingo(Calendar fecha) {
    return fecha.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
  }

  /**
   * Devuelve el siguiente día laborable según el calendario de festividades de
   * la empresa ELECTROSA.
   * @param fecha día del cual hay que calcular el siguiente laborable
   * @return siguiente día laborable a fecha
   * @throws SQLException si hay algún problema al consultar la tabla de
   *                      festividades
   */
  public static Calendar siguienteLaborable(Calendar fecha) throws ExcepcionDeAplicacion {
    Calendar aux = (Calendar) fecha.clone();
    if (isSabado(fecha)) {
      aux.add(Calendar.DATE, 2);
    } else if (fecha.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
      aux.add(Calendar.DATE, 3);
    } else {
      aux.add(Calendar.DATE, 1);

    }
    while (isFestivo(aux)) {
      aux = siguienteLaborable(aux);

    }
    return aux;
  }

  /**
   * Dada una fecha pone sus campos de hora, minuto, segundo y milisegundo a 0.
   * De esta manera es posible establecer comparaciones de fechas sin tener en
   * cuenta la hora.
   */
  public static void normaliza(Calendar fecha) {
    fecha.set(Calendar.HOUR_OF_DAY, 0);
    fecha.set(Calendar.MINUTE, 0);
    fecha.set(Calendar.SECOND, 0);
    fecha.set(Calendar.MILLISECOND, 0);
  }

  /**
   * Devuelve true si las dos fechas entregadas como parámetros se refieren al
   * mismo día, independientemente de la hora del mismo. Si alguna de las dos
   * fechas es null, devuelve falso. Si las dos fechas son null, devuelve true.
   *
   * @param c1 fecha 1
   * @param c2 fecha 2
   */
  public static boolean mismoDia(Calendar c1, Calendar c2) {
    if (c1 == null && c2 == null) {
      return true;
    }

    if (c1 == null || c2 == null) {
      return false;
    }
    boolean igual = c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) &&
        c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) &&
        c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH);
    return igual;
  }
}
