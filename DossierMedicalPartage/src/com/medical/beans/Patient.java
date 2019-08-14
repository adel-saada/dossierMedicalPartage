package com.medical.beans;

public class Patient extends Utilisateur {

    /*------------------- PROPRIETE  -------------------------*/

    private String numeroSS;
    private char   sexe;

    /*------------------- CONSTRUCTEUR  -----------------------*/

    public Patient() {
    }

    /*-------------------GETTERS SETTERS -------------------*/

    public String getNumeroSS() {
        return numeroSS;
    }

    public void setNumeroSS( String numeroSS ) {
        this.numeroSS = numeroSS;
    }

    public char getSexe() {
        return sexe;
    }

    public void setSexe( char sexe ) {
        this.sexe = sexe;
    }

    /*-------------------REDEFINITION METHODE  -------------------*/

    @Override
    public String toString() {
        return "Patient [" + super.toString() + "numeroSS=" + numeroSS + ", sexe=" + sexe + "]";
    }
}
