package amp.common.api.impl;

/**
 * @author michaelv
 *
 */
//import javax.xml.parsers.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import amp.common.api.interfaces.XMLInterface;




public class ToolkitXML implements XMLInterface
{
	private static Logger cLogger = 
			LoggerFactory.getLogger(ToolkitXML.class);
	
	/*=========class variables========================================*/
	//private SAXParserFactory factory = null;
	//private SAXParser saxParser = null;
	private XPath 		 xPath       = null; 
	private XPathFactory factory     = null;
	private InputSource  inputSource = null;
	private Document 	 doc         = null;
	
	private String xmlFile = "";
	
	
	private ToolkitReflection iReflection = null;
	
	/*-------------Save result for last call to class's methods--------*/
    private boolean lcRes = true;
    
	/*=========getters/setters========================================*/
	public InputSource getInputSource() {
		return inputSource;
	}
	public void setInputSource(InputSource inputSource) {
		this.inputSource = inputSource;
	}
	
	public XPath getxPath() {
		return xPath;
	}
	public void setxPath(XPath xPath) {
		this.xPath = xPath;
	}
	public XPathFactory getFactory() {
		return factory;
	}
	public void setFactory(XPathFactory factory) {
		this.factory = factory;
	}
	
	public String getXmlFile() {
		return xmlFile;
	}
	public void setXmlFile(String xmlFile) {
		this.xmlFile = xmlFile;
	}
	
	/**
	 * @return the doc
	 */
	public Document getDoc() {
		return doc;
	}
	/**
	 * @param doc the doc to set
	 */
	public void setDoc(Document doc) {
		this.doc = doc;
	}
	public boolean isLcRes() {
		return lcRes;
	}
	public void setLcRes(boolean lcRes) {
		this.lcRes = lcRes;
	}
	
	/*========class functions=========================================*/
	public ToolkitXML( String xmlFilePath )
	{
		 try
		 {
			 this.iReflection = new ToolkitReflection();
			 
			 this.setXmlFile(xmlFilePath);
			 
			 if ( ToolkitConstants.isLoadSettingsFromJar)
			 {
				 this.setLcRes(this.initFromJarFile());
			 }
			 //-------------------------------------------------------------
			 else if ( ToolkitConstants.isLoadSettingsFromWar )
			 {
				 this.setLcRes(this.initFromWarFile());
			 }
			 //-------------------------------------------------------------
			 else if ( ToolkitConstants.isLoadSettingsFromFS)
			 {
				 this.setLcRes(this.initFromOSFile());
			 }
		 }
		 catch( Exception e)
		 {
			 this.setLcRes(false);
			 
			 cLogger.error(e.getMessage() );
		 }
	}
	/*========class functions=========================================*/
//	public ToolkitXML( String xmlFile )
//	{
//		 try
//		 {
//			 this.iReflection = new ToolkitReflection();
//			 
//			 this.setXmlFile(xmlFile);
//			 
//			 this.setLcRes(this.initFromString());
//			 
//		 }
//		 catch( Exception e)
//		 {
//			 this.setLcRes(false);
//			 
//			 cLogger.error(e.getMessage() );
//		 }
//	}
	/*=============================================================================*/
	public ToolkitXML( File xmlFile )
	{
		 try
		 {
			 this.iReflection = new ToolkitReflection();
			 
			 this.setXmlFile(xmlFile.getAbsolutePath());
	
			 this.setLcRes(this.initFromOSFile());
		 }
		 catch( Exception e)
		 {
			 this.setLcRes(false);
					 
			 cLogger.error(e.getMessage() );
		 }
	}
	/*=============================================================================*/
	public boolean initFromJarFile()
    {
    	String methodName = "";
        
    	DocumentBuilder builder = null;
    	
        try
        {
        	this.setLcRes(true);
        	
        	methodName = this.iReflection.getMethodName();
         
        	this.setFactory(XPathFactory.newInstance());
			 
			this.setxPath(this.factory.newXPath());
			
			//---
			//InputStream is = this.getClass().getResourceAsStream(this.xmlFile);
			ClassLoader classLoader = this.getClass().getClassLoader();
			InputStream is = classLoader.getResourceAsStream(this.getXmlFile());
			if ( is != null )
			{
				InputSource iSrc = new InputSource(is);
				
				this.setInputSource(iSrc);
			}
			else
			{
				this.setLcRes(false);
			}
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    	
		    builder = factory.newDocumentBuilder();
		    	
		    this.setDoc(builder.parse(this.getInputSource()));
		    	
		    this.getDoc().getDocumentElement().normalize();
		     
        	return this.isLcRes();
        }
        catch (Exception e)
        {
            this.setLcRes(false);

            cLogger.error(methodName + ":" + e.getMessage() );
            
            return false; 
        }
        finally { }
    }
	
