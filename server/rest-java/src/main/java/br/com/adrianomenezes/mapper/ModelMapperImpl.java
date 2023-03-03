package br.com.adrianomenezes.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import br.com.adrianomenezes.data.vo.v1.BookVO;
import br.com.adrianomenezes.data.vo.v1.PersonVO;
import br.com.adrianomenezes.model.Book;
import br.com.adrianomenezes.model.Person;

//import com.github.dozermapper.core.DozerBeanMapperBuilder;
//import com.github.dozermapper.core.Mapper;

public class ModelMapperImpl {
	
	private static ModelMapper mapper = new ModelMapper();
	private static TypeMap<Person, PersonVO> propertyMapperPersonToPersonVO = mapper.createTypeMap(Person.class, PersonVO.class);
	private static TypeMap<PersonVO, Person> propertyMapperPersonVOToPerson = mapper.createTypeMap(PersonVO.class, Person.class);
	private static TypeMap<Book, BookVO> propertyMapperBookToBookVO = mapper.createTypeMap(Book.class, BookVO.class);
	private static TypeMap<BookVO, Book> propertyMapperBookVOToBook = mapper.createTypeMap(BookVO.class, Book.class);
	
	public static <O, D> D parseObject(O origin, Class<D> destination) {
		return mapper.map(origin, destination);
		
//	    TypeMap<Person, PersonVO> propertyMapper = mapper.createTypeMap(Person.class, PersonVO.class);
//	    propertyMapper.addMapping(Person::getId, PersonVO::setKey);
//	    
//
//	    return mapper.map(origin, destination);
		
	}
	
	public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
		List<D> destObjects =  new ArrayList<D>();
		for (O o : origin) {
			destObjects.add(mapper.map(o, destination));
		}
		return destObjects;
		
	}
	
	public static <O, D> D parseObjectPersonToPersonVO(O origin, Class<D> destination) {
		
	    
		propertyMapperPersonToPersonVO.addMapping(Person::getId, PersonVO::setKey);
	    

	    return mapper.map(origin, destination);
		
	}
	
	public static <O, D> D parseObjectPersonVOToPerson(O origin, Class<D> destination) {
		
	    
		propertyMapperPersonVOToPerson.addMapping(PersonVO::getKey, Person::setId);
	    

	    return mapper.map(origin, destination);
		
	}
	
	public static <O, D> List<D> parseListObjectsPersonVOTOPerson(List<O> origin, Class<D> destination) {
		List<D> destObjects =  new ArrayList<D>();
		for (O o : origin) {
			destObjects.add(parseObjectPersonVOToPerson(o, destination));
		}
		return destObjects;
		
	}

	public static <O, D> List<D> parseListObjectsPersonToPersonVO(List<O> origin, Class<D> destination) {
//	    TypeMap<Person, PersonVO> propertyMapper = mapper.createTypeMap(Person.class, PersonVO.class);
//	    propertyMapper.addMapping(Person::getId, PersonVO::setKey);
	    

		List<D> destObjects =  new ArrayList<D>();
		for (O o : origin) {
			destObjects.add(parseObjectPersonToPersonVO(o, destination));
		}
		return destObjects;
		
	}
	
	
	public static <O, D> D parseObjectBookToBookVO(O origin, Class<D> destination) {
		
	    
		propertyMapperBookToBookVO.addMapping(Book::getId, BookVO::setKey);
	    

	    return mapper.map(origin, destination);
		
	}
	
	public static <O, D> D parseObjectBookVOToBook(O origin, Class<D> destination) {
		
	    
		propertyMapperBookVOToBook.addMapping(BookVO::getKey, Book::setId);
	    

	    return mapper.map(origin, destination);
		
	}
	
	
	public static <O, D> List<D> parseListObjectsBookVOTOBook(List<O> origin, Class<D> destination) {
		List<D> destObjects =  new ArrayList<D>();
		for (O o : origin) {
			destObjects.add(parseObjectBookVOToBook(o, destination));
		}
		return destObjects;
		
	}

	public static <O, D> List<D> parseListObjectsBookToBookVO(List<O> origin, Class<D> destination) {

		List<D> destObjects =  new ArrayList<D>();
		for (O o : origin) {
			destObjects.add(parseObjectBookToBookVO(o, destination));
		}
		return destObjects;
		
	}
	

}
