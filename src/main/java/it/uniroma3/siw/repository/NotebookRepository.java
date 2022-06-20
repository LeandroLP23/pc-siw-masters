package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Notebook;
import org.springframework.data.repository.CrudRepository;

public interface NotebookRepository extends CrudRepository<Notebook, Long> {
    boolean existsByNameAndPrice(String name, Float price);
}
