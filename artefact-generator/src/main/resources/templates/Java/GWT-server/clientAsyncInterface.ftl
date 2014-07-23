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
public interface ${hm.name}${hm.file.suffix} {

	/**
	 * Asynchronous method of <code>${hm.name}${hm.file.suffix}</code>
	 *
	 * @param  param1 First parameter
	 * @param callback Asynchronous callback logic for receiving the response
	 * @throws The exception to be thrown
	 */
	// TODO modifications needed:
	//  - probably the one or more characters should be removed in front of the text 'AsyncCallback'
	//  - the type of AsyncCallback must be the return type of the method
	//  - the method mustn't return value => modify it to void
	//    e.g. Integer getPrice(String id)  =>  void getPrice(String id, AsyncCallback<Integer> callback)
	void /*TODO method must be void*/${hm.property.method}/*TODO remove parenthesis*/, AsyncCallback<???/*TODO define the type*/> callback);
  
}
