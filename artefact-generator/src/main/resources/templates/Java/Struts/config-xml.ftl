<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE struts PUBLIC
   "-//Apache Software Foundation//DTD Struts Configuration 2.0//EN"
   "http://struts.apache.org/dtds/struts-2.0.dtd">

<!-- This struts config XML can be included to your main struts config file with the 'include' operation -->
<struts>

	<constant name="struts.devMode" value="true"/>
	
	<package name="default" extends="struts-default" namespace="/">
		<!--interceptors>
			<interceptor name="parameterLogger" class="org.vhorvath.valogato.web.utils.ThrottlingParameterLoggerInterceptor"/>
			<interceptor-stack name="logginStack">
				<interceptor-ref name="parameterLogger"/>
				<interceptor-ref name="defaultStack"/>
			</interceptor-stack>
		</interceptors-->

		<!--default-interceptor-ref name="logginStack" /-->
		
		<global-results>
			<result name="error">/error.jsp</result>
			<result name="web_error">/web_error.jsp</result>
		</global-results>
		
		<global-exception-mappings>
			<exception-mapping result="error" exception="java.lang.Exception"/>
			<exception-mapping result="web_error" exception="java.lang.RuntimeException"/>
		</global-exception-mappings>
	</package>
	
	<package name="frequency" extends="default">
		<!--action name="list" class="org.???.ListAction">
			<result name="SUCCESS">/list.jsp</result>
		</action-->	
	
		<action name="save" class="${hm.package}.${hm.name}Action">
			<result name="input">/frequency/${hm.name?lower_case}-list.jsp</result>
			<result name="SUCCESS" type="redirectAction">list</result>
		</action>	
	</package>
	
</struts>   