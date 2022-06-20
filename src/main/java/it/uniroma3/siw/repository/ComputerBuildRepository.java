package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Accessory;
import it.uniroma3.siw.model.ComputerBuild;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public interface ComputerBuildRepository extends CrudRepository<ComputerBuild, Long> {
    boolean existsByNameAndPrice(String name,Float price);
}
