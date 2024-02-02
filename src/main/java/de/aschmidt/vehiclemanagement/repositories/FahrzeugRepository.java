package de.aschmidt.vehiclemanagement.repositories;

import de.aschmidt.vehiclemanagement.model.fahrzeug.Fahrzeug;
import de.aschmidt.vehiclemanagement.model.fahrzeug.FahrzeugStatus;
import de.aschmidt.vehiclemanagement.model.fahrzeug.Lkw;
import de.aschmidt.vehiclemanagement.model.fahrzeug.Pkw;
import de.aschmidt.vehiclemanagement.repositories.exteptions.ExeptionSaveToDB;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;

@Repository
public class FahrzeugRepository {
    private final JdbcTemplate jdbc;
    public FahrzeugRepository(
            JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void storePkw(Pkw pkw) {
        try {
            String sql =
                    "INSERT INTO pkws VALUES (NULL, ?, ?, ?, ?, ?, NULL, NULL, ?, ?, ?, ?)";

            //pkw.setUebergabezeit(LocalDateTime.now());
            LocalDate nexteWartung = pkw.getLetztewartung().plusYears(pkw.getWartungsinterval());
            pkw.setNextewartung(nexteWartung);

            jdbc.update(
                    sql,
                    pkw.getMarke(),
                    pkw.getKennzeichen(),
                    pkw.getKmstand(),
                    pkw.getFahrzeugStatusString(),
                    pkw.getFahrer(),
                    pkw.getLetztewartung(),
                    pkw.getLetztewartungkm(),
                    pkw.getWartungsinterval(),
                    pkw.getNextewartung()
            );
        } catch (DataAccessException e) {
            throw new ExeptionSaveToDB("Daten konnten nicht eingetragen werden! INSERT Befehl nicht durchgekommen");
        }
    }
    public void storeLkw(Lkw lkw) {
        try {
            String sql =
                    "INSERT INTO lkws VALUES (NULL, ?, ?, ?, ?, ?, NULL, NULL, ?, ?, ?, ?)";

            //lkw.setUebergabezeit(LocalDateTime.now());
            LocalDate nexteWartung = lkw.getLetztewartung().plusYears(lkw.getWartungsinterval());
            lkw.setNextewartung(nexteWartung);

            jdbc.update(
                    sql,
                    lkw.getMarke(),
                    lkw.getKennzeichen(),
                    lkw.getKmstand(),
                    lkw.getFahrzeugStatusString(),
                    lkw.getFahrer(),
                    lkw.getLetztewartung(),
                    lkw.getLetztewartungkm(),
                    lkw.getWartungsinterval(),
                    lkw.getNextewartung()
            );
        } catch (DataAccessException e) {
            throw new ExeptionSaveToDB("Daten konnten nicht eingetragen werden! INSERT Befehl nicht durchgekommen");
        }
    }

    public List<Fahrzeug> findAllPkw() {
        try {
            String sql = "SELECT * FROM pkws";

            RowMapper<Fahrzeug> pkwRowMapper = (r, i) -> {
                Fahrzeug rowObject = new Fahrzeug();
                rowObject.setId(r.getInt("id"));
                rowObject.setMarke(r.getString("marke"));
                rowObject.setKennzeichen(r.getString("kennzeichen"));
                rowObject.setKmstand(r.getInt("kmstand"));
                rowObject.setFahrzeugStatus(FahrzeugStatus.valueOf(r.getString("status")));
                rowObject.setFahrer(r.getString("fahrer"));

                LocalDateTime dateTimeFromDB = r.getObject("uebergabezeit", LocalDateTime.class);
                rowObject.setUebergabezeit(dateTimeFromDB);

                LocalDateTime rueckgabezeitFromDB = r.getObject("rueckgabezeit", LocalDateTime.class);
                rowObject.setRueckgabezeit(rueckgabezeitFromDB);

                LocalDate nextewartungFromDB = r.getObject("nextewartung", LocalDate.class);
                rowObject.setNextewartung(nextewartungFromDB);

                System.out.println(rowObject.toString());
                return rowObject;
            };
            return jdbc.query(sql, pkwRowMapper);

        } catch (DataAccessException e) {
            throw new ExeptionSaveToDB("Abfrage zum Dataenbank ist gescheitert! SELECT Befehl nicht durchgekommen");
        }


    }
    public List<Fahrzeug> findAllLkw() {
        try {
            String sql = "SELECT * FROM lkws";
            RowMapper<Fahrzeug> lkwRowMapper = (r, i) -> {
                Fahrzeug rowObject = new Fahrzeug();
                rowObject.setId(r.getInt("id"));
                rowObject.setMarke(r.getString("marke"));
                rowObject.setKennzeichen(r.getString("kennzeichen"));
                rowObject.setKmstand(r.getInt("kmstand"));
                rowObject.setFahrzeugStatus(FahrzeugStatus.valueOf(r.getString("status")));
                rowObject.setFahrer(r.getString("fahrer"));

                LocalDateTime uebergabezeitFromDB = r.getObject("uebergabezeit", LocalDateTime.class);
                rowObject.setUebergabezeit(uebergabezeitFromDB);

                LocalDateTime rueckgabezeitFromDB = r.getObject("rueckgabezeit", LocalDateTime.class);
                rowObject.setRueckgabezeit(rueckgabezeitFromDB);

                LocalDate nextewartungFromDB = r.getObject("nextewartung", LocalDate.class);
                rowObject.setNextewartung(nextewartungFromDB);

                return rowObject;
            };
            return jdbc.query(sql, lkwRowMapper);

        } catch (DataAccessException e) {
            throw new ExeptionSaveToDB("Abfrage zum Dataenbank ist gescheitert! SELECT Befehl nicht durchgekommen");
        }
    }

    public List<Fahrzeug> searchKfz(String fahrzeugTyp, String marke, String kennzeichen, String status) {
        String sql = "";
        boolean emptyMK = marke.isEmpty() & kennzeichen.isEmpty();
        boolean emptyM = marke.isEmpty() & !kennzeichen.isEmpty();
        boolean emptyK = !marke.isEmpty() & kennzeichen.isEmpty();
        boolean exisMK = !marke.isEmpty() & !kennzeichen.isEmpty();

        try {
            if (fahrzeugTyp.equalsIgnoreCase("PKW")) {
                if (status.isEmpty()) {
                    if (emptyMK)
                        sql = "SELECT * FROM pkws";
                    if (emptyK)
                        sql = "SELECT * FROM pkws WHERE marke = '" + marke + "'";
                    if (emptyM)
                        sql = "SELECT * FROM pkws WHERE kennzeichen = '" + kennzeichen + "'";
                    if (exisMK)
                        sql = "SELECT * FROM pkws WHERE marke = '" + marke + "' AND kennzeichen = '" + kennzeichen + "'";
                } else {
                    if (emptyMK)
                        sql = "SELECT * FROM pkws WHERE status = '" + status + "'";
                    if (emptyK)
                        sql = "SELECT * FROM pkws WHERE marke = '" + marke + "' AND  status = '" + status + "'";
                    if (emptyM)
                        sql = "SELECT * FROM pkws WHERE kennzeichen = '" + kennzeichen + "' AND  status = '" + status + "'";
                    if (exisMK)
                        sql = "SELECT * FROM pkws WHERE marke = '" + marke + "' AND kennzeichen = '" + kennzeichen + "' AND  status = '" + status + "'";
                }
            }

            if (fahrzeugTyp.equalsIgnoreCase("LKW")) {
                if (status.isEmpty()) {
                    if (emptyMK)
                        sql = "SELECT * FROM lkws";
                    if (emptyK)
                        sql = "SELECT * FROM lkws WHERE marke = '" + marke + "'";
                    if (emptyM)
                        sql = "SELECT * FROM lkws WHERE kennzeichen = '" + kennzeichen + "'";
                    if (exisMK)
                        sql = "SELECT * FROM lkws WHERE marke = '" + marke + "' AND kennzeichen = '" + kennzeichen + "'";
                } else {
                    if (emptyMK)
                        sql = "SELECT * FROM lkws WHERE status = '" + status + "'";
                    if (emptyK)
                        sql = "SELECT * FROM lkws WHERE marke = '" + marke + "' AND  status = '" + status + "'";
                    if (emptyM)
                        sql = "SELECT * FROM lkws WHERE kennzeichen = '" + kennzeichen + "' AND  status = '" + status + "'";
                    if (exisMK)
                        sql = "SELECT * FROM lkws WHERE marke = '" + marke + "' AND kennzeichen = '" + kennzeichen + "' AND  status = '" + status + "'";
                }
            }

            RowMapper<Fahrzeug> pkwRowMapper = (r, i) -> {
                Fahrzeug rowObject = new Fahrzeug();
                rowObject.setId(r.getInt("id"));
                rowObject.setFahrzeugTyp(fahrzeugTyp);
                rowObject.setMarke(r.getString("marke"));
                rowObject.setKennzeichen(r.getString("kennzeichen"));
                rowObject.setFahrzeugStatus(FahrzeugStatus.valueOf(r.getString("status")));
                return rowObject;
            };
            return jdbc.query(sql, pkwRowMapper);

        } catch (DataAccessException e) {
            throw new ExeptionSaveToDB("KFZ-Suchanfrage zum Dataenbank ist gescheitert! SELECT Befehl nicht durchgekommen");
        }
    }

    public List<Fahrzeug> searchKfzById(String fahrzeugTyp, int id) {
        try {
            String tabName = "";
            if(fahrzeugTyp.equalsIgnoreCase("LKW")) tabName = "lkws";
            if(fahrzeugTyp.equalsIgnoreCase("PKW")) tabName = "pkws";
            String sql = "SELECT * FROM " + tabName + " WHERE id = '" + id + "'";

            RowMapper<Fahrzeug> kfzRowMapper = (r, i) -> {
                Fahrzeug rowObject = new Fahrzeug();
                rowObject.setFahrzeugTyp(fahrzeugTyp);
                rowObject.setId(r.getInt("id"));
                rowObject.setMarke(r.getString("marke"));
                rowObject.setKennzeichen(r.getString("kennzeichen"));
                rowObject.setFahrzeugStatus(FahrzeugStatus.valueOf(r.getString("status")));
                rowObject.setFahrer(r.getString("fahrer"));

                LocalDateTime uebergabezeitFromDB = r.getObject("uebergabezeit", LocalDateTime.class);
                rowObject.setUebergabezeit(uebergabezeitFromDB);

                LocalDateTime rueckgabezeitFromDB = r.getObject("rueckgabezeit", LocalDateTime.class);
                rowObject.setRueckgabezeit(rueckgabezeitFromDB);

                return rowObject;
            };

            return jdbc.query(sql, kfzRowMapper);

        } catch (DataAccessException e) {
            throw new ExeptionSaveToDB("KFZ-Suchanfrage zum Dataenbank ist gescheitert! SELECT Befehl nicht durchgekommen");
        }
    }

    public  void delById(String fahrzeugTyp, int id) {
        try {
            String tabName = "";
            if(fahrzeugTyp.equalsIgnoreCase("LKW")) tabName = "lkws";
            if(fahrzeugTyp.equalsIgnoreCase("PKW")) tabName = "pkws";
            String sql = "DELETE FROM " + tabName + " WHERE id = '" + id + "'";

            jdbc.execute(sql);


        } catch (DataAccessException e) {
            throw new ExeptionSaveToDB("Daten konnten nicht geloescht werden!");
        }
    }

}
