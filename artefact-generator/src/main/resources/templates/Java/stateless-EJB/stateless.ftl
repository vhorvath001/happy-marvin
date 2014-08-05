<#assign actualDateTime = .now>

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


/**
 * EJB stateless bean
 *
 * @author      Firstname Lastname <address @ example.com>
 * @since       ${actualDateTime?string("yyyy-MM-dd")}
 *
 */
@Stateless(mappedName = "${hm.name}${hm.file.suffix}")
@Local(${hm.name}ServiceLocal.class)
@Remote(${hm.name}ServiceRemote.class)
public class ${hm.name}${hm.file.suffix} implements ${hm.name}ServiceLocal, ${hm.name}ServiceRemote {

	private static final Logger LOGGER = LoggerFactory.getLogger(${hm.name}${hm.file.suffix}.class);

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

	public ${hm.name}${hm.file.suffix}() {}

	
	/**
	 * TODO please define what the method does!
	 *
	 * @param  param1 First parameter
	 * @return The value to return
	 * @throws The exception to be thrown
	 */
	@Override
	public void doSomething() {
		LOGGER.info("The method doSomething of ${hm.name}${hm.file.suffix} is called!");
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