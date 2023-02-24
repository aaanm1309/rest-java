package br.com.adrianomenezes.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.adrianomenezes.exceptions.ResourceNotFoundException;
import br.com.adrianomenezes.model.Person;
import br.com.adrianomenezes.repositories.PersonRepository;


@Service
public class PersonService {
	private Logger logger =   Logger.getLogger(PersonService.class.getName());
	
	@Autowired
	private PersonRepository repository;
	
	public Person findById(Long id) {
		logger.info("Finding one person");

		return repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID: " + id));
	}

	public List<Person> findAll() {
		logger.info("Finding All persons");
		return repository.findAll();
	}

	public Person create(Person person) {
		logger.info("Creating person");
		return repository.save(person);
	}

	public Person update(Long id, Person person) {
		logger.info("Updating person");
		findById(id);
		person.setId(id);
		return repository.save(person);
	}

	public void delete(Long id) {
		logger.info("Deleting person");
		findById(id);
		repository.deleteById(id);
	}

	
	
}
