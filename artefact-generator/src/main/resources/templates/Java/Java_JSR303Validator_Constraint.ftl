package ????????;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.jpmorgan.ibtcp.tlem.framework.validation.validator.PR2181Validator;

/**
 * This annotation is a JSR303 validator constraint.
 * 
 * Requirement: !!! Please fill this !!!
 *
 * @author 
 */
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = ${hm.name}Validator.class)
@Documented
public @interface ${hm.name}Constraint {

	// These next parameters exist in every constraint
	// TODO Please fill this !
	String message() default "{????????????}";

	String constraintName() default "${hm.name}Constraint";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}