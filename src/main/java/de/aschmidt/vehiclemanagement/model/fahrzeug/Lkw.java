package de.aschmidt.vehiclemanagement.model.fahrzeug;

public class Lkw extends Fahrzeug {
    private double lastGrenze;
    public double getLastGrenze() {
        return lastGrenze;
    }
    public void setLastGrenze(double lastGrenze) {
        this.lastGrenze = lastGrenze;
    }

    public Lkw(){
    }

    public Lkw(String marke, FahrzeugStatus fahrzeugStatus, String kennzeichen, double geschwindigkeit, String fahrerName) {
        super(marke, "LKW", fahrzeugStatus, kennzeichen, geschwindigkeit, fahrerName);
        this.lastGrenze = 20.000;
    }
}
