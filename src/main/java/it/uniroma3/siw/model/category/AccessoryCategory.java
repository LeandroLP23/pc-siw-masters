package it.uniroma3.siw.model.category;

public enum AccessoryCategory {
    MOUSE("Mouse"),
    TASTIERA("Tastiera"),
    ALTRO("Altro");

    private final String displayValue;

    private AccessoryCategory(String displayValue){
    this.displayValue=displayValue;
    }
    public String getDisplayValue(){
        return displayValue;
    }

}
