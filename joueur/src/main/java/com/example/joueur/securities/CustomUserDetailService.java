package com.example.joueur.securities;

import authent.modele.Joueur;
import authent.modele.JoueurInexistantException;
import com.example.joueur.services.FacadeJoueur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private enum ROLES {
        USER,
        ADMIN
    }
    private final FacadeJoueur facadeJoueur;

    public CustomUserDetailService(
            @Autowired FacadeJoueur facadeJoueur) {
        this.facadeJoueur = facadeJoueur;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Joueur joueur;
        try {
            joueur = this.facadeJoueur.getJoueurByPseudo(username);
        } catch (JoueurInexistantException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
        String[] roles = {ROLES.USER.toString()};
        UserDetails userDetails = User.builder()
                .username(joueur.getNomJoueur())
                .roles(roles)
                .build();
        return userDetails;
    }
}
