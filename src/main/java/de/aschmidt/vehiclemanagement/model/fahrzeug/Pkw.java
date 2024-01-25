package de.aschmidt.vehiclemanagement.model.fahrzeug;

public class Pkw extends Fahrzeug {

    private boolean fahradTraeger;
    public void setFahradTraeger(boolean fahradTraeger) {
        this.fahradTraeger = fahradTraeger;
    }

    public Pkw(){
    }

    public Pkw(String marke, FahrzeugStatus fahrzeugStatus, String kennzeichen, double geschwindigkeit, String fahrerName) {
        super(marke, "PKW", fahrzeugStatus, kennzeichen, geschwindigkeit, fahrerName);
        this.fahradTraeger = true;
    }
}
