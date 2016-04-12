The hm-config.xml is the main configuration file for the generator. Let's see what is inside.

```
<config>
    <patternType>simplified</patternType>
    <instructionSeparationStart>~~~HAPPYMARVIN-INSTRUCTION~~~</instructionSeparationStart>
    <instructionSeparationEnd>~~~HAPPYMARVIN-INSTRUCTIONS-END~~~</instructionSeparationEnd>
    <instructionSentencePatterns>
        <sentence>I['d] need a '${template}' '${type}' [component]/[class]/[file]/[XML file] in the [project]/[folder] '${project}'.</sentence>
        <sentence>[A ]'${template}' '${type}' [component]/[class]/[file]/[XML file] [is ]needed in the project '${project}'.</sentence>
        <sentence>[Please ][put]/[create] [a ]'${template}' '${type}' [component]/[class]/[file]/[XML file] in the project '${project}'.</sentence>
        <sentence>The name [should be]/[is] '${name}'.</sentence>
        <sentence>[You should ]put it into the '${location}' folder.</sentence>
    </instructionSentencePatterns>
    <instructionSentencePatterns>
        <sentence>[Please ][put]/[create] a '${type}' component in the project '${project}'.</sentence>
        <sentence>Use the template '${template}'.</sentence>
        <sentence>The template [that should be used is]/[should be] '${template}'.</sentence>
        <sentence>Name it as '${name}'.</sentence>
        <sentence>The [destination ]folder [should be ]/[is ]'${location}'.</sentence>
    </instructionSentencePatterns>
    <jiraRestUrl>https://jira.uk.acme.com/jira/rest/api/latest/issue</jiraRestUrl>
    <project name="acme-project" value="C:/work/happy-marvin/temp" srcFolder="src/main/java" />
    <project name="get-customer-project" value="C:/work/legacy/get-customer" srcFolder="src/main/java" />
</config>
```

  * **patternType**: the type of the pattern

> possible values:
    * **regularExpression** -> using regular expressions in the sentence patterns
    * **simplified** ->  using simplified regular expressions in the sentence patterns (this simplified regular expression is invented by and you can see above how it looks like)
  * **instructionSeparationStart**: the separation characters that indicate the beginning of the artefact generation instruction.
  * **instructionSeparationEnd**: the separation characters that indicate the end of the artefact generation instruction.
  * **instructionSentencePatterns**: defining an instruction pattern story. See: [Patterns](Patterns.md)
  * **instructionSentencePatterns/sentence**: defining a sentence pattern. You have to use normal or simplified regular expression according to the value of patternType.
  * **jiraRestUrl**: The endpoint URL of the JIRA. It is used for connecting to it with a REST client and getting the description (that contains the instruction) of the JIRA issue.
  * **project**: The element can contain the location(s) of one or more projects. In the JIRA instruction a project must be defined where the artefact will be generated to. As the artefacts will be generated locally so the location(s) of the project(s) must be set. The **srcFolder** attribute is used to find out the package name of the generated class. (for example: in the instruction the location 'src/main/java/com/acme/factory' is set for a class, we know that the src folder is 'src/main/java' so the package name should be 'com.acme.factory')