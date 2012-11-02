/*
 * Copyright 1997-2010 Day Management AG
 * Barfuesserplatz 6, 4001 Basel, Switzerland
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information of
 * Day Management AG, ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Day.
 */
package com.antonyh.multipageform;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class used by script.
 * 
 */
public class SampleUtil {

	//example only; this wouldn't be held in the object in a real application
	static final List<FormField> FIELDNAMES = new ArrayList<FormField>();

	//example only; this wouldn't be held in the object in a real application
	static final List<FormPage> FORMPAGES = new ArrayList<FormPage>();
	
	
	/* 
	 *  This block initialises the lists. In reality this would source data 
	 *  from webservices and/or CRX.
	 * 
	 */
	static{
		
		List<FormField> fields1 = new ArrayList<FormField>();
		fields1.add(new FormField("FieldA1"));
		FORMPAGES.add( new FormPage("inputPage1", "inputPage2", fields1));
		
		List<FormField> fields2 = new ArrayList<FormField>();
		fields2.add(new FormField("FieldB1"));
		FORMPAGES.add(new FormPage("inputPage2", "inputPage3", fields2));
		
		List<FormField> fields3 = new ArrayList<FormField>();
		fields3.add(new FormField("FieldC1"));
		FORMPAGES.add(new FormPage("inputPage3", "summary", fields3));

		for(FormPage formPage: FORMPAGES){
			FIELDNAMES.addAll(formPage.getFields());
		}
		
	}
	
	
	public static String getText() {
		return "Form Example";
	}

	
	public static List<FormField> getFieldNames() {
		ArrayList<FormField> x = new ArrayList<FormField>();
		x.addAll(FIELDNAMES); 
		return x;
	}

	
	public static List<FormPage> getFormPages() {
		ArrayList<FormPage> x = new ArrayList<FormPage>();
		x.addAll(FORMPAGES); 
		return x;
	}

}