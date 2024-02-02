package de.aschmidt.vehiclemanagement.model.fahrzeug;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Fahrzeug {

    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    private String marke;
    public String getMarke() {return marke;}
    public void setMarke(String marke) {this.marke = marke;}

    private String fahrzeugTyp;
    public String getFahrzeugTyp() {return fahrzeugTyp;}
    public void setFahrzeugTyp(String fahrzeugTyp) {this.fahrzeugTyp = fahrzeugTyp;}

    private FahrzeugStatus fahrzeugStatus = FahrzeugStatus.UNBEKANNT;
    public FahrzeugStatus getFahrzeugStatus() {return fahrzeugStatus;}
    public String getFahrzeugStatusString() {
        return fahrzeugStatus.toString();
    }
    public void setFahrzeugStatus(FahrzeugStatus fahrzeugStatus) {this.fahrzeugStatus = fahrzeugStatus;}

    private String kennzeichen;
    public String getKennzeichen() {return kennzeichen;}
    public void setKennzeichen(String kennzeichen) {this.kennzeichen = kennzeichen;}

    private int kmstand;
    public int getKmstand() {
        return kmstand;
    }
    public void setKmstand(int kmstand) {
        this.kmstand = kmstand;
    }

    double geschwindigkeit;
    boolean motorIstAn;

    private String fahrer;
    public String getFahrer() {
        return fahrer;
    }
    public void setFahrer(String fahrer) {
        this.fahrer = fahrer;
    }

    private LocalDateTime uebergabezeit;
    public LocalDateTime getUebergabezeit() {
        return uebergabezeit;
    }
    public LocalDate getUebergabedatum() {
        LocalDate ld = LocalDate.of(uebergabezeit.getYear(), uebergabezeit.getMonth(), uebergabezeit.getDayOfMonth());
        return ld;
    }
    public LocalTime getUebergabeuhrzeit() {
        LocalTime ld = LocalTime.of(uebergabezeit.getHour(), uebergabezeit.getMinute());
        return ld;
    }
    public void setUebergabezeit(LocalDateTime uebergabezeit) {
        this.uebergabezeit = uebergabezeit;
    }


    private LocalDateTime rueckgabezeit;
    public LocalDateTime getRueckgabezeit() {
        return rueckgabezeit;
    }
    public void setRueckgabezeit(LocalDateTime rueckgabezeit) {
        this.rueckgabezeit = rueckgabezeit;
    }

    private LocalDate letztewartung;
    public LocalDate getLetztewartung() {
        return letztewartung;
    }
    public void setLetztewartung(LocalDate letztewartung) {
        this.letztewartung = letztewartung;
    }

    private int letztewartungkm;
    public int getLetztewartungkm() {
        return letztewartungkm;
    }
    public void setLetztewartungkm(int letztewartungkm) {
        this.letztewartungkm = letztewartungkm;
    }

    private int wartungsinterval;
    public int getWartungsinterval() {
        return wartungsinterval;
    }
    public void setWartungsinterval(int wartungsinterval) {
        this.wartungsinterval = wartungsinterval;
    }

    private LocalDate nextewartung;
    public LocalDate getNextewartung() {
        return nextewartung;
    }
    public void setNextewartung(LocalDate naextewartung) {
        this.nextewartung = naextewartung;
    }


    public Fahrzeug() {
    }

    public Fahrzeug(String marke, String fahrzeugTyp, FahrzeugStatus fahrzeugStatus, String kennzeichen, double geschwindigkeit, String fahrer) {
        this.marke = marke;
        this.fahrzeugTyp = fahrzeugTyp;
        this.fahrzeugStatus = fahrzeugStatus;
        this.kennzeichen = kennzeichen;
        this.geschwindigkeit = geschwindigkeit;
        this.fahrer = fahrer;
    }

    public void gasGeben() {
        if (motorIstAn) {
            geschwindigkeit = geschwindigkeit + 1.0;
        } else {
            System.out.println(marke + ": Motor ist nich an!");
        }
    }

    public void abBremsen() {
        if (motorIstAn) {
            geschwindigkeit = geschwindigkeit - 1.0;
        } else {
            System.out.println(marke + ": Motor ist nich an!");
        }
    }

    public void anFahrerUebergeben(String fahrerNahme) {
        LocalDateTime uebergegebenUm = LocalDateTime.now();
        this.fahrer = fahrer;
        this.uebergabezeit = uebergegebenUm;

    }

    @Override
    public String toString() {
        return "Fahrzeug {" +
                "Marke = " + marke + ", " +
                "Typ = " + fahrzeugTyp + ", " +
                "Kennzeichen = " + kennzeichen + ", " +
                ((motorIstAn) ? "Motorstatus = An, " : "Motorstatus = Aus, ") +
                "Geschwindigkeit = " + geschwindigkeit + ", " +
                "Fahrername = " + fahrer + "" +
                "nexte Wartung am = " + nextewartung + ", "
                + "}";
    }
}