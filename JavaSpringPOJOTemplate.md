# Java / Spring POJO template #

The template is for generating a simple Spring POJO component.

**Generated files:**
  1. Spring POJO class
  1. unit test
  1. context XML

**Properties:**
  * location\_Java\_Spring-POJO\_context\_XML : the location where the context XML will be generated to
  * injected\_bean\_0 : the first bean to be injected into the Spring POJO (the injected bean won't be generated, it should already exist)
  * injected\_bean\_1 : the second bean to be injected into the Spring POJO (the injected bean won't be generated, it should already exist)
  * injected\_bean\_2 : the third bean to be injected into the Spring POJO (the injected bean won't be generated, it should already exist)
  * method : the method will be generated into the Spring POJO and into the unit test

**Sample files:**
  * Spring POJO class
```
package com.acme.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author      Firstname Lastname <address @ example.com>
 * @since       2014-08-01
 *
 */
public class GetCustomerBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetCustomerBean.class);

    private CustomerDataHandler bean0;
  
    /**
     * TODO please define what the method does!
     *
     * @param  param1 First parameter
     * @return The value to return
     * @throws The exception to be thrown
     */
    String getCustomer(String id) {
        // TODO auto generated method - Please implement the method!
        LOGGER.info("The method String getCustomer(String id) has been called!");
    }
  
    public CustomerDataHandler getBean0() {
        return bean0;
    }
    
    public void setBean0(CustomerDataHandler bean0) {
        this.bean0 = bean0;
    }

}
```

  * context XML
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="bean0" class="CustomerDataHandler"/>
  
    <bean id="getCustomer" class="src.main.resources.GetCustomer">
        <property name="bean0" ref="bean0"/>
    </bean>

</beans>
```

  * unit test
```
package com.acme.beans;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author      Firstname Lastname <address @ example.com>
 * @since       2014-08-01
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
// TODO Please add the context XML path!
@ContextConfiguration(locations="classpath:???.xml")
public class GetCustomerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetCustomer.class);

    @Autowired
    private GetCustomer testGetCustomer;

    @Before
    public void setUp() {
        // TODO Please set up the mocked objects!
    }    
    
    /**
     * Testing the method String getCustomer(String id)
     */
    // TODO Please put the text 'test' to the beginning of the method; e.g. testGetCustomer!
    String getCustomer(String id) {
        // TODO auto generated method - Please implement the method!
    }
    
}
```

**Sample instruction (sentence):**
```
~~~HAPPYMARVIN-INSTRUCTION~~~
A 'Spring-POJO' 'Java' component is needed in the project 'tlem-validation-failures-report'. Name it as 'GetCustomer'. 
The destination folder should be 'src/main/java/com/acme/beans'. The context XML should be generated into 'src/main/resources' folder. 
The bean to be injected to the Spring class is 'CustomerDataHandler'. add a method to the component: 'String getCustomer(String id)'.
~~~HAPPYMARVIN-INSTRUCTIONS-END~~~
```