/**
 * 
 */
package util;

/**
 *
 * @author <a href="mailto:victor.s.cassara@ericssoninovacao.com.br">Victor Cassara</a>
 * @version $Id: filename,v x.x.x.x YYYY/MM/DD hh:mm:ss uname Exp $
 *
 */
public class Constants {
  
  /**
   * Variable that defines the name of the log file that is stored under the user's directory.
   */
  private final static String FILE_NAME = "log.txt";
  
  /**
   * Variable that defines the tag of an error message in the log.
   */
  private static final String ERROR_TAG = "[ERROR]";
  
  /**
   * Variable that defines the tag of an warning message in the log.
   */
  private static final String WARNING_TAG = "[WARNING]";
  
  /**
   * Variable that defines the tag of an ok message in the log.
   */
  private static final String OK_TAG = "[OK]";
  
  
  /**
   * Getter responsible for retrieving the ok tag that shows up inside the log file.
   * @return String containing the ok tag value (which is a constant defined by the developer)
   */
  public static String getOkTag() {
    return OK_TAG;
  }

  /**
   * Getter responsible for retrieving the name of the file that is stored under the user's directory/PlatformSense.
   * @return String containing the file name (which is a constant defined by the developer)
   */
  public static String getFILENAME() {
    return FILE_NAME;
  }

  /**
   * Getter responsible for retrieving the error tag that shows up inside the log file.
   * @return String containing the error tag value (which is a constant defined by the developer)
   */
  public static String getError() {
    return ERROR_TAG;
  }

  /**
   * Getter responsible for retrieving the warning that shows up inside the log file.
   * @return String containing the warning tag value (which is a constant defined by the developer)
   */
  public static String getWarning() {
    return WARNING_TAG;
  }
  
  
  
}
