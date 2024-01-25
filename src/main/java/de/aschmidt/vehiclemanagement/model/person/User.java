package de.aschmidt.vehiclemanagement.model.person;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
//@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
//@Table(name = "user", table = No)
public class User implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    @Column(length = 50, unique = true)
    private String email;

    @Column(length = 100)
    private String password;

    @Column(length = 50)
    private String anrede;

    @Column(length = 50)
    private String vorname;

    @Column(length = 100)
    private String nachname;

    @Column(length = 10)
    private String postalcode;

    @Column(length = 50)
    private String placename;

    @Column(length = 100)
    private String street;

    @Column(length = 5)
    private String housenumber;

    @Column(length = 3)
    private String housenumberadition;

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    @Column
    private LocalDate birthdate;

    @Column
    private LocalDate createdAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnrede() {
        return anrede;
    }

    public void setAnrede(String anrede) {
        this.anrede = anrede;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
