<#assign actualDateTime = .now>

package ${hm.package};

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * GWT JUnit test for ${hm.name}Entry
 */
public class ${hm.name}Entry extends GWTTestCase {


	private ${hm.name}ServiceAsync ${hm.name?uncap_first}Service = (${hm.name}ServiceAsync) GWT.create(${hm.name}Service.class);

	
   /**
   * must refer to a valid module that sources this class.
   */
   public String getModuleName() {
      return "${hm.name?uncap_first}";
   }

   
	/**
	 * Testing the method ${hm.property.method}
	 */
    // TODO Please put the text 'test' to the beginning of the method; e.g. testGetCustomer!
    ${hm.property.method} {
		// TODO auto generated method - Please implement the method!
		
		// sample for testing a server call
		//		create the service that we will test
		ServiceDefTarget target = (ServiceDefTarget) ${hm.name?uncap_first}Service;
		// TODO please specify the endpoint
		target.setServiceEntryPoint(GWT.getModuleBaseURL() + "????");
		//		the RPC calls are asynchronous, we will need to wait for the response
		delayTestFinish(10000);
		//		send a request to the server
		// TODO please set the method
		${hm.name?uncap_first}Service.???(new Object(), 
			new AsyncCallback<String>() {
				public void onFailure(Throwable caught) {
					// The request resulted in an unexpected error.
					fail("Request failure: " + caught.getMessage());
				}
				public void onSuccess(String result) {
					// Verify that the response is correct.
					// TODO
					assertTrue(result.startsWith("unknown"));
					// now that we have received a response, we need to tell the test runner that the test is complete. You must call finishTest() after an asynchronous test finishes successfully, or the test will time out.
					finishTest();
				}
			}
		);
	}
	
}