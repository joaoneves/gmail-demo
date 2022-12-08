package com.joaoneves.demo.gmail.api.contacts;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.gson.Gson;
import com.joaoneves.demo.gmail.api.AuthorizationDeserializerResponse;
import com.joaoneves.demo.gmail.api.AuthorizationResponse;
import com.joaoneves.demo.gmail.api.HttpClient;
import com.joaoneves.demo.gmail.api.OAuthRequestPayload;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ContactService {
	
	@Value("${custom.clientId}")
	private String clientId;
	
	@Value("${custom.clientSecret}")
	private String clientSecret;

	private final ContactRepository contactRepository;

	@Autowired
	public ContactService(ContactRepository contactRepository) {
		this.contactRepository = contactRepository;
	}
	
	public String retrieveAccessToken(final String code) throws Exception {
		HttpClient client = new HttpClient();
		try {
			OAuthRequestPayload request = OAuthRequestPayload
					.builder()
					.clientId(this.clientId)
					.clientSecret(this.clientSecret)
					.code(code)
					.build();
			
//			ObjectMapper objMapper = new ObjectMapper(new JsonFactory()); // or YAMLFactory()
//			objMapper.setPropertyNamingStrategy(
//			     PropertyNamingStrategies.SNAKE_CASE);
//			
//			String payload = objMapper.writeValueAsString(request);
			
			Gson gson = new Gson();
//			String payload = gson.toJson(request);
			
			String responseClient = client.post("https://accounts.google.com/o/oauth2/token", 
					OAuthRequestPayload.toNameValuePairArray(request));
			
//			ObjectMapper objectMapper = new ObjectMapper(new JsonFactory()); // or YAMLFactory()
//			objectMapper.setPropertyNamingStrategy(
//			     PropertyNamingStrategies.SNAKE_CASE);
			
			//AuthorizationResponse authorizationResponse = objectMapper.readValue(responseClient, AuthorizationResponse.class);
			
			ObjectMapper mapper = new ObjectMapper();
			SimpleModule module =
			  new SimpleModule("CustomCarDeserializer", new Version(1, 0, 0, null, null, null));
			module.addDeserializer(AuthorizationResponse.class, new AuthorizationDeserializerResponse());
			mapper.registerModule(module);
			AuthorizationResponse authorizationResponse = mapper.readValue(responseClient, AuthorizationResponse.class);
			
			//AuthorizationResponse authorizationResponse = gson.fromJson(responseClient, AuthorizationResponse.class);
			return authorizationResponse.getAccessToken();
		} catch (Exception e) {
        	log.error("error calling retrieveAccessToken", e);
        	throw e;
        }
	}
	
	public void createBatch(final String accessToken) {
		HttpClient client = new HttpClient();
        try {
        	String responseClient = client.get(
        			"https://people.googleapis.com/v1/people/me/connections?personFields=names,emailAddresses",
        			accessToken
        	);
        	Gson gson = new Gson();
        	ContactGoogleApisResponse contactsResponse = gson.fromJson(responseClient, ContactGoogleApisResponse.class);
        	this.persistBatch(contactsResponse);
        } catch (Exception e) {
        	log.error("error calling createBatch to store contacts", e);
        }
	}
	
	private List<ContactEntity> persistBatch(final ContactGoogleApisResponse response) {
		Assert.notNull(response, "It is not possible to persist not informed contacts");
		List<ContactEntity> entitiesToPersist = ContactGoogleApisResponse.from(response);
		
		//This deleteAll is just for implementation simplification
		this.contactRepository.deleteAll();
		return this.contactRepository.saveAll(entitiesToPersist);
	}
	
	public List<ContactDTO> listAll() {
		return this.contactRepository.findAll(
				Sort.by(Order.asc("name"), Order.asc("email")))
				.stream()
				.map(ContactDTO::to)
				.collect(Collectors.toList());
	}
}
