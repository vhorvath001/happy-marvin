<#assign actualDateTime = .now>

package ${hm.package};

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.beans.factory.annotation.Autowired;


/**
 *
 * @author      Firstname Lastname <address @ example.com>
 * @since       ${actualDateTime?string("yyyy-MM-dd")}
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
// TODO Please add the context XML path!
@ContextConfiguration(locations="classpath:???.xml")
public class ${hm.name}Test {

	private static final Logger LOGGER = LoggerFactory.getLogger(${hm.name}.class);

	@Autowired
	private ${hm.name} test${hm.name};


	@Before
	public void setUp() {
		// TODO Please set up the mocked objects!
	}
	
    
	/**
	 * Testing the method ${hm.property.method}
	 */
    // TODO Please put the text 'test' to the beginning of the method; e.g. testGetCustomer!
    ${hm.property.method} {
		// TODO auto generated method - Please implement the method!
    }
    
}