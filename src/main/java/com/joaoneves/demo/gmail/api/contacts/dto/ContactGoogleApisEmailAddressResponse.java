package com.joaoneves.demo.gmail.api.contacts.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ContactGoogleApisEmailAddressResponse implements Serializable {
	private static final long serialVersionUID = -2738355965983091827L;
	
	private String value;
}
