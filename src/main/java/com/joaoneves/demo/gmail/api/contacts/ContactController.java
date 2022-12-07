package com.joaoneves.demo.gmail.api.contacts;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joaoneves.demo.gmail.api.AuthorizationRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/contacts")
public class ContactController {
	
	private final ContactService contactService;
	
	@Autowired
	public ContactController(ContactService contactService) {
		super();
		this.contactService = contactService;
	}

	@PostMapping
	public ResponseEntity<String> storeContacts(@Valid @RequestBody AuthorizationRequest request) {
	    log.info("Fetching contacts by access token: {}", request.getAccessToken());
	    this.contactService.createBatch(request.getAccessToken());
	    return ResponseEntity.ok("OK");
	  }
}
