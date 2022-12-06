package com.joaoneves.demo.gmail.api.contacts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@GetMapping("/{code}")
	  public ResponseEntity<String> show(@PathVariable("id") String code) {
	    log.info("Fetching contacts by code: {}", code);
	    this.contactService.createBatch(code);
	    return ResponseEntity.ok("OK");
	  }
}
