package it.uniroma3.siw.model.category;

public enum AccessoryCategory {
    mouse("Mouse"),
    tastiera("Tastiera"),
    altro("Altro");

    private final String displayValue;

    private AccessoryCategory(String displayValue){
    this.displayValue=displayValue;
    }
    public String getDisplayValue(){
        return displayValue;
    }

}
