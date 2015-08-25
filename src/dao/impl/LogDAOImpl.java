/**
 * 
 */
package dao.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import util.Constants;
import util.exception.ConfigurationException;
import dao.LogDAO;
import entity.Log;

/**
 *
 * @author <a href="mailto:victor.s.cassara@ericssoninovacao.com.br">Victor Cassara</a>
 * @version $Id: filename,v x.x.x.x YYYY/MM/DD hh:mm:ss uname Exp $
 *
 */
public class LogDAOImpl implements LogDAO {
   Log log = new Log();
   File fileDir;
   File file;

  /* (non-Javadoc)
   * @see dao.LogDAO#message(java.lang.String)
   */
  @Override
  public void message(String message) {
    fileMaker();
    StringBuilder finalMessage = new StringBuilder();
    finalMessage.append(log.getDateTime());
    finalMessage.append("[");
    finalMessage.append(log.getProjectName());
    finalMessage.append("]");
    finalMessage.append(": ");
    finalMessage.append(message);
    finalMessage.append(System.getProperty("line.separator"));
    
    try {
      FileWriter fileWriter = new FileWriter(file, true);
      PrintWriter printWriter = new PrintWriter(fileWriter);
      printWriter.write(finalMessage.toString());
      printWriter.flush();
      printWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void warningMessage(String message) {
    fileMaker();
    StringBuilder finalMessage = new StringBuilder();
    finalMessage.append(log.getDateTime());
    finalMessage.append("[");
    finalMessage.append(log.getProjectName());
    finalMessage.append("]");
    finalMessage.append(Constants.getWarning());
    finalMessage.append(": ");
    finalMessage.append(message);
    finalMessage.append(System.getProperty("line.separator"));
    
    try {
      FileWriter fileWriter = new FileWriter(file, true);
      PrintWriter printWriter = new PrintWriter(fileWriter);
      printWriter.write(finalMessage.toString());
      printWriter.flush();
      printWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void errorMessage(String message) {
    fileMaker();
    StringBuilder finalMessage = new StringBuilder();
    finalMessage.append(log.getDateTime());
    finalMessage.append("[");
    finalMessage.append(log.getProjectName());
    finalMessage.append("]");
    finalMessage.append(Constants.getError());
    finalMessage.append(": ");
    finalMessage.append(message);
    finalMessage.append(System.getProperty("line.separator"));
    
    try {
      FileWriter fileWriter = new FileWriter(file, true);
      PrintWriter printWriter = new PrintWriter(fileWriter);
      printWriter.write(finalMessage.toString());
      printWriter.flush();
      printWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void fileMaker() {
    fileDir = new File(log.getDir());
    
    if(!fileDir.exists()){
      fileDir.mkdir();
    }
    
    if(fileDir.isDirectory()){
      file = new File(fileDir, Constants.getFILENAME());
    }
    
  }

  @Override
  public void loadConfiguration() throws ConfigurationException {
    File xmlFile = new File("../MyLogger/Configuration/", "configuration.xml");
    DocumentBuilderFactory dBFactory = DocumentBuilderFactory.newInstance();
    try {
      DocumentBuilder dBuilder = dBFactory.newDocumentBuilder();
      Document document = dBuilder.parse(xmlFile);
      document.getDocumentElement().normalize();
      NodeList nodeList = document.getElementsByTagName("configuration");
      
      for(int temp = 0; temp < nodeList.getLength(); temp++ ){
        Node node = nodeList.item(temp);
        
        if(node.getNodeType() == Node.ELEMENT_NODE){
          Element element = (Element) node;
          log.setProjectName(element.getElementsByTagName("projectname").item(0).getTextContent());
          String autoClean=element.getElementsByTagName("autoclean").item(0).getTextContent();
          if (autoClean.equals("true")){
            log.setAutoClean(true);
          } else if (autoClean.equals("false")) {
            log.setAutoClean(false);
          } else {
            throw new ConfigurationException("Invalid autoclean value inside configuration.xml");
          }
          log.setFileDirectory(element.getElementsByTagName("filedirectory").item(0).getTextContent());
        }
      }
      
      log.validate(log);
      
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    } catch (SAXException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void saveConfiguration(String projectName, String autoClean, String fileDirectory) throws ConfigurationException {
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder;
    try {
      DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
      Document document = documentBuilder.parse("../MyLogger/Configuration/configuration.xml");
      Node configuration = document.getElementsByTagName("configuration").item(0);
      NamedNodeMap attribute = configuration.getAttributes();
      NodeList nodes = configuration.getChildNodes();
      
      for (int i = 0; i < nodes.getLength(); i++) {
        Node element = nodes.item(i);
        if ("projectname".equals(element.getNodeName())) {
          element.setTextContent(projectName);
        }
        if ("autoclean".equals(element.getNodeName())) {
          element.setTextContent(autoClean);
        }
        if ("filedirectory".equals(element.getNodeName())) {
          element.setTextContent(fileDirectory);
        }
      }
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      DOMSource domSource = new DOMSource(document);
      StreamResult streamResult = new StreamResult(new File("../MyLogger/Configuration/configuration.xml"));
      transformer.transform(domSource, streamResult);


    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (SAXException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ParserConfigurationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (TransformerConfigurationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (TransformerException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
