package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.ComputerBuild;
import org.springframework.data.repository.CrudRepository;

public interface ComputerBuildRepository extends CrudRepository<ComputerBuild, Long> {
    boolean existsByName(String name);
}
