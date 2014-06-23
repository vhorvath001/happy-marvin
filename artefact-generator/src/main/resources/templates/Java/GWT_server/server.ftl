<#assign actualDateTime = .now>

package ${hm.package};

import com.google.gwt.sample.stockwatcher.client.GreetingService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 *
 * @author      Firstname Lastname <address @ example.com>
 * @since       ${actualDateTime?string("yyyy-MM-dd")}
 *
 */
@SuppressWarnings("serial")
public class ${hm.name}ServiceImpl extends RemoteServiceServlet implements ${hm.name}Service {

	private static final Logger LOGGER = LoggerFactory.getLogger(${hm.name}ServiceImpl.class);
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see ${hm.name}Service
	 */
	${hm.property.method} {
        // TODO auto generated method - Please fill the method!
		LOGGER.info("The method {hm.property.method} has been called!");
	}

}