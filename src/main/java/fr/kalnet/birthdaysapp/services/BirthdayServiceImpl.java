package fr.kalnet.birthdaysapp.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.kalnet.birthdaysapp.models.Birthday;
import fr.kalnet.birthdaysapp.repositories.BirthdayRepository;

@Service("birthdays")
public class BirthdayServiceImpl implements BirthdayService{

	@Autowired
	private BirthdayRepository birthdayRepository;
	
	@Override
	public List<Birthday> getAllBirthdays() {
		return (List<Birthday>) birthdayRepository.findAll();
	}
	
	@Override
	public Birthday save(Birthday birthday) {
		return birthdayRepository.save(birthday);
	}

	@Override
	public Birthday updateBirthday(Long id, LocalDate date, String firstname, String lastname) {
		if(birthdayRepository.findById(id).isPresent())
		{
			Birthday birthdayUpdate = birthdayRepository.findById(id).get();
			birthdayUpdate.setDate(date);
			birthdayUpdate.setFirstname(firstname);
			birthdayUpdate.setLastname(lastname);
			birthdayRepository.save(birthdayUpdate);
			return birthdayUpdate;
		}
		return null;
	}

	@Override
	public void deleteBirthday(Long id) {
		if(birthdayRepository.findById(id).isPresent())
			birthdayRepository.deleteById(id);		
	}

	@Override
	public boolean isBirthdayToUser(Long bdId, Long userId) {
		return birthdayRepository.isBirthdayToUser(bdId, userId).isPresent();
	}

	@Override
	public Birthday getBirthday(Long id) {
		if(birthdayRepository.findById(id).isPresent())
			return birthdayRepository.findById(id).get();
		return null;
	}

}
