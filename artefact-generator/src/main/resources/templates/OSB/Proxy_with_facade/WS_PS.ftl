<?xml version="1.0" encoding="UTF-8"?>
<xml-fragment xmlns:ser="http://www.bea.com/wli/sb/services" xmlns:tran="http://www.bea.com/wli/sb/transports" xmlns:env="http://www.bea.com/wli/config/env" xmlns:http="http://www.bea.com/wli/sb/transports/http" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:con="http://www.bea.com/wli/sb/pipeline/config" xmlns:con1="http://www.bea.com/wli/sb/stages/transform/config" xmlns:con2="http://www.bea.com/wli/sb/stages/config" xmlns:con3="http://www.bea.com/wli/sb/stages/alert/config" xmlns:con4="http://www.bea.com/wli/sb/stages/routing/config">
	<ser:coreEntry isProxy="true" isEnabled="true">
		<ser:binding type="SOAP" isSoap12="false" xsi:type="con:SoapBindingType" xmlns:con="http://www.bea.com/wli/sb/services/bindings/config">
			<con:wsdl ref="${hm.property.osb_project_name}/${hm.property.proxy_wsdl_path}"/>
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
		<ser:ws-policy>
			<ser:binding-mode>wsdl-policy-attachments</ser:binding-mode>
		</ser:ws-policy>
	</ser:coreEntry>
	<ser:endpointConfig>
		<tran:provider-id>http</tran:provider-id>
		<tran:inbound>true</tran:inbound>
		<tran:URI>
			<env:value>${hm.location}${hm.name}PS</env:value>
		</tran:URI>
		<tran:inbound-properties/>
		<tran:all-headers>false</tran:all-headers>
		<tran:provider-specific>
			<http:inbound-properties/>
		</tran:provider-specific>
	</ser:endpointConfig>
	<ser:router>
		<con:flow>
			<con:route-node name="call_WORFLOW">
				<con:context/>
				<con:actions>
					<con4:route>
						<con2:id>_ActionId-877522618316018714--64dec08.137e4ad8071.-7d48</con2:id>
						<con4:service ref="${hm.property.osb_project_name}/${hm.location}WORKFLOW_${hm.name}PS" xsi:type="ref:ProxyRef" xmlns:ref="http://www.bea.com/wli/sb/reference"/>
						<con4:outboundTransform>
							<con1:routing-options>
								<con2:id>_ActionId-8443755941730629262--2e758916.137fc3a0528.-7fae</con2:id>
								<con1:qualityOfService>exactly-once</con1:qualityOfService>
							</con1:routing-options>
						</con4:outboundTransform>
						<con4:responseTransform/>
					</con4:route>
				</con:actions>
			</con:route-node>
		</con:flow>
	</ser:router>
</xml-fragment>