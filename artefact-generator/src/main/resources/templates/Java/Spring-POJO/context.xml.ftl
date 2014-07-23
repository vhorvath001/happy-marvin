<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

  <#if hm.property.injected_bean_0??>
    <bean id="bean0" class="${hm.property.injected_bean_0}"/>
  </#if>
    
  <#if hm.property.injected_bean_1??>
    <bean id="bean1" class="${hm.property.injected_bean_1}"/>
  </#if>

  <#if hm.property.injected_bean_2??>
    <bean id="bean2" class="${hm.property.injected_bean_2}"/>
  </#if>
  
    <bean id="${hm.name?uncap_first}" class="${hm.package}.${hm.name}">
      <#if hm.property.injected_bean_0??>
        <property name="bean0" ref="bean0"/>
      </#if>
      <#if hm.property.injected_bean_1??>
        <property name="bean1" ref="bean1"/>
      </#if>
      <#if hm.property.injected_bean_2??>
        <property name="bean2" ref="bean2"/>
      </#if>
    </bean>

</beans>
