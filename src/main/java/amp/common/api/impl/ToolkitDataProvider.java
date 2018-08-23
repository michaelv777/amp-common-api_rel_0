package amp.common.api.impl;

/**
 * @author MVEKSLER
 *
 */

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ToolkitDataProvider
{
	private static Logger cLogger = 
			LoggerFactory.getLogger(ToolkitDataProvider.class);
	
	/*----------Reflection Variabsles----------------------------------*/
    private String className = "";
    
    private ToolkitReflection    iReflection    = null;
   
    private ToolkitSettings  	 tSettings      = null;
	
	private ToolkitDatabase  	 tDatabase      = null;
	
	private ToolkitSQL       	 tSQL           = null;
	
	private boolean lcRes = true;
	/*===getters/setters====================================================*/
    public ToolkitReflection getiReflection() {
		return iReflection;
	}
	public void setiReflection(ToolkitReflection iReflection) {
		this.iReflection = iReflection;
	}
	
	public ToolkitSettings gettSettings() {
		return tSettings;
	}
	public void settSettings(ToolkitSettings tSettings) {
		this.tSettings = tSettings;
	}
	
	public ToolkitDatabase gettDatabase() {
		return tDatabase;
	}
	public void settDatabase(ToolkitDatabase tDatabase) {
		this.tDatabase = tDatabase;
	}
	public ToolkitSQL gettSQL() {
		return tSQL;
	}
	public void settSQL(ToolkitSQL tSQL) {
		this.tSQL = tSQL;
	}
	
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
	
	/*======================================================================*/
	public ToolkitDataProvider()
	{
		 try
		 {
			 this.setLcRes(this.initClassVariables());
		 }
		 catch( Exception e )
		 {
			 cLogger.error( e.getMessage() );
			 
			 this.setLcRes(false);
		 }
		 finally{}
	}
	/*======================================================================*/
	public ToolkitDataProvider(boolean isInit)
	{
		 try
		 {
			 if ( isInit )
			 {
				 this.setLcRes(this.initClassVariables(isInit));
			 }
		 }
		 catch( Exception e )
		 {
			 cLogger.error( e.getMessage() );
			 
			 this.setLcRes(false);
		 }
		 finally{}
	}
	/*=================================================================*/
	@PostConstruct
	public boolean initClassVariablesExt()
	{
		 boolean cRes = true;
			
		 try
		 {
			 return cRes;
		 }
		 catch( Exception e)
		 {
			 this.setLcRes(cRes = false);
	        	
	         return cRes;
		 }
		 finally{}
	}
	/*=================================================================*/
	@PreDestroy
	public boolean freeResourcesExt()
	{
		 boolean cRes = true;
			
		 try
		 {
			
			 
			 return cRes;
		 }
		 catch( Exception e)
		 {
			 this.setLcRes(cRes = false);
	        	
	         return cRes;
		 }
		 finally{}
	}
	/*=================================================================*/
	public boolean setResources(HashMap<String, String> cResources)
	{
		 boolean cRes = true;
			
		 try
		 {
			
			 if ( this.gettSettings() != null )
			 {
				 this.gettSettings().setcResources(cResources);
			 }
			 
			 return cRes;
		 }
		 catch( Exception e)
		 {
			 this.setLcRes(cRes = false);
	        	
	         return cRes;
		 }
		 finally{}
	}
	/*=================================================================*/
	public boolean loadSettings()
	{
		 boolean cRes = true;
			
		 try
		 {
			
			 if ( this.gettSettings() != null )
			 {
				 this.gettSettings().initClassVariables();
			 }
			 
			 return cRes;
		 }
		 catch( Exception e)
		 {
			 this.setLcRes(cRes = false);
	        	
	         return cRes;
		 }
		 finally{}
	}
	/*=================================================================*/
    public boolean initClassVariables()
    {
    	String methodName = "";
        
    	boolean cRes = true;

        try
        {
        	this.iReflection = new ToolkitReflection();
        	
			methodName = this.iReflection.getMethodName();
			
			if ( cRes )
			{
				/*------create and load settings---------------------------*/
				this.tSettings = new ToolkitSettings();
				
				cRes = this.tSettings.isLcRes();
			}
			
			if ( cRes )
			{
				if ( this.tSettings.getcSettings() == null )
				{
					cRes = false;
				}
			}
		
			if ( cRes )
			{
	            /*------create and load sql queries file-------------------*/
	            this.tSQL = new ToolkitSQL(this.gettSettings());
	            
	            cRes = this.tSQL.isLcRes();
			}
			
			if ( cRes )
			{
	            /*------create and load database class---------------------*/
	            this.tDatabase = new ToolkitDatabase(this.gettSettings());
	            
	            cRes = this.tDatabase.isLcRes();
			}
			
			this.setLcRes(cRes);
			
            return cRes;
        }
        catch (Exception e)
        {
        	if ( cLogger != null )
        	{
        		cLogger.error( methodName + ":" + e.getMessage() );
        	}
	   		
        	this.setLcRes(cRes = false);
        	
        	return cRes;
        }
        finally {}
    }
    /*=================================================================*/
    public boolean initClassVariables(boolean isInit)
    {
    	String methodName = "";
        
    	boolean cRes = true;

        try
        {
        	this.iReflection = new ToolkitReflection();
        	
			methodName = this.iReflection.getMethodName();
			
			if ( cRes )
			{
				/*------create and load settings---------------------------*/
				this.tSettings = new ToolkitSettings(isInit);
				
				cRes = this.tSettings.isLcRes();
			}
			
			if ( cRes && isInit )
			{
				cRes = this.initClassObjects();
				
			}
			this.setLcRes(cRes);
			
            return cRes;
        }
        catch (Exception e)
        {
        	if ( cLogger != null )
        	{
        		cLogger.error( methodName + ":" + e.getMessage() );
        	}
	   		
        	this.setLcRes(cRes = false);
        	
        	return cRes;
        }
        finally {}
    }
    /*=================================================================*/
    public boolean initSettings(boolean isInit)
    {
    	String methodName = "";
        
    	boolean cRes = true;

        try
        {
        	this.iReflection = new ToolkitReflection();
        	
			methodName = this.iReflection.getMethodName();
			
			if ( cRes )
			{
				/*------create and load settings---------------------------*/
				this.tSettings = new ToolkitSettings(isInit);
				
				cRes = this.tSettings.isLcRes();
			}
			
			this.setLcRes(cRes);
			
            return cRes;
        }
        catch (Exception e)
        {
        	if ( cLogger != null )
        	{
        		cLogger.error( methodName + ":" + e.getMessage() );
        	}
	   		
        	this.setLcRes(cRes = false);
        	
        	return cRes;
        }
        finally {}
    }
    /*======================================================================*/
    public boolean initClassObjects()
    {
    	String methodName = "";
        
    	boolean cRes = true;

        try
        {
        	this.iReflection = new ToolkitReflection();
        	
			methodName = this.iReflection.getMethodName();
			
			if ( cRes )
			{
				if ( this.tSettings.getcSettings() == null )
				{
					cRes = false;
				}
			}
			
			if ( cRes )
			{
	            /*------create and load sql queries file-------------------*/
	            this.tSQL = new ToolkitSQL(this.gettSettings());
	            
	            cRes = this.tSQL.isLcRes();
			}
			
			if ( cRes )
			{
	            /*------create and load database class---------------------*/
	            this.tDatabase = new ToolkitDatabase(this.gettSettings());
	            
	            cRes = this.tDatabase.isLcRes();
			}
			
			this.setLcRes(cRes);
			
            return cRes;
        }
        catch (Exception e)
        {
        	if ( cLogger != null )
        	{
        		cLogger.error( methodName + ":" + e.getMessage() );
        	}
	   		
        	this.setLcRes(cRes = false);
        	
        	return cRes;
        }
        finally {}
    }
    /*======================================================================*/
    public boolean openDatabaseConnection()
    {
    	boolean cRes = true;
    	
    	String methodName = "";
      
        try
        {
			methodName = this.iReflection.getMethodName();
			/*-------------Connect to database------------------------------*/
			cRes = this.tDatabase.openSQLConnectionToDatabase(
	            	 this.tSettings.getDatabaseSettings());
			
			if (!cRes)
			{
				cLogger.error( 
						 methodName + ":tDatabase.OpenSQLConnectionToDatabase is false!" );
				
				this.setLcRes(cRes = false);
			}
			else
			{
				cLogger.info( 
						 methodName + ":tDatabase.OpenSQLConnectionToDatabase is successfull:" +
								 this.tSettings.getDatabaseSettings().getJDBCUrl());
				
				this.setLcRes(cRes = true);
			}
			
			return cRes;
        }
        catch( Exception e)
        {
        	cLogger.error( methodName + ":" + e.getMessage() );
        	
        	this.setLcRes(cRes = false);
        	
        	return cRes;
        }
        finally{}
    }
    /*======================================================================*/
    /**
	 * 
	 */
	public boolean closeDatabaseConnection() 
	{
		boolean cRes = true;
    	
    	String methodName = "";
      
        try
        {
			methodName = this.iReflection.getMethodName();
			/*-------------Connect to database------------------------------*/
			cRes = this.tDatabase.closeSQLConnectionToDatabase();
			
			if (!cRes)
			{
				cLogger.error(methodName + ":tDatabase.OpenSQLConnectionToDatabase is false!" );
				 
				 cRes = false;
			}
			return cRes;
        }
        catch( Exception e)
        {
        	cLogger.info( methodName + ":" + e.getMessage() );
        	 
        	this.setLcRes(cRes = false);
        	
        	return cRes;
        }
        finally{}
		
	}
    /*======================================================================*/
	public boolean getReflectionData()
	{
		 boolean cRes = true;
			
		 try
		 {
			 this.className = this.iReflection.getClassName();
			 
			 if ( this.className == null ) 
			 { 
				 cRes = false; 
			 }
			 /*Method currMethod = getClass().getDeclaredMethod(methodName, null);*/
			 
			 return cRes;
		 }
		 catch( Exception e)
		 {
			 this.setLcRes(cRes = false);
	        	
	         return cRes;
		 }
		 finally{}
	}
	
	/*======================================================================*/
	public boolean getReflectionDataLocal()
	{
		 boolean cRes = true;
			
		 try
		 {
			 this.className = this.iReflection.getClassName();
			 
			 if ( this.className == null ) 
			 { 
				 cRes = false; 
			 }
			 /*Method currMethod = getClass().getDeclaredMethod(methodName, null);*/
			 
			 return cRes;
		 }
		 catch( Exception e)
		 {
			 return false;
		 }
		 finally{}
	}
	/*-----------------------------------------------------*/
	public boolean freeResources()
    {
        boolean cRes = true;
        
        String methodName = "";
        
        try
        {
			methodName = this.iReflection.getMethodName();
			
	    	if ( this.tDatabase != null )
	    	{
	    		this.tDatabase.freeResources();
	    	}
	    	
	    	if ( this.tSQL != null )
	    	{
	    		this.tSQL.freeResources();
	    	}
        	
	    	if ( this.tSettings != null )
	    	{
	    		this.tSettings.freeResources();
	    	}
	    	
    		return cRes;
        }
        catch (Exception e)
        {
        	cLogger.error( methodName + ":" + e.getMessage() );
       	 
        	this.setLcRes(cRes = false);
        	
        	return cRes;
        }
    }
	//-----------------------------------------------------------------------------
	/*=========End DataProvider====================================================*/
	
}

