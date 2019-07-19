package com.pioneer.userreg.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pioneer.userreg.domain.UserDTO;
import com.pioneer.userreg.exception.CustomErrorType;
import com.pioneer.userreg.repository.UserRepository;

@RestController
@RequestMapping("/api/user")
public class UserController {

	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private UserRepository userRepository;
	
	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@GetMapping("/")
	public ResponseEntity<List<UserDTO>> listAllUsers(){
		List<UserDTO> users =  userRepository.findAll();
		if(CollectionUtils.isEmpty(users)) {
			logger.debug("User List : {}", users) ;
			return new ResponseEntity<List<UserDTO>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<UserDTO>>(users,HttpStatus.OK);
	}
	
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody final UserDTO user){
		if(userRepository.findByName(user.getName()) != null) {
			return new ResponseEntity<UserDTO>(new CustomErrorType("User exists with name " + user.getName()), HttpStatus.CONFLICT);
		}
		userRepository.save(user);
		return new ResponseEntity<UserDTO>(user, HttpStatus.CREATED);
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<UserDTO> getById(@PathVariable("id") final Long id){
		UserDTO user = userRepository.findById(id);
		if(user != null) {
			return new ResponseEntity<UserDTO>(user,HttpStatus.OK);
		}else {
			return new ResponseEntity<UserDTO>(new CustomErrorType("User with id " + id + "not found"), HttpStatus.NOT_FOUND);
		}
		
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<UserDTO> getByName(@PathVariable("name") final String name){
		UserDTO user = userRepository.findByName(name);
		if(user != null) {
			return new ResponseEntity<UserDTO>(user,HttpStatus.OK);
		}else {
			return new ResponseEntity<UserDTO>(new CustomErrorType("User with name " + name + "not found"), HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserDTO> udpateById(@PathVariable("id") final Long id, @Valid @RequestBody final UserDTO user){
		UserDTO existingUserOption = userRepository.findById(id);
		
		// Update
		if(existingUserOption != null) {
			UserDTO existingUser = existingUserOption;
			existingUser.setName(user.getName());
			existingUser.setAddress(user.getAddress());
			existingUser.setEmail(user.getEmail());
			
			//save and flush
			userRepository.saveAndFlush(existingUser);
			
			//return ReponseEntity
			return new ResponseEntity<UserDTO>(existingUser, HttpStatus.OK);
		}else {
			return new ResponseEntity<UserDTO>(new CustomErrorType("Unable to update.User with id " + id + " not found"), HttpStatus.NOT_FOUND);
		}
		
		
		
	}
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hadAuthority('ADMIN')")
	public ResponseEntity<UserDTO> deleteUser(@PathVariable("id") final Long id){
		UserDTO existingUserOptional = userRepository.findById(id);
		if(existingUserOptional != null) {
			userRepository.delete(existingUserOptional);
			return new ResponseEntity<UserDTO>(HttpStatus.NO_CONTENT);
		}else {
			return new ResponseEntity<UserDTO>(new CustomErrorType("Unable to delete.User with id " + id + " not found"),HttpStatus.NOT_FOUND);
		}
		
	}
	
}
