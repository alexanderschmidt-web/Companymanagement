package de.aschmidt.vehiclemanagement.controller;

import de.aschmidt.vehiclemanagement.model.fahrzeug.Fahrzeug;
import de.aschmidt.vehiclemanagement.model.person.Driver;
import de.aschmidt.vehiclemanagement.repositories.FahrzeugRepository;
import de.aschmidt.vehiclemanagement.repositories.UebergabeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import de.aschmidt.vehiclemanagement.repositories.DriverRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Controller
public class UebergabeController {

    @Autowired
    private DriverRepository driverRepository;

    private final UebergabeRepository uebergabeRepository;
    private final FahrzeugRepository fahrzeugRepository;

    public UebergabeController(UebergabeRepository uebergabeRepository, FahrzeugRepository fahrzeugRepository) {
        this.uebergabeRepository = uebergabeRepository;
        this.fahrzeugRepository = fahrzeugRepository;
    }

    private List<Fahrzeug> gefundenKfz;

    @RequestMapping(path = "/saveFahrerToKfz", method = RequestMethod.POST)
    public String saveFahrerToKfz (
            @RequestParam String fahrzeugTyp,
            @RequestParam String marke,
            @RequestParam String kennzeichen,
            @RequestParam String fahrzeugStatus,
            Model model) {

        gefundenKfz =  fahrzeugRepository.searchKfz(fahrzeugTyp, marke, kennzeichen, fahrzeugStatus);
        model.addAttribute("gefundeneFahrzeug", gefundenKfz);
        return "uebernahme.html";
    }


    @RequestMapping(path = "/sucheFahrer", method = RequestMethod.POST)
    public String getFahrer (
            @RequestParam String firstname,
            @RequestParam String lastname,
            @RequestParam String fahrzeugTyp,
            @RequestParam String marke,
            @RequestParam String kennzeichen,
            Model model
    ) {
        Fahrzeug f = new Fahrzeug(); //KFZ aus DB zu Uebergabe
        f.setFahrzeugTyp(fahrzeugTyp);
        f.setMarke(marke);
        f.setKennzeichen(kennzeichen);

        Optional<Driver> driver =  driverRepository.findByFirstnameAndLastname(firstname, lastname);

        if(driver.isEmpty()) {
            model.addAttribute("gefundeneFahrzeug", gefundenKfz);
            model.addAttribute("fahrerNichtGefunden", "Fahrer wurde nicht gefunden");
            return "uebernahme.html";
        } else {
            /* - Fahrzeug und Fahrer bei Uebergabe zur Bestaetigung senden - */
            model.addAttribute("fahrerGefunden", driver.get());
            model.addAttribute("kfzZurUebergabe", f);
            return "uebernahme.html";
        }


    }

    @RequestMapping(path = "/updFahrer", method = RequestMethod.POST)
    public String updateFahrer (
            @RequestParam String vorname,
            @RequestParam String nachname,
            @RequestParam String marke,
            @RequestParam String kennzeichen,
            Model model
    ){

        Fahrzeug k = new Fahrzeug();

        k.setUebergabezeit(LocalDateTime.now());

        k.setMarke(marke);
        k.setKennzeichen(kennzeichen);
        Driver f = new Driver();
        f.setFirstname(vorname);
        f.setLastname(nachname);

        uebergabeRepository.kfzAnFahrerUebergeben(kennzeichen, nachname, k.getUebergabezeit());

        model.addAttribute("fahrzeugUebergegeben", k);
        model.addAttribute("fahrerMitFahrzeug", f);
        return "uebernahme.html";
    }

}
