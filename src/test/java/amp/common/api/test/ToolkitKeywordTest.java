package amp.common.api.test;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import amp.common.api.impl.ToolkitKeyword;
import amp.common.api.textm.CardKeyword;

public class ToolkitKeywordTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetKeywordsList() 
	{
		@SuppressWarnings("unused")
		String cMethodName = "";
        
        try
        {
        	
        	StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
	        StackTraceElement ste = stacktrace[1];
	        cMethodName = ste.getMethodName();
         
	        String cText = "AmazonBasics Apple Certified Lightning to USB Cable - 6 Feet (1.8 Meters) - White";
	        	
	        ToolkitKeyword cToolkitKeyword = new ToolkitKeyword();
	        
	        List<CardKeyword> cKeywords = cToolkitKeyword.getKeywordsList(cText);
	        
	        for( CardKeyword cCardKeyword : cKeywords )
	        {
	        	System.out.println(cCardKeyword.getStem() + ":" + 
	        					   cCardKeyword.getFrequency()  + 
	        					   cCardKeyword.getTerms());
	        }
        }
        catch (Exception e)
        {
           fail(e.getMessage());
        }
        finally { }
	}

}
