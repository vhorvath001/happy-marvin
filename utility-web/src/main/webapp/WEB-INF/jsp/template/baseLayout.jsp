<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<html>

<head>
   <title><tiles:insertAttribute name="title" ignore="true"/></title>
</head>

<body>

   <div id="baseLayout_container">
      <div id="baseLayout_header"> <tiles:insertAttribute name="header"/> </div>
      <div id="baseLayout_menu"> <tiles:insertAttribute name="menu"/> </div>
      <div id="baseLayout_body"> <tiles:insertAttribute name="body"/> </div>
      <div id="baseLayout_footer"> <tiles:insertAttribute name="footer"/> </div>
   </div>

</body>

</html>