package com.example.joueur.services;

import authent.modele.Joueur;
import authent.modele.JoueurInexistantException;
import authent.modele.OperationNonAutorisee;
import authent.modele.PseudoDejaPrisException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component("facadeJoueurs")
public class FacadeJoueurImpl implements FacadeJoueur {


    /**
     * Dictionnaire des joueurs inscrits
     */
    private Map<String, Joueur> joueurs;

    public FacadeJoueurImpl() {
        this.joueurs = new HashMap<>();
    }


    @Override
    public Joueur inscription(String nouveauJoueur, String mdp) throws PseudoDejaPrisException {

        if (joueurs.containsKey(nouveauJoueur))
            throw new PseudoDejaPrisException();

        Joueur joueur = new Joueur(nouveauJoueur,mdp);
        this.joueurs.put(nouveauJoueur, joueur);
        return joueur;
    }



    @Override
    public void desincription(String pseudo, String mdp) throws JoueurInexistantException, OperationNonAutorisee {
        if (!joueurs.containsKey(pseudo))
            throw new JoueurInexistantException();

        Joueur j = joueurs.get(pseudo);
        if (j.checkPassword(mdp)) {
            this.joueurs.remove(pseudo);
        }
        else {
            throw new OperationNonAutorisee();
        }


    }

    @Override
    public Joueur getJoueurByPseudo(String pseudo) throws JoueurInexistantException {
        if (this.joueurs.containsKey(pseudo))
            return this.joueurs.get(pseudo);
        else
            throw new  JoueurInexistantException();
    }



}