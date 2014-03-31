<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <title>Check instruction</title>
   
   <link rel="stylesheet" type="text/css" href="../../resources/css/smoothness/jquery-ui-1.10.4.custom.css" />
   
   <style type="text/css">
      .panel {
         background-color: #ccc;
         border:solid 2px black;
         display: none;
         padding: 10px;
      }
     
      #div_result {
          width: 650px;
          height: 700px;
      }

      #div_result_header {
          width: 630px
      }

      #div_result_body {
          width: 630px
          height: 690px;
      }
 
       #div_error_modal_dialog {
          display: none;
      }

      #div_progressbar {
         background-color: #ccc;
      }
     
      #div_ta {
         border: solid 2px #c3c3c3; 
         width: 450px
      }
   </style>

   <script type="text/javascript" src="../../resources/js/jquery/jquery-1.10.2.js" ></script>
   <script type="text/javascript" src="../../resources/js/jquery/jquery-ui-1.10.4.custom.js" ></script>
   
   <script type="text/javascript">
      var jq = jQuery.noConflict();
   </script>
 
   <script type="text/javascript">
   function callbackResponseArrived(data) {
      // replacing the header text to the text 'There are X instruction(s)'
	  var headerSentence = "<b>There is one instruction.</b>";
	  if (data.length > 1) {
         headerSentence = "<b>There are " + data.length + " instructions.</b>";
      }
      jq("#div_result_header").html(headerSentence);
      // sliding down the div_result_body that contains the sentencePattern pairs and info getting from the instruction text
      jq("#div_result_body").show("scale");

      // building the accordion
      for(var i = 0; i < data.length; i++) {
         jq("#div_accordion").append("<h3>Instruction "+i+"</h3>");
         jq("#div_accordion").append("<div><table border=1 id='table_sentencePatternPairs_"+i+"' align='center'><tr><th>Sentence</th><th>Pattern</th></tr></table></div>");
      }
      jq( "#div_accordion" ).accordion();

      // building the sentence-pattern table
      for(var i = 0; i < data.length; i++) {
         for(var j = 0; j < data[i].sentencePatternPairs.length; j++) {
            jq("#table_sentencePatternPairs_"+i).append("<tr><td>"+data[i].sentencePatternPairs[j][1]+"</td><td>"+data[i].sentencePatternPairs[j][0]+"</td></tr>");
         }
      }
   }
   
   function callbackDisplayingHeader() {
      jq.post("check",
              { instructionText: jq("#taInstruction").val() },
              callbackResponseArrived);
   }
   
   function check() {
      jq(document).ready(function() {
         // hiding the previous result
         jq("#div_result").hide();
         jq("#div_result_header").hide();
         jq("#div_result_body").hide();
         
         jq("#div_result_header").html("<b>Please wait...</b>");
         
         jq("#div_result").show("slide", function() {
            jq("#div_result_header").show("scale", callbackDisplayingHeader);
         });
      });
   }

   jq(document).ajaxError(function (e, xhr, settings, exception) {
      var jsonExceptionResponse = jQuery.parseJSON(xhr.responseText);
      jq("#div_error_modal_dialog").append(jsonExceptionResponse.message);
      jq("#div_error_modal_dialog").dialog({
         height: 140,
         modal: true
      });
      
      //alert(jsonExceptionResponse.message);
   });
   </script>

</head>

<body>

   <table>
      <tr>
         <td>
            <h3>Check the instruction!</h3>

            <div id="div_ta">
               Please write the instruction:
               <textarea rows="10" cols="50" id="taInstruction"></textarea>
               <input type="submit" value="Check!" onclick="check()"/>
            </div>
         </td>
         <td>
            <div id="div_result" class="panel">
               <div id="div_result_header" class="panel"></div>
               <div id="div_result_body" class="panel">
                  <div id="div_accordion"></div>
               </div>
            </div>
         </td>
      </tr>
   </table>
   
   <div id="div_result1">
      <table border="1" id="table_sentencePatternPairs"></table>
   </div>
  
   <div id="div_result2">
   </div>

   <div id="div_error_modal_dialog" title="Error happened!"></div>
   
</body>

</html>