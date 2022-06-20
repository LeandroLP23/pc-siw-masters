package it.uniroma3.siw.model;

import it.uniroma3.siw.model.category.HardwareCategory;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Entity
public class ComputerBuild {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Float price;

    @ManyToMany
    private List<Hardware> hardwareList;

    @ManyToMany
    private List<Accessory> accessoryList;

    @ManyToOne
    private ComputerCase computerCase;

    public Map<HardwareCategory,Hardware> getMappedHardware(){
        Map<HardwareCategory,Hardware> hardwareMap = new TreeMap<>();
        for(Hardware hardware: this.hardwareList){
            hardwareMap.put(hardware.getCategory(),hardware);
        }
        return hardwareMap;
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public List<Hardware> getHardwareList() {
        return hardwareList;
    }

    public void setHardwareList(List<Hardware> hardwareList) {
        this.hardwareList = hardwareList;
    }

    public List<Accessory> getAccessoryList() {
        return accessoryList;
    }

    public void setAccessoryList(List<Accessory> accessoryList) {
        this.accessoryList = accessoryList;
    }

    public ComputerCase getComputerCase() {
        return computerCase;
    }

    public void setComputerCase(ComputerCase computerCase) {
        this.computerCase = computerCase;
    }
}
