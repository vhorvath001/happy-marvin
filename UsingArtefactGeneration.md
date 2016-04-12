The process is:
  1. creating the instruction(s) for the generator
  1. put it/them in the JIRA description
  1. starting the code generation on the local computer of the developer

The instruction has two types:
  * sentences (writing normal sentences and unsing patterns to process them)
  * key-pair values (see example below)
The [web application](CreateInstruction.md) can give us a great help to create the instruction.
There are 5 mandatory values that must exist in the instruction:

> <ol>
<blockquote><li>type - the type of the template</li>
<blockquote>e.g.: Java, .Net, C++, OSB, etc.<br>
</blockquote><li>template - the name of the template</li>
<blockquote>it depends on the type as templates belong to one specific type<br>
e.g.: POJO, stateless bean, JSR303Validator, etc. if the type is Java<br>
</blockquote><li>project - the name of the project where the artefact should be put to</li>
<blockquote>the location of the project is set in the hm-config.xml<br>
</blockquote><li>location - the location where the artefact should be created inside the project</li>
<li>name - the name of the artefact to be created</li>
</ol></blockquote>

There can be additional values that has to exist in the instruction. It depends on the chosen template and these values can be defined in the **hm-templates-config.xml** file, see the xpath in the XML: **/templates/template/property**.

Some sample:
  * key-value pair
```
	   TYPE:Java
       TEMPLATE: POJO
       PROJECT: tlem-validation-failures-report
       NAME:   EmailSender
       LOCATION:src/main/java/com/jpmorgan/ib/cp/tlem/validationFailuresReport/utils
       METHOD: int send(String emailTo, String filePath, String reportName)
```
  * sentence
> > `I'd need a 'POJO' 'Java' component in the project 'tlem-validation-failures-report'. The name should be 'EmailSender'. You should put it into the 'src/main/java/com/jpmorgan/ib/cp/tlem/validationFailuresReport/utils' folder. Please add a method to it: 'int send(String emailTo, String filePath, String reportName)'`

After creating the instruction it has to be put to the descriptuion of a JIRA issue. There is one limitiation, the end and the start of the instuction must be indicated with special characters. They can be amended in the **hm-config.xml**: **/config/instructionSeparationStart**, **/config/instructionSeparationEnd**
You can add more instructions to one description:

```
...
~~~HAPPYMARVIN-INSTRUCTION~~~
...
~~~HAPPYMARVIN-INSTRUCTION~~~
...
~~~HAPPYMARVIN-INSTRUCTIONS-END~~~
```

The instructions don't have to be put at the end of the description but you have to handle the instructions as a whole. It means that you mustn't add other text between two instructions.

You can define other then the current separation characters (`~~~HAPPYMARVIN-INSTRUCTION~~~`) so please don't be scared of the current ones. :)

The third step is to create the artefact(s). You have the chance to create a report first what artefacts will be created, what values will be mined from the description and so on.
It is important that the generator is going to create the artefacts if everything is fine. It means it won't happen that some of the artefacts will be created, other won't.

The generator won't overwrite an existing file.

Please see more information: [How to generate](HowToGenerate.md)