package it.uniroma3.siw.model;

import it.uniroma3.siw.model.category.HardwareCategory;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Entity
public class Hardware {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private HardwareCategory category;

    @Column(nullable = false)
    private Float price;

    @ManyToOne
    private Vendor vendor;

    @ManyToMany (cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE}, mappedBy = "hardwareList")
    private List<ComputerBuild> computerBuildList;

    @Column(nullable = false)
    private String picture;

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass().equals(this.getClass())) {
            Hardware that = (Hardware) obj;
            return this.name.equals(that.getName())
                    && this.category.getDisplayValue().equals(that.getCategory().getDisplayValue())
                    && this.price.equals(that.getPrice())
                    && this.vendor == (that.getVendor());
        }
        return false;
    }

    public Hardware getHardware(){
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

    public HardwareCategory getCategory() {
        return category;
    }

    public void setCategory(HardwareCategory category) {
        this.category = category;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
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
