package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Accessory;
import it.uniroma3.siw.repository.AccessoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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

    public boolean alreadyExists (Accessory accessory){
        return this.accessoryRepository.existsByNameAndCategoryAndPrice(accessory.getName(),  accessory.getCategory(), accessory.getPrice());
    }

    @Transactional
    public void deleteById(Long id) {
        this.accessoryRepository.deleteById(id);
    }
}
