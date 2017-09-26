package paw.util;

import java.util.Hashtable;
import java.util.Vector;

/**
 * Tabla hash que contiene vectores de elementos
 */
class Table {
  private Hashtable table;
  private final int cols;
  private final int rows;

  /**
   * Construye una Table con rows filas iniciales y con cols columnas iniciales
   * @param rows
   * @param cols
   */
  public Table(int rows, int cols) {
    this.rows = rows;
    this.cols = cols;
    table = new Hashtable(rows);
  }

  /**
   * Contruye una Table de 10 x 5
   */
  public Table() {
    this(10, 5);
  }

  /**
   * Añade un objeto en la fila de clave key
   * @param key clave para localizar la fila donde añadir el objeto
   * @param element objeto a añadir
   */
  public void add(Object key, Object element) {
    Vector row = this.getRow(key);
    row.add(element);
    this.table.put(key, row);
  }

  /**
   * Devuelve la fila de clave key
   * @param key
   * @return
   */
  public Vector get(Object key) {
    return (Vector) table.get(key);
  }

  /**
   * Elimina un elemento determinado de la fila de clave key
   * @param key clave para localizar la fila donde eliminar el objeto
   * @param element objeto a añadir
   * @return
   */
  public synchronized boolean remove(Object key, Object element) {
    Vector row = this.get(key);
    if (row == null)
      return false;

    boolean removed = row.remove(element);

    if (row.size()==0)
      table.remove(key);

    return removed;
  }

  /**
   * Si no existe la crea vacía
   */
  private Vector getRow(Object key) {
    Object row = table.get(key);
    if (row==null)
      return new Vector(this.cols);
    else
      return (Vector) row;
  }

  /**
   * Develve true si la Table contiene una fila de clave key
   * @param key
   * @return
   */
  public boolean containsKey(Object key) {
    return table.containsKey(key);
  }
}