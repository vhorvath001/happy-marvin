<#assign actualDateTime = .now>

package ${hm.package};

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author      Firstname Lastname <address @ example.com>
 * @since       ${actualDateTime?string("yyyy-MM-dd")}
 *
 */
public class ${hm.name}${hm.file.suffix} implements ConstraintValidator<${hm.name}Constraint, ???> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(${hm.name}${hm.file.suffix}.class);

    private ${hm.name}Constraint constraint;

    
    /**
     * This function receives the constraint instance
     * (along with the user values)
     */
    public void initialize(${hm.name}Constraint constraint) {
        this.constraint = constraint;
    }

    
	/**
	 * TODO please define what the method validates!
	 *
	 * @param  param1 First parameter
	 * @return The value to return
	 * @throws The exception to be thrown
	 */
    public boolean isValid(??? ???, ConstraintValidatorContext context) {
    	LOGGER.info("inside ${hm.name}${hm.file.suffix}.isValid(...)");

    	boolean validationResult = true;

		// TODO auto generated method - Please fill the method!
		
    	return validationResult;
    }


}