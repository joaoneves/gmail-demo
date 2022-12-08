package com.joaoneves.demo.gmail.api.contacts.dto;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.Assert;

import com.google.gson.Gson;
import com.joaoneves.demo.gmail.api.contacts.ContactEntity;

import lombok.Data;

@Data
public class ContactGoogleApisResponse implements Serializable {
	private static final long serialVersionUID = 8480026662688560600L;

	private List<ContactGoogleApisConnectionResponse> connections;

	public static List<ContactEntity> from(ContactGoogleApisResponse response) {
		Assert.notNull(response, "response cannot be null");
		Assert.notEmpty(response.getConnections(), "connections cannot be empty");
		return response.getConnections().stream().filter(ContactGoogleApisConnectionResponse::hasNamesOrEmailAddresses)
				.map(ContactGoogleApisConnectionResponse::from).collect(Collectors.toList());
	}
	
	public static ContactGoogleApisResponse deserialize(final String payload) {
		Gson gson = new Gson();
    	return gson.fromJson(payload, ContactGoogleApisResponse.class);
	}
}
