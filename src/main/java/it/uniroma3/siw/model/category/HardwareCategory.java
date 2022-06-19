package it.uniroma3.siw.model.category;

public enum HardwareCategory {
    cpu ("Processore - CPU"),
    gpu("Scheda Video - GPU"),
    motherboard("Scheda Madre"),
    ram ("Memoria RAM"),
    storage ("Memoria Secondaria"),
    powerSupply("Alimentatore");
    private final String displayValue;

    HardwareCategory(String displayValue){
        this.displayValue=displayValue;
    }
    public String getDisplayValue(){
        return displayValue;
    }
}
