package authent.modele;

public class Joueur {



    private String email;
    private String mdpJoueur;
    private String login;

    public Joueur(String email, String login, String mdpJoueur) {
        this.email = email;
        this.mdpJoueur = mdpJoueur;
        this.login = login;
    }


    public String getEmail() {
        return email;
    }

    public String getMdpJoueur() {
        return mdpJoueur;
    }

<<<<<<< HEAD
    public boolean checkPassword(String password) {
        return this.mdpJoueur.equals(password);
=======
    public String getLogin() {
        return login;
>>>>>>> b99f314b8594bebd4ffb1353a94c7c6564c5babb
    }
}
