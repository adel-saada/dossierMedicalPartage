package com.medical.beans;

public class Diagnostic {

    /*------------------- PROPRIETE  -----------------------*/

    private int    idDiagnostic;
    private String descriptifDiagnostic;
    private String allergiesDiagnostic;
    private String antecedentsMedicauxDiagnostic;
    private String prescriptionDiagnostic;

    /*------------------- CONSTRUCTEUR  -----------------------*/

    public Diagnostic() {

    }

    /*-------------------GETTERS SETTERS -------------------*/

    public int getIdDiagnostic() {
        return idDiagnostic;
    }

    public void setIdDiagnostic( int idDiagnostic ) {
        this.idDiagnostic = idDiagnostic;
    }

    public String getDescriptifDiagnostic() {
        return descriptifDiagnostic;
    }

    public void setDescriptifDiagnostic( String descriptifDiagnostic ) {
        this.descriptifDiagnostic = descriptifDiagnostic;
    }

    public String getAllergiesDiagnostic() {
        return allergiesDiagnostic;
    }

    public void setAllergiesDiagnostic( String allergiesDiagnostic ) {
        this.allergiesDiagnostic = allergiesDiagnostic;
    }

    public String getAntecedentsMedicauxDiagnostic() {
        return antecedentsMedicauxDiagnostic;
    }

    public void setAntecedentsMedicauxDiagnostic( String antecedentsMedicauxDiagnostic ) {
        this.antecedentsMedicauxDiagnostic = antecedentsMedicauxDiagnostic;
    }

    public String getPrescriptionDiagnostic() {
        return prescriptionDiagnostic;
    }

    public void setPrescriptionDiagnostic( String prescriptionDiagnostic ) {
        this.prescriptionDiagnostic = prescriptionDiagnostic;
    }

    /*-------------------REDEFINITION METHODE  -------------------*/

    @Override
    public String toString() {
        return "Diagnostic [idDiagnostic=" + idDiagnostic + ", descriptifDiagnostic=" + descriptifDiagnostic
                + ", allergiesDiagnostic=" + allergiesDiagnostic + ", antecedentsMedicauxDiagnostic="
                + antecedentsMedicauxDiagnostic + ", prescriptionDiagnostic=" + prescriptionDiagnostic + "]";
    }

}
