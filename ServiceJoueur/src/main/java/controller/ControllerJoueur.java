package controller;

import authent.modele.JoueurInexistantException;
import authent.modele.OperationNonAutorisee;
import authent.modele.PseudoDejaPrisException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.FacadeJoueur;

@Controller
@RequestMapping("/joueur")
public class ControllerJoueur {
    private final FacadeJoueur facadeJoueur;
    private final PasswordEncoder passwordEncoder;

    public ControllerJoueur(FacadeJoueur facadeJoueur, PasswordEncoder passwordEncoder) {
        this.facadeJoueur = facadeJoueur;
        this.passwordEncoder = passwordEncoder;
    }


}
