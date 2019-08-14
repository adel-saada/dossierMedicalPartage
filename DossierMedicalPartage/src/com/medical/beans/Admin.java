package com.medical.beans;

public class Admin {

    /*------------------- PROPRIETE  -----------------------*/

    private int    id;
    private String login;
    private String motDePasse;

    /*------------------- CONSTRUCTEUR  -----------------------*/

    public Admin() {
    }

    public Admin( int id, String login, String motDePasse ) {
        super();
        this.id = id;
        this.login = login;
        this.motDePasse = motDePasse;
    }

    /*-------------------GETTERS SETTERS -------------------*/

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

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse( String motDePasse ) {
        this.motDePasse = motDePasse;
    }

    /*-------------------REDEFINITION METHODE  -------------------*/

    @Override
    public String toString() {
        return "Admin [id=" + id + ", login=" + login + ", motDePasse=" + motDePasse + "]";
    }

}
