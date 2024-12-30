package ma.solide.usermanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import ma.solide.usermanagement.model.User;
import ma.solide.usermanagement.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository UserRepository;

	public Optional<User> getUser(Integer userNo) {
		if (userNo == null) {
			throw new IllegalArgumentException("User number cannot be null");
		}
		Optional<User> user = UserRepository.findById(userNo);
		return user;
	}

	public boolean existsBySurnameAndPassword(String username, String password)
	{
		return UserRepository.existsBySurnameAndPassword(username, password);
		
	}
	public List<User> findAllUsers() {
		return UserRepository.findAll();
	}

	public User createUser(User user) {

		return UserRepository.save(user); // Inserts or updates the user
	}
}