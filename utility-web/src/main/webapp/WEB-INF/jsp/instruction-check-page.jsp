<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <title>Check instruction</title>
   
   <style type="text/css">
      #result1, #result2 {
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

   <script type="text/javascript" src="../../resources/js/jquery/jquery-1.11.0.js" ></script>
   
   <script type="text/javascript">
      var jq = jQuery.noConflict();
   </script>
 
   <script type="text/javascript">
   function add() {
      jq(document).ready(function() {
      jq.post("check",
             { instructionText: jq("#taInstruction").val() },
             function(data) {
                jq("#result1").slideDown("slow", function() {
                   jq("#result2").slideDown("slow");
                });
             }
            )
      })
   }
   </script>

</head>

<body>

   <h3>Check the instruction</h3>
  
   <div id="div_ta">
      Please write the instruction:
      <textarea rows="10" cols="50" id="taInstruction"></textarea>
      <input type="submit" value="Check!" onclick="add()"/>
   </div>
  
   <div id="result1">   
      <table border="1">
         <tr>
            <th>Sentence</th>
            <th>Pattern</th>		
         </tr>
         <tr>
            <td>????</td>
            <td>????</td>		
         </tr>
      </table>
   </div>
  
   <div id="result2">
   </div>

</body>

</html>