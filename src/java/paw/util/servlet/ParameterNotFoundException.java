// Copyright (C) 1998-2001 by Jason Hunter <jhunter_AT_acm_DOT_org>.
// All rights reserved.  Use of this class is limited.
// Please see the LICENSE for more information.

package paw.util.servlet;

/** 
 * Thrown to indicate a parameter does not exist.
 *
 * @see paw.util.servlet.ParameterParser
 *
 * @author <b>Jason Hunter</b>, Copyright &#169; 1998
 * @version 2.0, 98/09/18
 */
public class ParameterNotFoundException extends Exception {

  /**
   * Constructs a new ParameterNotFoundException with no detail message.
   */
  public ParameterNotFoundException() {
    super();
  }

  /**
   * Constructs a new ParameterNotFoundException with the specified
   * detail message.
   *
   * @param s the detail message
   */
  public ParameterNotFoundException(String s) {
    super(s);
  }
}
