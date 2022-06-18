package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Accessory;
import it.uniroma3.siw.model.category.AccessoryCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface AccessoryRepository extends CrudRepository<Accessory, Long> {

    boolean existsByNameAndCategoryAndPrice(String name, AccessoryCategory category, float price);
}
