package it.uniroma3.siw.service;

import it.uniroma3.siw.model.ComputerCase;
import it.uniroma3.siw.model.Vendor;
import it.uniroma3.siw.repository.ComputerCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ComputerCaseService {

    @Autowired
    private ComputerCaseRepository computerCaseRepository;

    @Transactional
    public void save(ComputerCase computerCase){
        this.computerCaseRepository.save(computerCase);
    }

    public ComputerCase findById(Long id){
        return this.computerCaseRepository.findById(id).get();
    }

    public List<ComputerCase> findAll(){
        List<ComputerCase> computerCaseList = new ArrayList<>();
        for(ComputerCase computerCase : this.computerCaseRepository.findAll()){
            computerCaseList.add(computerCase);
        }
        return computerCaseList;
    }

    public List<ComputerCase> findByVendor(Vendor vendor){
        return this.computerCaseRepository.findComputerCasesByVendor(vendor);
    }

    public boolean alreadyExists (ComputerCase computerCase){
        return this.computerCaseRepository.existsByNameAndPrice(computerCase.getName(), computerCase.getPrice());
    }

    @Transactional
    public void deleteById(Long id) {
        this.computerCaseRepository.deleteById(id);
    }
}
