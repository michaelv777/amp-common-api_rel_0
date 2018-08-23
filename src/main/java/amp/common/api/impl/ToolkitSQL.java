/**
 * 
 */
package amp.common.api.impl;

import java.io.File;
import java.net.URL;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;


/**
 * @author MVEKSLER
 *
 */
public class ToolkitSQL 
{
	private static Logger cLogger = 
			LoggerFactory.getLogger(ToolkitSQL.class);
	
	/*-------------Class Variables-------------------------------------*/
	private ToolkitSettings tSettings = null;
	
	private ToolkitReflection iReflection = null;
    
    private ToolkitXML iXML = null;
    
    private String className   = "";
    
	/*-------------Save result for last call to class's methods--------*/
    private boolean lcRes = true;

    /*----------settings file - read and save at the program start-----*/
    private String sqlFile = "";
   
    
    /*-------------Getters/Setters---------------------------------------*/
    public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public ToolkitReflection getIReflection() {
		return iReflection;
	}
	public void setIReflection(ToolkitReflection reflection) {
		iReflection = reflection;
	}
	public boolean isLcRes() {
		return lcRes;
	}
	public void setLcRes(boolean lcRes) {
		this.lcRes = lcRes;
	}
	public String getSqlFile() {
		return sqlFile;
	}
	public void setSqlFile(String sqlFile) {
		this.sqlFile = sqlFile;
	}
	
	
    
	/**
   	 * @return the tSettings
   	 */
   	public ToolkitSettings gettSettings() {
   		return tSettings;
   	}
   	/**
   	 * @param tSettings the tSettings to set
   	 */
   	public void settSettings(ToolkitSettings tSettings) {
   		this.tSettings = tSettings;
   	}
   	/**
   	 * @return the iXML
   	 */
   	public ToolkitXML getiXML() {
   		return iXML;
   	}
   	/**
   	 * @param iXML the iXML to set
   	 */
   	public void setiXML(ToolkitXML iXML) {
   		this.iXML = iXML;
   	}
    
   	
    /*-------------Class Methods---------------------------------------*/
    public ToolkitSQL()
	{
    	try
    	{
    		this.iReflection = new ToolkitReflection();
    	}
    	catch( Exception e ){}
		//
		// TODO: Add constructor logic here
		//
	}
   
