# Java / Stateless EJB template #

It is a template for creating stateless EJB.

**Generated files:**
  1. local interface
  1. remote interface
  1. stateless bean class
  1. unit test for the stateless bean

**Properties:**
  * location\_Java\_stateless-EJB\_stateless : the location where the stateless bean will be generated to
  * location\_Java\_stateless-EJB\_local : the location where the local interface will be generated to
  * location\_Java\_stateless-EJB\_remote : the location where the remote interface will be generated to

**Sample files:**
  * local interface
```
package com.acme.server.business.interf;

/**
 *
 * @author      Firstname Lastname <address @ example.com>
 * @since       2014-07-31
 *
 */
public interface CheckCustomerServiceLocal {

    void doSomething();
    
}
```

  * remote interface
```
package com.acme.server.business.interf;

/**
 *
 * @author      Firstname Lastname <address @ example.com>
 * @since       2014-07-31
 *
 */
public interface CheckCustomerServiceRemote {

    void doSomething();
    
}
```

  * stateless bean
```
package com.acme.server.business.impl;

import java.io.Serializable;
import java.security.Principal;
import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

/**
 * EJB stateless bean
 *
 * @author      Firstname Lastname <address @ example.com>
 * @since       2014-07-31
 *
 */
@Stateless(mappedName = "CheckCustomerServiceBean")
@Local(CheckCustomerServiceLocal.class)
@Remote(CheckCustomerServiceRemote.class)
public class CheckCustomerServiceBean implements CheckCustomerServiceLocal, CheckCustomerServiceRemote {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckCustomerServiceBean.class);

    // TODO Please define the name of the entity manager
    @PersistenceContext(unitName = "???")
    EntityManager entityManager;
    
    // TODO Please define the bean being used 
    @EJB
    private ??? ???;
    
    @Resource(mappedName = "javax.jms.QueueConnectionFactory")
    private ConnectionFactory connectionFactory;
    
    // TODO Please define the destination queue mapped name
    @Resource(mappedName = "queue/???")
    private Destination queue;
    
    @Resource
    EJBContext ejbContext;

    public CheckCustomerServiceBean() {}

    /**
     * TODO please define what the method does!
     *
     * @param  param1 First parameter
     * @return The value to return
     * @throws The exception to be thrown
     */
    @Override
    public void doSomething() {
        LOGGER.info("The method doSomething of CheckCustomerServiceBean is called!");
        // TODO EJB method, please code it
    }

    // TODO Please remove this method if you don't need to send JMS message
    /**
     * Sending a JMS message to the queue defined above
     *
     * @param  message  The message to be sent
     */
    private void sendJMS(Serializable message) {
        LOGGER.debug("sendJMS: {}", message);

        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
            Session session = connection.createSession(true, 0);

            MessageProducer sender = session.createProducer(queue);
            ObjectMessage message = null;
            Principal principal = ejbContext.getCallerPrincipal();
            if (principal != null && principal instanceof User) {
                JMSUserMessageObject target = new JMSUserMessageObject(weblogic.security.Security.getCurrentSubject(), message);
                message = session.createObjectMessage(target);
            } else {
                message = session.createObjectMessage(message);
            }

            sender.send(message);
            connection.close();
        } catch (JMSException ex) {
            LOGGER.error("Failed to send JMS message.", ex);
            throw new RuntimeException(ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    LOGGER.error("Failed to close JMS connection!", e);
                }
            }
        }
    }

}
```

  * unit test
```
package com.acme.server.business.impl;

import java.io.Serializable;
import java.security.Principal;
import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author      Firstname Lastname <address @ example.com>
 * @since       2014-07-31
 *
 */
//@RunWith(SpringJUnit4ClassRunner.class)
// TODO Please add the context XML path!
//@ContextConfiguration(locations="classpath:???.xml")
public class CheckCustomerServiceBeanTestTest{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckCustomerServiceBeanTest.class);

//    @Autowired
    private CheckCustomerServiceBeanTest testCheckCustomerServiceBeanTest;

    
    @Before
    public void setUp() {
        // TODO Please set up the mocked objects!
    }
       
    /**
     * Testing the method doSomething
     */
    public void testDoSomething() {
    }

}
```

**Sample instruction (sentence):**
```
~~~HAPPYMARVIN-INSTRUCTION~~~
put a 'stateless-EJB' 'Java' component in the project 'tlem-validation-failures-report'. Name it as 'CheckCustomer'. 
The destination folder should be 'src/main/java/com/acme/server/business/impl'. Please put the local interface to 'src/main/java/com/acme/server/business/interf'. 
The remote interface should be put to 'src/main/java/com/acme/server/business/interf' folder. 
~~~HAPPYMARVIN-INSTRUCTIONS-END~~~
```

**Sample instruction (key-value pairs):**
```
~~~HAPPYMARVIN-INSTRUCTION~~~
TYPE:Java
TEMPLATE:stateless-EJB
PROJECT:tlem-validation-failures-report
NAME:CheckCustomer
LOCATION:src/main/java/com/acme/server/business/
LOCATION_JAVA_stateless-EJB_STATELESS:src/main/java/com/acme/server/business/impl
LOCATION_JAVA_stateless-EJB_LOCAL:
LOCATION_JAVA_stateless-EJB_REMOTE:
~~~HAPPYMARVIN-INSTRUCTIONS-END~~~
```