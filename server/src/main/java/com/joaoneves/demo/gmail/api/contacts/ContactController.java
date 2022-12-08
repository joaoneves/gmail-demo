package com.joaoneves.demo.gmail.api.contacts;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		this.contactService = contactService;
	}
	
	@GetMapping("/import")
	public ResponseEntity<Void> importContacts(@RequestParam String code) throws Exception {
		final String accessToken = this.contactService.retrieveAccessToken(code);
		this.contactService.createBatch(accessToken);
		return ResponseEntity.noContent().build();
	}

	@PostMapping
	public ResponseEntity<Void> storeContacts(@Valid @RequestBody AuthorizationRequest request) {
		log.info("Storing contacts by access token: {}", request.getAccessToken());
		this.contactService.createBatch(request.getAccessToken());
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	public List<ContactDTO> listAll() {
		log.info("Fetching contacts from the database");
		return this.contactService.listAll();
	}
	
	@ExceptionHandler({ Exception.class })
	  public ResponseEntity<String>  handleException(Exception e) {
	    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	  }
}
