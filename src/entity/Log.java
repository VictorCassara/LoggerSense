/**
 * 
 */
package entity;

import java.io.File;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import util.Constants;
import util.exception.ConfigurationException;

/**
 *
 * @author <a href="mailto:victor.s.cassara@ericssoninovacao.com.br">Victor Cassara</a>
 * @version $Id: filename,v x.x.x.x YYYY/MM/DD hh:mm:ss uname Exp $
 *
 */
public class Log {
  
  /**
   * Variable to receive the local date time of the server it is running. Example: [2015-04-26][21:05:15].
   */
  private String dateTime;
  
  /**
   * Variable to receive the message that will be put inside the log.
   */
  private String message;
  
  /**
   * Variable to receive the directory which the file will be created/maintened.
   */
  private String dir;
  
  /**
   * Variable to receive the directory which the log will be kept on.
   */
  private String fileDirectory;
  
  /**
   * Variable to receive the project name located at the XML configuration file.
   */
  private String projectName;
  
  /**
   * Variable to receive the XML configuration file value representing if the user wants to clean the logs automatically periodically or not.
   */
  private boolean autoClean;
  
  /**
   * Setter responsible for setting the project name according to the xml configuration file.
   * @param name
   */
  public void setProjectName(String name){
    this.projectName = name;
  }
  
  /**
   * Getter responsible for getting the project name.
   */
  public String getProjectName(){
    return projectName;
  }
  
  /**
   * Setter responsible for the directory that will define where the file will be located.
   * @param dir
   */
  public void setDir(String dir){
    this.dir = dir;
  }
  
  /**
   * Getter responsible for returning to the sistem the directory which the log will be kept on.
   * @return
   */
  public String getFileDirectory() {
    return fileDirectory;
  }
   
  /**
   * Setter responsible for the directory which the log will be kept on.
   * @param fileDirectory
   */
  public void setFileDirectory(String fileDirectory) {
    this.fileDirectory = fileDirectory;
  }

  /**
   * Getter responsible for returning to the system if the autoclean feature is activated or not 
   * @return boolean
   */
  public boolean getAutoClean() {
    return autoClean;
  }

  /**
   * Setter responsible for setting true or false to the autoclean log feature.
   * @param autoClean
   */
  public void setAutoClean(boolean autoClean) {
    this.autoClean = autoClean;
  }

  /**
   * Getter responsible for locating the user's directory.
   * @return boolean
   */
  public String getDir() {
    String userDir = System.getProperty("user.home");
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(userDir);
    stringBuilder.append(File.separator);
    stringBuilder.append(getProjectName());
    setDir(stringBuilder.toString());
    return dir;
  }

  /**
   * Getter for the local time of the machine the server is running.
   * @return
   */
  public String getDateTime() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    LocalDateTime localDateTime = LocalDateTime.now();
    dateTime = localDateTime.format(formatter);
    StringBuilder strBuilder = new StringBuilder();
    strBuilder.append("[");
    strBuilder.append(dateTime);
    strBuilder.append("]");
    
    return strBuilder.toString();
  }
  
  /**
   * Getter for the message of the log.
   * @return String
   */
  public String getMessage() {
    return message;
  }
  
  /**
   * Setter for the message of the log.
   * @param message
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * @param log
   * @throws ConfigurationException 
   */
  public void validate(Log log) throws ConfigurationException {
    if (!log.getFileDirectory().equals("default")){
      throw new ConfigurationException("Error at configuration.xml. User has input invalid values at <filedirectory>");
    }
  }

}

