package paw.util;

/**
 * Generador de números enteros aleatorios.
 *
 * <p>Copyright: Copyright (c) 2003 Dpto. Matemáticas y Computación.</p>
 * <p>Company: Universidad de La Rioja.</p>
 * @author Francisco José García Izquierdo
 * @version 2.0
 */
public class Ramdom {

  static private java.util.Random rand = new java.util.Random();

  /**
   * Genera un número aleatorio entre 0 y tope-1
   * @param tope valor máximo de número aleatorio generado
   * @return valor aleatorio entre 0 y tope-1
   */
  public static int random(int tope) {
    return rand.nextInt(tope);
  }
}