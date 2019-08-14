package com.medical.beans;

import java.sql.Date;
import java.sql.Time;

public class Consultation {

    /*------------------- PROPRIETE  -----------------------*/

    private int  idPatient;
    private int  idMedecin;
    private int  idDiagnostic;
    private Date dateConsultation;
    private Time heureConsultation;

    /*------------------- CONSTRUCTEUR  -----------------------*/

    public Consultation() {

    }

    /*-------------------GETTERS SETTERS -------------------*/

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient( int idPatient ) {
        this.idPatient = idPatient;
    }

    public int getIdMedecin() {
        return idMedecin;
    }

    public void setIdMedecin( int idMedecin ) {
        this.idMedecin = idMedecin;
    }

    public int getIdDiagnostic() {
        return idDiagnostic;
    }

    public void setIdDiagnostic( int idDiagnostic ) {
        this.idDiagnostic = idDiagnostic;
    }

    public Date getDateConsultation() {
        return dateConsultation;
    }

    public void setDateConsultation( Date dateConsultation ) {
        this.dateConsultation = dateConsultation;
    }

    public Time getHeureConsultation() {
        return heureConsultation;
    }

    public void setHeureConsultation( Time heureConsultation ) {
        this.heureConsultation = heureConsultation;
    }

    /*-------------------REDEFINITION METHODE  -------------------*/

    @Override
    public String toString() {
        return "Consultation [idPatient=" + idPatient + ", idMedecin=" + idMedecin + ", idDiagnostic=" + idDiagnostic
                + ", dateConsultation=" + dateConsultation + ", heureConsultation=" + heureConsultation + "]";
    }

}
