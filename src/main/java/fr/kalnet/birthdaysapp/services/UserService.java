package fr.kalnet.birthdaysapp.services;

import java.util.List;

import fr.kalnet.birthdaysapp.models.User;

public interface UserService {
	public User login(String email, String password);
	public boolean isUserExist(String email);
	public List<User> getAllUsers();
	public User save(User user);
	public User getUser(Long id);
	public User updateUser(Long id, String username, String email, String password);
	public void deleteUser(Long id);
}
