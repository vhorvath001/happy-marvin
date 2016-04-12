# OSB (Oracle Service Bus) / Proxy template #

An OSB Proxy service will be generated. A service call to the generated business service will be put into the message flow of the proxy service.
A message flow will contain the following steps:
  1. checking the name of the operation in the inbound request
  1. validating the inbound requests against the schema of the proxy service
  1. xquery transformation for creating the request of the business service
  1. error handler
The following files have to be copied to the resource folder of the script before generating (a lot of information will be extracted from these files):
  1. WSDL of the proxy service
  1. XSD of the proxy service
  1. WSDL of the business service
  1. XSD of the business service
The name of the files mustn't be modified! However you don't have to put the files into the same folder structure, only copy the files to the resource folder.

**Generated files:**
  1. business service
  1. webservice proxy service that contains the business logic

**Properties:**
  * proxy\_wsdl\_path : the path where the WSDL of the proxy service is in the OSB project
  * proxy\_xsd\_path : the path where the XSD of the proxy service is in the OSB project
  * location\_business\_service : the location where the business service will be generated to
  * business\_wsdl\_path : the path where the WSDL of the business service is in the OSB project
  * business\_xsd\_path : the path where the XSD of the business service is in the OSB project
  * xquery\_path : the path where the xquery is in the OSB project
  * osb\_project\_name : the name of the Oracle Service Bus project

**Sample files:**
The source of OSB is XML which is not very spectacular.

**Sample instruction (sentence):**
```
~~~HAPPYMARVIN-INSTRUCTION~~~
Please create a 'Proxy' 'OSB' component in the project 'tlem-validation-failures-report'. The name should be 'GetCustomer'. 
The destination folder should be 'proxy'. The WSDL file of the proxy service can be found in the folder 'proxy/wsdl/WSDL_NotifyInventoryChange.wsdl', the XSD is in 'proxy/xsd/NotifyInventoryChange.xsd'. 
The business service file should be put to 'business' folder. The WSDL of the business service can be found in the folder 'business/wsdl/WSDL_UpdateCableInventory.wsdl', the XSD file is in 'business/xsd/UpdateCableInventory.xsd'. The xquery file can be found in 'xquery/createSomethingRequest.xqy'. The name of the OSB project is 'generated-java'.
~~~HAPPYMARVIN-INSTRUCTIONS-END~~~
```