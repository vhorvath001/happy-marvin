# Java / GWT-server template #

It is a GWT template that generates server side components.

**Generated files:**
  1. GWT server class
  1. Client interface
  1. Client asynchronous interface
  1. Unit test class for the server class

**Properties:**
  * method :  the method will be generated into the classes
  * location\_Java\_GWT-server\_clientAsyncInterface : the location where the asynchronous interface will be generated to
  * location\_Java\_GWT-server\_clientInterface : the location where the interface will be generated to
  * location\_Java\_GWT-server\_server : the location where the server class will be generated to

**Sample files:**
  * client interface
```
package com.acme.client.gwt.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 *
 * @author      Firstname Lastname <address @ example.com>
 * @since       2014-07-31
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
package com.acme.client.gwt.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * TODO Please fill below!
 * The async counterpart of <code>GetCustomerService</code>.
 *
 * @author      Firstname Lastname <address @ example.com>
 * @since       2014-07-31
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
 * @since       2014-07-31
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

  * unit test
```
package com.acme.client.gwt;

//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author      Firstname Lastname <address @ example.com>
 * @since       2014-07-31
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

**Sample instruction (sentence):**
```
~~~HAPPYMARVIN-INSTRUCTION~~~
I need a 'GWT-server' 'Java' component in the project 'tlem-validation-failures-report'. The name is 'GetCustomer'. The folder is 'src/main/java/com/acme/client/gwt'. 
Please add a method to the component: 'String getCustomerName(String id)'. put the client async interface to 'src/main/java/com/acme/client/gwt/client'. 
The client interface should be generated into 'src/main/java/com/acme/client/gwt/client' folder.
~~~HAPPYMARVIN-INSTRUCTIONS-END~~~
```