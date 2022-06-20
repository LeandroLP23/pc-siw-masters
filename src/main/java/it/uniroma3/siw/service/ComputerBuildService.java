package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Accessory;
import it.uniroma3.siw.model.ComputerBuild;
import it.uniroma3.siw.repository.ComputerBuildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ComputerBuildService {

    @Autowired
    private ComputerBuildRepository computerBuildRepository;

    @Transactional
    public void save(ComputerBuild computerBuild){
        this.computerBuildRepository.save(computerBuild);
    }

    public ComputerBuild findById(Long id){
        return this.computerBuildRepository.findById(id).get();
    }

    public List<ComputerBuild> findAll(){
        List<ComputerBuild> computerBuildList = new ArrayList<>();
        for(ComputerBuild computerBuild : this.computerBuildRepository.findAll()){
            computerBuildList.add(computerBuild);
        }
        return computerBuildList;
    }

    public boolean alreadyExists (ComputerBuild computerBuild){
        return this.computerBuildRepository.existsByName(computerBuild.getName());
    }

    @Transactional
    public void deleteById(Long id) {
        this.computerBuildRepository.deleteById(id);
    }

    public ComputerBuild findRandom() {
        List<ComputerBuild> computerBuildList = (List<ComputerBuild>) this.computerBuildRepository.findAll();
        Collections.shuffle(computerBuildList);
        //Ritorna il primo elemento dopo aver fatto lo shuffle della lista
        if(computerBuildList.size()!=0){
            return computerBuildList.get(0);
        }
        return null;
    }
}
