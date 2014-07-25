<?xml version="1.0" encoding="UTF-8" ?>
<componentType xmlns="http://xmlns.oracle.com/sca/1.0"
               xmlns:xs="http://www.w3.org/2001/XMLSchema"
               xmlns:ui="http://xmlns.oracle.com/soa/designer/">
    <service name="bpelprocess_${hm.name?lower_case}_ep"
             ui:wsdlLocation="${hm.property.bpel_wsdl_path}">
        <interface.wsdl interface="${hm.extractedProperty.bpel_namespace}#wsdl.interface(${hm.extractedProperty.bpel_port_type})"/>
    </service>
    <reference name="PL_${hm.extractedProperty.pl_name}"
               ui:wsdlLocation="${hm.property.pl_wsdl_path}">
        <interface.wsdl interface="${hm.extractedProperty.pl_namespace}#wsdl.interface(${hm.extractedProperty.pl_port_type})"/>
    </reference>
</componentType>