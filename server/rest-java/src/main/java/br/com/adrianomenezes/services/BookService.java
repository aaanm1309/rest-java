package br.com.adrianomenezes.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.adrianomenezes.controllers.BookController;
import br.com.adrianomenezes.data.vo.v1.BookVO;
import br.com.adrianomenezes.exceptions.RequiredObjectIsNullException;
import br.com.adrianomenezes.exceptions.ResourceNotFoundException;
import br.com.adrianomenezes.mapper.ModelMapperImpl;
import br.com.adrianomenezes.model.Book;
import br.com.adrianomenezes.repositories.BookRepository;



@Service
public class BookService {
	private Logger logger =   Logger.getLogger(BookService.class.getName());
	
	@Autowired
	private BookRepository repository;
	
	public BookVO findById(Long id) throws Exception {
		logger.info("Finding one book");
		Book book = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID: " + id)); 
		var vo  = ModelMapperImpl.parseObjectBookToBookVO(book, BookVO.class) ;
		
		vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		return vo;
	}

	public List<BookVO> findAll() throws Exception  {
		logger.info("Finding All Books");
		var persons =  ModelMapperImpl.parseListObjectsBookToBookVO(repository.findAll(),BookVO.class);
		persons.stream()
			.forEach(b -> {
				try {
					b.add(linkTo(methodOn(BookController.class).findById(b.getKey())).withSelfRel());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		return persons;
	}

	public BookVO create(BookVO bookVO) throws Exception {
		
		if (bookVO == (null)) throw new RequiredObjectIsNullException();
		
		logger.info("Creating book");
		bookVO.setKey(null);

		var book  = ModelMapperImpl.parseObjectBookVOToBook(bookVO, Book.class) ;

		book = repository.save(book);
		var vo  = ModelMapperImpl.parseObjectBookToBookVO(book, BookVO.class) ;
		
		vo.add(linkTo(methodOn(BookController.class).findById(book.getId())).withSelfRel());
		return vo;
	}

	public BookVO update(Long id, BookVO bookVO) throws Exception {
		if (bookVO == (null)) throw new RequiredObjectIsNullException();
		logger.info("Updating book");
		findById(id);
		bookVO.setKey(id);
		var book  = ModelMapperImpl.parseObjectBookVOToBook(bookVO, Book.class) ;

		book = repository.save(book);
		var vo  = ModelMapperImpl.parseObjectBookToBookVO(book, BookVO.class) ;
		
		vo.add(linkTo(methodOn(BookController.class).findById(book.getId())).withSelfRel());
		return vo;

	}

	public void delete(Long id) throws Exception {
		logger.info("Deleting book");
		findById(id);
		repository.deleteById(id);
	}

	
	
}
