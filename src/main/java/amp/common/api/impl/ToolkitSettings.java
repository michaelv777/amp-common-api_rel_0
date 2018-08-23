/**
 * 
 */
package amp.common.api.impl;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author MVEKSLER
 *
 */
public class ToolkitSettings
{
	/*=========class variables======================================*/
	
	/*---------common settings variable-----------------------------*/
	private CommonSettings    cSettings   = null;
	
	private SQLSettings       sqlSettings = null;
	
	private DatabaseSettings  dbSettings  = null;
	
	private HibernateSettings hbsSettings = null;
	
	private RemotingSettings  remSettings = null;
	
	private JMSSettings       jmsSettings = null;
	
	private LoggerSettings    logSettings = null;
	
	private ToolkitReflection iReflection   = null;
	
	private ToolkitXML iSettingsXML         = null;
	
	private String settingsFilePath = ""; 
	private String className        = "";
	private String filePath         = "";
	private String configFolderPath = "";
	 
	private HashMap<String, String> cResources = null;
	
	public boolean lcRes = true;
	/*----------settings file - read and save at the program start-*/
    
	/*===getters/setters=============================================*/
	public SQLSettings getSqlSettings() {
		return sqlSettings;
	}
	public void setSqlSettings(SQLSettings sqlSettings) {
		this.sqlSettings = sqlSettings;
	}
	public DatabaseSettings getDbSettings() {
		return dbSettings;
	}
	public void setDbSettings(DatabaseSettings dbSettings) {
		this.dbSettings = dbSettings;
	}
	
	public RemotingSettings getRemSettings() {
		return remSettings;
	}
	public void setRemSettings(RemotingSettings remSettings) {
		this.remSettings = remSettings;
	}
	public ToolkitReflection getiReflection() {
		return iReflection;
	}
	public void setiReflection(ToolkitReflection iReflection) {
		this.iReflection = iReflection;
	}
	public ToolkitXML getSetiingsXML() {
		return iSettingsXML;
	}
	public void setSettingsXML(ToolkitXML iXML) {
		this.iSettingsXML = iXML;
	}
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getConfigFolderPath() {
		return configFolderPath;
	}
	public void setConfigFolderPath(String folderPath) {
		this.configFolderPath = folderPath;
	}
	public boolean isLcRes() {
		return lcRes;
	}
	public void setLcRes(boolean gStatus) {
		this.lcRes = gStatus;
	}
	public void setcSettings(CommonSettings cSettings) {
		this.cSettings = cSettings;
	}
	
	public String getSettingsFilePath() {
		return settingsFilePath;
	}
	public void setSettingsFilePath(String settingsFilePath) {
		this.settingsFilePath = settingsFilePath;
	}
	
	
	public JMSSettings getJmsSettings() {
		return jmsSettings;
	}
	public void setJmsSettings(JMSSettings jmsSettings) {
		this.jmsSettings = jmsSettings;
	}
	
	public HibernateSettings getHbsSettings() {
		return hbsSettings;
	}
	public void setHbsSettings(HibernateSettings hbsSettings) {
		this.hbsSettings = hbsSettings;
	}
	
	/**
	 * @return the logSettings
	 */
	public LoggerSettings getLogSettings() {
		return logSettings;
	}
	/**
	 * @param logSettings the logSettings to set
	 */
	public void setLogSettings(LoggerSettings logSettings) {
		this.logSettings = logSettings;
	}
	
	
	/**
	 * @return the cResources
	 */
	public HashMap<String, String> getcResources() {
		return cResources;
	}
	/**
	 * @param cResources the cResources to set
	 */
	public void setcResources(HashMap<String, String> cResources) {
		this.cResources = cResources;
	}
	/*=========class functions=======================================*/
	public ToolkitSettings()
	{
		 String methodName = "";
			 
		 try
         {
			 this.iReflection = new ToolkitReflection();
			 
			 methodName = this.iReflection.getMethodName();
			 
			 this.setLcRes(this.initClassVariables());
         }
         catch( Exception e )
         {
        	 System.out.println(methodName + "::" + e.getMessage());
        	 
        	 this.setLcRes(false);
         }
	}
	/*=========class functions=======================================*/
	public ToolkitSettings(boolean isInit)
	{
		 String methodName = "";
			 
		 try
         {
			 this.iReflection = new ToolkitReflection();
			 
			 methodName = this.iReflection.getMethodName();
			 
			 if ( isInit )
			 {
				 this.setLcRes(this.initClassVariables());
			 }
         }
         catch( Exception e )
         {
        	 System.out.println(methodName + "::" + e.getMessage());
        	 
        	 this.setLcRes(false);
         }
	}
	/*======================================================================*/
	public boolean initClassVariables()
    {
    	boolean cRes = true;
    	 
        try
        {
        	 if ( null == this.cResources )
        	 {
        		 this.cResources = new HashMap<String, String>();
        	 }
       	 
        	 if ( ToolkitConstants.isLoadSettingsFromWar )
        	 {
        		 cRes = this.initSettingsFromWar();
        	 }
        	 
        	 else if ( ToolkitConstants.isLoadSettingsFromJar )
        	 {
        		 cRes = this.initSettingsFromJar();
        	 }
        	 
        	 else if ( ToolkitConstants.isLoadSettingsFromFS )
			 {
				 cRes = this.initSettingsFromFS();
			 }
			 
			 if ( cRes )
			 {
				 cRes = this.loadAllSettings();
			 }
			 
			 this.setLcRes(cRes);
			 
			 return cRes;
        }
        catch (Exception e)
        {
            this.setLcRes(false);

            return false; 
        }
        finally { }
    }
	/*======================================================================*/
	/**
	 * @param 
	 * @return
	 */
	private boolean initSettingsFromFS() 
	{
		boolean cRes = true;
		
		try
        {
			 if ( ToolkitConstants.isLoadSettingsFromFS)
			 {
				 cRes = this.setConfigFolder(ToolkitConstants.FS_CONFIG_FODLER);
			 }
			 //-------------------------------------------------------------
			 if ( ToolkitConstants.isLoadSettingsFromFS )
			 {
				 cRes = this.setFSSettingsFile();
			 }
			 //-------------------------------------------------------------
			 if ( ToolkitConstants.isLoadSettingsFromFS)
			 {
				if ( this.iSettingsXML != null )
				{
					this.iSettingsXML.freeResources();
				}
				
			    this.iSettingsXML = new ToolkitXML(this.getSettingsFilePath());
			    
			    cRes =  this.iSettingsXML.isLcRes();
			 }
			 
			 return cRes;
        }
	    catch (Exception e)
	    {
	        this.setLcRes(false);
	
	        return false; 
	    }
	}
	/*======================================================================*/
	/**
	 * 
	 * @return boolean
	 */
	
