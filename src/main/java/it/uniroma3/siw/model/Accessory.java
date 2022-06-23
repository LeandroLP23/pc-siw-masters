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
    private Float price;

    @ManyToOne
    private Vendor vendor;

    @ManyToMany (cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private List<ComputerBuild> computerBuildList;

    @Column(nullable = false)
    private String picture;

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass().equals(this.getClass())) {
            Accessory that = (Accessory) obj;
            return this.name.equals(that.getName())
                    && this.category.equals(that.getCategory())
                    && this.price.equals(that.getPrice())
                    && this.vendor.equals(that.getVendor());
        }
        return false;
    }

    public Accessory getAccessory(){
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public List<ComputerBuild> getComputerBuildList() {
        return computerBuildList;
    }

    public void setComputerBuildList(List<ComputerBuild> computerBuildList) {
        this.computerBuildList = computerBuildList;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
