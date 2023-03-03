package br.com.adrianomenezes.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
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

import br.com.adrianomenezes.data.vo.v1.BookVO;
import br.com.adrianomenezes.exceptions.RequiredObjectIsNullException;
import br.com.adrianomenezes.model.Book;
import br.com.adrianomenezes.repositories.BookRepository;
import br.com.adrianomenezes.services.BookService;
import br.com.adrianomenezes.unittests.mapper.mocks.MockBook;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

	private static final Long codL1 = 1L;
	private static final Integer cod1 = 1;
	MockBook input;

	
	@InjectMocks
	private BookService service;
	
	@Mock
	BookRepository repository;
	
	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockBook();
		MockitoAnnotations.openMocks(this);

	}

	@Test
	void testFindById() throws Exception {
		Book entity = input.mockEntity(cod1);
		entity.setId(codL1);
		
		when(repository.findById(codL1)).thenReturn(Optional.of(entity));
		var result = service.findById(codL1);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
//		System.out.println(result.toString());
		assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals(entity.getAuthor(),result.getAuthor());
		assertEquals(entity.getTitle(),result.getTitle());
		assertEquals(entity.getPrice(),result.getPrice());
		assertEquals(entity.getLaunchDate(),result.getLaunchDate());
		assertNotNull(result.getLaunchDate());
		
	}

	@Test
	void testFindAll() throws Exception {
		List<Book> entities = input.mockEntityList();
//		entity.setId(codL1);
		
		Book entityOne = input.mockEntity(1);
		Book entityFive = input.mockEntity(5);
		Book entityTwelve = input.mockEntity(12);

		
		when(repository.findAll()).thenReturn(entities);
		var result = service.findAll();
		assertNotNull(result);
		assertEquals(14, result.size());
		
		assertEquals(entityOne.getId(),result.get(1).getKey());
		assertEquals(entityOne.getAuthor(),result.get(1).getAuthor());
		assertEquals(entityOne.getTitle(),result.get(1).getTitle());
		assertEquals(entityOne.getPrice(),result.get(1).getPrice());
		assertEquals(entityOne.getLaunchDate(),result.get(1).getLaunchDate());
		assertTrue(result.get(1).toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertNotNull(result.get(1).getLaunchDate());
		
		assertEquals(entityFive.getId(),result.get(5).getKey());
		assertEquals(entityFive.getAuthor(),result.get(5).getAuthor());
		assertEquals(entityFive.getTitle(),result.get(5).getTitle());
		assertEquals(entityFive.getPrice(),result.get(5).getPrice());
		assertEquals(entityFive.getLaunchDate(),result.get(5).getLaunchDate());
		assertTrue(result.get(5).toString().contains("links: [</api/book/v1/5>;rel=\"self\"]"));
		assertNotNull(result.get(5).getLaunchDate());
		

		assertEquals(entityTwelve.getId(),result.get(12).getKey());
		assertEquals(entityTwelve.getAuthor(),result.get(12).getAuthor());
		assertEquals(entityTwelve.getTitle(),result.get(12).getTitle());
		assertEquals(entityTwelve.getPrice(),result.get(12).getPrice());
		assertEquals(entityTwelve.getLaunchDate(),result.get(12).getLaunchDate());
		assertTrue(result.get(12).toString().contains("links: [</api/book/v1/12>;rel=\"self\"]"));
		assertNotNull(result.get(12).getLaunchDate());

	}

	@Test
	void testCreate() throws Exception {
		Book entity = input.mockEntity(cod1);
		entity.setId(null);
		
		Book persistedEntity = entity;
		persistedEntity.setId(codL1);
		
		BookVO vo = input.mockVO(cod1);
		vo.setKey(codL1);
		
		when(repository.save(any())).thenReturn(persistedEntity);
		
		var result = service.create(vo);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals(entity.getAuthor(),result.getAuthor());
		assertEquals(entity.getTitle(),result.getTitle());
		assertEquals(entity.getPrice(),result.getPrice());
		assertEquals(entity.getLaunchDate(),result.getLaunchDate());
		assertNotNull(result.getLaunchDate());
		
	}

	@Test
	void testUpdate() throws Exception {
		Book entity = input.mockEntity(cod1);
		entity.setId(codL1);
		
		Book persistedEntity = entity;
		persistedEntity.setId(codL1);
		
		BookVO vo = input.mockVO(cod1);
		vo.setKey(codL1);
		
		when(repository.findById(codL1)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persistedEntity);
		
		var result = service.update(codL1, vo);
		assertNotNull(result);
		assertNotNull(result.getKey());
		assertNotNull(result.getLinks());
		assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
		assertEquals(entity.getAuthor(),result.getAuthor());
		assertEquals(entity.getTitle(),result.getTitle());
		assertEquals(entity.getPrice(),result.getPrice());
		assertEquals(entity.getLaunchDate(),result.getLaunchDate());
		
	}

	@Test
	void testDelete() throws Exception {
		Book entity = input.mockEntity(cod1);
		entity.setId(codL1);
		
		when(repository.findById(codL1)).thenReturn(Optional.of(entity));
		service.delete(codL1);
	}
	
	@Test
	void testCreateWithNullBook() throws Exception {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () ->
				service.create(null));
		
		String expectedMessage = "It is not allowed to persist a null object!"; 
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));

	}
	
	@Test
	void testUpdateWithNullBook() throws Exception {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () ->
		service.update(codL1,null));
		
		String expectedMessage = "It is not allowed to persist a null object!"; 
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
		
	}
	
	
}
