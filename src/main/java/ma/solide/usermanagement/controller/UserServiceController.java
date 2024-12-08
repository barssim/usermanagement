package ma.solide.usermanagement.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ma.solide.usermanagement.model.User;
import ma.solide.usermanagement.service.UserService;

@RestController
@RequestMapping("/users")
public class UserServiceController {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceController.class);

	@Autowired
	UserService userService;

	// call example: http://localhost:8091/users/4
	@GetMapping("/{userNo}")
	public ResponseEntity<Object> getUserByUserNo(@PathVariable("userNo") Integer userNo) {
		logger.info("retrievs User by userno");
		Optional<User> user;
		try {

			user = userService.getUser(userNo);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>("User number cannot be null", HttpStatus.BAD_REQUEST);
		}

		if (user == null) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@RequestMapping("/")
	public ResponseEntity<Object> getAllUsers() {
		logger.info("retrievs all users");
		List<User> users = userService.findAllUsers();
		return new ResponseEntity<Object>(users, HttpStatus.OK);
	}

	@CrossOrigin(origins = "http://localhost:3000") // Allow React app to call this endpoint
	@PostMapping("/new")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		logger.info("create user:" + user.getUserno());
		User createdUser = userService.createUser(user);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}
}
