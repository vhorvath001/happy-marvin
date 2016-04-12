# Java / Struts-edit template #

Struts edit page and controller class can be generated with the template. On the page there will be one textbox and two buttons (save and cancel). The controller class will handle the button events.

**Generated files:**
  1. action class
  1. JSP files
  1. config XML

**Properties:**
  * location\_Java\_Struts\_jsp : the location where the JSP will be generated to
  * location\_Java\_Struts\_config-xml : the location where the config XML will be generated to

**Sample files:**
  * action class
```
package com.acme.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;

/**
*
* @author      Firstname Lastname <address @ example.com>
* @since       2014-08-02
*
*/
public class GetCustomerEditAction extends ActionSupport {
    
    private static final String LIST = "LIST";
    private static final String EDIT = "EDIT";
    protected static final Logger LOGGER = LoggerFactory.getLogger(GetCustomerEditAction.class);
    
    private String name;
    private String buttonName;

    public String execute() {
        String action = null;
        
        // clicking on the cancel button
        if (buttonName != null && buttonName.equals("Cancel")) {
            action = LIST;
        }
        // clicking on the save button
        else if (submitType != null && submitType.equals("Save")) {
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
        
    private void checkInteger(GetCustomerEditAction action, String value, String field, String fieldLabel, int... ranges) {
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
    
    private void checkRequired(GetCustomerEditAction action, String value, String field, String fieldLabel) {
        if (value.length() == 0) {
            action.addFieldError(field, String.format("%s is required.",fieldLabel));
        }
    }

    private void checkPossibleValues(GetCustomerEditAction action, String value, String field, String fieldLabel, String... values) {
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
```

  * config XML
```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE struts PUBLIC
   "-//Apache Software Foundation//DTD Struts Configuration 2.0//EN"
   "http://struts.apache.org/dtds/struts-2.0.dtd">

<struts>

    <constant name="struts.devMode" value="true"/>

    <package name="default" extends="struts-default" namespace="/">
        <global-results>
            <result name="error">/error.jsp</result>
        </global-results>

        <global-exception-mappings>
            <exception-mapping result="error" exception="java.lang.RuntimeException"/>
        </global-exception-mappings>
    </package>

    <package name="getcustomer" extends="default">
        <!-- TODO please add the package name to the class below! -->
        <action name="getcustomer-edit" class="GetCustomerEditAction">
            <result name="EDIT">getcustomer-edit.jsp</result>
            <result name="LIST" type="redirectAction">getcustomer-list</result>
        </action>    
    </package>

</struts>
```

  * JSP
```
<%@ taglib prefix="s" uri="/struts-tags" %>

<h1>GetCustomer - Edit</h1>

<s:form action="getcustomer-edit.action" method="post" theme="simple" validate="true">

   <table>
      <tr>
         <td>Name:</td>
         <td>
            <s:textfield name="name" size="36" maxlength="36"/>
         </td>
      </tr>
      <tr>
         <td colspan="2">
            <font color="red"><s:fielderror/></font>
         </td>
      </tr>
      <tr>
         <td>
            <s:submit name="buttonName" value="Save" align="center" theme="simple"/>
            <s:submit name="buttonName" value="Cancel" align="center" theme="simple"/>
         </td>
      </tr>
   </table>
   
</s:form>
```

**Sample instruction (sentence):**
```
~~~HAPPYMARVIN-INSTRUCTION~~~
Please put a 'Java' component in the project 'tlem-validation-failures-report'. The template that should be used is 'Struts'. 
Name it as 'GetCustomer'. Put it into the 'src/main/java/com/acme/server/controller' folder. 
Please put the JSP to the 'src/main/webapp/jsp' folder, the config XML  file to 'src/main/resources' folder.
~~~HAPPYMARVIN-INSTRUCTIONS-END~~~
```