package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Accessory;
import it.uniroma3.siw.model.Vendor;
import it.uniroma3.siw.repository.AccessoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Access;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class AccessoryService {

    @Autowired
    private AccessoryRepository accessoryRepository;

    @Transactional
    public void save(Accessory accessory){
        this.accessoryRepository.save(accessory);
    }

    public Accessory findById(Long id){
        return this.accessoryRepository.findById(id).get();
    }

    public List<Accessory> findAll(){
        List<Accessory> accessoryList = new ArrayList<>();
        for(Accessory accessory : this.accessoryRepository.findAll()){
            accessoryList.add(accessory);
        }
        return accessoryList;
    }

    public Accessory findRandom(){
        List<Accessory> accessoryList = (List<Accessory>) this.accessoryRepository.findAll();
        Collections.shuffle(accessoryList);
        //Ritorna il primo elemento dopo aver fatto lo shuffle della lista
        if(accessoryList!=null){
            return accessoryList.get(0);
        }
        return null;
    }

    public List<Accessory> findByVendor(Vendor vendor){
        return this.accessoryRepository.findAccessoriesByVendor(vendor);
    }

    public boolean alreadyExists (Accessory accessory){
        return this.accessoryRepository.existsByNameAndCategoryAndPrice(accessory.getName(),  accessory.getCategory(), accessory.getPrice());
    }

    @Transactional
    public void deleteById(Long id) {
        this.accessoryRepository.deleteById(id);
    }
}
