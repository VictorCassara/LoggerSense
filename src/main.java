import util.exception.ConfigurationException;
import dao.LogDAO;
import dao.impl.LogDAOImpl;

/**
 * 
 */

/**
 *
 * @author <a href="mailto:victor.s.cassara@ericssoninovacao.com.br">Victor Cassara</a>
 * @version $Id: filename,v x.x.x.x YYYY/MM/DD hh:mm:ss uname Exp $
 *
 */
public class main {

  /**
   * @param args
   */
  public static void main(String[] args) {
    LogDAO log = new LogDAOImpl();
    try {
      log.loadConfiguration();
      log.message("Testetra");
      log.errorMessage("Tetsucabra");
      log.warningMessage("SADADASDAS");
    } catch (ConfigurationException e) {
      e.printStackTrace();
    }
  }

}
