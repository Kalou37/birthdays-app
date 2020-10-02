package fr.kalnet.birthdaysapp.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.kalnet.birthdaysapp.adapters.BirthdayItem;
import fr.kalnet.birthdaysapp.adapters.ListItem;
import fr.kalnet.birthdaysapp.adapters.MonthItem;
import fr.kalnet.birthdaysapp.models.Birthday;
import fr.kalnet.birthdaysapp.models.User;
import fr.kalnet.birthdaysapp.services.BirthdayService;
import fr.kalnet.birthdaysapp.services.UserService;

@Controller
@RequestMapping("/")
public class MainController implements ErrorController{
		
	@Autowired
	private UserService userService;
	
	@Autowired
	private BirthdayService birthdayService;
	
	HttpSession session;
	
	private boolean error = false;
	private boolean emailExist = false;
	
	@GetMapping("/")
	public String index(Model model, HttpSession session) {
		if (session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			user = userService.login(user.getEmail(), user.getPassword());
			if(user != null) return "redirect:user";
			else {
				model.addAttribute("failed", false);
				return "index";
			}
		} else {
			model.addAttribute("failed", error);
			return "index";
		}
	}
			
	@GetMapping("/user")
    public String user(Model model, HttpSession session) {
		if(session.isNew() || session.getAttribute("user") == null) return "redirect:/";
		else {
			
			List<ListItem> mListItems = new ArrayList<>();

			User user = (User) session.getAttribute("user");
			List<Birthday> birthdays = userService.getUser(user.getId()).getBirthdays();
			
			String[] months = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Décembre"};
	        birthdays.sort(Comparator.comparing(Birthday::getDate));
	        for(int i = 0; i<12; i++){
	        	mListItems.add(new MonthItem(months[i]));
	            for (Birthday bd : birthdays){
	                if(bd.getDate().getMonthValue()-1==i) mListItems.add(new BirthdayItem(bd));
	            }
	        }
			session.setAttribute("birthdays", birthdays);
			model.addAttribute("user", user);
			model.addAttribute("birthdays", birthdays);
			model.addAttribute("listitems", mListItems);
	        return "user";
		}
    }
	
	@GetMapping("/signin")
	public String signin(Model model) {
		model.addAttribute("email", emailExist);
		return "signin";
	}
	
	@PostMapping("/connexion")
	public String connexion(@ModelAttribute User userModel, HttpSession session) {
		User user = userService.login(userModel.getEmail(), userModel.getPassword());
		if(user==null) {
			error = true;
			return "redirect:/";
		}
		session.setAttribute("user", user);
		return "redirect:user";
	}
	
	@PostMapping("/save")
	public String saveUser(@ModelAttribute User userModel,HttpSession session) {
		if(userService.isUserExist(userModel.getEmail())) {
			emailExist = true;
			return "redirect:signin";
		}
		else {
			User user = userService.save(new User(userModel.getUsername(), userModel.getEmail(), userModel.getPassword()));
			session.setAttribute("user", user);
		}
		return "redirect:user";
	}
	
	@GetMapping("/addbirthday")
	public String addBirthday(HttpSession session) {
		if(session.isNew() || session.getAttribute("user") == null) return "redirect:/";
		else return "addbirthday";
	}
	
	@PostMapping("/savebirthday")
	public String saveBirthday(
			@RequestParam("firstname") String firstname, 
			@RequestParam("lastname") String lastname,
			@RequestParam("date") String date, 
			HttpSession session) {
		if(session.isNew() || session.getAttribute("user") == null) return "redirect:/";
		else {
			Birthday birthday = null;
			User user = (User) session.getAttribute("user");
			Long id = (session.getAttribute("bdid") != null) ? (Long) session.getAttribute("bdid") : 0;
			session.removeAttribute("bdid");
			if(user.getId() > 0) {
				if(id > 0) {
					if(birthdayService.isBirthdayToUser(id, user.getId()))
						birthday = birthdayService.updateBirthday(id, LocalDate.parse(date), firstname, lastname);
					else return "error";	
				}
				else
					birthday = birthdayService.save(new Birthday(LocalDate.parse(date), firstname, lastname, user));
				if (birthday == null) return "error";
			}
			return "redirect:user";
		}
	}
	
	@GetMapping("/updatebirthday/{bdId}")
	public String updateBirthday(@PathVariable("bdId") Long id, Model model, HttpSession session) {
		if((session.isNew() || session.getAttribute("user") == null) && birthdayService.isBirthdayToUser(id, ((User) session.getAttribute("user")).getId())) return "error";
		else {
			Birthday birthday = birthdayService.getBirthday(id);
			session.setAttribute("bdid", id);
			model.addAttribute("birthday", birthday);
			return "updatebirthday";
		}
	}
	
	@GetMapping("/deletebirthday/{bdId}")
	public String deleteBirthday(@PathVariable("bdId") Long id, Model model, HttpSession session) {
		if((session.isNew() || session.getAttribute("user") == null) && birthdayService.isBirthdayToUser(id, ((User) session.getAttribute("user")).getId())) return "error";
		else {
			birthdayService.deleteBirthday(id);
			return "redirect:/user";
		}
	}
	
	@Override
	@GetMapping("/error")
	public String getErrorPath() {
		return "error";
	}
}
