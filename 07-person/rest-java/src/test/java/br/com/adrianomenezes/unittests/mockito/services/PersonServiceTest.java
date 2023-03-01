package br.com.adrianomenezes.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.adrianomenezes.data.vo.v1.PersonVO;
import br.com.adrianomenezes.exceptions.RequiredObjectIsNullException;
import br.com.adrianomenezes.model.Person;
import br.com.adrianomenezes.repositories.PersonRepository;
import br.com.adrianomenezes.services.PersonService;
import br.com.adrianomenezes.unittests.mapper.mocks.MockPerson;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

	private static final Long codL1 = 1L;
	private static final Integer cod1 = 1;
	MockPerson input;

	
	@InjectMocks
	private PersonService service;
	
	@Mock
	PersonRepository repository;
	
	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockPerson();
		MockitoAnnotations.openMocks(this);
		
		
	}

	@Test
	void testFindById() throws Exception {
		Person entity = input.mockEntity(cod1);
		entity.setId(codL1);
		
		when(repository.findById(codL1)).thenReturn(Optional.of(entity));
		var result = service.findById(codL1);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
//		System.out.println(result.toString());
		assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
		assertEquals(entity.getAddress(),result.getAddress());
		assertEquals(entity.getFirstName(),result.getFirstName());
		assertEquals(entity.getLastName(),result.getLastName());
		assertEquals(entity.getGender(),result.getGender());
		
		
	}
	
	/*
	@Test
	void testFindAll() throws Exception {
		List<Person> entities = input.mockEntityList();
//		entity.setId(codL1);
		
		Person entityOne = input.mockEntity(1);
		Person entityFive = input.mockEntity(5);
		Person entityTwelve = input.mockEntity(12);

		
		when(repository.findAll()).thenReturn(entities);
		var result = service.findAll();
		assertNotNull(result);
		assertEquals(14, result.size());
		
		assertEquals(entityOne.getId(),result.get(1).getKey());
		assertEquals(entityOne.getAddress(),result.get(1).getAddress());
		assertEquals(entityOne.getFirstName(),result.get(1).getFirstName());
		assertEquals(entityOne.getLastName(),result.get(1).getLastName());
		assertEquals(entityOne.getGender(),result.get(1).getGender());
		
		assertEquals(entityFive.getId(),result.get(5).getKey());
		assertEquals(entityFive.getAddress(),result.get(5).getAddress());
		assertEquals(entityFive.getFirstName(),result.get(5).getFirstName());
		assertEquals(entityFive.getLastName(),result.get(5).getLastName());
		assertEquals(entityFive.getGender(),result.get(5).getGender());

		assertEquals(entityTwelve.getId(),result.get(12).getKey());
		assertEquals(entityTwelve.getAddress(),result.get(12).getAddress());
		assertEquals(entityTwelve.getFirstName(),result.get(12).getFirstName());
		assertEquals(entityTwelve.getLastName(),result.get(12).getLastName());
		assertEquals(entityTwelve.getGender(),result.get(12).getGender());
		assertTrue(result.get(12).toString().contains("links: [</api/person/v1/12>;rel=\"self\"]"));
//		
//		System.out.println(entityTwelve.getId());
//		System.out.println(entityTwelve.getAddress());
		
	}
	*/
	
	@Test
	void testCreate() throws Exception {
		Person entity = input.mockEntity(cod1);
		
		Person persistedEntity = entity;
		persistedEntity.setId(codL1);
		
		PersonVO vo = input.mockVO(cod1);
		vo.setKey(codL1);
		
//		doReturn(persistedEntity).when(repository.save(entity));
		when(repository.save(any())).thenReturn(persistedEntity);
		
		var result = service.create(vo);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
//		System.out.println(result.toString());
		assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
		assertEquals(entity.getAddress(),result.getAddress());
		assertEquals(entity.getFirstName(),result.getFirstName());
		assertEquals(entity.getLastName(),result.getLastName());
		assertEquals(entity.getGender(),result.getGender());
	}

	@Test
	void testUpdate() throws Exception {
		Person entity = input.mockEntity(cod1);
		entity.setId(codL1);
		
		Person persistedEntity = entity;
		persistedEntity.setId(codL1);
		
		PersonVO vo = input.mockVO(cod1);
		vo.setKey(codL1);
		
		when(repository.findById(codL1)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persistedEntity);
		
		var result = service.update(codL1, vo);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
//		System.out.println(result.toString());
		assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));
		assertEquals(entity.getAddress(),result.getAddress());
		assertEquals(entity.getFirstName(),result.getFirstName());
		assertEquals(entity.getLastName(),result.getLastName());
		assertEquals(entity.getGender(),result.getGender());
	}

	@Test
	void testDelete() throws Exception {
		Person entity = input.mockEntity(cod1);
		entity.setId(codL1);
		
		when(repository.findById(codL1)).thenReturn(Optional.of(entity));
		service.delete(codL1);
	}
	
	@Test
	void testCreateWithNullPerson() throws Exception {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () ->
				service.create(null));
		
		String expectedMessage = "It is not allowed to persist a null object!"; 
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));

	}
	
	@Test
	void testUpdateWithNullPerson() throws Exception {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () ->
		service.update(codL1,null));
		
		String expectedMessage = "It is not allowed to persist a null object!"; 
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
		
	}
	
	
}
