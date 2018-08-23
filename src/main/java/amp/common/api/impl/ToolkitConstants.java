package amp.common.api.impl;

public class ToolkitConstants
{
	
    public static String      COMMON_DELIMITER = " ";
    public static String            COMMON_YES = "YES";
    public static String             COMMON_NO = "NO";
    public static String            COMMON_MIN = "MIN";
    public static String             ERROR_STR = "ERROR";
    
    public static String          	   COMMA = ",";
    public static String          	   PERCENT = "%";
    public static String     		 SEMICOLON = ":";
    public static char[]         NODE_SPLITTER = { ':' };
    public static String    		 PSIKPOINT = ";";
    public static String                   DOT = ".";
    public static String             UNDERLINE = "-"; 
    public static String             BACKSLASH = "\\";
    public static String                 SLASH = "/";
    public static String               SQL_REP = "?";
    public static String               NEW_LINE = "\n";
    
    /*---Log Values--------------------------------------------------*/
    public static final String       DATE_FORMAT_NOW = "MM-dd-yyyy";
    public static final String       CALENDAR_FORMAT = "MM/dd/yy";
    
    
    public static final String       EVENT_LOG    = "AMPFrameworkEvent";
    public static final String       LOG          = "AMPFramework.log";
    public static final String       PROBLEM_LOG  = "AMPFrameworkProblems.log";
    public static final String       LOG_NAME     = "AMPFramework";
    public static final String       LOG4J_PATH   = "/amp/common/api/resources/config/log4j.properties";
    
    /*---Settings Values----------------------------------------------*/
    //public static final String 		 JAR_RESOURCE_FODLER = "/amp/common/api/resources/";
    //public static final String 		 JAR_CONFIG_FODLER   = "/amp/common/api/resources/config/";
    public static final String		 FS_CONFIG_FODLER    = "C:/AWP/resources/config/";
    public static final String       CONFIG_FOLDER_NAME  = "config";
    public static final String       LOG_FOLDER          = "log";
    
    public static final String       TOOLKIT_SETTINGS_FILE_JAR = "FrameworkSettingsJar.xml";
    public static final String       TOOLKIT_SETTINGS_FILE_FS  = FS_CONFIG_FODLER  + "FrameworkSettingsFS.xml";
    public static final String       TOOLKIT_SQL_FILE = "FrameworkSQL.xml";
    
    
    public static final String       FILE_SEPARATOR = System.getProperty("file.separator");
    
    public static boolean 			 isLoadSettingsFromFS  = false;
    public static boolean 			 isLoadSettingsFromWar = true;
    public static boolean 			 isLoadSettingsFromJar = false;
    /*---Default Values---------------------------------------------*/
    public static int                ERROR_INT   = -1;
    public static int                MIN_VALUE   = -1;
    public static int              	 DEFAULT_INT = 1;
    public static int                ZERO_INT    = 0;

    public static String             DEFAULT_STR = "";
    public static String             BLANK_STR = " ";
    public static String         TXT_EXTENSION = ".txt"; 
   
    /*---Help Values------------------------------------------------*/
    public static String FPAR_BEG     = "{";
    public static String FPAR_END     = "}";
    public static String FPAR_BEG_END = "{}";
    public static String ASTER        = "*";
    
    /*---Browse Nodes ----------------------------------------------*/
    public static long AMP_ROOT_CATEGORY_NODE_ID  = 1;
    public static long AMP_CATEGORY_NODE_ID       = 2;
    public static long AMP_CLASSIFICATION_NODE_ID = 3;
    public static long AMP_UNDEFINED_NODE_ID      = 999;
    
    public static long AMP_CATEGORY_NODE_LEVEL = 2;
    public static long AMP_UNDEFINED_LEVEL     = -1;
    
    public static String AMP_AMAZON_SOURCE = "Amazon";
    
    public static String AMP_RUNTIME_SOURCE         = "Runtime";
    public static String AMP_ECOMMERCE_SOURCE       = "eCommerce";
    
    public static String AMP_FACEBOOK_SOURCE        = "Facebook";
    public static String AMP_FACEBOOK_GROUP_SOURCE  = "Facebook Group";
    public static String AMP_FACEBOOK_SOURCE_WORKER = "FacebookWorkerBean";
    
    public static String AMP_YOUTUBE_SOURCE         = "Youtube";
    public static String AMP_YOUTUBE_CHANNEL_SOURCE = "Youtube Channel";
    public static String AMP_YOUTUBE_VIDEO_SOURCE   = "Youtube Video";
    public static String AMP_YOUTUBE_VIDEO_TARGET   = "Youtube Video";
    public static String AMP_YOUTUBE_SOURCE_WORKER  = "YoutubeWorkerBean";
    
    public static String AMP_DEFAULT_THREAD = "NA";
    
    public static String AMP_CATEGORY_NODE_NAME       = "Category Node";
    public static String AMP_ALL_CATEGORY_VALUE       = "All";
    public static String AMP_ALL_CATEGORY_BROWSE_NODE = "0";
    
    public static String BROWSE_NODE_ID_PARAM  = "BrowseNodeId";
    public static String AMP_KEEP_ALIVE_THREAD = "cKeepAliveThread";
    public static String AMP_KEEP_ALIVE_TIMER  = "cKeepAliveTimer";
   
    /*---SOURCE DATA CONSTANTS  -------------------------------------*/
    public static String SD_ITEMID            = "ITEMID";
    public static String SD_DESCRIPTION       = "DESCRIPTION";
    public static String SD_STATUS            = "STATUS";
    
    /*--OPERATIONS TYPES CONSTANTS ---------------------------------*/
    public static String OP_READ_POST    = "ReadPost";
    public static String OP_PROCESS_POST = "ProcessPost";
    public static String OP_POST_LINK    = "PostLink";
    public static String OP_START_CYCLE  = "StartCycle";
    public static String OP_END_CYCLE    = "EndCycle";
    public static String OP_TRANSLATE    = "Translate";
    public static String OP_KEYWORDS     = "Keywords";
    
    /*--STATUSES CONSTANTS ----------------------------------------*/
    public static String AMP_STATUS_NORMAL        = "Normal";
    public static String AMP_STATUS_WARNING       = "Warning";
    public static String AMP_STATUS_CRITICAL      = "Critical";
    public static String AMP_STATUS_INPROCESS     = "InProcess";
    public static String AMP_STATUS_NOT_INPROCESS = "NotInProcess";
    
    /*--DB CONSTANTS ----------------------------------------------*/
    public static String AMP_DB_TYPE_ORACLE = "oracle";
    public static String AMP_DB_TYPE_MYSQL  = "mysql";
}


/*
protected String cFrameworkBeansConfig =  
		"src/main/java/amp/framework/data/handler/resources/config/FrameworkBeans.xml";

protected String cFrameworkBeansProps =  
		"src/main/java/amp/framework/data/handler/resources/config/FrameworkBeans.properties";
*/