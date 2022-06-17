package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Accessory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface AccessoryRepository extends CrudRepository<Accessory, Long> {

    boolean existsByNameAndCategoryAndPrice(String name, String category, float price);
}
