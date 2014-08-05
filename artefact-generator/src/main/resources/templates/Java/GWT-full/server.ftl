<#assign actualDateTime = .now>

package ${hm.package};

import com.google.gwt.sample.stockwatcher.client.GreetingService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * The server side implementation of the RPC service.
 *
 * @author      Firstname Lastname <address @ example.com>
 * @since       ${actualDateTime?string("yyyy-MM-dd")}
 *
 */
@SuppressWarnings("serial")
public class ${hm.name}${hm.file.suffix} extends RemoteServiceServlet implements ${hm.name}Service {

	private static final Logger LOGGER = LoggerFactory.getLogger(${hm.name}${hm.file.suffix}.class);
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see ${hm.name}Service
	 */
	${hm.property.method} {
        // TODO auto generated method - Please implement the method!
		LOGGER.info("The method ${hm.property.method} has been called!");
	}

}