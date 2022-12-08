package com.joaoneves.demo.gmail.api.contacts;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.joaoneves.demo.gmail.api.contacts.dto.AuthorizationResponse;
import com.joaoneves.demo.gmail.api.contacts.dto.ContactGoogleApisResponse;
import com.joaoneves.demo.gmail.api.contacts.dto.ContactResponse;
import com.joaoneves.demo.gmail.api.contacts.dto.OAuthRequest;
import com.joaoneves.demo.gmail.api.utils.HttpClient;

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
			OAuthRequest request = OAuthRequest
					.builder()
					.clientId(this.clientId)
					.clientSecret(this.clientSecret)
					.code(code)
					.build();
			
			String responseClient = client.post("https://accounts.google.com/o/oauth2/token", 
					OAuthRequest.toNameValuePairArray(request));
			AuthorizationResponse authorizationResponse = AuthorizationResponse.deserialize(responseClient);
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
        	ContactGoogleApisResponse contactsResponse = ContactGoogleApisResponse.deserialize(responseClient);
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
	
	public List<ContactResponse> listAll() {
		return this.contactRepository.findAll(
				Sort.by(Order.asc("name"), Order.asc("email")))
				.stream()
				.map(ContactResponse::to)
				.collect(Collectors.toList());
	}
}
