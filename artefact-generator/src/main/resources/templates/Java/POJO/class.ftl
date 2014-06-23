<#assign actualDateTime = .now>

package ${hm.package};

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author      Firstname Lastname <address @ example.com>
 * @since       ${actualDateTime?string("yyyy-MM-dd")}
 *
 */
public class ${hm.name} {

    private static final Logger LOGGER = LoggerFactory.getLogger(${hm.name}.class);

	/**
	 * TODO please define what the method does!
	 *
	 * @param  param1 First parameter
	 * @return The value to return
	 * @throws The exception to be thrown
	 */
	 ${hm.property.method} {
        // TODO auto generated method - Please fill the method!
		LOGGER.info("The method ${hm.property.method} has been called!");
    }
    
}