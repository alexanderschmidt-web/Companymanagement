package de.aschmidt.vehiclemanagement.model.person;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Driver {

    @Id
    @GeneratedValue
    private long id;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    @Column(length = 50, unique = true)
    private String formofaddress;
    public String getFormofaddress() {return formofaddress;}
    public void setFormofaddress(String formOfAddress) {this.formofaddress = formOfAddress;}

    @Column(length = 50, name="firstname")
    private String firstname;
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstName) {
        this.firstname = firstName;
    }

    @Column(length = 100, name="lastname")
    private String lastname;
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String name) {
        this.lastname = name;
    }
}
