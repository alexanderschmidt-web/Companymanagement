package de.aschmidt.vehiclemanagement.repositories;

import de.aschmidt.vehiclemanagement.model.fahrzeug.FahrzeugStatus;
import de.aschmidt.vehiclemanagement.model.person.Driver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class UebergabeRepository {
    private final JdbcTemplate jdbc;
    public UebergabeRepository(
            JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public int kfzAnFahrerUebergeben(String kennzeichen, String lastname, LocalDateTime uebergabezeit) {

        String sql = "UPDATE pkws SET fahrer = '"+lastname+"', status = '"+FahrzeugStatus.VERLIEHEN+"', uebergabezeit = '"+uebergabezeit+"' WHERE kennzeichen = '"+kennzeichen+"'";
        return jdbc.update(sql);
    }

}
