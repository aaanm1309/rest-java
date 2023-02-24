package br.com.adrianomenezes.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.adrianomenezes.exceptions.ResourceNotFoundException;
import br.com.adrianomenezes.mapper.DozerMapper;
import br.com.adrianomenezes.model.Person;
import br.com.adrianomenezes.data.vo.v1.*;
import br.com.adrianomenezes.repositories.PersonRepository;


@Service
public class PersonService {
	private Logger logger =   Logger.getLogger(PersonService.class.getName());
	
	@Autowired
	private PersonRepository repository;
	
	public PersonVO findById(Long id) {
		logger.info("Finding one person");
		Person person = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID: " + id)); 

		return DozerMapper.parseObject(person, PersonVO.class) ;
	}

	public List<PersonVO> findAll() {
		logger.info("Finding All persons");
		return DozerMapper.parseListObjects(repository.findAll(),PersonVO.class);
	}

	public PersonVO create(PersonVO personVO) {
		logger.info("Creating person");
		Person person = DozerMapper.parseObject(personVO, Person.class);
		return DozerMapper.parseObject(repository.save(person), PersonVO.class);
	}

	public PersonVO update(Long id, PersonVO personVO) {
		logger.info("Updating person");
		findById(id);
		personVO.setId(id);
		Person person = DozerMapper.parseObject(personVO, Person.class);
		return DozerMapper.parseObject(repository.save(person), PersonVO.class);

	}

	public void delete(Long id) {
		logger.info("Deleting person");
		findById(id);
		repository.deleteById(id);
	}

	
	
}
