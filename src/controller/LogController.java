/**
 * 
 */
package controller;

import dao.LogDAO;
import dao.impl.LogDAOImpl;

/**
 *
 * @author <a href="mailto:victor.s.cassara@ericssoninovacao.com.br">Victor Cassara</a>
 * @version $Id: filename,v x.x.x.x YYYY/MM/DD hh:mm:ss uname Exp $
 *
 */
public class LogController {
  
  /**
   * Our log functionalities object.
   */
  LogDAO log = new LogDAOImpl();
  
  /**
   * Method responsible for writing the message.
   * @param message
   */
  public void message(String message){
    log.message(message);
  }
  
  /**
   * Method responsible for writing the message with error.
   * @param message
   */
  public void error(String message){
    log.errorMessage(message);
  }
  
  /**
   * Method responsible for writing the message with warning.
   * @param message
   */
  public void warning(String message){
    log.warningMessage(message);
  }
}
