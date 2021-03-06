package com.uppoints.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.ok;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.uppoints.model.CustomError;
import com.uppoints.model.User;
import com.uppoints.service.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("users")
public class UserController {

	public static final Logger LOGGER = LoggerFactory.getLogger( UserController.class );

	private UserService service;

	@GetMapping
	public ResponseEntity<List<User>> list() {
		LOGGER.info( "Fetching all users" );
		List<User> users = service.list();
		if (users.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ok( users );
	}

	@GetMapping("{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		LOGGER.info( "Fetching User with id {}", id );
		User user = service.fetch( id );
		if (user == null) {
			String errorMessage = String.format( "User with id %s not found.", id );
			LOGGER.error( errorMessage );
			return new ResponseEntity<CustomError>( new CustomError( errorMessage ), NOT_FOUND );
		}
		return ok( user );
	}

	@PostMapping
	public ResponseEntity<User> save(@RequestBody User user, UriComponentsBuilder ucBuilder) {
		LOGGER.info( "Saving User: {}", user );
		User saved = service.save( user );
		return ResponseEntity.created( ucBuilder.path( "/users/{id}" ).buildAndExpand( saved.getId() ).toUri() ).build();
	}

	@PutMapping("{id}")
	public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody User user) {
		LOGGER.info( "Updating User with id {}", id );
		service.update( user );
		return ok( user );
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		LOGGER.info( "Deleting User with id {}", id );
		User user = service.fetch( id );
		if (user == null) {
			LOGGER.error( "Unable to delete. User with id {} not found.", id );
			return new ResponseEntity<CustomError>( new CustomError( "Unable to delete. User with id " + id + " not found." ), NOT_FOUND );
		}
		service.delete( id );
		return ResponseEntity.noContent().build();
	}

}
