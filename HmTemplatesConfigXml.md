The **hm-config.xml** is the main configuration file for the generator. Let's see what is inside.

```
<templates>
    <template name="POJO" type="Java">
        <file extension="java" path="Java/Java-POJO.ftl"/>
        <property name="method" text="METHOD" mandatory="true">
            <textDef>[Please ]add a method[ to it]: '${method}'[.]</textDef>
            <textDef>[A ]method [is ]needed: '${method}'.</textDef>
        </property>
    </template>
    <template name="JSR303Validator" type="Java">
        <file name="validator" extension="java" path="Java/Java_JSR303Validator_Validator.ftl" suffix="Validator"/>
        <file name="constraint" extension="java" path="Java/Java_JSR303Validator_Constraint.ftl" suffix="Constraint"/>
        <file name="configXML" extension="xml" path="XML/XML_JSR303Validator_configXML.ftl"/>
        <property name="location_Java_JSR303Validator_Validator" text="LOCATION_JAVA_JSR303VALIDATOR_VALIDATOR" 
                mandatory="false" locationOf="validator" textfieldLabel="The location of validator">
            <textDef>The [JSR303 ]validator class should be [put to]/[in] '${location_Java_JSR303Validator_Validator}' folder.</textDef>
            <textDef>[Please ]put the [JSR303 ]validator class to [the ]'${location_Java_JSR303Validator_Validator}'[ folder].</textDef>
        </property>
        <property name="location_Java_JSR303Validator_Constraint" text="LOCATION_JAVA_JSR303VALIDATOR_CONSTRAINT" 
                mandatory="false" locationOf="constraint" textfieldLabel="The location of constraint">
            <textDef>The [JSR303 ]constraint class should be [put to]/[in] '${location_Java_JSR303Validator_Constraint}' folder.</textDef>
            <textDef>[Please ]put the [JSR303 ]constraint class to [the ]'${location_Java_JSR303Validator_Constraint}'[ folder].</textDef>
        </property>
        <property name="location_Java_JSR303Validator_configXML" text="LOCATION_JAVA_JSR303VALIDATOR_CONFIGXML" 
                mandatory="false" locationOf="configXML" textfieldLabel="The location of config XML">
            <textDef>The [JSR303 ][config]/[configuration] XML[ file] should be [put to]/[in] '${location_Java_JSR303Validator_configXML}' folder.</textDef>
            <textDef>[Please ]put the [JSR303 ][config]/[configuration] XML[ file] to [the ]'${location_Java_JSR303Validator_configXML}'[ folder].</textDef>
        </property>
    </template>
</templates>
```

  * **template**: Group element to define a template.

> cardinality: 1..n

> Attributes:
    * **name**: the name of the template
    * **type**: the type of the template
  * **template/file**: defining the template file(s)

> cardinality: 1..n

> Attributes:
    * path: the location of the template file in artefact-generator\src\main\resources\templates
    * extension: the file extension of the generated artefact
  * **template/property**: defining the template-dependant properties

> cardinality: 0..n

> Attributes:
    * **name**: name of the attribute, used in the pattern
    * **text**: used when the instructions are defined in JIRA description as key-value pairs
    * **mandatory**: true or false
    * **locationOf**: It is mainly used when more template files are used and these files should be created in different locations. In the example above the location\_Java\_JSR303Validator\_Validator property is for defining the location of the Validator class file, its value is 'validator' which means that the property will define the location of the 'validator' template file (the first element of the template/file). Not mandatory attribute, if it is not set then all the files will be put in the default 'location'.
    * **textfieldLabel**: It is used in the web application when creating an instruction. Not mandatory attribute, if it is not set then the 'text' attribute will be used.
  * **template/property/textDef**: defining the patterns for the template