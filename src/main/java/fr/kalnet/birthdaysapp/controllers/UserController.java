package fr.kalnet.birthdaysapp.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.kalnet.birthdaysapp.models.Birthday;
import fr.kalnet.birthdaysapp.models.User;
import fr.kalnet.birthdaysapp.services.BirthdayService;
import fr.kalnet.birthdaysapp.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private BirthdayService birthdayService;
	
	// Return all users (with they birthdays)
	@GetMapping(value = { "", "/" })
	public List<User> getUsers() {
		return userService.getAllUsers();
	}
	
	// Return a user since his id
	@GetMapping("/{userId}")
	public User getUserById(@PathVariable("userId") Long id) {
		return userService.getUser(id);
	}
	
	// Return list of all birthdays
	@GetMapping("/birthdays")
	public List<Birthday> getBirthdays() {
		return birthdayService.getAllBirthdays();
	}
	
	// Return birthdays list of user since his id
	@GetMapping("/{userId}/birthdays")
	public Set<Birthday> getBirthdaysByUser(@PathVariable("userId") Long id) {
		return userService.getUser(id).getBirthdays();
	}
	
	@PostMapping("/signin")
	public User addUser(@RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("password") String password) {
		if(userService.isUserExist(email))
			return null;
		else
			return userService.save(new User(username, email, password));
	}
	
	// Create birthday for a user since his id by year, month, day, firstname and lastname
	@PostMapping("/{userId}/addbirthday")
	public Birthday addBirthday(
			@PathVariable("userId") Long id,
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("year") int year,
			@RequestParam("month") int month,
			@RequestParam("day") int day,
			@RequestParam("birthdayFirstname") String firstname,
			@RequestParam("birthdayLastname") String lastname) {
		if(userService.login(email, password) != null)
			return birthdayService.save(new Birthday(LocalDate.of(year, month, day), firstname, lastname, userService.getUser(id)));
		else return null;
	}
	
	// Update a user since his id by username, email and password (all required)
	@PutMapping("/{userId}/update")
	public User updateUser(
			@PathVariable("userId") Long id, 
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("newusername") String newusername,
			@RequestParam("newemail") String newemail,
			@RequestParam("newpassword") String newpassword
			) {
		if(userService.login(email, password) != null)
			return userService.updateUser(id, newusername, newemail, newpassword);
		else return null;
	}
	
	// Update and return a birthday since the id by year, month, day, firstname, lastname (all required)
	@PutMapping("/{userId}/birthdays/{birthdayId}/update")
	public Birthday updateBirthday(
			@PathVariable("userId") Long userId, 
			@RequestParam("email") String email, 
			@RequestParam("password") String password,
			@PathVariable("birthdayId") Long birthdayId, 
			@RequestParam("year") int year,
			@RequestParam("month") int month,
			@RequestParam("day") int day,
			@RequestParam("birthdayFirstname") String firstname,
			@RequestParam("birthdayLastname") String lastname) {
		User user = userService.login(email, password);
		if(user != null && birthdayService.isBirthdayToUser(birthdayId, user.getId()) && userId == user.getId()) {
			return birthdayService.updateBirthday(birthdayId, LocalDate.of(year, month, day), firstname, lastname);
		}
		else return null;
	}
	
	// Delete a user since his id
	@DeleteMapping("/{userId}/delete")
	public void deleteUser(
			@PathVariable("userId") Long id,
			@RequestParam("email") String email,
			@RequestParam("password") String password
			) {
		if(userService.login(email, password) != null) {
			for (Birthday birthday : userService.getUser(id).getBirthdays()) {
				birthdayService.deleteBirthday(birthday.getId());
			}
			userService.deleteUser(id);
		}
	}
	
	// Delete a birthday since the id
	@DeleteMapping("/{userId}/birthdays/{birthdayId}/delete")
	public void deleteBirthday(
			@PathVariable("userId") Long userId,
			@RequestParam("email") String email, 
			@RequestParam("password") String password,
			@PathVariable("birthdayId") Long birthdayId) {
		User user = userService.login(email, password);
		if(user != null && user.getId() == userId)
			birthdayService.deleteBirthday(birthdayId);
	}
}
