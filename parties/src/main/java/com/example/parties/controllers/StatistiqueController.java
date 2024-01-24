package com.example.parties.controllers;

import com.example.parties.services.FacadeParties;
import com.example.parties.services.FacadePartiesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pileouface.modele.MauvaisIdentifiantConnexionException;
import pileouface.modele.Statistiques;

import java.security.Principal;

@Controller
@RequestMapping("/statistiques")
public class StatistiqueController {
    private final FacadeParties facadeParties;

    public StatistiqueController(@Autowired FacadePartiesImpl facadeParties) {
        this.facadeParties = facadeParties;
    }

    @GetMapping
    public ResponseEntity<Statistiques> getStatistique(@Autowired Principal principal) {
        try {
            Statistiques statistiques = this.facadeParties.getStatistiques(principal.getName());
            return ResponseEntity.ok().body(statistiques);
        } catch (MauvaisIdentifiantConnexionException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

    }
}
