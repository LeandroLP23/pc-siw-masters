package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.ComputerCase;
import org.springframework.data.repository.CrudRepository;

public interface ComputerCaseRepository extends CrudRepository<ComputerCase, Long> {
    boolean existsByNameAndPrice(String name, float price);
}
