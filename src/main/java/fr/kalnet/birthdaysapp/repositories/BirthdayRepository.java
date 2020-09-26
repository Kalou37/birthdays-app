package fr.kalnet.birthdaysapp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import fr.kalnet.birthdaysapp.models.Birthday;

public interface BirthdayRepository extends CrudRepository<Birthday, Long> {
	
	@Query(value = "SELECT b FROM Birthday b WHERE b.id = ?1 and b.user.id = ?2")
	Optional<Birthday> isBirthdayToUser(Long bdId, Long userId);
	
}