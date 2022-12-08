package com.joaoneves.demo.gmail.api;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthorizationDeserializerResponse extends StdDeserializer<AuthorizationResponse> {
	private static final long serialVersionUID = 2604353907953820819L;

	public AuthorizationDeserializerResponse() {
		this(null);
	}

	public AuthorizationDeserializerResponse(Class<?> vc) {
		super(vc);
	}

	@Override
	public AuthorizationResponse deserialize(JsonParser parser, DeserializationContext deserializer) {
		try {
			ObjectCodec codec = parser.getCodec();
			JsonNode node = codec.readTree(parser);
			JsonNode accessToken = node.get("access_token");
			
			return AuthorizationResponse.builder().accessToken(accessToken.asText()).build();
		} catch (Exception e) {
			log.error("error deserializing Authorization Response", e);
		}
		return null;
	}
}
