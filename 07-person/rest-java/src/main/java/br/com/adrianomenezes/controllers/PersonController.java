package br.com.adrianomenezes.controllers;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.adrianomenezes.model.Person;
import br.com.adrianomenezes.services.PersonService;

@RestController
@RequestMapping(value = "/person")
public class PersonController {
	
	@Autowired
	private PersonService personService;

	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Person findById(@PathVariable(value="id") String id)  
		throws Exception {

		return personService.findById(Long.parseLong(id));
	}
	

	@GetMapping( produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Person> findAll()  
		throws Exception {

		return personService.findAll();
	}
	
	@PostMapping( consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Person create(@RequestBody Person person)  
			throws Exception {
		
		return personService.create(person);
	}
	
	@PutMapping( value = "/{id}"
			, consumes = MediaType.APPLICATION_JSON_VALUE
			, produces = MediaType.APPLICATION_JSON_VALUE)
	public Person update(@PathVariable(value="id") String id, @RequestBody Person person)  
			throws Exception {
		
		return personService.update(Long.parseLong(id), person);
	}
	
	@DeleteMapping( value = "/{id}"
			, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> delete(@PathVariable(value="id") String id)  
			throws Exception {
		
		personService.delete(Long.parseLong(id));
		return ResponseEntity.noContent().build();
	}
	


}
