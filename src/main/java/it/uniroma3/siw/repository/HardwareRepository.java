package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Hardware;
import org.springframework.data.repository.CrudRepository;

public interface HardwareRepository extends CrudRepository<Hardware, Long> {
    boolean existsByNameAndCategoryAndPrice(String name, String category, float price);
}
