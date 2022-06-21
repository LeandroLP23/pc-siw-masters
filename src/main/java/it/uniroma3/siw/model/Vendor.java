package it.uniroma3.siw.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL)
    private List<ComputerCase> computerCaseList;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL)
    private List<Accessory> accessoryList;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL)
    private List<Hardware> hardwareList;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL)
    private List<Notebook> notebookList;

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass().equals(this.getClass())) {
            Vendor that = (Vendor) obj;
            return this.name.equals(that.getName());
        }
        return false;
    }

    public Vendor getVendor(){
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ComputerCase> getComputerCaseList() {
        return computerCaseList;
    }

    public void setComputerCaseList(List<ComputerCase> computerCaseList) {
        this.computerCaseList = computerCaseList;
    }

    public List<Accessory> getAccessoryList() {
        return accessoryList;
    }

    public void setAccessoryList(List<Accessory> accessoryList) {
        this.accessoryList = accessoryList;
    }

    public List<Hardware> getHardwareList() {
        return hardwareList;
    }

    public void setHardwareList(List<Hardware> hardwareList) {
        this.hardwareList = hardwareList;
    }

    public List<Notebook> getNotebookList() {
        return notebookList;
    }

    public void setNotebookList(List<Notebook> notebookList) {
        this.notebookList = notebookList;
    }
}