	private boolean initSettingsFromJar() 
	{
		boolean cRes = true;
   	 
        try
        {
        	
        	if ( cRes && !ToolkitConstants.isLoadSettingsFromFS)
        	{
				 //-------------------------------------------------------------
				 if ( cRes )
				 {
					 cRes = this.setJarSettingsFile();
				 }
				 if ( cRes )
				 {
					if ( this.iSettingsXML != null )
					{
						this.iSettingsXML.freeResources();
					}
					 
				    this.iSettingsXML = new ToolkitXML(this.settingsFilePath);
				    
				    cRes =  this.iSettingsXML.isLcRes();
				 }
				 //-------------------------------------------------------------
				 
        	}

        	return cRes;
        }
	    catch (Exception e)
	    {
	            this.setLcRes(false);
	
	            return false; 
	    }
	}
	/*======================================================================*/
	/**
	 * 
	 * @return boolean
	 */
	private boolean initSettingsFromWar() 
	{
		boolean cRes = true;
   	 
		String cSettingsFile = "";
		
        try
        {
    		 //-------------------------------------------------------------
			 if ( cRes )
			 {
				if ( this.iSettingsXML != null )
				{
					this.iSettingsXML.freeResources();
				}
			 }
			 
			 if ( cRes )
			 {
				cSettingsFile = ToolkitConstants.TOOLKIT_SETTINGS_FILE_JAR;
				
				if ( (null == cSettingsFile) || (cSettingsFile.equals("")) )
				{
					cRes = false;
				}
			 }
			 
			 if ( cRes )
			 {
			    this.iSettingsXML = new ToolkitXML(cSettingsFile);
			    
			    cRes =  this.iSettingsXML.isLcRes();
			 }
			 
			 //-------------------------------------------------------------
				 
        	

        	return cRes;
        }
	    catch (Exception e)
	    {
	            this.setLcRes(false);
	
	            return false; 
	    }
	}
	/*======================================================================*/
	public boolean loadAllSettings()
    {
    	boolean cRes = true;
    	 
        try
        {
        	 this.setLcRes(true);
        	 
        	
			 //-------------------------------------------------------------
			 if ( cRes )
			 {
				 /*--------set common settings--------------*/
				 this.cSettings = new CommonSettings();
				 if ( !this.cSettings.setSettings(this.iSettingsXML))
				 {
					 this.setLcRes(false);
				 }
				 
				 /*-------set sql statements settings-------*/
				 this.sqlSettings = new SQLSettings();
				 if ( !this.sqlSettings.setSettings(this.iSettingsXML))
				 {
					 this.setLcRes(false);
				 }
				 
				 /*-------set database settings-------------*/
				 this.dbSettings = new DatabaseSettings();
				 if ( !this.dbSettings.setSettings(this.iSettingsXML))
				 {
					 this.setLcRes(false);
				 }
				 
				 /*-------set JMS settings------------------*/
				 this.jmsSettings = new JMSSettings();
				 if ( !this.jmsSettings.setSettings(this.iSettingsXML))
				 {
					 this.setLcRes(false);
				 }
				 
				 /*-------set Hibernate settings-------------*/
				 this.hbsSettings = new HibernateSettings();
				 if ( !this.hbsSettings.setSettings(this.iSettingsXML))
				 {
					 this.setLcRes(false);
				 }
				
				 /*-------set Logger settings-------------*/
				 this.logSettings = new LoggerSettings();
				 if ( !this.logSettings.setSettings(this.iSettingsXML))
				 {
					 this.setLcRes(false);
				 }
			 }
			 
			 return this.isLcRes();
        }
        catch (Exception e)
        {
            this.setLcRes(false);

            return false; 
        }
        finally { }
    }
	/*======================================================================*/
	public boolean setFSSettingsFile()
	{
		 boolean cRes = true;
			
		 String methodName = "";
 		
	     try
	     {
	        methodName = this.iReflection.getMethodName();
			/*----------if settings file exists - load it----------------------*/
			this.setSettingsFilePath( //this.getConfigFolderPath() +
						//ToolkitConstants.SLASH +
						ToolkitConstants.TOOLKIT_SETTINGS_FILE_FS);
			
			
			File sfile = null;
			
			sfile = new File(this.getSettingsFilePath());
			
		    if(!sfile.exists())
            {
				System.out.println(
						methodName + "::LoadConfiguration settings file not exists:" + 
						this.getSettingsFilePath());
				
				this.setLcRes(cRes = false);
            }
			 
			return cRes;
		 }
		 catch( Exception e)
		 {
			 this.setLcRes( cRes = false );
			 
			 return cRes;
		 }
	}
	/*======================================================================*/
	public boolean setConfigFolder(String configFolder)
	{
		 boolean cRes = true;
			
		 try
		 {
			 this.setConfigFolderPath(configFolder);  
			 
			 if ( this.getConfigFolderPath() == null)
			 {
				 cRes = false;
			 }
			 else
			 {
				 if ( StringUtils.isBlank(this.getConfigFolderPath()) )
				 {
					 cRes = false;
				 }
			 }
			 
			 return cRes;
		 }
		 catch( Exception e)
		 {
			 this.setLcRes( cRes = false );
			 
			 return cRes;
		 }
	}
	/*-----------------------------------------------------*/
	public boolean getReflectionData()
	{
	 boolean cRes = true;
	
	 try
	 {
		 className = this.iReflection.getClassName();
		 
		 if ( className == null ) 
		 { 
			 cRes = false; 
		 }
		 
		 return cRes;
	 }
	 catch( Exception e)
	 {
		 return false;
	 }
	 finally{}
	}
	/*--------------------------------------------------------*/
    public boolean setJarSettingsFile()
    {
    	boolean cRes = true;
		
    	String methodName = "";
    		
        try
        {
        	methodName = this.iReflection.getMethodName();
			
            this.setSettingsFilePath(ToolkitConstants.TOOLKIT_SETTINGS_FILE_JAR);
			
            return cRes;
        }
        catch (Exception e)
        {
        	System.out.println(methodName + "::" + e.getMessage());
            
        	this.setLcRes(cRes = false);
        	
            return cRes;
        }
    }
	/*-----------------------------------------------------*/
    public CommonSettings getcSettings() 
    { 
    	return this.cSettings; 
    }
    /*-----------------------------------------------------*/
    public SQLSettings getSQLSettings() 
    { 
    	return this.sqlSettings; 
    }
    /*-----------------------------------------------------*/
    public DatabaseSettings getDatabaseSettings() 
    { 
    	return this.dbSettings; 
    }
    /*-----------------------------------------------------*/
    public RemotingSettings getRemotingSettings() 
    { 
    	return this.remSettings; 
    }
    /*===============End StockMarketSettings=================*/
	
    /*===============Start RemotingSettings====================*/
	public class RemotingSettings
	{
	 private ToolkitReflection iReflection = null;
	 private String className   = null;
     private String classNameL  = null;
     
     public String  Policy 	    = null;
     public int     Port              ;
     public String  Codebase 	= null;
     public String  Hostname    = null;
     
