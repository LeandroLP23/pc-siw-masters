package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.ComputerCase;
import it.uniroma3.siw.model.Vendor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ComputerCaseRepository extends CrudRepository<ComputerCase, Long> {
    List<ComputerCase> findComputerCasesByVendor(Vendor vendor);
    boolean existsByNameAndPrice(String name, float price);
}
