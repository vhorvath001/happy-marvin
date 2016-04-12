If we want to use normal english (or hungarian, german, ... ) sentences patterns have to be defined.
Basically 5 mandatory values (type, template, project, location, name) have to be put to the instruction in the JIRA description and perhaps some other values coming from the chosen template and the main purpose of the instruction is get these values. The instruction is the the sentences in the JIRA description.

These values are important as they are needed to generate the arterfact(s). The **type** and **template** values are needed to get the template from the **hm-templates-config.xml**, the **project** value is needed to get the root folder where the artefact will be created (hm-config.xml -> **/config/project**), the folder path where the files will be generated to comes from the **project** value and the **location** value and the **name** value will determine the name of the artefact.

The basic patterns (which can only contain the 5 mandatory values) are defined in the **hm-config.xml** (**/config/instructionSentencePatterns**). You can find more **instructionSentencePatterns** elements here and the reason of this is that I thought it would be better to define variations of the sentences that contain all the values and creating an other story of the sentences (in **/config/instructionSentencePatterns**) in a different instructionSentencePatterns element.

For example (just to make things easier I won't use the patterns but the final sentences):

Story 1:
> `I'd need a 'POJO' 'JAVA' class in the project 'ACME'. The name should be 'First'. Put it into the 'src/main/java/com/acme/beans' folder.`

Story 2:
> `Please put a 'JAVA' component in the project 'ACME'. Use the template 'POJO'. The template should be 'POJO'. Name it as 'First'. The destination folder is 'src/main/java/com/acme/beans'.`
You can use as many stories as you want. Even you can define more variations of the same sentence inside the story but you have to define the same values in the variations.
> `<sentence>I['d] need a '${template}' '${type}' [component]/[class]/[file]/[XML file] in the [project]/[folder] '${project}'.</sentence>`

> `<sentence>[A ]'${template}' '${type}' [component]/[class]/[file]/[XML file] [is ]needed in the project '${project}'.</sentence>`
You can find an example at the end of the page.

The template dependant sentences can be found in the **hm-templates-config.xml** (**/templates/template/property/textDef**).

Some rules regarding how to write the instructions:
  * You have to separate the sentences by a space
> > `Use the template 'POJO'. Name it as 'First'. The destination ...`
  * Apostrophe has to be used when defining the values
> > `Use the template 'POJO'.`
  * If the first few characters are optional in a pattern then the first character of the first mandatory word might be upper case or lower case.
> > `[Please ]put ...` -> can be `Please put...` or `Put...` => no need to define a different word starting with upper case P
  * Any character can be used in the value but I would refrain from using egsotic characters...


So a lot of patterns can be defined in the configuration XMLs. The generator will pick the first matching one and use that. So you should avoid patterns like that:

> `I['d] need a '${template}' '${type}' component.`
> `I['d] need a '${type}' '${template}' component.`
The patterns are identical except the order of the values. Please don't create such a pattern!


Sample:
```xml

<instructionSentencePatterns>

<sentence>I['d] need a '${template}' '${type}' [component]/[class]/[file]/[XML file] in the [project]/[folder] '${project}'.

Unknown end tag for &lt;/sentence&gt;



<sentence>[A ]'${template}' '${type}' [component]/[class]/[file]/[XML file] [is ]needed in the project '${project}'.

Unknown end tag for &lt;/sentence&gt;



<sentence>[Please ][put]/[create] [a ]'${template}' '${type}' [component]/[class]/[file]/[XML file] in the project '${project}'.

Unknown end tag for &lt;/sentence&gt;



<sentence>The name [should be]/[is] '${name}'.

Unknown end tag for &lt;/sentence&gt;



<sentence>[You should ]put it into the '${location}' folder.

Unknown end tag for &lt;/sentence&gt;





Unknown end tag for &lt;/instructionSentencePatterns&gt;



<instructionSentencePatterns>

<sentence>[Please ][put]/[create] a '${type}' component in the project '${project}'.

Unknown end tag for &lt;/sentence&gt;



<sentence>Use the template '${template}'.

Unknown end tag for &lt;/sentence&gt;



<sentence>The template [that should be used is]/[should be] '${template}'.

Unknown end tag for &lt;/sentence&gt;



<sentence>Name it as '${name}'.

Unknown end tag for &lt;/sentence&gt;



<sentence>The [destination ]folder [should be ]/[is ]'${location}'.

Unknown end tag for &lt;/sentence&gt;





Unknown end tag for &lt;/instructionSentencePatterns&gt;


```