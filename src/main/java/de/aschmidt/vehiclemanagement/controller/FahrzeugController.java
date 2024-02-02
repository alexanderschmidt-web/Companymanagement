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

import java.time.LocalDate;
import java.util.List;

@Controller
public class FahrzeugController {
    private final FahrzeugRepository kfzRepository;
    public FahrzeugController(FahrzeugRepository kfzRepository) {
        this.kfzRepository = kfzRepository;
    }

    @RequestMapping("/kfz/fahrzeugliste")
    public String zeigeFahrzeuge(Model model) {
        var pkws = kfzRepository.findAllPkw(); // alle Fahrzeuge auslesen
        var lkws = kfzRepository.findAllLkw();
        model.addAttribute("pkws", pkws);
        model.addAttribute("lkws", lkws);

        return "kfz/fahrzeugliste.html";
    }

    @RequestMapping(path = "/kfz/saveKfz", method = RequestMethod.POST)
    public String saveKfz (
            @RequestParam String letztewartungdatum,
            @RequestParam int letztewartungkm,
            @RequestParam int wartungsinterval,
            @RequestParam String fahrzeugTyp,
            @RequestParam String marke,
            @RequestParam String kennzeichen,
            @RequestParam String status,
            Model model
    ) {
        //letzte Wartung am..
        String[] datum = letztewartungdatum.split("-");
        LocalDate letzteWart = LocalDate.of(Integer.parseInt(datum[0]), Integer.parseInt(datum[1]), Integer.parseInt(datum[2]));

        if(fahrzeugTyp.equalsIgnoreCase("PKW")) {

            Pkw p = new Pkw();
            p.setFahrzeugTyp(fahrzeugTyp);
            p.setMarke(marke);
            p.setKennzeichen(kennzeichen);
            p.setFahrzeugStatus(FahrzeugStatus.valueOf(status));
            p.setFahrer("");
            p.setLetztewartung(letzteWart);
            p.setLetztewartungkm(letztewartungkm);
            p.setWartungsinterval(wartungsinterval);
            System.out.println(p.toString());
            kfzRepository.storePkw(p);
            model.addAttribute("kfzeintrag", p);
        }
        if(fahrzeugTyp.equalsIgnoreCase("LKW")) {
            Lkw l = new Lkw();
            l.setFahrzeugTyp(fahrzeugTyp);
            l.setMarke(marke);
            l.setKennzeichen(kennzeichen);
            l.setFahrzeugStatus(FahrzeugStatus.valueOf(status));
            l.setFahrer("");
            l.setLetztewartung(letzteWart);
            l.setLetztewartungkm(letztewartungkm);
            l.setWartungsinterval(wartungsinterval);
            kfzRepository.storeLkw(l);

            model.addAttribute("kfzeintrag", l);
        }
        return "kfz/kfzeintrag.html";
    }


    //            ----- KFZ Suche -----
    @RequestMapping("/kfz/suchekfz")
    public String fahrzeugSuchen(Model model) {
        return "kfz/fahrzeugSuchen.html";
    }
    @RequestMapping("/kfz/gefundenkfz")
    public String fahrzeugGef(Model model) {
        return "kfz/fahrzeugSuchen.html";
    }

    @RequestMapping(path = "/kfz/findeKfz", method = RequestMethod.POST)
    public String findeKfz (
            @RequestParam String fahrzeugTyp,
            @RequestParam String marke,
            @RequestParam String kennzeichen,
            @RequestParam String status,
            Model model
    ) {
        List<Fahrzeug> gefunden =  kfzRepository.searchKfz(fahrzeugTyp, marke, kennzeichen, status);
        model.addAttribute("fahrzeugSuchen", gefunden);
        return "kfz/fahrzeugSuchen.html";
    }

    @RequestMapping("/kfz/neukfz")
    public String fahrzeugAnlegen(Model model) {
        return "kfz/neuesFahrzeug.html";
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
