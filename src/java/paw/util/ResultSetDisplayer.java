package paw.util;

import paw.debug.Debug;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

/**
 * Imprime en un stream de salida el contenido de un ResultSet en forma de tabla.
 * <b>IMPORTANTE</b>: el proceso de impresión vacía el ResultSet.
 *
 * <p>Copyright: Copyright (c) 2003 Dpto. Matemáticas y Computación.</p>
 * <p>Company: Universidad de La Rioja.</p>
 * @author Francisco José García Izquierdo
 * @version 2.0
 */
public class ResultSetDisplayer {
  private PrintWriter out;
  private ResultSet rs;

  /**
   * Constructor de ResultSetDisplayer para imprimir un result set en un canal
   * de salida determinado.
   * @param out canal de salida
   * @param rs result set a imprimir
   */
  public ResultSetDisplayer(Writer out, ResultSet rs) {
    Debug.prec(out, "Me debes proporcionar un out no nulo");
    Debug.prec(rs, "Me debes proporcionar un rs no nulo");
    this.rs = rs;
    this.out = new PrintWriter(out);
  }

  /**
   * Constructor de ResultSetDisplayer para imprimir un result set en un canal
   * de salida determinado.
   * @param out canal de salida
   * @param rs result set a imprimir
   */
  public ResultSetDisplayer(OutputStream out, ResultSet rs) {
    Debug.prec(out, "Me debes proporcionar un out no nulo");
    Debug.prec(rs, "Me debes proporcionar un rs no nulo");
    this.rs = rs;
    this.out = new PrintWriter(out);
  }

  /**
   * Constructor de ResultSetDisplayer para imprimir un result set en el canal
   * de salida por defecto (System.out).
   * @param rs result set a imprimir
   */
  public ResultSetDisplayer(ResultSet rs) {
    this(System.out, rs);
  }

//  public void generalInfo() throws SQLException {
//    ResultSetMetaData meta = rs.getMetaData();
//
//    int N = meta.getColumnCount();
//    System.out.println("getColumnCount --> "+N);
//    for (int i=1;i<=N;i++) {
//      System.out.println("COLUMNA "+i);
//      System.out.println("  getCatalogName --> "+meta.getCatalogName(i));
//      System.out.println("  getColumnClassName --> "+meta.getColumnClassName(i));
//      System.out.println("  getColumnDisplaySize  --> "+meta.getColumnDisplaySize(i));
//      System.out.println("  getColumnLabel  --> "+meta.getColumnLabel(i));
//      System.out.println("  getColumnName  --> "+meta.getColumnName(i));
//      System.out.println("  getColumnType  --> "+meta.getColumnType(i));
//      System.out.println("  getColumnTypeName  --> "+meta.getColumnTypeName(i));
//      System.out.println("  getPrecision  --> "+meta.getPrecision(i));
//      System.out.println("  getScale  --> "+meta.getScale(i));
//      System.out.println("  getSchemaName  --> "+meta.getSchemaName(i));
//      System.out.println("  getTableName  --> "+meta.getTableName(i));
//      System.out.println("  isNullable  --> "+meta.isNullable(i));
//    }
//  }

  /**
   * Imprime en forma de tabla el contenido del resultSet asociado al objeto, en
   * el canal de salida asociado al objeto. Acaba haciendo flush en el canal de
   * salida.
   *
   * @throws SQLException Si se produce algún error
   */
  public void print() throws SQLException {
    print(0);
  }

  /**
   * Imprime en forma de tabla el contenido del resultSet asociado al objeto, en
   * el canal de salida asociado al objeto. Acaba haciendo flush en el canal de
   * salida.
   *
   * @throws SQLException Si se produce algún error
   */
  public void print(int lineasAMostrar) throws SQLException {
    ResultSetMetaData meta = rs.getMetaData();

    int N = meta.getColumnCount();
    Table data = new Table(N,5);  // Para volcar los datos a una tabla ...
    int [] tamanios = new int[N];  // ... y poder calcular mientras los tamaños de las columnas
    for (int i = 0;i<tamanios.length;i++) tamanios[i] = 0;
    // Nombre de las columnas
    for (int i=1;i<=N;i++) {
      String colName =meta.getColumnLabel(i);
      String table = meta.getTableName(i);
      colName = table!=null && table.trim().length()>0?table+"."+colName:colName;
      data.add(""+i,colName);
      tamanios[i-1] = colName.length();
    }

    // Datos
    int numRows = 0;
    while (rs.next()) {
      numRows++;
      for (int i = 1; i <= N; i++) {
        String val = rs.getString(i);
        if (rs.wasNull() || val == null)
          val = "";
        tamanios[i-1] = tamanios[i-1] < val.length() ? val.length() : tamanios[i-1];
        data.add(""+i,val);
      }
    }

    // Impresion de cabecera
    for (int i = 1; i <= N; i++) {
      int size = tamanios[i-1]+1;
      List vals = data.get(""+i);
      out.print( toFixedSize((String)vals.get(0),size,' ') );
    }
    out.println();
    for (int i = 1; i <= N; i++) {
      int size = tamanios[i-1];
      out.print( toFixedSize("",size,'-')+" " );
    }
    out.println();

    // Impresion de datos
    int totalFilas = numRows;
    if (lineasAMostrar > 0 && lineasAMostrar<numRows) {
      numRows = lineasAMostrar;
    }
    for (int j=0;j<numRows;j++) {
      for (int i = 1; i <= N; i++) {
        int size = tamanios[i-1]+1;
        List vals = data.get(""+i);
        out.print( toFixedSize((String)vals.get(j+1),size,' ') );
      }
    out.println();
    }
    out.println("\n "+totalFilas+" filas en el resultado");
    out.flush();

  }

  private String toFixedSize(String txt, int size, char fill) {
    StringBuffer tfs = new StringBuffer(txt);
    for (int i=txt.length();i<size;i++)
      tfs.append(fill);
    return tfs.toString();
  }

//  public static void main(String[] args) throws SQLException {
//    java.sql.Statement stm = null;
//    try {
//      stm = bd.ConnectionManager.getStatement();
//
//      ResultSet rs = stm.executeQuery("SELECT * FROM Articulo");
//      ResultSetDisplayer rsd = new ResultSetDisplayer(rs);
//      rsd.generalInfo();
//      rsd.print();
//      rs.close();
//    } finally {
//      bd.ConnectionManager.closeStatement(stm);
//    }
//  }
}
