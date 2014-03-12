<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <title>Check instruction</title>
   
   <style type="text/css">
      #div_result {
         background-color: #ccc;
         border:solid 2px black;
         display: none;
         padding: 10px;
         width: 250px
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
   function add() {
      jq(document).ready(function() {
      jq.post("check",
             { instructionText: jq("#taInstruction").val() },
             function(data) {
                jq("#div_result1").slideDown("slow", function() {
                   jq("#div_result2").slideDown("slow");
                });
             }
            )
      })
   }
   
   
   function callbackResponseArrived(data) {
      // replacing the progress bar to the text 'There are X instruction(s)'
      // sliding down the div_result_body that contains the sentencePattern pairs and info getting from the instruction text
   }
   
   function callbackDisplayingProgressBar() {
      jq.post("check",
              { instructionText: jq("#taInstruction").val() },
              callbackResponseArrived
             )
   }
   
   function new_add() {
      jq(document).ready(function() {
	  jq("#div_result").show();
         jq("#div_result_header").progressbar({ value: 37 });
         jq("#div_result_header").show("drop", callbackDisplayingProgressBar);
      })
   }
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
               <input type="submit" value="Check!" onclick="new_add()"/>
            </div>
         </td>
      </tr>
      <tr>
         <td>
            <div id="div_result">
               <div id="div_result_header"></div>
               <div id="div_result_body"></div>
            </div>
         </td>
      </tr>
   </table>
   
   
   
  
   <div id="div_result1">
      <table border="1" id="table_sentencePatternPairs"></table>
   </div>
  
   <div id="div_result2">
   </div>

</body>

</html>