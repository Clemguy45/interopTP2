package controller;

import authent.modele.Joueur;
import authent.modele.JoueurInexistantException;
import authent.modele.PseudoDejaPrisException;
import org.springframework.cglib.core.internal.Function;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.*;
import service.FacadeJoueur;

@org.springframework.stereotype.Controller
@RequestMapping("/joueur")
public class Controller {
    private final FacadeJoueur facadeJoueur;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;
    private final Function<Joueur, String> genererTokenFunction;

    public Controller(FacadeJoueur facadeJoueur, PasswordEncoder passwordEncoder,
                      JwtEncoder jwtEncoder, Function<Joueur, String> genererTokenFunction) {
        this.facadeJoueur = facadeJoueur;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
        this.genererTokenFunction = genererTokenFunction;
    }

    @PostMapping("/inscription")
    public ResponseEntity<String> inscription(@RequestParam String nomJoueur, @RequestParam String mdpJoueur) {
        try {
            // Encoder le mot de passe avant de l'enregistrer
            String mdpEncode = passwordEncoder.encode(mdpJoueur);
            facadeJoueur.inscription(nomJoueur, mdpEncode);
            return ResponseEntity.ok("Inscription réussie");
        } catch (pileouface.modele.PseudoDejaPrisException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Pseudo déjà pris");
        }
    }

    @PostMapping("/connexion")
    public ResponseEntity<String> connexion(@RequestParam String nomJoueur, @RequestParam String mdpJoueur) {
        try {
            // Récupérer le joueur par son nom
            Joueur joueur = facadeJoueur.getJoueurByPseudo(nomJoueur);

            // Vérifier si le mot de passe fourni correspond au mot de passe enregistré
            if (joueur.checkPassword(mdpJoueur)) {
                // Générer et retourner un jeton JWT
                String jeton = genererJeton(joueur);
                return ResponseEntity.ok(jeton);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mot de passe incorrect");
            }
        } catch (pileouface.modele.JoueurInexistantException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Utilisateur inexistant");
        }
    }

    @GetMapping("/profil/{nomJoueur}")
    public ResponseEntity<Joueur> obtenirProfil(@PathVariable String nomJoueur) {
        try {
            Joueur joueur = facadeJoueur.getJoueurByPseudo(nomJoueur);
            return ResponseEntity.ok(joueur);
        } catch (pileouface.modele.JoueurInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    private String genererJeton(Joueur joueur) {
        // Utiliser votre méthode existante pour générer un jeton JWT
        return genererTokenFunction.apply(joueur);
    }
}
