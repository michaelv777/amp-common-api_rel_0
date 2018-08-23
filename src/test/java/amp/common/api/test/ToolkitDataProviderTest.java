/**
 * 
 */
package amp.common.api.test;

import org.junit.Ignore;
import org.junit.Test;

import amp.common.api.impl.ToolkitDataProvider;
import junit.framework.TestCase;

/**
 * @author MVEKSLER
 *
 */
public class ToolkitDataProviderTest extends TestCase {

	ToolkitDataProvider cToolkitDataProvider = null;
	/**
	 * @param name
	 */
	public ToolkitDataProviderTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception 
	{
		super.tearDown();
		
		try
		{
			if ( this.cToolkitDataProvider != null )
			{
				this.cToolkitDataProvider.closeDatabaseConnection();
			}
		}
		catch( Exception e)
		{
			fail("testToolkitDataProvider failed:" + e.getStackTrace());
		}
	}

	/**
	 * Test method for {@link amp.common.api.impl.ToolkitDataProvider#ToolkitDataProvider()}.
	 */
	@Test
	//@Ignore
	public void testToolkitDataProvider() 
	{
		try
		{
			
			this.cToolkitDataProvider = new ToolkitDataProvider();
			
			if ( this.cToolkitDataProvider.isLcRes() )
			{
				boolean cRes = this.cToolkitDataProvider.openDatabaseConnection();
				if ( !cRes )
				{
					fail("testToolkitDataProvider failed to open DB connection!");
				}
			}
			
		}
		catch( Exception e)
		{
			fail("testToolkitDataProvider failed:" + e.getStackTrace());
		}
	}

	
}
