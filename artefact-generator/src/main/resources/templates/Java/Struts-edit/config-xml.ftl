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

	<package name="${hm.name?lower_case}" extends="default">
		<!-- TODO please add the package name to the class below! -->
		<action name="${hm.name?lower_case}-edit" class="${hm.name}EditAction">
			<result name="EDIT">${hm.name?lower_case}-edit.jsp</result>
			<result name="LIST" type="redirectAction">${hm.name?lower_case}-list</result>
		</action>	
	</package>

</struts>