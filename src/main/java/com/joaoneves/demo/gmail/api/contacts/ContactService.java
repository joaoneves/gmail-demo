package com.joaoneves.demo.gmail.api.contacts;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
		//TODO use code to get all contacts from 
		//this.createBatch(null);
		
		HttpClient client = new HttpClient();
		//Gson gson = new Gson();
        //String payload = gson.toJson(new OAuthRequest());
        try {
        	String responseClient = client.get(
        			"https://people.googleapis.com/v1/people/me/connections?personFields=names,emailAddresses",
        			accessToken
        	);
        	System.out.println(responseClient);
        	//OAuthResponse oauthResponse = gson.fromJson(responseClient, OAuthResponse.class);
        	
        	//TODO construir response payload do retorno da lista de contatos (nomes e emails), iterando tokens de cada response
        } catch (Exception e) {
        	log.error("error calling oauth2 to obtain token", e);
        }
        
	}
	
	private List<ContactEntity> persistBatch(final List<ContactDTO> dtos) {
		Assert.notEmpty(dtos, "It is not possible to persist not informed contacts");
		List<ContactEntity> entitiesToPersist = dtos.stream().map(ContactDTO::from).collect(Collectors.toList());
		return this.contactRepository.saveAll(entitiesToPersist);
	}
}
