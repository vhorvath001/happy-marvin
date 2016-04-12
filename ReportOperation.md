You can generate a report before generating a file. A text file will be generated in the main folder.
To generate the report you have to start the **start\_generator.bat** file with the next parameters:
  1. the JIRA issue number; mandatory parameter
  1. the name of the operation - 'REPORT'; mandatory parameter
  1. report file name; not mandatory parameter

If the report file name parameter is not defined then a name will be generated, e.g. REPORT-INTSRV-97-20140610-095004-746.txt

Sample for creating a report:
> `start_generator.bat INTSRV-97 REPORT`

A sample log that you can see on the standard output:

```
09:50:04.735 INFO  [c.g.h.o.ArtefactGenerationOrchestrator:39] - -----------------------------------------------------------
09:50:04.735 INFO  [c.g.h.o.ArtefactGenerationOrchestrator:40] - -------------------------- START --------------------------
09:50:04.740 INFO  [c.g.h.o.ArtefactGenerationOrchestrator:41] - -----------------------------------------------------------
09:50:04.746 INFO  [c.g.h.o.operation.GenerateOperation:60] - ----- 1/5 Calling the JIRA REST service -----
09:50:04.851 INFO  [c.g.h.jiraexplorer.RestClient:16] - Getting the JIRA issue from https://ibjira.uk.jpmorgan.com/jira15/rest/api/latest/issue/INTSRV-97
09:50:04.851 INFO  [c.g.h.jiraexplorer.RestClient:17] - Successful call!
09:50:04.852 INFO  [c.g.h.o.operation.GenerateOperation:65] - ----- 2/5 Starting the surface mining -----
09:50:04.857 INFO  [c.g.h.o.operation.GenerateOperation:67] - ----- 3/5 Starting the underground mining -----
09:50:04.903 INFO  [c.g.h.o.operation.GenerateOperation:71] - ----- 4/5 Getting the artefact information without generating them -----
09:50:04.954 INFO  [c.g.h.a.ArtefactGenerator:56] - Processing the 0th instruction - type = Java, template = JSR303Validator, name = PR2121
09:50:05.015 INFO  [c.g.h.o.operation.GenerateOperation:75] - ----- 5/5 Generating the report to REPORT-INTSRV-97-20140610-095004-746.txt -----
09:50:05.018 INFO  [c.g.h.o.ArtefactGenerationOrchestrator:50] - --------------------------- END ---------------------------
```

In the log file much more detailed log can be found.

And the content of the report text file:

```
JIRA description:
blahblah
~~~HAPPYMARVIN-INSTRUCTION~~~
Create a 'Java' component in the project 'tlem-validation-failures-report'. Use the template 'JSR303Validator'.
The name is 'PR2121'. The destination folder should be 'src/main/java/com/jpmorgan/ib/cp/tlem/validator'.
The config XML file should be in 'src/main/resources/xmls' folder.
~~~HAPPYMARVIN-INSTRUCTIONS-END~~~


**************************** The 0th instruction ****************************
	Type:      Java
	Template:  JSR303Validator
	Name:      PR2121
	Project:   tlem-validation-failures-report
	Location:  src/main/java/com/jpmorgan/ib/cp/tlem/validator
	Properties:
		location_Java_JSR303Validator_configXML:		src/main/resources/xmls
The artefacts to be created:
	Type: FOLDER,    Exist: false,	 Path: C:/work/happy-marvin/temp/src/main/java/com/jpmorgan/ib/cp/tlem/validator
	Type: FILE,      Exist: false,	 Path: C:/work/happy-marvin/temp/src/main/java/com/jpmorgan/ib/cp/tlem/validator/PR2121Validator.java
	Type: FILE,      Exist: false,	 Path: C:/work/happy-marvin/temp/src/main/java/com/jpmorgan/ib/cp/tlem/validator/PR2121Constraint.java
	Type: FOLDER,    Exist: false,	 Path: C:/work/happy-marvin/temp/src/main/resources/xmls
	Type: FILE,      Exist: false,	 Path: C:/work/happy-marvin/temp/src/main/resources/xmls/PR2121.xml
The used patterns:
	Create a 'Java' component in the project 'tlem-validation-failures-report'.		-	[Please ][put]/[create] a '${type}' component in the project '${project}'.
	Use the template 'JSR303Validator'.		-	Use the template '${template}'.
	The name is 'PR2121'.		-	The name [should be]/[is] '${name}'.
	The destination folder should be 'src/main/java/com/jpmorgan/ib/cp/tlem/validator'.		-	The [destination ]folder [should be ]/[is ]'${location}'.
	The config XML file should be in 'src/main/resources/xmls' folder.		-	The [JSR303 ][config]/[configuration] XML[ file] should be [put to]/[in] '${location_Java_JSR303Validator_configXML}' folder.
```