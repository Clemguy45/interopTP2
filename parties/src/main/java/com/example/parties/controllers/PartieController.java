package com.example.parties.controllers;

import com.example.parties.dtos.JouerDTO;
import com.example.parties.services.FacadeParties;
import com.example.parties.services.FacadePartiesImpl;
import jakarta.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import pileouface.modele.MauvaisIdentifiantConnexionException;
import pileouface.modele.Partie;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/parties")
public class PartieController {
    private final FacadeParties facadeParties;

    public PartieController(@Autowired FacadePartiesImpl facadeParties) {
        this.facadeParties = facadeParties;
    }

    @GetMapping
    public ResponseEntity<Collection<Partie>> getParties(@Autowired Principal principal) {
        try {
            Collection<Partie> parties = this.facadeParties.getAllParties(principal.getName());
            return ResponseEntity.ok().body(parties);
        } catch (MauvaisIdentifiantConnexionException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Partie> jouer(@Autowired Principal principal, @RequestBody JouerDTO jouerDTO) {
        try {
            Partie partie = this.facadeParties.jouer(principal.getName(), jouerDTO.choix());
            return ResponseEntity.ok().body(partie);
        } catch (MauvaisIdentifiantConnexionException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
