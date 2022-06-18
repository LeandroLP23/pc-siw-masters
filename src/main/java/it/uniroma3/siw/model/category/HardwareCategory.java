package it.uniroma3.siw.model.category;

public enum HardwareCategory {
    CPU("Processore - CPU"),
    GPU("Scheda Video - GPU"),
    SCHEDAMADRE("Scheda Madre"),
    RAM ("Memoria RAM"),
    MemoriaSecondaria ("Memoria Secondaria"),
    Alimentatore("Alimentatore");
    private final String displayValue;

    private HardwareCategory(String displayValue){
        this.displayValue=displayValue;
    }
    public String getDisplayValue(){
        return displayValue;
    }
}
