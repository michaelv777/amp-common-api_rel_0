/**
 * 
 */
package amp.common.api.impl;

/**
 * @author michaelv
 *
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

import amp.common.api.impl.ToolkitSettings.DatabaseSettings;
import amp.common.api.impl.ToolkitSettings.HibernateSettings;
import amp.jpa.entities.manager.PersistenceManager;
import oracle.jdbc.pool.OracleDataSource;



public class ToolkitDatabase 
{
	private static Logger cLogger = 
			LoggerFactory.getLogger(ToolkitDatabase.class);
	
	/*-----------Class Variables------------------------------------------------------------*/
	private String     sqlConnectionString = "";
	
	private Connection sqlConnection = null;
    
    private DataSource sqlDataSource = null;
    
    private SessionFactory hbsSessions = null;
	
	private ToolkitSettings   tSettings   = null;
	
	private DatabaseSettings  dbSettings  = null;
	
	private HibernateSettings hbsSettings = null;
    /*----------Reflection Variabsles-------------------------------------------------------*/
    private ToolkitReflection iReflection = null;
    private String className   = null;

    private final String urlOracle = "jdbc:oracle:thin:@";
    
    private final String urlMySQl = "jdbc:mysql://";
    
    private final String urlMsft = "jdbc:microsoft:sqlserver://";
    
    private final String urlJtds = "jdbc:jtds:sqlserver://";
    		
	boolean lcRes = true;
	
	/*-----------Getters/Setters------------------------------------------------------------*/
	public Connection getSqlConnection() {
		return sqlConnection;
	}
	public void setSqlConnection(Connection sqlConnection) {
		this.sqlConnection = sqlConnection;
	}
	public String getSqlConnectionString() {
		return sqlConnectionString;
	}
	public void setSqlConnectionString(String sqlConnectionString) {
		this.sqlConnectionString = sqlConnectionString;
	}
	public boolean isLcRes() {
		return lcRes;
	}
	public void setLcRes(boolean lc_Res) {
		this.lcRes = lc_Res;
	}
	
	public DataSource getSqlDataSource() {
		return sqlDataSource;
	}
	public void setSqlDataSource(DataSource sqlDataSource) {
		this.sqlDataSource = sqlDataSource;
	}
	
	public String getUrl() {
		return urlMsft;
	}
	
	
	public DatabaseSettings getDbSettings() {
		return dbSettings;
	}
	public void setDbSettings(DatabaseSettings dbSettings) {
		this.dbSettings = dbSettings;
	}
	
	
	public HibernateSettings getHbsSettings() {
		return hbsSettings;
	}
	public void setHbsSettings(HibernateSettings hbsSettings) {
		this.hbsSettings = hbsSettings;
	}
	
	
	public ToolkitReflection getiReflection() {
		return iReflection;
	}
	public void setiReflection(ToolkitReflection iReflection) {
		this.iReflection = iReflection;
	}
	
	
	/**
	 * @return the hbsSessions
	 */
	public SessionFactory getHbsSessions() {
		return hbsSessions;
	}
	/**
	 * @param hbsSessions the hbsSessions to set
	 */
	public void setHbsSessions(SessionFactory hbsSessions) {
		this.hbsSessions = hbsSessions;
	}
	/**
	 * @return the hbsSession
	 */
	/*
	public Session getHbsSession() {
		return hbsSession;
	}
	*/
	/**
	 * @param hbsSession the hbsSession to set
	 */
	/*
	public void setHbsSession(Session hbsSession) {
		this.hbsSession = hbsSession;
	}
	*/
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
	 * @return the urlOracle
	 */
	public String getUrlOracle() {
		return urlOracle;
	}
	/**
	 * @return the urlMsft
	 */
	public String getUrlMsft() {
		return urlMsft;
	}
	/**
	 * @return the urlJtds
	 */
	public String getUrlJtds() {
		return urlJtds;
	}
	/*========================================================================================*/
	
	public ToolkitDatabase()
	{
		try
		{
			this.iReflection = new ToolkitReflection();
		}
		catch( Exception e )
		{
			
		}
	}
	/*-----------Class Methods--------------------------------------------------------------*/
	
    public ToolkitDatabase(ToolkitSettings tSettings)
	{
		try
		{
			this.iReflection = new ToolkitReflection();
			
			this.settSettings(tSettings);
			
			this.setLcRes(this.initClassVariables());
		}
		catch( Exception e )
		{
			
		}
	}
	/*==================================================================================*/
    public boolean initClassVariables()
    {
    	String methodName = "";
        
        try
        {
        	this.iReflection = new ToolkitReflection();
        	
        	this.setLcRes(true);
        	
        	methodName = this.iReflection.getMethodName();
        	
        	this.setDbSettings(this.tSettings.getDbSettings());
			
			this.setHbsSettings(this.tSettings.getHbsSettings());
			
			//this.constactConnectionStringOracle(this.getDbSettings());
            
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
    public boolean initClassVariables(DatabaseSettings  dbSettings, 
    								  HibernateSettings hbSettings)
    {
    	String methodName = "";
        
        try
        {
        	this.iReflection = new ToolkitReflection();
        	
        	this.setLcRes(true);
        	
        	methodName = this.iReflection.getMethodName();
        	
        	if ( (null == dbSettings) || ( null == hbSettings) )
        	{
        		cLogger.error(methodName + ":(null == dbSettings) || ( null == hbSettings)");
        		
        		this.setLcRes(false);
        	}
        	
        	if ( this.isLcRes() )
        	{
	        	this.setDbSettings(dbSettings);
				
				this.setHbsSettings(hbSettings);
				
				this.constactConnectionStringOracle(dbSettings);
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
    /*==================================================================================*/
   
    /*========================================================================================*/
	public boolean getReflectionData()
	{
		 boolean cRes = true;
		
		 try
		 {
			 this.className = this.iReflection.getClassName();
			 if ( className == null )
			 { 
				 cRes = false; 
			 }
			
			 return cRes;
		 }
		 catch( Exception e)
		 {
			 cLogger.error(className + ":" + e.getMessage() );
			 
			 return false;
		 }
		 finally{}
	}
	/*========================================================================================*/
	public boolean closeHibernateSession(Session hbsSession)
	{
		boolean cRes = true;
		
		String methodName = "";
			
		 try
		 {
			 this.setLcRes(true);	
			 
			 methodName = this.iReflection.getMethodName();
			 
			 if ( hbsSession != null )
			 {
				 hbsSession.close();
			 }
			 
			 return cRes;
		 }
		 catch( Exception e)
		 {
			 cLogger.error(methodName + ":" + e.getMessage() );
			 
			 return false;
		 }
		 finally{}
	}
	/*========================================================================================*/
	public boolean getHibernateSession(List<Class<? extends Object>> clazzes)
	{
		 String methodName = "";
			
		 try
		 {
			 methodName = this.iReflection.getMethodName();
			 
			 this.hbsSessions = 
					 ToolkitHibernateUtil.getInstance(
							 this.hbsSettings, clazzes).getSessionFactory();

			 return true;
		 }
		 catch( Exception e)
		 {
			 cLogger.error(methodName + ":" + e.getMessage() );
			 
			 this.setLcRes(false);
			 
			 return false;
		 }
		 finally{}
	}
	
	/*========================================================================================*/
	public org.hibernate.Session getHibernateSessionDataSource(List<Class<? extends Object>> clazzes)
	{
		 String methodName = "";
		
		 Session hbsSession  = null;
			
		 try
		 {
			 this.setLcRes(true);
			 
			 methodName = this.iReflection.getMethodName();
			 
			 hbsSession = ToolkitHibernateUtil.getInstance(this.hbsSettings, clazzes).getSession();
			
			 this.hbsSessions = 
					 ToolkitHibernateUtil.getInstance(this.hbsSettings, clazzes).getSessionFactory();
			
			 return hbsSession;
		 }
		 catch( Exception e)
		 {
			 cLogger.error(methodName + ":" + e.getMessage() );
			 
			 this.setLcRes(false);
			 
			 return null;
		 }
		 finally{}
	}
	/*========================================================================================*/
    public void constactConnectionStringJtds(ToolkitSettings.DatabaseSettings dbSettings)
	{
    	String methodName = "";
    		
		try
		{
			this.setLcRes(true);	
			methodName = this.iReflection.getMethodName();
			
			/*-----check if dbSettings-----------------*/
			if ( dbSettings != null )
			{
		
			 /*-----constract connection string--------*/
			 this.sqlConnectionString = this.urlJtds;
			
			 this.sqlConnectionString = this.sqlConnectionString + 
			 dbSettings.Server;
			
			 this.sqlConnectionString = this.sqlConnectionString + 
			 ToolkitConstants.SEMICOLON;
			
			 this.sqlConnectionString = this.sqlConnectionString + 
			 dbSettings.Port;
			
			 this.sqlConnectionString = this.sqlConnectionString + 
			 ToolkitConstants.SLASH;
			
			 this.sqlConnectionString = this.sqlConnectionString + 
			 dbSettings.Database;
			}
			else
			{
				this.sqlConnectionString = "";
				this.setLcRes(false);	
			}
		}
		catch( Exception e )
		{
			cLogger.error( methodName + ":" + e.getMessage() );
		}
	}
    /*========================================================================================*/
    public void constactConnectionStringOracle(ToolkitSettings.DatabaseSettings dbSettings)
	{
    	String methodName = "";
    		
		try
		{
				this.setLcRes(true);	
				methodName = this.iReflection.getMethodName();
				
				/*-----check if dbSettings-----------------*/
				if ( dbSettings != null )
				{
			
				 /*-----constract connection string--------*/
				 this.sqlConnectionString = this.urlOracle;
				
				 this.sqlConnectionString = this.sqlConnectionString + 
				 dbSettings.Server;
				
				 this.sqlConnectionString = this.sqlConnectionString + 
				 ToolkitConstants.SEMICOLON;
				
				 this.sqlConnectionString = this.sqlConnectionString + 
				 dbSettings.Port;
				
				 this.sqlConnectionString = this.sqlConnectionString + 
				 ToolkitConstants.SEMICOLON;
				
				 this.sqlConnectionString = this.sqlConnectionString + 
				 dbSettings.Database;
			}
			else
			{
				this.sqlConnectionString = "";
				this.setLcRes(false);	
			}
		}
		catch( Exception e )
		{
			cLogger.error( methodName + ":" + e.getMessage() );
		}
	}
    /*========================================================================================*/
    public void constactConnectionStringMySQL(ToolkitSettings.DatabaseSettings dbSettings)
	{
    	String methodName = "";
    		
		try
		{
				this.setLcRes(true);	
				methodName = this.iReflection.getMethodName();
				
				/*-----check if dbSettings-----------------*/
				if ( dbSettings != null )
				{
			
				 /*-----constract connection string--------*/
				 this.sqlConnectionString = this.urlMySQl;
				
				 this.sqlConnectionString = this.sqlConnectionString + 
				 dbSettings.Server;
				
				 this.sqlConnectionString = this.sqlConnectionString + 
				 ToolkitConstants.SEMICOLON;
				
				 this.sqlConnectionString = this.sqlConnectionString + 
				 dbSettings.Port;
				
				 this.sqlConnectionString = this.sqlConnectionString + 
				 ToolkitConstants.SLASH;
				
				 this.sqlConnectionString = this.sqlConnectionString + 
				 dbSettings.Database;
			}
			else
			{
				 this.sqlConnectionString = "";
				 this.setLcRes(false);	
			}
		}
		catch( Exception e )
		{
			cLogger.error( methodName + ":" + e.getMessage() );
		}
	}
    /*========================================================================================*/
    public void constactConnectionStringMsft(ToolkitSettings.DatabaseSettings dbSettings)
	{
    	String methodName = "";
    		
		try
		{
				this.setLcRes(true);	
				methodName = this.iReflection.getMethodName();
				
				/*-----check if dbSettings-----------------*/
				if ( dbSettings != null )
				{
			
					 /*-----constract connection string--------*/
					 this.sqlConnectionString = this.urlMsft;
					
					 this.sqlConnectionString = this.sqlConnectionString + 
					 dbSettings.Server;
					
					 this.sqlConnectionString = this.sqlConnectionString + 
					 ToolkitConstants.SEMICOLON;
					
					 this.sqlConnectionString = this.sqlConnectionString + 
					 dbSettings.Port;
					
					 this.sqlConnectionString = this.sqlConnectionString + 
					 ToolkitConstants.PSIKPOINT;
					
					 this.sqlConnectionString = this.sqlConnectionString + 
					 "databaseName=" + dbSettings.Database;
				}
				else
				{
					 cLogger.error( methodName + ":dbSettings is null!" );
					
					 this.setLcRes(false);	
				}
		}
		catch( Exception e )
		{
			 cLogger.error( methodName + ":" + e.getMessage() );
		}
	}
    /*========================================================================================*/
    protected boolean validateConnectionSettings(String dbResource,
    										     String contextFactory,
    										     String providerURL)
    {
    	boolean cRes = true;
    	
    	String methodName = "";
    	
    	try
        {
			methodName = this.iReflection.getMethodName();
			
			if ( "".equals(dbResource ) )
			{
				cLogger.error( methodName + "::dbResource is empty!" );
				cRes = false;
			}
			
			if ( "".equals(contextFactory ) )
			{
				cLogger.error( methodName + "::contextFactory is empty!" );
				cRes = false;
			}
			
			if ( "".equals(providerURL ) )
			{
				cLogger.error( methodName + "::providerURL is empty!" );
				cRes = false;
			}
			
			return cRes;
        }
        catch (Exception e)
        {
        	cLogger.error( methodName + ":" + e.getMessage() );
	        
	   		return false;
        }
    }
    /*========================================================================================*/
	public boolean openSQLConnectionToDatabase(ToolkitSettings.DatabaseSettings dbSettings,
											   ToolkitSettings.GlassFishSettings gfSettings)
    {
    	boolean cRes = true;
    	
    	String methodName = "";
    	
    	try
    	{
    		methodName = this.iReflection.getMethodName();
    		
    		String dbResource     = dbSettings.getJDBCResource();
    		
    		String contextFactory = gfSettings.getContextFactory();
    		
    		String providerURL    = gfSettings.getProviderURL();
    		
    		cRes = this.validateConnectionSettings(dbResource, contextFactory, providerURL);
    		
    		if (  cRes )
    		{
	    		Hashtable<String, String> properties = new Hashtable<String, String>(2);
		         
		        properties.put(Context.INITIAL_CONTEXT_FACTORY, contextFactory);
		         
		        properties.put(Context.PROVIDER_URL, providerURL);
		         
	    		Context jndiContext = new InitialContext(properties);  
	    		
	    		this.setSqlDataSource((DataSource) jndiContext.lookup(dbResource));
	    			
	    		if ( this.getSqlDataSource() != null )
	        	{
	    			this.setSqlConnection(this.getSqlDataSource().getConnection());
	    			
	    			if ( this.getSqlConnection() == null ) 
	    			{
	    				cLogger.error( methodName + "::getSmConnection is null!" );
	    			}
	        	}
    		}
    		return cRes;
    	}
    	catch( SQLException e)
    	{
    		cLogger.error(methodName + "::" + e.getMessage());
    		
    		return false;
    	}
    	catch( Exception e)
    	{
    		 cLogger.error(methodName + "::" + e.getMessage());
    		
    		return false;
    	}
    }
    /*========================================================================================*/
  
    public boolean openSQLConnectionToDatabase(ToolkitSettings.DatabaseSettings dbSettings)
	{
    	String methodName = "";
    	
		try
		{
			this.setLcRes(true);	
			methodName = this.iReflection.getMethodName();
			
			if ( dbSettings.getDbsDataType().equals(ToolkitConstants.AMP_DB_TYPE_ORACLE) )
			{
				OracleDataSource ds = new OracleDataSource();
				ds.setUser(dbSettings.UserID);
				ds.setPassword(dbSettings.PWD);
				ds.setServerName(dbSettings.Server);
				ds.setPortNumber(Integer.parseInt(dbSettings.Port.trim())); 
				ds.setDatabaseName(dbSettings.Database);
				ds.setDriverType("thin");
				
				this.sqlConnection = ds.getConnection();
				//this.sqlConnection.setAutoCommit(true);
				this.sqlConnection.setTransactionIsolation(
							Connection.TRANSACTION_SERIALIZABLE);
			}
			else if (dbSettings.getDbsDataType().equals(ToolkitConstants.AMP_DB_TYPE_MYSQL))
			{
				MysqlConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();
			    ds.setUser(dbSettings.UserID);
			    ds.setPassword(dbSettings.PWD);
			    ds.setServerName(dbSettings.Server);
			    ds.setPort(Integer.valueOf(dbSettings.Port.trim()));
			    ds.setDatabaseName(dbSettings.Database);
			    
			    this.sqlConnection = ds.getConnection();
				//this.sqlConnection.setAutoCommit(true);
				this.sqlConnection.setTransactionIsolation(
							Connection.TRANSACTION_SERIALIZABLE);
			}
		  
			
		  return true;
		}
		catch ( Exception e )
		{
			 this.setLcRes(false);
				
			 cLogger.error( methodName + ":" + e.getMessage() );
			 
	         return false;
		}
		finally{}
	}
    /*========================================================================================*/
    public boolean closeSQLConnectionToDatabase()
	{
    	String methodName = "";
    	
		try
		{
			this.setLcRes(true);	
			
			methodName = this.iReflection.getMethodName();
			
            if ( (!this.sqlConnection.isClosed()) )
            {
                this.sqlConnection.close();
            }

			return true;
		}
		catch ( Exception e )
		{
			this.setLcRes(false);
			
			cLogger.error( methodName + ":" + e.getMessage() );
		    
			return false;
		}
		finally{}
	}
    /*========================================================================================*/
    public int executeSQLCommand(
    		String sqlQuery, String[] sqlParameters) throws SQLException 
	{
		int rowsAffected = -1; 
		
		String methodName = "";
		
		PreparedStatement sqlStatement = null;
		
		try
		{
			this.setLcRes(true);	
			methodName = this.iReflection.getMethodName();
			
			/*---------Check if we can do opeartion------------------------------------------*/
			if ( (this.sqlConnection.isClosed())  || (this.sqlConnection.isReadOnly()) )
			{
				this.setLcRes(false);
				
				return rowsAffected;
			}
			
			/*---------Execute sql command--------------------------------------------------*/
			sqlStatement = this.sqlConnection.prepareStatement(sqlQuery, sqlParameters);
			
			rowsAffected = sqlStatement.executeUpdate();
			
			/*---------Check if we good results---------------------------------------------*/
            return rowsAffected;
	    }
		catch(SQLException ex)
		{
			cLogger.info("SQLException�information");
			
			while( ex != null)
		    {
				cLogger.info("ErrorMsg:"  + ex.getMessage());
				cLogger.info("SQLSTATE:"  + ex.getSQLState());
				cLogger.info("ErrorCode:" + ex.getErrorCode());
				
				ex = ex.getNextException();
			}
			
			return rowsAffected;
		}
       
		catch( Exception e )
		{
			cLogger.info( methodName + ":" + e.getMessage() );
			
            return rowsAffected;
		}
		finally
		{ 
			if ( (!(sqlStatement == null)) && (!((Connection) sqlStatement).isClosed()))
			{
				sqlStatement.close();
			}
		}
	}
    /*========================================================================================*/
    public int executeSQLCommand(String sqlQuery) throws SQLException 
	{
		int rowsAffected       = -1; 
		
		String methodName 	   = "";
		
		java.sql.Statement sqlStatement = null;
		
		try
		{
			this.setLcRes(true);	
			
			methodName = this.iReflection.getMethodName();
			
			/*---------Check if we can do opeartion------------------------------------------*/
			if ( (this.sqlConnection.isClosed())  || (this.sqlConnection.isReadOnly()) )
			{
				 this.setLcRes(false);
				 
				 return rowsAffected;
			}
			
			/*---------Execute sql command--------------------------------------------------*/
			sqlStatement = this.sqlConnection.createStatement();
			
			rowsAffected = sqlStatement.executeUpdate(sqlQuery);
			
			/*---------Check if we good results---------------------------------------------*/
			
            return rowsAffected;
	    }
		catch(SQLException ex)
		{
			cLogger.error("SQLException�information");
			
			while( ex!=null )
		    {
				cLogger.info("ErrorMsg:"  + ex.getMessage());
				cLogger.info("SQLSTATE:"  + ex.getSQLState());
				cLogger.info("ErrorCode:" + ex.getErrorCode());
				//ex.printStackTrace();
				ex = ex.getNextException();//�For�drivers�that�support�
			}
			
			return rowsAffected;
		}
       
		catch( Exception e )
		{
			cLogger.error( methodName + ":" + e.getMessage() );
			
            return rowsAffected;
		}
		finally 
		{
			if ( !(sqlStatement == null))
			{
				sqlStatement.close();
			}
		}
	}
    /*========================================================================================*/
	public ResultSet executeSQLQuery(String sqlQuery, String[] sqlParameters) 
	{
		
		String methodName = "";
		
		PreparedStatement sqlStatement = null;
		
		ResultSet  sqlResultSet  = null;
		
		try
		{
			this.setLcRes(true);	
			
			methodName = this.iReflection.getMethodName();
			
			/*---------Check if we can do opeartion------------------------------------------*/
			if ( (this.sqlConnection.isClosed())  ||
				 (this.sqlConnection.isReadOnly()) )
			{
				 this.setLcRes(false);
				 
				 return sqlResultSet;
			}
			
			/*---------Execute sql command--------------------------------------------------*/
			sqlStatement = this.sqlConnection.prepareStatement(sqlQuery, sqlParameters);
			
			sqlResultSet = sqlStatement.executeQuery();
			
			/*---------Check if we good results---------------------------------------------*/
			if ( sqlResultSet == null )
			{
				 this.setLcRes(false);
				 
				 return sqlResultSet;
			}
			
			//sqlStatement.close();
			
	        return sqlResultSet;
	    }
		catch(SQLException ex)
		{
			cLogger.info("SQLException�information");
			
			while( ex != null)
		    {
				cLogger.info("ErrorMsg:"  + ex.getMessage());
				cLogger.info("SQLSTATE:"  + ex.getSQLState());
				cLogger.info("ErrorCode:" + ex.getErrorCode());
				
				ex = ex.getNextException();//�For�drivers�that�support�
			}
			
			return sqlResultSet;
		}
		catch( Exception e )
		{
			cLogger.info( methodName + ":" + e.getMessage() );
			
	        return sqlResultSet;
		}
	}
	/*========================================================================================*/
    public ResultSet executeSQLQuery(String sqlQuery) 
	{
		
		String methodName 	   = "";
		
		Statement sqlStatement = null;
		
		ResultSet  sqlResultSet  = null;
		
		try
		{
			this.setLcRes(true);
			
			methodName = this.iReflection.getMethodName();
			
			/*---------Check if we can do opeartion------------------------------------------*/
			if ( (this.sqlConnection.isClosed())  || (this.sqlConnection.isReadOnly()) )
			{
				 this.setLcRes(false);
				 return sqlResultSet;
			}
			
			/*---------Execute sql command--------------------------------------------------*/
			sqlStatement = this.sqlConnection.createStatement();
			
			sqlResultSet = sqlStatement.executeQuery(sqlQuery);
			
			/*---------Check if we good results---------------------------------------------*/
			if ( sqlResultSet == null )
			{
				 setLcRes(false);
				 
				 return sqlResultSet;
			}
			
			//sqlStatement.close();
			
            return sqlResultSet;
	    }
		catch(SQLException ex)
		{
			cLogger.info("SQLException�information");
			
			while( ex != null)
		    {
				cLogger.info("ErrorMsg:"  + ex.getMessage());
				cLogger.info("SQLSTATE:"  + ex.getSQLState());
				cLogger.info("ErrorCode:" + ex.getErrorCode());
				
				ex = ex.getNextException();//�For�drivers�that�support�
			}
			
			return sqlResultSet;
		}
		catch( Exception e )
		{
			cLogger.info( methodName + ":" + e.getMessage() );
			
            return sqlResultSet;
		}
		finally { }
	}
    /*========================================================================================*/
    public boolean closeResultSet(ResultSet  sqlResultSet)
	{
		try
		{
            if ( sqlResultSet != null )
            {
                  sqlResultSet.close();
            }

		    return true;
		}
		catch( Exception e )
		{
			return false;
		}
	}
    
    //----------------------------------------------
  	public List<Class<? extends Object>> getPersistanceClasses()
  	{
  		List<Class<? extends Object>> cPersistanceClasses = new ArrayList<Class<? extends Object>>();
  		
  		try
  		{
  			cPersistanceClasses = PersistenceManager.getPersistanceClasses();
  			 
  			return cPersistanceClasses;
  		}
  		catch(Exception e)
  		{
  			return cPersistanceClasses;
  		}
  	}
  	
    /*========================================================================================*/
    public boolean freeResources()
    {
        boolean cRes = true;

        try
        {
	    	this.closeSQLConnectionToDatabase();
	    	
    		return cRes;
        }
        catch (Exception e)
        {
            return false;
        }
    }
    
    
}
