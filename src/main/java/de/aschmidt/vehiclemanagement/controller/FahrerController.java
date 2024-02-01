package de.aschmidt.vehiclemanagement.controller;


import de.aschmidt.vehiclemanagement.model.person.Fahrer;
import de.aschmidt.vehiclemanagement.repositories.FahrerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class FahrerController {
    private final FahrerRepository fahrerRepository;
    public FahrerController(FahrerRepository kfzRepository) {
        this.fahrerRepository = kfzRepository;
    }

    @RequestMapping("/fahrer/fahrerliste")
    public String zeigeFahrzeuge(Model model) {
        var fahrer = fahrerRepository.findAllFahrer(); // alle Fahrzeuge auslesen
        model.addAttribute("fahrer", fahrer);
        return "fahrer/fahrerliste.html";
    }

    // -----------   Fahrer anlegen   --------------
    @RequestMapping("/neueFahrer")
    public String fahrergAnlegen(Model model) {
        return "neueFahrer.html";
    }
    @RequestMapping(path = "/saveFahrer", method = RequestMethod.POST)
    public String saveFahrer (
            @RequestParam String anrede,
            @RequestParam String vorname,
            @RequestParam String nachname,
            Model model
    ) {

        Fahrer f = new Fahrer();
        f.setAnrede(anrede);
        f.setVorname(vorname);
        f.setNachname(nachname);
        fahrerRepository.storeFahrer(f);
        model.addAttribute("neueFahrer", f);
        return "fahrer/neueFahrer.html";
    }

    @RequestMapping(path = "/findeFahrer", method = RequestMethod.POST)
    public String findeFahrer (
            @RequestParam String vorname,
            @RequestParam String nachname,
            Model model
    ) {
        List<Fahrer> gefunden =  fahrerRepository.searchFahrer(vorname, nachname);
        model.addAttribute("fahrerSuchen", gefunden);
        return "kfz/fahrzeugSuchen.html";
    }

    @RequestMapping("/neuefahrer")
    public String fahrzeugSuchen(Model model) {
        return "fahrer/neueFahrer.html";
    }


    @RequestMapping(path = "/fahrer/editFahrer", method = RequestMethod.POST)
    public String editFahrer (
            @RequestParam int id,
            Model model
    ) {
        List<Fahrer> gefunden =  fahrerRepository.searchFahrerById(id);
        model.addAttribute("fahrer", gefunden);
        return "fahrer/editfahrer.html";
    }


    @RequestMapping(path = "/delfahrer", method = RequestMethod.POST)
    public String delKfz (
            @RequestParam int id,
            Model model
    ) {
        fahrerRepository.delFahrerById(id);

        var fahrer = fahrerRepository.findAllFahrer(); // alle Fahrzeuge auslesen
        model.addAttribute("fahrer", fahrer);
        return "fahrer/fahrerliste.html";
    }

}
