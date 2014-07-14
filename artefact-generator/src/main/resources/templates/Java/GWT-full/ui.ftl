<#assign actualDateTime = .now>

package ${hm.package};

import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.Window;


/**
 * TODO Please fill below! 
 *
 * @author      Firstname Lastname <address @ example.com>
 * @since       ${actualDateTime?string("yyyy-MM-dd")}
 *
 */
public class ${hm.name}Entry implements EntryPoint {
	
	private static Logger logger = Logger.getLogger("${hm.name}Entry");
	private ${hm.name}ServiceAsync ${hm.name?uncap_first}Service = (${hm.name}ServiceAsync) GWT.create(${hm.name}Service.class);
	
	
	public void onModuleLoad() {
		// TODO Please implement the (UI) initialization!
		Panel panel = null;

		// Add widgets to the root panel.
		RootPanel.get().add(panel);
	}


	private Object doAsyncCall(Object message) {
		// TODO Please add the method to the service call! Method signature: ${hm.property.method}
		${hm.name?uncap_first}Service.???(message, new AsyncCallback<Object>() {

			public void onFailure(Throwable caught) {
				Window.alert("RPC call failed!" + caught.getMessage());
				logger.log(Level.SEVERE, "RPC call failed!", caught);
			}

			public void onSuccess(Object result) {
				// TODO Please do something with the result!
            }
		});
	}

}