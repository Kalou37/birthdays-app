package fr.kalnet.birthdaysapp.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.kalnet.birthdaysapp.models.Birthday;
import fr.kalnet.birthdaysapp.services.BirthdayService;
import fr.kalnet.birthdaysapp.services.UserService;

@RestController
@RequestMapping("/birthdays")
public class BirthdayController {
	
	@Autowired
	private BirthdayService birthdayService;
	
	@Autowired
	private UserService userService;
	
	// Return list of all birthdays
	@GetMapping(value = { "", "/" })
	public List<Birthday> getBirthdays() {
		return birthdayService.getAllBirthdays();
	}
	
	// Update and return a birthday since the id by year, month, day, firstname, lastname (all required)
	@PutMapping("/{birthdayId}/update")
	public Birthday updateBirthday(
			@RequestParam("email") String email, 
			@RequestParam("password") String password,
			@PathVariable("birthdayId") Long id, 
			@RequestParam("year") int year,
			@RequestParam("month") int month,
			@RequestParam("day") int day,
			@RequestParam("birthdayFirstname") String firstname,
			@RequestParam("birthdayLastname") String lastname) {
		if(userService.login(email, password) != null && birthdayService.isBirthdayToUser(id, userService.login(email, password).getId())) {
			return birthdayService.updateBirthday(id, LocalDate.of(year, month, day), firstname, lastname);
		}
		else return null;
	}
	
	// Delete a birthday since the id
	@DeleteMapping("/{birthdayId}/delete")
	public void deleteBirthday(
			@RequestParam("email") String email, 
			@RequestParam("password") String password,
			@PathVariable("birthdayId") Long id) {
		if(userService.login(email, password) != null)
			birthdayService.deleteBirthday(id);
	}
}
