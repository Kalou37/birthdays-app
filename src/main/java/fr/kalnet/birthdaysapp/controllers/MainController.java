package fr.kalnet.birthdaysapp.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.kalnet.birthdaysapp.models.Birthday;
import fr.kalnet.birthdaysapp.models.User;
import fr.kalnet.birthdaysapp.services.UserService;

@Controller
@RequestMapping("/")
public class MainController implements ErrorController{
	
	@Autowired
	private UserService userService;
	
	private User user;
	
	private List<Birthday> birthdays;
	
	private boolean error = false;
	private boolean emailExist = false;
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("failed", error);
		return "index";
	}
			
	@GetMapping("/test")
    public String test(Model model) {
		if(user==null) return "redirect:/";
		birthdays = userService.getUser(user.getId()).getBirthdays();
		model.addAttribute("user", user);
		model.addAttribute("birthdays", birthdays);
        return "test";
    }
	
	@GetMapping("/signin")
	public String signin(Model model) {
		model.addAttribute("email", emailExist);
		return "signin";
	}
	
	@PostMapping("/connexion")
	public String connexion(@ModelAttribute User userModel) {
		this.user = userService.login(userModel.getEmail(), userModel.getPassword());
		if(user==null) {
			error = true;
			return "redirect:/";
		}
		return "redirect:test";
	}
	
	@PostMapping("/save")
	public String saveUser(@ModelAttribute User userModel) {
		if(userService.isUserExist(userModel.getEmail())) {
			emailExist = true;
			return "redirect:signin";
		}
		else
			user = userService.save(new User(userModel.getUsername(), userModel.getEmail(), userModel.getPassword()));
		return "redirect:test";
	}
	
	@Override
	@GetMapping("/error")
	public String getErrorPath() {
		return "error";
	}
}
