package it.uniroma3.siw.service;

import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public void save(User o) {
		userRepository.save(o);
	}
	
	public void update(User o) {
		this.delete(o);
		this.save(o);
	}


	public void delete(User o) {
		userRepository.delete(o);
	}

	public User findById(Long id) {
		return userRepository.findById(id).get();
	}

	public List<User> findAll() {
		List<User> users = new LinkedList<User>();

		for (User i : userRepository.findAll()) {
			users.add(i);
		}

		return users;
	}

	public boolean alreadyExists(User target) {
		return this.userRepository.findByNomeAndCognomeAndEmail(
				target.getNome(),
				target.getCognome(),
				target.getEmail()
		) != null;
	}
}
