<link rel="stylesheet" type="text/css" href="../../resources/css/smoothness/jquery-ui-1.10.4.custom.css" />
<link rel="stylesheet" type="text/css" href="../../resources/css/happy-marvin.css" />

<script type="text/javascript" src="../../resources/js/jquery/jquery-1.10.2.js" ></script>
<script type="text/javascript" src="../../resources/js/jquery/jquery-ui-1.10.4.custom.js" ></script>
   
<script type="text/javascript">
   var jq = jQuery.noConflict();
</script>
 
<script type="text/javascript">
function callbackResponseArrived(data) {
   // replacing the header text to the text 'There are X instruction(s)'
   var headerSentence = "<b>There isn't a valid instruction in the text.</b>";
   if (data.length === 1) {
      headerSentence = "<b>There is one valid instruction in the text.</b>";
   } else if (data.length > 1) {
      headerSentence = "<b>There are " + data.length + " valid instructions in the text.</b>";
   }
   jq("#div_result_header").html(headerSentence);

   // sliding down the div_result_body that contains the sentencePattern pairs and info getting from the instruction text
   jq("#div_result_body").show("scale");

   // building the accordion
   var html = "";
   for(var i = 0; i < data.length; i++) {
      html += "<h3>Instruction " + (i+1) + "</h3>";
      html += "<div>";
      // displaying the values from the instruction
      html += "Type: <b>" + data[i].instructionBean.type + "</b><br>";
      html += "Template: <b>" + data[i].instructionBean.template + "</b><br>";
      html += "Project: <b>" + data[i].instructionBean.project + "</b><br>";
      html += "Artefact name: <b>" + data[i].instructionBean.name + "</b><br>";
      html += "Artefact location: <b>" + data[i].instructionBean.location + "</b><br>";
      html += "Properties:<ul>";
      for (var key in data[i].instructionBean.properties) {
         if (data[i].instructionBean.properties.hasOwnProperty(key)) {
            html += "<li>" + key + ": <b>" + data[i].instructionBean.properties[key] + "</b></li>";
         }
      }
      html += "</ul>";

      // building the sentence-pattern table
      html += "<table id='table_sentencePatternPairs_"+i+"' class='result_table'><tr><th>Sentence</th><th>Pattern</th></tr>";
      for(var j = 0; j < data[i].sentencePatternPairs.length; j++) {
         var rowBackgroundColor = "";
         if (j % 2 === 0) {
            rowBackgroundColor = "class='alteration_row_table'";
         }
         html += "<tr "+rowBackgroundColor+"><td>"+data[i].sentencePatternPairs[j][1]+"</td><td>"+data[i].sentencePatternPairs[j][0]+"</td></tr>";
      }
      html += "</table></div>";
   }

   jq( "#div_accordion" ).html(html).accordion({
      heightStyle: "content"
   });
}
   
function callbackDisplayingHeader() {
   jq.post("check",
          { instructionText: jq("#taInstruction").val() },
          callbackResponseArrived);
}
   
function check() {
      // hiding the previous result
      jq("#div_result_header").empty();
      jq("#div_result_header").hide();
      jq("#div_result").hide();
      jq("#div_result_body").empty();
      jq("#div_result_body").html("<div id='div_accordion'></div>");
        
      jq("#div_result_header").html("<b>Please wait...</b>");
         
      jq("#div_result").show("slide", function() {
         jq("#div_result_header").show("scale", callbackDisplayingHeader);
      });
}

jq(document).ajaxError(function (e, xhr, settings, exception) {
   var jsonExceptionResponse = jQuery.parseJSON(xhr.responseText);
   jq("#div_error_modal_dialog").text(jsonExceptionResponse.message);
   jq("#div_error_modal_dialog").dialog({
      height: 200,
      modal: true
   });
});
</script>

<table>
   <tr>
      <td valign="top">
         <h2>Check the instruction!</h2>

         <div id="div_ta">
            <h3>Please write the instruction:</h3>
            <textarea rows="10" id="taInstruction"></textarea>
            <br>
            <input type="submit" value="Check!" onclick="check()"/>
         </div>
      </td>
      <td valign="top">
         <div id="div_result" class="panel_result_rear">
            <div id="div_result_header" class="panel_result_ahead"></div>
            <div id="div_result_body" class="panel_result_ahead">
               <div id="div_accordion"></div>
            </div>
         </div>
      </td>
   </tr>
</table>
   
<div id="div_error_modal_dialog" title="Error happened!"></div>


<script type="text/javascript">
sPageURL = window.location.search.substring(1);
sURLVariables = sPageURL.split('&');
for (var i = 0; i < sURLVariables.length; i++) {
   sParameterName = sURLVariables[i].split('=');
   if (sParameterName[0] == "instruction") {
      jq("#taInstruction").val(decodeURIComponent(sParameterName[1]));
   }
}
</script>