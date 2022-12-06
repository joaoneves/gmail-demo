package com.joaoneves.demo.gmail.api.contacts;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Embeddable
@EqualsAndHashCode
public class ContactEntityId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	private String code;

	@Column(nullable = false)
	@Email
	private String email;
}
