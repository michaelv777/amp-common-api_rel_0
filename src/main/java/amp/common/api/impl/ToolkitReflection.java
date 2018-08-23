/**
 * 
 */
package amp.common.api.impl;

import java.io.File;
import java.util.StringTokenizer;

import amp.common.api.interfaces.ReflectionInterface;


/**
 * @author michaelv
 *
 */
public class ToolkitReflection implements ReflectionInterface
{
	/*-----------------------------------------------------*/
	public int getLineNumber()
	{
	    return Thread.currentThread().getStackTrace()[2].getLineNumber();
	}
	/*-----------------------------------------------------*/
	public String getMethodName()
	{
	    return Thread.currentThread().getStackTrace()[2].getMethodName();
	}
	/*-----------------------------------------------------*/
	public String getClassName()
	{
	    return Thread.currentThread().getStackTrace()[2].getClassName();
	}
	/*-----------------------------------------------------*/
	public String getFileName()
	{
	    return Thread.currentThread().getStackTrace()[2].getFileName();
	}
	/*-----------------------------------------------------*/
	public String getFolderPath (String filePath)
	{
		try
		{
	         StringTokenizer st = new StringTokenizer(filePath, ".");
	         StringBuffer buf = new StringBuffer();
	
	         if (st.hasMoreTokens() && st.countTokens() > 0)
	         {
	        	buf.append(st.nextToken());
	         }
	       
	         String folderPath = buf.toString();
	
	         return folderPath;
		}
		catch( Exception e )
		{
			return null;
		}
	}
	/*-----------------------------------------------------*/
	public String getFilePath (String fileName)
	{
	 try
	 {
		 File dir = new File(fileName);
		 
		 return dir.getAbsolutePath(); 
	 }
	 catch( Exception e)
	 {
		return null; 	
	 }
	}
	
}

