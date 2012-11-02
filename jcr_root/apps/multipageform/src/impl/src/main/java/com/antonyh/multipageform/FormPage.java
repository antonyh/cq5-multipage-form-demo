package com.antonyh.multipageform;

import java.util.ArrayList;
import java.util.List;

public class FormPage {

	private String pageName;
	private String nextPageName;
	
	private List<FormField> fields = new ArrayList<FormField>();
	
	public List<FormField> getFields(){
		return fields;
	}
	
	public FormPage(String pageName, String nextPageName, List<FormField> fieldlist){
		this.pageName = pageName;
		this.nextPageName = nextPageName;
		fields.addAll(fieldlist);
	}


	public String getPageName() {
		return pageName;
	}
	
	public String getNextPageName() {
		return nextPageName;
	}
	
}
