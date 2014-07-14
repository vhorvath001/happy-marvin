<#assign actualDateTime = .now>

package ${hm.package};


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;

/**
*
* @author      Firstname Lastname <address @ example.com>
* @since       ${actualDateTime?string("yyyy-MM-dd")}
*
*/
public class ${hm.name}Action extends ActionSupport {

	
	protected static final Logger LOGGER = LoggerFactory.getLogger(${hm.name}Action.class);
	
	private String name;
	private String buttonName;

	
	public String execute() throws ThrottlingConfigurationException {
		if (buttonName.equals("Save")) {
			// TODO Please implement the save logic!
		}
		return "SUCCESS";
	}

	
	public void validate() {
		if (buttonName.equals("Save")) {
			// TODO Please validate the input
			//    For example: checkInteger(this, frequency, "frequency", "Frequency", 0, 99999);
			//                 checkRequired(this, name, "name", "Name")
		}
	}
	
	
	private void checkInteger(ThrottlingActionSupport action, String value, String field, String fieldLabel, int... ranges) {
		checkRequired(action, value, field, fieldLabel);
		if (action.getFieldErrors().isEmpty()) {
			try {
				Integer i = Integer.parseInt(value);
				if (ranges.length == 2) {
					if (i < ranges[0] || i > ranges[1]) {
						action.addFieldError(field, String.format("%s must be in the range %s and %s.", fieldLabel, Integer.toString(ranges[0]), Integer.toString(ranges[1])));
					}
				}
			} catch(NumberFormatException nfe) {
				action.addFieldError(field, String.format("%s must be an integer.",fieldLabel));
			}
		}
	}
	
	private void checkRequired(ThrottlingActionSupport action, String value, String field, String fieldLabel) {
		if (value.length() == 0) {
			action.addFieldError(field, String.format("%s is required.",fieldLabel));
		}
	}

	private void checkPossibleValues(ThrottlingActionSupport action, String value, String field, String fieldLabel, String... values) {
		checkRequired(action, value, field, fieldLabel);
		if (action.getFieldErrors().isEmpty()) {
			if (Arrays.binarySearch(values, value) < 0) {
				action.addFieldError(field, String.format("The value (%s) of %s must be in the set (%s).", value, fieldLabel, ThrottlingUtils.commaSeparated(values)));
			}
		}
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getButtonName() {
		return buttonName;
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}

}
