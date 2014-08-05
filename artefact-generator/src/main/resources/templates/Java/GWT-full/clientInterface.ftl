<#assign actualDateTime = .now>

package ${hm.package};

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 *
 * @author      Firstname Lastname <address @ example.com>
 * @since       ${actualDateTime?string("yyyy-MM-dd")}
 *
 */
@RemoteServiceRelativePath("${hm.name?uncap_first}")
public interface ${hm.name}${hm.file.suffix} extends RemoteService {

	/**
	 * TODO please define what the method does!
	 *
	 * @param  param1 First parameter
	 * @return The value to return
	 * @throws The exception to be thrown
	 */
	${hm.property.method};
	 
}
