package com.example.parties.controllers;

import com.example.parties.services.FacadeParties;
import com.example.parties.services.FacadePartiesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pileouface.modele.Joueur;

import java.security.Principal;

@Controller
@RequestMapping("/joueurs")
public class JoueurController {
    private final FacadeParties facadeParties;

    public JoueurController(@Autowired FacadePartiesImpl facadeParties) {
        this.facadeParties = facadeParties;
    }

    @GetMapping
    public ResponseEntity<Joueur> getJoueur(@Autowired Principal principal) {
        Joueur joueur = this.facadeParties.getJoueur(principal.getName());
        return ResponseEntity.ok().body(joueur);
    }

    @DeleteMapping
    public ResponseEntity supprimerJoueur(@Autowired Principal principal) {
        this.facadeParties.suppressionJoueur(principal.getName());
        return ResponseEntity.noContent().build();
    }
}
