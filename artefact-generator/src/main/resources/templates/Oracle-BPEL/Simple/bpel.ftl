<?xml version = "1.0" encoding = "UTF-8" ?>
<process name="BPELProcess_${hm.name}"
         targetNamespace="http://xmlns.oracle.com/${hm.name}/BPELProcess_${hm.name}"
         xmlns="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
         xmlns:client="http://xmlns.oracle.com/${hm.name}/BPELProcess_${hm.name}"
         xmlns:ora="http://schemas.oracle.com/xpath/extension"
         xmlns:bpelx="http://schemas.oracle.com/bpel/extension"
         xmlns:ns1="${hm.extractedProperty.bpel_namespace}"
         xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable"
         xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/"
         xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20"
         xmlns:oraext="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.ExtFunc"
         xmlns:dvm="http://www.oracle.com/XSL/Transform/java/oracle.tip.dvm.LookupValue"
         xmlns:hwf="http://xmlns.oracle.com/bpel/workflow/xpath"
         xmlns:ids="http://xmlns.oracle.com/bpel/services/IdentityService/xpath"
         xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions"
         xmlns:xdk="http://schemas.oracle.com/bpel/extension/xpath/function/xdk"
         xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions"
         xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap"
         xmlns:ns4="${hm.extractedProperty.bpel_xsd_namespace}"
         xmlns:ns10="${hm.extractedProperty.pl_namespace}"
         xmlns:ns11="${hm.extractedProperty.pl_xsd_namespace}"
         xmlns:xsd="http://www.w3.org/2001/XMLSchema"
         xmlns:ns18="http://schemas.xmlsoap.org/ws/2003/03/addressing"
         xmlns:wfcommon="http://xmlns.oracle.com/bpel/workflow/common"
         xmlns:wf="http://schemas.oracle.com/bpel/extensions/workflow">
  <!-- 
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
      PARTNERLINKS                                                      
      List of services participating in this BPEL process               
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  -->
  <import namespace="http://schemas.xmlsoap.org/ws/2003/03/addressing"
          location="xsd/Addressing.xsd"
          importType="http://www.w3.org/2001/XMLSchema"/>
  <import namespace="${hm.extractedProperty.pl_namespace}"
          location="${hm.property.bpel_wsdl_path}"
          importType="http://schemas.xmlsoap.org/wsdl/"/>          
  <import namespace="http://schemas.oracle.com/bpel/extension"
          location="RuntimeFault.wsdl"
          importType="http://schemas.xmlsoap.org/wsdl/"/>
  <partnerLinks>
    <!-- 
      The 'client' role represents the requester of this service. It is 
      used for callback. The location and correlation information associated
      with the client role are automatically set using WS-Addressing.
    -->
    
    
    <partnerLink name="PL_${hm.extractedProperty.pl_name}"
                 partnerLinkType="ns10:${hm.extractedProperty.pl_partnerLinkType}"
                 partnerRole="${hm.extractedProperty.pl_partnerRoleType}"/>
    <partnerLink name="bpelprocess_${hm.name?lower_case}_ep"
                 partnerLinkType="ns1:${hm.extractedProperty.bpel_partnerLinkType}"
                 myRole="${hm.extractedProperty.bpel_partnerRoleType}"/>
  </partnerLinks>
  <!-- 
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
      VARIABLES                                                        
      List of messages and XML documents used within this BPEL process 
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  -->
  <variables>
    <variable name="req_${hm.extractedProperty.bpel_operation}Op" messageType="ns1:${hm.extractedProperty.bpel_request_messageType}"/>
    <variable name="resp_${hm.extractedProperty.bpel_operation}Op" messageType="ns1:${hm.extractedProperty.bpel_response_messageType}"/>
    <variable name="v_operation" type="xsd:string"/>
    <variable name="C_OP_${hm.extractedProperty.bpel_operation}" type="xsd:string">
      <from>"${hm.extractedProperty.bpel_operation}"</from>
    </variable>
    <variable name="v_RuntimeFaultMessage" messageType="bpelx:RuntimeFaultMessage"/>
  </variables>
  <!-- 
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
     ORCHESTRATION LOGIC                                               
     Set of activities coordinating the flow of messages across the    
     services integrated within this business process                  
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  -->
  <sequence name="main">
    <!-- Receive input from requestor. -->
    <pick name="Pick_start_process" createInstance="yes">
      <onMessage partnerLink="bpelprocess_${hm.name?lower_case}_ep"
                 portType="ns1:${hm.extractedProperty.bpel_port_type}"
                 operation="${hm.extractedProperty.bpel_operation}"
                 variable="req_${hm.extractedProperty.bpel_operation}Op">
        <assign name="Assign_v_operation" xmlns="http://docs.oasis-open.org/wsbpel/2.0/process/executable">
          <copy>
            <from>$C_OP_${hm.extractedProperty.bpel_operation}</from>
            <to>$v_operation</to>
          </copy>
        </assign>
      </onMessage>
    </pick>
    <scope name="Scope_init" exitOnStandardFault="no">
      <assign name="Assign_set_EndpointReference">
        <copy>
          <from><literal><sref:service-ref xmlns:sref="http://docs.oasis-open.org/wsbpel/2.0/serviceref">
