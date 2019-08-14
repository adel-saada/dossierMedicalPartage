package com.medical.beans;

public class Medecin extends Utilisateur {

    /*------------------- PROPRIETE  -----------------------*/

    private String specialite;

    /*------------------- CONSTRUCTEUR  -----------------------*/

    public Medecin() {
    }

    /*-------------------GETTERS SETTERS -------------------*/

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite( String specialite ) {
        this.specialite = specialite;
    }

    /*-------------------REDEFINITION METHODE  -------------------*/

    @Override
    public String toString() {
        return "Medecin [" + super.toString() + "specialite=" + specialite + "]";
    }

}
