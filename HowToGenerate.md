To create the artefact(s) based on instruction in the JIRA description is not a difficult task if the instruction is correct (with the help of the web application you can check the correctness of the instruction or even you can create a new one).
Before starting the artefact generation you have to make sure that the config files are also correct ([Main config file](HmConfigXml.md), [Template config file](HmTemplatesConfigXml.md)).

There are 2 operations that can be used:
  1. making report without creating the artefact (using usually to check if everything is OK) -> [ReportOperation](ReportOperation.md)
  1. artefact generation -> [GenerateOperation](GenerateOperation.md)

You can download the generation package from here: [Downloads](Downloads.md)

You have to unzip the ZIP file and set the config files that are in the folder config.
The log can be found in the folder log.

All you have to do is to run the **start\_generator.bat** and provide some parameters. The necessary parameter list can be found on the page [ReportOperation](ReportOperation.md) and [GenerateOperation](GenerateOperation.md).