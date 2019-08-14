package com.medical.forms;

import javax.servlet.http.HttpServletRequest;

public class UtilitaireForm {

    private static final String PARAM_SPE_MEDECIN             = "medecin";
    private static final String PARAM_SPE_CHIRURGIEN          = "chirurgien";
    private static final String PARAM_SPE_OSTEOPATHE          = "osteopathe";
    private static final String PARAM_SPE_KINE                = "kine";
    private static final String PARAM_SPE_ANESTHESISTE        = "anesthesiste";
    private static final String PARAM_SPE_DERMATO             = "dermato";

    /* Champs Medecin */
    private static final String CHAMP_SPE_MEDECIN_GENERALISTE = "médecin généraliste";
    private static final String CHAMP_SPE_CHIRURGIEN          = "chirurgien";
    private static final String CHAMP_SPE_OSTEOPATHE          = "ostéopathe";
    private static final String CHAMP_SPE_KINE                = "kinésithérapeute";
    private static final String CHAMP_SPE_ANESTHESISTE        = "anesthésiste";
    private static final String CHAMP_SPE_DERMATO             = "dérmatologue";

    /**
     * Objectif : Méthode utilitaire qui retourne null si un champ est vide, et
     * son contenu sinon
     * 
     * @param request
     * @param nomChamp
     * @return
     */
    static String getValeurParametre( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur.trim();
        }
    }

    /**
     * Objectif : Méthode utilitaire qui reçoit une chaine correspond à l'heure
     * souhaitée, et qui retourne la chaine avec l'heure au format valide pour
     * le type TIME (de mysql)
     * 
     * @param request
     * @param nomChamp
     * @return
     */
    static String getHeureConsultation( String selectedHeureConsultation ) {
        String chaineHeureConsultation = "";
        switch ( selectedHeureConsultation ) {
        case "huit":
            chaineHeureConsultation = "08:00:00";
            break;
        case "huit-trente":
            chaineHeureConsultation = "08:30:00";
            break;
        case "neuf":
            chaineHeureConsultation = "09:00:00";
            break;
        case "neuf-trente":
            chaineHeureConsultation = "09:30:00";
            break;
        case "dix":
            chaineHeureConsultation = "10:00:00";
            break;
        case "dix-trente":
            chaineHeureConsultation = "10:30:00";
            break;
        case "onze":
            chaineHeureConsultation = "11:00:00";
            break;
        case "onze-trente":
            chaineHeureConsultation = "11:30:00";
            break;
        case "treize":
            chaineHeureConsultation = "13:00:00";
            break;
        case "treize-trente":
            chaineHeureConsultation = "13:30:00";
            break;
        case "quatorze":
            chaineHeureConsultation = "14:00:00";
            break;
        case "quatorze-trente":
            chaineHeureConsultation = "14:30:00";
            break;
        case "quinze":
            chaineHeureConsultation = "15:00:00";
            break;
        case "quinze-trente":
            chaineHeureConsultation = "15:30:00";
            break;
        case "seize":
            chaineHeureConsultation = "16:00:00";
            break;
        case "seize-trente":
            chaineHeureConsultation = "16:30:00";
            break;
        case "dix-sept":
            chaineHeureConsultation = "17:00:00";
            break;
        case "dix-sept-trente":
            chaineHeureConsultation = "17:30:00";
            break;
        case "dix-huit":
            chaineHeureConsultation = "18:00:00";
            break;
        case "dix-huit-trente":
            chaineHeureConsultation = "18:30:00";
            break;
        }
        return chaineHeureConsultation;
    }

    /**
     * Objectif : Méthode utilitaire qui reçoit une chaine avec un nom de type
     * de medecin et lui renvoie la chaine au format valide pour la base de
     * donnée.
     * 
     * @param request
     * @param nomChamp
     * @return
     */
    static String getSpecialite( String selectedMedecin ) throws FormValidationException {
        String specialiste;
        switch ( selectedMedecin ) {
        case PARAM_SPE_MEDECIN:
            specialiste = CHAMP_SPE_MEDECIN_GENERALISTE;
            break;
        case PARAM_SPE_CHIRURGIEN:
            specialiste = CHAMP_SPE_CHIRURGIEN;
            break;
        case PARAM_SPE_OSTEOPATHE:
            specialiste = CHAMP_SPE_OSTEOPATHE;
            break;
        case PARAM_SPE_KINE:
            specialiste = CHAMP_SPE_KINE;
            break;
        case PARAM_SPE_ANESTHESISTE:
            specialiste = CHAMP_SPE_ANESTHESISTE;
            break;
        case PARAM_SPE_DERMATO:
            specialiste = CHAMP_SPE_DERMATO;
            break;
        default:
            throw new FormValidationException( "Veuillez selectionner une spécialité présente dans la liste" );
        }
        return specialiste;
    }

}
