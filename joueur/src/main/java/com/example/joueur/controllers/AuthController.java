package com.example.joueur.controllers;

import authent.modele.Joueur;
import authent.modele.JoueurInexistantException;
import authent.modele.PseudoDejaPrisException;
import com.example.joueur.dtos.LoginDTO;
import com.example.joueur.dtos.RegisterDTO;
import com.example.joueur.services.FacadeJoueur;
import com.example.joueur.services.FacadeJoueurImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.function.Function;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final FacadeJoueur facadeJoueur;
    private final PasswordEncoder passwordEncoder;
    private final Function<Joueur, String> genererToken;

    public AuthController(@Autowired FacadeJoueurImpl facadeJoueur, @Autowired PasswordEncoder passwordEncoder, @Autowired Function<Joueur, String> genererToken ) {
        this.facadeJoueur = facadeJoueur;
        this.passwordEncoder = passwordEncoder;
        this.genererToken = genererToken;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDTO) {
        Joueur joueur;
        try {
            joueur = this.facadeJoueur.getJoueurByPseudo(loginDTO.pseudo());
        } catch (JoueurInexistantException e) {
            return ResponseEntity.notFound().build();
        }
        if(passwordEncoder.matches(loginDTO.mdp(), joueur.getMdpJoueur())){
            String token = this.genererToken.apply(joueur);
            return ResponseEntity.ok().header("Authorization", "Bearer " +token).build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDTO registerDTO) {
        Joueur joueur;
        try {
            joueur = this.facadeJoueur.inscription(registerDTO.nouveauJoueur(), this.passwordEncoder.encode(
                    registerDTO.mdp()));
        } catch(PseudoDejaPrisException p) {
            return ResponseEntity.badRequest().build();
        }
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{pseudo}")
                .buildAndExpand(joueur.getNomJoueur()).toUri();
        return ResponseEntity.created(location).build();
    }
}
