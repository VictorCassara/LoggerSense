/**
 * 
 */
package loggersense.dao;

import loggersense.util.exception.ConfigurationException;

/**
 *
 * @author <a href="mailto:victor.s.cassara@ericssoninovacao.com.br">Victor Cassara</a>
 * @version $Id: filename,v x.x.x.x YYYY/MM/DD hh:mm:ss uname Exp $
 *
 */
public interface LogDAO {
  /**
   * Writes a message in the log file.
   */
  public void message(String message);
  /**
   * Writes a message with the warning tag in the log file.
   */
  public void warningMessage(String message);
  /**
   * Writes a message with the error tag in the log file.
   */
  public void errorMessage(String message);
  /**
   * Makes sure the directory is created at the user's home and loads the file variable.
   */
  public void fileMaker();
  /**
   * Load all configurations made by the user in the XML file.
   */
  public void loadConfiguration() throws ConfigurationException;
  /**
   * Save any changes the user made to the XML configuration file.
   */  
  public void saveConfiguration(String projectName, String autoClean, String fileDirectory) throws ConfigurationException;
  
  /**
   * Checks if the directory exists to create it or not.
   */
  public void xmlFileMaker();
}
