<link rel="stylesheet" type="text/css" href="../../resources/css/smoothness/jquery-ui-1.10.4.custom.css" />
<link rel="stylesheet" type="text/css" href="../../resources/css/happy-marvin.css" />

<script type="text/javascript" src="../../resources/js/jquery/jquery-1.10.2.js" ></script>
<script type="text/javascript" src="../../resources/js/jquery/jquery-ui-1.10.4.custom.js" ></script>
   
<script type="text/javascript">
   var jq = jQuery.noConflict();
</script>
 
<script type="text/javascript">
   var instructionCreateInputBean;
   var selectedType;
   var selectedTemplate;
   
   jq(document).ready(function() {
      // displaying the tabs
      jq('#div_fake_wizard').tabs();
      // disable the next button on tab 1 / 2 / 3
      jq("#button_tab_1_next").attr("disabled", true);
      jq("#button_tab_2_next").attr("disabled", true);
      jq("#button_tab_3_next").attr("disabled", true);
      
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
      jq.post("create/loadData",
              callbackDataArrived);
   });
   
   function callbackDataArrived(_data) {
      instructionCreateInputBean = _data;
      jq("#div_loading_modal_dialog").dialog("close");
      
      initializeTab(1);
   }

   function initializeTab(tabNr) {
      // tab type
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
            jq("#wizardButton_next").removeClass("buttonDisabled");
            // disable the next button on tab 1
            jq("#button_tab_1_next").attr("disabled", false);
            // getting the selected type
            selectedType = jq(this).closest('tr').text();
            // clearing and initializing the tabs
            clearTabs(2);
            initializeTab(2);
         });
	  }
      // tab template
      else if (tabNr == 2) {
         // building the type table
         html = "<table id='table_template' class='result_table'><tr><th>Template</th></tr>";
         for (var key in instructionCreateInputBean.typeTemplatesTextfields[selectedType]) {
            if (instructionCreateInputBean.typeTemplatesTextfields[selectedType].hasOwnProperty(key)) {
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
            jq("#wizardButton_next").removeClass("buttonDisabled");
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
         html = "<table>";
         for (var i in instructionCreateInputBean.typeTemplatesTextfields[selectedType][selectedTemplate]) {
            name = instructionCreateInputBean.typeTemplatesTextfields[selectedType][selectedTemplate][i].name;
            mandatory = instructionCreateInputBean.typeTemplatesTextfields[selectedType][selectedTemplate][i].mandatory;
            label = instructionCreateInputBean.typeTemplatesTextfields[selectedType][selectedTemplate][i].textfieldLabel;
            if (label == "" || label == null) {
               label = instructionCreateInputBean.typeTemplatesTextfields[selectedType][selectedTemplate][i].text;
            }
            html += "<tr><td>" + label + ":</td><td>" + "<input type='text' id='tf'"+name+" size='70'></td></tr>";
            if (mandatory.toUpperCase() == "TRUE" || mandatory.toUpperCase() == "YES") {
               alert("IMPLEMENT!")
            }
         }
         html += "<input type='submit' value='Submit'></table>";
         jq("#div_tab_3_textfields").html(html);
      }
   }
   
   function clearTabs(actualTab) {
      if (actualTab <= 3) {
         jq("#div_tab_3_textfields").html("");
      } 
      if (actualTab <= 2) {
         jq("#div_tab_2_template").html("");
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
      <li><a href="#div_tab_1">Type<a></a></li>
      <li><a href="#div_tab_2">Template<a></a></li>
      <li><a href="#div_tab_3">Values<a></a></li>
   </ul>
   
   <div id="div_tab_1">
      <h2>Choose a type!</h2>
      <div style="padding:30px"><div id="div_tab_1_table" class="div_tab_table"></div></div>
      <div>
         <input id="button_tab_1_next" type="submit" value="Next ->"/>
      </div>
   </div>
   
   <div id="div_tab_2">
      <h2>Choose a template!</h2>
      <div style="padding:30px"><div id="div_tab_2_template" class="div_tab_table"></div></div>
      <div class="wizard_buttons">
         <input id="button_tab_2_prev" type="submit" value="<- Previous"/>
         <input id="button_tab_2_next" type="submit" value="Next ->"/>
      </div>
   </div>

   <div id="div_tab_3">
      <h2>Please fill the textfields!</h2>
      <div style="padding:30px"><div id="div_tab_3_textfields"></div></div>
      <div class="wizard_buttons">
         <input id="button_tab_3_prev" type="submit" value="<- Previous"/>
         <input id="button_tab_3_next" type="submit" value="Next ->"/>
      </div>
   </div>

</div>










<!-- <div id="div_wizard" class="swMain"> -->
<!--    <!-- building the headers of the steps -->
<!--    <ul> -->
<!--       <li> -->
<!--          <a href="#step-1"> -->
<!--             <span class="stepNumber">1</span> -->
<!--             <span class="stepDesc">Type<br> -->
<!--                <small>Choose a type!</small> -->
<!--             </span> -->
<!--          </a> -->
<!--       </li> -->
<!--       <li> -->
<!--          <a href="#step-2"> -->
<!--             <span class="stepNumber">2</span> -->
<!--             <span class="stepDesc">Template<br> -->
<!--                <small>Choose a template!</small> -->
<!--             </span> -->
<!--          </a> -->
<!--       </li> -->
<!--    </ul> -->
   
<!--    <!-- building the bodies of the steps -->
<!--    <div id="step-1"> -->
<!--       <h2 class="StepTitle">Type list</h2> -->
<!--       <div style="padding:30px"><div id="div_wizard_step1_table"></div></div> -->
<!--    </div> -->
<!--    <div id="step-2"> -->
<!--       <h2 class="StepTitle">Template list</h2> -->
<!--       valami list -->
<!--    </div> -->

<!-- </div> -->

<div id="div_error_modal_dialog" title="Error happened!"></div>

<div id="div_loading_modal_dialog" title="Loading...">
   <div id="div_progressBar"/>
</div>