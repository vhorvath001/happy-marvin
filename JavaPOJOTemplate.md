# Java / POJO template #

It is a simple template, a Java class can be created with a method in it.

**Generated files:**
  1. POJO Java class
  1. unit test for the POJO Java class

**Property:**
  * method : the method will be generated into the POJO and into the unit test

**Sample files:**
  * POJO
```
package com.acme;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author      Firstname Lastname <address @ example.com>
 * @since       2014-07-30
 *
 */
public class GetCustomerBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetCustomerBean.class);

    /**
     * TODO please define what the method does!
     *
     * @param  param1 First parameter
     * @return The value to return
     * @throws The exception to be thrown
     */
     String callBackend(String id) {
        // TODO auto generated method - Please fill the method!
        LOGGER.info("The method String callBackend(String id) has been called!");
    }
    
}
```

  * Unit test
```
package com.acme;

//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author      Firstname Lastname <address @ example.com>
 * @since       2014-07-30
 *
 */
//@RunWith(SpringJUnit4ClassRunner.class)
// TODO Please add the context XML path!
//@ContextConfiguration(locations="classpath:???.xml")
public class GetCustomerBeanTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetCustomerBean.class);

//    @Autowired
    private GetCustomerBean testGetCustomerBean;

    @Before
    public void setUp() {
        // TODO Please set up the mocked objects!
    }
        
    /**
     * Testing the method String callBackend(String id)
     */
    String callBackend(String id) {
        // TODO Please put the text 'test' to the beginning of the method; e.g. testGetCustomer!
        // TODO auto generated method - Please fill the method!
    }
    
}
```

**Sample instruction (sentence):**
```
~~~HAPPYMARVIN-INSTRUCTION~~~
I need a 'POJO' 'Java' class in the folder 'tlem-validation-failures-report'. Name it as 'GetCustomerBean'. 
The destination folder should be 'src/main/java/com/acme'. Please add a method to the class: 'String callBackend(String id)'.
~~~HAPPYMARVIN-INSTRUCTIONS-END~~~
```

**Sample instruction (key-value pairs):**
```
~~~HAPPYMARVIN-INSTRUCTION~~~
TYPE:Java
TEMPLATE:POJO
PROJECT:tlem-validation-failures-report
NAME:GetCustomerBean
LOCATION:src/main/java/com/acme
METHOD:String callBackend(String id)
~~~HAPPYMARVIN-INSTRUCTIONS-END~~~
```