<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Check instruction</title>
  <script type="text/javascript" src="js/jquery-1.11.0.js" />
</head>

<body>

  <script type="text/javascript">
  function add() {
    jq(function() {
      jq.post("/web-utility/jquery/instruction/check",
              { instructionText: jq("#taInstruction").val() },
              function(data) {
                $(function() {
                  options = { to: { width: 280, height: 185 } };
                  $("#result").show("slide", options, 500);
                }
              });
    });
      
    $("result").hide();
  }
  </script>
  
  <h3>Check the instruction</h3>
  
  <div style="border: 1px solid #ccc; width: 250px">
    Please write the instruction:
    <textarea rows="10" cols="50" id="taInstruction"></textarea>
    <input type="submit" value="Check!" onclick="add()"/>
  </div>
  
  <div id="result" style="border: 1px solid #ccc; width: 250px">
    <label></label>
  </div>
  
</body>

</html>