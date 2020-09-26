package fr.kalnet.birthdaysapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.kalnet.birthdaysapp.models.User;
import fr.kalnet.birthdaysapp.repositories.UserRepository;

@Service("users")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User login(String email, String password) {
		if(userRepository.login(email, password).isPresent())
		{
			return userRepository.login(email, password).get();
		}
		return null;
	}

	@Override
	public List<User> getAllUsers() {
		return (List<User>) userRepository.findAll();
	}

	@Override
	public User save(User user) {		// User newUser = new User(user.getId(), user.getUsername(), user.getEmail(), user.getPassword());
		return userRepository.save(user);
	}
	
	@Override
	public User getUser(Long id) {
		if(userRepository.findById(id).isPresent())
			return userRepository.findById(id).get();
		return null;
	}

	@Override
	public User updateUser(Long id, String username, String email, String password) {
		if(userRepository.findById(id).isPresent())
		{
			User userUpdate = userRepository.findById(id).get();
			userUpdate.setUsername(username);
			userUpdate.setEmail(email);
			userUpdate.setPassword(password);
			userRepository.save(userUpdate);
			return userUpdate;
		}
		return null;
	}

	@Override
	public void deleteUser(Long id) {
		if(userRepository.findById(id).isPresent())
			userRepository.deleteById(id);
	}

	@Override
	public boolean isUserExist(String email) {
		return userRepository.getUserByEmail(email).isPresent();
	}
}
