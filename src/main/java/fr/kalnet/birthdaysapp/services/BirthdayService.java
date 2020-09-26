package fr.kalnet.birthdaysapp.services;

import java.time.LocalDate;
import java.util.List;

import fr.kalnet.birthdaysapp.models.Birthday;

public interface BirthdayService {
	public List<Birthday> getAllBirthdays();
	public Birthday save(Birthday birthday);
	public Birthday updateBirthday(Long id, LocalDate date, String firstname, String lastname);
	public void deleteBirthday(Long id);
	public boolean isBirthdayToUser(Long bdId, Long userId);
}
