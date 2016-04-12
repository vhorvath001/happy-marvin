# Oracle BPEL / Simple template #

The template is for generating a BPEL process with an invoke action in it to a partner link. A workflow will contain the following steps:
  1. setting the endpoint of the partner link dynamically
  1. validating the inbound requests against the schema of the bpel process
  1. calling the partner link
  1. fault handler
The following files have to be copied to the resource folder of the script before generating (a lot of information will be extracted from these files):
  1. WSDL of the BPEL process
  1. XSD of the BPEL process
  1. WSDL of the partner link
  1. XSD of the partner link
The name of the files mustn't be modified! However you don't have to put the files into the same folder structure, only copy the files to the resource folder.

**Generated files:**
  1. BPEL process XML
  1. component type XML
  1. composite XML

**Properties:**
  * bpel\_wsdl\_path : the path where the WSDL of the BPEL process is in the project
  * bpel\_xsd\_path : the path where the XSD of the BPEL process is in the project
  * pl\_wsdl\_path : the path where the WSDL of the partner link is in the project
  * pl\_xsd\_path : the path where the XSD of the partner link is in the project


**Sample files:**
The source of BPEL is XML which is not very spectacular.

**Sample instruction (sentence):**
```
~~~HAPPYMARVIN-INSTRUCTION~~~
Put a 'Simple' 'Oracle-BPEL' component in the project 'tlem-validation-failures-report'. Name it as 'GetCustomer'. The destination folder is '.'. 
The BPEL process WSDL can be found in the folder 'WSDL_NotifyInventoryChange.wsdl'. The BPEL XSD file can be found in 'xsd/NotifyInventoryChange.xsd'. 
The partner link WSDL can be found in 'WSDL_UpdateCableInventory.wsdl'. The path of the XSD file of the partner link is 'xsd/UpdateCableInventory.xsd'.
~~~HAPPYMARVIN-INSTRUCTIONS-END~~~
```