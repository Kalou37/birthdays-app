package fr.kalnet.birthdaysapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.kalnet.birthdaysapp.models.User;
import fr.kalnet.birthdaysapp.services.UserService;

@RestController
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	// Return a user with credential (name password)
	@PostMapping(value = {"", "/"})
	public User postLoginUser(@RequestParam("email") String email, @RequestParam("password") String password) {
		return userService.login(email, password);
	}
}
