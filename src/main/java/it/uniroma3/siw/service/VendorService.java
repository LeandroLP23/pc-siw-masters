package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Vendor;
import it.uniroma3.siw.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    @Transactional
    public void save(Vendor vendor){
        this.vendorRepository.save(vendor);
    }

    public Vendor findById(Long id){
        return this.vendorRepository.findById(id).get();
    }

    public List<Vendor> findAll(){
        List<Vendor> vendorList = new ArrayList<>();
        for(Vendor vendor : this.vendorRepository.findAll()){
            vendorList.add(vendor);
        }
        return vendorList;
    }

    public boolean alreadyExists (Vendor vendor){
        return this.vendorRepository.existsByName(vendor.getName());
    }

    @Transactional
    public void deleteById(Long id) {
        this.vendorRepository.deleteById(id);
    }
}
