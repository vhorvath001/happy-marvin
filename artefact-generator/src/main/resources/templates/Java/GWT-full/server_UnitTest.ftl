<#assign actualDateTime = .now>

package ${hm.package};

//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author      Firstname Lastname <address @ example.com>
 * @since       ${actualDateTime?string("yyyy-MM-dd")}
 *
 */
//@RunWith(SpringJUnit4ClassRunner.class)
// TODO Please add the context XML path!
//@ContextConfiguration(locations="classpath:???.xml")
public class ${hm.name}${hm.file.suffix}Test {

	private static final Logger LOGGER = LoggerFactory.getLogger(${hm.name}${hm.file.suffix}Test.class);

//	@Autowired
	private ${hm.name}${hm.file.suffix} test${hm.name}${hm.file.suffix};


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