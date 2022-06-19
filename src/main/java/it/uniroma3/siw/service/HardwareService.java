package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Hardware;
import it.uniroma3.siw.model.category.HardwareCategory;
import it.uniroma3.siw.repository.HardwareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class HardwareService {

    @Autowired
    private HardwareRepository hardwareRepository;

    @Transactional
    public void save(Hardware hardware){
        this.hardwareRepository.save(hardware);
    }

    public Hardware findById(Long id){
        return this.hardwareRepository.findById(id).get();
    }

    public List<Hardware> findAll(){
        List<Hardware> hardwareList = new ArrayList<>();
        for(Hardware hardware : this.hardwareRepository.findAll()){
            hardwareList.add(hardware);
        }
        return hardwareList;
    }

    public Map<HardwareCategory, List<Hardware>> findOnCategory(){
        Map<HardwareCategory,List<Hardware>> hardwareMap = new TreeMap<>();
        for(Hardware hardware: this.hardwareRepository.findAll() ){
            if(hardwareMap.get(hardware.getCategory())==null){
                hardwareMap.put(hardware.getCategory(), new LinkedList<>());
            }
            hardwareMap.get(hardware.getCategory()).add(hardware);
        }
        return hardwareMap;
    }

    public boolean alreadyExists (Hardware hardware){
        return this.hardwareRepository.existsByNameAndCategoryAndPrice(hardware.getName(), hardware.getCategory(),hardware.getPrice());
    }

    @Transactional
    public void deleteById(Long id) {
        this.hardwareRepository.deleteById(id);
    }
}