     /*-----------------------------------------------------*/
     public RemotingSettings()
     {
      try
      {
     	 this.iReflection = new ToolkitReflection();	
          
          this.initSettings();	 	
      }
      catch( Exception e )
      {
     	 
      }
      finally {}
     }
     /*-----------------------------------------------------*/
     public boolean initSettings()
     {
         try
         {
             this.Hostname = "localhost";
             this.Port     = 1099;
             this.Codebase = ToolkitConstants.DEFAULT_STR;
             this.Policy   = ToolkitConstants.DEFAULT_STR;
             
             return true;
         }
         catch (Exception e)
         {
             return false;
         }
         finally
         {
         }
     }
     /*-----------------------------------------------------*/
     public boolean getReflectionData()
 	 {
 	  boolean cRes = true;
 	
 	  try
 	  {
 		 className  = this.iReflection.getClassName();
 		 if ( className == null ) { cRes = false; }
 		
 		 classNameL = getClass().getSimpleName();
 		 
 		 return cRes;
 	  }
 	  catch( Exception e)
 	  {
 		 return false;
 	  }
 	  finally{}
 	 }
     /*-----------------------------------------------------*/
 	 public boolean setSettings(ToolkitXML iXML)
	  	{
			 boolean cRes = true;
			 
			 Node  cNode  = null;
			 Field cField = null;
			 
			 try
			 {
				 //String methodName = this.iReflection.getMethodName();
				 
				 if( iXML == null )
				 {
					 cRes = false;
					 return cRes;
				 }
				 
				 if ( !this.getReflectionData())
				 {
					 cRes = false;
					 return cRes;
				 }
				
				 Node settNode = iXML.getXMLNode(this.classNameL);
				 if ( settNode == null)
				 {
				  cRes = false;
				  return cRes;
				 }
				 
				 NodeList cNodes = settNode.getChildNodes();
				 Field[] cFields = getClass().getDeclaredFields();
				 
				 for( int index = 0; index < cNodes.getLength(); ++index)
				 {
					 for( int jondex = 0; jondex < cFields.length; ++jondex )
					 {
						 cNode  = (Node)cNodes.item(index);
						 cField = (Field)cFields[jondex];
						 
						 if ( (cField.getName()).equals(cNode.getNodeName()) )
						 {
							  String textContent = cNode.getTextContent(); 
							  Type type = (Type) cField.getGenericType();
							  	
							  if ( type.equals(String.class ))
							  {
							   cField.set(this, textContent);
							  }
							  else if ( type.equals(boolean.class ))
							  {
							   boolean cBoolSet = Boolean.parseBoolean(textContent);
							   cField.setBoolean(this, cBoolSet);	
							  }
							  else if ( type.equals(int.class ))
							  {
							   int cIntSet = Integer.parseInt(textContent);
							   cField.setInt(this, cIntSet);	
							  }
						 }
					 }
				 }
				 return cRes;
			 }
			 catch( Exception e)
			 {
				 return false;
			 }
			 finally{}
		}
 	   /*-----------------------------------------------------*/
	   public String getCodebase() {
		return Codebase;
	   }
	   public void setCodebase(String codebase) {
		Codebase = codebase;
	   }
	   public String getHostname() {
		return Hostname;
	   }
	   public void setHostname(String hostname) {
		Hostname = hostname;
	   }
	   public String getPolicy() {
		return Policy;
	   }
	   public void setPolicy(String policy) {
		Policy = policy;
	   }
	   public int getPort() {
		return Port;
	   }
	   public void setPort(int port) {
		Port = port;
	   }
 	   
	}
    /*===============End RemotingSettings======================*/
	
	
	public class HibernateSettings
	{
		protected ToolkitReflection iReflection = null;
        protected String     className   = "";
        protected String     classNameL  = "";
        
		public String hibernateArchiveAutodetection;
		public String hibernateSessionFactory;
		public String hibernateJndiUrl;
		public String hibernateJndiClass;
		public String hibernateDialect;
		public String hibernateConnectionDatasource;
		public String hibernateOrderUpdates;
		
