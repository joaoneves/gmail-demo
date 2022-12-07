package com.joaoneves.demo.gmail.api.contacts;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.Assert;

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
}
