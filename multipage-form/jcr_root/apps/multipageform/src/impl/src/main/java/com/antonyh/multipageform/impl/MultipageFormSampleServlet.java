package com.antonyh.multipageform.impl;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.servlets.OptingServlet;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.sling.api.resource.Resource;

import com.antonyh.multipageform.FormPage;

@Service
@Component
public class MultipageFormSampleServlet extends SlingAllMethodsServlet implements OptingServlet, Filter {

	static final long serialVersionUID = 1l;

	protected static final Logger log = LoggerFactory.getLogger(MultipageFormSampleServlet.class);

	@Property(value = "multipageform/components/sample")
	static final String SLING_SERVLET_RESOURCETYPES = "sling.servlet.resourceTypes";

	@Property(value = "POST")
	static final String SLING_SERVLET_METHODS = "sling.servlet.methods";

	@Property(value = "request", propertyPrivate = true)
	static final String FILTER_SCOPE = "filter.scope";

	@Property(value = "-600", propertyPrivate = true)
	static final String FILTER_ORDER = "filter.order";

	static final String ATTR_RESOURCE = "multipageform/components/sample/resource";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.sling.api.servlets.SlingAllMethodsServlet#doPost(org.apache
	 * .sling.api.SlingHttpServletRequest,
	 * org.apache.sling.api.SlingHttpServletResponse)
	 */
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {

		log.debug("POST into MultipageFormSampleServlet.");

		//put all the field values in the request attributes
		java.util.Enumeration fields = request.getParameterNames();
		while (fields.hasMoreElements()) {
			String field = (String) fields.nextElement();
			if (field.startsWith(":")) {
				continue; //skip parameters that start with a ':' - we don't need to keep these
			}

			request.setAttribute("multipageform/components/sample/form/" + field, request.getParameter(field));
		}

	 	List<FormPage> formPages = com.antonyh.multipageform.SampleUtil.getFormPages();
		
	 	
	 	//display page logic. defaults to first page in the list
		String displayPage = formPages.iterator().next().getPageName(); 
		if (request.getParameter("displayPage") != null) {
			displayPage = request.getParameter("displayPage");
		}
		if (displayPage.equals("null")) {
			displayPage = formPages.iterator().next().getPageName(); 
		}
		
		
		//Back & forward button logic
		
		if (request.getParameter("back") != null) {
			for(FormPage formPage : formPages){
				if (displayPage.equals(formPage.getNextPageName())) {
					displayPage=formPage.getPageName();
					break;
				}
			}
		}else if (request.getParameter("next") != null){
			for(FormPage formPage : formPages){
				if (displayPage.equals(formPage.getPageName())) {
					displayPage=formPage.getNextPageName();
					break;
				}
			}
		}
		
		request.setAttribute("multipageform/components/sample/form/displayPage", displayPage);
	

		// take user back to the form page.
		final Resource rsrc = (Resource) request.getAttribute(ATTR_RESOURCE);
		request.removeAttribute(ATTR_RESOURCE);
		SlingHttpServletRequest formsRequest = new FormsHandlingRequest(request);
		request.getRequestDispatcher(rsrc).forward(formsRequest, response);
		return;

	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.sling.api.servlets.OptingServlet#accepts(org.apache.sling.
	 * api.SlingHttpServletRequest)
	 */
	public boolean accepts(SlingHttpServletRequest request) {
		//only accept .html requests
		return "html".equals(request.getRequestPathInfo().getExtension());
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		if (request instanceof SlingHttpServletRequest) {
			final SlingHttpServletRequest req = (SlingHttpServletRequest) request;

			if ("POST".equalsIgnoreCase(req.getMethod()) && req.getParameter(":formtype") != null && req.getParameter(":formtype").equals("multipageform/components/sample")) {

				// store original resource as request attribute
				req.setAttribute(ATTR_RESOURCE, req.getResource());
				final StringBuilder sb = new StringBuilder();

				// forward to the path where the form component is, so that it
				// matches the "sling.servlet.resourceTypes" property define at
				// the beginning of this class. As a result, the doPost() method
				// is executed.
				final String formPath = req.getParameter(":formpath");
				if (!formPath.startsWith("/")) {
					sb.append(req.getResource().getPath());
					sb.append('/');
				}
				sb.append(formPath);
				sb.append(".html");

				// forward to forms handling servlet
				final String forwardPath = sb.toString();
				req.getRequestDispatcher(forwardPath).forward(request, response);

				return;
			}
		}

		chain.doFilter(request, response);
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig arg0) throws ServletException {
		// no init required.
	}

}
