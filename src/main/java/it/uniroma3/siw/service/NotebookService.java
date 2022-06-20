package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Accessory;
import it.uniroma3.siw.model.Notebook;
import it.uniroma3.siw.repository.NotebookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class NotebookService {

    @Autowired
    private NotebookRepository notebookRepository;

    @Transactional
    public void save(Notebook notebook){
        this.notebookRepository.save(notebook);
    }

    public Notebook findById(Long id){
        return this.notebookRepository.findById(id).get();
    }

    public List<Notebook> findAll(){
        List<Notebook> notebookList = new ArrayList<>();
        for(Notebook notebook : this.notebookRepository.findAll()){
            notebookList.add(notebook);
        }
        return notebookList;
    }

    public boolean alreadyExists (Notebook notebook){
        return this.notebookRepository.existsByNameAndPrice(notebook.getName(), notebook.getPrice());
    }

    @Transactional
    public void deleteById(Long id) {
        this.notebookRepository.deleteById(id);
    }

    public Notebook findRandom() {
        List<Notebook> notebookList = (List<Notebook>) this.notebookRepository.findAll();
        Collections.shuffle(notebookList);
        //Ritorna il primo elemento dopo aver fatto lo shuffle della lista
        if(notebookList.size()!=0){
            return notebookList.get(0);
        }
        return null;
    }
}
