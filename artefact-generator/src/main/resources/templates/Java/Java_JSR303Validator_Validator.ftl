package ????;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ???.${hm.name}Constraint;

/**
 * This is validator class to validate
 * 
 * !!! Please fill this !!!
 * 
 * @author 
 *
 */
public class ${hm.name}Validator implements ConstraintValidator<${hm.name}Constraint, ???> {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(${hm.name}.class);

    private ${hm.name}Constraint constraint;

    
    /**
     * This function receives the constraint instance
     * (along with the user values)
     */
    public void initialize(${hm.name}Constraint constraint) {
        this.constraint = constraint;
    }

    
    /**
     * The value is the actual object instance.
     */
    public boolean isValid(??? ???, ConstraintValidatorContext context) {
    	LOGGER.info("inside ${hm.name}Validator");

    	boolean validationResult = true;

		// TODO auto generated method - Please fill this!
		
    	return validationResult;
    }


}