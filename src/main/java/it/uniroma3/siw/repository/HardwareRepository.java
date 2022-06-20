package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Hardware;
import it.uniroma3.siw.model.Vendor;
import it.uniroma3.siw.model.category.HardwareCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HardwareRepository extends CrudRepository<Hardware, Long> {
    boolean existsByNameAndCategoryAndPrice(String name, HardwareCategory category, Float price);
    List<Hardware> findHardwareByVendor(Vendor vendor);
}
