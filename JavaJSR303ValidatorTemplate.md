# Java / JavaJSR303ValidatorTemplate template #

The template is for generating JSR303 validator classes.

**Generated files:**
  1. JSR303 validator constraint class
  1. JSR303 validator class
  1. JSR303 config XML
  1. unit test class for JSR303 validator

**Properties:**
  * location\_Java\_JSR303Validator\_Validator : the location where the validator class will be generated to; if it is not defined then the basic location property will be used
  * location\_Java\_JSR303Validator\_Constraint : the location where the validator constraint class will be generated to; if it is not defined then the basic location property will be used
  * location\_Java\_JSR303Validator\_configXML : the location where the config XML will be generated to; if it is not defined then the basic location property will be used

**Sample files:**
  * validator constraint class
```
package com.acme.validators;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * This annotation is a JSR303 validator constraint.
 * 
 * Requirement: TODO Please fill this!
 *
 * @author      Firstname Lastname <address @ example.com>
 * @since       2014-07-30
 */
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = CheckCustomerValidator.class)
@Documented
public @interface CheckCustomerConstraint {

    // These next parameters exist in every constraint
    // TODO Please fill this !
    String message() default "{????????????}";

    String constraintName() default "CheckCustomerConstraint";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
}
```

  * validator class
```
package com.acme.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author      Firstname Lastname <address @ example.com>
 * @since       2014-07-30
 *
 */
public class CheckCustomerValidator implements ConstraintValidator<CheckCustomerConstraint, ???> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckCustomerValidator.class);

    private CheckCustomerConstraint constraint;
    
    /**
     * This function receives the constraint instance
     * (along with the user values)
     */
    public void initialize(CheckCustomerConstraint constraint) {
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
        LOGGER.info("inside CheckCustomerValidator.isValid(...)");

        boolean validationResult = true;

        // TODO auto generated method - Please fill the method!
        
        return validationResult;
    }

}
```

  * config XML
```
<jsr303Config>
    <validator name="CheckCustomerValidator">
        <fields>
            <field>This is just a test...</fields>
        </fields>
    <validator>
</jsr303Config>
```

  * unit test
```
package com.acme.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

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
public class CheckCustomerValidatorTestTest {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckCustomerValidatorTest.class);
    
//    @Autowired
    private CheckCustomerValidatorTest testCheckCustomerValidatorTest;
    
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
```

**Sample instruction (sentence):**
```
~~~HAPPYMARVIN-INSTRUCTION~~~
A 'JSR303Validator' 'Java' component needed in the project 'tlem-validation-failures-report'. Name it as 'CheckCustomer'. 
You should put it into the 'src/main/java/com/acme/validators' folder. The config XML file should be put to 'src/main/resources' folder.
~~~HAPPYMARVIN-INSTRUCTIONS-END~~~
```

**Sample instruction (key-value pairs):**
```
~~~HAPPYMARVIN-INSTRUCTION~~~
TYPE:Java
TEMPLATE:JSR303Validator
PROJECT:tlem-validation-failures-report
NAME:CheckCustomer
LOCATION:src/main/java/com/acme/validators
LOCATION_JAVA_JSR303VALIDATOR_VALIDATOR:
LOCATION_JAVA_JSR303VALIDATOR_CONSTRAINT:
LOCATION_JAVA_JSR303VALIDATOR_CONFIGXML:src/main/resources
~~~HAPPYMARVIN-INSTRUCTIONS-END~~~
```