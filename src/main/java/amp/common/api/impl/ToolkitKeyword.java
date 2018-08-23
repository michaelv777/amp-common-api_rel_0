package amp.common.api.impl;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import amp.common.api.textm.CardKeyword;
import amp.common.api.textm.KeywordsExtractor;

public class ToolkitKeyword 
{
	private static Logger cLogger = 
			LoggerFactory.getLogger(ToolkitKeyword.class);
	
	//---Class Variables
	protected KeywordsExtractor tKeyExtractor = 
			new KeywordsExtractor();
	
	
    private boolean lcRes = true;

    //---Getters/Seeters
	public KeywordsExtractor gettKeyExtractor() {
		return tKeyExtractor;
	}


	public void settKeyExtractor(KeywordsExtractor tKeyExtractor) {
		this.tKeyExtractor = tKeyExtractor;
	}


	public boolean isLcRes() {
		return lcRes;
	}


	public void setLcRes(boolean lcRes) {
		this.lcRes = lcRes;
	}
    
    public ToolkitKeyword()
    {
    	String cMethodName = "";
        
        try
        {
        	this.setLcRes(true);
        	
        	StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
         
	        this.setLcRes(this.initClassVariables());
        }
        catch (Exception e)
        {
            this.setLcRes(false);

            cLogger.error(cMethodName + ":" + e.getMessage() );
        }
        finally { }
    }
 
	public boolean initClassVariables()
    {
    	String cMethodName = "";
        
        try
        {
        	this.setLcRes(true);
        	
        	StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
         
	        this.tKeyExtractor = new KeywordsExtractor();
        	
        	return this.isLcRes();
        }
        catch (Exception e)
        {
            this.setLcRes(false);

            cLogger.error(cMethodName + ":" + e.getMessage() );

            return false; 
        }
        finally { }
    }
	
	public List<CardKeyword> getKeywordsList(String fullText)
	{
		String cMethodName = "";
        
		List<CardKeyword> cKeywords = new LinkedList<CardKeyword>();
		
        try
        {
        	this.setLcRes(true);
        	
        	StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
         
	        cKeywords = this.tKeyExtractor.getKeywordsList(fullText);
        	
        	return cKeywords;
        }
        catch (Exception e)
        {
            this.setLcRes(false);

            cLogger.error(cMethodName + ":" + e.getMessage() );

            return new LinkedList<CardKeyword>(); 
        }
        finally { }
	}
}