		public String hibernateCacheProviderClass;
		public String hibernateTransactionFactoryClass;
		public String hibernateCurrentSessionContextClass;
		public String hibernateConnectionDriverClass;
		public String hibernateConnectionUrl;
		public String hibernateConnectionUsername;
		public String hibernateConnectionPassword;
		/*-----------------------------------------------------*/
        public HibernateSettings()
        {
         try
         {
        	 this.iReflection = new ToolkitReflection();	
             
             this.initSettings();	 	
         }
         catch( Exception e )
         {
        	 
         }
         finally {}
        }
        /*-----------------------------------------------------*/
        public boolean initSettings()
        {
            try
            {
            	this.hibernateArchiveAutodetection = ToolkitConstants.DEFAULT_STR;
            	this.hibernateSessionFactory       = ToolkitConstants.DEFAULT_STR;
            	this.hibernateJndiUrl              = ToolkitConstants.DEFAULT_STR;
            	this.hibernateJndiClass            = ToolkitConstants.DEFAULT_STR;
            	this.hibernateDialect              = ToolkitConstants.DEFAULT_STR;
            	this.hibernateConnectionDatasource = ToolkitConstants.DEFAULT_STR;
            	this.hibernateOrderUpdates         = ToolkitConstants.DEFAULT_STR;

            	this.hibernateCacheProviderClass         = ToolkitConstants.DEFAULT_STR;
            	this.hibernateTransactionFactoryClass    = ToolkitConstants.DEFAULT_STR;
            	this.hibernateCurrentSessionContextClass = ToolkitConstants.DEFAULT_STR;
            	this.hibernateConnectionDriverClass      = ToolkitConstants.DEFAULT_STR;
            	this.hibernateConnectionUrl 			 = ToolkitConstants.DEFAULT_STR;
            	this.hibernateConnectionUsername 		 = ToolkitConstants.DEFAULT_STR;
            	this.hibernateConnectionPassword 		 = ToolkitConstants.DEFAULT_STR;
        		
                return true;
            }
            catch (Exception e)
            {
                return false;
            }
            finally
            {
            }
        }
        /*-----------------------------------------------------*/
        public boolean getReflectionData()
    	{
    	 boolean cRes = true;
    	
    	 try
    	 {
    		 className  = this.iReflection.getClassName();
    		 if ( className == null ) { cRes = false; }
    		 
    		 classNameL = getClass().getSimpleName();
    		 
    		 return cRes;
    	 }
    	 catch( Exception e)
    	 {
    		 return false;
    	 }
    	 finally{}
    	} 
        /*-----------------------------------------------------*/
    	public boolean setSettings(ToolkitXML iXML)
		{
		 boolean cRes = true;
		 Node  cNode  = null;
		 Field cField = null;
		 
		 try
		 {
			 if( iXML == null )
			 {
				 cRes = false;
				 return cRes;
			 }
			 
			 if ( !this.getReflectionData())
			 {
				 cRes = false;
				 return cRes;
			 }
			
			 Node settNode = iXML.getXMLNode(this.classNameL);
			 if ( settNode == null)
			 {
			  cRes = false;
			  return cRes;
			 }
			 
			 NodeList cNodes = settNode.getChildNodes();
			 Field[] cFields = getClass().getDeclaredFields();
			 
			 for( int index = 0; index < cNodes.getLength(); ++index)
			 {
				 for( int jondex = 0; jondex < cFields.length; ++jondex )
				 {
					 cNode  = (Node)cNodes.item(index);
					 cField = (Field)cFields[jondex];
					 
					 if ( (cField.getName()).equals(cNode.getNodeName()) )
					 {
					  String textContent = cNode.getTextContent(); 
					  Type type = (Type) cField.getGenericType();
					  	
					  if ( type.equals(String.class ))
					  {
					   cField.set(this, textContent);
					  }
					  else if ( type.equals(boolean.class ))
					  {
					   boolean cBoolSet = Boolean.parseBoolean(textContent);
					   cField.setBoolean(this, cBoolSet);	
					  }
					  else if ( type.equals(int.class ))
					  {
					   int cIntSet = Integer.parseInt(textContent);
					   cField.setInt(this, cIntSet);	
					  }
					 }
				 }
			 }
			 return cRes;
		 }
		 catch( Exception e)
		 {
			 return false;
		 }
		 finally{}
		}
        /*-----------------------------------------------------*/
		public ToolkitReflection getiReflection() {
			return iReflection;
		}
		public void setiReflection(ToolkitReflection iReflection) {
			this.iReflection = iReflection;
		}
		public String getClassName() {
			return className;
		}
		public void setClassName(String className) {
			this.className = className;
		}
		public String getClassNameL() {
			return classNameL;
		}
		public void setClassNameL(String classNameL) {
			this.classNameL = classNameL;
		}
		public String getHibernateArchiveAutodetection() {
			return hibernateArchiveAutodetection;
		}
		public void setHibernateArchiveAutodetection(
				String hibernateArchiveAutodetection) {
			this.hibernateArchiveAutodetection = hibernateArchiveAutodetection;
		}
		public String getHibernateSessionFactory() {
			return hibernateSessionFactory;
		}
		public void setHibernateSessionFactory(String hibernateSessionFactory) {
			this.hibernateSessionFactory = hibernateSessionFactory;
		}
		public String getHibernateJndiUrl() {
			return hibernateJndiUrl;
		}
		public void setHibernateJndiUrl(String hibernateJndiUrl) {
			this.hibernateJndiUrl = hibernateJndiUrl;
		}
		public String getHibernateJndiClass() {
			return hibernateJndiClass;
		}
		public void setHibernateJndiClass(String hibernateJndiClass) {
			this.hibernateJndiClass = hibernateJndiClass;
		}
		public String getHibernateDialect() {
			return hibernateDialect;
		}
		public void setHibernateDialect(String hibernateDialect) {
			this.hibernateDialect = hibernateDialect;
		}
		public String getHibernateConnectionDatasource() {
			return hibernateConnectionDatasource;
		}
		public void setHibernateConnectionDatasource(
				String hibernateConnectionDatasource) {
			this.hibernateConnectionDatasource = hibernateConnectionDatasource;
		}
		public String getHibernateOrderUpdates() {
			return hibernateOrderUpdates;
		}
		public void setHibernateOrderUpdates(String hibernateOrderUpdates) {
			this.hibernateOrderUpdates = hibernateOrderUpdates;
		}
		/**
		 * @return the hibernateCacheProviderClass
		 */
		public String getHibernateCacheProviderClass() {
			return hibernateCacheProviderClass;
		}
		/**
		 * @param hibernateCacheProviderClass the hibernateCacheProviderClass to set
		 */
		public void setHibernateCacheProviderClass(String hibernateCacheProviderClass) {
			this.hibernateCacheProviderClass = hibernateCacheProviderClass;
		}
		/**
		 * @return the hibernateTransactionFactoryClass
		 */
		public String getHibernateTransactionFactoryClass() {
			return hibernateTransactionFactoryClass;
		}
		/**
		 * @param hibernateTransactionFactoryClass the hibernateTransactionFactoryClass to set
		 */
		public void setHibernateTransactionFactoryClass(
				String hibernateTransactionFactoryClass) {
			this.hibernateTransactionFactoryClass = hibernateTransactionFactoryClass;
		}
		/**
		 * @return the hibernateCurrentSessionContextClass
		 */
		public String getHibernateCurrentSessionContextClass() {
			return hibernateCurrentSessionContextClass;
		}
		/**
		 * @param hibernateCurrentSessionContextClass the hibernateCurrentSessionContextClass to set
		 */
		public void setHibernateCurrentSessionContextClass(
				String hibernateCurrentSessionContextClass) {
			this.hibernateCurrentSessionContextClass = hibernateCurrentSessionContextClass;
		}
		/**
		 * @return the hibernateConnectionDriverClass
		 */
		public String getHibernateConnectionDriverClass() {
			return hibernateConnectionDriverClass;
		}
		/**
		 * @param hibernateConnectionDriverClass the hibernateConnectionDriverClass to set
		 */
		public void setHibernateConnectionDriverClass(
				String hibernateConnectionDriverClass) {
			this.hibernateConnectionDriverClass = hibernateConnectionDriverClass;
		}
		/**
		 * @return the hibernateConnectionUrl
		 */
		public String getHibernateConnectionUrl() {
			return hibernateConnectionUrl;
		}
		/**
		 * @param hibernateConnectionUrl the hibernateConnectionUrl to set
		 */
		public void setHibernateConnectionUrl(String hibernateConnectionUrl) {
			this.hibernateConnectionUrl = hibernateConnectionUrl;
		}
		/**
		 * @return the hibernateConnectionUsername
		 */
		public String getHibernateConnectionUsername() {
			return hibernateConnectionUsername;
		}
		/**
		 * @param hibernateConnectionUsername the hibernateConnectionUsername to set
		 */
		public void setHibernateConnectionUsername(String hibernateConnectionUsername) {
			this.hibernateConnectionUsername = hibernateConnectionUsername;
		}
		/**
		 * @return the hibernateConnectionPassword
		 */
		public String getHibernateConnectionPassword() {
			return hibernateConnectionPassword;
		}
		/**
		 * @param hibernateConnectionPassword the hibernateConnectionPassword to set
		 */
		public void setHibernateConnectionPassword(String hibernateConnectionPassword) {
			this.hibernateConnectionPassword = hibernateConnectionPassword;
		}
    	
		
	}
	
	
    /*===============Start DatabaseSettings====================*/
	public interface DatabaseSettingsI
	{
		public boolean initSettings();
		
		public boolean getReflectionData();
		
		public boolean setSettings(ToolkitXML iXML);
	}
	
	public class DatabaseSettings implements DatabaseSettingsI
	{
		protected ToolkitReflection iReflection = null;
        protected String     className   = "";
        protected String     classNameL  = "";
        
		public String  Server 	      	  = ToolkitConstants.DEFAULT_STR;
		public String  Port           	  = ToolkitConstants.DEFAULT_STR;
        public String  Database  	  	  = ToolkitConstants.DEFAULT_STR;
        public String  UserID 		  	  = ToolkitConstants.DEFAULT_STR;
        public String  PWD 			  	  = ToolkitConstants.DEFAULT_STR;
        public String  Authentication     = ToolkitConstants.DEFAULT_STR;
        public String  JDBCConnectionPool = ToolkitConstants.DEFAULT_STR;
        public String  JDBCResource       = ToolkitConstants.DEFAULT_STR;   
        public String  JDBCUrl            = ToolkitConstants.DEFAULT_STR;
        public String  JDBCDriverName     = ToolkitConstants.DEFAULT_STR;
        public boolean IsUpperCase        = false;
        public String  dbsDataType        = ToolkitConstants.AMP_DB_TYPE_MYSQL;
        
