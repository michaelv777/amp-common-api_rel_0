/**
 * 
 */
package amp.common.api.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author MVEKSLER
 *
 */
public class ToolkitJAXB 
{
	private static Logger cLogger = 
			LoggerFactory.getLogger(ToolkitSQL.class);
	
	/*-------------Class Variables-------------------------------------*/
	private ToolkitReflection iReflection = null;
	/*-------------Save result for last call to class's methods--------*/
    private boolean lcRes = true;
    
    /*-------------Getters/Setters-------------------------------------*/
    public boolean isLcRes() {
		return lcRes;
	}
	public void setLcRes(boolean lcRes) {
		this.lcRes = lcRes;
	}
	/*-----------------------------------------------------------------*/
	/**
	 * 
	 */
	public ToolkitJAXB() 
	{
		this.setLcRes(true);

        try
        {
        	this.iReflection = new ToolkitReflection();
        	
        	this.setLcRes(this.initClassVariables());
        }
        catch (Exception e)
        {
            this.setLcRes(false);
        }
        finally { }
		
	}
	/*-----------------------------------------------------------------*/
    public boolean initClassVariables()
    {
    	String methodName = "";
        
        try
        {
        	this.setLcRes(true);
        	
        	methodName = this.iReflection.getMethodName();
         
            
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
    /*-----------------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	public <T> T loadFileWithJAXB(Class<T> clazz, String xmlFile) 
	{
		@SuppressWarnings("unused")
		boolean cRes = true;
		
		String methodName = "";
		
		InputStream is = null;
		
        try 
        {
        	methodName = this.iReflection.getMethodName();
			
        	cLogger.info(methodName + " going to build JAXB objects!");
			
        	
        	 if ( ToolkitConstants.isLoadSettingsFromJar)
			 {
        		JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
 	        	
 	        	Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
 	        	
 	        	is = getClass().getResourceAsStream(xmlFile);
 	        	
 				if ( is != null )
 				{
 					this.setLcRes(cRes = true);
 					
 					return (T) unmarshaller.unmarshal(is);
 				}
 				else
 				{
 					this.setLcRes(cRes = false);
 					
 					return null;
 				}
 	        	
 	        	//return (T) unmarshaller.unmarshal(new File(xmlFile));
 				
			 }
			 //-------------------------------------------------------------
			 else if ( ToolkitConstants.isLoadSettingsFromWar )
			 {
				 JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
		        	
		         Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		        	
		         is = getClass().getResourceAsStream(xmlFile);
 				
		         if ( is != null )
 				 {
 					this.setLcRes(cRes = true);
 					
 					return (T) unmarshaller.unmarshal(is);
 				 }
 				 else
 				 {
 					this.setLcRes(cRes = false);
 					
 					return null;
 				 }
		         //return (T) unmarshaller.unmarshal(new File(xmlFile));
			 }
			 //-------------------------------------------------------------
			 else if ( ToolkitConstants.isLoadSettingsFromFS)
			 {
				 JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
		        	
		         Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		        	
		         return (T) unmarshaller.unmarshal(new File(xmlFile));
			 }
        	 
        	 
	         return null;	
        	
        } 
        catch( JAXBException jbe )
        {
        	cLogger.error(methodName + ":error to build JAXB objects" + jbe.getMessage());
        	
        	return null;
        }
        finally
        {
        	if ( is != null )
        	{
        		try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }
	}
	
	protected void convertObjecttoXML(Object cObject) 
	{
		String  cMethodName = "";
		
		try
		{
			StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
	        
	        if ( cObject == null )
	        {
	        	return;
	        }
	        
	        StringWriter sw = new StringWriter();
	        
	        JAXBContext contextObj = JAXBContext.newInstance(cObject.getClass());  
	        
	        Marshaller marshallerObj = contextObj.createMarshaller();  
	        marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
	        marshallerObj.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
	        
	        marshallerObj.marshal(cObject, sw);
	        
	        cLogger.debug(cMethodName + ":" + sw.toString());
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			
			cLogger.error(cMethodName + ":" + e.getMessage());
		}
	}
}
