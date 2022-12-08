package com.joaoneves.demo.gmail.api.contacts.dto;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Slf4j
public class AuthorizationResponse {
	private String accessToken;
	
	public static AuthorizationResponse deserialize(String payload) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			SimpleModule module =
			  new SimpleModule("CustomCarDeserializer", new Version(1, 0, 0, null, null, null));
			module.addDeserializer(AuthorizationResponse.class, new AuthorizationDeserializerResponse());
			mapper.registerModule(module);
			return mapper.readValue(payload, AuthorizationResponse.class);
		} catch (Exception e) {
			log.error("error deserializing AuthorizationResponse", e);
		}
		return null;
	}
}
