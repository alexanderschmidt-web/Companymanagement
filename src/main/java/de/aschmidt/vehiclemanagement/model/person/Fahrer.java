package de.aschmidt.vehiclemanagement.model.person;

public class Fahrer{

    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    private String anrede;
    public String getAnrede() {return anrede;}
    public void setAnrede(String anrede) {this.anrede = anrede;}

    private String vorname;
    public String getVorname() {
        return vorname;
    }
    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    private String nachname;
    public String getNachname() {
        return nachname;
    }
    public void setNachname(String name) {
        this.nachname = name;
    }
}