        /*-----------------------------------------------------*/
        public DatabaseSettings()
        {
	         try
	         {
	        	 this.iReflection = new ToolkitReflection();	
	             
	             this.initSettings();	 	
	         }
	         catch( Exception e )
	         {
	        	 
	         }
	         finally {}
        }
        
        public DatabaseSettings(ToolkitXML iXML)
		{
			try
			{
				this.iReflection = new ToolkitReflection();	
				
				this.setSettings(iXML);
			}
			catch( Exception e)
			{
				
			}
		}
        /*-----------------------------------------------------*/
        public boolean initSettings()
        {
            try
            {
                this.Server         = ToolkitConstants.DEFAULT_STR;
                this.Database       = ToolkitConstants.DEFAULT_STR;
                this.UserID         = ToolkitConstants.DEFAULT_STR;
                this.PWD            = ToolkitConstants.DEFAULT_STR;
                this.Authentication = ToolkitConstants.DEFAULT_STR;
                this.JDBCConnectionPool = ToolkitConstants.DEFAULT_STR;
                this.JDBCResource       = ToolkitConstants.DEFAULT_STR; 
                this.JDBCUrl            = ToolkitConstants.DEFAULT_STR;
                this.JDBCDriverName     = ToolkitConstants.DEFAULT_STR;
                this.IsUpperCase    	= false;

                return true;
            }
            catch (Exception e)
            {
                return false;
            }
            finally
            {
            }
        }
        /*-----------------------------------------------------*/
        public boolean getReflectionData()
    	{
    	 boolean cRes = true;
    	
    	 try
    	 {
    		 className  = this.iReflection.getClassName();
    		 if ( className == null ) { cRes = false; }
    		 
    		 classNameL = getClass().getSimpleName();
    		 
    		 return cRes;
    	 }
    	 catch( Exception e)
    	 {
    		 return false;
    	 }
    	 finally{}
    	} 
        /*-----------------------------------------------------*/
    	public boolean setSettings(ToolkitXML iXML)
		{
		 boolean cRes = true;
		 Node  cNode  = null;
		 Field cField = null;
		 
		 try
		 {
			 //String methodName = this.iReflection.getMethodName();
			 
			 if( iXML == null )
			 {
				 cRes = false;
				 return cRes;
			 }
			 
			 if ( !this.getReflectionData())
			 {
				 cRes = false;
				 return cRes;
			 }
			
			 Node settNode = iXML.getXMLNode(this.classNameL);
			 if ( settNode == null)
			 {
			  cRes = false;
			  return cRes;
			 }
			 
			 NodeList cNodes = settNode.getChildNodes();
			 Field[] cFields = getClass().getDeclaredFields();
			 
			 for( int index = 0; index < cNodes.getLength(); ++index)
			 {
				 for( int jondex = 0; jondex < cFields.length; ++jondex )
				 {
					 cNode  = (Node)cNodes.item(index);
					 cField = (Field)cFields[jondex];
					 
					 if ( (cField.getName()).equals(cNode.getNodeName()) )
					 {
						  String textContent = cNode.getTextContent(); 
						  Type type = (Type) cField.getGenericType();
						  	
						  if ( type.equals(String.class ))
						  {
						   cField.set(this, textContent);
						  }
						  else if ( type.equals(boolean.class ))
						  {
						   boolean cBoolSet = Boolean.parseBoolean(textContent);
						   cField.setBoolean(this, cBoolSet);	
						  }
						  else if ( type.equals(int.class ))
						  {
						   int cIntSet = Integer.parseInt(textContent);
						   cField.setInt(this, cIntSet);	
						  }
					 }
				 }
			 }
			 return cRes;
		 }
		 catch( Exception e)
		 {
			 return false;
		 }
		 finally{}
		}
    	/*-----------------------------------------------------*/
    	
		public String getAuthentication() {
			return Authentication;
		}
		public String getDbsDataType() {
			return dbsDataType;
		}

		public void setDbsDataType(String dbsDataType) {
			this.dbsDataType = dbsDataType;
		}

		public void setAuthentication(String authentication) {
			Authentication = authentication;
		}
		public String getClassName() {
			return className;
		}
		public void setClassName(String className) {
			this.className = className;
		}
		public String getClassNameL() {
			return classNameL;
		}
		public void setClassNameL(String classNameL) {
			this.classNameL = classNameL;
		}
		public String getDatabase() {
			return Database;
		}
		public void setDatabase(String database) {
			Database = database;
		}
		public ToolkitReflection getIReflection() {
			return iReflection;
		}
		public void setIReflection(ToolkitReflection reflection) {
			iReflection = reflection;
		}
		public boolean isUpperCase() {
			return IsUpperCase;
		}
		public void setUpperCase(boolean isUpperCase) {
			IsUpperCase = isUpperCase;
		}
		public String getPWD() {
			return PWD;
		}
		public void setPWD(String pwd) {
			PWD = pwd;
		}
		public String getServer() {
			return Server;
		}
		public void setServer(String server) {
			Server = server;
		}
		public String getUserID() {
			return UserID;
		}
		public void setUserID(String userID) {
			UserID = userID;
		}
		public String getPort() {
			return Port;
		}
		public void setPort(String port) {
			Port = port;
		}
		public String getJDBCConnectionPool() {
			return JDBCConnectionPool;
		}
		public void setJDBCConnectionPool(String jDBCConnectionPool) {
			JDBCConnectionPool = jDBCConnectionPool;
		}
		public String getJDBCResource() {
			return JDBCResource;
		}
		public void setJDBCResource(String jDBCResource) {
			JDBCResource = jDBCResource;
		}
		public boolean isIsUpperCase() {
			return IsUpperCase;
		}
		public void setIsUpperCase(boolean isUpperCase) {
			IsUpperCase = isUpperCase;
		}
		public String getJDBCUrl() {
			return JDBCUrl;
		}
		public void setJDBCUrl(String jDBCUrl) {
			JDBCUrl = jDBCUrl;
		}
		public String getJDBCDriverName() {
			return JDBCDriverName;
		}
		public void setJDBCDriverName(String jDBCDriverName) {
			JDBCDriverName = jDBCDriverName;
		}
		
	}
	
	public class MySQLDatabaseSettings extends DatabaseSettings
	{
		public MySQLDatabaseSettings(ToolkitXML iXML)
		{
			super(iXML);
			
			try
			{
				
			}
			catch( Exception e)
			{
				
			}
		}
		/*-----------------------------------------------------*/
    	public boolean setSettings(ToolkitXML iXML)
		{
			 try
			 {
				 super.setSettings(iXML);
				 
				 return true;
			 }
			 catch( Exception e)
			 {
				 return false;
			 }
			 finally{}
		}
	}
	/*===============End DatabaseSettings======================*/
	
	/*===============Start LoggerSettings======================*/
	public class LoggerSettings
	{
		private String log4JConfigFile = "";
		
		private String logFolder = "";
		
		private String eventSource;
        
        private String logFileName;
        
