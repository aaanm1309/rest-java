package br.com.adrianomenezes.controllers;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.adrianomenezes.data.vo.v1.PersonVO;
import br.com.adrianomenezes.services.PersonService;
import br.com.adrianomenezes.util.MediaTypeCustom;

@RestController
@RequestMapping(value = "/api/person/v1")
public class PersonController {

	@Autowired
	private PersonService personService;

	
	@GetMapping(value = "/{id}"
			, produces = {	MediaTypeCustom.APPLICATION_JSON,
							MediaTypeCustom.APPLICATION_XML,
							MediaTypeCustom.APPLICATION_YML})
	public PersonVO findById(@PathVariable(value="id") Long id)  
		throws Exception {

		return personService.findById(id);
	}
	

	@GetMapping( produces = {	MediaTypeCustom.APPLICATION_JSON,
								MediaTypeCustom.APPLICATION_XML,
								MediaTypeCustom.APPLICATION_YML})
	public List<PersonVO> findAll()  
		throws Exception {

		return personService.findAll();
	}
	
	@PostMapping( consumes = {	MediaTypeCustom.APPLICATION_JSON,
								MediaTypeCustom.APPLICATION_XML,
								MediaTypeCustom.APPLICATION_YML},
				produces = {	MediaTypeCustom.APPLICATION_JSON,
								MediaTypeCustom.APPLICATION_XML,
								MediaTypeCustom.APPLICATION_YML})
	public PersonVO create(@RequestBody PersonVO person)  
			throws Exception {
		
		return personService.create(person);
	}
	
	@PutMapping( value = "/{id}"
			, consumes = {	MediaTypeCustom.APPLICATION_JSON,
							MediaTypeCustom.APPLICATION_XML,
							MediaTypeCustom.APPLICATION_YML},
			produces = {	MediaTypeCustom.APPLICATION_JSON,
							MediaTypeCustom.APPLICATION_XML,
							MediaTypeCustom.APPLICATION_YML})
	public PersonVO update(@PathVariable(value="id") Long id, @RequestBody PersonVO person)  
			throws Exception {
		
		return personService.update(id, person);
	}
	
	@DeleteMapping( value = "/{id}"
			, produces = {	MediaTypeCustom.APPLICATION_JSON,
							MediaTypeCustom.APPLICATION_XML,
							MediaTypeCustom.APPLICATION_YML})
	public ResponseEntity<?> delete(@PathVariable(value="id") Long id)  
			throws Exception {
		
		personService.delete(id);
		return ResponseEntity.noContent().build();
	}
	


}
