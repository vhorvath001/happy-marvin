<?xml version="1.0" encoding="UTF-8"?>
<xml-fragment xmlns:ser="http://www.bea.com/wli/sb/services" xmlns:tran="http://www.bea.com/wli/sb/transports" xmlns:env="http://www.bea.com/wli/config/env" xmlns:http="http://www.bea.com/wli/sb/transports/http" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:con="http://www.bea.com/wli/sb/pipeline/config" xmlns:con1="http://www.bea.com/wli/sb/stages/transform/config" xmlns:con2="http://www.bea.com/wli/sb/stages/config" xmlns:con3="http://www.bea.com/wli/sb/stages/alert/config" xmlns:con4="http://www.bea.com/wli/sb/stages/logging/config">
  <ser:coreEntry isProxy="true" isEnabled="true">
    <ser:binding type="SOAP" isSoap12="false" xsi:type="con:SoapBindingType" xmlns:con="http://www.bea.com/wli/sb/services/bindings/config">
      <con:wsdl ref="${hm.property.proxy_wsdl_path}"/>
      <con:port>
        <con:name>${hm.extractedProperty.port_name}</con:name>
        <con:namespace>${hm.extractedProperty.proxy_targetNamespace}</con:namespace>
      </con:port>
      <con:selector type="SOAP body"/>
    </ser:binding>
    <ser:monitoring isEnabled="false">
      <ser:aggregationInterval>10</ser:aggregationInterval>
      <ser:pipelineMonitoringLevel>Pipeline</ser:pipelineMonitoringLevel>
    </ser:monitoring>
    <ser:reporting>true</ser:reporting>
    <ser:logging isEnabled="true">
      <ser:logLevel>debug</ser:logLevel>
    </ser:logging>
    <ser:sla-alerting isEnabled="true">
      <ser:alertLevel>normal</ser:alertLevel>
    </ser:sla-alerting>
    <ser:pipeline-alerting isEnabled="true">
      <ser:alertLevel>normal</ser:alertLevel>
    </ser:pipeline-alerting>
  </ser:coreEntry>
  <ser:endpointConfig>
    <tran:provider-id>local</tran:provider-id>
    <tran:inbound>true</tran:inbound>
    <tran:inbound-properties/>
    <tran:all-headers>true</tran:all-headers>
  </ser:endpointConfig>
  <ser:router>
    <con:pipeline type="request" name="validating the request_request">
      <con:stage name="req_stage" errorHandler="_onErrorHandler-1971003569530537335-28fba6a8.137f6b6eafa.-7fe8">
        <con:context>
          <con2:varNsDecl namespace="${hm.extractedProperty.port_namespace}" prefix="not"/>
        </con:context>
        <con:actions>
          <con1:ifThenElse>
            <con2:id>_ActionId-1168617826443513018--54145756.137acf3f9df.-7bbb</con2:id>
            <con1:case>
              <con1:condition>
                <con2:xqueryText>fn:local-name($body/*[1]) = "${hm.extractedProperty.request_root_element}"</con2:xqueryText>
              </con1:condition>
              <con1:actions>
                <con1:validate>
                  <con2:id>_ActionId-1168617826443513018--54145756.137acf3f9df.-7b4d</con2:id>
                  <con1:schema ref="${hm.property.proxy_xsd_path}"/>
                  <con1:schemaElement xmlns:not="http://virginmedia/schema/NotifyInventoryChange">not:${hm.extractedProperty.request_root_element}</con1:schemaElement>
                  <con1:varName>body</con1:varName>
                  <con1:location>
                    <con2:xpathText>./not:${hm.extractedProperty.request_root_element}</con2:xpathText>
                  </con1:location>
                </con1:validate>
              </con1:actions>
            </con1:case>
            <con1:default>
              <con1:assign varName="body">
                <con2:id>_ActionId-877522618316018714--64dec08.137e4ad8071.-7f09</con2:id>
                <con1:expr>
                  <con2:xqueryText><![CDATA[<soapenv:Body xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
      <soapenv:Fault xmlns:ns="http://virginmedia/schema/faults/1/0">
         <faultcode>NO_OPERATION</faultcode>
         <faultstring xml:lang="en">No operation in the request!</faultstring>
         <detail>
            <ns:SOAPFaultMessage>
               <ns:id>id_1</ns:id>
               <ns:Fault>
                  <ns:faultcode>NO_OPERATION</ns:faultcode>
                  <ns:detail>No operation in the request!</ns:detail>
               </ns:Fault>
            </ns:SOAPFaultMessage>
         </detail>
      </soapenv:Fault>
</soapenv:Body>]]></con2:xqueryText>
                </con1:expr>
              </con1:assign>
              <con2:reply isError="false">
                <con2:id>_ActionId-1168617826443513018--54145756.137acf3f9df.-7ac0</con2:id>
              </con2:reply>
            </con1:default>
          </con1:ifThenElse>
        </con:actions>
      </con:stage>
    </con:pipeline>
    <con:pipeline type="response" name="validating the request_response"/>
    <con:pipeline type="request" name="calling external service_request">
      <con:stage name="init_stage">
        <con:context>
          <con2:varNsDecl namespace="${hm.extractedProperty.port_namespace}" prefix="not"/>
        </con:context>
        <con:actions>
          <con1:ifThenElse>
            <con2:id>_ActionId-1168617826443513018--54145756.137acf3f9df.-79a1</con2:id>
            <con1:case>
              <con1:condition>
                <con2:xqueryText>fn:local-name($body/*[1]) = "${hm.extractedProperty.request_root_element}"</con2:xqueryText>
              </con1:condition>
              <con1:actions>
                <con1:assign varName="req_ExternalServiceRequest">
                  <con2:id>_ActionId-5438086606170537229--6f1e14b2.137b26c199b.-7f74</con2:id>
                  <con1:expr>
                    <con2:xqueryTransform>
                      <con2:resource ref="${hm.property.xquery_path}"/>
                      <con2:param name="externalServiceRequest1">
                        <con2:path>$body/not:${hm.extractedProperty.request_root_element}</con2:path>
                      </con2:param>
                    </con2:xqueryTransform>
                  </con1:expr>
                </con1:assign>
              </con1:actions>
            </con1:case>
          </con1:ifThenElse>
        </con:actions>
      </con:stage>
      <con:stage name="calling" errorHandler="_onErrorHandler-1994248388348932979--69286bed.137b3ae0edb.-7f90">
        <con:context/>
        <con:actions>
          <con1:wsCallout>
            <con2:id>_ActionId-1994248388348932979--69286bed.137b3ae0edb.-7f93</con2:id>
            <con1:service xsi:type="ref:BusinessServiceRef" ref="${hm.property.location_business_service}/${hm.name}BS" xmlns:ref="http://www.bea.com/wli/sb/reference"/>
            <con1:operation>${hm.extractedProperty.business_operation}</con1:operation>			
            <con1:request>
              <con1:body wrapped="false">$req_ExternalServiceRequest</con1:body>
            </con1:request>
            <con1:response>
              <con1:body wrapped="false">resp_ExternalServiceRequest</con1:body>
            </con1:response>
            <con1:requestTransform/>
            <con1:responseTransform/>
          </con1:wsCallout>
        </con:actions>
      </con:stage>
    </con:pipeline>
    <con:pipeline type="response" name="calling external service_response"/>
    <con:pipeline type="error" name="_onErrorHandler-1994248388348932979--69286bed.137b3ae0edb.-7f90">
      <con:stage name="ExternalService_call_error_handler_stage">
        <con:context/>
        <con:actions>
          <con1:ifThenElse>
            <con2:id>_ActionId-4696471516841890525-2fd02c72.1469215f334.-7eb3</con2:id>
            <con1:case>
              <con1:condition>
                <con2:xqueryText>$body/ctx:fault/ctx:errorCode/text() = "BEA-382500"</con2:xqueryText>
              </con1:condition>
              <con1:actions>
                <con1:assign varName="v_error">
                  <con2:id>_ActionId-4696471516841890525-2fd02c72.1469215f334.-7eb2</con2:id>
                  <con1:expr>
                    <con2:xqueryText>$body/ctx:fault/*[local-name()='details']/*[1]</con2:xqueryText>
                  </con1:expr>
                </con1:assign>
                <con1:rename varName="v_error">
                  <con2:id>_ActionId-4696471516841890525-2fd02c72.1469215f334.-7eb1</con2:id>
                  <con1:location>
                    <con2:xpathText>.</con2:xpathText>
                  </con1:location>
                  <con1:namespace>http://schemas.xmlsoap.org/soap/envelope/</con1:namespace>
                  <con1:localname>Fault</con1:localname>
                </con1:rename>
              </con1:actions>
            </con1:case>
            <con1:default>
              <con1:assign varName="v_error">
                <con2:id>_ActionId-4696471516841890525-2fd02c72.1469215f334.-7eb0</con2:id>
                <con1:expr>
                  <con2:xqueryText>$body/ctx:fault</con2:xqueryText>
                </con1:expr>
              </con1:assign>
            </con1:default>
          </con1:ifThenElse>
          <con1:replace varName="body" contents-only="true">
            <con2:id>_ActionId-4696471516841890525-2fd02c72.1469215f334.-7eae</con2:id>
            <con1:location>
              <con2:xpathText>.</con2:xpathText>
            </con1:location>
            <con1:expr>
              <con2:xqueryText>$v_error</con2:xqueryText>
            </con1:expr>
          </con1:replace>
          <con2:reply isError="false">
            <con2:id>_ActionId-4696471516841890525-2fd02c72.1469215f334.-7eac</con2:id>
          </con2:reply>
        </con:actions>
      </con:stage>
    </con:pipeline>
    <con:pipeline type="error" name="_onErrorHandler-1971003569530537335-28fba6a8.137f6b6eafa.-7fe8">
      <con:stage name="error_at_validation">
        <con:context/>
        <con:actions>
          <con1:replace varName="body" contents-only="true">
            <con2:id>_ActionId-1971003569530537335-28fba6a8.137f6b6eafa.-7eec</con2:id>
            <con1:location>
              <con2:xpathText>.</con2:xpathText>
            </con1:location>
            <con1:expr>
              <con2:xqueryText>$fault</con2:xqueryText>
            </con1:expr>
          </con1:replace>
          <con2:reply isError="false">
            <con2:id>_ActionId-1971003569530537335-28fba6a8.137f6b6eafa.-7f5d</con2:id>
          </con2:reply>
        </con:actions>
      </con:stage>
    </con:pipeline>
    <con:flow>
      <con:pipeline-node name="validating the request">
        <con:request>validating the request_request</con:request>
        <con:response>validating the request_response</con:response>
      </con:pipeline-node>
      <con:pipeline-node name="calling external service">
        <con:request>calling external service_request</con:request>
        <con:response>calling external service_response</con:response>
      </con:pipeline-node>
    </con:flow>
  </ser:router>
</xml-fragment>