<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <title>Check instruction</title>
   
   <link rel="stylesheet" type="text/css" href="/css/jquery-ui.custom.css" />
   
   <style type="text/css">
      #div_result, #div_result_header, #div_result_body {
         background-color: #ccc;
         border:solid 2px black;
         display: none;
         padding: 10px;
         width: 250px
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
      jq("#div_result_header").html("<b>There are " + data.length + " instruction(s)</b>");
      // sliding down the div_result_body that contains the sentencePattern pairs and info getting from the instruction text
      jq("#div_result_body").show("scale");
      jq("#table_sentencePatternPairs").append("yeah");
   }
   
   function callbackDisplayingHeader() {
      jq.post("check",
              { instructionText: jq("#taInstruction").val() },
              callbackResponseArrived)
   }
   
   function check() {
      jq(document).ready(function() {
         jq("#div_result").hide();
         jq("#div_result_header").hide();
         jq("#div_result_body").hide();
         
         jq("#div_result_header").html("<b>Please wait...</b>");
         
         jq("#div_result").show("slide", function() {
            jq("#div_result_header").show("scale", callbackDisplayingHeader);
         });
      })
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
            <div id="div_result">
               <div id="div_result_header"></div>
               <div id="div_result_body">
                  <div id="div" style="overflow:auto; height: 100px">
                    <table border=1 id="table_sentencePatternPairs" align="center">
                      <tr>
                         <th class="col1">Sentence</th>
                         <th class="col2">Pattern</th>
                      </tr>

    <tr>
    <td>1</td>
    <td>2</td>
    <td>3</td>
    </tr>
    <tr>
    <td>1</td>
    <td>2</td>
    <td>3</td>
    </tr>
    <tr>
    <td>1</td>
    <td>2</td>
    <td>3</td>
    </tr>
    <tr>
    <td>1</td>
    <td>2</td>
    <td>3</td>
    </tr>
    <tr>
    <td>1</td>
    <td>2</td>
    <td>3</td>
    </tr>
    <tr>
    <td>1</td>
    <td>2</td>
    <td>3</td>
    </tr>
    <tr>
    <td>1</td>
    <td>2</td>
    <td>3</td>
    </tr>
</table>    
</div>
                  
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

   <div id="div_error_modal_dialog"><h4>Error happened!</h4><br></div>
</body>

</html>