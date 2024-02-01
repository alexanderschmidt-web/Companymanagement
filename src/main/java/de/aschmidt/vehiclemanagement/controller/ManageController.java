package de.aschmidt.vehiclemanagement.controller;

import de.aschmidt.vehiclemanagement.repositories.FahrzeugRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ManageController {
  private final FahrzeugRepository kfzRepository;
  public ManageController(FahrzeugRepository pkwRepository) {
    this.kfzRepository = pkwRepository;
  }

  /*
  @RequestMapping("/")
  public ResponseEntity getConnection() {
      try {
          return ResponseEntity.ok("Server ist im Betrieb");
      } catch (Exception e) {
          return ResponseEntity.badRequest().body("Es ist ein Fehler aufgetretten!");
      }
  }
  */
  @RequestMapping("/neukfz")
  public String fahrzeugAnlegen(Model model) {
    return "neuesFahrzeug.html";
  }

  @GetMapping("/")
  public String startseite(Model model) {
    return "index.html";
  }

  @RequestMapping("/kfz/fahrzeugliste")
  public String zeigeFahrzeuge(Model model) {
    var pkws = kfzRepository.findAllPkw(); // alle Fahrzeuge auslesen
    var lkws = kfzRepository.findAllLkw();
    model.addAttribute("pkws", pkws);
    model.addAttribute("lkws", lkws);

    return "kfz/fahrzeugliste.html";
  }


  @RequestMapping("/suchekfz")
  public String fahrzeugSuchen(Model model) {
    return "fahrzeugSuchen.html";
  }
  @RequestMapping("/gefundenkfz")
  public String fahrzeugGef(Model model) {
    return "fahrzeugSuchen.html";
  }

  @RequestMapping("/neueFahrer")
  public String fahrergAnlegen(Model model) {
    return "neueFahrer.html";
  }

}
