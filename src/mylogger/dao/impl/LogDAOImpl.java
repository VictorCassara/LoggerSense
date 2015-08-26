/**
 * 
 */
package mylogger.dao.impl;

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

import mylogger.dao.LogDAO;
import mylogger.entity.Log;
import mylogger.util.Constants;
import mylogger.util.exception.ConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
   File fileXMLDir;
   File fileXML;

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
    StringBuilder sbDir = new StringBuilder();
    sbDir.append(System.getProperty("user.home"));
    sbDir.append(File.separator);
    sbDir.append("LoggerSense");
    sbDir.append(File.separator);
    fileDir = new File(sbDir.toString());
    
    if(!fileDir.exists()){
      fileDir.mkdir();
    }
    
    if(fileDir.isDirectory()){
      file = new File(fileDir, Constants.getFILENAME());
    }
    
  }
  
  

  @Override
  public void loadConfiguration() throws ConfigurationException {
    xmlFileMaker();
    DocumentBuilderFactory dBFactory = DocumentBuilderFactory.newInstance();
    try {
      DocumentBuilder dBuilder = dBFactory.newDocumentBuilder();
      Document document = dBuilder.parse(fileXML);
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
      StringBuilder sbDir = new StringBuilder();
      sbDir.append(System.getProperty("user.home"));
      sbDir.append(File.separator);
      sbDir.append("LoggerSense");
      sbDir.append(File.separator);
      sbDir.append(Constants.getXmlFile());
      
      DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
      Document document = documentBuilder.parse(sbDir.toString());
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
      StreamResult streamResult = new StreamResult(new File(sbDir.toString()));
      transformer.transform(domSource, streamResult);


    } catch (IOException e) {
      e.printStackTrace();
    } catch (SAXException e) {
      e.printStackTrace();
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    } catch (TransformerConfigurationException e) {
      e.printStackTrace();
    } catch (TransformerException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void xmlFileMaker() {
    StringBuilder sbDir = new StringBuilder();
    sbDir.append(System.getProperty("user.home"));
    sbDir.append(File.separator);
    sbDir.append("LoggerSense");
    sbDir.append(File.separator);
    fileXMLDir = new File(sbDir.toString());
    
    if(!fileXMLDir.exists()){
      fileXMLDir.mkdir();
      try {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("loggersense");
        doc.appendChild(rootElement);

        Element configuration = doc.createElement("configuration");
        rootElement.appendChild(configuration);

        Element projectName = doc.createElement("projectname");
        projectName.appendChild(doc.createTextNode("MyProject"));
        configuration.appendChild(projectName);

        Element autoClean = doc.createElement("autoclean");
        autoClean.appendChild(doc.createTextNode("true"));
        configuration.appendChild(autoClean);

        Element fileDirectory = doc.createElement("filedirectory");
        fileDirectory.appendChild(doc.createTextNode("default"));
        configuration.appendChild(fileDirectory);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        fileXML = new File(fileXMLDir, Constants.getXmlFile());
        StreamResult result = new StreamResult(fileXML);

        transformer.transform(source, result);

        } catch (ParserConfigurationException pce) {
        pce.printStackTrace();
        } catch (TransformerException tfe) {
        tfe.printStackTrace();
        }
    }
    
    if(fileXMLDir.isDirectory()){
      fileXML = new File(fileXMLDir, Constants.getXmlFile());
    }
  }

}
