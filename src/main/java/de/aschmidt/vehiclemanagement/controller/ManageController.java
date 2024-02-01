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

  @GetMapping("/")
  public String startseite(Model model) {
    return "index.html";
  }

}




