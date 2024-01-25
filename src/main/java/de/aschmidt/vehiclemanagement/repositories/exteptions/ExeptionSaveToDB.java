package de.aschmidt.vehiclemanagement.repositories.exteptions;

public class ExeptionSaveToDB extends RuntimeException {

    public ExeptionSaveToDB(String message) {
        super(message);
    }

    public ExeptionSaveToDB() {
    }

}
