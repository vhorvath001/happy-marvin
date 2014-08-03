<?xml version="1.0" encoding="UTF-8"?>
<xml-fragment xmlns:ser="http://www.bea.com/wli/sb/services" xmlns:tran="http://www.bea.com/wli/sb/transports" xmlns:http="http://www.bea.com/wli/sb/transports/http" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:env="http://www.bea.com/wli/config/env">
  <ser:coreEntry isProxy="false" isEnabled="true">
    <ser:binding type="SOAP" isSoap12="false" xsi:type="con:SoapBindingType" xmlns:con="http://www.bea.com/wli/sb/services/bindings/config">
      <con:wsdl ref="${hm.property.osb_project_name}/${hm.property.business_wsdl_path}"/>
      <con:binding>
        <con:name>${hm.extractedProperty.business_binding}</con:name>
        <con:namespace>${hm.extractedProperty.business_targetNamespace}</con:namespace>
      </con:binding>
      <con:WSI-compliant>false</con:WSI-compliant>
    </ser:binding>
    <ser:monitoring isEnabled="false">
      <ser:aggregationInterval>10</ser:aggregationInterval>
    </ser:monitoring>
    <ser:sla-alerting isEnabled="true">
      <ser:alertLevel>normal</ser:alertLevel>
    </ser:sla-alerting>
    <ser:ws-policy>
      <ser:binding-mode>wsdl-policy-attachments</ser:binding-mode>
    </ser:ws-policy>
  </ser:coreEntry>
  <ser:endpointConfig>
    <tran:provider-id>http</tran:provider-id>
    <tran:inbound>false</tran:inbound>
    <tran:URI>
      <env:value>PLEASE_DEFINE_ME</env:value>
    </tran:URI>
    <tran:outbound-properties>
      <tran:load-balancing-algorithm>round-robin</tran:load-balancing-algorithm>
      <tran:retry-count>1</tran:retry-count>
      <tran:retry-interval>0</tran:retry-interval>
      <tran:retry-application-errors>false</tran:retry-application-errors>
    </tran:outbound-properties>
    <tran:provider-specific>
      <http:outbound-properties>
        <http:request-method>POST</http:request-method>
        <http:timeout>5</http:timeout>
        <http:connection-timeout>5</http:connection-timeout>
        <http:follow-redirects>false</http:follow-redirects>
        <http:chunked-streaming-mode>true</http:chunked-streaming-mode>
      </http:outbound-properties>
    </tran:provider-specific>
  </ser:endpointConfig>
</xml-fragment>