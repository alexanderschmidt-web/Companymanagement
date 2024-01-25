package de.aschmidt.vehiclemanagement.controller;


import de.aschmidt.vehiclemanagement.model.person.User;
import de.aschmidt.vehiclemanagement.model.person.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    User us = new User();

    @PostMapping("/saveuser")
    public String saveFahrer (@RequestParam User user, Model model) {
        us.setEmail("@");
        us.setPassword("pw");
        us.setAnrede("Herr");
        us.setVorname("Al");
        us.setNachname("Schm");
        userRepository.save(us);
        return "fahrzeugliste";
    }

}
