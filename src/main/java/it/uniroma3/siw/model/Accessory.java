package it.uniroma3.siw.model;

import it.uniroma3.siw.model.category.AccessoryCategory;

import javax.persistence.*;
import java.util.List;

@Entity
public class Accessory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private AccessoryCategory category;

    @Column(nullable = false)
    private float price;

    @ManyToOne
    private Vendor vendor;

    @ManyToMany (cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private List<ComputerBuild> computerBuildList;

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

    public AccessoryCategory getCategory() {
        return category;
    }

    public void setCategory(AccessoryCategory category) {
        this.category = category;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    public List<ComputerBuild> getComputerBuildList() {
        return computerBuildList;
    }

    public void setComputerBuildList(List<ComputerBuild> computerBuildList) {
        this.computerBuildList = computerBuildList;
    }

}
