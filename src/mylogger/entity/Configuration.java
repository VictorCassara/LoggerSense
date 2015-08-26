/**
 * 
 */
package mylogger.entity;

/**
 *
 * @author <a href="mailto:victor.s.cassara@ericssoninovacao.com.br">Victor Cassara</a>
 * @version $Id: filename,v x.x.x.x YYYY/MM/DD hh:mm:ss uname Exp $
 *
 */
public class Configuration {

  /**
   * Variable to receive the project name that is going to be the name of the folder under the user's directory.
   */
  private static String projectName;

  /**
   * 
   * @return
   */
  public static String getProjectName() {
    return projectName;
  }

  /**
   * 
   * @param projectName
   */
  public static void setProjectName(String projectName) {
    Configuration.projectName = projectName;
  }
  //TODO PAREI NA LÓGICA DE CONFIGURAÇÃO. COMO FAZER CARREGAR A CONFIGURAÇÃO ESTATICA A PARTIR DE UM XML
  
}
