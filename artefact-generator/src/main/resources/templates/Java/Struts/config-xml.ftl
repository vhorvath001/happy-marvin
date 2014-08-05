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
		<action name="save" class="${hm.package}.${hm.name}Action">
			<result name="input">/${hm.name?lower_case}/${hm.name?lower_case}-list.jsp</result>
			<result name="SUCCESS" type="redirectAction">list</result>
		</action>	
	</package>

</struts>