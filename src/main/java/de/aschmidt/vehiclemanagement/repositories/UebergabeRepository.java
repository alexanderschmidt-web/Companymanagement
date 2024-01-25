package de.aschmidt.vehiclemanagement.repositories;

import de.aschmidt.vehiclemanagement.model.fahrzeug.FahrzeugStatus;
import de.aschmidt.vehiclemanagement.model.person.Fahrer;
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

    public List<Fahrer> searchFahrer(String nachname) {
        String sql = "SELECT * FROM fahrer WHERE nachname = '"+nachname+"'";

        RowMapper<Fahrer> pkwRowMapper = (r, i) -> {
            Fahrer rowObject = new Fahrer();
            rowObject.setId(r.getInt("id"));
            rowObject.setAnrede(r.getString("anrede"));
            rowObject.setVorname(r.getString("vorname"));
            rowObject.setNachname(r.getString("nachname"));
            return rowObject;
        };
        return jdbc.query(sql, pkwRowMapper);
    }

    public int kfzAnFahrerUebergeben(String kennzeichen, String nachname, LocalDateTime uebergabezeit) {

        String sql = "UPDATE pkws SET fahrer = '"+nachname+"', status = '"+FahrzeugStatus.VERLIEHEN+"', uebergabezeit = '"+uebergabezeit+"' WHERE kennzeichen = '"+kennzeichen+"'";
        return jdbc.update(sql);
    }

}
