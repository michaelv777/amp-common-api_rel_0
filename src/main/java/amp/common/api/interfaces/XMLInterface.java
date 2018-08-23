/**
 * 
 */
package amp.common.api.interfaces;

/**
 * @author michaelv
 *
 */
//import javax.xml.parsers.*;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public interface XMLInterface
{
	/*----------------------------------------*/
	public NodeList getXMLNodeList( String nodeName );
	
	/*----------------------------------------*/
	public Node getXMLNode( String nodeName );
}
