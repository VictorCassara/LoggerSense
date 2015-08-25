/**
 * 
 */
package dao;

import util.exception.ConfigurationException;

/**
 *
 * @author <a href="mailto:victor.s.cassara@ericssoninovacao.com.br">Victor Cassara</a>
 * @version $Id: filename,v x.x.x.x YYYY/MM/DD hh:mm:ss uname Exp $
 *
 */
public interface LogDAO {
  public void message(String message);
  public void warningMessage(String message);
  public void errorMessage(String message);
  public void fileMaker();
  public void loadConfiguration() throws ConfigurationException;
  public void saveConfiguration() throws ConfigurationException;
}
