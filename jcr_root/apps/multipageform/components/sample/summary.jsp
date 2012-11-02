<%@page import="com.antonyh.multipageform.*"%>
<%@page import="java.util.List"%>
<%@include file="/libs/foundation/global.jsp" %>
<%@taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling/1.0"%>
<sling:defineObjects/>
<%@ page contentType="text/html;charset=UTF-8" %><%
  List<FormField> fields = com.antonyh.multipageform.SampleUtil.getFieldNames();
  %>
<div>
  <%= com.antonyh.multipageform.SampleUtil.getText() %> SUMMARY

<form name="sampleform" id="sampleform" action="<%=resourceResolver.map(currentPage.getPath())%>.html" method="POST" enctype="multipart/form-data">
    <input type="hidden" name=":formpath" value="<%= currentNode.getPath() %>" />
    <input type="hidden" name=":formtype" value="<%= component.getResourceType() %>" />
    <input type="hidden" name=":redirect" value="<%= resourceResolver.map(currentPage.getPath())%>.thankyou.html" />
    <input type="hidden" name="_charset_" value="utf-8" />
    
    <input type="hidden" name="displayPage" value="<%=request.getAttribute("multipageform/components/sample/form/displayPage") %>"/>

<%

//simple display of the values as a summary
//use hidden fields to keep the data for use by the back button
for (FormField field: fields) { 
%>
     Field : 
     <input type="hidden" name="<%=field.getName() %>" value="<%=request.getAttribute("multipageform/components/sample/form/" + field.getName() ) %>"/>
    <%=request.getAttribute("multipageform/components/sample/form/" + field.getName() ) %><br/>
<%
} 
%>

<input type="submit" name="back" value="back"/>
    
    
  </form>
</div>