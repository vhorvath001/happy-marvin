<link rel="stylesheet" type="text/css" href="../../resources/css/smoothness/jquery-ui-1.10.4.custom.css" />
<link rel="stylesheet" type="text/css" href="../../resources/css/happy-marvin.css" />

<script type="text/javascript" src="../../resources/js/jquery/jquery-1.10.2.js" ></script>
<script type="text/javascript" src="../../resources/js/jquery/jquery-ui-1.10.4.custom.js" ></script>
   
<script type="text/javascript">
   var jq = jQuery.noConflict();
</script>
 
<script type="text/javascript">
   var instructionCreateInputBean;
   var selectedTypeOfTemplate;
   var selectedTemplate;
   var selectedTypeOfInstruction;
   var sentenceString;
   
   jq(document).ready(function() {
      // displaying the tabs
      jq('#div_fake_wizard').tabs( { event: '' } );

      // disable the next button on tab 1 / 2 / 3 / 4 / 5
      jq("#button_tab_1_next").attr("disabled", true);
      jq("#button_tab_2_next").attr("disabled", true);
      jq("#button_tab_3_next").attr("disabled", true);
      jq("#button_tab_4_next").attr("disabled", true);
      jq("#button_tab_5_next").attr("disabled", true);
      
      // adding click handler to the buttons next / prev
      jq("#button_tab_1_next").bind("click", function(event) {
         jq('#div_fake_wizard').tabs('option', 'active', 1);
      });
      jq("#button_tab_2_next").bind("click", function(event) {
         jq('#div_fake_wizard').tabs('option', 'active', 2);
      });
      jq("#button_tab_2_prev").bind("click", function(event) {
         jq('#div_fake_wizard').tabs('option', 'active', 0);
      });
      jq("#button_tab_3_next").bind("click", function(event) {
         jq('#div_fake_wizard').tabs('option', 'active', 3);
      });
      jq("#button_tab_3_prev").bind("click", function(event) {
         jq('#div_fake_wizard').tabs('option', 'active', 1);
      });
      jq("#button_tab_4_next").bind("click", function(event) {
         if (selectedTypeOfInstruction == "keyvalue") {
            jq('#div_fake_wizard').tabs('option', 'active', 5);
         } else {
            jq('#div_fake_wizard').tabs('option', 'active', 4);
         }
      });
      jq("#button_tab_4_prev").bind("click", function(event) {
         jq('#div_fake_wizard').tabs('option', 'active', 2);
      });
      jq("#button_tab_5_next").bind("click", function(event) {
         jq('#div_fake_wizard').tabs('option', 'active', 5);
      });
      jq("#button_tab_5_prev").bind("click", function(event) {
         jq('#div_fake_wizard').tabs('option', 'active', 3);
      });
      jq("#button_tab_999_prev").bind("click", function(event) {
         if (selectedTypeOfInstruction == "keyvalue") {
            jq('#div_fake_wizard').tabs('option', 'active', 3);
         } else {
            jq('#div_fake_wizard').tabs('option', 'active', 4);
         }
      });

      // displaying a modal Loading... window
      jq("#div_loading_modal_dialog").dialog({
          height: 70,
          modal: true
      });
      jq("#div_loading_modal_dialog").closest('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
      jq("#div_progressBar").progressbar({
         value: false
      });
      
      // loading the instructionCreateInputBean
      jq.post("create/loadData", callbackDataArrived);

      jq('#div_fake_wizard').tabs('option', 'active', 0);
   });
   
   function callbackDataArrived(_data) {
      instructionCreateInputBean = _data;
      jq("#div_loading_modal_dialog").dialog("close");
      
      initializeTab(1);
   }

   function initializeTab(tabNr) {
      // tab type of template
      if (tabNr == 1) {
         // building the type table
         html = "<table id='table_type' class='result_table'><tr><th>Type</th></tr>";
         var i = 0;
         for (var key in instructionCreateInputBean.typeTemplatesTextfields) {
            if (instructionCreateInputBean.typeTemplatesTextfields.hasOwnProperty(key)) {
               var rowBackgroundColor = "";
               if (i % 2 === 0) {
                  rowBackgroundColor = "class='alteration_row_table'";
               }
               html += "<tr " + rowBackgroundColor + "><td>" + key + "</td></tr>";
               i += 1;
            }
         }
         html += "</table>";

         jq("#div_tab_1_table").html(html);

         // adding a handler to select a row
         var trs = jq("#table_type").find("tr");
         trs.bind("click", function(event) {
            // highlighting the actual row
            trs.removeClass("highlight_in_table");
            jq(this).addClass("highlight_in_table");
            // disable the next button on tab 1
            jq("#button_tab_1_next").attr("disabled", false);
            // getting the selected type
            selectedTypeOfTemplate = jq(this).closest('tr').text();
            // clearing and initializing the tabs
            clearTabs(2);
            initializeTab(2);
         });
	  }
      
      // tab template
      else if (tabNr == 2) {
         // building the type table
         html = "<table id='table_template' class='result_table'><tr><th>Template</th></tr>";
         for (var key in instructionCreateInputBean.typeTemplatesTextfields[selectedTypeOfTemplate]) {
            if (instructionCreateInputBean.typeTemplatesTextfields[selectedTypeOfTemplate].hasOwnProperty(key)) {
               var rowBackgroundColor = "";
               if (i % 2 === 0) {
                  rowBackgroundColor = "class='alteration_row_table'";
               }
               html += "<tr " + rowBackgroundColor + "><td>" + key + "</td></tr>";
            }
         }
         html += "</table>";

         jq("#div_tab_2_template").html(html);

         // adding a handler to select a row
         var trs = jq("#table_template").find("tr");
         trs.bind("click", function(event) {
            trs.removeClass("highlight_in_table");
            jq(this).addClass("highlight_in_table");
            // disable the next button on tab 2
            jq("#button_tab_2_next").attr("disabled", false);
            // getting the selected template
            selectedTemplate = jq(this).closest('tr').text();
            // clearing and initializing the tabs
            clearTabs(3);
            initializeTab(3);
         });
      }
      
      // tab texfields
      else if (tabNr == 3) {
         // building the textfield
         html = "<table>";
         html += "<tr><td>Type:</td><td>" + selectedTypeOfTemplate + "</td></tr>";
         html += "<tr><td>Template:</td><td>" + selectedTemplate  + "</td></tr>";
         for (var i in instructionCreateInputBean.typeTemplatesTextfields[selectedTypeOfTemplate][selectedTemplate]) {
            name = instructionCreateInputBean.typeTemplatesTextfields[selectedTypeOfTemplate][selectedTemplate][i].name;
            mandatory = instructionCreateInputBean.typeTemplatesTextfields[selectedTypeOfTemplate][selectedTemplate][i].mandatory;
            label = instructionCreateInputBean.typeTemplatesTextfields[selectedTypeOfTemplate][selectedTemplate][i].textfieldLabel;
            if (label == "" || label == null) {
               label = instructionCreateInputBean.typeTemplatesTextfields[selectedTypeOfTemplate][selectedTemplate][i].text;
            }
            html += "<tr><td>" + label + ":</td><td>" + "<input type='text' id='tf_"+name+"' size='70'></td><td>";
            if (mandatory.toUpperCase() == "TRUE" || mandatory.toUpperCase() == "YES") {
               html += "(mandatory)";
            }
            html += "</td></tr>";
         }
         html += "</table>";

         jq("#div_tab_3_textfields").html(html);

         // adding event handler to the textfields
         for (var i in instructionCreateInputBean.typeTemplatesTextfields[selectedTypeOfTemplate][selectedTemplate]) {
            name = instructionCreateInputBean.typeTemplatesTextfields[selectedTypeOfTemplate][selectedTemplate][i].name;
            jq("#tf_"+name).change(checkAllMandetoryTextfieldsFilled);
         }
      }
      
      // tab type of instruction
      if (tabNr == 4) {
         // building the radio buttons
         html = "";
         html += "<input type='radio' name='radio_instr_type' id='radio_instr_type_keyvalue' value='keyvalue'>Key-value</input>";
         html += "<input type='radio' name='radio_instr_type' id='radio_instr_type_sentence' value='sentence'>Sentence</input>";

         jq("#div_tab_4_choose").html(html);

         // adding a handler to select a radio
         jq("input[name='radio_instr_type']").change(function(){
        	 jq("#button_tab_4_next").attr("disabled", false);
        	 selectedTypeOfInstruction = jq('input[name=radio_instr_type]').filter(':checked').val();
        	 doNextStepAfterTypeOfInstruction();
         });
      }

      // tab sentences
      if (tabNr == 5) {
         // building the sentence table
         html = "<table id='table_sentences' class='result_table'><tr><th>Sentence</th></tr>";
         allSentences = getAllSentences();
         var i = 0;
         for (var i in allSentences) {
               var rowBackgroundColor = "";
               if (i % 2 === 0) {
                  rowBackgroundColor = "class='alteration_row_table'";
               }
               html += "<tr " + rowBackgroundColor + "><td>" + allSentences[i] + "</td></tr>";
         }
         html += "</table>";

         jq("#div_tab_5_sentences").html(html);

         // adding a handler to select a row
         var trs = jq("#table_sentences").find("tr");
         trs.bind("click", function(event) {
            // highlighting the actual row
            trs.removeClass("highlight_in_table");
            jq(this).addClass("highlight_in_table");
            // disable the next button on tab 1
            //jq("#button_tab_1_next").attr("disabled", false);
         });
      }

      // tab THE RESULT
      if (tabNr == 999) {
         // building the sentences
         html = "<b>" + buildSentence() + "</b>";

         jq("#div_tab_999_result").html(html);
      }
   }
   
   function getAllSentences(notInProperty, inProperties) {
      allPatterns = instructionCreateInputBean.defaultSentences;
      allPatterns.concat(instructionCreateInputBean.templateDependantSentences[selectedTypeOfTemplate+"-"+selectedTemplate]);

      for (var i in allPatterns) {
         // if the notInPorperty is in the pattern ...
         if (allPatterns[i].indexOf("${"+notInProperty+"}") > -1) {
            // ... and the inProperties are not in the pattern
            candidate = true;
            for (var j in inProperties) {
               if (allPatterns[i].indexOf("${"+inProperties[j]+"}") > -1) {
                  candidate = false;
                  break;
               }
            }
            if (candidate) {
               // this pattern can be chosen so put it to the list
               
            }
         }
      }
      return allSentences;
   }

   function doNextStepAfterTypeOfInstruction() {
      // if key-value
      if (selectedTypeOfInstruction == "keyvalue") {
    	  // sending a post request to get result
          //jq.post("create/getSentence", {type: selectedTypeOfTemplate, template: selectedTemplate }, function(_sentenceString){        	  sentenceString = _sentenceString;          });
    	  // displaying the result
          initializeTab(999);
      } else {
         initializeTab(5);
      }
   }
   
   function buildSentence() {
      html = "TYPE:" + selectedTypeOfTemplate + "<br>";
      html += "TEMPLATE:" + selectedTemplate + "<br>";
      for (var i in instructionCreateInputBean.typeTemplatesTextfields[selectedTypeOfTemplate][selectedTemplate]) {
         name = instructionCreateInputBean.typeTemplatesTextfields[selectedTypeOfTemplate][selectedTemplate][i].name;
         text = instructionCreateInputBean.typeTemplatesTextfields[selectedTypeOfTemplate][selectedTemplate][i].text;
         value = jq("#tf_"+name).val();
         html += text + ":" + value + "<br>";
      }
      return html;
   }

   function checkAllMandetoryTextfieldsFilled() {
      for (var i in instructionCreateInputBean.typeTemplatesTextfields[selectedTypeOfTemplate][selectedTemplate]) {
         name = instructionCreateInputBean.typeTemplatesTextfields[selectedTypeOfTemplate][selectedTemplate][i].name;
         mandatory = instructionCreateInputBean.typeTemplatesTextfields[selectedTypeOfTemplate][selectedTemplate][i].mandatory;
         if (mandatory.toUpperCase() == "TRUE" || mandatory.toUpperCase() == "YES") {
            if (jq("#tf_"+name).val() == "" || jq("#tf_"+name).val() == null) {
               jq("#button_tab_3_next").attr("disabled", true);
               return;
            }
         }
      }
      jq("#button_tab_3_next").attr("disabled", false);
      // clearing and initializing the tabs
      clearTabs(4);
      initializeTab(4);
   }

   function clearTabs(actualTab) {
      if (actualTab <= 999) {
         jq("#div_tab_last_result").html("");
      } 
      if (actualTab <= 5) {
          jq("#div_tab_5_sentences").html("");
          jq("#button_tab_5_next").attr("disabled", true);
       } 
      if (actualTab <= 4) {
         jq("#div_tab_4_choose").html("");
         jq("#button_tab_4_next").attr("disabled", true);
      } 
      if (actualTab <= 3) {
         jq("#div_tab_3_textfields").html("");
         jq("#button_tab_3_next").attr("disabled", true);
      } 
      if (actualTab <= 2) {
         jq("#div_tab_2_template").html("");
         jq("#button_tab_2_next").attr("disabled", true);
      }
   }
   
   jq(document).ajaxError(function (e, xhr, settings, exception) {
      jq("#div_loading_modal_dialog").dialog("close");
      var jsonExceptionResponse = jQuery.parseJSON(xhr.responseText);
      jq("#div_error_modal_dialog").append(jsonExceptionResponse.message);
      jq("#div_error_modal_dialog").dialog({
         height: 140,
         modal: true
      });
   });
</script>




<div id="div_fake_wizard">
   <ul>
      <li><a href="#div_tab_1">Type of Template<a></a></li>
      <li><a href="#div_tab_2">Template<a></a></li>
      <li><a href="#div_tab_3">Values<a></a></li>
      <li><a href="#div_tab_4">Type of Instruction<a></a></li>
      <li><a href="#div_tab_5">Sentences<a></a></li>
      <li><a href="#div_tab_999">THE RESULT<a></a></li>
   </ul>
   
   <div id="div_tab_1">
      <h3>Choose a type!</h3>
      <div style="padding:30px"><div id="div_tab_1_table" class="div_tab_table"></div></div>
      <div>
         <input id="button_tab_1_next" type="submit" value="Next ->"/>
      </div>
   </div>
   
   <div id="div_tab_2">
      <h3>Choose a template!</h3>
      <div style="padding:30px"><div id="div_tab_2_template" class="div_tab_table"></div></div>
      <div class="wizard_buttons">
         <input id="button_tab_2_prev" type="submit" value="<- Previous"/>
         <input id="button_tab_2_next" type="submit" value="Next ->"/>
      </div>
   </div>

   <div id="div_tab_3">
      <h3>Please fill the textfields!</h3>
      <div style="padding:30px"><div id="div_tab_3_textfields"></div></div>
      <div class="wizard_buttons">
         <input id="button_tab_3_prev" type="submit" value="<- Previous"/>
         <input id="button_tab_3_next" type="submit" value="Next ->"/>
      </div>
   </div>

   <div id="div_tab_4">
      <h3>Please choose the type of the instructions!</h3>
      <div style="padding:30px"><div id="div_tab_4_choose"></div></div>
      <div class="wizard_buttons">
         <input id="button_tab_4_prev" type="submit" value="<- Previous"/>
         <input id="button_tab_4_next" type="submit" value="Next ->"/>
      </div>
   </div>

   <div id="div_tab_5">
      <h3>Please choose the sentences to be used!</h3>
      <div style="padding:30px"><div id="div_tab_5_sentences"></div></div>
      <div class="wizard_buttons">
         <input id="button_tab_5_prev" type="submit" value="<- Previous"/>
         <input id="button_tab_5_next" type="submit" value="Next ->"/>
      </div>
   </div>

   <div id="div_tab_999">
      <h3>The created instruction</h3>
      <div style="padding:30px"><div id="div_tab_999_result"></div></div>
      <div class="wizard_buttons">
         <input id="button_tab_999_prev" type="submit" value="<- Previous"/>
      </div>
   </div>

</div>

<div id="div_error_modal_dialog" title="Error happened!"></div>

<div id="div_loading_modal_dialog" title="Loading...">
   <div id="div_progressBar"/>
</div>