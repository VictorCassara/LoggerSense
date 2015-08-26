/**
 * 
 */
package mylogger.controller;

import mylogger.dao.LogDAO;
import mylogger.dao.impl.LogDAOImpl;
import mylogger.util.exception.ConfigurationException;

/**
 *
 * @author <a href="mailto:victor.s.cassara@ericssoninovacao.com.br">Victor Cassara</a>
 * @version $Id: filename,v x.x.x.x YYYY/MM/DD hh:mm:ss uname Exp $
 *
 */
public class Logger {
  private LogDAO log;
  
  public Logger(){
    log = new LogDAOImpl();
    try {
      log.loadConfiguration();
    } catch (ConfigurationException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Our log functionalities object.
   */
  
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
  
  /**
   * Method responsible for writing the new project name, auto clean option and file directory
   * @param projectName
   * @param autoClean
   * @param fileDirectory
   */
  public void newConfig(String projectName, String autoClean, String fileDirectory){
    try {
      log.saveConfiguration(projectName, autoClean, fileDirectory);
    } catch (ConfigurationException e) {
      e.printStackTrace();
    }
  }
}
