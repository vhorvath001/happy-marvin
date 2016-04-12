To generate the artefact the **start\_generator.bat** must be executed with the next parameters:
  1. the JIRA issue number; mandatory parameter
  1. the name of the operation - 'GENERATE'; mandatory parameter

Sample for generating the artefacts:
> `start_generator.bat INTSRV-97 GENERATE`

If there is a problem when generating a file (existing file or write permission problems) then the other files won't be generated either.

The generator will create all necessary folders and will use the existing ones. For example: if the location is **src/main/java/com/acme/factory/beans** but we only have the **src/main/java/com/acme** folder then it will generate the factory and the beans folders. However if the project folder (defined in the main configuration XML - [HmConfigXml](HmConfigXml.md)) doesn't exist then an exception will be thrown.
The generator won't overwrite an existing file, in this case an exception will be thrown.

A sample log that you can see on the standard output:

```
10:14:54.299 INFO  [c.g.h.o.ArtefactGenerationOrchestrator:39] - -----------------------------------------------------------
10:14:54.299 INFO  [c.g.h.o.ArtefactGenerationOrchestrator:40] - -------------------------- START --------------------------
10:14:54.299 INFO  [c.g.h.o.ArtefactGenerationOrchestrator:41] - -----------------------------------------------------------
10:14:54.300 INFO  [c.g.h.o.operation.GenerateOperation:49] - ----- 1/4 Calling the JIRA REST service -----
10:14:54.394 INFO  [c.g.h.jiraexplorer.RestClient:16] - Getting the JIRA issue from https://ibjira.uk.jpmorgan.com/jira15/rest/api/latest/issue/INTSRV-97
10:14:54.394 INFO  [c.g.h.jiraexplorer.RestClient:17] - Successful call!
10:14:54.394 INFO  [c.g.h.o.operation.GenerateOperation:54] - ----- 2/4 Starting the surface mining -----
10:14:54.395 INFO  [c.g.h.o.operation.GenerateOperation:56] - ----- 3/4 Starting the underground mining -----
10:14:54.438 INFO  [c.g.h.o.operation.GenerateOperation:60] - ----- 4/4 Starting the artefact(s) generation -----
10:14:54.481 INFO  [c.g.h.a.ArtefactGenerator:56] - Processing the 0th instruction - type = Java, template = JSR303Validator, name = PR2121
10:14:54.540 INFO  [c.g.h.a.ArtefactGenerator:43] - Artefacts to be created:
10:14:54.551 INFO  [c.g.h.a.ArtefactGenerator:47] -     Type:FOLDER             Name:C:/work/happy-marvin/temp/src/main/java/com/jpmorgan/ib/cp/tlem/validator
10:14:54.553 INFO  [c.g.h.a.ArtefactGenerator:47] -     Type:FILE               Name:C:/work/happy-marvin/temp/src/main/java/com/jpmorgan/ib/cp/tlem/validator/PR2121Validator.java
10:14:54.554 INFO  [c.g.h.a.ArtefactGenerator:47] -     Type:FILE               Name:C:/work/happy-marvin/temp/src/main/java/com/jpmorgan/ib/cp/tlem/validator/PR2121Constraint.java
10:14:54.555 INFO  [c.g.h.a.ArtefactGenerator:47] -     Type:FOLDER             Name:C:/work/happy-marvin/temp/src/main/resources/xmls
10:14:54.557 INFO  [c.g.h.a.ArtefactGenerator:47] -     Type:FILE               Name:C:/work/happy-marvin/temp/src/main/resources/xmls/PR2121.xml
10:14:54.557 INFO  [c.g.h.o.ArtefactGenerationOrchestrator:50] - --------------------------- END ---------------------------
```

In the log file much more detailed log can be found.