package com.medical.beans;

public abstract class Utilisateur {

    /*------------------- PROPRIETE  -------------------------*/

    private int    id;
    private String login;
    private String nom;
    private String prenom;
    private String tel;
    private String email;
    private String rue;
    private String ville;
    private String codePostal;
    private String motDePasse;

    /*------------------- CONSTRUCTEUR  -----------------------*/

    public Utilisateur() {
    }

    /*------------------- GETTERS SETTERS  -----------------------*/

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin( String login ) {
        this.login = login;
    }

    public String getNom() {
        return nom;
    }

    public void setNom( String nom ) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom( String prenom ) {
        this.prenom = prenom;
    }

    public String getTel() {
        return tel;
    }

    public void setTel( String tel ) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

    public String getRue() {
        return rue;
    }

    public void setRue( String rue ) {
        this.rue = rue;
    }

    public String getVille() {
        return ville;
    }

    public void setVille( String ville ) {
        this.ville = ville;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal( String codePostal ) {
        this.codePostal = codePostal;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse( String motDePasse ) {
        this.motDePasse = motDePasse;
    }

    /*-------------------REDEFINITION METHODE  -------------------*/

    @Override
    public String toString() {
        return "id=" + id + ", login=" + login + ", nom=" + nom + ", prenom=" + prenom + ", tel=" + tel
                + ", email=" + email + ", rue=" + rue + ", ville=" + ville + ", codePostal=" + codePostal
                + ", motDePasse=" + motDePasse;
    }

}
