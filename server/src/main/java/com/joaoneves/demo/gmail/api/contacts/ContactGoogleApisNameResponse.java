package com.joaoneves.demo.gmail.api.contacts;

import java.io.Serializable;

import lombok.Data;

@Data
public class ContactGoogleApisNameResponse implements Serializable {
	private static final long serialVersionUID = 2925599304726836503L;
	
	private String displayName;
}
