<%@page import="java.util.List" %><%
%><%@page import="com.antonyh.multipageform.*" %><%
%><%@include file="/libs/foundation/global.jsp"%>
<%
	//init; set first page
	if (request.getAttribute("multipageform/components/sample/form/displayPage") == null) {
		request.setAttribute("multipageform/components/sample/form/displayPage", "inputPage1");
	}

	//init; set field defaults
	List<FormField> fields = com.antonyh.multipageform.SampleUtil.getFieldNames();
	for (FormField field: fields) {
		if (request.getAttribute("multipageform/components/sample/form/" + field.getName()) == null) {
			request.setAttribute("multipageform/components/sample/form/" + field.getName(), "");
		}
	}

	//-------
	//very simple display logic. If it's an 'input' page use the input script, otherwise display a summary.
	//extend this as needed.
	
	String displayPage = (String) request.getAttribute("multipageform/components/sample/form/displayPage");
	if (displayPage == null || displayPage.startsWith("input")) {
%>
<cq:include script="input.jsp" />
<%
	} else /*if (displayPage.equals("summary"))*/ {
%>
<cq:include script="summary.jsp" />
<%
	}
%>