/**
 * 
 */
package amp.common.api.impl;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author MVEKSLER
 *
 */
@Configuration
public class ToolkitSpringConfig  implements ApplicationContextAware 
{

	ApplicationContext applicationContext;
	
	/**
	 * 
	 */
	public ToolkitSpringConfig() {
		// TODO Auto-generated constructor stub
	}

	@Bean
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public ToolkitDataProvider toolkitDataProvider() 
	{
	      return new ToolkitDataProvider();
	}
	
	@Bean 
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public ToolkitJAXB toolkitJAXB() 
	{
	      return new ToolkitJAXB();
	}
	
	@Bean 
	@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public ToolkitSQL toolkitSQL() 
	{
	      return new ToolkitSQL();
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		// TODO Auto-generated method stub
		this.applicationContext = applicationContext;
	}

}

