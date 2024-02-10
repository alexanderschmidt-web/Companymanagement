package de.aschmidt.vehiclemanagement.controller;


import de.aschmidt.vehiclemanagement.model.person.Driver;
import de.aschmidt.vehiclemanagement.repositories.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

import java.util.List;

@Controller
public class FahrerController {

    @Autowired
    private DriverRepository driverRepository;

    @RequestMapping("/fahrer/fahrerliste")
    public String zeigeFahrzeuge(Model model) {
        //var fahrer = fahrerRepository.findAllFahrer(); // alle Fahrer auslesen
        model.addAttribute("fahrer", driverRepository.findAll());
        return "fahrer/fahrerliste.html";
    }

    // -----------   Fahrer anlegen   --------------
    @RequestMapping("/neueFahrer")
    public String fahrergAnlegen(Model model) {
        return "neueFahrer.html";
    }
    @RequestMapping(path = "/saveFahrer", method = RequestMethod.POST)
    public String saveFahrer (
            @RequestParam String formofaddress,
            @RequestParam String firstname,
            @RequestParam String lastname,
            Model model
    ) {

        Driver f = new Driver();
        f.setFormofaddress(formofaddress);
        f.setFirstname(firstname);
        f.setLastname(lastname);
        //fahrerRepository.storeFahrer(f);
        driverRepository.save(f);
        model.addAttribute("newDriver", f);
        return "fahrer/neueFahrer.html";
    }

    @RequestMapping("/neuefahrer")
    public String fahrzeugSuchen(Model model) {
        return "fahrer/neueFahrer.html";
    }


    @RequestMapping(path = "/fahrer/editFahrer", method = RequestMethod.POST)
    public String editFahrer (
            @RequestParam long id,
            Model model
    ) {
        Optional<Driver> opt = driverRepository.findById(id);
        if(opt.isPresent()) {
            model.addAttribute("fahrer", opt.get());
            return "fahrer/editfahrer.html";
        } else {
            model.addAttribute("driverFirstName", opt.get().getFirstname());
            model.addAttribute("driverLastName", opt.get().getLastname());
            model.addAttribute("fahrer", driverRepository.findAll());
            return "fahrer/fahrerliste.html";
        }

    }


    @RequestMapping(path = "/delfahrer", method = RequestMethod.POST)
    public String delKfz (
            @RequestParam long id,
            Model model
    ) {
        Optional<Driver> opt = driverRepository.findById(id);
        if(opt.isPresent()) {
            driverRepository.delete(opt.get());
            model.addAttribute("fahrer", driverRepository.findAll());
            return "fahrer/fahrerliste.html";
        } else {
            model.addAttribute("fahrer", opt.get());
            model.addAttribute("faultId", opt.get().getId());
            return "fahrer/editfahrer.html";
        }


    }

}
