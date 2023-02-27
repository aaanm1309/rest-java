package br.com.adrianomenezes.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.adrianomenezes.controllers.PersonController;
import br.com.adrianomenezes.data.vo.v1.PersonVO;
import br.com.adrianomenezes.exceptions.RequiredObjectIsNullException;
import br.com.adrianomenezes.exceptions.ResourceNotFoundException;
import br.com.adrianomenezes.mapper.ModelMapperImpl;
import br.com.adrianomenezes.model.Person;
import br.com.adrianomenezes.repositories.PersonRepository;


@Service
public class PersonService {
	private Logger logger =   Logger.getLogger(PersonService.class.getName());
	
	@Autowired
	private PersonRepository repository;
	
	public PersonVO findById(Long id) throws Exception {
		logger.info("Finding one person");
		Person person = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID: " + id)); 
		var vo  = ModelMapperImpl.parseObjectPersonToPersonVO(person, PersonVO.class) ;
		
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return vo;
	}

	public List<PersonVO> findAll() throws Exception  {
		logger.info("Finding All persons");
		var persons =  ModelMapperImpl.parseListObjectsPersonToPersonVO(repository.findAll(),PersonVO.class);
		persons.stream()
			.forEach(p -> {
				try {
					p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		return persons;
	}

	public PersonVO create(PersonVO personVO) throws Exception {
		
		if (personVO == (null)) throw new RequiredObjectIsNullException();
		
		logger.info("Creating person");
		personVO.setKey(null);

		var person  = ModelMapperImpl.parseObjectPersonVOToPerson(personVO, Person.class) ;

		person = repository.save(person);
		var vo  = ModelMapperImpl.parseObjectPersonToPersonVO(person, PersonVO.class) ;
		
		vo.add(linkTo(methodOn(PersonController.class).findById(person.getId())).withSelfRel());
		return vo;
	}

	public PersonVO update(Long id, PersonVO personVO) throws Exception {
		if (personVO == (null)) throw new RequiredObjectIsNullException();
		logger.info("Updating person");
		findById(id);
		personVO.setKey(id);
		var person  = ModelMapperImpl.parseObjectPersonVOToPerson(personVO, Person.class) ;

		person = repository.save(person);
		var vo  = ModelMapperImpl.parseObjectPersonToPersonVO(person, PersonVO.class) ;
		
		vo.add(linkTo(methodOn(PersonController.class).findById(person.getId())).withSelfRel());
		return vo;

	}

	public void delete(Long id) throws Exception {
		logger.info("Deleting person");
		findById(id);
		repository.deleteById(id);
	}

	
	
}
