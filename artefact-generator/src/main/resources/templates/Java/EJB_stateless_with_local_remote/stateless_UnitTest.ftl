package ${hm.package};

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
 * @since       ${actualDateTime?string("yyyy-MM-dd")}
 *
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations="classpath:???.xml")
public class ${hm.name}${hm.file.suffix}Test{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(${hm.name}${hm.file.suffix}.class);

//	@Autowired
	private ${hm.name}${hm.file.suffix} test${hm.name}${hm.file.suffix};

    
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