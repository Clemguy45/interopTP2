package service;

import authent.modele.OperationNonAutorisee;
import authent.modele.Joueur;
import pileouface.modele.JoueurInexistantException;
import pileouface.modele.PseudoDejaPrisException;

public interface FacadeJoueur {
    /**
     * Inscription d'un nouveau joueur à la POFOL
     *
     * @param email
     * @param mdp
     * @return
     * @throws PseudoDejaPrisException
     */
    Joueur inscription(String email, String mdp) throws PseudoDejaPrisException;

    Joueur getJoueurByPseudo(String idJoueur) throws JoueurInexistantException;


    /**
     * Permet de se désinscrire de la plate-forme
     *
     * @param pseudo
     * @throws JoueurInexistantException
     */
    void desincription(String pseudo) throws JoueurInexistantException;
}
