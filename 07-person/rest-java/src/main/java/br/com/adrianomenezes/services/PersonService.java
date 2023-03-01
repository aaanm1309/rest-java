package br.com.adrianomenezes.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import br.com.adrianomenezes.controllers.PersonController;
import br.com.adrianomenezes.data.vo.v1.PersonVO;
import br.com.adrianomenezes.exceptions.RequiredObjectIsNullException;
import br.com.adrianomenezes.exceptions.ResourceNotFoundException;
import br.com.adrianomenezes.mapper.ModelMapperImpl;
import br.com.adrianomenezes.model.Person;
import br.com.adrianomenezes.repositories.PersonRepository;
import jakarta.transaction.Transactional;


@Service
public class PersonService {
	private Logger logger =   Logger.getLogger(PersonService.class.getName());
	
	@Autowired
	private PersonRepository repository;
	
	@Autowired
	private PagedResourcesAssembler<PersonVO> assembler;
	
	public PersonVO findById(Long id) throws Exception {
		logger.info("Finding one person");
		Person person = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID: " + id)); 
		var vo  = ModelMapperImpl.parseObjectPersonToPersonVO(person, PersonVO.class) ;
		
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return vo;
	}

	public PagedModel<EntityModel<PersonVO>> findAll(Pageable page) throws Exception  {
		logger.info("Finding All persons");
		var personPage = repository.findAll(page);
		Page<PersonVO> pesonVOPage = personPage.map(p ->  
				ModelMapperImpl
				.parseObjectPersonToPersonVO(p, PersonVO.class));

		pesonVOPage.map( p -> 
			{
				try {
					return p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return p;
			}
			);		
		var pageItens = page.getSort().toString().split(":");

		Link link = linkTo(
					methodOn(PersonController.class)
					.findAll(page.getPageNumber()
							,page.getPageSize()
							,pageItens[0].trim().toLowerCase()
							,pageItens[0].trim()))
				.withSelfRel();
		//		var persons =  
//				ModelMapperImpl
//					.parseListObjectsPersonToPersonVO(
//							personPage
//							,PersonVO.class);
//		pesonVOPage.stream()
//			.forEach(p -> {
//				try {
//					p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel());
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			});
		return assembler.toModel(pesonVOPage, link) ;
	}
	public PagedModel<EntityModel<PersonVO>> findPersonsByName(String firstName, Pageable pageable) throws Exception  {
		logger.info("Finding persons by firstName");
		var personPage = repository.findPersonsByName(firstName, pageable);
		Page<PersonVO> pesonVOPage = personPage.map(p ->  
		ModelMapperImpl
		.parseObjectPersonToPersonVO(p, PersonVO.class));
		
		pesonVOPage.map( p -> 
		{
			try {
				return p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return p;
		}
				);		
		var pageItens = pageable.getSort().toString().split(":");
		
		Link link = linkTo(
				methodOn(PersonController.class)
				.findAll(pageable.getPageNumber()
						,pageable.getPageSize()
						,pageItens[0].trim().toLowerCase()
						,pageItens[0].trim()))
				.withSelfRel();
		//		var persons =  
//				ModelMapperImpl
//					.parseListObjectsPersonToPersonVO(
//							personPage
//							,PersonVO.class);
//		pesonVOPage.stream()
//			.forEach(p -> {
//				try {
//					p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel());
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			});
		return assembler.toModel(pesonVOPage, link) ;
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
	
	@Transactional
	public PersonVO disablePerson(Long id) throws Exception {
		
		logger.info("Disabling one person!");
		
		repository.disablePerson(id);
		
		var entity = repository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		var vo = ModelMapperImpl.parseObjectPersonToPersonVO(entity, PersonVO.class) ;
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return vo;
	}

	public void delete(Long id) throws Exception {
		logger.info("Deleting person");
		findById(id);
		repository.deleteById(id);
	}

	
	
}
