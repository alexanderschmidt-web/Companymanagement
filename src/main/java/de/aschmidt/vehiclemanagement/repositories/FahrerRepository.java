package de.aschmidt.vehiclemanagement.repositories;

import de.aschmidt.vehiclemanagement.model.person.Fahrer;
import de.aschmidt.vehiclemanagement.repositories.exteptions.ExeptionSaveToDB;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class FahrerRepository {
    private final JdbcTemplate jdbc;
    public FahrerRepository(
            JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void storeFahrer(Fahrer fahrer) {
        try {
            String sql = "INSERT INTO fahrer VALUES (NULL, ?, ?, ?)";
            jdbc.update(sql, fahrer.getAnrede(), fahrer.getVorname(), fahrer.getNachname());
        } catch (DataAccessException e) {
            throw new ExeptionSaveToDB("Daten konnten nicht eingetragen werden! INSERT Befehl nicht durchgekommen");
        }

    }

    public List<Fahrer> findAllFahrer() {
        try {
            String sql = "SELECT * FROM fahrer";
            RowMapper<Fahrer> pkwRowMapper = (r, i) -> {
                Fahrer rowObject = new Fahrer();
                rowObject.setId(r.getInt("id"));
                rowObject.setAnrede(r.getString("anrede"));
                rowObject.setVorname(r.getString("vorname"));
                rowObject.setNachname(r.getString("nachname"));
                return rowObject;
            };
            return jdbc.query(sql, pkwRowMapper);
        } catch (DataAccessException e) {
            throw new ExeptionSaveToDB("Abfrage zum Dataenbank ist gescheitert! SELECT Befehl nicht durchgekommen");
        }
    }

    public List<Fahrer> searchFahrer(String vorname, String nachname) {
        String sql = "";

        try {
            if (!vorname.isEmpty() & nachname.isEmpty()) {
                sql = "SELECT * FROM fahrer WHERE nachname = '" + nachname + "'";
            }
            if (vorname.isEmpty() & !nachname.isEmpty()) {
                sql = "SELECT * FROM fahrer WHERE vorname = '" + vorname + "'";
            }
            if (!vorname.isEmpty() & !nachname.isEmpty()) {
                sql = "SELECT * FROM fahrer WHERE vorname = '" + vorname + "' AND nachname = '" + nachname + "'";
            }

            RowMapper<Fahrer> pkwRowMapper = (r, i) -> {
                Fahrer rowObject = new Fahrer();
                rowObject.setId(r.getInt("id"));
                rowObject.setVorname(r.getString("vorname"));
                rowObject.setNachname(r.getString("nachname"));
                return rowObject;
            };
            return jdbc.query(sql, pkwRowMapper);
        } catch (DataAccessException e) {
            throw new ExeptionSaveToDB("Abfrage zum Dataenbank ist gescheitert! SELECT Befehl nicht durchgekommen");
        }
    }

}
