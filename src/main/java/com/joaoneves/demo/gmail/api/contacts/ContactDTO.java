package com.joaoneves.demo.gmail.api.contacts;

import java.io.Serializable;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class ContactDTO implements Serializable {

	private static final long serialVersionUID = -1775158531410932883L;
	
	private String resourceName;
	
	private String name;
	
	private String email;
	
	public static ContactDTO to(ContactEntity entity) {
		Assert.notNull(entity, "Contact Entity object must not be null");
		return ContactDTO.builder()
				.resourceName(entity.getResourceName())
				.name(entity.getName())
				.email(entity.getEmail())
				.build();
	}
}