        private String logNameProblems;
        
        private String     className   = "";
        
        private String     classNameL  = "";
        
		private ToolkitReflection iReflection = null;
        
		/**
		 * @return the log4JConfigFile
		 */
		public String getLog4JConfigFile() {
			return log4JConfigFile;
		}
		/**
		 * @param log4jConfigFile the log4JConfigFile to set
		 */
		public void setLog4JConfigFile(String log4jConfigFile) {
			log4JConfigFile = log4jConfigFile;
		}
		/**
		 * @return the logFolder
		 */
		public String getLogFolder() {
			return logFolder;
		}
		/**
		 * @param logFolder the logFolder to set
		 */
		public void setLogFolder(String logFolder) {
			this.logFolder = logFolder;
		}
		
		/**
		 * @return the eventSource
		 */
		public String getEventSource() {
			return eventSource;
		}
		/**
		 * @param eventSource the eventSource to set
		 */
		public void setEventSource(String eventSource) {
			this.eventSource = eventSource;
		}
		/**
		 * @return the logFileName
		 */
		public String getLogFileName() {
			return logFileName;
		}
		/**
		 * @param logFileName the logFileName to set
		 */
		public void setLogFileName(String logFileName) {
			this.logFileName = logFileName;
		}
		/**
		 * @return the logNameProblems
		 */
		public String getLogNameProblems() {
			return logNameProblems;
		}
		/**
		 * @param logNameProblems the logNameProblems to set
		 */
		public void setLogNameProblems(String logNameProblems) {
			this.logNameProblems = logNameProblems;
		}
		/*-----------------------------------------------------*/
        public LoggerSettings()
        {
         try
         {
        	 this.iReflection = new ToolkitReflection();	
             
             this.initSettings();	 	
         }
         catch( Exception e )
         {
        	 
         }
         finally {}
        }
		/*-----------------------------------------------------*/
		public boolean initSettings()
        {
            try
            {
                this.log4JConfigFile = ToolkitConstants.DEFAULT_STR;
                
                this.logFolder = ToolkitConstants.DEFAULT_STR;
                
                return true;
            }
            catch (Exception e)
            {
                return false;
            }
            finally {}
        }
        /*-----------------------------------------------------*/
		
    	public boolean getReflectionData()
    	{
    	 boolean cRes = true;
    	
    	 try
    	 {
    		 className  = this.iReflection.getClassName();
    		 if ( className == null ) { cRes = false; }
    		
    		 classNameL = getClass().getSimpleName();
    		 
    		 return cRes;
    	 }
    	 catch( Exception e)
    	 {
    		 return false;
    	 }
    	 finally{}
    	} 
		/*-----------------------------------------------------*/
    	public boolean setSettings(ToolkitXML iXML)
		{
		 boolean cRes = true;
		 Node  cNode  = null;
		 Field cField = null;
		 
		 try
		 {
			 //String methodName = this.iReflection.getMethodName();
			 
			 if( iXML == null )
			 {
				 cRes = false;
				 return cRes;
			 }
			 
			 if ( !this.getReflectionData())
			 {
				 cRes = false;
				 return cRes;
			 }
			
			 Node settNode = iXML.getXMLNode(this.classNameL);
			 if ( settNode == null)
			 {
			  cRes = false;
			  return cRes;
			 }
			 
			 NodeList cNodes = settNode.getChildNodes();
			 Field[] cFields = getClass().getDeclaredFields();
			 
			 for( int index = 0; index < cNodes.getLength(); ++index)
			 {
				 for( int jondex = 0; jondex < cFields.length; ++jondex )
				 {
					 cNode  = (Node)cNodes.item(index);
					 cField = (Field)cFields[jondex];
					 
					 if ( (cField.getName()).equals(cNode.getNodeName()) )
					 {
					  String textContent = cNode.getTextContent(); 
					  Type type = (Type) cField.getGenericType();
					  	
					  if ( type.equals(String.class ))
					  {
					   cField.set(this, textContent);
					  }
					  else if ( type.equals(boolean.class ))
					  {
					   boolean cBoolSet = Boolean.parseBoolean(textContent);
					   cField.setBoolean(this, cBoolSet);	
					  }
					  else if ( type.equals(int.class ))
					  {
					   int cIntSet = Integer.parseInt(textContent);
					   cField.setInt(this, cIntSet);	
					  }
					 }
				 }
			 }
			 return cRes;
		 }
		 catch( Exception e)
		 {
			 return false;
		 }
		 finally{}
		}
    	/*-----------------------------------------------------*/
    	
		public String getClassName() {
			return className;
		}
		public void setClassName(String className) {
			this.className = className;
		}
		public String getClassNameL() {
			return classNameL;
		}
		public void setClassNameL(String classNameL) {
			this.classNameL = classNameL;
		}
		public ToolkitReflection getIReflection() {
			return iReflection;
		}
		public void setIReflection(ToolkitReflection reflection) {
			iReflection = reflection;
		}
		
	}
	/*===============End LoggerSettings========================*/
	
    /*===============Start SQLSettings=========================*/
	public class SQLSettings
	{
		private String sqlFile;
		
		private ToolkitReflection iReflection = null;
        private String className  = "";
        private String classNameL = "";
        
        /*-----------------------------------------------------*/
        public SQLSettings()
        {
         try
         {
        	 this.iReflection = new ToolkitReflection();	
             
             this.initSettings();	 	
         }
         catch( Exception e )
         {
        	 
         }
         finally {}
        }
		/*-----------------------------------------------------*/
		public boolean initSettings()
        {
            try
            {
                this.sqlFile = ToolkitConstants.DEFAULT_STR;
                
                return true;
            }
            catch (Exception e)
            {
                return false;
            }
            finally {}
        }
        /*-----------------------------------------------------*/
		
    	public boolean getReflectionData()
    	{
    	 boolean cRes = true;
    	
    	 try
    	 {
    		 className  = this.iReflection.getClassName();
    		 if ( className == null ) { cRes = false; }
    		
    		 classNameL = getClass().getSimpleName();
    		 
    		 return cRes;
    	 }
    	 catch( Exception e)
    	 {
    		 return false;
    	 }
    	 finally{}
    	} 
		/*-----------------------------------------------------*/
    	public boolean setSettings(ToolkitXML iXML)
		{
		 boolean cRes = true;
		 Node  cNode  = null;
		 Field cField = null;
		 
		 try
		 {
			 //String methodName = this.iReflection.getMethodName();
			 
			 if( iXML == null )
			 {
				 cRes = false;
				 return cRes;
			 }
			 
			 if ( !this.getReflectionData())
			 {
				 cRes = false;
				 return cRes;
			 }
			
			 Node settNode = iXML.getXMLNode(this.classNameL);
			 if ( settNode == null)
			 {
			  cRes = false;
			  return cRes;
			 }
			 
			 NodeList cNodes = settNode.getChildNodes();
			 Field[] cFields = getClass().getDeclaredFields();
			 
			 for( int index = 0; index < cNodes.getLength(); ++index)
			 {
				 for( int jondex = 0; jondex < cFields.length; ++jondex )
				 {
					 cNode  = (Node)cNodes.item(index);
					 cField = (Field)cFields[jondex];
					 
					 if ( (cField.getName()).equals(cNode.getNodeName()) )
					 {
					  String textContent = cNode.getTextContent(); 
					  Type type = (Type) cField.getGenericType();
					  	
					  if ( type.equals(String.class ))
					  {
					   cField.set(this, textContent);
					  }
					  else if ( type.equals(boolean.class ))
					  {
					   boolean cBoolSet = Boolean.parseBoolean(textContent);
					   cField.setBoolean(this, cBoolSet);	
					  }
					  else if ( type.equals(int.class ))
					  {
					   int cIntSet = Integer.parseInt(textContent);
					   cField.setInt(this, cIntSet);	
					  }
					 }
				 }
			 }
			 return cRes;
		 }
		 catch( Exception e)
		 {
			 return false;
		 }
		 finally{}
		}
    	/*-----------------------------------------------------*/
    	