	/*=============================================================================*/
	public boolean initFromWarFile()
    {
    	String methodName = "";
        
    	DocumentBuilder builder = null;
    	
        try
        {
        	this.setLcRes(true);
        	
        	methodName = this.iReflection.getMethodName();
         
        	this.setFactory(XPathFactory.newInstance());
			 
			this.setxPath(this.factory.newXPath());
			
			ClassLoader classLoader = this.getClass().getClassLoader();
			InputStream is = classLoader.getResourceAsStream(this.getXmlFile());
			//InputStream is = this.getClass().getResourceAsStream(this.getXmlFile());
			
			if ( is != null )
			{
				InputSource iSrc = new InputSource(is);
				
				this.setInputSource(iSrc);
			}
			else
			{
				this.setLcRes(false);
			}
            
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    	
		    builder = factory.newDocumentBuilder();
		    	
		    this.setDoc(builder.parse(this.getInputSource()));
		    	
		    this.getDoc().getDocumentElement().normalize();
		     
        	return this.isLcRes();
        }
        catch (Exception e)
        {
            this.setLcRes(false);

            cLogger.error(methodName + ":" + e.getMessage() );
            
            return false; 
        }
        finally { }
    }
	/*=============================================================================*/
	public boolean initFromString()
    {
    	String methodName = "";
        
    	DocumentBuilder builder = null;
    	
        try
        {
        	this.setLcRes(true);
        	
        	methodName = this.iReflection.getMethodName();
         
        	if ( (null == this.getXmlFile()) ||  (this.getXmlFile().equals("")))
        	{
        		return true;
        	}
        	
        	this.setFactory(XPathFactory.newInstance());
			 
			this.setxPath(factory.newXPath());
			
			if ( this.isLcRes() )
			{
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    	
			    builder = factory.newDocumentBuilder();
			    
			    this.setInputSource(new InputSource(new StringReader(this.getXmlFile())));
			    
			    this.setDoc(builder.parse(this.getInputSource()));
			    
			    this.getDoc().getDocumentElement().normalize();
			}
			
        	return this.isLcRes();
        }
        catch (Exception e)
        {
            this.setLcRes(false);

            cLogger.error(methodName + ":" + e.getMessage() );
            
            return false; 
        }
        finally { }
    }
	/*=============================================================================*/
	public boolean initFromOSFile()
    {
    	String methodName = "";
        
    	DocumentBuilder builder = null;
    	
        try
        {
        	this.setLcRes(true);
        	
        	methodName = this.iReflection.getMethodName();
         
        	this.setFactory(XPathFactory.newInstance());
			 
			this.setxPath(factory.newXPath());
			
			this.setInputSource(new InputSource(this.getXmlFile()));
            
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    	
		    builder = factory.newDocumentBuilder();
		    	
		    this.setDoc(builder.parse(this.getInputSource()));
		    	
		    this.getDoc().getDocumentElement().normalize();
		    
        	return this.isLcRes();
        }
        catch (Exception e)
        {
            this.setLcRes(false);

            cLogger.error(methodName + ":" + e.getMessage() );
            
            return false; 
        }
        finally { }
    }
	/*=============================================================================*/
	public Node getXMLNodeByNodeName(String xmlStr, String nodeName)
	{
		 NodeList rNodeList = null;
		 
		 Node rNode = null;
		 
		 InputSource inputSourceL = null;
			
		 String methodName = "";
		 
		 try
		 {
			 methodName = this.iReflection.getMethodName();
			 
			 inputSourceL = new InputSource(new StringReader(xmlStr));         
			 
			 String xmlPath = "\\" + nodeName;
			 
			 XPathFactory xPathFactoryL = XPathFactory.newInstance();
			
			 XPath xPathL = xPathFactoryL.newXPath(); 
			
			 rNodeList = (NodeList)xPathL.evaluate(xmlPath, 
					 							  inputSourceL, 
					 							  XPathConstants.NODESET);

			 if ( rNodeList != null )
			 {
				 rNode = rNodeList.item(0);
			 }
			 
			 return rNode;
		 }
		  catch( Exception e)
		 {
			 cLogger.error(methodName + ":" + e.getMessage() );
			  
			 return null;
		 }
		 finally
		 {  
			 if ( inputSourceL != null )
			 {
				try 
				{
					inputSourceL.getByteStream().close();
				} 
				catch (IOException e) 
				{
					// TODO Auto-generated catch block
					this.setLcRes(false);

					cLogger.error(methodName + ":" + e.getMessage() );
				}
			 }
		 } 
	}
	/*------------------------------------------------------------------*/
	@SuppressWarnings("unused")
	private String getXmlMessage(String xmlMessageFile)
	{
		DOMSource domSource = null;
	    
		StringWriter writer = null;
	    
		StreamResult result = null;
	    
		try
		{
			String xmlFile = xmlMessageFile;
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new File(xmlFile));
			domSource = new DOMSource(doc);
		    
			writer = new StringWriter();
			result = new StreamResult(writer);
		    
		    TransformerFactory tf = TransformerFactory.newInstance();
		    Transformer transformer = tf.newTransformer();
		    transformer.transform(domSource, result);
		    String s = writer.toString();
			
			return s;
		}
		catch(ParserConfigurationException e)
		{
			this.setLcRes(false);

			cLogger.error(e.getMessage() );

			return null;
		}
		catch(SAXException e)
		{
			this.setLcRes(false);

			cLogger.error(e.getMessage() );

			return null;
		}
		catch(TransformerException e)
		{
			this.setLcRes(false);

			cLogger.error(e.getMessage() );

			return null;
		}
		catch(IOException e)
		{ 
			this.setLcRes(false);

			cLogger.error(e.getMessage() );

			return null;
		}
		finally
		{
			if ( writer != null )
			{
				try 
				{
					writer.close();
				} 
				catch (IOException e) 
				{
					this.setLcRes(false);

					cLogger.error(e.getMessage() );
				}
			}
		}
	}
	/*------------------------------------------------------------------*/

	public NodeList getXMLNodesByNodeName(String xmlStr, String nodeName)
	{
		 NodeList rNodeList = null;
		 
		 InputSource inputSourceL = null;
			 
		 try
		 {
			 inputSourceL = new InputSource(new StringReader(xmlStr));         
			 
			 String xmlPath = "\\" + nodeName;
			 
			 XPathFactory xPathFactoryL = XPathFactory.newInstance();
			
			 XPath xPathL = xPathFactoryL.newXPath(); 
			
			 rNodeList = (NodeList)xPathL.evaluate(xmlPath, 
					 							  inputSourceL, 
					 							  XPathConstants.NODESET);

			
			 
			 return rNodeList;
		 }
		  catch( Exception e)
		 {
			 return rNodeList;
		 }
		 finally
		 {  
			 if ( inputSourceL != null )
			 {
				try 
				{
					inputSourceL.getByteStream().close();
				} 
				catch (IOException e) 
				{
					// TODO Auto-generated catch block
					this.setLcRes(false);

					cLogger.error(e.getMessage() );
				}
			 }
		 } 
	}
	/*=============================================================================*/
	public Node getXMLNode( String nodeName )
	{
	     Node rNode = null;
		 
		 try
		 {
			  //String expression = "//" + nodeName;
			  
			  NodeList nodes = doc.getElementsByTagName(nodeName);
			  
			  for (int i = 0; i < nodes.getLength(); i++) 
			  {
				  rNode = (Node) nodes.item(i);
			  }
			  /*
			  rNode = (Node)xPath.evaluate(expression, 
					  					   this.inputSource, 
					  					   XPathConstants.NODE);
			  
			  */
			  
			  return rNode;
		 }
		 /*
		 catch( XPathExpressionException e)
		 {
			 this.setLcRes(false);

			 cLogger.error(e.getMessage() );
	         
			 return rNode;
		 }
		 */
		 catch( Exception e)
		 {
			 this.setLcRes(false);

			 cLogger.error(e.getMessage() );
	            
			 return rNode;
		 }
	}
	/*=============================================================================*/
	public NodeList getXMLNodeList( String nodeName )
	{
	     NodeList rNodes = null;
		 
		 try
		 {
			  String expression = "/" + nodeName;
			  
			  rNodes = (NodeList)xPath.evaluate(expression, 
					  							this.inputSource, 
					  							XPathConstants.NODESET);
			  
			  return rNodes;
		 }
		 catch( Exception e)
		 {
			 this.setLcRes(false);

			 cLogger.error(e.getMessage() );
	         
			 return rNodes;
		 }
	}
	
	/*=============================================================================*/
    public boolean isXMLFileExists(String xmlFilePath)
    {
        boolean cRes = true;

        try
        {
           if (cRes)
            {
                //if settings file exists - load it
        	    File xmlFile = new File(xmlFilePath);

                if ( !xmlFile.exists() )
                {
                    cRes = false;
                }
            }

            return cRes;
        }
        catch (Exception e)
        {
        	this.setLcRes(false);

        	cLogger.error(e.getMessage() );
	         
            return false;
        }
    }
    /*=============================================================================*/
    public boolean initClassVariables(String xmlFilePath)
    {
        boolean cRes = true;

        try
        {
        	cRes = this.isXMLFileExists(xmlFilePath);
        	if ( cRes )
        	{
	        	 this.setFactory(XPathFactory.newInstance());
	        	 
	    		 this.setxPath(factory.newXPath());
	    		 
	    		 this.setInputSource(new InputSource(xmlFilePath));
        	}
    		
        	return cRes;
        }
        catch (Exception e)
        {
        	this.setLcRes(false);

        	cLogger.error(e.getMessage() );
	         
            return false;
        }
    }
    /*=============================================================================*/
    public boolean freeResources()
    {
        boolean cRes = true;

        try
        {
	    	if ( this.getInputSource() != null )
	    	{
	    		if ( this.getInputSource().getByteStream() != null )
	    		{
	    			this.getInputSource().getByteStream().close();
	    		}
	    		if ( this.getInputSource().getCharacterStream() != null )
        		{
        			this.getInputSource().getCharacterStream().close();
        		}
	    	}
        	
    		return cRes;
        }
        catch (Exception e)
        {
        	this.setLcRes(false);

        	cLogger.error(e.getMessage() );
	         
            return false;
        }
    }
}