<EndpointReference xmlns="http://schemas.xmlsoap.org/ws/2003/03/addressing">
<Address>PLEASE_SET_THE_ENDPOINT</Address>
<ServiceName xmlns:ns661="${hm.extractedProperty.pl_namespace}">ns661:${hm.extractedProperty.pl_name}</ServiceName>
</EndpointReference>
</sref:service-ref></literal></from>
          <to partnerLink="PL_${hm.extractedProperty.pl_name}"/>
        </copy>
      </assign>
    </scope>
    <scope name="Scope_validate_req" exitOnStandardFault="no">
      <sequence name="Sequence4">
        <if name="by_operation">
          <documentation>v_operation = "${hm.extractedProperty.bpel_operation}"<![CDATA[$v_operation = $C_OP_${hm.extractedProperty.bpel_operation}]]></documentation>
          <condition>$v_operation = $C_OP_${hm.extractedProperty.bpel_operation}</condition>
          <sequence name="Sequence6">
            <scope name="Scope_validate_request2" exitOnStandardFault="no">
              <faultHandlers>
                <catch faultName="bpel:invalidVariables">
                  <sequence name="Sequence9">
                    <rethrow name="Rethrow4"/>
                  </sequence>
                </catch>
              </faultHandlers>
              <sequence>
                <validate name="Validate_req_${hm.extractedProperty.bpel_operation}Op"
                          variables="req_${hm.extractedProperty.bpel_operation}Op"/>
              </sequence>
            </scope>
          </sequence>
        </if>
      </sequence>
    </scope>
    <!-- Generate reply to synchronous request -->
    <scope name="Scope_call_${hm.extractedProperty.pl_name}" exitOnStandardFault="no">
      <faultHandlers>
        <catch faultName="bpelx:bindingFault"
               faultVariable="v_RuntimeFaultMessage"
               faultMessageType="bpelx:RuntimeFaultMessage"><rethrow name="Rethrow_bindingFault"
                                                                     xmlns="http://docs.oasis-open.org/wsbpel/2.0/process/executable"/></catch>
        <catch faultName="bpelx:remoteFault"
               faultVariable="v_RuntimeFaultMessage"
               faultMessageType="bpelx:RuntimeFaultMessage"><rethrow name="Rethrow_remoteFault"
                                                                     xmlns="http://docs.oasis-open.org/wsbpel/2.0/process/executable"/></catch>
      </faultHandlers>
      <sequence name="Sequence3">

          <if name="by_operation">
            <documentation>v_operation = "${hm.extractedProperty.bpel_operation}"</documentation>
            <condition>$v_operation = $C_OP_${hm.extractedProperty.bpel_operation}</condition>
            <scope name="Scope2">
      <variables>
        <variable name="req_${hm.extractedProperty.pl_name}"
                  messageType="ns10:${hm.extractedProperty.pl_request_messageType}"/>
        <variable name="resp_${hm.extractedProperty.pl_name}"
                  messageType="ns10:${hm.extractedProperty.pl_response_messageType}"/>
      </variables>
              <sequence>
                <assign name="Assign_set_req_${hm.extractedProperty.pl_name}">
                  <documentation>TODO - Please create the request of the ${hm.extractedProperty.pl_name} from the req_${hm.extractedProperty.bpel_operation}Op!</documentation>
                </assign>
                <invoke name="Invoke_PL_${hm.extractedProperty.pl_name}"
                        partnerLink="PL_${hm.extractedProperty.pl_name}"
                        portType="ns10:${hm.extractedProperty.pl_port_type}"
                        operation="${hm.extractedProperty.pl_operation}"
                        outputVariable="resp_${hm.extractedProperty.pl_name}"
                        bpelx:invokeAsDetail="no"
                        inputVariable="req_${hm.extractedProperty.pl_name}"/>
                <assign name="Assign_set_resp">
                  <documentation>TODO - Please create the response of the BPEL process from the resp_${hm.extractedProperty.pl_name}!</documentation>
                </assign>
                <reply name="replyOutput"
                       partnerLink="bpelprocess_${hm.name?lower_case}_ep"
                       portType="ns1:${hm.extractedProperty.bpel_partnerRoleType}"
                       operation="${hm.extractedProperty.bpel_operation}"
                       variable="resp_${hm.extractedProperty.bpel_operation}Op"/>
              </sequence>
            </scope>
          </if>
      </sequence>
    </scope>
  </sequence>
</process>