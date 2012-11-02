<%@page import="com.antonyh.multipageform.*"%>
<%@page import="java.util.List"%>
<%@include file="/libs/foundation/global.jsp"%>
<%@taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling/1.0"%>
<sling:defineObjects />
<%@ page contentType="text/html;charset=UTF-8"%><%

    String displayPage = (String) request.getAttribute("multipageform/components/sample/form/displayPage");

    //get form defintions
    List<FormField> allfields = com.antonyh.multipageform.SampleUtil.getFieldNames();
    List<FormPage> formPages = com.antonyh.multipageform.SampleUtil.getFormPages();
    
    //get the form object for the current form page
    FormPage formPage = formPages.iterator().next();
    for (FormPage formPageObj: formPages) {
        if (formPageObj.getPageName().equals(displayPage)){
            formPage = formPageObj;
            break;
        }
    }
 %>

<div><%=com.antonyh.multipageform.SampleUtil.getText()%>  <%=displayPage%>
<form name="sampleform" id="sampleform" action="<%=resourceResolver.map(currentPage.getPath())%>.html" method="POST" enctype="multipart/form-data">
	
	<input type="hidden" name=":formpath" value="<%=currentNode.getPath()%>" /> 
	<input type="hidden" name=":formtype" value="<%=component.getResourceType()%>" /> 
	<input type="hidden" name=":redirect" value="<%=resourceResolver.map(currentPage.getPath())%>.html" /> 
	<input type="hidden" name="_charset_" value="utf-8" /> 
	<input type="hidden" name="displayPage" value="<%=displayPage%>" /> 
	
	<%
 	//hide fields not on this page.
 	//show fields on this page.
 	//all fields are always present.
 	allfields.removeAll(formPage.getFields());
 	
 	for (FormField field: formPage.getFields()) {
	 %> Field <%=field.getName()%> <input type="text" name="<%=field.getName()%>"
		value="<%=request.getAttribute("multipageform/components/sample/form/" + field.getName())%>" /><br /><%
		}
 	
    for (FormField field: allfields) {
	 %><input type="hidden" name="<%=field.getName()%>"
	    value="<%=request.getAttribute("multipageform/components/sample/form/" + field.getName())%>" /><%
	    }  	
%> 

<input type="submit" name="back" value="back" /> 
<input type="submit" name="next" value="next" />
</form>
</div>