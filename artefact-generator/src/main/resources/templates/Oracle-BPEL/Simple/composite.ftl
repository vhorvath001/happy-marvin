<?xml version="1.0" encoding="UTF-8" ?>
<composite name="${hm.name}" 
           revision="1.0"
           label="2012-06-28_21-38-50_678" 
           mode="active" 
           state="on"
           xmlns="http://xmlns.oracle.com/sca/1.0"
           xmlns:xs="http://www.w3.org/2001/XMLSchema"
           xmlns:wsp="http://schemas.xmlsoap.org/ws/2004/09/policy"
           xmlns:orawsp="http://schemas.oracle.com/ws/2006/01/policy"
           xmlns:ui="http://xmlns.oracle.com/soa/designer/">
    <import namespace="${hm.extractedProperty.pl_namespace}"
            location="${hm.property.pl_wsdl_path}" 
            importType="wsdl"/>
    <import namespace="${hm.extractedProperty.bpel_namespace}"
            location="${hm.property.bpel_wsdl_path}" 
            importType="wsdl"/>
    <service name="bpelprocess_${hm.name?lower_case}_ep"
             ui:wsdlLocation="${hm.property.bpel_wsdl_path}">
        <interface.wsdl interface="${hm.extractedProperty.bpel_namespace}#wsdl.interface(${hm.extractedProperty.bpel_port_type})"/>
        <binding.ws port="${hm.extractedProperty.bpel_namespace}#wsdl.endpoint(bpelprocess_${hm.name?lower_case}_ep/${hm.extractedProperty.bpel_port})">
            <property name="weblogic.wsee.wsat.transaction.flowOption"
                      type="xs:string" 
                      many="false">NEVER</property>
        </binding.ws>
    </service>
    <property name="validateSchema" 
              type="xs:boolean" 
              many="false">false</property>
    <component name="BPELProcess_${hm.name}" version="2.0">
        <implementation.bpel src="BPELProcess_${hm.name}.bpel"/>
        <property name="bpel.config.transaction" 
                  type="xs:string" 
                  many="false">required</property>
        <property name="bpel.config.oneWayDeliveryPolicy" 
                  type="xs:string" 
                  many="false">async.persist</property>
    </component>
    <reference name="PL_${hm.extractedProperty.pl_name}"
               ui:wsdlLocation="${hm.property.pl_wsdl_path}">
        <interface.wsdl interface="${hm.extractedProperty.pl_namespace}#wsdl.interface(${hm.extractedProperty.pl_port_type})"/>
        <binding.ws port="${hm.extractedProperty.pl_namespace}#wsdl.endpoint(${hm.extractedProperty.pl_name}/${hm.extractedProperty.pl_port})"
                    location="${hm.property.pl_wsdl_path}"
                    soapVersion="1.1">
            <property name="weblogic.wsee.wsat.transaction.flowOption"
                      type="xs:string" 
					  many="false">WSDLDriven</property>
        </binding.ws>
    </reference>
    <wire>
        <source.uri>BPELProcess_${hm.name}/PL_${hm.extractedProperty.pl_name}</source.uri>
        <target.uri>PL_${hm.extractedProperty.pl_name}</target.uri>
    </wire>
    <wire>
        <source.uri>bpelprocess_${hm.name?lower_case}_ep</source.uri>
        <target.uri>BPELProcess_${hm.name}/bpelprocess_${hm.name?lower_case}_ep</target.uri>
    </wire>
</composite>