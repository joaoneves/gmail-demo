package com.joaoneves.demo.gmail.api.contacts;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ContactService {

	private final ContactRepository contactRepository;

	@Autowired
	public ContactService(ContactRepository contactRepository) {
		this.contactRepository = contactRepository;
	}
	
	public void createBatch(final String code) {
		//TODO use code to get all contacts from 
		//this.createBatch(null);
	}
	
	private List<ContactEntity> persistBatch(final List<ContactDTO> dtos) {
		Assert.notEmpty(dtos, "It is not possible to persist not informed contacts");
		List<ContactEntity> entitiesToPersist = dtos.stream().map(ContactDTO::from).collect(Collectors.toList());
		return this.contactRepository.saveAll(entitiesToPersist);
	}
}
