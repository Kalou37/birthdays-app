package fr.kalnet.birthdaysapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.kalnet.birthdaysapp.models.User;
import fr.kalnet.birthdaysapp.services.UserService;

@RestController
@RequestMapping("/")
public class MainController implements ErrorController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
    public String main() {
        return String.format("<h1>Accueil !</h1>");
    }

	@Override
	@RequestMapping(value = { "error", "error/" })
	public String getErrorPath() {
		return "<center><h1>Oops !</h1></center>";
	}

	// Return a user with credential (name password)
	@PostMapping("/login")
	public User getLoginUser(@RequestParam("email") String email, @RequestParam("password") String password) {
		return userService.login(email, password);
	}
}
