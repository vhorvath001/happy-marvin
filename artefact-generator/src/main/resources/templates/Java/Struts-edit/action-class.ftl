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
public class ${hm.name}${hm.file.suffix} extends ActionSupport {

	
	private static final String LIST = "LIST";
	private static final String EDIT = "EDIT";
	protected static final Logger LOGGER = LoggerFactory.getLogger(${hm.name}${hm.file.suffix}.class);
	
	private String name;
	private String buttonName;

	
	public String execute() {
		String action = null;
		
		// clicking on the cancel button
		if (buttonName != null && buttonName.equals("Cancel")) {
			action = LIST;
		}
		// clicking on the save button
		else if (buttonName != null && buttonName.equals("Save")) {
			// TODO Please implement the save logic!
			
			action = LIST;
		} else {
			action = EDIT;
		}
				
		return action;
		
	}

	
	public void validate() {
		if (buttonName.equals("Save")) {
			// TODO Please validate the input
			//    For example: checkInteger(this, frequency, "frequency", "Frequency", 0, 99999);
			//                 checkRequired(this, name, "name", "Name")
		}
	}
	
	
	private void checkInteger(${hm.name}${hm.file.suffix} action, String value, String field, String fieldLabel, int... ranges) {
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
	
	private void checkRequired(${hm.name}${hm.file.suffix} action, String value, String field, String fieldLabel) {
		if (value.length() == 0) {
			action.addFieldError(field, String.format("%s is required.",fieldLabel));
		}
	}

	private void checkPossibleValues(${hm.name}${hm.file.suffix} action, String value, String field, String fieldLabel, String... values) {
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
