package com.joaoneves.demo.gmail.api.contacts;

import java.io.Serializable;

import org.springframework.util.Assert;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactDTO implements Serializable {

	private static final long serialVersionUID = -1775158531410932883L;
	
	private String code;
	
	private String email;
	
	private String name;
	
	public static ContactEntity from(ContactDTO dto) {
		Assert.notNull(dto, "Contact DTO object must not be null");
		return ContactEntity.builder()
				.name(dto.getName())
				.id(
					ContactEntityId.builder()
						.code(dto.getCode())
						.email(dto.getEmail())
						.build())
				.build();
	}
	
	public static ContactDTO to(ContactEntity entity) {
		Assert.notNull(entity, "Contact Entity object must not be null");
		return ContactDTO.builder()
				.code(entity.getId().getCode())
				.email(entity.getId().getEmail())
				.name(entity.getName())
				.build();
	}
}
