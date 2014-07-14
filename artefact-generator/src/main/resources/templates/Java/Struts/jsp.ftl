<%@ taglib prefix="s" uri="/struts-tags" %>

<h1>${hm.name} - Edit</h1>

<s:form action="save.action" method="post" theme="simple" validate="true">

   <table>
      <tr>
         <td>Name:</td>
         <td>
            <s:textfield name="name" size="36" maxlength="36"/>
         </td>
      </tr>
      <tr>
         <td colspan="2">
            <font color="red"><s:fielderror/></font>
         </td>
      </tr>
      <tr>
         <td>
            <s:submit name="buttonName" value="Save" align="center" theme="simple"/>
            <s:submit name="buttonName" value="Cancel" align="center" theme="simple"/>
         </td>
      </tr>
   </table>
   
</s:form>