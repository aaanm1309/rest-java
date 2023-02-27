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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/api/person/v1")
@Tag(name = "People", description = "EndPoint to manage People")
public class PersonController {

	@Autowired
	private PersonService personService;

	
	@GetMapping(value = "/{id}"
			, produces = {	MediaTypeCustom.APPLICATION_JSON,
							MediaTypeCustom.APPLICATION_XML,
							MediaTypeCustom.APPLICATION_YML})
	@Operation(summary = "Finds a Person"
	, description = "EndPoint to Finds a Person",
	tags = {"People"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200", content =  {
					@Content(
							mediaType = "application/json",
							 schema = @Schema(implementation = PersonVO.class))
					,@Content(
							mediaType = "application/xml",
							 schema = @Schema(implementation = PersonVO.class))
					
			} ),
			@ApiResponse(description = "No Content", responseCode = "204", content =  @Content ),
			@ApiResponse(description = "Bad Request", responseCode = "400", content =  @Content ),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content =  @Content ),
			@ApiResponse(description = "Not Found", responseCode = "404", content =  @Content ),
			@ApiResponse(description = "Not Acceptable", responseCode = "406", content =  @Content ),
			@ApiResponse(description = "Internal Error", responseCode = "500", content =  @Content )
	})
	public PersonVO findById(@PathVariable(value="id") Long id)  
		throws Exception {

		return personService.findById(id);
	}
	

	@GetMapping( produces = {	MediaTypeCustom.APPLICATION_JSON,
								MediaTypeCustom.APPLICATION_XML,
								MediaTypeCustom.APPLICATION_YML})
	@Operation(summary = "Find All People"
			, description = "EndPoint to Find All People",
			tags = {"People"},
			responses = {
					@ApiResponse(description = "Success", responseCode = "200", content =  {
							@Content(
									mediaType = "application/json",
									array = @ArraySchema( schema = @Schema(implementation = PersonVO.class)))
							,@Content(
									mediaType = "application/xml",
									array = @ArraySchema( schema = @Schema(implementation = PersonVO.class)))
							
					} ),
					@ApiResponse(description = "Bad Request", responseCode = "400", content =  @Content ),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content =  @Content ),
					@ApiResponse(description = "Not Found", responseCode = "404", content =  @Content ),
					@ApiResponse(description = "Not Acceptable", responseCode = "406", content =  @Content ),
					@ApiResponse(description = "Internal Error", responseCode = "500", content =  @Content )
			})
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
	@Operation(summary = "Adds a new Person"
	, description = "EndPoint to Adds a new Person by passing in a Json, XML or yml representtation of a person ",
	tags = {"People"},
	responses = {
			@ApiResponse(description = "Success", responseCode = "200", content =  {
					@Content(
							mediaType = "application/json",
							 schema = @Schema(implementation = PersonVO.class))
					,@Content(
							mediaType = "application/xml",
							 schema = @Schema(implementation = PersonVO.class))
			} ),
			@ApiResponse(description = "Bad Request", responseCode = "400", content =  @Content ),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content =  @Content ),
			@ApiResponse(description = "Not Acceptable", responseCode = "406", content =  @Content ),
			@ApiResponse(description = "Internal Error", responseCode = "500", content =  @Content )
	})
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
	@Operation(summary = "Update a existent Person"
	, description = "EndPoint to Update a existent Person by passing in a Json, XML or yml representtation of a person ",
	tags = {"People"},
	responses = {
			@ApiResponse(description = "Updated", responseCode = "200", content =  {
					@Content(
							mediaType = "application/json",
							 schema = @Schema(implementation = PersonVO.class))
					,@Content(
							mediaType = "application/xml",
							 schema = @Schema(implementation = PersonVO.class))
					
			} ),
			@ApiResponse(description = "Bad Request", responseCode = "400", content =  @Content ),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content =  @Content ),
			@ApiResponse(description = "Not Found", responseCode = "404", content =  @Content ),
			@ApiResponse(description = "Not Acceptable", responseCode = "406", content =  @Content ),
			@ApiResponse(description = "Internal Error", responseCode = "500", content =  @Content )
	})
	public PersonVO update(@PathVariable(value="id") Long id, @RequestBody PersonVO person)  
			throws Exception {
		
		return personService.update(id, person);
	}
	
	@Operation(summary = "Deletes a Person"
	, description = "EndPoint to Delete a Person",
	tags = {"People"},
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
		
		personService.delete(id);
		return ResponseEntity.noContent().build();
	}
	


}
