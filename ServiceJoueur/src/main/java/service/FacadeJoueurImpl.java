package service;

import authent.modele.Joueur;
import authent.modele.OperationNonAutorisee;
import pileouface.modele.JoueurInexistantException;
import pileouface.modele.PseudoDejaPrisException;

import java.util.HashMap;
import java.util.Map;

public class FacadeJoueurImpl implements FacadeJoueur{
    /**
     * Dictionnaire des joueurs inscrits
     */
    private Map<String,Joueur> joueurs;


    public FacadeJoueurImpl() {
        this.joueurs = new HashMap<>();
    }


    @Override
    public Joueur inscription(String email, String mdp) throws PseudoDejaPrisException {
        if (joueurs.containsKey(email))
            throw new PseudoDejaPrisException();
        Joueur v = new Joueur(email,  mdp);
        this.joueurs.put(email, v);
        return v;
    }

    @Override
    public Joueur getJoueurByPseudo(String idJoueur)  throws JoueurInexistantException {
        if (!joueurs.containsKey(idJoueur)) {
            throw new JoueurInexistantException();
        }
        return joueurs.get(idJoueur);
    }

    @Override
    public void desincription(String pseudo) throws JoueurInexistantException {
        if (!joueurs.containsKey(pseudo))
            throw new JoueurInexistantException();
        joueurs.remove(pseudo);
    }

}