	/*=================================================================*/
    public ToolkitSQL(ToolkitSettings tSettings)
    {
        
        this.setLcRes(true);

        try
        {
        	this.iReflection = new ToolkitReflection();
        	
        	this.settSettings(tSettings);
        	 
        	this.setSqlFile(tSettings.getSQLSettings().getSqlFile());
           
            if (!this.initClassVariables())
            {
                this.setLcRes(false);
            }
        }
        catch (Exception e)
        {
        	cLogger.error(e.getMessage());
        	
            this.setLcRes(false);
        }
        finally { }
    }
    /*==================================================================================*/
    public boolean checkSQLFile()
    {
    	String methodName = "";
    	
        try
        {
        	this.setLcRes(true);
        	
        	methodName = this.iReflection.getMethodName();
            
        	cLogger.warn(
					methodName + ":" + this.sqlFile + " check if sql file exists!" );
        	
        	File sFile = null;
        	
        	if ( this.gettSettings().getcSettings().isLoadSettingsFromFS() )
        	{
        		sFile = new File(this.sqlFile);
        	}
        	else
        	{
        		ClassLoader classLoader = getClass().getClassLoader();
    			
    			URL url = classLoader.getResource(this.sqlFile);
    			
    			if ( url != null )
    			{
    				sFile = new File(url.getFile());
    			}
        	}
        	
			if ( (sFile == null) || (!sFile.exists()))
            {
				this.setLcRes(false);
				
				cLogger.warn( 
						methodName + ":" + this.sqlFile + " doesn't exists!" );
            }
            else
            {
            	this.setLcRes(true);
            }
           
			return this.isLcRes();
        }
        catch (Exception e)
        {
        	cLogger.error(methodName + ":" + e.getMessage() );
            
            return false;
        }
    }
    /*==================================================================================*/
    public boolean initClassVariables()
    {
    	String methodName = "";
        
        try
        {
        	this.setLcRes(true);
        	
        	methodName = this.iReflection.getMethodName();
         
        	this.iXML = new ToolkitXML(this.sqlFile);
        	
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
    /*==================================================================================*/
   
    /*========Get sql query by function name===================================================*/
    public String getSqlQueryByFunctionName(String sqlFunName,
                                            Vector<String> repParams)
    {
    	String methodName = "";
        String sqlQuery = "";

        try
        {
        	this.setLcRes(true);
        	methodName = this.iReflection.getMethodName();

            if (this.iXML.getInputSource() == null)
            {
            	this.setLcRes(false);
            	
            	cLogger.warn(
						methodName + ":" + " is null for " + this.sqlFile);

                return ToolkitConstants.DEFAULT_STR;
            }

            Node csNode = this.iXML.getXMLNode(sqlFunName);
            
            if (csNode != null)
            {
                sqlQuery = csNode.getTextContent();
            }
            else
            {
            	this.setLcRes(false);
            	
            	cLogger.error(
            		methodName + ":" + "Sql statement not found for " + sqlFunName);
            }

            /*------------------replace all query parameters------------------------------------*/
            StringBuilder sqlQueryBld = new StringBuilder(sqlQuery);
            if (this.isLcRes())
            {
                int par_index = 0;
                int rep_index = 0;

                rep_index = sqlQuery.indexOf(ToolkitConstants.SQL_REP);
                
                while (rep_index >= 0)
                {
                	String parValue = String.valueOf(repParams.elementAt(par_index));
                    
                	sqlQueryBld = new StringBuilder(sqlQuery);
                    
                	sqlQueryBld.replace(rep_index,
                    					rep_index + 
                    					ToolkitConstants.SQL_REP.length(),
                                        parValue);
                    
                	sqlQuery = sqlQueryBld.toString();

                    ++par_index;
                    
                    rep_index = sqlQuery.indexOf(ToolkitConstants.SQL_REP);
                }
            }

            return sqlQuery;
        }
        catch (Exception e)
        {
        	cLogger.error(methodName + ":" + e.getMessage() );
        	
        	this.setLcRes(false);

            return ToolkitConstants.DEFAULT_STR;
        }
        finally { }
    }
    /*========Get sql query by function name===================================================*/
    public String getSqlQueryByFunctionName(String sqlFunName)
    {
    	String methodName = "";
        String sqlQuery = "";

        try
        {
        	this.setLcRes(true);
        	methodName = this.iReflection.getMethodName();

            if (this.iXML.getInputSource() == null)
            {
            	this.setLcRes(false);
            	
            	cLogger.warn(methodName + ":" + " is null for " + this.sqlFile);

                return ToolkitConstants.DEFAULT_STR;
            }

            Node csNode = this.iXML.getXMLNode(sqlFunName);
            if (csNode != null)
            {
                sqlQuery = csNode.getTextContent();
            }
            else
            {
            	this.setLcRes(false);
            	
            	cLogger.error(methodName + ":" + "Sql statement not found for " + sqlFunName);
            }

            return sqlQuery;
        }
        catch (Exception e)
        {
        	cLogger.error(methodName + ":" + e.getMessage() );
        	
        	this.setLcRes(false);

            return ToolkitConstants.DEFAULT_STR;
        }
        finally { }
    }
    /*------------------------------------------------------------------------------*/
    public boolean freeResources()
    {
        boolean cRes = true;

        String methodName = "";
        
        try
        {
        	methodName = this.iReflection.getMethodName();
        	
	    	if ( this.iXML != null )
	    	{
	    		this.iXML.freeResources();
	    	}
	    	
    		return cRes;
        }
        catch (Exception e)
        {
        	cLogger.error(methodName + ":" + e.getMessage() );
        	
            return false;
        }
    }
}
