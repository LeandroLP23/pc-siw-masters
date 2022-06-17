package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Hardware;
import it.uniroma3.siw.repository.HardwareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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
        for(Hardware vendor : this.hardwareRepository.findAll()){
            hardwareList.add(vendor);
        }
        return hardwareList;
    }

    public boolean alreadyExists (Hardware hardware){
        return this.hardwareRepository.existsByNameAndCategoryAndPrice(hardware.getName(), hardware.getCategory(),hardware.getPrice());
    }

    @Transactional
    public void deleteById(Long id) {
        this.hardwareRepository.deleteById(id);
    }
}
