<#assign actualDateTime = .now>

package ${hm.package};

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
 * @since       ${actualDateTime?string("yyyy-MM-dd")}
 */
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = ${hm.name}Validator.class)
@Documented
public @interface ${hm.name}${hm.file.suffix} {

	// These next parameters exist in every constraint
	// TODO Please fill this !
	String message() default "{????????????}";

	String constraintName() default "${hm.name}${hm.file.suffix}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
	
}