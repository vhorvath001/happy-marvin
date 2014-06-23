<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE module PUBLIC "-//Google Inc.//DTD Google Web Toolkit 1.6.2//EN" "http://google-web-toolkit.googlecode.com/svn/tags/1.6.2/distro-source/core/src/gwt-module.dtd">
<module rename-to='${hm.name?uncap_first}'>

	<!-- Inherit the core Web Toolkit stuff.                        -->
	<inherits name='com.google.gwt.user.User'/>

	<!-- Inherit the default GWT style sheet.  You can change       -->
	<!-- the theme of your GWT application by uncommenting          -->
	<!-- any one of the following lines.                            -->
	<inherits name='com.google.gwt.user.theme.standard.Standard'/>
	<!-- <inherits name='com.google.gwt.user.theme.chrome.Chrome'/> -->
	<!-- <inherits name='com.google.gwt.user.theme.dark.Dark'/>     -->

	<inherits name="com.google.gwt.logging.Logging"/>
	<set-property name="gwt.logging.logLevel" value="INFO"/>
	<set-property name="gwt.logging.enabled" value="TRUE"/>

	<!-- Specify the app entry point class.                         -->
	<!-- TODO Please add the package name -->

	<entry-point class='${hm.name}Entry'/>

</module>
