package com.joaoneves.demo.gmail.api.contacts.dto;

import java.io.Serializable;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.joaoneves.demo.gmail.api.contacts.ContactEntity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class ContactResponse implements Serializable {

	private static final long serialVersionUID = -1775158531410932883L;
	
	private String resourceName;
	
	private String name;
	
	private String email;
	
	public static ContactResponse to(ContactEntity entity) {
		Assert.notNull(entity, "Contact Entity object must not be null");
		return ContactResponse.builder()
				.resourceName(entity.getResourceName())
				.name(entity.getName())
				.email(entity.getEmail())
				.build();
	}
}
