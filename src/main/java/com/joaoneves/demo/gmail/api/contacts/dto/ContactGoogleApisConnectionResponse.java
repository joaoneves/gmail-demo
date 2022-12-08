package com.joaoneves.demo.gmail.api.contacts.dto;

import java.io.Serializable;
import java.util.List;

import com.joaoneves.demo.gmail.api.contacts.ContactEntity;

import lombok.Data;

@Data
public class ContactGoogleApisConnectionResponse implements Serializable {
	private static final long serialVersionUID = 8238736094536068762L;

	private String resourceName;
	private List<ContactGoogleApisNameResponse> names;
	private List<ContactGoogleApisEmailAddressResponse> emailAddresses;

	public Boolean hasNamesOrEmailAddresses() {
		return (this.getNames() != null && !this.getNames().isEmpty())
				|| (this.getEmailAddresses() != null && !this.getEmailAddresses().isEmpty());
	}

	public static ContactEntity from(ContactGoogleApisConnectionResponse response) {
		return ContactEntity.builder().resourceName(response.getResourceName())
				.name(response.getNames() != null && !response.getNames().isEmpty()
						? response.getNames().get(0).getDisplayName()
						: null)
				.email(response.getEmailAddresses() != null && !response.getEmailAddresses().isEmpty()
						? response.getEmailAddresses().get(0).getValue()
						: null)
				.build();
	}
}
