package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Accessory;
import it.uniroma3.siw.model.Vendor;
import it.uniroma3.siw.model.category.AccessoryCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccessoryRepository extends CrudRepository<Accessory, Long> {

    List<Accessory> findAccessoriesByVendor(Vendor vendor);
    boolean existsByNameAndCategoryAndPrice(String name, AccessoryCategory category, float price);
}
