package ma.solide.usermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ma.solide.usermanagement.model.LoginRequest;
import ma.solide.usermanagement.service.UserService;
import ma.solide.usermanagement.util.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final JwtUtil jwtUtil;
	
	@Autowired
	UserService userService;

	public AuthController(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		// Mock authentication logic (replace with database/user service in production)
		if(userService.existsByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword())) {
//		if ("admin".equals(loginRequest.getUsername()) && "password".equals(loginRequest.getPassword())) {
			String token = jwtUtil.generateToken(loginRequest.getUsername());
			return ResponseEntity.ok(new AuthResponse(token));
		} else {
			return ResponseEntity.status(401).body("Invalid username or password");
		}
	}

	// DTO for the authentication response
	public static class AuthResponse {
		private final String token;

		public AuthResponse(String token) {
			this.token = token;
		}

		public String getToken() {
			return token;
		}
	}
}
