package de.aschmidt.vehiclemanagement.service;
/**
 * Zur Anwendung im Webbrowser werden folgende Funktionen gegeben:
 *  - Anlegen und entfernen Firmenfahrzeuge
 *  - Suchfunktion anhand Kennzeichen um Information bestimmten Autos zu finden
 *  - Protokollfunktion für Übergabe an den Fahrer
 *  - Aktuelle Status zu Wartung/Reparatur/Fahrzeuguntersuchung
 */

import de.aschmidt.vehiclemanagement.model.fahrzeug.Fahrzeug;
import de.aschmidt.vehiclemanagement.model.fahrzeug.FahrzeugStatus;
import de.aschmidt.vehiclemanagement.model.fahrzeug.Lkw;
import de.aschmidt.vehiclemanagement.model.fahrzeug.Pkw;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FahrzeugManage {

    public Fahrzeug suchErgebnis; //gesuchte Fahrzeug anhand der Kennzeichen

    public List<Fahrzeug> fahrzeuge = new ArrayList<>(); //Liste aller Fahrzeugen erstellen

    public List<Fahrzeug> getFahrzeuge() {
        return fahrzeuge;
    }

    public List<Fahrzeug> findAll() {
        return fahrzeuge;
    }  //Liste aller Fahrzeugen ausgeben


    /* - neues Fahrzeug anlegen - */
    public void neuAnlegen(String typ, String marke, String kennz) {
        if(typ.equalsIgnoreCase("p")) {
            fahrzeuge.add(new Pkw(marke, FahrzeugStatus.UNBEKANNT, kennz, 0.0, ""));
        }
        if(typ.equalsIgnoreCase("l")) {
            fahrzeuge.add(new Lkw(marke, FahrzeugStatus.UNBEKANNT, kennz, 0.0, ""));}
    }

    /* - Fahrzeug in der Liste suchen - */
    public void fahrzeugAusgeben(String kennz) {
        for(Fahrzeug s: fahrzeuge)
            if(s.getKennzeichen().equalsIgnoreCase(kennz)) {
                suchErgebnis = s;
            }
    }

}
