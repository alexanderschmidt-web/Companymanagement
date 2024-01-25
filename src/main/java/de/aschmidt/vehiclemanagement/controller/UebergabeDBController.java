package de.aschmidt.vehiclemanagement.controller;

import de.aschmidt.vehiclemanagement.model.fahrzeug.Fahrzeug;
import de.aschmidt.vehiclemanagement.model.person.Fahrer;
import de.aschmidt.vehiclemanagement.repositories.FahrzeugRepository;
import de.aschmidt.vehiclemanagement.repositories.UebergabeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;


@Controller
public class UebergabeDBController {

    private final UebergabeRepository uebergabeRepository;
    private final FahrzeugRepository fahrzeugRepository;

    public UebergabeDBController(UebergabeRepository uebergabeRepository, FahrzeugRepository fahrzeugRepository) {
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
            @RequestParam String nachname,
            @RequestParam String fahrzeugTyp,
            @RequestParam String marke,
            @RequestParam String kennzeichen,
            Model model
    ) {
        Fahrzeug f = new Fahrzeug(); //KFZ aus DB zu Uebergabe
        f.setFahrzeugTyp(fahrzeugTyp);
        f.setMarke(marke);
        f.setKennzeichen(kennzeichen);

        List<Fahrer> fahrerGefunden =  uebergabeRepository.searchFahrer(nachname);
        if(fahrerGefunden.isEmpty()) {
            model.addAttribute("gefundeneFahrzeug", gefundenKfz);
            model.addAttribute("fahrerNichtGefunden", "Fahrer wurde nicht gefunden");
            return "uebernahme.html";
        } else {
            /* - Fahrzeug und Fahrer bei Uebergabe zur Bestaetigung senden - */
            model.addAttribute("fahrerGefunden", fahrerGefunden);
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
        Fahrer f = new Fahrer();
        f.setVorname(vorname);
        f.setNachname(nachname);

        uebergabeRepository.kfzAnFahrerUebergeben(kennzeichen, nachname, k.getUebergabezeit());

        model.addAttribute("fahrzeugUebergegeben", k);
        model.addAttribute("fahrerMitFahrzeug", f);
        return "uebernahme.html";
    }

}
