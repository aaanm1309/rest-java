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

import br.com.adrianomenezes.data.vo.v1.BookVO;
import br.com.adrianomenezes.services.BookService;
import br.com.adrianomenezes.util.MediaTypeCustom;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/api/book/v1")
@Tag(name = "Books", description = "EndPoint to manage Books")
public class BookController {

	@Autowired
	private BookService bookService;

	
	@GetMapping(value = "/{id}"
			, produces = {	MediaTypeCustom.APPLICATION_JSON
//							,MediaTypeCustom.APPLICATION_XML,
//							MediaTypeCustom.APPLICATION_YML
							})
	@Operation(summary = "Finds a Book"
	, description = "EndPoint to Finds a Book",
	tags = {"Books"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200", content =  {
					@Content(
							mediaType = "application/json",
							 schema = @Schema(implementation = BookVO.class))
//					,@Content(
//							mediaType = "application/xml",
//							 schema = @Schema(implementation = BookVO.class))
					
			} ),
			@ApiResponse(description = "No Content", responseCode = "204", content =  @Content ),
			@ApiResponse(description = "Bad Request", responseCode = "400", content =  @Content ),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content =  @Content ),
			@ApiResponse(description = "Not Found", responseCode = "404", content =  @Content ),
			@ApiResponse(description = "Not Acceptable", responseCode = "406", content =  @Content ),
			@ApiResponse(description = "Internal Error", responseCode = "500", content =  @Content )
	})
	public BookVO findById(@PathVariable(value="id") Long id)  
		throws Exception {

		return bookService.findById(id);
	}
	

	@GetMapping( produces = {	MediaTypeCustom.APPLICATION_JSON,
								MediaTypeCustom.APPLICATION_XML,
								MediaTypeCustom.APPLICATION_YML})
	@Operation(summary = "Find All Books"
			, description = "EndPoint to Find All Books",
			tags = {"Books"},
			responses = {
					@ApiResponse(description = "Success", responseCode = "200", content =  {
							@Content(
									mediaType = "application/json",
									array = @ArraySchema( schema = @Schema(implementation = BookVO.class)))
//							,@Content(
//							mediaType = "application/xml",
//							 schema = @Schema(implementation = BookVO.class))
							
					} ),
					@ApiResponse(description = "Bad Request", responseCode = "400", content =  @Content ),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content =  @Content ),
					@ApiResponse(description = "Not Found", responseCode = "404", content =  @Content ),
					@ApiResponse(description = "Not Acceptable", responseCode = "406", content =  @Content ),
					@ApiResponse(description = "Internal Error", responseCode = "500", content =  @Content )
			})
	public List<BookVO> findAll()  
		throws Exception {

		return bookService.findAll();
	}
	
	@PostMapping( consumes = {	MediaTypeCustom.APPLICATION_JSON,
								MediaTypeCustom.APPLICATION_XML,
								MediaTypeCustom.APPLICATION_YML},
				produces = {	MediaTypeCustom.APPLICATION_JSON,
								MediaTypeCustom.APPLICATION_XML,
								MediaTypeCustom.APPLICATION_YML})
	@Operation(summary = "Adds a new Book"
	, description = "EndPoint to Adds a new Book by passing in a Json, XML or yml representtation of a person ",
	tags = {"Books"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200", content =  {
					@Content(
							mediaType = "application/json",
							 schema = @Schema(implementation = BookVO.class))
//					,@Content(
//					mediaType = "application/xml",
//					 schema = @Schema(implementation = BookVO.class))
					
			} ),
			@ApiResponse(description = "Bad Request", responseCode = "400", content =  @Content ),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content =  @Content ),
			@ApiResponse(description = "Not Acceptable", responseCode = "406", content =  @Content ),
			@ApiResponse(description = "Internal Error", responseCode = "500", content =  @Content )
	})
	public BookVO create(@RequestBody BookVO bookVO)  
			throws Exception {
		
		return bookService.create(bookVO);
	}
	
	@PutMapping( value = "/{id}"
			, consumes = {	MediaTypeCustom.APPLICATION_JSON,
							MediaTypeCustom.APPLICATION_XML,
							MediaTypeCustom.APPLICATION_YML},
			produces = {	MediaTypeCustom.APPLICATION_JSON,
							MediaTypeCustom.APPLICATION_XML,
							MediaTypeCustom.APPLICATION_YML})
	@Operation(summary = "Update a existent Book"
	, description = "EndPoint to Update a existent Book by passing in a Json, XML or yml representtation of a person ",
	tags = {"Books"},
	responses = {
			@ApiResponse(description = "Updated", responseCode = "200", content =  {
					@Content(
							mediaType = "application/json",
							 schema = @Schema(implementation = BookVO.class))
//					,@Content(
//					mediaType = "application/xml",
//					 schema = @Schema(implementation = BookVO.class))
					
			} ),
			@ApiResponse(description = "Bad Request", responseCode = "400", content =  @Content ),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content =  @Content ),
			@ApiResponse(description = "Not Found", responseCode = "404", content =  @Content ),
			@ApiResponse(description = "Not Acceptable", responseCode = "406", content =  @Content ),
			@ApiResponse(description = "Internal Error", responseCode = "500", content =  @Content )
	})
	public BookVO update(@PathVariable(value="id") Long id, @RequestBody BookVO book)  
			throws Exception {
		
		return bookService.update(id, book);
	}
	
	@Operation(summary = "Deletes a Book"
	, description = "EndPoint to Delete a Book",
	tags = {"Books"},
	responses = {
			@ApiResponse(description = "No Content", responseCode = "204", content =  @Content ),
			@ApiResponse(description = "Bad Request", responseCode = "400", content =  @Content ),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content =  @Content ),
			@ApiResponse(description = "Not Found", responseCode = "404", content =  @Content ),
			@ApiResponse(description = "Not Acceptable", responseCode = "406", content =  @Content ),
			@ApiResponse(description = "Internal Error", responseCode = "500", content =  @Content )
	})
	@DeleteMapping( value = "/{id}"
			, produces = {	MediaTypeCustom.APPLICATION_JSON,
							MediaTypeCustom.APPLICATION_XML,
							MediaTypeCustom.APPLICATION_YML})
	public ResponseEntity<?> delete(@PathVariable(value="id") Long id)  
			throws Exception {
		
		bookService.delete(id);
		return ResponseEntity.noContent().build();
	}
	


}
