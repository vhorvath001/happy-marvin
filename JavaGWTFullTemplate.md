# Java / GWT-server template #

It is a GWT template that generates server side and client side components.

**Generated files:**
  1. GWT server class
  1. Client interface
  1. Client asynchronous interface
  1. Unit test class for the server class
  1. GWT xml
  1. UI class
  1. Unit test class for the UI class

**Properties:**
  * method :  the method will be generated into the classes
  * location\_Java\_GWT-full\_clientAsyncInterface : the location where the asynchronous interface will be generated to
  * location\_Java\_GWT-full\_clientInterface : the location where the interface will be generated to
  * location\_Java\_GWT-full\_server : the location where the server class will be generated to
  * location\_Java\_GWT-full\_ui : the location where the UI class will be generated to
  * location\_Java\_GWT-full\_gwtXml : the location where the GWT XML file will be generated to

**Sample files:**
  * client interface
```
package com.acme.client.gwt;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 *
 * @author      Firstname Lastname <address @ example.com>
 * @since       2014-08-01
 *
 */
@RemoteServiceRelativePath("getCustomer")
public interface GetCustomerService extends RemoteService {

    /**
     * TODO please define what the method does!
     *
     * @param  param1 First parameter
     * @return The value to return
     * @throws The exception to be thrown
     */
    String getCustomerName(String id);
     
} 
```

  * client async interface
```
package com.acme.client.gwt;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * TODO Please fill below!
 * The async counterpart of <code>GetCustomerService</code>.
 *
 * @author      Firstname Lastname <address @ example.com>
 * @since       2014-08-01
 *
 */
public interface GetCustomerServiceAsync {

    /**
     * Asynchronous method of <code>GetCustomerServiceAsync</code>
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
    void /*TODO method must be void*/String getCustomerName(String id)/*TODO remove parenthesis*/, AsyncCallback<???/*TODO define the type*/> callback);
  
}
```

  * server class
```
package com.acme.client.gwt;

import com.google.gwt.sample.stockwatcher.client.GreetingService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * The server side implementation of the RPC service.
 *
 * @author      Firstname Lastname <address @ example.com>
 * @since       2014-08-01
 *
 */
@SuppressWarnings("serial")
public class GetCustomerServiceImpl extends RemoteServiceServlet implements GetCustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetCustomerServiceImpl.class);
    
    /*
     * (non-Javadoc)
     * 
     * @see GetCustomerService
     */
    String getCustomerName(String id) {
        // TODO auto generated method - Please implement the method!
        LOGGER.info("The method String getCustomerName(String id) has been called!");
    }

} 
```

  * unit test of the server class
```
package com.acme.client.gwt;

//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author      Firstname Lastname <address @ example.com>
 * @since       2014-08-01
 *
 */
//@RunWith(SpringJUnit4ClassRunner.class)
// TODO Please add the context XML path!
//@ContextConfiguration(locations="classpath:???.xml")
public class GetCustomerServiceImplTestTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetCustomerServiceImplTestTest.class);

//    @Autowired
    private GetCustomerServiceImplTest testGetCustomerServiceImplTest;


    @Before
    public void setUp() {
        // TODO Please set up the mocked objects!
    }
    
    
    /**
     * Testing the method String getCustomerName(String id)
     */
    // TODO Please put the text 'test' to the beginning of the method; e.g. testGetCustomer!
    String getCustomerName(String id) {
        // TODO auto generated method - Please implement the method!
    }
    
}
```

  * UI class
```
package com.acme.client.ui;

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
 * @since       2014-08-01
 *
 */
public class GetCustomerEntry implements EntryPoint {
    
    private static Logger LOGGER = Logger.getLogger("GetCustomerEntry");
    private GetCustomerServiceAsync getCustomerService = (GetCustomerServiceAsync) GWT.create(GetCustomerService.class);
    
    public void onModuleLoad() {
        // TODO Please implement the (UI) initialization!
        Panel panel = null;

        // Add widgets to the root panel.
        RootPanel.get().add(panel);
    }

    private Object doAsyncCall(Object message) {
        // TODO Please add the method to the service call! Method signature: String getCustomerName(String id)
        getCustomerService.???(message, new AsyncCallback<Object>() {

            public void onFailure(Throwable caught) {
                Window.alert("RPC call failed!" + caught.getMessage());
                LOGGER.log(Level.SEVERE, "RPC call failed!", caught);
            }

            public void onSuccess(Object result) {
                // TODO Please do something with the result!
            }
        });
    }

}
```

  * GWT XML
```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE module PUBLIC "-//Google Inc.//DTD Google Web Toolkit 1.6.2//EN" "http://google-web-toolkit.googlecode.com/svn/tags/1.6.2/distro-source/core/src/gwt-module.dtd">
<module rename-to='getCustomer'>

    <!-- Inherit the core Web Toolkit stuff.                        -->
    <inherits name='com.google.gwt.user.User'/>

    <!-- Inherit the default GWT style sheet.  You can change       -->
    <!-- the theme of your GWT application by uncommenting          -->
    <!-- any one of the following lines.                            -->
    <inherits name='com.google.gwt.user.theme.standard.Standard'/>
    <!-- <inherits name='com.google.gwt.user.theme.chrome.Chrome'/> -->
    <!-- <inherits name='com.google.gwt.user.theme.dark.Dark'/>     -->

    <inherits name="com.google.gwt.logging.Logging"/>
    <set-property name="gwt.logging.logLevel" value="INFO"/>
    <set-property name="gwt.logging.enabled" value="TRUE"/>

    <!-- Specify the app entry point class.                         -->
    
    <!-- TODO Please add the package name -->
    <entry-point class='GetCustomerEntry'/>

</module>
```

  * unit test of the UI class
```
package com.acme.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * GWT JUnit test for GetCustomerEntry
 */
public class GetCustomerEntry extends GWTTestCase {

    private GetCustomerServiceAsync getCustomerService = (GetCustomerServiceAsync) GWT.create(GetCustomerService.class);
    
   /**
   * must refer to a valid module that sources this class.
   */
   public String getModuleName() {
      return "getCustomer";
   }

    /**
     * Testing the method String getCustomerName(String id)
     */
    // TODO Please put the text 'test' to the beginning of the method; e.g. testGetCustomer!
    String getCustomerName(String id) {
        // TODO auto generated method - Please implement the method!
        
        // sample for testing a server call
        //        create the service that we will test
        ServiceDefTarget target = (ServiceDefTarget) getCustomerService;
        // TODO please specify the endpoint
        target.setServiceEntryPoint(GWT.getModuleBaseURL() + "????");
        //        the RPC calls are asynchronous, we will need to wait for the response
        delayTestFinish(10000);
        //        send a request to the server
        // TODO please set the method
        getCustomerService.???(new Object(), 
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
```

**Sample instruction (sentence):**~~~HAPPYMARVIN-INSTRUCTION~~~
I need a 'GWT-full' 'Java' component in the project 'tlem-validation-failures-report'. Name it as 'GetCustomer'. Put it into the 'src/main/java/com/acme/client/gwt' folder. 
Please add a method to the component: 'String getCustomerName(String id)'. The server class should be put to 'src/main/java/com/acme/client/gwt/server' folder. 
Also put the ui class to 'src/main/java/com/acme/client/ui' folder and the GWT XML file to the 'src/main/resources/gwt' folder.
~~~HAPPYMARVIN-INSTRUCTIONS-END~~~}}}
```