		public String getClassName() {
			return className;
		}
		public void setClassName(String className) {
			this.className = className;
		}
		public String getClassNameL() {
			return classNameL;
		}
		public void setClassNameL(String classNameL) {
			this.classNameL = classNameL;
		}
		public ToolkitReflection getIReflection() {
			return iReflection;
		}
		public void setIReflection(ToolkitReflection reflection) {
			iReflection = reflection;
		}
		public String getSqlFile() {
			return sqlFile;
		}
		public void setSqlFile(String sqlFile) {
			this.sqlFile = sqlFile;
		}
	}
	/*===============End SQLSettings===========================*/
	
	/*===============Start JMSSettings=========================*/
	public class JMSSettings
	{
		public String JMSConnectionFactory;
		public String JMSDestinationQueue;
		
		private ToolkitReflection iReflection = null;
        private String className  = "";
        private String classNameL = "";
        
        /*-----------------------------------------------------*/
        public JMSSettings()
        {
         try
         {
        	 this.iReflection = new ToolkitReflection();	
             
             this.initSettings();	 	
         }
         catch( Exception e )
         {
        	 
         }
         finally {}
        }
		/*-----------------------------------------------------*/
		public boolean initSettings()
        {
            try
            {
                this.JMSDestinationQueue  = ToolkitConstants.DEFAULT_STR;
                this.JMSConnectionFactory = ToolkitConstants.DEFAULT_STR;
                
                return true;
            }
            catch (Exception e)
            {
                return false;
            }
            finally {}
        }
        /*-----------------------------------------------------*/
		
    	public boolean getReflectionData()
    	{
    	 boolean cRes = true;
    	
    	 try
    	 {
    		 className  = this.iReflection.getClassName();
    		 if ( className == null ) { cRes = false; }
    		
    		 classNameL = getClass().getSimpleName();
    		 
    		 return cRes;
    	 }
    	 catch( Exception e)
    	 {
    		 return false;
    	 }
    	 finally{}
    	} 
		/*-----------------------------------------------------*/
    	public boolean setSettings(ToolkitXML iXML)
		{
		 boolean cRes = true;
		 Node  cNode  = null;
		 Field cField = null;
		 
		 try
		 {
			 //String methodName = this.iReflection.getMethodName();
			 
			 if( iXML == null )
			 {
				 cRes = false;
				 return cRes;
			 }
			 
			 if ( !this.getReflectionData())
			 {
				 cRes = false;
				 return cRes;
			 }
			
			 Node settNode = iXML.getXMLNode(this.classNameL);
			 if ( settNode == null)
			 {
			  cRes = false;
			  return cRes;
			 }
			 
			 NodeList cNodes = settNode.getChildNodes();
			 Field[] cFields = getClass().getDeclaredFields();
			 
			 for( int index = 0; index < cNodes.getLength(); ++index)
			 {
				 for( int jondex = 0; jondex < cFields.length; ++jondex )
				 {
					 cNode  = (Node)cNodes.item(index);
					 cField = (Field)cFields[jondex];
					 
					 if ( (cField.getName()).equals(cNode.getNodeName()) )
					 {
					  String textContent = cNode.getTextContent(); 
					  Type type = (Type) cField.getGenericType();
					  	
					  if ( type.equals(String.class ))
					  {
					   cField.set(this, textContent);
					  }
					  else if ( type.equals(boolean.class ))
					  {
					   boolean cBoolSet = Boolean.parseBoolean(textContent);
					   cField.setBoolean(this, cBoolSet);	
					  }
					  else if ( type.equals(int.class ))
					  {
					   int cIntSet = Integer.parseInt(textContent);
					   cField.setInt(this, cIntSet);	
					  }
					 }
				 }
			 }
			 return cRes;
		 }
		 catch( Exception e)
		 {
			 return false;
		 }
		 finally{}
		}
    	/*-----------------------------------------------------*/
    	
		public String getClassName() {
			return className;
		}
		public void setClassName(String className) {
			this.className = className;
		}
		public String getClassNameL() {
			return classNameL;
		}
		public void setClassNameL(String classNameL) {
			this.classNameL = classNameL;
		}
		public ToolkitReflection getIReflection() {
			return iReflection;
		}
		public void setIReflection(ToolkitReflection reflection) {
			iReflection = reflection;
		}
		public String getJMSConnectionFactory() {
			return JMSConnectionFactory;
		}
		public void setJMSConnectionFactory(String jMSConnectionFactory) {
			JMSConnectionFactory = jMSConnectionFactory;
		}
		public String getJMSDestinationQueue() {
			return JMSDestinationQueue;
		}
		public void setJMSDestinationQueue(String jMSDestinationQueue) {
			JMSDestinationQueue = jMSDestinationQueue;
		}
		
	}
	/*===============End JMS===================================*/
	
	/*===============Start GlassFishSettings=========================*/
	public class GlassFishSettings
	{
		public String ContextFactory;
		public String ProviderURL;
		
		private ToolkitReflection iReflection = null;
        private String className  = "";
        private String classNameL = "";
        
        /*-----------------------------------------------------*/
        public GlassFishSettings()
        {
         try
         {
        	 this.iReflection = new ToolkitReflection();	
             
             this.initSettings();	 	
         }
         catch( Exception e )
         {
        	 
         }
         finally {}
        }
		/*-----------------------------------------------------*/
		public boolean initSettings()
        {
            try
            {
                this.ContextFactory  = ToolkitConstants.DEFAULT_STR;
                this.ProviderURL = ToolkitConstants.DEFAULT_STR;
                
                return true;
            }
            catch (Exception e)
            {
                return false;
            }
            finally {}
        }
        /*-----------------------------------------------------*/
		
