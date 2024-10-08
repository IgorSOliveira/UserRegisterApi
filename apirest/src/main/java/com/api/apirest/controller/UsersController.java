package com.api.apirest.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.apirest.dto.UpdateUserDto;
import com.api.apirest.dto.UserDto;
import com.api.apirest.entity.User;
import com.api.apirest.service.UserService;

@RestController
@RequestMapping("/users")
public class UsersController {

	private final UserService userService;

	public UsersController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
		var userId = userService.createUser(userDto);

		return ResponseEntity.created(URI.create("/users/" + userId.toString())).build();
	}

	@GetMapping("/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable("userId") String userId) {
		var user = userService.getUserById(userId);

		if (user.isPresent()) {
			return ResponseEntity.ok(user.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping
	public ResponseEntity<List<User>> listAllUsers() {
		var users = userService.listUsers();

		return ResponseEntity.ok(users);
	}

	@PutMapping("/{userId}")
	public ResponseEntity<Void> updateUserById(@PathVariable("userId") String userId, @RequestBody UpdateUserDto updateUserDto) {
		userService.UpdateUserById(userId, updateUserDto);
		
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<Void> deleteById(@PathVariable("userId") String userId) {
		userService.deleteById(userId);
		return ResponseEntity.noContent().build();
	}

}
