<#assign actualDateTime = .now>

package ${hm.package};

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * TODO Please fill below!
 * The async counterpart of <code>${hm.name}Service</code>.
 *
 * @author      Firstname Lastname <address @ example.com>
 * @since       ${actualDateTime?string("yyyy-MM-dd")}
 *
 */
public interface ${hm.name}ServiceAsync {

	/**
	 * Asynchronous method of <code>${hm.name}Service</code>
	 *
	 * @param  param1 First parameter
	 * @param callback Asynchronous callback logic for receiving the response
	 * @return The value to return
	 * @throws The exception to be thrown
	 */
	 // TODO probably the one or more characters should be removed in front of the text 'AsyncCallback'
	 ${hm.property.method}, AsyncCallback<String> callback);
  
}
