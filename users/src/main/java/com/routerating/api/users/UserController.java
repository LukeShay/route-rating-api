package com.routerating.api.users;

import com.routerating.api.common.user.User;
import com.routerating.api.common.user.UserService;
import com.routerating.api.common.user.UserTypes;
import com.routerating.api.common.utils.Annotations.*;
import com.routerating.api.common.utils.BodyUtils;
import com.routerating.api.common.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller("/api")
public class UserController {

	private static Logger LOG = LoggerFactory.getLogger(UserController.class.getName());

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@AdminPostMapping("/admin")
	public ResponseEntity<?> createAdminUser(Authentication authentication, @RequestBody User body) {
		LOG.debug("Creating user {}", body.toString());

		Map<String, String> responseBody = new HashMap<>();

		boolean validEmail = userService.validateEmail(responseBody, authentication, body.getEmail());
		boolean validUsername = userService.validateUsername(responseBody, authentication, body.getUsername());
		boolean validPassword = userService.validatePassword(responseBody, body.getPassword());
		//    boolean validState = userService.validateState(responseBody, body.getState());

		if (!validEmail || !validUsername || !validPassword) {
			return ResponseUtils.badRequest(responseBody);
		}

		return userService.createUser(body, UserTypes.ADMIN);
	}

	@UnauthPostMapping("/new")
	public ResponseEntity<?> createUser(@RequestBody NewUserBody body) {
		LOG.debug("Creating user {}", body.toString());

		Map<String, String> responseBody = new HashMap<>();

		boolean validEmail = userService.validateEmail(responseBody, null, body.getEmail());
		boolean validUsername = userService.validateUsername(responseBody, null, body.getUsername());
		boolean validPassword = userService.validatePassword(responseBody, body.getPassword());
		boolean validRecaptcha = userService.validateRecaptcha(responseBody, body.getRecaptcha());
		// boolean validState = userService.validateState(responseBody, body.getState());

		if (!validEmail || !validUsername || !validPassword || !validRecaptcha) {
			return ResponseUtils.badRequest(responseBody);
		}

		return userService.createUser(body.getUser(), UserTypes.ADMIN);
	}

	@AdminDeleteMapping("/{userId}")
	public ResponseEntity<?> deleteUserByUserId(@PathVariable String userId) {
		User deletedUser = userService.deleteUserById(userId);

		if (deletedUser == null) {
			return ResponseUtils.badRequest(BodyUtils.error("User not found."));
		}
		else {
			return ResponseUtils.ok(deletedUser);
		}
	}

	@AdminGetMapping("/all")
	public ResponseEntity<?> getAllUsers() {
		LOG.debug("Getting all users.");

		Iterable<User> users = userService.getAllUsers();

		return ResponseUtils.ok(users);
	}

	@AuthGetMapping("")
	public ResponseEntity<?> getUser(Authentication authentication) {
		LOG.debug("Getting user.");

		User user = userService.getUser(authentication);

		if (user == null) {
			LOG.debug("Could not find user");
			return ResponseUtils.notFound(BodyUtils.error("User not found."));
		}
		else {
			return ResponseUtils.ok(user);
		}
	}

	@AuthPutMapping("")
	public ResponseEntity<?> updateUser(Authentication authentication, @RequestBody User body) {
		LOG.debug("Updating user {}", body);

		Map<String, String> responseBody = new HashMap<>();

		boolean validEmail = userService.validateEmail(responseBody, authentication, body.getEmail());
		boolean validUsername = userService.validateUsername(responseBody, authentication, body.getUsername());
		boolean validPassword = userService.validatePassword(
				responseBody,
				body.getPassword()
		) || body.getPassword() == null || body.getPassword().equals("");
		//    boolean validState = userService.validateState(responseBody, body.getState());

		if (!validEmail || !validUsername || !validPassword) {
			return ResponseUtils.badRequest(responseBody);
		}

		body = userService.updateUser(
				authentication,
				body.getUsername(),
				body.getEmail(),
				body.getFirstName(),
				body.getLastName(),
				body.getCity(),
				body.getState(),
				body.getCountry(),
				body.getPassword()
		);

		if (body == null) {
			LOG.debug("User was not found");

			return ResponseUtils.badRequest(BodyUtils.error("User not found."));
		}
		else {
			return ResponseUtils.ok(body);
		}
	}
}
