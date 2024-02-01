package de.aschmidt.vehiclemanagement.controller;


import de.aschmidt.vehiclemanagement.model.fahrzeug.Fahrzeug;
import de.aschmidt.vehiclemanagement.model.fahrzeug.FahrzeugStatus;
import de.aschmidt.vehiclemanagement.model.fahrzeug.Pkw;
import de.aschmidt.vehiclemanagement.repositories.FahrzeugRepository;
import de.aschmidt.vehiclemanagement.model.fahrzeug.Lkw;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FahrzeugDBController {
    private final FahrzeugRepository kfzRepository;
    public FahrzeugDBController(FahrzeugRepository kfzRepository) {
        this.kfzRepository = kfzRepository;
    }

    @RequestMapping(path = "/saveKfz", method = RequestMethod.POST)
    public String saveKfz (
            @RequestParam String fahrzeugTyp,
            @RequestParam String marke,
            @RequestParam String kennzeichen,
            Model model
    ) {
        if(fahrzeugTyp.equalsIgnoreCase("PKW")) {
            Pkw p = new Pkw();
            p.setFahrzeugTyp(fahrzeugTyp);
            p.setMarke(marke);
            p.setKennzeichen(kennzeichen);
            p.setFahrzeugStatus(FahrzeugStatus.UNBEKANNT);
            p.setFahrer("");
            kfzRepository.storePkw(p);
            model.addAttribute("dbeintrag", p);
        }
        if(fahrzeugTyp.equalsIgnoreCase("LKW")) {
            Lkw l = new Lkw();
            l.setFahrzeugTyp(fahrzeugTyp);
            l.setMarke(marke);
            l.setKennzeichen(kennzeichen);
            l.setFahrzeugStatus(FahrzeugStatus.UNBEKANNT);
            l.setFahrer("");
            kfzRepository.storeLkw(l);

            model.addAttribute("dbeintrag", l);
        }

        return "dbeintrag.html";
    }

    @RequestMapping(path = "/findeKfz", method = RequestMethod.POST)
    public String findeKfz (
            @RequestParam String fahrzeugTyp,
            @RequestParam String marke,
            @RequestParam String kennzeichen,
            @RequestParam String status,
            Model model
    ) {
        List<Fahrzeug> gefunden =  kfzRepository.searchKfz(fahrzeugTyp, marke, kennzeichen, status);
        model.addAttribute("fahrzeugSuchen", gefunden);
        return "fahrzeugSuchen.html";
    }

    @RequestMapping(path = "/kfz/editKfz", method = RequestMethod.POST)
    public String editKfz (
            @RequestParam String fahrzeugTyp,
            //@RequestParam String marke,
            //@RequestParam String kennzeichen,
            @RequestParam int id,
            Model model
    ) {
        List<Fahrzeug> gefunden =  kfzRepository.searchKfzById(fahrzeugTyp, id);
        model.addAttribute("kfz", gefunden);
        return "kfz/editkfz.html";
    }

    @RequestMapping(path = "/delkfz", method = RequestMethod.POST)
    public String delKfz (
            @RequestParam String fahrzeugTyp,
            @RequestParam int id,
            Model model
    ) {
        kfzRepository.delById(fahrzeugTyp, id);

        var pkws = kfzRepository.findAllPkw(); // alle Fahrzeuge auslesen
        var lkws = kfzRepository.findAllLkw();
        model.addAttribute("pkws", pkws);
        model.addAttribute("lkws", lkws);
        return "kfz/fahrzeugliste.html";
    }

}
