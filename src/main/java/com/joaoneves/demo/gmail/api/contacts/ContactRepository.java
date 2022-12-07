package com.joaoneves.demo.gmail.api.contacts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<ContactEntity, String>{

}