    	public boolean getReflectionData()
    	{
    	 boolean cRes = true;
    	
    	 try
    	 {
    		 className  = this.iReflection.getClassName();
    		 if ( className == null ) { cRes = false; }
    		
    		 classNameL = getClass().getSimpleName();
    		 
    		 return cRes;
    	 }
    	 catch( Exception e)
    	 {
    		 return false;
    	 }
    	 finally{}
    	} 
		/*-----------------------------------------------------*/
    	public boolean setSettings(ToolkitXML iXML)
		{
		 boolean cRes = true;
		 Node  cNode  = null;
		 Field cField = null;
		 
		 try
		 {
			 //String methodName = this.iReflection.getMethodName();
			 
			 if( iXML == null )
			 {
				 cRes = false;
				 return cRes;
			 }
			 
			 if ( !this.getReflectionData())
			 {
				 cRes = false;
				 return cRes;
			 }
			
			 Node settNode = iXML.getXMLNode(this.classNameL);
			 if ( settNode == null)
			 {
			  cRes = false;
			  return cRes;
			 }
			 
			 NodeList cNodes = settNode.getChildNodes();
			 Field[] cFields = getClass().getDeclaredFields();
			 
			 for( int index = 0; index < cNodes.getLength(); ++index)
			 {
				 for( int jondex = 0; jondex < cFields.length; ++jondex )
				 {
					 cNode  = (Node)cNodes.item(index);
					 cField = (Field)cFields[jondex];
					 
					 if ( (cField.getName()).equals(cNode.getNodeName()) )
					 {
					  String textContent = cNode.getTextContent(); 
					  Type type = (Type) cField.getGenericType();
					  	
					  if ( type.equals(String.class ))
					  {
					   cField.set(this, textContent);
					  }
					  else if ( type.equals(boolean.class ))
					  {
					   boolean cBoolSet = Boolean.parseBoolean(textContent);
					   cField.setBoolean(this, cBoolSet);	
					  }
					  else if ( type.equals(int.class ))
					  {
					   int cIntSet = Integer.parseInt(textContent);
					   cField.setInt(this, cIntSet);	
					  }
					 }
				 }
			 }
			 return cRes;
		 }
		 catch( Exception e)
		 {
			 return false;
		 }
		 finally{}
		}
    	/*-----------------------------------------------------*/
    	
		public String getClassName() {
			return className;
		}
		public void setClassName(String className) {
			this.className = className;
		}
		public String getClassNameL() {
			return classNameL;
		}
		public void setClassNameL(String classNameL) {
			this.classNameL = classNameL;
		}
		public ToolkitReflection getIReflection() {
			return iReflection;
		}
		public void setIReflection(ToolkitReflection reflection) {
			iReflection = reflection;
		}
		public String getContextFactory() {
			return ContextFactory;
		}
		public void setContextFactory(String contextFactory) {
			ContextFactory = contextFactory;
		}
		public String getProviderURL() {
			return ProviderURL;
		}
		public void setProviderURL(String providerURL) {
			ProviderURL = providerURL;
		}
		
		
	}
	/*===============End GlassFishSettings===========================*/
    
    /*===============Start CommonSettings============================*/
	public class CommonSettings
	{
        private String runFolder  = "";
        private String configFolder  = "";
        
        private boolean isLoadSettingsFromFS  = false;
       
        private ToolkitReflection iReflection = null;
        private String className  = "";
        private String classNameL = "";
        
        
        /**
		 * @return the configFolder
		 */
		public String getConfigFolder() {
			return configFolder;
		}

		/**
		 * @param configFolder the configFolder to set
		 */
		public void setConfigFolder(String configFolder) {
			this.configFolder = configFolder;
		}

		/**
		 * @return the isLoadSettingsFromFS
		 */
		public boolean isLoadSettingsFromFS() {
			return isLoadSettingsFromFS;
		}

		/**
		 * @param isLoadSettingsFromFS the isLoadSettingsFromFS to set
		 */
		public void setLoadSettingsFromFS(boolean isLoadSettingsFromFS) {
			this.isLoadSettingsFromFS = isLoadSettingsFromFS;
		}

		/*-----------------------------------------------------*/
        public CommonSettings()
        {
	         try
	         {
		          this.iReflection = new ToolkitReflection();	
		          
		          this.initSettings();
	         }
	         catch( Exception e ){}
        }
        
        /*-----------------------------------------------------*/
    	public boolean getReflectionData()
    	{
	    	 boolean cRes = true;
	    	
	    	 try
	    	 {
	    		 className  = this.iReflection.getClassName();
	    		 if ( className == null ) { cRes = false; }
	    		 
	    		 classNameL = getClass().getSimpleName();
	    		 
	    		 return cRes;
	    	 }
	    	 catch( Exception e)
	    	 {
	    		 return false;
	    	 }
    	}
    	
        /*-----------------------------------------------------*/
		
		public String getRunFolder()
		{
			return runFolder;
		}
		public void setRunFolder(String runFolder)
		{
			this.runFolder = runFolder;
		}
		
		public String getClassName() {
			return className;
		}

		public void setClassName(String className) {
			this.className = className;
		}

		public String getClassNameL() {
			return classNameL;
		}

		public void setClassNameL(String classNameL) {
			this.classNameL = classNameL;
		}

		

		/*-----------------------------------------------------*/
		public void initSettings()
		{
		 try
		 {
			 this.runFolder = new File(".").getAbsolutePath();
			 
		 }
		 catch( Exception e ){}
		}
		/*-----------------------------------------------------*/
		
		public boolean setSettings(ToolkitXML iXML)
		{
			 boolean cRes = true;
			 Node  cNode  = null;
			 Field cField = null;
			 
			 try
			 {
				 //String methodName = this.iReflection.getMethodName();
				 
				 if( iXML == null )
				 {
					 cRes = false;
					 return cRes;
				 }
				 
				 if ( !this.getReflectionData())
				 {
					 cRes = false;
					 return cRes;
				 }
				
				 Node settNode = iXML.getXMLNode(this.classNameL);
				 if ( settNode == null)
				 {
				  cRes = false;
				  return cRes;
				 }
				 
				 NodeList cNodes = settNode.getChildNodes();
				 
				 Field[] cFields = getClass().getDeclaredFields();
				 
				 for( int index = 0; index < cNodes.getLength(); ++index)
				 {
					 for( int jondex = 0; jondex < cFields.length; ++jondex )
					 {
						 cNode  = (Node)cNodes.item(index);
						 cField = (Field)cFields[jondex];
						 
						 if ( (cField.getName()).equals(cNode.getNodeName()) )
						 {
						  String textContent = cNode.getTextContent(); 
						  Type type = (Type) cField.getGenericType();
						  	
						  if ( type.equals(String.class ))
						  {
						   cField.set(this, textContent);
						  }
						  else if ( type.equals(boolean.class ))
						  {
						   boolean cBoolSet = Boolean.parseBoolean(textContent);
						   cField.setBoolean(this, cBoolSet);	
						  }
						  else if ( type.equals(int.class ))
						  {
						   int cIntSet = Integer.parseInt(textContent);
						   cField.setInt(this, cIntSet);	
						  }
						 }
					 }
				 }
				
				 return cRes;
			 }
			
			 catch( Exception e)
			 {
				 return false;
			 }
		}
	}
	/*------------------------------------------------------------------------------*/
    public boolean freeResources()
    {
        boolean cRes = true;

        try
        {
	    	if ( this.iSettingsXML != null )
	    	{
	    		this.iSettingsXML.freeResources();
	    	}
	    	
    		return cRes;
        }
        catch (Exception e)
        {
            return false;
        }
    }
	/*=========End CommonSettings===========================*/
	
}


