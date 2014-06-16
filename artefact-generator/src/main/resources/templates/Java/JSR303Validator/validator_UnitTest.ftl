<#assign actualDateTime = .now>

package ${hm.package};

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

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
public class ${hm.name}${hm.file.suffix}Test {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(${hm.name}${hm.file.suffix}.class);
	
//	@Autowired
	private ${hm.name}${hm.file.suffix} test${hm.name}${hm.file.suffix};

    
	@Before
	public void setUp() {
		// TODO Please set up the mocked objects!
	}
	
    
	/**
	 * Testing the method isValid
	 */
    public void testIsValid() {
        // TODO Please put the text 'test' to the beginning of the method; e.g. testGetCustomer!
		// TODO auto generated method - Please fill the method!
    }
    
}