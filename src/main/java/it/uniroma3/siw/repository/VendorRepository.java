package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Vendor;
import org.springframework.data.repository.CrudRepository;

public interface VendorRepository extends CrudRepository<Vendor, Long> {
    boolean existsByName(String name);
}
