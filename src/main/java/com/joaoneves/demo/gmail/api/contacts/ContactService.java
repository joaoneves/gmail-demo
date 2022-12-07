package com.joaoneves.demo.gmail.api.contacts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.google.gson.Gson;
import com.joaoneves.demo.gmail.api.HttpClient;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ContactService {

	private final ContactRepository contactRepository;

	@Autowired
	public ContactService(ContactRepository contactRepository) {
		this.contactRepository = contactRepository;
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
}
