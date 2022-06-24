package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Accessory;
import it.uniroma3.siw.model.OAuth;
import org.springframework.data.repository.CrudRepository;

public interface OAuthRepository extends CrudRepository<OAuth, Long> {
}
